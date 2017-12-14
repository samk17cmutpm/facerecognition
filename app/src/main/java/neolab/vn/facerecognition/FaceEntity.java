package neolab.vn.facerecognition;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sam_nguyen on 12/14/17.
 */

public class FaceEntity {

    @SerializedName("confident")
    public Double confident;

    @SerializedName("person")
    public String person;

    @SerializedName("x")
    public int x;

    @SerializedName("y")
    public int y;

}
