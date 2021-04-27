package ops.screen;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import base.endpoint.UploadImageJson;
import base.network.EndPoint;
import base.network.NetworkClient;
import base.screen.BaseDialogActivity;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.Userdata;
import base.widget.BaseCamera;
import id.co.smmf.mobile.R;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import ops.screen.camera.PictureActivity;
import ops.screen.fragment.FullEntry;
import ops.screen.fragment.FullEntryList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CameraActivity extends BaseDialogActivity {

    private Button takePictureButton;
    private ImageView imageView;
    private Uri file, image;
    private static File f;
    private static final String IMAGE_DIRECTORY = "/LosKalbar";
    private Bitmap bitmap;
    private int bmpWidth, bmpHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);
        initiateApiData();
//        userdata = new Userdata(this);
//        config = new SQLiteConfigKu(this);
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(config.getServer())
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(NetworkClient.getUnsafeOkHttpClient())
//                .build();
//
//        OkHttpClient.Builder httpclient = new OkHttpClient.Builder();
//        httpclient.addInterceptor(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request request = chain.request().newBuilder().addHeader("parameter", "value").build();
//                return chain.proceed(request);
//            }
//        });
//
//        endPoint = retrofit.create(EndPoint.class);
        takePictureButton = (Button) findViewById(R.id.button_image);
        imageView = (ImageView) findViewById(R.id.imageview);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            takePictureButton.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        takePicture();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureButton.setEnabled(true);
            }
        }
    }

    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100);
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }

//        checkResolution(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        f = new File(mediaStorageDir, timeStamp + ".jpg");

        return f;
    }

    private static void checkResolution(File fileImage) {
//        Log.e("1",path);
//        Log.e("2",id.toString());
//        Log.e("3",latitude);
//        Log.e("4",longitude);

//        File imgFile = new File(path);
        if (fileImage.exists()) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeFile(fileImage.getAbsolutePath(), options);
            int height = options.outHeight;
            int width = options.outWidth;

            if ((height*width) >= 1228800) {
                resizePhotonew(fileImage);
                //CameraHelper.resizePhoto(path);
            }

//            savePhoto(path,id,latitude,longitude);
            bitmap.recycle();
            System.gc();
        }
    }

    public static void resizePhotonew(File fileImage) {
//        File imgFile = new File(path);

        if(fileImage.exists()){
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 4;

            Bitmap bitmap = BitmapFactory.decodeFile(fileImage.getAbsolutePath(), options);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap = BaseCamera.rotate(bitmap, 90);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
            byte[] bitmapData = bos.toByteArray();

            try {
                FileOutputStream fos = new FileOutputStream(fileImage);
                fos.write(bitmapData);

                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            bitmap.recycle();
            System.gc();

        }
    }

    /*public void getImageOrientation(String photoPath ){
        ExifInterface ei = new ExifInterface(photoPath);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = bitmap;
        }
    }*/

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("requestCode" ," "+ requestCode);
        if (requestCode == 100) {
            Log.e("Result" ," "+ resultCode);
            if (resultCode == RESULT_OK) {
//                checkResolution(file)

//                getImageOrientation(file);

                final int defaultImageResId = R.drawable.ic_person_white_24dp;
                Picasso.with(getApplicationContext())
                        .load(file)
                        .error(defaultImageResId)
//                        .resize(640, 480)
                        .into(imageView);
//                imageView.setImageURI(file);
            }
        }
    }

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        f = getOutputMediaFile();
        file = Uri.fromFile(f);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(intent, 100);
    }


    public void uploadImage(View view) {
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

            File image = new File(f.getAbsolutePath());
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), image);

            Log.e("Masuk ", "Upload Image abab  " + image.getAbsolutePath());
            Log.e("Masuk ", "Upload Image dfdfd " + image.getName());

            MultipartBody.Part body = MultipartBody.Part.createFormData("file", image.getName(), reqFile);
            RequestBody regno = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("REGNO") == null ? "" : getIntent().getStringExtra("REGNO"));
            RequestBody userid = RequestBody.create(MediaType.parse("text/plain"), userdata.select().getUserid());
            RequestBody tc = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("TC"));
            RequestBody uploadtype = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("UPLOAD_TYPE"));
            RequestBody docid = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("DOC_ID") == null ? "" : getIntent().getStringExtra("DOC_ID"));
            RequestBody doccode = RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("DOC_CODE") == null ? "" : getIntent().getStringExtra("DOC_CODE"));
            RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), "0");
            RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), "0");
            RequestBody address = RequestBody.create(MediaType.parse("text/plain"), "mana hayo");

            HashMap<String, RequestBody> map = new HashMap<>();
            map.put("regno", regno);
            map.put("userid", userid);
            map.put("tc", tc);
            map.put("uploadtype", uploadtype);
            map.put("docid", docid);
            map.put("doccode", doccode);
            map.put("latitude", latitude);
            map.put("longitude", longitude);
            map.put("address", address);
            Log.e("Ahh 1", "HALO LINK" + image.getName());
            Log.e("Ahh 2", "HALO LINK" + getIntent().getStringExtra("REGNO"));
            Log.e("Ahh 3", "HALO LINK" + userdata.select().getUserid());
            Log.e("Ahh 4", "HALO LINK" + getIntent().getStringExtra("TC"));
            Log.e("Ahh 5", "HALO LINK" + getIntent().getStringExtra("UPLOAD_TYPE"));
            Log.e("Ahh 6", "HALO LINK" + doccode);
            Log.e("Ahh 7", "HALO LINK" + address);

            Call<UploadImageJson.Callback> call = endPoint.uploadFile(userdata.select().getAccesstoken(), map, body);
            call.enqueue(new Callback<UploadImageJson.Callback>() {
                @Override
                public void onResponse(Call<UploadImageJson.Callback> call, Response<UploadImageJson.Callback> response) {
                    try {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            String link = response.body().getPHOTO_PROFILE();
                            Log.e("Ahh ", "HALO LINK" + link);
                            if (getIntent().getExtras().getString("UPLOAD_TYPE").equalsIgnoreCase("profile")) {
                                startActivity(new Intent(getApplicationContext(), MainActivityDashboard.class));
                                userdata.updatelinkProfile(link, userdata.select().getUserid());
                            } else {
                                if (response.body().getStatus().equalsIgnoreCase("1")) {
                                    Intent intent = new Intent(CameraActivity.this, FullEntryList.class);
                                    intent.putExtra("SECTION_NAME", getIntent().getStringExtra("SECTION_NAME"));
                                    intent.putExtra("REGNO", getIntent().getStringExtra("REGNO"));
                                    intent.putExtra("TC", getIntent().getStringExtra("TC"));
                                    intent.putExtra("TYPE", getIntent().getStringExtra("TYPE"));
                                    intent.putExtra("TABLE_NAME", getIntent().getStringExtra("TABLE_NAME"));
                                    intent.putExtra("FORM_NAME", getIntent().getStringExtra("FORM_NAME"));
                                    intent.putExtra("IMAGEID", getIntent().getStringExtra("IMAGEID"));
                                    startActivity(intent);
                                } else {
                                    dialogMessage(response.body().getMessage());
                                }
                            }
                        } else {
                            Log.e("Ahh ", "HALO LINKCOKKK ");
                            dialog(R.string.errorBackend);
                        }
                    } catch (Exception e) {
                        Log.e("Ahh ", "HALOas  LINKCOKKK ");
                        dialog(R.string.errorBackend);
                    }

                }

                @Override
                public void onFailure(Call<UploadImageJson.Callback> call, Throwable t) {
                    Log.e("Ahh ", "SAtt HALO LINKCOKKK ");
                    dialog(R.string.errorConnection);
//                uploadFailed();

                }
            });
        }
    }

}
