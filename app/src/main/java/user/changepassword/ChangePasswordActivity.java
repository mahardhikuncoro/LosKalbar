package user.changepassword;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import base.network.ChangePassJson;
import base.screen.BaseDialogActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.smmf.mobile.R;
import ops.screen.MainActivityDashboard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import user.login.LoginActivity;

public class ChangePasswordActivity extends BaseDialogActivity {

    @BindView(R.id.etOldpass) EditText etOldpass;
    @BindView(R.id.etNewpass) EditText etNewpass;
    @BindView(R.id.etNewpassconf) EditText etNewpassconf;
    @BindView(R.id.input_layout_password) TextInputLayout layoutInputPassword;
    @BindView(R.id.input_layout_password_confirm) TextInputLayout layoutInputPasswordconfirm;
    @BindView(R.id.txtFullname) TextView txtFullname;
    @BindView(R.id.txtIdUser) TextView txtIdUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        transparentStatusbar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        ButterKnife.bind(this);
        initiateApiData();
        setToolbar();
    }

    @OnClick(R.id.buttonSavePass)
    public void saveNewPassword(){
        if(passwordIdentic(etNewpass.getText().toString(),etNewpassconf.getText().toString())){
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

                final ChangePassJson.ChangePassRequest request = new ChangePassJson().new ChangePassRequest();
                request.setUserid(userdata.select().getUserid());
                request.setPassword(etOldpass.getText().toString());
                request.setNewpwd(etNewpass.getText().toString());
                request.setNewpwd2(etNewpassconf.getText().toString());

                Call<ChangePassJson.ChangePassCallback> callChangePass = endPoint.changePassword(userdata.select().getAccesstoken(), request);
                callChangePass.enqueue(new Callback<ChangePassJson.ChangePassCallback>() {
                    @Override
                    public void onResponse(Call<ChangePassJson.ChangePassCallback> call, Response<ChangePassJson.ChangePassCallback> response) {
                        try {
                            if(response.isSuccessful()){
                                dialog.dismiss();
                                if(response.body().getStatus().equalsIgnoreCase("1")){
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.successupdatepassword), Toast.LENGTH_LONG).show();
                                    userdata.deleteAll();
                                    formData.deleteAll();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                }else{
                                    dialogMessage(response.body().getMessage());
                                }
                            }
                        }catch (Exception e){
                            dialog.dismiss();
                            dialog(R.string.errorBackend);
                        }
                    }

                    @Override
                    public void onFailure(Call<ChangePassJson.ChangePassCallback> call, Throwable t) {
                        dialog.dismiss();
                        dialog(R.string.errorBackend);
                    }
                });
            }
        }else{
            layoutInputPasswordconfirm.setError(getResources().getString(R.string.passnotmatch));
        }
    }

    public boolean passwordIdentic(String newPass, String confirmPass){
        return newPass.equalsIgnoreCase(confirmPass);
    }

    private void setToolbar() {

        String id = userdata.select().getBranchid();
        String fullname = userdata.select().getFullname();
        txtIdUser.setText(id);
        txtFullname.setText(fullname);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivityDashboard.class));
    }

    @OnClick(R.id.btnback_toolbar)
    public void backButton(){
        onBackPressed();
    }
}
