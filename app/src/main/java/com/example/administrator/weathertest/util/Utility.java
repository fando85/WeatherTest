package com.example.administrator.weathertest.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.example.administrator.weathertest.db.WeatherDB;
import com.example.administrator.weathertest.model.City;
import com.example.administrator.weathertest.model.County;
import com.example.administrator.weathertest.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016/9/12.
 */
public class Utility {
    public synchronized static boolean handleProvincesResponse(WeatherDB weatherDB,String response){
        if (!TextUtils.isEmpty(response)){
            Province province=new Province();
            String[] allProvinces=response.split(",");
            if (allProvinces!=null&&allProvinces.length>0){
                for (String p:allProvinces){
                    String[] array=p.split("\\|");
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    weatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    public synchronized static boolean handleCitiesResponse(WeatherDB weatherDB,String response,int provinceId){
        if (!TextUtils.isEmpty(response)){
            City city=new City();
            String[] allCities=response.split(",");
            if (allCities!=null&&allCities.length>0){
                for (String p:allCities){
                    String[] array=p.split("\\|");
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    weatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }
    public synchronized static boolean handleCountiesResponse(WeatherDB weatherDB,String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            County county=new County();
            String[] allCounties=response.split(",");
            if (allCounties!=null&&allCounties.length>0){
                for (String p:allCounties){
                    String[] array=p.split("\\|");
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    weatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
    public static void handleWeatherResponse(Context context,String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONObject weatherinfo=jsonObject.getJSONObject("weatherinfo");
            String cityName=weatherinfo.getString("city");
            String weatherCode=weatherinfo.getString("cityid");
            String temp1=weatherinfo.getString("temp1");
            String temp2=weatherinfo.getString("temp2");
            String weatherDesp=weatherinfo.getString("weather");
            String publishTime=weatherinfo.getString("ptime");
            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public static void saveWeatherInfo(Context context,String cityName,String weatherCode,String temp1,String temp2,String weatherDesp,String publishTime){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name",cityName);
        editor.putString("weather_code",weatherCode);
        editor.putString("temp1",temp1);
        editor.putString("temp2",temp2);
        editor.putString("weather_desp",weatherDesp);
        editor.putString("publish_time",publishTime);
        editor.putString("current_date",simpleDateFormat.format(new Date()));
        editor.commit();
    }





















}
