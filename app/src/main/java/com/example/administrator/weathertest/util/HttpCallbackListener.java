package com.example.administrator.weathertest.util;

/**
 * Created by Administrator on 2016/9/12.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);

}
