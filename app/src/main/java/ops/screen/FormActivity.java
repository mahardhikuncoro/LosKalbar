package ops.screen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.DnsResolver;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import base.network.FormJson;
import base.network.RetreiveJson;
import base.network.SetDataJson;
import base.screen.BaseDialogActivity;
import base.sqlite.DataModel;
import base.sqlite.FieldModel;
import base.sqlite.ParameterModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.smmf.mobile.R;
import ops.DinamicLayout;
import base.sqlite.TaskListDetailModel;
import ops.screen.fragment.FullEntry;
import ops.screen.fragment.FullEntryList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import user.changepassword.ChangePasswordActivity;
import user.login.LoginActivity;

public class FormActivity extends BaseDialogActivity {

    private DinamicLayout dynamicLayout;

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.linearLayout)
    LinearLayout topLayout;
    @BindView(R.id.surveyFormSaveButton)
    Button surveyFormSaveButton;
    @BindView(R.id.surveyFormToolbar)
    Toolbar toolbar;
    @BindView(R.id.titletxtform)
    TextView titletxtform;

    @BindView(R.id.surveyFormDelete)
    Button surveyFormDelete;

    ArrayList<TaskListDetailModel> menulist;
    public ArrayList<FieldModel> fieldArrayList;
    public ArrayList<FieldModel> fieldSingleArrayList;
    public ArrayList<DataModel> dataModelArrayList;
    public DataModel dataModel;
    private Dialog dialog;
    private String datalevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        transparentStatusbar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_activity);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        menulist = new ArrayList<TaskListDetailModel>();
        fieldArrayList = new ArrayList<FieldModel>();
        fieldSingleArrayList = new ArrayList<FieldModel>();
        dataModelArrayList = new ArrayList<DataModel>();
        dataModel = new DataModel();
        initiateApiData();
//        initViews();
        setToolbar();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if(getIntent().getExtras() != null)
            retreiveData();
        else{
            callForm();
        }



    }
    private void initViews() {
        setSupportActionBar(toolbar);
        if(getIntent().getExtras() != null)
            getSupportActionBar().setTitle(getIntent().getStringExtra("SECTION_NAME"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public Dialog showProgressDialog(Context context, String message){
        Dialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public void showprogress(Context context,boolean oke){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
//        if(oke){
//        if(dialog == null){
            dialog.show();
//        }else{
//            dialog.dismiss();
//        }

    }
    public void dismissprogress(Context context){
        dialog.dismiss();
    }

//    public void showProgressDialog(Activity activity){
//
//        if(progressDialog != null && progressDialog.isShowing()){
//            progressDialog.dismiss();
//            progressDialog = null;
//        }
//
//        if(progressDialog == null ) {
//            progressDialog = new ProgressDialog(activity);
//        }
//        progressDialog.setMessage("Loading...");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setCancelable(false);
//
//        if(!activity.isFinishing())
//            progressDialog.show();
//    }



    public void retreiveData(){
        final RetreiveJson.RetreiveRequest request = new RetreiveJson().new RetreiveRequest();
        request.setRegno(getIntent().getExtras().getString("REGNO"));
        request.setUserid(userdata.select().getUserid());
        request.setTc(getIntent().getExtras().getString("TC"));
        request.setType(getIntent().getExtras().getString("TYPE"));
        if(getIntent().getStringExtra("IMAGEID")!= null && getIntent().getStringExtra("IMAGEID")!="") {
            request.setDataLevel("listfield");
            request.setListItemId(getIntent().getExtras().getString("IMAGEID"));
            datalevel = "listfield";
        }
        else if(getIntent().getStringExtra("LISTITEMID") != null){
            request.setDataLevel("listfield");
            request.setListItemId("new");
            datalevel = "listfield";
        }
        else {
            request.setListItemId(getIntent().getExtras().getString("IMAGEID"));
            request.setDataLevel("field");
            datalevel = "field";
        }
        request.setFormname(getIntent().getExtras().getString("FORM_NAME"));

        Call<RetreiveJson.RetreiveCallback> callBack = endPoint.getDataRetreive(userdata.select().getAccesstoken(),request);
        callBack.enqueue(new Callback<RetreiveJson.RetreiveCallback>() {
            @Override
            public void onResponse(Call<RetreiveJson.RetreiveCallback> call, Response<RetreiveJson.RetreiveCallback> response) {
                try {
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        if(response.body().getStatus().equalsIgnoreCase("1")) {
                            if (response.body().getAllowDeleteItem().equalsIgnoreCase("1")) {
                                surveyFormDelete.setVisibility(View.VISIBLE);
                            }

                            if (response.body().getData().size() <= 0) {
                                dialogMessage(response.body().getMessage());
                            } else {
                                for (RetreiveJson.RetreiveCallback.Data datamodel : response.body().getData()) {
                                    dataModel.setFormName(datamodel.getFormName());
                                    dataModel.setTableName(datamodel.getTableName());
                                    for (RetreiveJson.RetreiveCallback.Data.Field fieldmodel : datamodel.getField()) {
                                        FieldModel model = new FieldModel();
                                        model.setFieldName(fieldmodel.getFieldName());
                                        model.setFieldId(fieldmodel.getFieldId());
                                        model.setFieldValue(fieldmodel.getFieldValue());
                                        fieldArrayList.add(model);
                                    }
                                }
                            }
                        }else{
                            dialogMessage(response.body().getMessage());
                        }
                        callForm();
                    }else{
                        dialog.dismiss();
                        dialogMessage(response.body().getMessage());
                    }
                }catch (Exception e){
                    dialog.dismiss();
                    dialog(R.string.errorBackend);
                }
            }

            @Override
            public void onFailure(Call<RetreiveJson.RetreiveCallback> call, Throwable t) {

            }
        });

    }

    private void callForm() {

        String section = formData.select(getIntent().getStringExtra("FORM_NAME"));
        Log.e("FORM DATA ", " SECTION : " + section);
        if(getIntent().getStringExtra("FORM_NAME") != null) {
            if (getIntent().getStringExtra("FORM_NAME").equalsIgnoreCase("SDE05")) {
                surveyFormSaveButton.setVisibility(View.GONE);
            }
        }
        if(getIntent().getExtras()!= null) {
            Log.e("LAHHHHAHHHA ", " : " +  getIntent().getStringExtra("FORM_NAME") );
            dynamicLayout = new DinamicLayout(this, fieldArrayList,datalevel,
                    getIntent().getStringExtra("SECTION_NAME"),
                    getIntent().getStringExtra("REGNO"),
                    getIntent().getStringExtra("TC"),
                    getIntent().getStringExtra("TYPE"),
                    getIntent().getStringExtra("TABLE_NAME"),
                    getIntent().getStringExtra("FORM_NAME"),
                    getIntent().getStringExtra("NEW_DATA"),
                    getIntent().getStringExtra("IMAGEID"),
                    getIntent().getStringExtra("NAMA_NASABAH")
            );
        }else {
            dynamicLayout = new DinamicLayout(this, fieldArrayList,datalevel,
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "");
        }

        dynamicLayout.setScrollView(scrollView);
        dynamicLayout.setTopLayout(topLayout);
        dynamicLayout.countitem = dynamicLayout.countSpinner(section);
        dynamicLayout.create(section);
        dialog.dismiss();


//        final FormJson.RequestForm requestForm = new FormJson().new RequestForm();
//        requestForm.setType("form");
//        String token= userdata.select().getAccesstoken();
//
//        Call<FormJson.CallbackForm> call = endPoint.getDataMaster(token,requestForm);
//        call.enqueue(new Callback<FormJson.CallbackForm>() {
//            @Override
//            public void onResponse(Call<FormJson.CallbackForm> call, Response<FormJson.CallbackForm> response) {
//                if(response.isSuccessful()){


//                    Log.e("JSON  "," ADALAH " + response.body().toString());
        ;

                   /* try {
                        JSONObject jsonObj = new JSONObject(response.body().getData().toString());

                        // Getting JSON Array node
                        JSONArray contacts = jsonObj.getJSONArray("contacts");

                        // looping through All Contacts
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);

                            String id = c.getString("id");
                            String name = c.getString("name");
                            String email = c.getString("email");
                            String address = c.getString("address");
                            String gender = c.getString("gender");

                            // Phone node is JSON Object
                            JSONObject phone = c.getJSONObject("phone");
                            String mobile = phone.getString("mobile");
                            String home = phone.getString("home");
                            String office = phone.getString("office");

                            // tmp hash map for single contact
                            HashMap<String, String> contact = new HashMap<>();

                            // adding each child node to HashMap key => value
                            contact.put("id", id);
                            contact.put("name", name);
                            contact.put("email", email);
                            contact.put("mobile", mobile);

                            // adding contact to contact list
                            contactList.add(contact);
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });

                    }*/


//                    for (FormJson.CallbackForm.Data model : response.body().getData()) {
//                        for(FormJson.CallbackForm.Data.Field field : model.getField()){
//                            TaskListDetailModel fieldModel = new TaskListDetailModel();
//                            fieldModel.setNamaNasabah(field.getFieldName());
//                            menulist.add(fieldModel);
//                        }
//
//                    }
//                    setAdapter();



/*                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson("test"));
//                        jsonObject =  new JSONArray(JSONConverter.convertStandardJSONString(jsonObject);
                        dynamicLayout.createItem(jsonObject);
                        Log.e("jsonObject "," ADALAH " + jsonObject);
//                        msg = jsonObject.getString("msg");
//                        status = jsonObject.getBoolean("status");
//
//                        msg = jsonObject.getString("msg");
//                        status = jsonObject.getBoolean("status");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

//                }
//            }
//
//            @Override
//            public void onFailure(Call<FormJson.CallbackForm> call, Throwable t) {
//
//            }
//        });
    }

//    @Override
//    public void onBackPressed(){
//        onBackPressed();
//    }

//    private  void setAdapter(){
//        fullEntryAdapter = new FullEntryAdapter(this,menulist);
//        recyclerView.setAdapter(fullEntryAdapter);
//    }

    private void setToolbar(){
        titletxtform.setText(getIntent().getStringExtra("SECTION_NAME"));
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_menu));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    // action with ID action_refresh was selected
                    case R.id.action_application:
                        final View addView = getLayoutInflater().inflate(R.layout.about_bexi, null);
                        new AlertDialog.Builder(getApplicationContext()).setTitle("LOS KALBAR").setView(addView)
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
//                    case R.id.btnback_toolbar:
//                        Intent intentform = new Intent(getApplicationContext(), FormActivity.class);
//                        startActivity(intentform);
//                        finish();
//                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
    @OnClick(R.id.btnback_toolbar)public void backToform(){
        if(getIntent().getStringExtra("IMAGEID") == null && getIntent().getStringExtra("LISTITEMID") == null) {
            Intent intentform = new Intent(getApplicationContext(), FullEntry.class);
            intentform.putExtra("SECTION_NAME", getIntent().getStringExtra("SECTION_NAME"));
            intentform.putExtra("TYPE", getIntent().getStringExtra("TYPE"));
            intentform.putExtra("REGNO", getIntent().getStringExtra("REGNO"));
            intentform.putExtra("STATUS", getIntent().getStringExtra("STATUS"));
            intentform.putExtra("TC", getIntent().getStringExtra("TC"));
            intentform.putExtra("SECTION_TYPE", getIntent().getStringExtra("SECTION_TYPE"));
            intentform.putExtra("TABLE_NAME", getIntent().getStringExtra("TABLE_NAME"));
            intentform.putExtra("NAMA_NASABAH", getIntent().getStringExtra("NAMA_NASABAH"));
            startActivity(intentform);
            finish();
        } else if(getIntent().getStringExtra("LISTITEMID") != null){
            Intent intentform = new Intent(getApplicationContext(), FullEntryList.class);
            intentform.putExtra("SECTION_NAME", getIntent().getStringExtra("SECTION_NAME"));
            intentform.putExtra("TYPE", getIntent().getStringExtra("TYPE"));
            intentform.putExtra("REGNO", getIntent().getStringExtra("REGNO"));
            intentform.putExtra("STATUS", getIntent().getStringExtra("STATUS"));
            intentform.putExtra("TC", getIntent().getStringExtra("TC"));
            intentform.putExtra("FORM_NAME", getIntent().getStringExtra("FORM_NAME"));
            intentform.putExtra("TABLE_NAME", getIntent().getStringExtra("TABLE_NAME"));
            intentform.putExtra("NAMA_NASABAH", getIntent().getStringExtra("NAMA_NASABAH"));
            startActivity(intentform);
            finish();
        }else{
            Intent intentform = new Intent(getApplicationContext(), FullEntryList.class);
            intentform.putExtra("SECTION_NAME", getIntent().getStringExtra("SECTION_NAME"));
            intentform.putExtra("TYPE", getIntent().getStringExtra("TYPE"));
            intentform.putExtra("REGNO", getIntent().getStringExtra("REGNO"));
            intentform.putExtra("STATUS", getIntent().getStringExtra("STATUS"));
            intentform.putExtra("TC", getIntent().getStringExtra("TC"));
            intentform.putExtra("FORM_NAME", getIntent().getStringExtra("FORM_NAME"));
            intentform.putExtra("IMAGEID", getIntent().getStringExtra("IMAGEID"));
            intentform.putExtra("TABLE_NAME", getIntent().getStringExtra("TABLE_NAME"));
            intentform.putExtra("NAMA_NASABAH", getIntent().getStringExtra("NAMA_NASABAH"));
            startActivity(intentform);
            finish();
        }
    }

    @OnClick(R.id.surveyFormSaveButton)
    public void saveData() {
        String section = formData.select(getIntent().getStringExtra("FORM_NAME"));
        dynamicLayout.getTextInput(section,
                getIntent().getExtras().getString("REGNO").equalsIgnoreCase("") ? "1234567" : getIntent().getExtras().getString("REGNO"),
                getIntent().getExtras().getString("TC"),
                getIntent().getExtras().getString("FORM_NAME"),
                getIntent().getExtras().getString("TABLE_NAME"), true);
//        dialog = new MaterialDialog.Builder(this)
//                .content(R.string.loading)
//                .progress(true, 0)
//                .cancelable(false)
//                .show();
//        if (!networkConnection.isNetworkConnected()) {
//            dialog.dismiss();
//            dialog(R.string.errorNoInternetConnection);
//        } else {
//            final SetDataJson.SetDataRequest request = new SetDataJson().new SetDataRequest();
//            if(!getIntent().getExtras().getString("REGNO").equalsIgnoreCase(""))
//                request.setRegno(getIntent().getExtras().getString("REGNO"));
//            else
//                request.setRegno("1234567");
//
//            request.setUserid(userdata.select().getUserid());
//            request.setTc(getIntent().getExtras().getString("TC"));
//            request.setFormName(getIntent().getExtras().getString("FORM_NAME"));
//            request.setTableName(getIntent().getExtras().getString("TABLE_NAME"));
//            request.setField(dynamicLayout.getFieldArrayList());
//
//            Call<SetDataJson.SetDataCallback> callBack = endPoint.setData(userdata.select().getAccesstoken(), request);
//            callBack.enqueue(new Callback<SetDataJson.SetDataCallback>() {
//                @Override
//                public void onResponse(Call<SetDataJson.SetDataCallback> call, Response<SetDataJson.SetDataCallback> response) {
//                    if (response.isSuccessful()) {
//                        dialog.dismiss();
//                        if (response.body().getStatus().equalsIgnoreCase("1")) {
//                            dialogSukses(R.string.successubmit);
//                        } else {
//                            dialog(R.string.failSubmit);
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<SetDataJson.SetDataCallback> call, Throwable t) {
//                    dialog.dismiss();
//                    dialog(R.string.errorBackend);
//                }
//            });
//
//        }
    }

    @OnClick(R.id.surveyFormDelete)
    public void deleteData() {
        String section = formData.select(getIntent().getStringExtra("FORM_NAME"));
        dynamicLayout.getTextInput(section,
                getIntent().getExtras().getString("REGNO").equalsIgnoreCase("") ? "1234567" : getIntent().getExtras().getString("REGNO"),
                getIntent().getExtras().getString("TC"),
                getIntent().getExtras().getString("FORM_NAME"),
                getIntent().getExtras().getString("TABLE_NAME"), false);
    }


        protected void dialogSukses(int rString) {
        new MaterialDialog.Builder(this)
                .content(rString)
                .positiveText(R.string.buttonClose)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(), FullEntry.class);
                        intent.putExtra("REGNO","");
                        intent.putExtra("TC","5.0");
                        intent.putExtra("TYPE","IDJ");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .cancelable(true)
                .show();
    }



}
