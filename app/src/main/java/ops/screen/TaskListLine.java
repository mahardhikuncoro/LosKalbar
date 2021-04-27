package ops.screen;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.smmf.mobile.R;


public class TaskListLine extends TaskListAdapter.ViewHolder {

    @BindView(R.id.namaNasabah)
    TextView namaNasabah;
    @BindView(R.id.idNasabah)
    TextView idNasabah;
    @BindView(R.id.rentalLinear)
    LinearLayout rentalLinear;


    public TaskListLine(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
