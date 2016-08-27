package winep.ir.contentcentricapp.Presenter.Observer;

import java.util.ArrayList;

/**
 * Created by ShaisteS on 8/27/2016.
 */
public class ObserverAudioPlayer {

    private static boolean audioPlayerStatuse;
    private static ArrayList<ObserverAudioPlayerListener> audioPlayerListenerArrayList=new ArrayList<>();

    public static boolean isAudioPlayerStatuse() {
        return audioPlayerStatuse;
    }

    public static void setAudioPlayerStatuse(boolean audioPlayerStatus) {
        ObserverAudioPlayer.audioPlayerStatuse = audioPlayerStatus;
        for (ObserverAudioPlayerListener status : audioPlayerListenerArrayList) {
            status.changeAudioPlayerStatus();
        }
    }

    public static void ObserverChangeAudioPlayerStatus(ObserverAudioPlayerListener change){
        audioPlayerListenerArrayList.add(change);
    }
}
