package winep.ir.contentcentricapp.Presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import winep.ir.contentcentricapp.R;
import winep.ir.contentcentricapp.Utility.ChangeThem;

public class SettingsActivity extends AppCompatActivity {


    private Activity thisActivity;
    private RadioGroup rgThemColor;
    private RadioGroup rgTextsize;
    private RadioGroup rgTextFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangeThem.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_settings);
        thisActivity=this;
        initializeItemsInView();

        rgThemColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.rbGreen:
                        ChangeThem.changeToTheme(thisActivity, ChangeThem.THEME_Green);
                        break;
                    case R.id.rbBlue:
                        ChangeThem.changeToTheme(thisActivity, ChangeThem.THEME_BLUE);
                        break;
                    case R.id.rbViolet:
                        ChangeThem.changeToTheme(thisActivity, ChangeThem.THEME_VIOLET);
                        break;
                }

            }
        });
    }

    private void initializeItemsInView(){
        rgThemColor=(RadioGroup)findViewById(R.id.rgColor);
        rgTextsize=(RadioGroup)findViewById(R.id.rgTextSize);
        rgTextFont=(RadioGroup)findViewById(R.id.rgtextFont);
    }
}
