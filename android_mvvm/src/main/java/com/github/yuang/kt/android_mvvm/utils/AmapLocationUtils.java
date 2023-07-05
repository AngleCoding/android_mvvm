package com.github.yuang.kt.android_mvvm.utils;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.LogUtils;

/**
 * 作者:admin
 * 描述:
 * 时间:2020/6/4
 * @author admin
 */
public class AmapLocationUtils {
  private AMapLocationClient locationClient = null;
  private AMapLocationClientOption locationOption = null;
  private Callback mCallback;

  /**
   * 定位监听
   */
  private AMapLocationListener locationListener = location -> {
    if (null != location) {
      //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
      if (location.getErrorCode() == 0) {
        if (mCallback != null) {
          mCallback.onSuccess(location);
        }
      } else {
        String message = "定位失败" + "\n"
            + "错误码:" + location.getErrorCode() + "\n"
            + "错误信息:" + location.getErrorInfo() + "\n"
            + "错误描述:" + location.getLocationDetail() + "\n";
        LogUtils.i(message);
        if (mCallback != null) {
          mCallback.onFailure(message);
        }
      }
    } else {
      if (mCallback != null) {
        mCallback.onFailure("定位失败,loc is null");
      }
      LogUtils.e("定位失败，loc is null");
    }
  };

  private AmapLocationUtils() {
  }

  public static AmapLocationUtils getInstance() {
    return Holder.INSTANCE;
  }

  /**
   * 初始化定位
   */
  public void initLocation(Context context, Callback callback) {
    this.mCallback = callback;
    //初始化client
    try {
      locationClient = new AMapLocationClient(context);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    locationOption = getDefaultOption();
    //设置定位参数
    locationClient.setLocationOption(locationOption);
    // 设置定位监听
    locationClient.setLocationListener(locationListener);
  }

  /**
   * 默认的定位参数
   */
  private AMapLocationClientOption getDefaultOption() {
    AMapLocationClientOption mOption = new AMapLocationClientOption();
    mOption.setLocationMode(
        AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
    mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
    mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
    mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
    mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
    mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
    mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
    AMapLocationClientOption.setLocationProtocol(
        AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
    mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
    //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
    mOption.setWifiScan(true);
    mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
    return mOption;
  }

  /**
   * 开始定位
   */
  public void startLocation() {
    if (locationClient == null) {
      return;
    }
    // 设置定位参数
    locationClient.setLocationOption(locationOption);
    // 启动定位
    locationClient.startLocation();
  }

  /**
   * 停止定位
   */
  public void stopLocation() {
    if (locationClient == null) {
      return;
    }
    // 停止定位
    locationClient.stopLocation();
  }

  /**
   * 销毁定位
   */
  public void destroyLocation() {
    if (null != locationClient) {
      /*
       * 如果AMapLocationClient是在当前Activity实例化的，
       * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
       */
      locationClient.onDestroy();
      locationClient = null;
      locationOption = null;
    }
  }

  public void resetOption(AMapLocationClientOption option) {
    this.locationOption = option;
  }

  public interface Callback {
    void onSuccess(AMapLocation location);

    void onFailure(String message);
  }

  private static class Holder {
    private static final AmapLocationUtils INSTANCE = new AmapLocationUtils();
  }
}
