package winep.ir.contentcentricapp.Presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import winep.ir.contentcentricapp.DataModel.Audio;
import winep.ir.contentcentricapp.Presenter.Observer.ObserverAudioPlayer;
import winep.ir.contentcentricapp.R;

/**
 * Created by ShaisteS on 8/23/2016.
 */
public class ContentActivityRecyclerAudioAdapter extends RecyclerView.Adapter<ContentActivityRecyclerAudioAdapter.MyViewHolder> {

    private ArrayList<Audio> audios;

    ContentActivityRecyclerAudioAdapter(ArrayList<Audio> audios){
        this.audios=audios;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_view_audio_list_item,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtAudioTitle.setText(audios.get(position).getAudioTitle());
        holder.btnAudioPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObserverAudioPlayer.setAudioPlayerStatuse(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return audios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtAudioTitle;
        public ImageButton btnAudioDownload;
        public ImageButton btnAudioPlay;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtAudioTitle=(TextView)itemView.findViewById(R.id.audioTitle);
            btnAudioDownload=(ImageButton)itemView.findViewById(R.id.audioDownload);
            btnAudioPlay=(ImageButton)itemView.findViewById(R.id.audioPlay);
        }
    }
}
