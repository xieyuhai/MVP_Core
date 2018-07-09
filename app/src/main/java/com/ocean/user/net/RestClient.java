package com.ocean.user.net;

import com.ocean.user.net.callback.IError;
import com.ocean.user.net.callback.IFailure;
import com.ocean.user.net.callback.IRequest;
import com.ocean.user.net.callback.ISuccess;
import com.ocean.user.net.callback.ReuqestCallbacks;
import com.ocean.user.net.download.DownloadHandler;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by xieyuhai on 2018/7/8.
 */

public class RestClient {

    private final HashMap<String, Object> PARAMS;
    private final String URL;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;


    private final File FILE;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String FILENAME;

//    public RestClient(HashMap<String, Object> PARAMS,
//                      String URL, IRequest REQUEST,
//                      ISuccess SUCCESS, IFailure FAILURE,
//                      IError ERROR,
//                      RequestBody BODY) {
//        this.PARAMS = PARAMS;
//        this.URL = URL;
//        this.REQUEST = REQUEST;
//        this.SUCCESS = SUCCESS;
//        this.FAILURE = FAILURE;
//        this.ERROR = ERROR;
//        this.BODY = BODY;
//    }


    public RestClient(HashMap<String, Object> PARAMS, String URL, IRequest REQUEST, ISuccess SUCCESS, IFailure FAILURE,
                      IError ERROR, RequestBody BODY, File FILE, String DOWNLOAD_DIR, String EXTENSION, String FILENAME) {
        this.PARAMS = PARAMS;
        this.URL = URL;
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.FAILURE = FAILURE;
        this.ERROR = ERROR;
        this.BODY = BODY;
        this.FILE = FILE;
        this.DOWNLOAD_DIR = DOWNLOAD_DIR;
        this.EXTENSION = EXTENSION;
        this.FILENAME = FILENAME;
    }

    public static RestClientBuilder create() {
        return new RestClientBuilder();
    }


    //网络操作
    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();

        Call<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
//                final RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final RequestBody requestBody = RequestBody.create(MultipartBody.FORM, FILE);
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);//.createFormData("file",requestBody);
                call = service.upload(URL, part);
                break;
        }
        if (call != null) {
            call.enqueue(getRequestCallback());
        }
    }

    private Callback<String> getRequestCallback() {
        return new ReuqestCallbacks(REQUEST, SUCCESS, FAILURE, ERROR);
    }

    //各种请求设置
    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        request(HttpMethod.POST);
    }

    public final void put() {
        request(HttpMethod.PUT);
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void post_raw() {
        request(HttpMethod.POST_RAW);
    }

    public final void put_raw() {
        request(HttpMethod.PUT_RAW);
    }


    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
//        request(HttpMethod.DOWNLOAD);

        new DownloadHandler(PARAMS, URL, REQUEST, SUCCESS, FAILURE, ERROR, BODY, DOWNLOAD_DIR, EXTENSION, FILENAME).handlerDownload();
    }

}
