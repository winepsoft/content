package winep.ir.contentcentricapp.Presenter.DownloadFile;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

/**
 * Created by ShaisteS on 9/27/2016.
 */
public interface RetrofitInterface {

    @GET("download/Node-Android-Chat.zip")
    @Streaming
    Call<ResponseBody> downloadFile();
}
