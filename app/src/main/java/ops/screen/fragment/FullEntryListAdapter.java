package ops.screen.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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


public class FullEntryListAdapter extends RecyclerView.Adapter<FullEntryListAdapter.ViewHolder> {

    private List<TaskListDetailModel> list;
    private Context context;
    private int lastPosition = -1;
    private SQLiteConfigKu sqLiteConfigKu;
    private NetworkConnection networkConnection;
    private EndPoint endPoint;
    private MaterialDialog dialog;
    private String regno , tc, type, nama;


    public FullEntryListAdapter(Context context, List<TaskListDetailModel> list, String regno, String tc, String type, String nama) {
        this.list = list;
        this.regno = regno;
        this.tc = tc;
        this.type = type;
        this.context = context;
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
    public FullEntryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklist_holder, parent, false);
        return new FullEntryDataListLine(view);
    }

    @Override
    public void onBindViewHolder(final FullEntryListAdapter.ViewHolder holder, final int position) {
            ((FullEntryDataListLine) holder).namaNasabah.setText("" + list.get(position).getDataDesc());
            ((FullEntryDataListLine) holder).idNasabah.setVisibility(View.GONE);

        setAnimation(holder.itemView, position);

        ((FullEntryDataListLine) holder).rentalLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if(list != null && !list.get(position).getSectionType().equalsIgnoreCase("list")) {
                    Intent intent = new Intent(context, FormActivity.class);
                    intent.putExtra("SECTION_NAME", list.get(position).getSectionName());
//                    intent.putExtra("REGNO", list.get(position).getDataId());
                    intent.putExtra("REGNO", regno);
                    intent.putExtra("IMAGEID", list.get(position).getDataId());
                    intent.putExtra("TC", tc);
                    intent.putExtra("TYPE", type);
                    intent.putExtra("TABLE_NAME", list.get(position).getTableName());
                    intent.putExtra("FORM_NAME", list.get(position).getFormName());
                    intent.putExtra("NAMA_NASABAH", nama);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
//                }else{
//                    Intent intent = new Intent(context, FullEntryList.class);
//                    intent.putExtra("SECTION_NAME", list.get(position).getSectionName());
//                    intent.putExtra("REGNO", regno);
//                    intent.putExtra("TC", tc);
//                    intent.putExtra("TYPE", type);
//                    intent.putExtra("TABLE_NAME", list.get(position).getTableName());
//                    intent.putExtra("FORM_NAME", list.get(position).getFormName());
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(intent);
//                }

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
