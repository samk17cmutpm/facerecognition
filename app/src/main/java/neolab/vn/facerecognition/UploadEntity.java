package neolab.vn.facerecognition;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sam_nguyen on 12/14/17.
 */

public class UploadEntity {

    @SerializedName("face")
    public List<FaceEntity> faceEntities;

    @SerializedName("file")
    public String file;

}
