package winep.ir.contentcentricapp.Presenter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import winep.ir.contentcentricapp.R;
import winep.ir.contentcentricapp.Utility.ChangeThem;

public class AudioPlayerActivity extends AppCompatActivity {

    private ImageButton imgBtn;
    private TextView titleTextView;
    private MediaPlayer mp;
    private SeekBar seekbar;
    private Handler handler;
    private Runnable runnable;
    private ImageView songImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangeThem.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_audio_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeItemsInView();
        titleTextView.setText("Ashoobam - Chartar");
        songImageView.setImageResource(R.drawable.song_image);
        mp = MediaPlayer.create(AudioPlayerActivity.this,R.raw.music);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekbar();
            }
        };

        imgBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = (String) imgBtn.getTag();
                if (s.equals("pause")) {
                    play();
                } else {
                    pause();
                }
            }
        });

        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                long now = (long) ((float) seekbar.getProgress() / 100 * mp.getDuration());
                mp.seekTo((int) now);
                handler.postDelayed(runnable, 1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {

            }
        });

        mp.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                pause();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_share:
                shareText("share audio link");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void shareText(String shareText){

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void onBackPressed() {
        mp.stop();
        finish();
    }

    /*@Override
    protected void onPause() {
        super.onPause();
        mp.pause();
        imgBtn.setBackgroundResource(R.drawable.play_selector);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String s = (String) imgBtn.getTag();
        // check if its not first onResume which is invoked
        // note that onResum invoked always after onCreate()-> onStart()->
        // and also after we come back to our application (Activity)
        // if s equals to 'pause' it means we used setTag('pause') before
        // it means that we now should start  the player again.
        if (!s.equals("pause")) {
            mp.start();
            imgBtn.setBackgroundResource(R.drawable.pause_selector);
        }
    }*/

    public void initializeItemsInView(){
        imgBtn = (ImageButton) findViewById(R.id.playBtn);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        songImageView = (ImageView) findViewById(R.id.songImage);
        titleTextView = (TextView) findViewById(R.id.title);
    }

    public void updateSeekbar() {
        //find current progress position of mediaPlayer
        float progress = ((float) mp.getCurrentPosition() / mp.getDuration());
        //set this progress to seekBar
        seekbar.setProgress((int) (progress * 100));
        //run handler again after 1 second
        handler.postDelayed(runnable, 1000);
    }

    public void pause() {
        mp.pause();
        imgBtn.setTag("pause"); // next state should be 'pause'
        //imgBtn.setBackgroundResource(R.drawable.play_selector);
        // stop seekbar
        handler.removeCallbacks(runnable);
    }

    public void play() {
        mp.start();
        imgBtn.setTag("play"); // next state should be 'pause'
        //imgBtn.setBackgroundResource(R.drawable.pause_selector);
        //start seekBar
        updateSeekbar();
    }
}
