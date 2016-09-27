package winep.ir.contentcentricapp.Presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;
import java.util.ArrayList;

import winep.ir.contentcentricapp.DataModel.Video;
import winep.ir.contentcentricapp.R;
import winep.ir.contentcentricapp.Utility.Utility;

/**
 * Created by ShaisteS on 8/23/2016.
 */
public class ContentActivityRecyclerVideoAdapter extends RecyclerView.Adapter<ContentActivityRecyclerVideoAdapter.MyViewHolder> {

    private ArrayList<Video> videos;
    private Context context;

    ContentActivityRecyclerVideoAdapter(Context context, ArrayList<Video> videos){
        this.videos=videos;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_view_list_video_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        String filePath=Utility.getInstance().getAdreessSaveFile()+videos.get(position).getVideoTitle()+".mp4";

        File file = new File(filePath);
        if(file.exists()){
            holder.progressBar.setVisibility(View.GONE);
            holder.btnDownload.setVisibility(View.GONE);
            holder.btnPlay.setVisibility(View.VISIBLE);

        }
        else{
            holder.progressBar.setVisibility(View.GONE);
            holder.btnDownload.setVisibility(View.VISIBLE);
            holder.btnPlay.setVisibility(View.GONE);

        }

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btnDownload.setVisibility(View.GONE);
                holder.progressBar.setVisibility(View.VISIBLE);
                //TODO Download Process

            }
        });

        holder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Open Video Player Activity
            }
        });

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageOfVideo;
        public ImageButton btnPlay;
        public ImageButton btnDownload;
        public ProgressBar progressBar;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageOfVideo=(ImageView)itemView.findViewById(R.id.image);
            btnPlay=(ImageButton)itemView.findViewById(R.id.btnPlay);
            btnDownload=(ImageButton)itemView.findViewById(R.id.btnDownload);
            progressBar=(ProgressBar)itemView.findViewById(R.id.progress);
        }
    }

}
