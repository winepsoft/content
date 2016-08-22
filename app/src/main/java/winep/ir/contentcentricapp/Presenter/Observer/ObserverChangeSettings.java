package winep.ir.contentcentricapp.Presenter.Observer;

import java.util.ArrayList;

/**
 * Created by ShaisteS on 8/22/2016.
 */
public class ObserverChangeSettings {

    private static Boolean changeSettingsStatus;
    private final static ArrayList<ObserverChangeSettingsListener> arrayOfObserverChangeSettingsListener=new ArrayList<>();

    public static Boolean isChangeSettingsStatus() {
        return changeSettingsStatus;
    }

    public static void setChangeSettingsStatus(Boolean changeSettings) {
        ObserverChangeSettings.changeSettingsStatus = changeSettings;
        for (ObserverChangeSettingsListener status : arrayOfObserverChangeSettingsListener) {
            status.changeSettings();
        }
    }

    public static void ObserverChangeSettingsListener(ObserverChangeSettingsListener change){
        arrayOfObserverChangeSettingsListener.add(change);
    }
}
