package neolab.vn.facerecognition;

/**
 * Created by sam_nguyen on 12/15/17.
 */

public class BaseUrl {

    private String url = "http://192.168.1.13:5000/";

    private static final BaseUrl ourInstance = new BaseUrl();

    public static BaseUrl getInstance() {
        return ourInstance;
    }

    private BaseUrl() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
