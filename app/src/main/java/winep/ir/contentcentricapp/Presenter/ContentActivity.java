package winep.ir.contentcentricapp.Presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import winep.ir.contentcentricapp.DataModel.MainMenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangeThem.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StaticParameters.getInstance().ContentActivity=this;
        initializeItemsInView();

        Boolean imageStatus=getIntent().getExtras().getBoolean("imageStatus");
        setImageForBackgroundAppBarLayout(imageStatus);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        audioPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAudioPlayer();
            }
        });
        videoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVideoPlayer();
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
    }

    public void initializeItemsInView(){
        topAppBarLayout=(AppBarLayout)findViewById(R.id.app_bar);
        backgroundImage=(ImageView)findViewById(R.id.backgroundImage);
        audioPlayer=(ImageButton)findViewById(R.id.play_audio);
        videoPlayer=(ImageButton)findViewById(R.id.play_video);
        mainContentText =(TextView)findViewById(R.id.mainContent);
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
