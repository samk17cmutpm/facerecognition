package neolab.vn.facerecognition;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by sam_nguyen on 12/14/17.
 */

public interface Service {

    @Multipart
    @POST(EndPoint.UPLOAD)
    Call<UploadEntity> uploadImage(@Part MultipartBody.Part image);

}
