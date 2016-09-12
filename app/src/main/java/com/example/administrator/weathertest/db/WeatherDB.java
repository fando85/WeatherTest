package com.example.administrator.weathertest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.weathertest.model.City;
import com.example.administrator.weathertest.model.County;
import com.example.administrator.weathertest.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */
public class WeatherDB {
    public static final String DB_NAME="weather";
    public static final int version=1;
    private static WeatherDB weatherDB;
    private SQLiteDatabase db;
    private WeatherDB(Context context){
        WeatherOpenHelper dbHelper=new WeatherOpenHelper(context,DB_NAME,null,version);
        db=dbHelper.getWritableDatabase();
    }
    public synchronized static WeatherDB getInstance(Context context){
        if (weatherDB==null){
            weatherDB=new WeatherDB(context);
        }
        return weatherDB;
    }
    public void saveProvince(Province province){
        if (province!=null){
            ContentValues values=new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("province",null,values);
        }
    }
    public List<Province> loadProvinces(){
        List<Province> provinceList=new ArrayList<Province>();
        Cursor cursor=db.query("province",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                Province province=new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                provinceList.add(province);
            }while (cursor.moveToNext());
        }
        return provinceList;
    }
    public void saveCity(City city){
        if (city!=null){
            ContentValues values=new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("city",null,values);
        }
    }
    public List<City> loadCities(int provinceId) {
        List<City> cityList = new ArrayList<City>();
        Cursor cursor = db.query("city", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
        while (cursor.moveToNext()) {
            City city = new City();
            city.setId(cursor.getInt(cursor.getColumnIndex("id")));
            city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
            city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
            city.setProvinceId(provinceId);
            cityList.add(city);
        }
        return cityList;
    }
    public void saveCounty(County county){
        if (county!=null){
            ContentValues values=new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_id",county.getCityId());
            db.insert("county",null,values);
        }
    }
    public List<County> loadCounties(int cityId){
        List<County> countyList=new ArrayList<County>();
        Cursor cursor=db.query("county",null,"city_id=?",new String[]{String.valueOf(cityId)},null,null,null);
        while (cursor.moveToNext()){
            County county=new County();
            county.setId(cursor.getInt(cursor.getColumnIndex("id")));
            county.setCityId(cityId);
            county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
            county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
            countyList.add(county);
        }
        return countyList;
    }
}
