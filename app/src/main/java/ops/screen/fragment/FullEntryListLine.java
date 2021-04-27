package ops.screen.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.co.smmf.mobile.R;
import ops.screen.TaskListAdapter;


public class FullEntryListLine extends FullEntryAdapter.ViewHolder {

    @BindView(R.id.namaNasabah)
    TextView namaNasabah;
    @BindView(R.id.idNasabah)
    TextView idNasabah;
    @BindView(R.id.rentalLinear)
    LinearLayout rentalLinear;


    public FullEntryListLine(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
