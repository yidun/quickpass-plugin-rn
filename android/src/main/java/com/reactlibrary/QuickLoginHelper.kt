package com.reactlibrary;

import android.text.TextUtils
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.WritableNativeMap
import com.netease.nis.basesdk.Logger
import com.netease.nis.quicklogin.QuickLogin
import com.netease.nis.quicklogin.listener.QuickLoginPreMobileListener
import com.netease.nis.quicklogin.listener.QuickLoginTokenListener

/**
 * Created by hzhuqi on 2020/9/9
 */
class QuickLoginHelper(context: ReactApplicationContext) {
    private var quickLogin: QuickLogin? = null
    private var context: ReactApplicationContext? = context

    fun init(businessId: String) {
        if (TextUtils.isEmpty(businessId)) {
            Logger.e("业务id不允许为空")
            return
        }
        quickLogin = QuickLogin.getInstance()
        quickLogin?.init(context, businessId)
    }

    fun setUiConfig(uiConfig: Map<String, Any>, callback: Callback) {
        context?.let {
            try {
                quickLogin?.setUnifyUiConfig(UiConfigParser.getUiConfig(it, uiConfig))
                callback.invoke(true)
            } catch (e: Exception) {
                callback.invoke(false)
            }
        }
    }

    fun prefetchNumber(callback: Callback) {
        val map = WritableNativeMap()
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

    fun onePass(callback: Callback) {
        val map = WritableNativeMap()
        quickLogin?.onePass(object : QuickLoginTokenListener() {
            override fun onGetTokenSuccess(YDToken: String?, accessCode: String?) {
                map.putString("token", YDToken)
                map.putString("accessToken", accessCode)
                map.putString("desc", "取号成功")
                callback.invoke(true, map)
            }

            override fun onGetTokenError(YDToken: String?, msg: String?) {
                map.putString("token", YDToken)
                map.putString("desc", "取号失败$msg")
                callback.invoke(false, map)
            }
        })
    }

    fun quitActivity() {
        quickLogin?.quitActivity()
    }

    fun checkVerifyEnable(callback: Callback) {
        val type = quickLogin?.checkNetWork(context)
        //5是未知类型
        if (type != 5 && type != 4) {
            callback.invoke(true)
        } else {
            callback.invoke(false)
        }
    }

    fun verifyPhoneNumber(phoneNumber: String?, callback: Callback) {
        if (TextUtils.isEmpty(phoneNumber)) {
            Logger.e("手机号码不允许为空")
            return
        }
        val map = WritableNativeMap()
        quickLogin?.getToken(phoneNumber, object : QuickLoginTokenListener() {
            override fun onGetTokenSuccess(YDToken: String?, accessCode: String?) {
                map.putString("token", YDToken)
                map.putString("accessToken", accessCode)
                map.putString("desc", "本机校验成功")
                callback.invoke(true, map)
            }

            override fun onGetTokenError(YDToken: String?, msg: String?) {
                map.putString("token", YDToken)
                map.putString("desc", "本机校验失败$msg")
                callback.invoke(false, map)
            }

        })
    }
}
