package winep.ir.contentcentricapp.Utility;

import android.app.Activity;
import android.content.Intent;

import winep.ir.contentcentricapp.R;

/**
 * Created by ShaisteS on 8/20/2016.
 */
public class ChangeThem {

    private static int sTheme;
    public final static int THEME_DEFAULT = 100;
    public final static int THEME_DEFAULT_Small_Size=110;
    public final static int THEME_DEFAULT_Meduim_Size=120;
    public final static int THEME_DEFAULT_large_Size=130;
     public final static int THEME_WHITE = 2;
    public final static int THEME_BLUE = 3;
    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }
    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.FirstTheme);
                break;
            case THEME_WHITE:
                activity.setTheme(R.style.SecondTheme);
                break;
            case THEME_BLUE:
                activity.setTheme(R.style.ThirdTheme);
                break;
            case THEME_DEFAULT_Meduim_Size:
                activity.setTheme(R.style.FirstTheme_MeduimTextSize);
                break;
            case THEME_DEFAULT_large_Size:
                activity.setTheme(R.style.FirstTheme_LargeTextSize);
                break;
        }
    }
}
