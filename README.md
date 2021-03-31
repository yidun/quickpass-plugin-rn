# 易盾一键登录RN接入使用文档
在使用前请确保已申请到易盾一键登录的业务id

## 导入插件
```
npm install --save https://github.com/yidun/quickpass-react-native.git
react-native link react-native-quicklogin-plugin
```
## 配置依赖
在react-native工程对应的android/app/build.gradle 文件的android域中添加
```
repositories {
	        flatDir {
	            dirs project(':react-native-quicklogin-plugin').file('libs')
	        }
	}
```

## 引入
```js
import {NativeModules} from 'react-native';
```

然后就可以使用原生模块NativeModules获取易盾一键登录RN对象NativeModules.QuickLoginPlugin

## 一键登录API说明

### initQuickLogin(businessid, callback)
*方法描述：*
初始化一键登录

*参数说明：*
```
businessId: '从易盾申请的id',
callback -- 初始化结果回调

// 返回参数
success: 初始化是否成功
 
```
*代码示例：*
```js
NativeModules.QuickLoginPlugin.initQuickLogin('此处填写您申请的业务id');
```

### setUiConfig(config, callback)
*方法描述：*
自定义授权页面，包括默认组件样式文字、背景等自定义还可以传入自定义控件进行布局

*参数说明：*
config -- 自定义配置项，iOS参见，android参见[易盾android接入文档](https://support.dun.163.com/documents/287305921855672320?docId=424017619994976256%235.4%2520%e7%8a%b6%e6%80%81%e6%a0%8f)

callback -- 授权页事件回调（含生命周期钩子）

```js
const NTESRNRouterEmitter = new  NativeEventEmitter(NativeModules.QuickLoginPlugin)
NTESRNRouterEmitter.addListener('uiCallback',(value)=>{
       console.log(value)
});
```
其中value内容和含义如下
value: {
    viewId: 事件目标
    lifecycle: 生命周期名称
    clickViewType: 内置组件事件触发目标
    isCheckboxChecked: 协议复选框的值，仅在复选框事件和登录按钮点击事件中存在
 }
```

action值说明：

* iOS授权页生命周期action:
  * authViewDidLoad -- 加载授权页
  * authViewWillAppear -- 授权页已经出现
  * authViewWillDisappear -- 授权页将要消失
  * authViewDidDisappear -- 授权页已经消失
  * authViewDealloc -- 授权页销毁

* android lifecycle取值说明：
  * onCreate -- 页面创建
  * onStart -- 页面已开始活动
  * onResume -- 页面展示
  * onPause -- 页面非活动状态
  * onStop -- 页面已停止
  * onDestroy -- 页面销毁

clickViewType取值说明：
* privacy -- 隐私协议点击事件
* checkbox -- 复选框点击事件（含isCheckboxChecked: 0/1）
* loginButton -- 登录按钮点击事件（含isCheckboxChecked: 0/1）
* leftBackButton -- 左上角返回按钮点击事件

*代码示例：*

android版，[安卓自定义项说明](https://support.dun.163.com/documents/287305921855672320?docId=424017619994976256) 图标放置在react_native对应android工程的drawable下面 配置文件中只配置名字

```js
  const config = {
    "statusBarColor":-1,          // 设置状态栏颜色
    "navBackIcon": "yd_left",     // 导航栏返回图标
    "navBackIconWidth": 25,       // 导航栏返回图标宽度
    "navBackIconHeight": 25,      // 导航栏返回图标高度
    "navBackgroundColor": -1,     // 导航栏背景颜色
    "navTitle": "一键登录/注册",   // 导航栏标题
    "navTitleColor": -16777216,   // 导航栏标题颜色
    "isHideNav": false,           // 是否隐藏导航栏

    "logoIconName": "ico_logo", // 设置应用logo图标，logoIconName：logo图标名称
    "logoWidth": 70,  // 设置应用logo宽度，单位dp
    "logoHeight": 70, // 设置应用logo高度，单位dp
    "logoTopYOffset": 50, // 设置logo顶部Y轴偏移，单位dp
    "logoBottomYOffset": 0, // 设置logo距离屏幕底部偏移，单位dp
    "logoXOffset": 0, // 设置logo水平方向的偏移，单位dp
    "isHideLogo": false, // 设置是否隐藏Logo

    "maskNumberColor": -16777216, // 设置手机掩码颜色
    "maskNumberSize": 15, // 设置手机掩码字体大小，单位sp
    "maskNumberDpSize": 0, // 设置手机掩码字体大小，单位dp
    "maskNumberTopYOffset": 150, // 设置手机掩码顶部Y轴偏移，单位dp
    "maskNumberBottomYOffset": 0, // 设置手机掩码距离屏幕底部偏移，单位dp
    "maskNumberXOffset": 0, // 设置手机掩码水平方向的偏移，单位dp

    "sloganSize": 15, // 设置认证品牌字体大小，单位sp
    "sloganDpSize": 0, // 设置认证品牌字体大小，单位dp
    "sloganColor": -16777216, // 设置认证品牌颜色
    "sloganTopYOffset": 200, // 设置认证品牌顶部Y轴偏移，单位dp
    "sloganXOffset": 0, // 设置认证品牌水平方向的偏移，单位dp
    "sloganBottomYOffset":0, // 设置认证品牌距离屏幕底部偏移，单位dp

    "loginBtnText": "同意协议并登录", // 设置登录按钮文本
    "loginBtnTextSize": 15, // 设置登录按钮文本字体大小，单位sp
    "loginBtnTextDpSize": 15, // 设置登录按钮文本字体大小，单位dp
    "loginBtnTextColor": -1, // 设置登录按钮文本颜色
    "loginBtnWidth": 300, // 设置登录按钮宽度，单位dp
    "loginBtnHeight": 45, // 设置登录按钮高度，单位dp
    "loginBtnTopYOffset": 250, // 设置登录按钮顶部Y轴偏移，单位dp
    "loginBtnBottomYOffset": 0, // 设置登录按钮距离屏幕底部偏移，单位dp
    "loginBtnXOffset": 0, // 设置登录按钮水平方向的偏移，单位dp
    "loginBtnBackgroundRes":"btn_shape_login_onepass", // 支持按钮背景图片设置，如"static/launchImage.png"

    "privacyTextColor": -6710887, // 设置隐私栏文本颜色，不包括协议 ，如若隐私栏协议文案为：登录即同意《中国移动认证条款》且授权QuickLogin登录， 则该API对除协议‘《中国移动认证条款》’区域外的其余文本生效
    "privacyProtocolColor": -13480449, // 设置隐私栏协议颜色 。例如：登录即同意《中国移动认证条款》且授权QuickLogin登录 ， 则该API仅对‘《中国移动认证条款》’文案生效
    "privacySize": 12, // 设置隐私栏区域字体大小，单位sp
    "privacyDpSize": 12, // 设置隐私栏区域字体大小，单位dp
    "privacyTopYOffset": 0, // 设置隐私栏顶部Y轴偏移，单位dp
    "privacyBottomYOffset": 50, // 设置隐私栏距离屏幕底部偏移，单位dp
    "privacyMarginLeft": 0, // 设置隐私栏水平方向的偏移，单位dp
    "privacyMarginRight": 1, // 设置隐私栏右侧边距，单位dp
    "privacyState": true, // 设置隐私栏协议复选框勾选状态，true勾选，false不勾选
    "isHidePrivacySmh": false,
    "isHidePrivacyCheckBox": false, // 设置是否隐藏隐私栏勾选框
    "isPrivacyTextGravityCenter": false, // 设置隐私栏文案换行后是否居中对齐，如果为true则居中对齐，否则左对齐
    "checkBoxGravity": 48, // 设置隐私栏勾选框与文本协议对齐方式，可选择顶部（Gravity.TOP），居中（Gravity.CENTER），底部（Gravity.BOTTOM）等
    "checkedImageName": "yd_checkbox_checked", // 设置隐私栏复选框选中时的图片资源
    "unCheckedImageName": "yd_checkbox_unchecked", // 设置隐私栏复选框未选中时的图片资源
    "privacyTextStart": "登录即同意", // 设置隐私栏声明部分起始文案 。如：隐私栏声明为"登录即同意《隐私政策》和《中国移动认证条款》且授权易盾授予本机号码"，则可传入"登录即同意"
    "protocolText": "", // 设置隐私栏协议文本
    "protocolLink": "", // 设置隐私栏协议链接
    "protocol2Text": "", // 设置隐私栏协议2文本
    "protocol2Link": "", // 设置隐私栏协议2链接
    "privacyTextEnd": "", // 设置隐私栏声明部分尾部文案。如：隐私栏声明为"登录即同意《隐私政策》和《中国移动认证条款》且授权易盾授予本机号码"，则可传入"且授权易盾授予本机号码"
    "protocolNavHeight": 0, // 设置协议Web页面导航栏高度
    "protocolNavTitleSize": 0, // 设置协议Web页面导航栏标题大小，单位sp
    "protocolNavTitleDpSize": 0, // 设置协议Web页面导航栏标题大小，单位dp
    "protocolNavBackIconWidth": 25, // 设置协议Web页面导航栏返回按钮宽度，单位dp
    "protocolNavBackIconHeight": 25, // 设置协议Web页面导航栏返回按钮高度，单位dp

    "protocolNavTitle": "易盾一键登录SDK服务条款", // 设置协议Web页面导航栏标题
    "protocolNavBackIcon": "yd_left", // 设置协议Web页面导航栏返回图标
    "protocolNavColor": -1, // 设置协议Web页面导航栏颜色

    "backgroundImage":"", // 设置登录页面背景
    "backgroundGif": "", // 设置登录页面背景为Gif
    "backgroundVideo": "", // 设置登录页面背景为视频，参数videoPath为背景Video文件路径:(支持本地路径如："android.resource://" + context.getPackageName() + "/" + R.raw.xxxVideo；支持网络路径如"https://xxx"(建议下载到本地后使用本地路径，网络路径由于网络环境的不可控体验不如直接加载本地视频)，参数videoImage为视频播放前的背景图片(需要放置到drawable文件中，传入图片名称即可)，2个参数必须都设置
    "backgroundVideoImage": "",

    "isLandscape": false, // 设置是否为横屏模式，默认竖屏
    "isDialogMode": false, // 是否开启对话框模式，true开启，false关闭
    "dialogWidth": 300, // 对话框宽度
    "dialogHeight": 500, // 对话框高度
    "dialogX": 0, // 当弹窗模式为中心模式时，弹窗X轴偏移（以屏幕中心为基准）
    "dialogY": 0, // 当弹窗模式为中心模式时，弹窗Y轴偏移（以屏幕中心为基准）
    "isBottomDialog": false, // 是否为底部对话框模式，true则为底部对话框模式，否则为中心模式
    "widgets": [ // widgets type 支持 TextView Button ImageView
      {
          "viewId": "view_title",  // 自定义组件id
          "type": "TextView",      // 自定义组件对应的Android原生类型
          "top": 10,               // 组件距离顶部的偏移
          "left": 0,               // 组件距离左侧的偏移
          "right": 10,             // 组件距离右侧的偏移
          "width": 20,             // 组件宽度
          "height": 20,            // 组件高度
          "text": "",              // 组件文案
          "font": 16,              // 组件字体
          "positionType": 1,       // 被添加组件的位置类型，1表示位于导航栏下方的body部分
          "backgroundImgPath": "yidun_logo.png"   // 组件的背景图片路径
      },
      {
          "viewId": "view_tips",
          "type": "TextView",
          "left": 110,
          "top": 350,
          "right": 0,
          "bottom": 0,
          "width": 150,
          "height": 50,
          "clickable": false,
          "text": "--- 其他方式登录 ---",
          "font": 16,
          "textColor": "#797894"
      },
      {
          "viewId": "wx_button",
          "type": "Button",
          "text": "",
          "left": 110,
          "top": 380,
          "right": 0,
          "bottom": 0,
          "width": 32,
          "height": 32,
          "clickable": true,
        "backgroundImgPath": "weixin.png"
      },
      {
          "viewId": "qq_button",
          "type": "Button",
          "text": "",
          "left": 162,
          "top": 380,
          "right": 0,
          "bottom": 0,
          "width": 32,
          "height": 32,
          "clickable": true,
        "backgroundImgPath": "qq.png"
      },
      {
          "viewId": "wb_button",
          "type": "Button",
          "text": "",
          "left": 214,
          "top": 380,
          "right": 0,
          "bottom": 0,
          "width": 32,
          "height": 32,
          "clickable": true,
        "backgroundImgPath": "weibo.png"
      }
    ]
}
```
iOS版本，[iOS自定义项说明](https://support.dun.163.com/documents/287305921855672320?docId=429869784953151488)
```js
  const config = {
    "presentDirectionType": "1", // 弹出方式设置 0：右边 导航栏效果 1：底部 present默认效果
    "backgroundColor": "#FFFFFF", // 授权页面背景颜色设置
    "navBarHidden": false, // 导航栏隐藏
    "navBgColor": "#3478F7", // 导航栏背景颜色
    "navText": "易盾登录", // 导航栏标题
    "navTextFont": 16, // 导航栏标题字体
    "navTextColor": "#ffffff", // 导航栏标题颜色
    "navTextHidden": false, // 导航栏标题是否隐藏 默认不隐藏
    "navReturnImg": "back.jpg", // 导航返回图标
    "navReturnImgLeftMargin": 0, // 可根据navReturnImgLeftMargin值调整返回按钮距离屏幕左边的距离
    "navReturnImgBottomMargin": 0, // 可根据navReturnImgBottomMargin值调整返回按钮距离屏幕底部的距离
    "navReturnImgWidth": 60, // 导航返回图标的宽度 
    "navReturnImgHeight": 60, // 导航返回图标的高度
    "logoIconName": "checkedBox", // LOGO图片
    "logoWidth": 50, // LOGO图片宽度 
    "logoHeight": 50, // LOGO图片高度
    "logoOffsetX": 0, // LOGO图片左右偏移量 ，logoOffsetX = 0居中显示
    "logoHidden": false, // LOGO图片隐藏
    "logoOffsetTopY": 50, // LOGO图片Y偏移量， logoOffsetTopY为距离屏幕顶部的距离
    "numberColor": "#000000", // 手机号码字体颜色
    "numberOffsetX": 0, // 手机号码X偏移量， numberOffsetX = 0 居中显示
    "numberHeight": 50, // 手机号码框的高度
    "numberFont": 14, // 手机号码大小
    "numberCornerRadius": 10, // 手机号码的控件的圆角
    "numberLeftContent": "",  // 手机号码左边自定义内容
    "numberRightContent": "", // 手机号码右边自定义内容
    "numberOffsetTopY": "100", // 距离屏幕顶部的距离
    "brandColor": "#000000", // 认证服务品牌文字颜色
    "brandWidth": 100, // 认证服务品牌的宽度
    "brandHeight": 20, // 认证服务品牌的高度
    "brandFont": 15, // 认证服务品牌文字字体
    "brandOffsetX": 0, // 认证服务品牌X偏移量 ，brandOffsetX = 0居中显示
    "brandHidden": false, // 隐藏认证服务品牌
    "brandLogoOffsetMargin": 0, // 认证服务品牌x轴的偏移量
    "brandLogoWidth": 50,   // 认证服务品牌的宽度
    "brandLogoHeight": 50,  // 认证服务品牌的高度
    "brandOffsetTopY": 170, // 离屏幕顶部的距离
    "logBtnText": "确定登录", // 登录按钮文本
    "loginBtnTextSize": 15, // 登录按钮字号
    "logBtnTextColor": "#000", // 登录按钮文本颜色
    "logBtnRadius": 20, // 登录按钮圆角
    "logBtnHeight": 40, // 登录按钮的高度
    "logBtnOriginLeft": 80, // 登录按钮的左边距
    "logBtnOriginRight": 80, // 登录按钮的右边距
    "logBtnUsableBGColor": "#0099FF", // 登录按钮背景颜色
    "logBtnOffsetTopY": 230, // 距离屏幕顶部的距离
    "logBtnEnableImg": "logo", // 登录按钮可用状态下的背景图片
    "logBtnHighlightedImg": "pic_success",
    "checkedImageName": "checkedImageName.png", // 复选框选中时图片
    "unCheckedImageName": "unCheckedImageName.png", // 复选框未选中时图片
    "checkboxWH": 50, // 复选框大小（只能正方形)
    "privacyState": true, // 隐私条款check框默认状态 
    "checkedHidden": true, // 隐藏复选框
    "checkBoxAlignment": 0, // 隐私条款check框 可相对协议顶对齐、中对齐、下对齐
    "checkBoxMargin": 0, // check框距离隐私条款的边距
    "appPrivacyOriginLeft": 40, // 隐藏条款距离屏幕左边的距离
    "appPrivacyOriginRight": 40, // 隐藏条款距离屏幕右边的距离
    "appPrivacyText": "登录即同意《默认》和《用户隐私协议》,《用户协议》", // 隐私的内容模板
    "appPrivacyTitleText": "中国移动认证服务条款",
    "appFPrivacyText": "用户隐私协议",
    "appFPrivacyTitleText": "用户隐私协议appFPrivacyTitleText",
    "appFPrivacyURL": "www.example.com",
    "appSPrivacyText": "用户协议",
    "appSPrivacyTitleText": "用户协议appSPrivacyTitleText",
    "appSPrivacyURL": "www.google.com",
    "appPrivacyAlignment": 0,  // 0 ： 隐私条款居左显示， 1： 居中显示， 2 ：居右显示
    "appPrivacyOriginLeftMargin": 60, // 隐私条款距离屏幕左边的距离
    "appPrivacyOriginBottomMargin": 60, // 隐私条款距离屏幕的距离
    "shouldHiddenPrivacyMarks": 0, // 是否隐藏"《默认》" 两边的《》，
    "privacyNavReturnImg": "back.jpg", // 用户协议界面，导航栏返回图标，默认用导航栏返回图标
    "privacyFont": 15, // 隐私条款字体的大小
    "privacyColor": "#000000", // 隐私条款名称颜色
    "protocolColor": "#3478F7", // 协议条款协议名称颜色

    "authWindowPop": 0, // 全屏模式
    "scaleW": 0.9, // 自定义窗口宽-缩放系数(屏幕宽乘以系数) 
    "scaleH": 0.5, // 自定义窗口高-缩放系数(屏幕高乘以系数) 
    "closePopImg": "back", // 居中弹窗 ,底部弹窗，
    "closePopImgWidth": 20, // 居中弹窗 ,底部弹窗，视图的关闭按钮的图片的宽度
    "closePopImgHeight": 20, // 居中弹窗,底部弹窗，视图的关闭按钮的图片的高度
    "closePopImgOriginY": -10, // 居中弹窗,底部弹窗，可调整关闭按钮距离顶部的距离
    "closePopImgOriginX": -10, // 居中弹窗,底部弹窗，可调整关闭按钮距离父视图右边的距离
    "authWindowCenterOriginY": 0, // 居中弹窗，可移动窗口中间点坐标Y
    "authWindowCenterOriginX": 0, // 居中弹窗，可移动窗口中间点坐标X
    "popCenterCornerRadius": 16, // 居中弹窗，视图的圆角 
    "popBottomCornerRadius": 10, // 底部弹窗，圆角的值，只可修改顶部左右二边的值
    "popBackgroundColor": "#0099FF", /// 弹窗模式的背景颜色
    "isOpenSwipeGesture": true, // 底部弹窗，是否开启轻扫手势，向下轻扫关闭弹窗

    "isRepeatPlay": "YES", // 是否重复播放视频
    "localVideoFileName": "video_portrait.mp4", // 视频本地名称 例如xx.mp4
    "animationDuration": 2, // 动画的时长
    "animationRepeatCount": 1000, // 动画重复的次数 -1无限重复

    "bgImage":"launchImage.png",  // 授权页背景颜色
    "faceOrientation" : 1, // 授权页面方向设置
    "loginDidDisapperfaceOrientation" : 1, /// 关闭授权页之后的旋转方向
    "modalTransitionStyle": 1, // 授权页转场动画 0 : 下推 ,1 :翻转, 2 :淡出.
    
    "statusBarStyle": 0, // 授权页状态栏的颜色 0:黑色 1：白色
    "widgets": [
      {
        "type": "UIButton", 
        "UIButtonType": 0, 
        "image": "weixin.png",
        "title": "",	
        "titleColor": "#000000",
        "titleFont": 12,
        "cornerRadius": 20,
        "action": "handleCustomEvent1",
        "frame": {"mainScreenLeftDistance":100,"mainScreenTopDistance":360,"width":32,"height":32},
        "backgroundImage":"yidun_logo.png"
      },
      {
        "type": "UIButton", 
        "UIButtonType": 0, 
        "image": "qq.png",
        "title": "",	
        "titleColor": "#FFFFFF",
        "titleFont": 12,
        "cornerRadius": 20,
        "action": "handleCustomEvent2",
        "frame": {"mainScreenCenterXWithLeftDistance":0,"mainScreenTopDistance":360,"width":32,"height":32},
        "backgroundImage": "yidun_logo.png"
      },
      {
        "type": "UIButton", 
        "UIButtonType": 0, 
        "image": "weibo.png",
        "title": "",	
        "titleColor": "#FFFFFF",
        "titleFont": 12,
        "cornerRadius": 20,
        "action": "handleCustomEvent3",
        "frame": {"mainScreenRightDistance":100,"mainScreenTopDistance":360,"width":32,"height":32},
        "backgroundImage": "yidun_logo.png"
      },
      {
        "type": "UILabel",
        "textColor": "#000000",
        "font": 15,
        "action": "handleCustomLabel",
        "text": "其他登录方式",
        "textAlignment": 1,
        "frame": {"mainScreenCenterXWithLeftDistance":0,"mainScreenTopDistance":310,"width":200,"height":32}
      }
    ]
  }

```
```js
NativeModules.QuickLoginPlugin.setUiConfig(config,(value)=>{
       console.log(value)
});
```

### prefetchNumber(callbak)
*方法描述：*
预取号

*参数说明：*

```
  callback -- 预取号结果回调

  // 返回参数
  [
  	true/false, // 预取号是否成功
  	result: {
   		token: 易盾token
   		desc:  如果预取号失败，返回的错误详情, 否则无此字段
    }
  ]

```

*代码示例：*

```js
NativeModules.QuickLoginPlugin.prefetchNumber((success, resultDic) => {
    if (success) {
        // TODO: 预取号成功处理，可以进行调起授权页
    }else{
        // TODO: 预取号失败处理  
    }
});
```

### login(callback)
*方法说明：*
调起授权页面

*参数说明：*

```
  callback --- 授权结果回调

  // 返回参数
  [
  	true/false, // 取号是否成功
  	result: {
   		token: 易盾token
   		accessToken: 如果取号成功返回运营商授权码，否则无此字段
   		desc:  如果取号失败，返回的错误详情, 否则返回"预取号成功"
    }
  ]
```

*代码示例：*
```js
NativeModules.QuickLoginPlugin.login((success, resultDic)=>{
    console.log(resultDic)
    if (success) {
        // TODO: 授权成功处理，可以进行关闭授权页、服务端验证等
        NativeModules.NTESRNRouter.closeAuthController();
    } else {
        // TODO: 授权失败处理
    }
})
```
### closeAuthController()
*方法说明：*
关闭授权页

*示例代码：*
```js
NativeModules.QuickLoginPlugin.closeAuthController()
```
