package winep.ir.contentcentricapp.Presenter.Observer;

import java.util.ArrayList;

/**
 * Created by ShaisteS on 8/27/2016.
 */
public class ObserverVideoPlayer {
    private static boolean videoPlayerStatus;
    private static ArrayList<ObserverVideoPlayerListener> videoPlayerListenerArrayList=new ArrayList<>();

    public static boolean isVideoPlayerStatus() {
        return videoPlayerStatus;
    }

    public static void setVideoPlayerStatus(boolean videoPlayerStatus) {
        ObserverVideoPlayer.videoPlayerStatus = videoPlayerStatus;
        for (ObserverVideoPlayerListener status : videoPlayerListenerArrayList) {
            status.changeVideoPlayerStatus();
        }
    }

    public static void ObserverChangeVideoPlayerStatus(ObserverVideoPlayerListener change){
        videoPlayerListenerArrayList.add(change);
    }
}
