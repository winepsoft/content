package winep.ir.contentcentricapp.Presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import winep.ir.contentcentricapp.DataModel.MainMenuItem;
import winep.ir.contentcentricapp.R;
import winep.ir.contentcentricapp.Utility.ChangeThem;
import winep.ir.contentcentricapp.Utility.RecyclerItemClickListener;
import winep.ir.contentcentricapp.Utility.SettingsManager;
import winep.ir.contentcentricapp.Utility.StaticParameters;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mainRecyclerView;
    private MainActivityRecyclerAdapter adapter;
    private SettingsManager settingsManager;
    private StaticParameters staticParameters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staticParameters=StaticParameters.getInstance();
        staticParameters.ActivityMainContext=this;
        settingsManager=new SettingsManager(this);
        setSettingParameter();
        ChangeThem.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);
        initializeItemsInView();
        createGridViewWithRecyclerView(StaticParameters.getInstance().ActivityMainGridViewColumnNumber);
        setAdapterToMainMenuRecycler();

        mainRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(StaticParameters.getInstance().ActivityMainContext,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                       openContentDetailsActivity();
                    }
                })
        );
    }

    public void openContentDetailsActivity(){
        Intent intent=new Intent(StaticParameters.getInstance().ActivityMainContext,ContentDetailsActivity.class);
        startActivity(intent);
    }

    public void initializeItemsInView(){
        mainRecyclerView=(RecyclerView)findViewById(R.id.mainRecyclerView);
    }

    public void setSettingParameter(){
        staticParameters.ThemCode=settingsManager.getThemCode();
        staticParameters.TextSize=settingsManager.getTextSize();
        staticParameters.TextFont=settingsManager.getTextFont();

    }

    public void createGridViewWithRecyclerView(int gridColumnNumber){
        GridLayoutManager mLayoutManager;
        mLayoutManager = new GridLayoutManager(StaticParameters.getInstance().ActivityMainContext,gridColumnNumber);
        mainRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void setAdapterToMainMenuRecycler(){
        adapter=new MainActivityRecyclerAdapter(createMainMenu(),StaticParameters.getInstance().ActivityMainContext);
        mainRecyclerView.setAdapter(adapter);
    }

    public ArrayList<MainMenuItem> createMainMenu(){
       ArrayList<MainMenuItem> items=new ArrayList<>();
        for (int i=0;i<15;i++){
            MainMenuItem item=new MainMenuItem();
            item.setTitle("title"+i);
            item.setIconId(R.mipmap.ic_launcher);
            items.add(item);
        }
        return items;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_share:
                shareText("share app link in mobile market");
                return true;
            case R.id.action_settings:
                openSettingsActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openSettingsActivity(){
        Intent intent=new Intent(StaticParameters.getInstance().ActivityMainContext,SettingsActivity.class);
        startActivity(intent);
    }

    public void shareText(String shareText){

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
