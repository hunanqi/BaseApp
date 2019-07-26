package utils;

import android.content.Context;
import android.content.SharedPreferences;

import base.LibApp;

/**
 * SharedPreferences工具类
 */
public class SharedPreferencesUtil {

    //存储的sharedpreferences文件名
    private static final String FILE_NAME = "zhangQian-Sp";

    /**
     * 保存数据到文件
     */
    public static void saveData(String key,Object data){

        String type = data.getClass().getSimpleName();
            SharedPreferences sharedPreferences = LibApp.getContext()
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if ("Integer".equals(type)){
            editor.putInt(key, (Integer)data);
        }else if ("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)data);
        }else if ("String".equals(type)){
            editor.putString(key, (String)data);
        }else if ("Float".equals(type)){
            editor.putFloat(key, (Float)data);
        }else if ("Long".equals(type)){
            editor.putLong(key, (Long)data);
        }

        editor.apply();
    }

    /**
     * 从文件中读取数据
     */
    public static Object getData(String key, Object defValue){

        String type = defValue.getClass().getSimpleName();
        SharedPreferences sharedPreferences = LibApp.getContext().getSharedPreferences
                (FILE_NAME, Context.MODE_PRIVATE);

        //defValue为为默认值，如果当前获取不到数据就返回它
        if ("Integer".equals(type)){
            return sharedPreferences.getInt(key, (Integer)defValue);
        }else if ("Boolean".equals(type)){
            return sharedPreferences.getBoolean(key, (Boolean)defValue);
        }else if ("String".equals(type)){
            return sharedPreferences.getString(key, (String)defValue);
        }else if ("Float".equals(type)){
            return sharedPreferences.getFloat(key, (Float)defValue);
        }else if ("Long".equals(type)){
            return sharedPreferences.getLong(key, (Long)defValue);
        }

        return null;
    }

    public static void clearData(){
        SharedPreferences sharedPreferences = LibApp.getContext()
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void clearDataByKey(String key){
        SharedPreferences sharedPreferences = LibApp.getContext()
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,"");
        editor.apply();
    }
}
