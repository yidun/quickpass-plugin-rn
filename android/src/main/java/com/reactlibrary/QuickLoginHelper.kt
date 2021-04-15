package com.reactlibrary;

import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.WritableNativeMap
import com.netease.nis.quicklogin.QuickLogin
import com.netease.nis.quicklogin.listener.QuickLoginPreMobileListener
import com.netease.nis.quicklogin.listener.QuickLoginTokenListener

/**
 * Created by hzhuqi on 2020/9/9
 */
class QuickLoginHelper {
    var quickLogin: QuickLogin? = null
    var context: ReactApplicationContext? = null

    constructor(context: ReactApplicationContext) {
        this.context = context
    }


    fun init(businessId: String) {
        quickLogin = QuickLogin.getInstance(context, businessId)
    }

    fun setUiConfig(uiConfig: Map<String, Any>?) {
        quickLogin?.setUnifyUiConfig(UiConfigParser.getUiConfig(context!!, uiConfig!!))
    }

    fun prefetchNumber(callback: Callback) {
        var map = WritableNativeMap()
        quickLogin?.prefetchMobileNumber(object : QuickLoginPreMobileListener() {
            override fun onGetMobileNumberSuccess(YDToken: String?, mobileNumber: String?) {
                map.putString("token", YDToken)
                callback.invoke(true, map)
            }

            override fun onGetMobileNumberError(YDToken: String?, msg: String?) {
                map.putString("token", YDToken)
                map.putString("desc", msg)
                callback.invoke(false, map)
            }
        })
    }

    fun onepass(callback: Callback) {
        var map = WritableNativeMap()
        quickLogin?.onePass(object : QuickLoginTokenListener() {
            override fun onGetTokenSuccess(YDToken: String?, accessCode: String?) {
                map.putString("token", YDToken)
                map.putString("accessToken", accessCode)
                map.putString("desc", "预取号成功")
                callback.invoke(true, map)
            }

            override fun onGetTokenError(YDToken: String?, msg: String?) {
                map.putString("token", YDToken)
                map.putString("desc", "预取号失败" + msg)
                callback.invoke(false, map)
            }
        })
    }

    fun quitActivity() {
        quickLogin?.quitActivity()
    }


}
