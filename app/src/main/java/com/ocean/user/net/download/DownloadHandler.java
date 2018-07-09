package com.ocean.user.net.download;

import android.os.AsyncTask;

import com.ocean.user.net.RestClient;
import com.ocean.user.net.RestCreator;
import com.ocean.user.net.callback.IError;
import com.ocean.user.net.callback.IFailure;
import com.ocean.user.net.callback.IRequest;
import com.ocean.user.net.callback.ISuccess;

import java.io.File;
import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xieyuhai on 2018/7/8.
 */

public class DownloadHandler {


    private final HashMap<String, Object> PARAMS;
    private final String URL;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;


    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String FILENAME;


    public DownloadHandler(HashMap<String, Object> PARAMS, String URL, IRequest REQUEST, ISuccess SUCCESS, IFailure FAILURE, IError ERROR, RequestBody BODY, String DOWNLOAD_DIR, String EXTENSION, String FILENAME) {
        this.PARAMS = PARAMS;
        this.URL = URL;
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.FAILURE = FAILURE;
        this.ERROR = ERROR;
        this.BODY = BODY;
        this.DOWNLOAD_DIR = DOWNLOAD_DIR;
        this.EXTENSION = EXTENSION;
        this.FILENAME = FILENAME;


    }

    public final void handlerDownload() {
        RestCreator.getRestService().download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
//开始下载文件，开启异步任务
                            SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                    DOWNLOAD_DIR,
                                    EXTENSION,
                                    response.body(),
                                    FILENAME);

                            //如果下载完成
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        } else {
                            if (ERROR != null) {
                                ERROR.onError(response.code(), response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
