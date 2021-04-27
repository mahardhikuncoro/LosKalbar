package base.screen;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import base.network.EndPoint;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.sqlite.FormData;
import base.sqlite.SQLiteConfig;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.Userdata;
import id.co.smmf.mobile.R;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import ops.screen.MainActivityDashboard;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import user.changepassword.ChangePasswordActivity;
import user.login.LoginActivity;


public class BaseDialogActivity extends AppCompatActivity {

    protected SQLiteConfigKu config;
    protected NetworkConnection networkConnection;
    protected EndPoint endPoint;
    protected TelephonyManager telephonyManager;
    protected String UserImei = "";
    protected Userdata userdata;
    protected FormData formData;
    protected Bundle bundle;
    protected Dialog dialog;

    protected void initiateApiData(){
        config = new SQLiteConfigKu(this);
        networkConnection = new NetworkConnection(this);
        userdata = new Userdata(this);
        formData = new FormData(this);
        bundle = new Bundle();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(config.getServer())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();

        OkHttpClient.Builder httpclient = new OkHttpClient.Builder();
        httpclient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("parameter", "value").build();
                return chain.proceed(request);
            }
        });

        endPoint = retrofit.create(EndPoint.class);

        telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    }

    protected void transparentStatusbar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    protected void dialog(int rString) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
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
                .build();

//        LinearLayout text = (LinearLayout) dialog.getCustomView();
//        text.("Hi!");
        dialog.show();
    }

    protected void dialogMessage(String rString) {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(rString)
                .icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .positiveText(R.string.buttonClose)
                .positiveColor(getResources().getColor(R.color.black))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .build();

        dialog.show();
    }

    protected void dialogPlaystore(int rString) {
        final SQLiteConfig config = new SQLiteConfig(this);
        new MaterialDialog.Builder(this)
                .content(rString)
                .positiveText(R.string.buttonClose)
                .negativeText(R.string.buttonUpdate)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                    public void onNegative(MaterialDialog dialog) {
                        final String appPackageName = getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(config.getGoogleMarket() + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(config.getGooglePlaystore() + appPackageName)));
                        }
                    }
                })
                .cancelable(true)
                .show();
    }

    protected void dialogGps(int rString) {
        new MaterialDialog.Builder(this)
                .content(rString)
                .positiveText(R.string.buttonClose)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .cancelable(false)
                .show();
    }

    protected void dialogPermission() {
        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.READ_PHONE_STATE
        }, 1);
    }

    protected void dialogPermissionCamera() {
        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.CAMERA
        }, 1);
    }
    protected static String encryptPassword(String password)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return sha1;
    }

    protected static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    protected void setToolbarMenu(Toolbar toolbar) {
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

    protected void setDialog(boolean show){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //View view = getLayoutInflater().inflate(R.layout.progress);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        Dialog dialog = builder.create();
        if (show){
            dialog.show();
        }
        else if(!show) {
            dialog.dismiss();
        }
    }

}