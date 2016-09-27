package winep.ir.contentcentricapp.Presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import winep.ir.contentcentricapp.R;
import winep.ir.contentcentricapp.Utility.ChangeThem;
import winep.ir.contentcentricapp.Utility.Utility;

/**
 * Created by ShaisteS on 8/15/2016.
 */
public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView videoView;
    private String videoName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangeThem.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_video_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeItemsInView();
        videoName=getIntent().getStringExtra("videoName");
        String videoPath=Utility.getInstance().getAdreessSaveFile()+videoName+".mp4";
        playVideo(videoPath);

    }

    public void initializeItemsInView(){
        videoView=(VideoView)findViewById(R.id.videoView);
    }

    public void playVideo(String videoPath){
        Uri uri=Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
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
                shareText("share video link");
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
       finish();
    }

}