package ops.screen;

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
import id.co.smmf.mobile.R;
import ops.screen.fragment.FullEntry;
import base.sqlite.TaskListDetailModel;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private List<TaskListDetailModel> list;
    private Context context;
    private int lastPosition = -1;
    private SQLiteConfigKu sqLiteConfigKu;
    private NetworkConnection networkConnection;
    private EndPoint endPoint;
    private String typelist;



    public TaskListAdapter(Context context, List<TaskListDetailModel> list, String typeList) {
        this.list = list;
        this.context = context;
        this.typelist = typeList;

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
    public TaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklist_holder, parent, false);
        return new TaskListLine(view);
    }

    @Override
    public void onBindViewHolder(final TaskListAdapter.ViewHolder holder, final int position) {
            ((TaskListLine) holder).namaNasabah.setText("" + list.get(position).getNamaNasabah());
            ((TaskListLine) holder).idNasabah.setText("" + list.get(position).getIdNasabah());

        setAnimation(holder.itemView, position);

        ((TaskListLine) holder).rentalLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullEntry.class);
                intent.putExtra("NAMA_NASABAH",list.get(position).getNamaNasabah());
                intent.putExtra("REGNO",list.get(position).getIdNasabah());
                intent.putExtra("TC",list.get(position).getTrack_id());
                intent.putExtra("TYPE",list.get(position).getFormCode());
                intent.putExtra("TYPELIST",typelist);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
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
