package ops.screen.fragment;



import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.smmf.mobile.R;
import ops.screen.MainActivityDashboard;

public class PanduanTransferFragment extends Fragment {

    @BindView(R.id.lineartransf1)
    LinearLayout linearLayout;

    @BindView(R.id.layoutDesc)
    LinearLayout layoutDesc;

    @BindView(R.id.lineartransf2)
    LinearLayout lineartransf2;

    @BindView(R.id.lineartransf3)
    LinearLayout lineartransf3;

    @BindView(R.id.lineartransf4)
    LinearLayout lineartransf4;

    @BindView(R.id.txtDesctransf)
    TextView txtDesctransf;


    @BindView(R.id.headerTxttransf)
    TextView headerTxttransf;

    View view;

    Bundle bundle = new Bundle();


//    PanduanTransferDetailFragment panduanTransferDetailFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.panduan_transfer, container, false);
        ButterKnife.bind(this, view);
        txtDesctransf.setVisibility(view.INVISIBLE);
//        panduanTransferDetailFragment = new PanduanTransferDetailFragment();
        MainActivityDashboard myActivity = (MainActivityDashboard) getActivity();
        myActivity.setScreenumber(1);
        return view;
    }


    @OnClick(R.id.lineartransf1)
    public void expandButton(){
        int vis = txtDesctransf.getVisibility();
        if(vis<=0)
            collapse();
        else
            expand();
    }

    @OnClick(R.id.lineartransf2)
    public void gotopanduanTransfer(){
        loadFragment("TRANSFERATM_SINARMAS");
    }

    @OnClick(R.id.lineartransf3)
    public void gotopanduanTransferAtm(){
        loadFragment("TRANSFERATM_LAIN");
    }

    @OnClick(R.id.lineartransf4)
    public void gotopanduanTransferTeller(){
        loadFragment("TRANSFER_TELLER");
    }

    public void collapse(){
/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layoutDesc.getLayoutTransition()
                    .enableTransitionType(LayoutTransition.DISAPPEARING);
        }*/
        ObjectAnimator animation = ObjectAnimator.ofInt(
                txtDesctransf,
                "maxLines",
                0);
        animation.setDuration(500);
        animation.start();
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

    }

    public void expand(){
    /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layoutDesc.getLayoutTransition()
                    .enableTransitionType(LayoutTransition.APPEARING);
        }*/
/*        layoutDesc.animate()
                .translationY(layoutDesc.getHeight())
                .alpha(1.0f)
                .setDuration(2000);*/

        ObjectAnimator animation = ObjectAnimator.ofInt(
                txtDesctransf,
                "maxLines",
                50);
        animation.setDuration(1000);
        animation.start();

    }

    private void loadFragment(String Screen) {


        Bundle bundle = new Bundle();
        bundle.putString("key", Screen); // Put anything what you want

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.animation_enter, R.animator.animation_out, R.animator.animation_back_left, R.animator.animation_back_right);
//        panduanTransferDetailFragment.setArguments(bundle);
//        fragmentTransaction.replace(R.id.frameLayout, panduanTransferDetailFragment).addToBackStack(null).commit();

    }
}

