package winep.ir.contentcentricapp.Presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import winep.ir.contentcentricapp.DataModel.Video;
import winep.ir.contentcentricapp.Presenter.Observer.ObserverVideoPlayer;
import winep.ir.contentcentricapp.R;

/**
 * Created by ShaisteS on 8/23/2016.
 */
public class ContentActivityRecyclerVideoAdapter extends RecyclerView.Adapter<ContentActivityRecyclerVideoAdapter.MyViewHolder> {

    private ArrayList<Video> videos;

    ContentActivityRecyclerVideoAdapter(ArrayList<Video> videos){
        this.videos=videos;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_view_list_video_list,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtVideoTitle.setText(videos.get(position).getVideoTitle());
        holder.btnVideoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObserverVideoPlayer.setVideoPlayerStatus(true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtVideoTitle;
        public ImageButton btnVideoDownload;
        public ImageButton btnVideoPlay;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtVideoTitle=(TextView)itemView.findViewById(R.id.videoTitle);
            btnVideoDownload=(ImageButton)itemView.findViewById(R.id.videoDownload);
            btnVideoPlay=(ImageButton)itemView.findViewById(R.id.videoPlay);
        }
    }
}
