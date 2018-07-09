package com.ocean.user.rx;

import com.ocean.user.net.HttpMethod;
import com.ocean.user.net.RestCreator;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by xieyuhai on 2018/7/8.
 */

public class RxRestClient {

    private final HashMap<String, Object> PARAMS;
    private final String URL;
    private final RequestBody BODY;


    private final File FILE;


    public RxRestClient(HashMap<String, Object> PARAMS, String URL, RequestBody BODY, File FILE) {
        this.PARAMS = PARAMS;
        this.URL = URL;
        this.BODY = BODY;
        this.FILE = FILE;
    }

    public static RxRestClientBuilder create() {
        return new RxRestClientBuilder();
    }


    //网络操作
    private Observable<String> request(HttpMethod method) {
        final RxRestService service = RestCreator.getRxRestService();

        Observable<String> observable = null;


        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
//                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final RequestBody requestBody = RequestBody.create(MultipartBody.FORM, FILE);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);//.createFormData("file",requestBody);
                observable = service.upload(URL, part);
                break;
        }
        return observable;
    }

    //各种请求设置
    public final Observable<String> get() {
        return request(HttpMethod.GET);
    }

    public final Observable<String> post() {
        return request(HttpMethod.POST);
    }

    public final Observable<String> put() {
        return request(HttpMethod.PUT);
    }

    public final Observable<String> delete() {
        return request(HttpMethod.DELETE);
    }

    public final Observable<String> post_raw() {
        return request(HttpMethod.POST_RAW);
    }

    public final Observable<String> put_raw() {
        return request(HttpMethod.PUT_RAW);
    }


    public final Observable<String> upload() {
        return request(HttpMethod.UPLOAD);
    }

    public final Observable<String> download() {
//        request(HttpMethod.DOWNLOAD);
//        new DownloadHandler(PARAMS, URL, REQUEST, SUCCESS, FAILURE, ERROR, BODY, DOWNLOAD_DIR, EXTENSION, FILENAME).handlerDownload();
        return RestCreator.getRxRestService().download(URL, PARAMS);
    }

}
