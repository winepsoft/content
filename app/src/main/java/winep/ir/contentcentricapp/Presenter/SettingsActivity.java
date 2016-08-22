package winep.ir.contentcentricapp.Presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import winep.ir.contentcentricapp.Presenter.Observer.ObserverChangeSettings;
import winep.ir.contentcentricapp.R;
import winep.ir.contentcentricapp.Utility.ChangeFont;
import winep.ir.contentcentricapp.Utility.ChangeThem;
import winep.ir.contentcentricapp.Utility.SettingsManager;
import winep.ir.contentcentricapp.Utility.StaticParameters;

public class SettingsActivity extends AppCompatActivity {


    private Activity thisActivity;
    private RadioGroup rgThemColor;
    private RadioGroup rgTextSize;
    private RadioGroup rgTextFont;
    private RadioButton rbGreen;
    private RadioButton rbBlue;
    private RadioButton rbViolet;
    private RadioButton rbSmallTextSize;
    private RadioButton rbMediumTextSize;
    private RadioButton rbLargeTextSize;
    private RadioButton rbTextFontNazanin;
    private RadioButton rbTextFontMitra;
    private ViewGroup viewGroup;
    private FloatingActionButton fabApplySettings;
    private StaticParameters staticParameters;
    private SettingsManager settingsManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChangeThem.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_settings);
        staticParameters=StaticParameters.getInstance();
        settingsManager=new SettingsManager(this);
        thisActivity=this;
        initializeItemsInView();
        setThemColorRadioButton();
        setTextSizeRadioButton();
        setTextFontRadioButton();


        rgThemColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.rbGreen:
                        staticParameters.ThemCode=100;
                        break;
                    case R.id.rbBlue:
                        staticParameters.ThemCode=200;
                        break;
                    case R.id.rbViolet:
                        staticParameters.ThemCode=300;
                        break;
                }
                applySettings(rgTextFont);
            }
        });

        rgTextSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.rbSmallSize:
                        staticParameters.TextSize=10;
                        break;
                    case R.id.rbMeduimSize:
                        staticParameters.TextSize=20;
                        break;
                    case R.id.rbLargeSize:
                        staticParameters.TextSize=30;
                       break;
                }
                applySettings(rgTextFont);
            }
        });

        rgTextFont.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.rbNazaninFont:
                        staticParameters.TextFont=1;
                        break;
                    case R.id.rbMitra:
                        staticParameters.TextFont=2;
                        break;
                }
                applySettings(rgTextFont);
            }
        });

        fabApplySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsManager.setThemCode(staticParameters.ThemCode);
                settingsManager.setTextSize(staticParameters.TextSize);
                settingsManager.setTextFont(staticParameters.TextFont);
                ObserverChangeSettings.setChangeSettingsStatus(true);
                finish();
            }
        });
    }

    private void applySettings(View view){
        if (staticParameters.TextFont==1)
            ChangeFont.getInstance().overrideFonts(getBaseContext(),view,"BNazanin");
        else if(staticParameters.TextFont==2)
            ChangeFont.getInstance().overrideFonts(getBaseContext(),view,"BMitra");
        ChangeThem.changeToTheme(thisActivity, staticParameters.ThemCode+staticParameters.TextSize);
    }

    private void initializeItemsInView(){
        viewGroup = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        rgThemColor=(RadioGroup)findViewById(R.id.rgColor);
        rgTextSize =(RadioGroup)findViewById(R.id.rgTextSize);
        rgTextFont=(RadioGroup)findViewById(R.id.rgtextFont);
        rbGreen=(RadioButton)findViewById(R.id.rbGreen);
        rbBlue=(RadioButton)findViewById(R.id.rbBlue);
        rbViolet=(RadioButton)findViewById(R.id.rbViolet);
        rbSmallTextSize=(RadioButton)findViewById(R.id.rbSmallSize);
        rbMediumTextSize =(RadioButton)findViewById(R.id.rbMeduimSize);
        rbLargeTextSize=(RadioButton) findViewById(R.id.rbLargeSize);
        rbTextFontNazanin=(RadioButton)findViewById(R.id.rbNazaninFont);
        rbTextFontMitra=(RadioButton)findViewById(R.id.rbMitra);
        fabApplySettings = (FloatingActionButton) findViewById(R.id.fabApplySettings);

    }

    private void setThemColorRadioButton(){
        if (staticParameters.ThemCode==100)
            rbGreen.setChecked(true);
        else if(staticParameters.ThemCode==200)
            rbBlue.setChecked(true);
        else if (staticParameters.ThemCode==300)
            rbViolet.setChecked(true);
    }

    private void setTextSizeRadioButton(){
        if (staticParameters.TextSize==10)
            rbSmallTextSize.setChecked(true);
        else if(staticParameters.TextSize==20)
            rbMediumTextSize.setChecked(true);
        else if (staticParameters.TextSize==30)
            rbLargeTextSize.setChecked(true);

    }

    private void setTextFontRadioButton(){
        if(staticParameters.TextFont==1)
            rbTextFontNazanin.setChecked(true);
        else if(staticParameters.TextFont==2)
                rbTextFontMitra.setChecked(true);
    }
}
