package ops.screen;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import base.NumberSeparatorTextWatcher;
import base.network.EndPoint;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.network.Slider;
import base.sqlite.ParameterPinjaman;
import base.sqlite.ParameterPinjamanModel;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.SliderSQL;
import base.utils.NumberSeparator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.smmf.mobile.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;




/**
 * Created by christian on 4/5/18.
 */

public class SubmitDana extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    public String dana;
    public Double temp_dana;

    @BindView(R.id.slider)
    SliderLayout sliderLayout;

    @BindView(R.id.etDana)
    EditText etDana;

//    @BindView(R.id.viewPager)
//    ViewPager viewPager;

    @BindView(R.id.layoutDots)
    LinearLayout dotsLayout;

    private SQLiteConfigKu sqLiteConfigKu;
    private NetworkConnection networkConnection;
    private EndPoint endPoint;

    private MaterialDialog dialog;

    private ArrayList<String> sliderList;
    private ArrayList<Slider> sliderDataList;

    private Integer layout;

    private Integer minDana;
    private String minDana2;

    private TextView[] dots;

    private Picasso picasso;

    private HashMap<String, String> HashMapForURL;

//    SQL Parameter
    private ParameterPinjaman parameterPinjaman;
    private ParameterPinjamanModel parameterPinjamanModel;
    private SliderSQL slidersql;
//    private List<ParameterPinjamanModel>list;

    private boolean doubleBackToExitPressedOnce = false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_dana_activity);
        ButterKnife.bind(this);

//        AddImagesUrlOnline();


        Picasso.Builder picassoBuilder = new Picasso.Builder(this);
        picassoBuilder.downloader(new OkHttp3Downloader(
                NetworkClient.getUnsafeOkHttpClient()
        ));
        picasso = picassoBuilder.build();

        parameterPinjamanModel = new ParameterPinjamanModel();
        sqLiteConfigKu = new SQLiteConfigKu(this);
        networkConnection = new NetworkConnection(this);
        parameterPinjaman = new ParameterPinjaman(this);
        slidersql = new SliderSQL(this);

        Integer slidesize = slidersql.count();
        if(slidesize > 0){
            sliderLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }

        for(int i = 1 ; i <= slidesize; i++){

            Slider temp = slidersql.select(i);

            TextSliderView textSliderView = new TextSliderView(SubmitDana.this);

            textSliderView
                    .description(temp.getName())
                    .image(temp.getImage())
                    .error(R.drawable.defaultslide)
                    .empty(R.drawable.defaultslide)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(SubmitDana.this);

            textSliderView.bundle(new Bundle());

            textSliderView.getBundle()
                    .putString("extra",temp.getLink());

            sliderLayout.addSlider(textSliderView);

        }

//        for(String name : HashMapForURL.keySet()){
//
//            TextSliderView textSliderView = new TextSliderView(SubmitDana.this);
//
//            textSliderView
//                    .description(name)
//                    .image(HashMapForURL.get(name))
//                    .setScaleType(BaseSliderView.ScaleType.Fit)
//                    .setOnSliderClickListener(SubmitDana.this);
//
//            textSliderView.bundle(new Bundle());
//
//            textSliderView.getBundle()
//                    .putString("extra",name);
//
//            sliderLayout.addSlider(textSliderView);
//        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);

        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        sliderLayout.setCustomAnimation(new DescriptionAnimation());

        sliderLayout.setDuration(6000);

        sliderLayout.addOnPageChangeListener(SubmitDana.this);



//        list = null;
//        list = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sqLiteConfigKu.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();

        endPoint = retrofit.create(EndPoint.class);




//        callApiSlider();

//        slider(viewPager);

//        getSliderAdapter();
        dana();

    }

//    private void getSliderAdapter(){
//        AndroidImageAdapter adapterView = new AndroidImageAdapter(this);
//        layout = adapterView.getCount();
//
//
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
//
//        addBottomDotsForSlider(0);
//        changeStatusBarColor();
//
//        viewPager.setAdapter(adapterView);
//        viewPager.addOnPageChangeListener(sliderPageChangeListener);
//
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        int current = getItem(+1);
//                        if (current == layout){
//                            current = 0;
//                        }
//                        viewPager.setCurrentItem(current, true);
//                    }
//                });
//            }
//        },6000 , 5000);
//    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void dana() {
        final NumberSeparatorTextWatcher numberSeparatorTextWatcher = new NumberSeparatorTextWatcher(etDana);
        etDana.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etDana.getText().toString().trim().length() > 0) {
                    if (etDana.getText().toString().trim().equalsIgnoreCase(".")) {
//                        kreditModel.setDana(0.0);
                        etDana.setText("0.");
                    }
                    else if (etDana.getText().toString().trim().equalsIgnoreCase(",")) {
//                        kreditModel.setDana(null);
                    }

                }
            }
        });
        etDana.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    etDana.addTextChangedListener(numberSeparatorTextWatcher);

                }
                if (!hasFocus) {
                    etDana.removeTextChangedListener(numberSeparatorTextWatcher);
                }
            }
        });

    }

    @OnClick(R.id.buttonHitung)
    public void hitung(){

        Intent i = new Intent(SubmitDana.this, ops.ServiceBackground.class);
        startService(i);

        dana = etDana.getText().toString();
        if (dana.equalsIgnoreCase("")) {
            Toast.makeText(this, "Silahkan masukkan dana yang diinginkan", Toast.LENGTH_LONG).show();
        } else {
            temp_dana = Double.parseDouble(dana.replaceAll("[^\\d]", ""));

//            Integer jumlah = parameterPinjaman.count();
//            Log.e("ini jumlah",jumlah.toString());
//
//            list = parameterPinjaman.selectList();
//            parameterPinjamanModel = parameterPinjaman.select(2);

//            Log.e("ini parameter pinjaman",parameterPinjamanModel.getParameter());

//            for(int i =0; i<list.size(); i++){
//
//                Log.e("ini parameter nya", list.get(i).getParameter());
//            }
            DecimalFormat format1 = new DecimalFormat("#");
            format1.setMaximumFractionDigits(0);
            parameterPinjamanModel = parameterPinjaman.selectBy("minimal_peminjaman");
            minDana = parameterPinjamanModel.getValue_motor();

            minDana2 = NumberSeparator.split(format1.format(minDana));

//            Log.e("max", parameterPinjamanModel.getValue_mobil().toString());

            //hitung min Dana
            if (temp_dana < minDana){
                dialogOTR(R.string.errorDanaMin, minDana2);
            } else {
                if (dana != null) {

//                    Log.e("Dana awal", dana);
//                    Intent intent = new Intent(this, SimulasiHome.class);
////                    intent.putExtra(SimulasiHome.DANA, dana);
//                    startActivity(intent);
                }
            }
        }

    }



    private void slider(ViewPager viewPager) {
        SliderAdapter adapter = new SliderAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();

        viewPager.setAdapter(adapter);
    }

    ViewPager.OnPageChangeListener sliderPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            addBottomDotsForSlider(position);
            if (position == layout - 1) {} else {}
        }
        @Override public void onPageScrolled(int arg0, float arg1, int arg2) {}
        @Override public void onPageScrollStateChanged(int arg0) {}
    };

    private void addBottomDotsForSlider(int currentPage) {
        dots = new TextView[layout];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(30);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

//    private int getItem(int i) {
//        return viewPager.getCurrentItem() + i;
//    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.doubleBackPressConfirmation, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    protected void dialogOTR(int rString, String bm) {
        new MaterialDialog.Builder(this)
                .content(getString(rString)+ " Rp. " + bm + ",-")
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

    @Override
    protected void onStop() {

        sliderLayout.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        //BIKIN WEB ACTION DISINI
        String link = slider.getBundle().getString("extra");
        if (!link.equalsIgnoreCase("")){
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(slider.getBundle().get("extra") + ""));
            startActivity(browserIntent);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
