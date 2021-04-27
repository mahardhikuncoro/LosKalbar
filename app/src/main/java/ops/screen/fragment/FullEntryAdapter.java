package ops.screen.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import base.network.EndPoint;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.TaskListDetailModel;
import id.co.smmf.mobile.R;
import ops.screen.FormActivity;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FullEntryAdapter extends RecyclerView.Adapter<FullEntryAdapter.ViewHolder> {

    private List<TaskListDetailModel> list;
    private Context context;
    private int lastPosition = -1;
    private SQLiteConfigKu sqLiteConfigKu;
    private NetworkConnection networkConnection;
    private EndPoint endPoint;
    private MaterialDialog dialog;
    private String regno , tc, type, status, newdata, nama;


    public FullEntryAdapter(Context context, List<TaskListDetailModel> list, String regno, String tc, String type, String status, String newdata, String nama) {
        this.list = list;
        this.regno = regno;
        this.tc = tc;
        this.type = type;
        this.context = context;
        this.status = status;
        this.newdata = newdata;
        this.nama = nama;

        sqLiteConfigKu = new SQLiteConfigKu(context);
        networkConnection = new NetworkConnection(context);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sqLiteConfigKu.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();

        endPoint = retrofit.create(EndPoint.class);
    }


    @Override
    public FullEntryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklist_holder, parent, false);
        return new FullEntryListLine(view);
    }

    @Override
    public void onBindViewHolder(final FullEntryAdapter.ViewHolder holder, final int position) {
            ((FullEntryListLine) holder).namaNasabah.setText("" + list.get(position).getSectionName());
            ((FullEntryListLine) holder).idNasabah.setVisibility(View.GONE);
            ((FullEntryListLine) holder).namaNasabah.setGravity(Gravity.CENTER_VERTICAL);

        setAnimation(holder.itemView, position);

        ((FullEntryListLine) holder).rentalLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("HA"," HOHO"  + list.get(position).getFormName());
                Log.e("HA"," REGNO"  + regno);
                if(!list.get(position).getSectionType().equalsIgnoreCase("list")) {
                    Intent intent = new Intent(context, FormActivity.class);
                    intent.putExtra("SECTION_NAME", list.get(position).getSectionName());
                    intent.putExtra("REGNO", regno);
                    intent.putExtra("TC", tc);
                    intent.putExtra("TYPE", type);
                    intent.putExtra("STATUS", status);
                    intent.putExtra("NEW_DATA", newdata);
                    intent.putExtra("TABLE_NAME", list.get(position).getTableName());
                    intent.putExtra("FORM_NAME", list.get(position).getFormName());
                    intent.putExtra("SECTION_TYPE", list.get(position).getSectionType());
                    intent.putExtra("NAMA_NASABAH", nama);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, FullEntryList.class);
                    intent.putExtra("SECTION_NAME", list.get(position).getSectionName());
                    intent.putExtra("REGNO", regno);
                    intent.putExtra("TC", tc);
                    intent.putExtra("TYPE", type);
                    intent.putExtra("STATUS", status);
                    intent.putExtra("TABLE_NAME", list.get(position).getTableName());
                    intent.putExtra("FORM_NAME", list.get(position).getFormName());
                    intent.putExtra("SECTION_TYPE", list.get(position).getSectionType());
                    intent.putExtra("NAMA_NASABAH", nama);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }

            }
        });


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


    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    protected void dialog(String rString) {
        new MaterialDialog.Builder(context)
                .content(rString)
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

}
