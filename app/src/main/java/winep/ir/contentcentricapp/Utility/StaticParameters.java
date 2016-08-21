package winep.ir.contentcentricapp.Utility;

import android.content.Context;

/**
 * Created by ShaisteS on 8/6/2016.
 */
public class StaticParameters {

    private static StaticParameters staticParameters = new StaticParameters();

    public static StaticParameters getInstance() {
        if (staticParameters != null) {
            return staticParameters;
        } else return new StaticParameters();
    }

    public Context ActivityMainContext;
    public Context ContentDetailsContext;
    public Context ContentActivity;
    public int ActivityMainGridViewColumnNumber=3;
    public int DefualtThemCode=100;
    public int DefualtTextSizeCode=20;
    public int DefualtTextFontCode=1;
}
