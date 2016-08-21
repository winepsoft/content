package winep.ir.contentcentricapp.Presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import winep.ir.contentcentricapp.R;
import winep.ir.contentcentricapp.Utility.ChangeFont;
import winep.ir.contentcentricapp.Utility.ChangeThem;

public class SettingsActivity extends AppCompatActivity {


    private Activity thisActivity;
    private RadioGroup rgThemColor;
    private RadioGroup rgTextsize;
    private RadioGroup rgTextFont;
    private ViewGroup viewGroup;

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

        rgTextsize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.rbSmallSize:
                        ChangeThem.changeToTheme(thisActivity, ChangeThem.THEME_Green_Small_Size);
                        break;
                    case R.id.rbMeduimSize:
                        ChangeThem.changeToTheme(thisActivity, ChangeThem.THEME_Green_Meduim_Size);
                        break;
                    case R.id.rbLargeSize:
                        ChangeThem.changeToTheme(thisActivity, ChangeThem.THEME_Green_large_Size);
                        break;
                }

            }
        });

        rgTextFont.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.rbNazaninFont:
                        ChangeFont.getInstance().overrideFonts(getBaseContext(),viewGroup,"BNazanin");
                        break;
                    case R.id.rbMitra:
                        ChangeFont.getInstance().overrideFonts(getBaseContext(),viewGroup,"BMitra");
                        break;
                }
            }
        });
    }

    private void initializeItemsInView(){
        viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        rgThemColor=(RadioGroup)findViewById(R.id.rgColor);
        rgTextsize=(RadioGroup)findViewById(R.id.rgTextSize);
        rgTextFont=(RadioGroup)findViewById(R.id.rgtextFont);
    }
}
