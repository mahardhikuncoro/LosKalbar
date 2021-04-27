package ops.screen.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import base.network.FormJson;
import base.network.RetreiveJson;
import base.network.SetDataJson;
import base.screen.BaseDialogActivity;
import base.sqlite.DataModel;
import base.sqlite.TaskListDetailModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.smmf.mobile.R;
import ops.screen.FormActivity;
import ops.screen.MainActivityDashboard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import user.changepassword.ChangePasswordActivity;
import user.login.LoginActivity;

public class FullEntryList extends BaseDialogActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.titleList) TextView titleList;
    @BindView(R.id.btnback_toolbar) ImageView btnback_toolbar;
    @BindView(R.id.recycleMenuEntry) RecyclerView recyclerView;
    @BindView(R.id.txtFullname) TextView txtFullname;
    @BindView(R.id.txtIdUser) TextView txtIdUser;
    @BindView(R.id.surveyBackToPullButton) Button surveyBackToPullButton;
    @BindView(R.id.surveyFormSubmitButton) Button surveyFormSubmitButton;

    private ArrayList<TaskListDetailModel> taskListList;
    private FullEntryListAdapter fullEntryAdapter;
    private Dialog dialog;
    private String regnumb,tc,typelist, sectionname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        transparentStatusbar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullentry_fragment);
        ButterKnife.bind(this);
        initiateApiData();
        toolbar.inflateMenu(R.menu.menu_actionbar);
        setToolbar();
        taskListList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        tc = getIntent().getStringExtra("TC");
        typelist = getIntent().getStringExtra("TYPELIST");
        regnumb = getIntent().getExtras().getString("REGNO");
        surveyBackToPullButton.setVisibility(View.GONE);
        surveyFormSubmitButton.setText("TAMBAH");
        if(!tc.equalsIgnoreCase("5.0"))
            surveyBackToPullButton.setVisibility(View.GONE);
        retreiveData(regnumb);

    }

    public void retreiveData(final String regno){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {

            final RetreiveJson.RetreiveRequest request = new RetreiveJson().new RetreiveRequest();

            request.setRegno(regno);
            request.setUserid(userdata.select().getUserid());
            request.setTc(getIntent().getExtras().getString("TC"));
            request.setType(getIntent().getExtras().getString("TYPE"));
            request.setFormname(getIntent().getExtras().getString("FORM_NAME"));
            request.setDataLevel("list");

            Call<RetreiveJson.RetreiveCallback> callBack = endPoint.getDataRetreive(userdata.select().getAccesstoken(), request);
            callBack.enqueue(new Callback<RetreiveJson.RetreiveCallback>() {
                @Override
                public void onResponse(Call<RetreiveJson.RetreiveCallback> call, Response<RetreiveJson.RetreiveCallback> response) {
                    if (response.isSuccessful()) {
                        if(response.body().getAllowNewItem().equalsIgnoreCase("0")){
                            surveyFormSubmitButton.setVisibility(View.GONE);
                        }

                        if (response.body().getData().size() <= 0) {
                            dialogMessage(response.body().getMessage());
                        } else {
                            for (RetreiveJson.RetreiveCallback.Data datamodel : response.body().getData()) {
                                Log.e("getDataRetreive", "KOKOKOK " + getIntent().getExtras().getString("FORM_NAME"));
                                Log.e("getSectionTYpe", "KOKOKOK " + datamodel.getKeyFieldName());
                                TaskListDetailModel model = new TaskListDetailModel();
                                model.setKeyFieldName(datamodel.getKeyFieldName());
                                model.setDataId(datamodel.getDataId());
                                model.setDataDesc(datamodel.getDataDesc());

                                model.setFormName(getIntent().getExtras().getString("FORM_NAME"));
                                model.setSectionName(getIntent().getExtras().getString("SECTION_NAME"));
                                model.setTableName(getIntent().getExtras().getString("TABLE_NAME"));
                                taskListList.add(model);
                            }
                        }
                        dialog.dismiss();
                        setAdapter(regno);
                    }else{
                        dialogMessage(response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<RetreiveJson.RetreiveCallback> call, Throwable t) {

                }
            });
        }

    }



    private void setToolbar(){

        String id = (getIntent().getExtras().getString("REGNO") == null ? "" : getIntent().getExtras().getString("REGNO"));
        String fullname = (getIntent().getExtras().getString("NAMA_NASABAH") == null ? "": getIntent().getExtras().getString("NAMA_NASABAH"));
        txtIdUser.setText(id);
        txtFullname.setText(fullname);
        txtFullname.setAllCaps(true);
        sectionname = getIntent().getExtras().getString("SECTION_NAME");
        titleList.setText(sectionname);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    // action with ID action_refresh was selected
                    case R.id.action_application:
                        final View addView = getLayoutInflater().inflate(R.layout.about_bexi, null);
                        new AlertDialog.Builder(FullEntryList.this).setTitle("LOS KALBAR").setView(addView)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                }).setNegativeButton("", null).show();

                        break;
                    case R.id.action_change_pass:
                        Intent intentchangepass = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                        startActivity(intentchangepass);
                        finish();
                        break;
                    case R.id.action_logout:
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        formData.deleteAll();
                        userdata.deleteAll();
                        finish();
                        Toast.makeText(getApplicationContext(), "Logout Application", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
    @OnClick(R.id.btnback_toolbar)
    public void backButton(){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intentform= new Intent(getApplicationContext(), FullEntry.class);
        intentform.putExtra("SECTION_NAME", getIntent().getStringExtra("SECTION_NAME"));
        intentform.putExtra("TYPE", getIntent().getStringExtra("TYPE"));
        intentform.putExtra("REGNO",getIntent().getStringExtra("REGNO"));
        intentform.putExtra("STATUS",getIntent().getStringExtra("STATUS"));
        intentform.putExtra("TC",getIntent().getStringExtra("TC"));
        intentform.putExtra("NAMA_NASABAH",getIntent().getStringExtra("NAMA_NASABAH"));
        startActivity(intentform);
        finish();
    }

    private  void setAdapter(String regno){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fullEntryAdapter = new FullEntryListAdapter(this,taskListList, regno,getIntent().getExtras().getString("TC"),getIntent().getExtras().getString("TYPE"),getIntent().getExtras().getString("NAMA_NASABAH"));
        recyclerView.setAdapter(fullEntryAdapter);
    }

    @OnClick(R.id.surveyFormSubmitButton)
    public void submitData(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            builder.setView(R.layout.progress_bar).setCancelable(false);
//        }
//        dialog = builder.create();
//        dialog.show();
//        if (!networkConnection.isNetworkConnected()) {
//            dialog.dismiss();
//            dialog(R.string.errorNoInternetConnection);
//        } else {
//
//            final RetreiveJson.RetreiveRequest request = new RetreiveJson().new RetreiveRequest();
//            request.setRegno(getIntent().getExtras().getString("REGNO"));
//            request.setUserid(userdata.select().getUserid());
//            request.setTc(getIntent().getExtras().getString("TC"));
//            request.setType("IDJ");
//            request.setDataLevel("listfield");
//            request.setListItemId("new");
//            request.setFormname(getIntent().getExtras().getString("FORM_NAME"));
//            Log.e("TYPELIST", " : " + typelist);
//            Log.e("TC ", " : " + getIntent().getStringExtra("TC"));
//            Log.e("FORM_NAME ", " : " + getIntent().getStringExtra("FORM_NAME"));
////            Log.e("SECTION_TYPE ", " : " + getIntent().getStringExtra("TYPE"));
//
//            Call<RetreiveJson.RetreiveCallback> callBack = endPoint.getDataRetreive(userdata.select().getAccesstoken(), request);
//            callBack.enqueue(new Callback<RetreiveJson.RetreiveCallback>() {
//                @Override
//                public void onResponse(Call<RetreiveJson.RetreiveCallback> call, Response<RetreiveJson.RetreiveCallback> response) {
//                    if(response.isSuccessful())
//                    {
//                        dialog.dismiss();
//                        Log.e("Berhasil Load"," ");
        Intent intent = new Intent(getApplicationContext(), FormActivity.class);
//                        for (RetreiveJson.RetreiveCallback.Data datamodel : response.body().getData()) {
////                                Log.e("getDataRetreive", "KOKOKOK " + getIntent().getExtras().getString("SECTION_NAME"));
//                            Log.e("getSectionTYpe", "KOKOKOK " + datamodel.getKeyFieldName());
//                            TaskListDetailModel model = new TaskListDetailModel();
//                            model.setKeyFieldName(datamodel.getKeyFieldName());
//                            model.setDataId(datamodel.getDataId());
//                            model.setDataDesc(datamodel.getDataDesc());
////                            model.setFormName(getIntent().getExtras().getString("FORM_NAME"));
////                            model.setSectionName(getIntent().getExtras().getString("SECTION_NAME"));
////                            model.setTableName(getIntent().getExtras().getString("TABLE_NAME"));
//                            taskListList.add(model);
//                        }
//
        intent.putExtra("SECTION_NAME", getIntent().getExtras().getString("SECTION_NAME"));
        intent.putExtra("REGNO", getIntent().getExtras().getString("REGNO"));
        intent.putExtra("TC", getIntent().getExtras().getString("TC"));
        intent.putExtra("TYPE", getIntent().getExtras().getString("TYPE"));
        intent.putExtra("TABLE_NAME", getIntent().getExtras().getString("TABLE_NAME"));
        intent.putExtra("FORM_NAME", getIntent().getExtras().getString("FORM_NAME"));
        intent.putExtra("NAMA_NASABAH", getIntent().getExtras().getString("NAMA_NASABAH"));
        intent.putExtra("LISTITEMID", "NEW");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
//
//                    }
//
//
//
//                }
//
//                @Override
//                public void onFailure(Call<RetreiveJson.RetreiveCallback> call, Throwable t) {
//
//                }
//            });


//
//                final SetDataJson.SetDataRequest request = new SetDataJson().new SetDataRequest();
//                request.setRegno(regnumb);
//                request.setUserid(userdata.select().getUserid());
//                request.setTc(getIntent().getStringExtra("TC"));

               /* if(typelist.equalsIgnoreCase("unassigned"))
                {request.setStatus("5");}
                else {request.setStatus("1");}
                Call<SetDataJson.SetDataCallback> call = endPoint.updateStatus(userdata.select().getAccesstoken(), request);
                call.enqueue(new Callback<SetDataJson.SetDataCallback>() {
                    @Override
                    public void onResponse(Call<SetDataJson.SetDataCallback> call, Response<SetDataJson.SetDataCallback> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body().getStatus().equalsIgnoreCase("1")) {
                                dialogSuksesSubmit(R.string.successubmit);
                            } else {
                                dialog(R.string.errorBackend);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SetDataJson.SetDataCallback> call, Throwable t) {
                        dialog.dismiss();
                        dialog(R.string.errorBackend);
                    }
                });*/

//        }
    }

    @OnClick(R.id.surveyBackToPullButton)
    public void submitAllasigned() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        }else{
            final SetDataJson.SetDataRequest request = new SetDataJson().new SetDataRequest();
            request.setRegno(regnumb);
            request.setUserid(userdata.select().getUserid());
            request.setTc("5.0");
            request.setStatus("2");
            Call<SetDataJson.SetDataCallback> call = endPoint.updateStatus(userdata.select().getAccesstoken(), request);
            call.enqueue(new Callback<SetDataJson.SetDataCallback>() {
                @Override
                public void onResponse(Call<SetDataJson.SetDataCallback> call, Response<SetDataJson.SetDataCallback> response) {
                    dialog.dismiss();
                    if (response.isSuccessful()){
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            dialogSuksesSubmit(R.string.successbacktopool);
                        } else {
                            dialogMessage(response.body().getMessage());
                        }
                    }
                }
                @Override
                public void onFailure(Call<SetDataJson.SetDataCallback> call, Throwable t) {

                }
            });
        }
    }

    private void dialogSuksesSubmit(int successubmit) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(successubmit).icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .positiveText(R.string.buttonClose)
                .positiveColor(getResources().getColor(R.color.black))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        Intent i = new Intent(getApplicationContext(), MainActivityDashboard.class);
                        i.putExtra("FLAG_SUBMIT","1");
                        startActivity(i);
                    }
                })
                .cancelable(true)
                .build();

//        LinearLayout text = (LinearLayout) dialog.getCustomView();
//        text.("Hi!");
        dialog.show();
    }
}