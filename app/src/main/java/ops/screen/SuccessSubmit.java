package ops.screen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import base.network.Slider;
import base.sqlite.SliderSQL;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.smmf.mobile.R;

/**
 * Created by christian on 4/10/18.
 */

public class SuccessSubmit extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    public static final String ID = "ID";

    @BindView(R.id.layoutDots2)
    LinearLayout dotsLayout;

    @BindView(R.id.slider)
    SliderLayout sliderLayout;

    private Integer layout2;

    private TextView[] dots2;

    private Long id;

    private SliderSQL slidersql;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_submit_activity);
        ButterKnife.bind(this);

        id = getIntent().getExtras().getLong(ID);
//        Log.e("Ini ID callback",id.toString());

//        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager2);
//        AndroidImageAdapter adapterView = new AndroidImageAdapter(this);
//        layout2 = adapterView.getCount();
//        addBottomDotsForSlider(0);

        slidersql = new SliderSQL(this);

        Integer slidesize = slidersql.count();
        if(slidesize > 0){
            sliderLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }

        for(int i = 1 ; i <= slidesize; i++){

            Slider temp = slidersql.select(i);

            TextSliderView textSliderView = new TextSliderView(SuccessSubmit.this);

            textSliderView
                    .description(temp.getName())
                    .image(temp.getImage())
                    .empty(R.drawable.defaultslide)
                    .error(R.drawable.defaultslide)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(SuccessSubmit.this);

            textSliderView.bundle(new Bundle());

            textSliderView.getBundle()
                    .putString("extra",temp.getLink());

            sliderLayout.addSlider(textSliderView);

        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(6000);
        sliderLayout.addOnPageChangeListener(SuccessSubmit.this);

//        mViewPager.setAdapter(adapterView);
//        mViewPager.addOnPageChangeListener(sliderPageChangeListener2);
    }


    @OnClick(R.id.buttonPinjamanLain)
    public void next(){
//        Intent intent = new Intent(this, SubmitDana.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        finish();
    }

    ViewPager.OnPageChangeListener sliderPageChangeListener2 = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            addBottomDotsForSlider(position);
            if (position == layout2 - 1) {} else {}
        }
        @Override public void onPageScrolled(int arg0, float arg1, int arg2) {}
        @Override public void onPageScrollStateChanged(int arg0) {}
    };

    private void addBottomDotsForSlider(int currentPage) {
        dots2 = new TextView[layout2];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots2.length; i++) {
            dots2[i] = new TextView(this);
            dots2[i].setText(Html.fromHtml("&#8226;"));
            dots2[i].setTextSize(30);
            dots2[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots2[i]);
        }

        if (dots2.length > 0)
            dots2[currentPage].setTextColor(colorsActive[currentPage]);
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
