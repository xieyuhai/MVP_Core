package com.ocean.user.net;

/**
 * Created by xieyuhai on 2018/7/8.
 */

import com.ocean.user.net.callback.IError;
import com.ocean.user.net.callback.IFailure;
import com.ocean.user.net.callback.IRequest;
import com.ocean.user.net.callback.ISuccess;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * use build model
 */

public class RestClientBuilder {


    private HashMap<String, Object> mParams;
    private String mUrl;
    private IRequest mRequest;
    private ISuccess mSuccess;
    private IFailure mFailure;
    private IError mError;
    private RequestBody mBody;


    //上传下载
    private File mFile;
    private String mDownloadDir;
    private String mExtension;
    private String mFilename;

    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder param(HashMap<String, Object> params) {
        this.mParams = params;
        return this;
    }

    public final RestClientBuilder request(IRequest request) {
        this.mRequest = request;
        return this;
    }

    public final RestClientBuilder success(ISuccess url) {
        this.mSuccess = url;
        return this;
    }

    public final RestClientBuilder fail(IFailure url) {
        this.mFailure = url;
        return this;
    }

    public final RestClientBuilder error(IError url) {
        this.mError = url;
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }


    //upload
    public final RestClientBuilder file(File file) {
        mFile = file;
        return this;
    }

    public final RestClientBuilder file(String file) {
        mFile = new File(file);
        return this;
    }

    //download
    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String dir) {
        this.mExtension = dir;
        return this;
    }

    public final RestClientBuilder filename(String dir) {
        this.mFilename = dir;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mParams, mUrl, mRequest, mSuccess, mFailure, mError, mBody, mFile, mDownloadDir, mExtension, mFilename);
    }

}
