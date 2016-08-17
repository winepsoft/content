package winep.ir.contentcentricapp.Presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import winep.ir.contentcentricapp.DataModel.MainMenuItem;
import winep.ir.contentcentricapp.R;

/**
 * Created by ShaisteS on 8/6/2016.
 */
public class MainActivityRecyclerAdapter extends RecyclerView.Adapter<MainActivityRecyclerAdapter.MyViewHolder>  {

    ArrayList<MainMenuItem> items;
    private Context context;

    public MainActivityRecyclerAdapter(ArrayList<MainMenuItem> items,Context context){
        this.items=items;
        this.context=context;

    }

    @Override
    public MainActivityRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.activity_main_recycler_view_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainActivityRecyclerAdapter.MyViewHolder holder, int position) {
        holder.imageViewItemIcon.setImageResource(items.get(position).getIconId());
        holder.txtViewItemTitle.setText(items.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewItemIcon;
        public TextView txtViewItemTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewItemIcon=(ImageView)itemView.findViewById(R.id.itemIcon);
            txtViewItemTitle=(TextView)itemView.findViewById(R.id.itemTitle);
        }
    }
}
