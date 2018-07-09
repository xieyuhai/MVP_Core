package com.ocean.user;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ocean.user.net.RestClient;
import com.ocean.user.net.callback.IError;
import com.ocean.user.net.callback.IFailure;
import com.ocean.user.net.callback.ISuccess;
import com.ocean.user.rx.RxRestClient;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 文件下载
 * http://xfzmd.top/upload/o_1c107hvoq1alu1krs5arbtu131gd.jpg
 * 文件上传
 * http://xfzmd.top/jw/UploadServlet
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RestClient.create().param(new HashMap<String, Object>())
//                .url("")
//                .request(new IRequest() {
//                    @Override
//                    public void onRequestStart() {
//
//                    }
//
//                    @Override
//                    public void onRequestEnd() {
//
//                    }
//                }).success(new ISuccess() {
//            @Override
//            public void onSuccess(String response) {
//
//            }
//        });

    }

    //登陆
    public void click(View view) {
        HashMap params = new HashMap();
        params.put("username", "admin");
        params.put("password", "123456");

        RestClient.create().url("login/info").param(params).error(new IError() {
            @Override
            public void onError(int code, String msg) {
                Toast.makeText(MainActivity.this, msg + "false", Toast.LENGTH_SHORT).show();
            }
        }).fail(new IFailure() {
            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this, "false", Toast.LENGTH_SHORT).show();
            }
        }).success(new ISuccess() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(MainActivity.this, "" + response, Toast.LENGTH_SHORT).show();
            }
        }).build().get();
    }

    public void upload(View view) {
        HashMap params = new HashMap();
        params.put("username", "admin");
        params.put("password", "123456");

        RestClient.create().url("jw/UploadServlet").param(params).file(Environment.getExternalStorageDirectory() + "/c.png").success(new ISuccess() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(MainActivity.this, "" + response, Toast.LENGTH_SHORT).show();
            }
        }).build().upload();
    }

    public void download(View view) {
        HashMap params = new HashMap();
        params.put("username", "admin");
        params.put("password", "123456");

        RestClient.create().url("upload/o_1c107hvoq1alu1krs5arbtu131gd.jpg")
                .param(params)
                .dir("/mnt/sdcard") //基于手机目录下的目录
                .extension("jpg")
                //.filename("file.jpg")
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(MainActivity.this, msg + ":onError", Toast.LENGTH_SHORT).show();
                    }
                }).fail(new IFailure() {
            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this, "onFailure:", Toast.LENGTH_SHORT).show();
            }
        })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.e("TAG", "onSuccess: " + response);
                        Toast.makeText(MainActivity.this, "onSuccess:" + response, Toast.LENGTH_SHORT).show();
                    }
                }).build().download();
    }

    public void rxdownload(View view) {
        HashMap params = new HashMap();
        params.put("username", "admin");
        params.put("pwd", "123456");


        RxRestClient.create()
                .url("login/info")
                .param(params)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: " + d.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "onNext: " + s);
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: ");
                    }
                });
    }
}
