package winep.ir.contentcentricapp.Utility;

import android.app.Activity;
import android.content.Intent;

import winep.ir.contentcentricapp.R;

/**
 * Created by ShaisteS on 8/20/2016.
 */
public class ChangeThem {

    private static int sTheme;
    public final static int THEME_Green = 100;
    public final static int THEME_Green_Small_Size =110;
    public final static int THEME_Green_Meduim_Size =120;
    public final static int THEME_Green_large_Size =130;
    public final static int THEME_BLUE = 200;
    public final static int THEME_BLUE_Small_Size =210;
    public final static int THEME_BLUE_Meduim_Size =220;
    public final static int THEME_BLUE_large_Size =230;
    public final static int THEME_VIOLET = 300;
    public final static int THEME_VIOLET_Small_Size =310;
    public final static int THEME_VIOLET_Meduim_Size =320;
    public final static int THEME_VIOLET_large_Size =330;

    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme) {
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
            case THEME_Green:
                activity.setTheme(R.style.FirstTheme);
                break;
            case THEME_BLUE:
                activity.setTheme(R.style.SecondTheme);
                break;
            case THEME_VIOLET:
                activity.setTheme(R.style.ThirdTheme);
                break;
            case THEME_Green_Meduim_Size:
                activity.setTheme(R.style.FirstTheme_MeduimTextSize);
                break;
            case THEME_Green_large_Size:
                activity.setTheme(R.style.FirstTheme_LargeTextSize);
                break;
            case THEME_BLUE_Small_Size:
                activity.setTheme(R.style.SecondTheme_SmallTextSize);
                break;
            case THEME_BLUE_Meduim_Size:
                activity.setTheme(R.style.SecondTheme_MeduimTextSize);
                break;
            case THEME_BLUE_large_Size:
                activity.setTheme(R.style.SecondTheme_LargeTextSize);
                break;
            case THEME_VIOLET_Small_Size:
                activity.setTheme(R.style.ThirdTheme_SmallTextSize);
                break;
            case THEME_VIOLET_Meduim_Size:
                activity.setTheme(R.style.ThirdTheme_MeduimTextSize);
                break;
            case THEME_VIOLET_large_Size:
                activity.setTheme(R.style.ThirdTheme_LargeTextSize);
                break;
        }
    }
}
