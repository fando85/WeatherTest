package com.example.administrator.weathertest.util;

import android.text.TextUtils;

import com.example.administrator.weathertest.db.WeatherDB;
import com.example.administrator.weathertest.model.City;
import com.example.administrator.weathertest.model.County;
import com.example.administrator.weathertest.model.Province;

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





















}
