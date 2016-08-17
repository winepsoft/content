package winep.ir.contentcentricapp.Presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.util.ArrayList;

import winep.ir.contentcentricapp.DataModel.MainMenuItem;
import winep.ir.contentcentricapp.R;
import winep.ir.contentcentricapp.Utility.RecyclerItemClickListener;
import winep.ir.contentcentricapp.Utility.StaticParameters;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mainRecyclerView;
    private MainActivityRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StaticParameters.getInstance().ActivityMainContext=this;
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
        inflater.inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
