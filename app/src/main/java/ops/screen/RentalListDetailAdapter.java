package ops.screen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import base.sqlite.RentalListDetailModel;
import base.sqlite.RentalListModel;
import base.utils.NumberSeparator;
import id.co.smmf.mobile.R;


public class RentalListDetailAdapter extends RecyclerView.Adapter<RentalListDetailAdapter.ViewHolder> {

    private List<RentalListDetailModel> list;
    private Context context;


    public RentalListDetailAdapter(Context context, List<RentalListDetailModel> list) {
        this.list = list;
        this.context = context;
    }

//    public void updateList(List<RentalListModel> list) {
//        this.list = list;
//        notifyDataSetChanged();
//    }

    @Override
    public RentalListDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rental_detail_holder, parent, false);
        return new RentalListDetailLine(view);
    }

    @Override
    public void onBindViewHolder(RentalListDetailAdapter.ViewHolder holder, final int position) {

        ((RentalListDetailLine) holder).tanggaljatuhtempotxt.setText(list.get(position).getDueDate());
        ((RentalListDetailLine) holder).tahunjatuhtempotxt.setText(list.get(position).getDueYear());
        ((RentalListDetailLine) holder).cicilantxt.setText("Cicilan : Rp. " +list.get(position).getRental().replace(",","."));
        ((RentalListDetailLine) holder).tglpembayarantxt.setText("Tgl Pembayaran : " + list.get(position).getPaymentDate());
        ((RentalListDetailLine) holder).saldoHutangtxt.setText("Saldo Hutang : Rp. " + list.get(position).getOsBalance().replace(",","."));


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
