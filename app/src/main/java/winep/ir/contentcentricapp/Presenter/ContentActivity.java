package winep.ir.contentcentricapp.Presenter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import winep.ir.contentcentricapp.DataModel.Audio;
import winep.ir.contentcentricapp.DataModel.MainMenuItem;
import winep.ir.contentcentricapp.DataModel.Video;
import winep.ir.contentcentricapp.Presenter.Observer.ObserverAudioPlayer;
import winep.ir.contentcentricapp.Presenter.Observer.ObserverAudioPlayerListener;
import winep.ir.contentcentricapp.Presenter.Observer.ObserverVideoPlayer;
import winep.ir.contentcentricapp.Presenter.Observer.ObserverVideoPlayerListener;
import winep.ir.contentcentricapp.R;
import winep.ir.contentcentricapp.Utility.ChangeThem;
import winep.ir.contentcentricapp.Utility.StaticParameters;

public class ContentActivity extends AppCompatActivity implements OnMapReadyCallback {

    private AppBarLayout topAppBarLayout;
    private ImageView backgroundImage;
    private ImageButton audioPlayer;
    private ImageButton videoPlayer;
    private TextView mainContentText;
    private MapFragment mMapFragment;
    private RecyclerView recyclerViewAudio;
    private RecyclerView recyclerViewVideo;
    private ContentActivityRecyclerAudioAdapter audioAdapter;
    private ContentActivityRecyclerVideoAdapter videoAdapter;
    private View bottomSheetAudioPlayer;
    private BottomSheetBehavior mBottomSheetBehaviorAudio;
    private View bottomSheetVideoPlayer;
    private BottomSheetBehavior mBottomSheetBehaviorVideo;
    private VideoView videoView;
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
        setContentView(R.layout.activity_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StaticParameters.getInstance().ContentActivity=this;
        initializeItemsInView();
        //visibleCardViewAudioAndVideo();

        Boolean imageStatus=getIntent().getExtras().getBoolean("imageStatus");
        setImageForBackgroundAppBarLayout(imageStatus);

        audioAdapter=new ContentActivityRecyclerAudioAdapter(this,createAudioList());
        recyclerViewAudio.setAdapter(audioAdapter);

        videoAdapter=new ContentActivityRecyclerVideoAdapter(StaticParameters.getInstance().ContentActivity,createVideoList());
        recyclerViewVideo.setAdapter(videoAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);

        mainContentText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.menu_text_selection,menu);
                menu.removeItem(android.R.id.selectAll);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.share:
                        shareText(getTextSelect());
                        actionMode.finish();
                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });


        ObserverAudioPlayer.ObserverChangeAudioPlayerStatus(new ObserverAudioPlayerListener() {
            @Override
            public void changeAudioPlayerStatus() {
                mBottomSheetBehaviorAudio.setState(BottomSheetBehavior.STATE_EXPANDED);
                play();
            }
        });

        ObserverVideoPlayer.ObserverChangeVideoPlayerStatus(new ObserverVideoPlayerListener() {
            @Override
            public void changeVideoPlayerStatus() {
                /*mBottomSheetBehaviorVideo.setState(BottomSheetBehavior.STATE_EXPANDED);
                playVideo();*/
                openVideoPlayer();
            }
        });

        mp = MediaPlayer.create(getBaseContext(),R.raw.music);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekbar();
            }
        };

        imgBtn.setOnClickListener(new View.OnClickListener() {
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

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                pause();
            }
        });

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
        imgBtn.setImageResource(R.mipmap.play);
        // stop seekbar
        handler.removeCallbacks(runnable);
    }

    public void play() {
        mp.start();
        imgBtn.setTag("play"); // next state should be 'pause'
        imgBtn.setImageResource(R.mipmap.pause);
        //start seekBar
        updateSeekbar();
    }

    public void visibleCardViewAudioAndVideo(){
        findViewById(R.id.layoutCardViewAudio).setVisibility(View.GONE);
        findViewById(R.id.layoutCardViewVideo).setVisibility(View.GONE);
    }
    public void initializeItemsInView(){
        topAppBarLayout=(AppBarLayout)findViewById(R.id.app_bar);
        backgroundImage=(ImageView)findViewById(R.id.backgroundImage);
        mainContentText =(TextView)findViewById(R.id.mainContent);
        recyclerViewAudio=(RecyclerView)findViewById(R.id.recyclerViewAudio);
        recyclerViewVideo=(RecyclerView)findViewById(R.id.recyclerViewVideo);
        setLayoutManagerToRecyclerView();

        bottomSheetAudioPlayer = findViewById( R.id.bottom_sheet_audio_player );
        mBottomSheetBehaviorAudio= BottomSheetBehavior.from(bottomSheetAudioPlayer);

        bottomSheetVideoPlayer= findViewById( R.id.bottom_sheet_video_player);
        mBottomSheetBehaviorVideo = BottomSheetBehavior.from(bottomSheetVideoPlayer);

        videoView=(VideoView)findViewById(R.id.videoView);

        imgBtn = (ImageButton) findViewById(R.id.playBtn);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        songImageView = (ImageView) findViewById(R.id.songImage);
        titleTextView = (TextView) findViewById(R.id.title);
    }

    public void playVideo(){
        String uriPath="android.resource://"+getPackageName()+"/"+R.raw.video;
        Uri uri=Uri.parse(uriPath);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();

    }

    public void setLayoutManagerToRecyclerView(){
        LinearLayoutManager audioLayoutManager;
        audioLayoutManager = new LinearLayoutManager(StaticParameters.getInstance().ContentActivity);
        recyclerViewAudio.setLayoutManager(audioLayoutManager);
        /*LinearLayoutManager videoLayoutManager;
        videoLayoutManager = new LinearLayoutManager(StaticParameters.getInstance().ContentActivity);
        recyclerViewVideo.setLayoutManager(videoLayoutManager);*/
        GridLayoutManager mLayoutManager;
        mLayoutManager = new GridLayoutManager(StaticParameters.getInstance().ContentActivity,StaticParameters.getInstance().VideoGridViewColumnNumber);
        recyclerViewVideo.setLayoutManager(mLayoutManager);
    }

    public void changeAppBarLayoutHeight(int fractionOfHeightScreenSize){
        float heightDp = getResources().getDisplayMetrics().heightPixels / fractionOfHeightScreenSize;
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)topAppBarLayout.getLayoutParams();
        lp.height = (int)heightDp;
    }

    public void setImageForBackgroundAppBarLayout(boolean statusSetImage){

        if (statusSetImage) {
            changeAppBarLayoutHeight(2);
            Glide.with(this).load(R.drawable.back).crossFade().into(backgroundImage);
        }
        else
            changeAppBarLayoutHeight(4);

    }

    public ArrayList<MainMenuItem> createMainMenu(){
        ArrayList<MainMenuItem> items=new ArrayList<>();
        for (int i=0;i<10;i++){
            MainMenuItem item=new MainMenuItem();
            item.setTitle("title"+i);
            item.setIconId(R.mipmap.ic_launcher);
            items.add(item);
        }
        return items;
    }

    public ArrayList<Audio> createAudioList(){
        ArrayList<Audio> audios=new ArrayList<>();
        for (int i=0;i<2;i++){
            Audio audio=new Audio();
            audio.setAudioTitle("audio"+i);
            audios.add(audio);
        }
        return audios;
    }

    public ArrayList<Video> createVideoList(){
        ArrayList<Video> videos=new ArrayList<>();
        for (int i=0;i<3;i++){
            Video video=new Video();
            video.setVideoTitle("video"+i);
            videos.add(video);
        }
        return videos;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_scrolling, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(35.985078, 50.728629);
        googleMap.addMarker(new MarkerOptions().position(latLng)
                .title("Sydney Opera House"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

    }

    @Override
    public void onBackPressed() {
        mp.stop();
        finish();
    }

    public void shareText(String shareText){

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void openAudioPlayer(){
        Intent intent=new Intent(StaticParameters.getInstance().ContentActivity,AudioPlayerActivity.class);
        startActivity(intent);
    }

    public void openVideoPlayer(){
        Intent intent=new Intent(StaticParameters.getInstance().ContentActivity,VideoPlayerActivity.class);
        startActivity(intent);
    }

    public String getTextSelect(){
        int min = 0;
        int max = mainContentText.getText().length();
        if (mainContentText.isFocused()) {
            int selStart = mainContentText.getSelectionStart();
            int selEnd = mainContentText.getSelectionEnd();
            min = Math.max(0, Math.min(selStart, selEnd));
            max = Math.max(0, Math.max(selStart, selEnd));
        }
        CharSequence selectedText = mainContentText.getText().subSequence(min, max);
        return selectedText.toString();
    }

}
