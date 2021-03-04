package com.reactlibrary;

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.WritableNativeMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.netease.nis.quicklogin.QuickLogin.TAG
import com.netease.nis.quicklogin.helper.UnifyUiConfig
import com.netease.nis.quicklogin.listener.ActivityLifecycleCallbacks

/**
 * Created by hzhuqi on 2020/11/2
 */
object UiConfigParser {
    private var statusBarColor = 0
    private var isStatusBarDarkColor = false
    private var navBackIcon: String? = null
    private var navBackIconWidth = 25
    private var navBackIconHeight = 25
    private var isHideBackIcon = false
    private var navHeight = 0
    private var navBackgroundColor = 0
    private var navTitle: String? = null
    private var navTitleSize = 0
    private var isNavTitleBold = false
    private var navTitleColor = 0
    private var isHideNav = false
    private var logoIconName: String? = null
    private var logoWidth = 0
    private var logoHeight = 0
    private var logoTopYOffset = 0
    private var logoBottomYOffset = 0
    private var logoXOffset = 0
    private var isHideLogo = false
    private var maskNumberColor = 0
    private var maskNumberSize = 0
    private var maskNumberDpSize = 0
    private var maskNumberTopYOffset = 0
    private var maskNumberBottomYOffset = 0
    private var maskNumberXOffset = 0
    private var sloganSize = 10
    private var sloganDpSize = 0
    private var sloganColor = Color.BLUE
    private var sloganTopYOffset = 0
    private var sloganBottomYOffset = 0
    private var sloganXOffset = 0
    private var loginBtnText: String? = "本机号码一键登录"
    private var loginBtnTextSize = 15
    private var loginBtnTextDpSize = 0
    private var loginBtnTextColor = -1
    private var loginBtnWidth = 0
    private var loginBtnHeight = 0
    private var loginBtnBackgroundRes: String? = null
    private var loginBtnTopYOffset = 0
    private var loginBtnBottomYOffset = 0
    private var loginBtnXOffset = 0
    private var privacyTextColor = Color.BLACK
    private var privacyProtocolColor = Color.GRAY
    private var privacySize = 0
    private var privacyDpSize = 0
    private var privacyTopYOffset = 0
    private var privacyBottomYOffset = 0
    private var privacyMarginLeft = 0
    private var privacyMarginRight = 0
    private var privacyState = true
    private var isHidePrivacySmh = false
    private var isHidePrivacyCheckBox = false
    private var isPrivacyTextGravityCenter = false
    private var checkBoxGravity = 0
    private var checkedImageName = "yd_checkbox_checked"
    private var unCheckedImageName = "yd_checkbox_unchecked"
    private var privacyTextStart: String? = "登录即同意"
    private var protocolText: String? = null
    private var protocolLink: String? = null
    private var protocol2Text: String? = null
    private var protocol2Link: String? = null
    private var privacyTextEnd: String? = "且授权使用本机号码登录"
    private var protocolNavTitle: String? = null
    private var protocolNavBackIcon: String? = null
    private var protocolNavHeight = 0
    private var protocolNavTitleSize = 0
    private var protocolNavTitleDpSize = 0
    private var protocolNavBackIconWidth = 25
    private var protocolNavBackIconHeight = 25
    private var protocolNavColor = 0
    private var backgroundImage: String? = null
    private var backgroundGif: String? = null
    private var backgroundVideo: String? = null
    private var backgroundVideoImage: String? = null
    private var isLandscape = false
    private var isDialogMode = false
    private var dialogWidth = 0
    private var dialogHeight = 0
    private var dialogX = 0
    private var dialogY = 0
    private var isBottomDialog = false
    private var context: ReactContext? = null
    private var bundleUrl: String? = null
    private var widgets: List<HashMap<String, Any>>? = null

    fun JsonConfigParser(bundleUrl: String?) {
        this.bundleUrl = bundleUrl
    }

    fun parser(uiConfig: Map<String, Any>) {
        Log.d("Quick", "uiConfig--->$uiConfig")
        statusBarColor = ((uiConfig["statusBarColor"] ?: 0) as Double).toInt()
        isStatusBarDarkColor = (uiConfig["isStatusBarDarkColor"] ?: false) as Boolean
        navBackIcon = (uiConfig["navBackIcon"] ?: "") as String
        navBackIconWidth = ((uiConfig["navBackIconWidth"] ?: 25) as Double).toInt()
        navBackIconHeight = ((uiConfig["navBackIconHeight"] ?: 25) as Double).toInt()
        isHideBackIcon = (uiConfig["isHideBackIcon"] ?: false) as Boolean
        navHeight = uiConfig["navHeight"]?.let { (it as Double).toInt() } ?: 50
        navTitleSize = uiConfig["navTitleSize"]?.let { (it as Double).toInt() } ?: 18
        isNavTitleBold = (uiConfig["isNavTitleBold"] ?: false) as Boolean
        navBackgroundColor = ((uiConfig["navBackgroundColor"] ?: Color.WHITE) as Double).toInt()
        navTitle = (uiConfig["navTitle"] ?: "") as String
        navTitleColor = ((uiConfig["navTitleColor"] ?: Color.BLACK) as Double).toInt()
        isHideNav = (uiConfig["isHideNav"] ?: false) as Boolean
        logoIconName = (uiConfig["logoIconName"] ?: "") as String
        logoWidth = ((uiConfig["logoWidth"] ?: 50) as Double).toInt()
        logoHeight = ((uiConfig["logoHeight"] ?: 50) as Double).toInt()
        logoTopYOffset = ((uiConfig["logoTopYOffset"] ?: 0) as Double).toInt()
        logoBottomYOffset = ((uiConfig["logoBottomYOffset"] ?: 0) as Double).toInt()
        logoXOffset = ((uiConfig["logoXOffset"] ?: 0) as Double).toInt()
        isHideLogo = (uiConfig["isHideLogo"] ?: false) as Boolean
        maskNumberColor = ((uiConfig["maskNumberColor"] ?: Color.BLACK) as Double).toInt()
        maskNumberSize = ((uiConfig["maskNumberSize"] ?: 18) as Double).toInt()
        maskNumberDpSize = ((uiConfig["maskNumberDpSize"] ?: 18) as Double).toInt()
        maskNumberTopYOffset = ((uiConfig["maskNumberTopYOffset"] ?: 0) as Double).toInt()
        maskNumberBottomYOffset = ((uiConfig["maskNumberBottomYOffset"] ?: 0) as Double).toInt()
        maskNumberXOffset = ((uiConfig["maskNumberXOffset"] ?: 0) as Double).toInt()
        sloganSize = ((uiConfig["sloganSize"] ?: 10) as Double).toInt()
        sloganDpSize = ((uiConfig["sloganDpSize"] ?: 10) as Double).toInt()
        sloganColor = ((uiConfig["sloganColor"] ?: Color.BLACK) as Double).toInt()
        sloganTopYOffset = ((uiConfig["sloganTopYOffset"] ?: 0) as Double).toInt()
        sloganBottomYOffset = ((uiConfig["sloganBottomYOffset"] ?: 0) as Double).toInt()
        sloganXOffset = ((uiConfig["sloganXOffset"] ?: 0) as Double).toInt()
        loginBtnText = (uiConfig["loginBtnText"] ?: "一键登录") as String
        loginBtnTextSize = ((uiConfig["loginBtnTextSize"] ?: 15) as Double).toInt()
        loginBtnTextDpSize = ((uiConfig["loginBtnTextDpSize"] ?: 15) as Double).toInt()
        loginBtnTextColor = ((uiConfig["loginBtnTextColor"] ?: Color.BLACK) as Double).toInt()
        loginBtnWidth = ((uiConfig["loginBtnWidth"] ?: 0) as Double).toInt()
        loginBtnHeight = ((uiConfig["loginBtnHeight"] ?: 0) as Double).toInt()
        loginBtnBackgroundRes = (uiConfig["loginBtnBackgroundRes"] ?: "") as String
        loginBtnTopYOffset = ((uiConfig["loginBtnTopYOffset"] ?: 0) as Double).toInt()
        loginBtnBottomYOffset = ((uiConfig["loginBtnBottomYOffset"] ?: 0) as Double).toInt()
        loginBtnXOffset = ((uiConfig["loginBtnXOffset"] ?: 0) as Double).toInt()
        privacyTextColor = ((uiConfig["privacyTextColor"] ?: Color.BLACK) as Double).toInt()
        privacyProtocolColor = ((uiConfig["privacyProtocolColor"] ?: Color.GREEN) as Double).toInt()
        privacySize = ((uiConfig["privacySize"] ?: 0) as Double).toInt()
        privacyDpSize = ((uiConfig["privacyDpSize"] ?: 0) as Double).toInt()
        privacyTopYOffset = ((uiConfig["privacyTopYOffset"] ?: 0) as Double).toInt()
        privacyBottomYOffset = ((uiConfig["privacyBottomYOffset"] ?: 0) as Double).toInt()
        privacyMarginLeft = ((uiConfig["privacyMarginLeft"] ?: 0) as Double).toInt()
        privacyMarginRight = ((uiConfig["privacyMarginRight"] ?: 0) as Double).toInt()
        privacyState = (uiConfig["privacyState"] ?: true) as Boolean
        isHidePrivacySmh = (uiConfig["isHidePrivacySmh"] ?: false) as Boolean
        isHidePrivacyCheckBox = (uiConfig["isHidePrivacyCheckBox"] ?: false) as Boolean
        isPrivacyTextGravityCenter = (uiConfig["isPrivacyTextGravityCenter"] ?: false) as Boolean
        checkBoxGravity = ((uiConfig["checkBoxGravity"] ?: Gravity.CENTER) as Double).toInt()
        checkedImageName = (uiConfig["checkedImageName"] ?: "") as String
        unCheckedImageName = (uiConfig["unCheckedImageName"] ?: "") as String
        privacyTextStart = (uiConfig["privacyTextStart"] ?: "") as String
        protocolText = (uiConfig["protocolText"] ?: "") as String
        protocolLink = (uiConfig["protocolLink"] ?: "") as String
        protocol2Text = (uiConfig["protocol2Text"] ?: "") as String
        protocol2Link = (uiConfig["protocol2Link"] ?: "") as String
        privacyTextEnd = (uiConfig["privacyTextEnd"] ?: "") as String
        protocolNavTitle = (uiConfig["protocolNavTitle"] ?: "协议详情") as String
        protocolNavBackIcon = (uiConfig["protocolNavBackIcon"] ?: "") as String
        protocolNavHeight = ((uiConfig["protocolNavHeight"] ?: 0) as Double).toInt()
        protocolNavTitleSize = ((uiConfig["protocolNavTitleSize"] ?: 0) as Double).toInt()
        protocolNavTitleDpSize = ((uiConfig["protocolNavTitleDpSize"] ?: 0) as Double).toInt()
        protocolNavBackIconWidth = ((uiConfig["protocolNavBackIconWidth"] ?: 0) as Double).toInt()
        protocolNavBackIconHeight = ((uiConfig["protocolNavBackIconHeight"] ?: 0) as Double).toInt()
        protocolNavColor = ((uiConfig["protocolNavColor"] ?: Color.BLACK) as Double).toInt()
        backgroundImage = (uiConfig["backgroundImage"] ?: "") as String
        backgroundGif = (uiConfig["backgroundGif"] ?: "") as String
        backgroundVideo = (uiConfig["backgroundVideo"] ?: "") as String
        backgroundVideoImage = (uiConfig["backgroundVideoImage"] ?: "") as String
        isLandscape = (uiConfig["isLandscape"] ?: false) as Boolean
        isDialogMode = (uiConfig["isDialogMode"] ?: false) as Boolean
        dialogWidth = ((uiConfig["dialogWidth"] ?: 0) as Double).toInt()
        dialogHeight = ((uiConfig["dialogHeight"] ?: 0) as Double).toInt()
        dialogX = ((uiConfig["dialogX"] ?: 0) as Double).toInt()
        dialogY = ((uiConfig["dialogY"] ?: 0) as Double).toInt()
        isBottomDialog = (uiConfig["isBottomDialog"] ?: false) as Boolean
        widgets = (uiConfig["widgets"] ?: null) as List<HashMap<String, Any>>
        Log.d(TAG, "ui config parser finished")
    }

    fun getUiConfig(context: ReactContext, map: Map<String, Any>): UnifyUiConfig {
        this.context = context
        parser(map)
        return buildUiConfig(context)
    }

    private fun buildUiConfig(context: Context): UnifyUiConfig {
        val builder: UnifyUiConfig.Builder = UnifyUiConfig.Builder()
                .setStatusBarColor(statusBarColor)
                .setStatusBarDarkColor(isStatusBarDarkColor)
                .setNavigationIconDrawable(getDrawable(navBackIcon, context))
                .setNavigationBackIconWidth(navBackIconWidth)
                .setNavigationBackIconHeight(navBackIconHeight)
                .setHideNavigationBackIcon(isHideBackIcon)
                .setNavigationHeight(navHeight)
                .setNavigationBackgroundColor(navBackgroundColor)
                .setNavigationTitle(navTitle)
                .setNavTitleSize(navTitleSize)
                .setNavTitleBold(isNavTitleBold)
                .setNavigationTitleColor(navTitleColor)
                .setHideNavigation(isHideNav)
                .setLogoIconDrawable(getDrawable(logoIconName, context))
                .setLogoWidth(logoWidth)
                .setLogoHeight(logoHeight)
                .setLogoTopYOffset(logoTopYOffset)
                .setLogoBottomYOffset(logoBottomYOffset)
                .setLogoXOffset(logoXOffset)
                .setHideLogo(isHideLogo)
                .setMaskNumberSize(maskNumberSize)
                .setMaskNumberDpSize(maskNumberDpSize)
                .setMaskNumberColor(maskNumberColor)
                .setMaskNumberXOffset(maskNumberXOffset)
                .setMaskNumberTopYOffset(maskNumberTopYOffset)
                .setMaskNumberBottomYOffset(maskNumberBottomYOffset)
                .setSloganSize(sloganSize)
                .setSloganDpSize(sloganDpSize)
                .setSloganColor(sloganColor)
                .setSloganTopYOffset(sloganTopYOffset)
                .setSloganXOffset(sloganXOffset)
                .setSloganBottomYOffset(sloganBottomYOffset)
                .setLoginBtnText(loginBtnText)
                .setLoginBtnBackgroundDrawable(getDrawable(loginBtnBackgroundRes, context))
                .setLoginBtnTextColor(loginBtnTextColor)
                .setLoginBtnTextSize(loginBtnTextSize)
                .setLoginBtnTextDpSize(loginBtnTextDpSize)
                .setLoginBtnHeight(loginBtnHeight)
                .setLoginBtnWidth(loginBtnWidth)
                .setLoginBtnTopYOffset(loginBtnTopYOffset)
                .setLoginBtnBottomYOffset(loginBtnBottomYOffset)
                .setLoginBtnXOffset(loginBtnXOffset)
                .setPrivacyTextColor(privacyTextColor)
                .setPrivacyProtocolColor(privacyProtocolColor)
                .setPrivacySize(privacySize)
                .setPrivacyDpSize(privacyDpSize)
                .setPrivacyTopYOffset(privacyTopYOffset)
                .setPrivacyBottomYOffset(privacyBottomYOffset)
                .setPrivacyMarginLeft(privacyMarginLeft)
                .setPrivacyMarginRight(privacyMarginRight)
                .setPrivacyState(privacyState)
                .setHidePrivacySmh(isHidePrivacySmh)
                .setHidePrivacyCheckBox(isHidePrivacyCheckBox)
                .setPrivacyTextGravityCenter(isPrivacyTextGravityCenter)
                .setCheckBoxGravity(checkBoxGravity)
                .setCheckedImageDrawable(getDrawable(checkedImageName, context))
                .setUnCheckedImageDrawable(getDrawable(unCheckedImageName, context))
                .setPrivacyTextStart(privacyTextStart)
                .setProtocolText(protocolText)
                .setProtocolLink(protocolLink)
                .setProtocol2Text(protocol2Text)
                .setProtocol2Link(protocol2Link)
                .setPrivacyTextEnd(privacyTextEnd)
                .setBackgroundImageDrawable(getDrawable(backgroundImage, context))
                .setBackgroundVideo(backgroundVideo, backgroundVideo)
                .setBackgroundGifDrawable(getDrawable(backgroundGif, context))
                .setProtocolPageNavTitle(protocolNavTitle)
                .setProtocolPageNavBackIconDrawable(getDrawable(protocolNavBackIcon, context))
                .setProtocolPageNavColor(protocolNavColor)
                .setProtocolPageNavHeight(protocolNavHeight)
                .setProtocolPageNavTitleSize(protocolNavTitleSize)
                .setProtocolPageNavTitleDpSize(protocolNavTitleDpSize)
                .setProtocolPageNavBackIconWidth(protocolNavBackIconWidth)
                .setProtocolPageNavBackIconHeight(protocolNavBackIconHeight)
                .setLandscape(isLandscape)
                .setDialogMode(isDialogMode, dialogWidth, dialogHeight, dialogX, dialogY, isBottomDialog)
                .setClickEventListener { viewType, code ->
                    if (viewType == UnifyUiConfig.CLICK_PRIVACY) {
                        Log.d(TAG, "点击了隐私协议")
                        val resultMap = WritableNativeMap()
                        resultMap.putString("clickViewType", "privacy")
                        sendEvent(resultMap)
                    } else if (viewType == UnifyUiConfig.CLICK_CHECKBOX) {
                        if (code == 0) {
                            Log.d(TAG, "点击了复选框,且复选框未勾选")
                            val resultMap = WritableNativeMap()
                            resultMap.putString("clickViewType", "checkbox")
                            resultMap.putBoolean("isCheckboxChecked", false)
                            sendEvent(resultMap)
                        } else if (code == 1) {
                            Log.d(TAG, "点击了复选框,且复选框已勾选")
                            val resultMap = WritableNativeMap()
                            resultMap.putString("clickViewType", "checkbox")
                            resultMap.putBoolean("isCheckboxChecked", true)
                            sendEvent(resultMap)
                        }
                    } else if (viewType == UnifyUiConfig.CLICK_LOGIN_BUTTON) {
                        if (code == 0) {
                            Log.d(TAG, "点击了登录按钮,且复选框未勾选")
                            val resultMap = WritableNativeMap()
                            resultMap.putString("clickViewType", "loginButton")
                            resultMap.putBoolean("isCheckboxChecked", false)
                            sendEvent(resultMap)
                        } else if (code == 1) {
                            Log.d(TAG, "点击了登录按钮,且复选框已勾选")
                            val resultMap = WritableNativeMap()
                            resultMap.putString("clickViewType", "loginButton")
                            resultMap.putBoolean("isCheckboxChecked", true)
                            sendEvent(resultMap)
                        }
                    } else if (viewType == UnifyUiConfig.CLICK_TOP_LEFT_BACK_BUTTON) {
                        Log.d(TAG, "点击了左上角返回")
                        val resultMap = WritableNativeMap()
                        resultMap.putString("clickViewType", "leftBackButton")
                        sendEvent(resultMap)
                    }
                }
                .setActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                    override fun onCreate(activity: Activity) {
                        Log.d(TAG, "lifecycle onCreate回调------>" + activity.localClassName)
                        val resultMap = WritableNativeMap()
                        resultMap.putString("lifecycle", "onCreate")
                        sendEvent(resultMap)
                    }

                    override fun onResume(activity: Activity) {
                        Log.d(TAG, "lifecycle onResume回调------>" + activity.localClassName)
                        val resultMap = WritableNativeMap()
                        resultMap.putString("lifecycle", "onResume")
                        sendEvent(resultMap)
                    }

                    override fun onStart(activity: Activity) {
                        Log.d(TAG, "lifecycle onStart回调------>" + activity.localClassName)
                        val resultMap = WritableNativeMap()
                        resultMap.putString("lifecycle", "onStart")
                        sendEvent(resultMap)
                    }

                    override fun onPause(activity: Activity) {
                        Log.d(TAG, "lifecycle onPause回调------>" + activity.localClassName)
                        val resultMap = WritableNativeMap()
                        resultMap.putString("lifecycle", "onPause")
                        sendEvent(resultMap)
                    }

                    override fun onStop(activity: Activity) {
                        Log.d(TAG, "lifecycle onStop回调------>" + activity.localClassName)
                        val resultMap = WritableNativeMap()
                        resultMap.putString("lifecycle", "onStop")
                        sendEvent(resultMap)
                    }

                    override fun onDestroy(activity: Activity) {
                        Log.d(TAG, "lifecycle onDestroy回调------>" + activity.localClassName)
                        val resultMap = WritableNativeMap()
                        resultMap.putString("lifecycle", "onDestroy")
                        sendEvent(resultMap)
                    }
                })
        setCustomView(context, builder, widgets)
        Log.d(TAG, "---------------UI配置设置完成---------------");
        return builder.build(context)
    }

    private fun setCustomView(context: Context, builder: UnifyUiConfig.Builder, list: List<Map<String, Any>>?) {
        if (list == null) {
            Log.d(TAG, "UI配置widgets为空");
            return
        }
        for (element in list) {
            val itemMap: Map<String, Any> = element
            val viewParams = ViewParams()
            viewParams.viewId = (itemMap["viewId"] ?: "") as String
            viewParams.type = (itemMap["type"] ?: "") as String
            when {
                "Button".equals(viewParams.type, ignoreCase = true) -> {
                    viewParams.view = Button(context)
                }
                "TextView".equals(viewParams.type, ignoreCase = true) -> {
                    viewParams.view = TextView(context)
                }
                "ImageView".equals(viewParams.type, ignoreCase = true) -> {
                    viewParams.view = ImageView(context)
                }
            }
            viewParams.text = (itemMap["text"] ?: "") as String
            viewParams.left = itemMap["left"]?.let { (it as Double).toInt() } ?: 0
            viewParams.top = itemMap["top"]?.let { (it as Double).toInt() } ?: 0
            viewParams.right = itemMap["right"]?.let { (it as Double).toInt() } ?: 0
            viewParams.bottom = itemMap["bottom"]?.let { (it as Double).toInt() } ?: 0
            viewParams.width = itemMap["width"]?.let { (it as Double).toInt() } ?: 0
            viewParams.height = itemMap["height"]?.let { (it as Double).toInt() } ?: 0
            viewParams.font = itemMap["font"]?.let { (it as Double).toInt() } ?: 0
            viewParams.textColor = (itemMap["textColor"] ?: "") as String
            viewParams.clickable = (itemMap["clickable"] ?: true) as Boolean
            viewParams.backgroundColor = (itemMap["backgroundColor"] ?: "") as String
            viewParams.positionType = itemMap["positionType"]?.let { (it as Double).toInt() } ?: 0
            viewParams.backgroundImgPath = (itemMap["backgroundImgPath"] ?: "") as String
            val layoutParams = RelativeLayout.LayoutParams(dip2px(context, viewParams.width.toFloat()), dip2px(context, viewParams.height.toFloat()))
            if (viewParams.left != 0) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.CENTER_VERTICAL)
                layoutParams.leftMargin = dip2px(context, viewParams.left.toFloat())
            }
            if (viewParams.top != 0) {
                layoutParams.topMargin = dip2px(context, viewParams.top.toFloat())
            }
            if (viewParams.right != 0) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.CENTER_VERTICAL)
                layoutParams.rightMargin = dip2px(context, viewParams.right.toFloat())
            }
            if (viewParams.bottom != 0) {
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.CENTER_VERTICAL)
                layoutParams.bottomMargin = dip2px(context, viewParams.bottom.toFloat())
            }
            viewParams.view!!.layoutParams = layoutParams
            viewParams.view!!.tag = viewParams.viewId
            if (viewParams.view is TextView || viewParams.view is Button) {
                (viewParams.view as TextView?)!!.text = viewParams.text
                if (viewParams.font != 0) {
                    (viewParams.view as TextView?)!!.textSize = viewParams.font.toFloat()
                }
                if (!TextUtils.isEmpty(viewParams.textColor)) {
                    (viewParams.view as TextView?)!!.setTextColor(Color.parseColor(viewParams.textColor))
                }
            }
            if (!viewParams.clickable) {
                viewParams.view?.isClickable = false
            }
            if (!TextUtils.isEmpty(viewParams.backgroundColor)) {
                viewParams.view?.setBackgroundColor(Color.parseColor(viewParams.backgroundColor))
            }
            if (!TextUtils.isEmpty(viewParams.backgroundImgPath)) {
                viewParams.view?.background = getDrawable(viewParams.backgroundImgPath, context)
            }
            builder.addCustomView(viewParams.view, viewParams.viewId, if (viewParams.positionType == 0) UnifyUiConfig.POSITION_IN_BODY else UnifyUiConfig.POSITION_IN_TITLE_BAR) { context, view ->
                Log.d(TAG, "CustomViewListener onClick:" + view.tag)
                val resultMap = WritableNativeMap()
                resultMap.putString("viewId", view.tag as String)
                sendEvent(resultMap)
            }
        }
        Log.d(TAG, "-----------set custom view finished-----------")
    }

    class ViewParams {
        var view: View? = null
        var viewId: String? = null
        var type: String? = null
        var text: String? = null
        var left = 0
        var top = 0
        var right = 0
        var bottom = 0
        var width = 0
        var height = 0
        var font = 0
        var textColor: String? = null
        var clickable = true
        var backgroundColor: String? = null
        var backgroundImgPath: String? = null
        var positionType = 0
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    private fun getDrawable(resPath: String?, context: Context): Drawable? {
        var drawable: Drawable? = null
        var rid = 0
        try {
            Log.d(TAG, "drawable path: $resPath")
            if(!TextUtils.isEmpty(resPath)){
                rid = context.resources.getIdentifier(resPath,"drawable",context.packageName)
                drawable = context.resources.getDrawable(rid)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return drawable
    }

    fun sendEvent(params: WritableMap?) {
        this.context?.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                ?.emit("uiCallback", params)
    }
}
