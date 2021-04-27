package ops.screen.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.List;

import base.network.EndPoint;
import base.network.LoginJson;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.network.Slider;
import base.network.TaskListJson;
import base.sqlite.TaskListDetailModel;
import base.sqlite.Userdata;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.SliderSQL;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.smmf.mobile.R;
import ops.screen.MainActivityDashboard;
import ops.screen.TaskListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import user.login.LoginActivity;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    @BindView(R.id.slider)
    SliderLayout sliderLayout;
    @BindView(R.id.taskListRecycle)
    RecyclerView recyclerView;
    @BindView(R.id.txtSeeAll)
    TextView txtSeeAll;
    @BindView(R.id.linearmenu1)
    LinearLayout linearmenu1;
    @BindView(R.id.linearmenu2)
    LinearLayout linearmenu2;
    @BindView(R.id.linearmenu3)
    LinearLayout linearmenu3;
    @BindView(R.id.linearmenu4)
    LinearLayout linearmenu4;



    TaskListAdapter taskListAdapter;

    private TaskListFragment taskListFragment;
    private SQLiteConfigKu sqLiteConfigKu;
    private SliderSQL slidersql;
    private Userdata userdata;
    private ArrayList<TaskListDetailModel> taskListList;
    private EndPoint endPoint;
    private NetworkConnection networkConnection;
    private Dialog dialog;
    private ArrayList<String> menulist;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dashboard_activity, container, false);
        ButterKnife.bind(this, view);
        linearmenu1.setVisibility(View.GONE);
        linearmenu2.setVisibility(View.GONE);
        linearmenu3.setVisibility(View.GONE);
        linearmenu4.setVisibility(View.GONE);
        initialisation();
        getMenuAccess();

//        fillList();

        if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)) {
            dialogPermission();
        }else {
            Integer slidesize = slidersql.count();
            Log.e("SLIDER SIZE " , " + "+ slidesize);
            if (slidesize > 0) {
                sliderLayout.setBackgroundColor(getResources().getColor(R.color.white));

                for (int i = 1; i <= slidesize; i++) {
                    Slider temp = slidersql.select(i);

                    TextSliderView textSliderView = new TextSliderView(getActivity());
                    textSliderView
                            .description(temp.getName())
                            .image(temp.getImage())
                            .error(R.drawable.defaultslide)
                            .empty(R.drawable.defaultslide)
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(HomeFragment.this);

                    textSliderView.bundle(new Bundle());

                    textSliderView.getBundle()
                            .putString("extra", temp.getLink());

                    textSliderView.getBundle()
                            .putString("package", temp.getPackage_name());

                    sliderLayout.addSlider(textSliderView);

                }

                sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
                sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                sliderLayout.setCustomAnimation(new DescriptionAnimation());
                sliderLayout.setDuration(6000);
                sliderLayout.addOnPageChangeListener(HomeFragment.this);
            }
        }


        return view;
    }

    private void callTopAssign() {


        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {
            final TaskListJson.TasklistRequest request = new TaskListJson().new TasklistRequest();
            request.setUserid(userdata.select().getUserid());
            request.setType("topassigned");
            String token = userdata.select().getAccesstoken();
            Call<TaskListJson.TasklistCallback> call = endPoint.getInbox(token, request);
            call.enqueue(new Callback<TaskListJson.TasklistCallback>() {
                @Override
                public void onResponse(Call<TaskListJson.TasklistCallback> call, Response<TaskListJson.TasklistCallback> response) {
                    try {
                        dialog.dismiss();
                        if (response.body().getMessage().equalsIgnoreCase("Invalid Token")) {
                            Toast.makeText(getActivity().getApplicationContext(), R.string.invalidToken, Toast.LENGTH_SHORT)
                                    .show();
                            userdata.deleteAll();
                            dialogMessage(response.body().getMessage());
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        } else if (response.body().getMessage().equalsIgnoreCase("Data not available.")) {
                            Log.e("Data List", " Empty");
                            dialogMessage(response.body().getMessage());
                        } else {
                            taskListList = new ArrayList<TaskListDetailModel>();
                            for (TaskListJson.TasklistCallback.Data datamodel : response.body().getData()) {
                                TaskListDetailModel detailModel = new TaskListDetailModel();
                                detailModel.setIdNasabah(datamodel.getAp_regno().toUpperCase());
                                detailModel.setCustomertype_id(datamodel.getCustomertype_id());
                                detailModel.setNamaNasabah(datamodel.getCustomername().toUpperCase());
                                detailModel.setCustomerdocument_id(datamodel.getCustomerdocument_id());
                                detailModel.setCustomerdocument_type(datamodel.getCustomerdocument_type());
                                detailModel.setProduct_id(datamodel.getProduct_id());
                                detailModel.setProduct_desc(datamodel.getProduct_desc());
                                detailModel.setPlafon(datamodel.getPlafon());
                                detailModel.setTrack_id(datamodel.getTrack_id());
                                detailModel.setTrack_name(datamodel.getTrack_name());
                                detailModel.setFormCode(datamodel.getFormCode());
                                taskListList.add(detailModel);
                            }
                            // set up the RecyclerView
                            taskListAdapter = new TaskListAdapter(getActivity().getApplicationContext(), taskListList, "allassigned");
                            recyclerView.setAdapter(taskListAdapter);
                        }
                    }catch (Exception e){
                        dialog.dismiss();
                        dialog(R.string.errorBackend);
                    }
                }

                @Override
                public void onFailure(Call<TaskListJson.TasklistCallback> call, Throwable t) {
                    dialog.dismiss();
                }
            });
        }
    }

    protected void getMenuAccess(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        }else{

            final LoginJson.LoginRequest request = new LoginJson().new LoginRequest();
            request.setUserid(userdata.select().getUserid());
            Call<LoginJson.getmenuCallback> callback = endPoint.getMenuAcces(userdata.select().getAccesstoken(),request);
            callback.enqueue(new Callback<LoginJson.getmenuCallback>() {
                @Override
                public void onResponse(Call<LoginJson.getmenuCallback> call, Response<LoginJson.getmenuCallback> response) {
                    try {

                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equalsIgnoreCase("1")) {
                                for (LoginJson.getmenuCallback.Data datamodel : response.body().getData()) {
                                    String data = datamodel.getMenudesc();
                                    menulist.add(data);
                                }
                                showMenu();
                            } else if (response.body().getMessage().equalsIgnoreCase("Invalid Token")) {
                                userdata.deleteAll();
                                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                                Toast.makeText(getActivity().getApplicationContext(), R.string.invalidToken, Toast.LENGTH_LONG)
                                        .show();
                            } else if (response.body().getStatus().equalsIgnoreCase("0")) {
                                userdata.deleteAll();
                                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                            }

                        } else {
                            dialog.dismiss();
                            dialogMessage(response.body().getMessage());
                        }
                    }catch (Exception e){
                    }
                }
                @Override
                public void onFailure(Call<LoginJson.getmenuCallback> call, Throwable t) {
                    dialog.dismiss();
                    dialog(R.string.errorBackend);
                }
            });
        }
    }

    private void showMenu() {
        for(int i=0 ; i < menulist.size();i++){
            Log.e("menu "," : " + menulist.get(i).toString());
            if(menulist.get(i).toString().equalsIgnoreCase("Input Data Usaha")){
                linearmenu3.setVisibility(View.VISIBLE);
            }else if(menulist.get(i).toString().equalsIgnoreCase("Input Data Jaminan")){
                linearmenu2.setVisibility(View.VISIBLE);
            }else if(menulist.get(i).toString().equalsIgnoreCase("Pengajuan Awal")){
                linearmenu1.setVisibility(View.VISIBLE);
            }else if(menulist.get(i).toString().equalsIgnoreCase("Daftar Data Jaminan")){
                linearmenu4.setVisibility(View.VISIBLE);
            }
        }
        callTopAssign();
    }

    private void initialisation() {
        userdata = new Userdata(getActivity().getApplicationContext());
        slidersql = new SliderSQL(getActivity().getApplicationContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        sqLiteConfigKu = new SQLiteConfigKu(getActivity().getApplicationContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sqLiteConfigKu.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();
        endPoint = retrofit.create(EndPoint.class);

        networkConnection = new NetworkConnection(getActivity());
        menulist = new ArrayList<>();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        String link = slider.getBundle().getString("extra");
        String package_name = slider.getBundle().getString("package");

        if (package_name.length() > 1) {
            if (isPackageExisted(package_name)) {
                Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage(package_name);
                startActivity(launchIntent);//null pointer check in case package name was not found
            } else {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(slider.getBundle().get("extra") + ""));
                startActivity(browserIntent);
            }
        } else if (!link.equalsIgnoreCase("") && link.length() > 10) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(slider.getBundle().get("extra") + ""));
            startActivity(browserIntent);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick(R.id.txtSeeAll) public void seeAll(){
        taskListFragment = new TaskListFragment();
        loadFragment(taskListFragment);
    }


    private void loadFragment(Fragment fragment) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animation_enter, R.animator.animation_out, R.animator.animation_back_left, R.animator.animation_back_right);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public boolean isPackageExisted(String targetPackage) {
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = getActivity().getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }

    protected void dialogPermission() {
        new MaterialDialog.Builder(getActivity())
                .content("Please allow all permission on your app setting, thank you")
                .positiveText(R.string.buttonClose)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .cancelable(false)
                .show();
    }

    protected void dialog(int rString) {
       new MaterialDialog.Builder(getActivity())
                .title(rString).icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .positiveText(R.string.buttonClose)
                .positiveColor(getResources().getColor(R.color.black))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        getMenuAccess();
                    }
                })
                .cancelable(true)
                .show();
    }
    protected void dialogMessage(String rString) {
       new MaterialDialog.Builder(getActivity())
                .title(rString).icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .positiveText(R.string.buttonClose)
                .positiveColor(getResources().getColor(R.color.black))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .show();
    }
  /*  protected void fillList(){

        taskListList = new ArrayList<TaskListDetailModel>();

        for(int i =0;i<=2;i++) {
            TaskListDetailModel detailModel = new TaskListDetailModel();
            detailModel.setNamaNasabah("Agus_" + i);
            Log.e("Agus ","Id Agus_" + i );
            detailModel.setIdNasabah(String.valueOf(i));
            taskListList.add(detailModel);
        }

        // set up the RecyclerView
        taskListAdapter = new TaskListAdapter(getActivity().getApplicationContext(),taskListList);
        recyclerView.setAdapter(taskListAdapter);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_application:
//                dialog(R.string.addPinNumber);
                return true;
//                break;
            // action with ID action_settings was selected
            case R.id.action_logout:
                Toast.makeText(getActivity(), "Settings selected", Toast.LENGTH_SHORT)
                        .show();
                return true;
//                break;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.linearmenu1)public void linearclick1(){
        gotolist("1.0");
    }
    @OnClick(R.id.linearmenu2)public void linearclick2() { gototask("1.0"); }
    @OnClick(R.id.linearmenu3)public void linearclick3(){
        gototask("1.0");
    }
    @OnClick(R.id.linearmenu4)public void linearclick4(){
        gototask("1.0");
    }

    public void gotolist(String tc){

        Intent intent = new Intent(getActivity(), FullEntry.class);
        //LIST WITH FILL
//        intent.putExtra("REGNO","");
//        intent.putExtra("TC","5.0");
//        intent.putExtra("TYPE","IDJ");

        intent.putExtra("REGNO","");
        intent.putExtra("TC",tc);
        intent.putExtra("TYPE","SDE IND");
        intent.putExtra("STATUS","0");
        intent.putExtra("NEW_DATA","1");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void gototask(String tc){

        Intent i = new Intent(getActivity(), MainActivityDashboard.class);
        i.putExtra("FLAG_SUBMIT","1");
        startActivity(i);
    }

}
