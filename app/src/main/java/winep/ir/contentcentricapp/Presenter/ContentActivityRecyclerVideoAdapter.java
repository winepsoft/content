package winep.ir.contentcentricapp.Presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import winep.ir.contentcentricapp.DataModel.Video;
import winep.ir.contentcentricapp.Presenter.DownloadFile.Download;
import winep.ir.contentcentricapp.Presenter.DownloadFile.DownloadService;
import winep.ir.contentcentricapp.R;
import winep.ir.contentcentricapp.Utility.Utility;

/**
 * Created by ShaisteS on 8/23/2016.
 */
public class ContentActivityRecyclerVideoAdapter extends RecyclerView.Adapter<ContentActivityRecyclerVideoAdapter.MyViewHolder> {

    private ArrayList<Video> videos;
    private Context context;
    public static final String MESSAGE_PROGRESS = "message_progress";


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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        String filePath=Utility.getInstance().getAdreessSaveFile()+videos.get(position).getVideoTitle()+".mp4";

        File file = new File(filePath);
        if(file.exists()){
            holder.progressBar.setVisibility(View.GONE);
            holder.btnDownload.setVisibility(View.GONE);
            holder.btnPlay.setVisibility(View.VISIBLE);
            holder.videoInfoText.setText(videos.get(position).getVideoTitle());

        }
        else{
            holder.progressBar.setVisibility(View.GONE);
            holder.btnDownload.setVisibility(View.VISIBLE);
            holder.btnPlay.setVisibility(View.GONE);
            holder.videoInfoText.setText(videos.get(position).getVideoTitle());

        }

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btnDownload.setVisibility(View.GONE);
                holder.progressBar.setVisibility(View.VISIBLE);
                //TODO Download Process
                String url="http://winep.ir/media/video1.mov";
                String videoName=videos.get(position).getVideoTitle()+".mp4";
                startDownload(url,videoName,holder.item);

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
        public TextView videoInfoText;
        public View item;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageOfVideo=(ImageView)itemView.findViewById(R.id.image);
            btnPlay=(ImageButton)itemView.findViewById(R.id.btnPlay);
            btnDownload=(ImageButton)itemView.findViewById(R.id.btnDownload);
            progressBar=(ProgressBar)itemView.findViewById(R.id.progress);
            videoInfoText=(TextView)itemView.findViewById(R.id.videoInformation);
            this.item=itemView;
        }
    }

    private void startDownload(String url,String videoName,View itemView){
        registerReceiver(itemView);
        Intent intent = new Intent(context,DownloadService.class);
        intent.putExtra("url",url);
        intent.putExtra("name",videoName);
        context.startService(intent);

    }


    private void registerReceiver(View itemView){

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        final ProgressBar mProgressBar=(ProgressBar)itemView.findViewById(R.id.progress);
        final ImageButton mPlayButton=(ImageButton)itemView.findViewById(R.id.btnPlay);


        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if(intent.getAction().equals(MESSAGE_PROGRESS)){

                    Download download = intent.getParcelableExtra("download");
                    mProgressBar.setProgress(download.getProgress());
                    if(download.getProgress() == 100){
                        mProgressBar.setVisibility(View.GONE);
                        mPlayButton.setVisibility(View.VISIBLE);

                    }
                }
            }
        };
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

}
