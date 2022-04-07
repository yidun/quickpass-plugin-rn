
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;

import java.util.HashMap;

public class RNQuickloginPluginModule extends ReactContextBaseJavaModule {

    private final QuickLoginHelper quickLoginHelper;

    public RNQuickloginPluginModule(ReactApplicationContext reactContext) {
        super(reactContext);
        quickLoginHelper = new QuickLoginHelper(reactContext);
    }

    @Override
    public String getName() {
        return "QuickLoginPlugin";
    }

    @ReactMethod
    public void initQuickLogin(String businessId) {
        quickLoginHelper.init(businessId);
    }

    @ReactMethod
    public void setUiConfig(ReadableMap uiConfig, Callback callback) {
        quickLoginHelper.setUiConfig(Utils.toHashMap(uiConfig), callback);
    }

    @ReactMethod
    public void prefetchNumber(Callback callback) {
        quickLoginHelper.prefetchNumber(callback);
    }

    @ReactMethod
    public void login(Callback callback) {
        quickLoginHelper.onePass(callback);
    }

    @ReactMethod
    public void closeAuthController() {
        quickLoginHelper.quitActivity();
    }

    @ReactMethod
    public void checkVerifyEnable(Callback callback) {
        quickLoginHelper.checkVerifyEnable(callback);
    }

    public void verifyPhoneNumber(String phoneNumber, Callback callback) {
        quickLoginHelper.verifyPhoneNumber(phoneNumber, callback);
    }
}