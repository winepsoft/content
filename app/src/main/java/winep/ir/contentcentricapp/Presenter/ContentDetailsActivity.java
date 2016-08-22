package winep.ir.contentcentricapp.Presenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import winep.ir.contentcentricapp.DataModel.MainMenuItem;
import winep.ir.contentcentricapp.R;
import winep.ir.contentcentricapp.Utility.ChangeThem;
import winep.ir.contentcentricapp.Utility.RecyclerDividerItemDecoration;
import winep.ir.contentcentricapp.Utility.RecyclerItemClickListener;
import winep.ir.contentcentricapp.Utility.StaticParameters;

public class ContentDetailsActivity extends AppCompatActivity{

        private RecyclerView contentDetailRecyclerView;
        private ContentDetailsRecyclerAdapter adapter;

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            ChangeThem.onActivityCreateSetTheme(this);
            setContentView(R.layout.activity_content_details);
            StaticParameters.getInstance().ContentDetailsContext = this;
            initializeItemsInView();
            createListViewInRecyclerView();
            setAdapterToMainMenuRecycler();

            contentDetailRecyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(StaticParameters.getInstance().ActivityMainContext, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            openContentActivity(position);
                        }
                    })
            );
        }

    public void initializeItemsInView(){
        contentDetailRecyclerView=(RecyclerView)findViewById(R.id.contentDetailRecyclerView);
    }

    public void createListViewInRecyclerView(){
        contentDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contentDetailRecyclerView.addItemDecoration(new RecyclerDividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    }

    public void setAdapterToMainMenuRecycler(){
        adapter=new ContentDetailsRecyclerAdapter(createMainMenu());
        contentDetailRecyclerView.setAdapter(adapter);
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

    public void openContentActivity(int position){
        Intent intent=new Intent(StaticParameters.getInstance().ContentDetailsContext,ContentActivity.class);
        if ((position%2)==0)
           intent.putExtra("imageStatus",true);
        else
            intent.putExtra("imageStatus",false);
        startActivity(intent);
    }
}
