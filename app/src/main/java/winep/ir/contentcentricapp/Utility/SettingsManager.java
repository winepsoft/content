package winep.ir.contentcentricapp.Utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ShaisteS on 8/21/2016.
 */
public class SettingsManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    private int PRIVATE_MODE = 0;
    private String PREFER_NAME = "SettingData";
    private String THEM_CODE = "themCode";
    private String TEXT_SIZE = "textSize";
    private String TEXT_FONT = "textFont";
    private StaticParameters staticParameters;

    public SettingsManager(Context context){
        this._context = context;
        staticParameters=StaticParameters.getInstance();
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setThemCode(int themCode){
        editor.putInt(THEM_CODE,themCode);
        editor.commit();
    }
    public int getThemCode(){
        return pref.getInt(THEM_CODE,staticParameters.DefualtThemCode);
    }

    public void setTextSize(int textSize){
        editor.putInt(TEXT_SIZE, textSize);
        editor.commit();
    }
    public int getTextSize(){
        return pref.getInt(TEXT_SIZE,staticParameters.DefualtTextSizeCode);
    }

    public void setTextFont(int textFont){
        editor.putInt(TEXT_FONT, textFont);
        editor.commit();
    }
    public int getTextFont(){
        return pref.getInt(TEXT_FONT,staticParameters.DefualtTextFontCode);
    }
}
