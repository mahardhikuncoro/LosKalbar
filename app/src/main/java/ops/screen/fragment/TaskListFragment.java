package ops.screen.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import base.network.EndPoint;
import base.network.LoginJson;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.network.TaskListJson;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.TaskListDetailModel;
import base.sqlite.Userdata;
import butterknife.BindView;
import butterknife.ButterKnife;

import butterknife.OnClick;
import id.co.smmf.mobile.R;
import ops.screen.TaskListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import user.login.LoginActivity;

public class TaskListFragment extends Fragment {


    @BindView(R.id.taskListRecycleAll)
    RecyclerView taskListRecycleAll;

    @BindView(R.id.btn_all) Button btn_all;
    @BindView(R.id.btn_shortdata) Button btn_shortdata;
    @BindView(R.id.btn_fulldata) Button btn_fulldata;
    @BindView(R.id.btn_approval) Button btn_approval;
    @BindView(R.id.linearmenu1) LinearLayout linearmenu1;
    @BindView(R.id.linearmenu2) LinearLayout linearmenu2;
    @BindView(R.id.linearmenu3) LinearLayout linearmenu3;
    @BindView(R.id.linearmenu4) LinearLayout linearmenu4;


    private SQLiteConfigKu config;
    private ArrayList<TaskListDetailModel> taskListList;
    private TaskListAdapter taskListAdapter;
    private EndPoint endPoint;
    private NetworkConnection networkConnection;
    public Dialog dialogmaterial;
    private Userdata userdata;
    private ArrayList<String> menulist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.tasklist_fragment, container, false);
        ButterKnife.bind(this, view);
        btn_all.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_selected));
        btn_shortdata.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_unselected));
        btn_fulldata.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_unselected));
        btn_approval.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_unselected));
        initialisation();
        prepare();
        getMenuAccess();
        return view;
    }

    private void prepare(){
        linearmenu1.setVisibility(View.GONE);
        linearmenu2.setVisibility(View.GONE);
        linearmenu3.setVisibility(View.GONE);
        linearmenu4.setVisibility(View.GONE);
    }

   @OnClick(R.id.btn_all)
    public void showAll() {
       btn_all.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_selected));
       btn_shortdata.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_unselected));
       btn_fulldata.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_unselected));
       btn_approval.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_unselected));
       fillList("allassigned");
    }

    @OnClick(R.id.btn_shortdata)
    public void shortData() {
        btn_all.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_unselected));
        btn_shortdata.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_selected));
        btn_fulldata.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_unselected));
        btn_approval.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_unselected));
        fillList("unassigned");
    }


    @OnClick(R.id.btn_fulldata)
    public void fullData(){
        btn_all.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_unselected));
        btn_shortdata.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_unselected));
        btn_fulldata.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_selected));
        btn_approval.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_unselected));
        fillList("assigned");
    }

    @OnClick(R.id.btn_approval)
    public void approvalData() {
        btn_all.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_unselected));
        btn_shortdata.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_unselected));
        btn_fulldata.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_unselected));
        btn_approval.setBackgroundDrawable(getResources().getDrawable(R.drawable.styles_background_selected));
        fillList("unassigned");
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animation_enter, R.animator.animation_out, R.animator.animation_back_left, R.animator.animation_back_right);
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    protected void fillList(final String typeList){

//         dialogmaterial = new MaterialDialog.Builder(getActivity())
//                .content(R.string.loading)
//                .progress(true, 0)
//                .cancelable(false)
//                .show();

        if (!networkConnection.isNetworkConnected()) {
//            dialogmaterial.dismiss();
            dialog(R.string.errorNoInternetConnection);

        } else {
            final TaskListJson.TasklistRequest request = new TaskListJson().new TasklistRequest();
            request.setUserid(userdata.select().getUserid());
            request.setType(typeList);
            request.setTc("5.0");
            String token = userdata.select().getAccesstoken();
            Call<TaskListJson.TasklistCallback> call = endPoint.getInbox(token, request);
            call.enqueue(new Callback<TaskListJson.TasklistCallback>() {
                @Override
                public void onResponse(Call<TaskListJson.TasklistCallback> call, Response<TaskListJson.TasklistCallback> response) {

                    if(response.body().getMessage().equalsIgnoreCase("Data not available."))
                    {
//                        dialogmaterial.dismiss();
                        Log.e("Data List"," Empty");
                        taskListList = new ArrayList<TaskListDetailModel>();
                        taskListAdapter = new TaskListAdapter(getActivity().getApplicationContext(), taskListList, typeList);
                        taskListRecycleAll.setAdapter(taskListAdapter);
                    }else if(response.body().getMessage().equalsIgnoreCase("Invalid Token"))  {
                        Toast.makeText(getActivity().getApplicationContext(), R.string.invalidToken, Toast.LENGTH_SHORT)
                                .show();
                        userdata.deleteAll();
                        startActivity(new Intent(getActivity(), LoginActivity.class));

                    }else {
                        Log.e("Data List"," ADA ISINYA");
//                        dialogmaterial.dismiss();
                        taskListList = new ArrayList<TaskListDetailModel>();
                        for (TaskListJson.TasklistCallback.Data callbackList : response.body().getData()) {
                            TaskListDetailModel detailModel = new TaskListDetailModel();
                            detailModel.setNamaNasabah(callbackList.getCustomername().toUpperCase());
                            detailModel.setIdNasabah(callbackList.getAp_regno().toUpperCase());
                            detailModel.setTrack_id(callbackList.getTrack_id());
                            detailModel.setCustomertype_id(callbackList.getCustomertype_id());
                            detailModel.setFormCode(callbackList.getFormCode());
                            taskListList.add(detailModel);
                        }
                        // set up the RecyclerView

                        taskListAdapter = new TaskListAdapter(getActivity().getApplicationContext(), taskListList, typeList);
                        taskListRecycleAll.setAdapter(taskListAdapter);
                    }

                }

                @Override
                public void onFailure(Call<TaskListJson.TasklistCallback> call, Throwable t) {
//                    dialogmaterial.dismiss();
                }
            });
        }
    }

    private void initialisation() {
        userdata = new Userdata(getActivity().getApplicationContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        taskListRecycleAll.setLayoutManager(linearLayoutManager);
        taskListRecycleAll.setHasFixedSize(true);
        taskListRecycleAll.smoothScrollToPosition(10);

        config = new SQLiteConfigKu(getActivity().getApplicationContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();
        menulist = new ArrayList<>();
        endPoint = retrofit.create(EndPoint.class);
        networkConnection = new NetworkConnection(getActivity());

    }

    protected void getMenuAccess(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialogmaterial = builder.create();
        dialogmaterial.show();
        if (!networkConnection.isNetworkConnected()) {
            dialogmaterial.dismiss();
            dialog(R.string.errorNoInternetConnection);
        }else{

            final LoginJson.LoginRequest request = new LoginJson().new LoginRequest();
            request.setUserid(userdata.select().getUserid());
            Call<LoginJson.getmenuCallback> callback = endPoint.getMenuAcces(userdata.select().getAccesstoken(),request);
            callback.enqueue(new Callback<LoginJson.getmenuCallback>() {
                @Override
                public void onResponse(Call<LoginJson.getmenuCallback> call, Response<LoginJson.getmenuCallback> response) {
                    try{
                        if(response.isSuccessful()) {
                            if(response.body().getStatus().equalsIgnoreCase("1")){
                                for(LoginJson.getmenuCallback.Data datamodel : response.body().getData()){
                                    String data = datamodel.getMenudesc();
                                    menulist.add(data);
                                }
                                showMenu();
                            }else if(response.body().getMessage().equalsIgnoreCase("Invalid Token")) {
                                userdata.deleteAll();
                                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                                Toast.makeText(getActivity().getApplicationContext(), R.string.invalidToken, Toast.LENGTH_LONG)
                                        .show();
                            }else if(response.body().getStatus().equalsIgnoreCase("0")) {
                                userdata.deleteAll();
                                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                            }
                            dialogmaterial.dismiss();

                        }else{
                            dialogmaterial.dismiss();
                            dialogMessage(response.body().getMessage());
                        }
                    }catch (Exception e){
                        dialogmaterial.dismiss();
                        dialog(R.string.errorBackend);
                    }

                }
                @Override
                public void onFailure(Call<LoginJson.getmenuCallback> call, Throwable t) {
                    dialogmaterial.dismiss();
                    dialog(R.string.errorBackend);
                }
            });
        }
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
                .content(rString)
                .positiveText(R.string.buttonClose)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .show();
    }
    private void showMenu() {
        linearmenu1.setVisibility(View.VISIBLE);
        btn_all.setText("ALL ASSIGNED");
        for(int i=0 ; i < menulist.size();i++){
            Log.e("menu "," : " + menulist.get(i).toString());
            if(menulist.get(i).toString().equalsIgnoreCase("Input Data Usaha")){
//                linearmenu4.setVisibility(View.VISIBLE);
//                btn_approval.setVisibility(View.VISIBLE);
//                btn_approval.setText("UNASSIGNED");
            }else if(menulist.get(i).toString().equalsIgnoreCase("Input Data Jaminan")){
                linearmenu4.setVisibility(View.VISIBLE);
                btn_approval.setVisibility(View.VISIBLE);
                btn_approval.setText("UNASSIGNED");
            }/*else if(menulist.get(i).toString().equalsIgnoreCase("Pengajuan Awal")){
                linearmenu4.setVisibility(View.VISIBLE);
                btn_approval.setVisibility(View.VISIBLE);
                btn_approval.setText("UNASSIGNED");
            }*/else if(menulist.get(i).toString().equalsIgnoreCase("Daftar Data Jaminan")){
                linearmenu4.setVisibility(View.VISIBLE);
                btn_approval.setVisibility(View.VISIBLE);
                btn_approval.setText("UNASSIGNED");
            }
        }
        fillList("allassigned");
    }

}
