package com.ocean.user.rx;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by xieyuhai on 2018/7/9.
 */

public interface RxRestService {


    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, Object> map);

    @FormUrlEncoded  //表单形式提交
    @POST
    Observable<String> post(@Url String url, @FieldMap Map<String, Object> map);

    @FormUrlEncoded  //表单形式提交
    @PUT
    Observable<String> put(@Url String url, @FieldMap Map<String, Object> map);

    @DELETE
    Observable<String> delete(@Url String url, @QueryMap Map<String, Object> map);

    //下载到内存，使用Streaming存入文件
    @Streaming
    @GET
    Observable<String> download(@Url String url, @QueryMap Map<String, Object> map);

    //上传
    @Multipart
    @POST
    Observable<String> upload(@Url String url, @Part MultipartBody.Part body);


    //原始数据；非表单方式提交的
    @POST
    Observable<String> postRaw(@Url String url, @Body RequestBody body);

    @PUT
    Observable<String> putRaw(@Url String url, @Body RequestBody body);

}
