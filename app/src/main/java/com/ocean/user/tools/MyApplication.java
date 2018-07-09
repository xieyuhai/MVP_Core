package com.ocean.user.tools;

import android.app.Application;

import com.ocean.user.app.ProjectInit;

import java.util.ArrayList;

import okhttp3.Interceptor;

/**
 * Created by xieyuhai on 2018/7/8.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ProjectInit.init(this)
                .withApiHost("http://xfzmd.top/")
//        http://xfzmd.top/upload/o_1c107hvoq1alu1krs5arbtu131gd.jpg
                .withInterceptors(new ArrayList<Interceptor>())
                .configure();
    }
}
