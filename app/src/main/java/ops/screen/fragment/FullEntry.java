package ops.screen.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import base.network.FormCascadingJson;
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
import ops.screen.MainActivityDashboard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import user.changepassword.ChangePasswordActivity;
import user.login.LoginActivity;

public class FullEntry extends BaseDialogActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.btnback_toolbar) ImageView btnback_toolbar;
    @BindView(R.id.recycleMenuEntry) RecyclerView recyclerView;
    @BindView(R.id.txtFullname) TextView txtFullname;
    @BindView(R.id.txtIdUser) TextView txtIdUser;
    @BindView(R.id.surveyBackToPullButton) Button surveyBackToPullButton;
    @BindView(R.id.surveyFormSubmitButton) Button surveyFormSubmitButton;

    private ArrayList<TaskListDetailModel> taskListList;
    private FullEntryAdapter fullEntryAdapter;
    private Dialog dialog;
    private static String regnumb,tc,typelist;

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
        if(!tc.equalsIgnoreCase("5.0"))
            surveyBackToPullButton.setVisibility(View.GONE);
        setRegno();


    }
    private void setRegno(){
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
            regnumb = getIntent().getExtras().getString("REGNO");
            if (regnumb.equalsIgnoreCase("")) {
                final SetDataJson.SetDataRequest request = new SetDataJson().new SetDataRequest();
                request.setRegno("mobile");
                request.setUserid(userdata.select().getUserid());
                request.setTc(getIntent().getExtras().getString("TC"));
                request.setStatus(getIntent().getExtras().getString("STATUS"));

                Call<SetDataJson.SetDataCallback> call = endPoint.updateStatus(userdata.select().getAccesstoken(), request);
                call.enqueue(new Callback<SetDataJson.SetDataCallback>() {
                    @Override
                    public void onResponse(Call<SetDataJson.SetDataCallback> call, Response<SetDataJson.SetDataCallback> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equalsIgnoreCase("1")) {
                                regnumb = response.body().getRegno();
                                retreiveData(regnumb);
                            } else {
                                dialog.dismiss();
                                dialogMessage(response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SetDataJson.SetDataCallback> call, Throwable t) {

                    }
                });
            } else {
                retreiveData(regnumb);
            }
        }
    }

    public void retreiveData(final String regno){
        final RetreiveJson.RetreiveRequest request = new RetreiveJson().new RetreiveRequest();
        request.setRegno(regno);
        request.setUserid(userdata.select().getUserid());
        request.setTc(getIntent().getExtras().getString("TC"));
        request.setType(getIntent().getExtras().getString("TYPE"));
        request.setDataLevel("section");

        Call<RetreiveJson.RetreiveCallback> callBack = endPoint.getDataRetreive(userdata.select().getAccesstoken(),request);
        callBack.enqueue(new Callback<RetreiveJson.RetreiveCallback>() {
            @Override
            public void onResponse(Call<RetreiveJson.RetreiveCallback> call, Response<RetreiveJson.RetreiveCallback> response) {
               try{
                   if(response.isSuccessful()) {
                       if(response.body().getAllowBackPool().equalsIgnoreCase("0")){
                           surveyBackToPullButton.setVisibility(View.GONE);
                       }
                       if (response.body().getData().size() <= 0) {
                           dialog.dismiss();
                           dialog(R.string.dataempty);
                       } else if(response.body().getData().size()> 0 ){
                           for (RetreiveJson.RetreiveCallback.Data datamodel : response.body().getData()) {
//                            Log.e("getDataRetreive","KOKOKOK " + datamodel.getSectionName());
                               Log.e("SECTION TYPE","KOKOKOK " + datamodel.getSectionType());
                               TaskListDetailModel model = new TaskListDetailModel();
                               model.setFormName(datamodel.getFormName());
                               model.setSectionName(datamodel.getSectionName());
                               model.setTableName(datamodel.getTableName());
                               model.setSectionType(datamodel.getSectionType());
                               taskListList.add(model);
                           }
                           fillListMenu();
                       }
                       setAdapter(regno);
                   }
               }catch (Exception e){
                   dialog(R.string.errorBackend);
               }
            }

            @Override
            public void onFailure(Call<RetreiveJson.RetreiveCallback> call, Throwable t) {

            }
        });

    }


    protected void fillListMenu() {

        Log.e("COUNT "," FORM : " + formData.countByNameForm("FORM_SURVEY_REFERENCE"));
        if(formData.countByNameForm("FORM_SURVEY_REFERENCE") <= 0) {
            if (!networkConnection.isNetworkConnected()) {
                dialog.dismiss();
                dialog(R.string.errorNoInternetConnection);
            } else {
                final FormJson.RequestForm requestForm = new FormJson().new RequestForm();
                requestForm.setType("reference");
                String token = userdata.select().getAccesstoken();
                Call<FormJson.CallbackForm> callForm = endPoint.getDataMaster(token, requestForm);
                callForm.enqueue(new Callback<FormJson.CallbackForm>() {
                    @Override
                    public void onResponse(Call<FormJson.CallbackForm> call, final Response<FormJson.CallbackForm> response) {
                        if (response.isSuccessful()) {
                            saveReferences(response);
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            dialogMessage(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<FormJson.CallbackForm> call, Throwable t) {
                        dialog.dismiss();
                    }
                });

            }
        }else{
            dialog.dismiss();
        }
    }
    public void saveReferences(Response<FormJson.CallbackForm> response){
        String successResponsedata="";
        if(response.body().getStatus().equalsIgnoreCase("1")) {
            for (FormJson.CallbackForm.Data model : response.body().getData()) {
                try{
                    Gson gsondata = new Gson();
                    successResponsedata = gsondata.toJson(model);
                    Log.e("Data Reference TABLE ", " : " + model.getTableName());
                    Log.e("Data Reference ", " : " + successResponsedata);
                    formData.save("1", "FORM_SURVEY_REFERENCE", model.getTableName(), successResponsedata);
                }catch (Exception e){}
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }



    private void setToolbar(){

        String id = (getIntent().getExtras().getString("REGNO") == null ? "" : getIntent().getExtras().getString("REGNO"));
        String fullname = (getIntent().getExtras().getString("NAMA_NASABAH") == null ? "": getIntent().getExtras().getString("NAMA_NASABAH"));
        txtIdUser.setText(id);
        txtFullname.setText(fullname);
        txtFullname.setAllCaps(true);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    // action with ID action_refresh was selected
                    case R.id.action_application:
                        final View addView = getLayoutInflater().inflate(R.layout.about_bexi, null);
                        new AlertDialog.Builder(FullEntry.this).setTitle("LOS KALBAR").setView(addView)
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
        startActivity(new Intent(getApplicationContext(), MainActivityDashboard.class));
    }

    private  void setAdapter(String regno){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fullEntryAdapter = new FullEntryAdapter(this,taskListList, regno ,getIntent().getExtras().getString("TC"),getIntent().getExtras().getString("TYPE"), getIntent().getExtras().getString("STATUS"),getIntent().getExtras().getString("NEW_DATA"), getIntent().getExtras().getString("NAMA_NASABAH"));
        recyclerView.setAdapter(fullEntryAdapter);


    }

    @OnClick(R.id.surveyFormSubmitButton)
    public void dialogSubmit(){
        new MaterialDialog.Builder(this)
                .content("Submit Data ?")
                .positiveText(R.string.buttonSubmit)
                .negativeText(R.string.buttonClose)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        submitData();
                    }
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .show();
    }


    public void submitData(){
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

                final SetDataJson.SetDataRequest request = new SetDataJson().new SetDataRequest();
                request.setRegno(regnumb);
                request.setUserid(userdata.select().getUserid());
                request.setTc(getIntent().getStringExtra("TC"));
                Log.e("TYPELIST", " : " + typelist);
                if(typelist != null && typelist.equalsIgnoreCase("unassigned"))
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
                                dialogMessage(response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SetDataJson.SetDataCallback> call, Throwable t) {
                        dialog.dismiss();
                        dialog(R.string.errorBackend);
                    }
                });

            }
    }

    @OnClick(R.id.surveyBackToPullButton)
    public void dialogbacktoPool() {
        new MaterialDialog.Builder(this)
                .content("Send Data to pool ?")
                .positiveText(R.string.buttonYes)
                .negativeText(R.string.buttonNo)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        submitAllasigned();
                    }
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .show();
    }

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