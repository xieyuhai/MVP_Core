package com.ocean.user.app;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by xieyuhai on 2018/7/8.
 */

public class Configurator {

    private static final HashMap<Object, Object> CONFIGS = new HashMap<>();


    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();


    //单例   java兵法实战中推的方法
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private Configurator() {
        CONFIGS.put(ConfigKeys.CONFIG_READY.name(), false);
    }

    final HashMap<Object, Object> getConfigs() {
        return CONFIGS;
    }

    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = CONFIGS.get(key);

        if (value == null) {
            throw new NullPointerException(key.toString());
        }
        return (T) CONFIGS.get(key);
    }

    //配置主机APIHOST
    public final Configurator withApiHost(String host) {
        CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }


    //配置拦截器
    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        CONFIGS.put(ConfigKeys.INTERCEPTOR, interceptors);
        return this;
    }

    //检测配置是否完成
    private void checkConfiguration() {
        final boolean isReady = (boolean) CONFIGS.get(ConfigKeys.CONFIG_READY.name());

        if (!isReady) {
            throw new RuntimeException("Configuration not ready");
        }
    }

    //设置配置完成
    public final void configure() {
        CONFIGS.put(ConfigKeys.CONFIG_READY.name(), true);
    }
}
