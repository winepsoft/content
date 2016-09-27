package winep.ir.contentcentricapp.Presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
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
    private MediaPlayer mp;
    private Handler handler;
    private Runnable runnable;


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
        final String filePath = Utility.getInstance().getAdreessSaveFile() + audios.get(position).getAudioTitle() + ".wav";
        File file = new File(filePath);
        if (file.exists()) {
            holder.txtAudioTitle.setText(audios.get(position).getAudioTitle());
            holder.playerLayout.setVisibility(View.VISIBLE);
            holder.downloadLayout.setVisibility(View.GONE);
            holder.btnAudioPlay.setTag("pause");
        }
        else{
            holder.txtAudioTitle.setText(audios.get(position).getAudioTitle());
            holder.playerLayout.setVisibility(View.GONE);
            holder.downloadLayout.setVisibility(View.VISIBLE);
            holder.progressBr.setVisibility(View.VISIBLE);
        }

        holder.btnAudioDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="http://winep.ir/media/voice1.wav";
                String audioName=audios.get(position).getAudioTitle()+".wav";
                startDownload(url,audioName,holder.item);
            }
        });

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekbar(holder.itemView);
            }
        };

        holder.btnAudioPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO audio play
                if(mp!= null && mp.isPlaying()){
                    mp.release();
                }
                mp = MediaPlayer.create(context,Uri.parse(filePath));
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        updateSeekbar(holder.itemView);
                    }
                };
                String s = (String) holder.btnAudioPlay.getTag();
                if (s.equals("pause")) {
                    mp.start();
                    holder.btnAudioPlay.setTag("play");
                    holder.btnAudioPlay.setImageResource(R.mipmap.pause);
                    updateSeekbar(holder.item);
                } else {
                    mp.stop();
                    holder.btnAudioPlay.setTag("pause");
                    holder.btnAudioPlay.setImageResource(R.mipmap.play);
                    handler.removeCallbacks(runnable);
                }
            }
        });
    }

    public void updateSeekbar(View itemView) {
        //find current progress position of mediaPlayer
        float progress = ((float) mp.getCurrentPosition() / mp.getDuration());
        //set this progress to seekBar
        SeekBar seekBar=(SeekBar) itemView.findViewById(R.id.seekBar);
        seekBar.setProgress((int) (progress * 100));
        //run handler again after 1 second
        handler.postDelayed(runnable, 1000);
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

    private void startDownload(String url,String audioName,View itemView){
        registerReceiver(itemView);
        Intent intent = new Intent(context,DownloadService.class);
        intent.putExtra("url",url);
        intent.putExtra("name",audioName);
        context.startService(intent);
    }

    private void registerReceiver(View itemView){
        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(context);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        final ProgressBar mProgressBar=(ProgressBar)itemView.findViewById(R.id.audioProgress);
        final ImageButton mAudioPlayerBtn=(ImageButton)itemView.findViewById(R.id.audioPlay);
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
                        mAudioPlayerBtn.setTag("pause");
                    }
                }
            }
        };
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

}
