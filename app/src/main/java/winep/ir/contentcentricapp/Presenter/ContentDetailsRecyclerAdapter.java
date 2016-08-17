package winep.ir.contentcentricapp.Presenter;

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
public class ContentDetailsRecyclerAdapter extends RecyclerView.Adapter<ContentDetailsRecyclerAdapter.MyViewHolder> {

    private ArrayList<MainMenuItem> items;

    ContentDetailsRecyclerAdapter(ArrayList<MainMenuItem> items){
        this.items=items;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_content_details_recycler_view_item,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.imgContentDetailIcon.setImageResource(items.get(position).getIconId());
        holder.txtContentDetailTitle.setText(items.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgContentDetailIcon;
        public TextView txtContentDetailTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgContentDetailIcon=(ImageView)itemView.findViewById(R.id.contentDetailImage);
            txtContentDetailTitle=(TextView) itemView.findViewById(R.id.contentDetailTitle);
        }
    }

}
