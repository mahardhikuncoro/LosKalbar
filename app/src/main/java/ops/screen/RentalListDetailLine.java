package ops.screen;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.smmf.mobile.R;


public class RentalListDetailLine extends RentalListDetailAdapter.ViewHolder {

    @BindView(R.id.tanggaljatuhtempotxt)
    TextView tanggaljatuhtempotxt;
    @BindView(R.id.tahunjatuhtempotxt)
    TextView tahunjatuhtempotxt;
    @BindView(R.id.cicilantxt)
    TextView cicilantxt;
    @BindView(R.id.tglpembayarantxt)
    TextView tglpembayarantxt;
    @BindView(R.id.saldoHutangtxt)
    TextView saldoHutangtxt;



    public RentalListDetailLine(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
