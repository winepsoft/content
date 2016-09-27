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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import winep.ir.contentcentricapp.DataModel.Audio;
import winep.ir.contentcentricapp.Presenter.DownloadFile.Download;
import winep.ir.contentcentricapp.Presenter.DownloadFile.DownloadService;
import winep.ir.contentcentricapp.R;
import winep.ir.contentcentricapp.Utility.Utility;

/**
 * Created by ShaisteS on 8/23/2016.
 */
public class ContentActivityRecyclerAudioAdapter extends RecyclerView.Adapter<ContentActivityRecyclerAudioAdapter.MyViewHolder> {

    public static final String MESSAGE_PROGRESS = "message_progress";
    private ArrayList<Audio> audios;
    private Context context;

    ContentActivityRecyclerAudioAdapter(Context context,ArrayList<Audio> audios){
        this.audios=audios;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_view_audio_list_item,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String filePath = Utility.getInstance().getAdreessSaveFile() + audios.get(position).getAudioTitle() + ".wav";
        File file = new File(filePath);
        if (file.exists()) {
            holder.txtAudioTitle.setText(audios.get(position).getAudioTitle());
            holder.playerLayout.setVisibility(View.VISIBLE);
            holder.downloadLayout.setVisibility(View.GONE);
        }
        else{
            holder.txtAudioTitle.setText(audios.get(position).getAudioTitle());
            holder.playerLayout.setVisibility(View.GONE);
            holder.downloadLayout.setVisibility(View.VISIBLE);
        }

        holder.btnAudioDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Download
                String url="http://winep.ir/media/voice1.wav";
                String audioName=audios.get(position).getAudioTitle()+".wav";
                startDownload(url,audioName,holder.item);
            }
        });

        holder.btnAudioPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO audio play
            }
        });
    }

    @Override
    public int getItemCount() {
        return audios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtAudioTitle;
        public LinearLayout playerLayout;
        public LinearLayout downloadLayout;
        public ImageButton btnAudioDownload;
        public ProgressBar progressBr;
        public ImageButton btnAudioPlay;
        public SeekBar seekBar;
        public View item;
        public MyViewHolder(View itemView) {
            super(itemView);
            item=itemView;
            txtAudioTitle=(TextView)itemView.findViewById(R.id.audioTitle);
            playerLayout=(LinearLayout) itemView.findViewById(R.id.playerLayout);
            downloadLayout=(LinearLayout) itemView.findViewById(R.id.downloadLayout);
            progressBr=(ProgressBar)itemView.findViewById(R.id.audioProgress);
            btnAudioDownload=(ImageButton)itemView.findViewById(R.id.audioDownload);
            btnAudioPlay=(ImageButton)itemView.findViewById(R.id.audioPlay);
            seekBar=(SeekBar)itemView.findViewById(R.id.seekBar);
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
        final ProgressBar mProgressBar=(ProgressBar)itemView.findViewById(R.id.audioProgress);
        final LinearLayout downloadLayout=(LinearLayout)itemView.findViewById(R.id.downloadLayout);
        final LinearLayout playerLayout=(LinearLayout)itemView.findViewById(R.id.playerLayout);


        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if(intent.getAction().equals(MESSAGE_PROGRESS)){
                    Download download = intent.getParcelableExtra("download");
                    mProgressBar.setProgress(download.getProgress());
                    if(download.getProgress() == 100){
                        downloadLayout.setVisibility(View.GONE);
                        playerLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

}
