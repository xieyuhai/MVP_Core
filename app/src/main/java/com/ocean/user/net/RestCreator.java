package com.ocean.user.net;

import com.ocean.user.app.ConfigKeys;
import com.ocean.user.app.ProjectInit;
import com.ocean.user.rx.RxRestService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by xieyuhai on 2018/7/8.
 */

public class RestCreator {


    /**
     * 产生一个全局的Retrofit
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL = ProjectInit.getConfiguration(ConfigKeys.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())    //添加数据转换器
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //添加rxjava2适配器
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .build();
    }

    //产生一个全局的OkHttpClient
    private static final class OKHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder().connectTimeout(TIME_OUT, TimeUnit.SECONDS).build();
    }


    //产生一个接口服务对象
    private static final class RestServiceHolder {
        private static final RestService REST_CREATOR = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    //产生一个接口服务对象
    private static final class RxRestServiceHolder {
        private static final RxRestService REST_CREATOR = RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }

    /**
     * 获得对象
     *
     * @return
     */
    public static RestService getRestService() {
        return RestServiceHolder.REST_CREATOR;
    }

    /**
     * 获得对象Rx
     *
     * @return
     */
    public static RxRestService getRxRestService() {
        return RxRestServiceHolder.REST_CREATOR;
    }
}
