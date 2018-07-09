package com.ocean.user.rx;

/**
 * Created by xieyuhai on 2018/7/8.
 */


import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * use build model
 */

public class RxRestClientBuilder {


    private HashMap<String, Object> mParams;
    private String mUrl;
    private RequestBody mBody;


    //上传下载
    private File mFile;

    RxRestClientBuilder() {
    }

    public final RxRestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RxRestClientBuilder param(HashMap<String, Object> params) {
        this.mParams = params;
        return this;
    }


    public final RxRestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }


    //upload
    public final RxRestClientBuilder file(File file) {
        mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String file) {
        mFile = new File(file);
        return this;
    }



    public final RxRestClient build() {
        return new RxRestClient(mParams, mUrl, mBody, mFile);
    }

}
