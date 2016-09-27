package winep.ir.contentcentricapp.Utility;

import android.os.Environment;

/**
 * Created by ShaisteS on 9/27/2016.
 */
public class Utility {

    private static Utility utilityMethods = new Utility();

    public static Utility getInstance() {
        if (utilityMethods != null) {
            return utilityMethods;
        } else return new Utility();
    }

    public String getAdreessSaveFile(){
        String root = Environment.getExternalStorageDirectory().toString();
        String path=root+"/Download/";
        return path;
    }
}
