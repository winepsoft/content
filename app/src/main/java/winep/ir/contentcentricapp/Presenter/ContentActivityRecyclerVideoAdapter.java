package winep.ir.contentcentricapp.Presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import winep.ir.contentcentricapp.DataModel.Video;
import winep.ir.contentcentricapp.R;

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
        View itemview= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_view_list_video_list,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
/*      holder.txtVideoTitle.setText(videos.get(position).getVideoTitle());
        holder.btnVideoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObserverVideoPlayer.setVideoPlayerStatus(true);
            }
        });*/

        /*String vidAddress = "http://winep.ir/media/video1.mov";
        Uri videoUrl = Uri.parse(vidAddress);
        holder.video.setVideoURI(videoUrl);
        holder.video.seekTo(2000);*/

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageOfVideo;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageOfVideo=(ImageView)itemView.findViewById(R.id.image);
        }
    }
}
