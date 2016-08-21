package winep.ir.contentcentricapp.Utility;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ShaisteS on 8/20/2016.
 */
public class ChangeFont {

    private static ChangeFont changeFont = new ChangeFont();

    private ChangeFont() {
    }

    public static ChangeFont getInstance() {
        return changeFont;
    }

    public void overrideFonts(final Context context, final View v, String fontName) {
        Typeface typeFace = Typeface.createFromAsset(context.getAssets()
                , "fonts/"+fontName+".ttf");
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child,fontName);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(typeFace);
            } else if (v instanceof Button) {
                ((Button) v).setTypeface(typeFace);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // ignore
        }
    }
}
