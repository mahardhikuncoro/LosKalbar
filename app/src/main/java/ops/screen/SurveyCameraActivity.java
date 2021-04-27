package ops.screen;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import base.data.UserData;
import base.data.UserMetaData;
import base.sqlite.ActiveData;
import base.sqlite.PartnerApplyImageData;
import base.sqlite.SurveyImage;
import base.widget.BaseCamera;
import id.co.smmf.mobile.R;


public class SurveyCameraActivity extends AppCompatActivity {

    public static final String EXTRA_PATH   = "CAMERA_EXTRA";
    public static final String PHOTO_ID     = "PHOTO_ID";
    public static final String CATEGORY     = "CATEGORY";

    public static final String RECAPTURE_FLAG  = "RECAPTURE_FLAG";

    private UserData userData = new UserData(this);
    private ActiveData activeData = new ActiveData(getApplicationContext());
//    private SurveyImage applyImageData = new SurveyImage(getApplicationContext());
    private PartnerApplyImageData partnerApplyImageData = new PartnerApplyImageData(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_camera);

//        checkResolution(getIntent().getExtras().getString(EXTRA_PATH),getIntent().getExtras().getLong("APPLICATIONONLINEID"),
//               "0",
//                "0");
    }

    private void checkResolution(String path, Long id, String latitude, String longitude) {
//        Log.e("1",path);
//        Log.e("2",id.toString());
//        Log.e("3",latitude);
//        Log.e("4",longitude);

        File imgFile = new File(path);
        if (imgFile.exists()) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);
            int height = options.outHeight;
            int width = options.outWidth;

            if ((height*width) >= 1228800) {
                resizePhotonew(path);
                //CameraHelper.resizePhoto(path);
            }

            savePhoto(path,id,latitude,longitude);
            bitmap.recycle();
            System.gc();
        }
    }

    private void savePhoto(String photoPath, Long id, String latitude, String longitude) {
        File file = new File(photoPath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, options);
        UserMetaData activeUser = userData.getActiveUser();

        String category = getIntent().getExtras().getString(CATEGORY);

        SurveyImage model;
        if (getIntent().getExtras().getInt(PHOTO_ID) != 0) {
            model = partnerApplyImageData.select(getIntent().getExtras().getInt(PHOTO_ID));
            model.setPath(photoPath);
            model.setHeight(options.outHeight);
            model.setWidth(options.outWidth);
            model.setSize((int) file.length() / 1024);
            model.setStatus(id.intValue());
            partnerApplyImageData.save(model);
            Log.e("PHOTO SAVE "," SUKSES");

        } else {
            Log.e("PHOTO ID NULLL"," HAHH");
            partnerApplyImageData.save(photoPath, options.outHeight, options.outWidth, (int) file.length() / 1024, category,0,id.intValue(),latitude,longitude);
        }
//
//        if (activeData.getInteger(ActiveKey.PAGE) == 1)
//            startActivity(new Intent(this, ApplyFormImageActivity.class));
//
//        if (activeData.getInteger(ActiveKey.PAGE) == 2)
//            startActivity(new Intent(this, MainActivityDashboard.class));

        finish();
    }

    public void resizePhotonew(String path) {
        File imgFile = new File(path);

        if(imgFile.exists()){
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = 4;

            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap = BaseCamera.rotate(bitmap, 90);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
            byte[] bitmapData = bos.toByteArray();

            try {
                FileOutputStream fos = new FileOutputStream(imgFile);
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

    @Override
    public void onBackPressed() {
        finish();
    }
}