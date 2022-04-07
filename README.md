# 号码认证
直连三大运营商，一步校验手机号与当前SIM卡号一致性。优化注册/登录/支付等场景验证流程

## 平台支持（兼容性）
  | Android|iOS|  
  | ---- | ---- |
  | 适用版本区间：4.4以上|适用版本区间：9 - 14| 
  
## 环境准备

[CocoaPods安装教程](https://guides.cocoapods.org/using/getting-started.html)

## 资源引入/集成

```
npm install @yidun/quickpass-plugin-rn
```
### 项目开发配置

在 react-native 工程对应的 android/app/build.gradle 文件的 android 域中添加
```
repositories {
    flatDir {
        dirs project(':yidun_quickpass-plugin-rn').file('libs')
    }
}
```

## 调用示例

```
export default class App extends Component {
  componentDidMount() {
    NativeModules.QuickLoginPlugin.initQuickLogin('此处填写您申请的业务id');
  }

  handlePressLogin() {
    NativeModules.QuickLoginPlugin.prefetchNumber((success, resultDic) => {
      if (!success) return;
      
      const token = resultDic.token

      let uiJsonConfig;
      if (Platform.OS==='android') {
        uiJsonConfig = androidConfigJson;
        NativeModules.QuickLoginPlugin.setUiConfig(uiJsonConfig);
        NativeModules.QuickLoginPlugin.login((success, resultDic)=>{
            if (!success) return
            NativeModules.QuickLoginPlugin.closeAuthController();

            fetch('您的后端认证接口', {
                method: 'POST',
                body: JSON.stringify({
                    token,
                    accessToken: resultDic.accessToken
                })
            })
        })
      } else {
        uiJsonConfig = iosConfigJson;
        NativeModules.QuickLoginPlugin.setUiConfig(uiJsonConfig, (success) => {
            if (success) {
                console.log("设置UI成功")
                NativeModules.QuickLoginPlugin.login((success, resultDic)=>{
                    if (!success) return
                    NativeModules.QuickLoginPlugin.closeAuthController();

                    fetch('您的后端认证接口', {
                        method: 'POST', 
                        body: JSON.stringify({
                            token,
                            accessToken: resultDic.accessToken
                        })
                    })
                });
            } 
        });
      }
    })
  }

  render() {
    return (
      <View>
        <TouchableOpacity onPress={this.handlePressLogin}>
          <View>
            <Text>登录</Text>
          </View>
        </TouchableOpacity>
      </View>
    )
  }
}
```
更多使用场景请参考 [demo](https://github.com/yidun/quicklogin_react_demo)

## SDK方法说明

### 1. 创建对象

#### 代码说明：

```
import {NativeModules} from 'react-native';
var quichLoginPlugin = NativeModules.QuickLoginPlugin;
```

### 2. 初始化

#### 代码说明：

```
quichLoginPlugin.initQuickLogin('此处填写您申请的业务id');
```
#### 参数说明：
*  入参说明：

    |参数|类型|是否必填|默认值|描述|
    |----|----|--------|------|----|
    |businessId|String|是|无|易盾分配的业务id|

### 3. 预取号

#### 代码说明：
```
quichLoginPlugin.prefetchNumber((success, data) => {
  if (success) {
    // TODO: 预取号成功处理，可以进行授权页面配置和调起授权页
  } else {
    // TODO: 预取号失败处理  
  }
})
```
#### 参数说明：
*  回调参数说明：
 
    |回调参数|类型|描述|
    |----|----|----|
    | success|Boolean|预取号成功|
	| data.token|String|如果预取号成功则返回易盾token，否则无此字段|
	| data.desc|String|如果预取号失败，返回错误详情, 否则无此字段|

### 4. 是否具备一键登录能力

#### 代码说明：
```
quichLoginPlugin.checkVerifyEnable((success) => {
  if (success) {
    // TODO: 具备一键登录能力
  } else {
    // TODO: 不具备一键登录能力
  }
})
```
#### 参数说明：
*  回调参数说明：
 
    |回调参数|类型|描述|
    |----|----|----|
    | success|Boolean|是否具备一键登录能力|
    	
### 5. 自定义授权页面

#### 代码说明：
```
NativeModules.QuickLoginPlugin.setUiConfig(config, (success) => {
    if (success) {  
        // TODO:设置UI成功，可以进行取号接口调用
    } else {
    }
});
```

#### 参数说明：
*  基础参数：

    |参数|类型|是否必填|默认值|描述|
    |----|----|--------|------|----|
    |config|Object|是|无|自定义配置项|
    |success|Boolean|设置授权页面UI成功|
    
	
#### config 可配置项说明： android版

**<font color=red>开发者不得通过任何技术手段，将授权页面的隐私栏、手机掩码号、供应商品牌内容隐藏、覆盖</font>**<br>
**<font color=red>网易易盾与运营商会对应用授权页面进行审查，若发现上述违规行为，网易易盾有权将您的一键登录功能下线</font>**

![安卓规范示意图](https://nos.netease.com/cloud-website-bucket/fc608fc8c376e8b384e947e575ef8b5f.jpg)
![自定义展示图](https://nos.netease.com/cloud-website-bucket/410d6012173c5531b1065909c9484d36.jpg)

##### 状态栏
| 配置项                                              | 说明                                   |
| ------------------------------------------------- | -------------------------------------- |
| statusBarColor:String |     设置状态栏背景颜色，十六进制RGB值，如 "#ff0000"|
| isStatusBarDarkColor:boolean | 设置状态栏字体图标颜色是否为暗色(黑色) |

##### 导航栏

| 配置项                                              | 说明                                                         |
| :------------------------------------------------ | ------------------------------------------------------------ |
| navBackIcon:String                | 导航栏图标，图标放在 android drawable 下，这里配置图标名字 |
| navBackIconWidth:int     | 设置导航栏返回图标的宽度，单位 dp                                     |
| navBackIconHeight:int  | 设置导航栏返回图标的高度，单位 dp                                     |
| navBackIconGravity:int  | 设置导航栏返回图标位置，居左 3，居右 5，默认居左                                    |
| isHideBackIcon:boolean | 设置是否隐藏导航栏返回按钮                                       |
| navBackgroundColor:String | 设置导航栏背景颜色，十六进制RGB值，如 "#ff0000"                                          |
| navHeight:int                | 设置导航栏高度，单位 dp                                       |
| navTitle:String                  | 设置导航栏标题                                               |
| navTitleColor:String          | 设置导航栏标题颜色，十六进制RGB值，如 "#ff0000"值                                           |
| navTitleSize:int                 | 设置导航栏标题大小，单位 sp                                   |
| isNavTitleBold:boolean             | 设置导航栏标题是否为粗体                                     |
| isHideNav:boolean       | 设置是否隐藏导航栏                                           |

##### 应用 Logo

| 配置项                                        | 说明                                                         |
| :------------------------------------------ | ------------------------------------------------------------ |
| logoIconName:String        | 应用 logo 图标，图标放在 android drawable 下，这里配置图标名字 |
| logoWidth:int                 | 设置应用logo宽度，单位dp                                     |
| logoHeight:int               | 设置应用 logo 高度，单位 dp                                     |
| logoTopYOffset:int       | 设置 logo 顶部 Y 轴偏移，单位 dp                                  |
| logoBottomYOffset:int | 设置 logo 距离屏幕底部偏移，单位 dp                             |
| logoXOffset:int             | 设置 logo 水平方向的偏移，单位 dp                               |
| isHideLogo:boolean               | 设置是否隐藏 logo                                             |

##### 手机掩码

| 配置项                                                         | 说明                                                         |
| :----------------------------------------------------------- | ------------------------------------------------------------ |
| maskNumberColor:String                      | 设置手机掩码颜色，十六进制RGB值，如 "#ff0000"                                              |
| maskNumberSize:int                        | 设置手机掩码字体大小，单位 px                                 |
| maskNumberXOffset:int                  | 设置手机掩码水平方向的偏移，单位 dp                           |
| maskNumberDpSize:int                    | 设置手机掩码字体大小，单位 dp                                 |
| maskNumberTopYOffset:int      | 设置手机掩码顶部Y轴偏移，单位 dp                         |
| maskNumberBottomYOffset:int            | 设置手机掩码距离屏幕底部偏移，单位 dp                           |

##### 认证品牌

| 配置项                                            | 说明                                 |
| :---------------------------------------------- | ------------------------------------ |
| sloganSize:int                   | 设置认证品牌字体大小，单位 px         |
| sloganDpSize:int               | 设置认证品牌字体大小，单位 dp         |
| sloganColor:String                 | 设置认证品牌颜色，十六进制RGB值，如 "#ff0000"                      |
| sloganTopYOffset:int       | 设置认证品牌顶部 Y 轴偏移，单位 dp      |
| sloganBottomYOffset:int | 设置认证品牌距离屏幕底部偏移，单位 dp |
| sloganXOffset:int             | 设置认证品牌水平方向的偏移，单位 dp   |

##### 登录按钮

| 配置项                                                   | 说明                                                 |
| :----------------------------------------------------- | ---------------------------------------------------- |
| loginBtnText:String                   | 设置登录按钮文本                                     |
| loginBtnTextSize:int              | 设置登录按钮文本字体大小，单位 px                     |
| loginBtnTextDpSize:int          | 设置登录按钮文本字体大小，单位 dp                     |
| loginBtnTextColor:String           | 设置登录按钮文本颜色，十六进制RGB值，如 "#ff0000"                                 |
| loginBtnWidth:int                    | 设置登录按钮宽度，单位 dp                             |
| loginBtnHeight:int                  | 设置登录按钮高度，单位 dp                             |
| loginBtnBackgroundRes:String | 设置登录按钮背景图标，图标放在 android drawable 下，这里配置图标名字 |
| loginBtnTopYOffset:int          | 设置登录按钮顶部Y轴偏移，单位 dp                      |
| loginBtnBottomYOffset:int    | 设置登录按钮距离屏幕底部偏移，单位 dp                 |
| loginBtnXOffset:int                | 设置登录按钮水平方向的偏移，单位 dp                   |

##### 隐私协议

| 配置项                                                         | 说明                                                         |
| :----------------------------------------------------------- | ------------------------------------------------------------ |
| privacyTextColor:int                    | 设置隐私栏文本颜色，不包括协议 ，如若隐私栏协议文案为：登录即同意《中国移动认证条款》且授权 QuickLogin 登录， 则该API对除协议‘《中国移动认证条款》’区域外的其余文本生效 |
| privacyProtocolColor:int            | 设置隐私栏协议颜色 。例如：登录即同意《中国移动认证条款》且授权 QuickLogin 登录 ， 则该 API 仅对‘《中国移动认证条款》’文案生效 |
| privacySize:int                              | 设置隐私栏区域字体大小，单位 px                               |
| privacyDpSize:int                          | 设置隐私栏区域字体大小，单位 dp                               |
| privacyTopYOffset:int                  | 设置隐私栏顶部Y轴偏移，单位 dp                                |
| privacyBottomYOffset:int            | 设置隐私栏距离屏幕底部偏移，单位 dp                           |
| privacyTextMarginLeft:int                  | 设置隐私栏复选框和文字内边距，单位 dp                             |
| privacyMarginLeft:int                  | 设置隐私栏水平方向的偏移，单位 dp                             |
| privacyMarginRight:int               | 设置隐私栏右侧边距，单位 dp                                   |
| privacyState:boolean                        | 设置隐私栏协议复选框勾选状态，true 勾选，false 不勾选          |
| isHidePrivacyCheckBox:boolean          | 设置是否隐藏隐私栏勾选框                                     |
| isPrivacyTextGravityCenter:boolean                     | 设置隐私栏勾选框与文本协议对齐方式，可选择顶部（Gravity.TOP），居中（Gravity.CENTER），底部（Gravity.BOTTOM）等 |
| checkBoxGravity:int                     | 设置隐私栏勾选框与文本协议对齐方式，可选择顶部（48），居中（17），底部（80）等 |
| checkBoxWith:int                     | 设置隐私栏复选框宽度，单位 dp|
| checkBoxHeight:int                     | 设置隐私栏复选框高度，单位 dp |
| checkedImageName:string                | 设置隐私栏复选框选中时的图片资源，图标放在 android drawable 下，这里配置图标名字 |
| unCheckedImageName:String             | 设置隐私栏复选框未选中时的图片资源，图标放在 android drawable 下，这里配置图标名字 |
| privacyTextStart:String                 | 设置隐私栏声明部分起始文案 。如：隐私栏声明为"登录即同意《隐私政策》和《中国移动认证条款》且授权易盾授予本机号码"，则可传入"登录即同意" |
| isHidePrivacySmh:boolean                    | 是否隐藏运营商协议书名号                                           |
| protocolText:String                         | 设置隐私栏协议文本                                           |
| protocolLink:String                         | 设置隐私栏协议链接                                           |
| protocol2Text:String                       | 设置隐私栏协议 2 文本                                          |
| protocol2Link:String                       | 设置隐私栏协议 2 链接                                          |
| protocol3Text:String                       | 设置隐私栏协议 3 文本                                          |
| protocol3Link:String                       | 设置隐私栏协议 3 链接                                          |
| privacyTextEnd:String                     | 设置隐私栏声明部分尾部文案。如：隐私栏声明为"登录即同意《隐私政策》和《中国移动认证条款》且授权易盾授予本机号码"，则可传入"且授权易盾授予本机号码" |

##### 协议详情 Web 页面导航栏

| 配置项                                                         | 说明                                                         |
| :----------------------------------------------------------- | ------------------------------------------------------------ |
| protocolNavTitle:String             | 设置协议 Web 页面导航栏标题，如果需要根据不同运营商设置不同标题|
| protocolNavBackIcon:String       | 设置协议 Web 页面导航栏返回图标，图标放在 android drawable 下，这里配置图标名字                                |
| protocolNavColor:String                | 设置协议Web页面导航栏颜色                                    |
| protocolNavHeight:int             | 设置协议 Web 页面导航栏高度                                    |
| protocolNavTitleSize:int        | 设置协议Web页面导航栏标题大小，单位 px                        |
| protocolNavTitleDpSize:int    | 设置协议 Web 页面导航栏标题大小，单位 dp                        |
| protocolNavBackIconWidth:int | 设置协议 Web 页面导航栏返回按钮宽度，单位 dp                    |
| protocolNavBackIconHeight:int | 设置协议 Web 页面导航栏返回按钮高度，单位 dp                    |

##### 其他

| 配置项                                                         | 说明                                                         |
| :----------------------------------------------------------- | ------------------------------------------------------------ |
| backgroundImage:String                 | 设置登录页面背景，图片放在 android drawable 下，这里配置图片名字             |
| backgroundGif:String                       | 设置登录页面背景为 Gif，Gif 资源需要放置到android drawable 目录下，传入资源名称即可 |
| backgroundVideo:String      | 设置登录页面背景为视频，参数 videoPath 为背景 Video 文件路径:(支持本地路径如："android.resource://" + context.getPackageName() + "/" + R.raw.xxxVideo；支持网络路径如"https://xxx"(建议下载到本地后使用本地路径，网络路径由于网络环境的不可控体验不如直接加载本地视频) |
| backgroundVideoImage:String                           | 设置视频背景时的预览图，配合 backgroundVideo 使用 |
| enterAnimation:String | 设置授权页进场动画，enterAnimation 进场动画xml无后缀文件名。放置在 android anim目录下 |
| exitAnimation:String      | 设置授权页退出动画，exitAnimation 进场动画xml无后缀文件名。放置在 android anim目录下 |
| isLandscape:boolean      | 是否横屏 |
| isDialogMode:boolean      | 是否弹窗模式 |
| dialogWidth:int      | 授权页弹窗宽度，单位 dp |
| dialogHeight:int      | 授权页弹窗高度，单位 dp |
| dialogX:int      | 授权页弹窗 X 轴偏移量，以屏幕中心为原点 |
| dialogY:int      | 授权页弹窗 Y 轴偏移量，以屏幕中心为原点 |
| isBottomDialog:boolean      | 授权页弹窗是否贴于屏幕底部<br>true：显示在屏幕底部，dialogY 失效<br> false：不显示在屏幕底部，以 dialogY 参数为准 |
| isProtocolDialogMode:boolean      | 协议详情页是否开启弹窗模式 |
| isPrivacyDialogAuto:boolean      | 协议未勾选弹窗点击是否自动登录 |
| isShowPrivacyDialog:boolean      | 是否显示协议未勾选默认弹窗 |
| privacyDialogText:String      | 协议未勾选弹窗自定义message |
| privacyDialogSize:float      | 协议未勾选弹窗文本字体大小 |

##### 自定义view

| 配置项                                            | 说明                                 |
| :---------------------------------------------- | ------------------------------------ |
| widgets:JsonArray                   | 自定义view数组        |
|  ∟ viewId:String          | 控件 id         |
|  ∟ type:String            | 控件类型，可选值为 TextView、Button、ImageView        |
|  ∟ top:int       | 控件距离顶部的偏移，单位 dp|
|  ∟ left:int       | 控件距离左侧的偏移，单位 dp|
|  ∟ right:int       | 控件距离右侧的偏移，单位 dp|
|  ∟ bottom:int       | 控件距离底部的偏移，和top互斥，单位 dp|
|  ∟ width:int | 控件宽度，单位 dp |
|  ∟ height:int| 控件高度，单位 dp |
|  ∟ text:String| 控件文本，单位 dp |
|  ∟ font:int| 控件文本大小，单位 sp |
|  ∟ textColor:String| 控件文本颜色，十六进制颜色码 |
|  ∟ clickable:boolean| 控件是否可点击，单位 sp |
|  ∟ backgroundColor:String| 控件背景颜色，十六进制颜色码 |
|  ∟ backgroundImgPath:String| 控件背景图片，图片放在 android drawable 下，这里配置图片名字  |
|  ∟ positionType:int| 添加控件的位置类型，1表示位于导航栏下方的body部分 |

#### config 可配置项说明： iOS版
设计规范概览
**<font color=red>开发者不得通过任何技术手段，将授权页面的隐私栏、手机掩码号、供应商品牌内容隐藏、覆盖</font>**<br>
**<font color=red>网易易盾与运营商会对应用授权页面进行审查，若发现上述违规行为，网易易盾有权将您的一键登录功能下线</font>**
![iOS设计规范](https://nos.netease.com/cloud-website-bucket/58fca2df814059b54171724b7702b06f.jpg)
![自定义展示图](https://nos.netease.com/cloud-website-bucket/410d6012173c5531b1065909c9484d36.jpg)

##### 基础配置
| 属性 | 说明 |
| :-------- | -------- |
| currentVC | 当前VC,注意:要用一键登录这个值必传 |
| rootViewController   | 设置应用的根控制器，用作隐私协议的弹出，如果不传，则使用默认值|
| presentDirectionType   | 设置授权页的弹出方式<br>NTESPresentDirectionPush 表示从右边弹出 <br>NTESPresentDirectionPresent 表示从底部弹出|
| backgroundColor   |设置授权页面背景颜色|
| authWindowPop | 设置窗口类型<br>NTESAuthWindowPopFullScreen 表示全屏模式<br> NTESAuthWindowPopCenter 表示窗口在屏幕的中间<br> NTESAuthWindowPopBottom 表示窗口在屏幕的底部(不支持横屏)|
| faceOrientation   |设置授权页面方向<br>UIInterfaceOrientationUnknown表示设备方向未知<br>UIInterfaceOrientationPortrait 表示设置保持直立<br>UIInterfaceOrientationPortraitUpsideDown 表示设备上下颠倒 <br>UIInterfaceOrientationLandscapeLeft 表示设备向左旋转 <br>UIInterfaceOrientationLandscapeRight 表示设备向右旋转 |
| bgImage   |设置授权转背景图片，例如 ：model.bgImage = [UIImage imageNamed:]|  
| contentMode   |设置背景图片显示模式|  
| modalPresentationStyle   |设置 present 控制器的展示方式。如果弹窗模式下。modalPresentationStyle为UIModalPresentationOverFullScreen| 

##### 转场动画
| 属性 | 说明 |
| :-------- | -------- |
| modalTransitionStyle | 设置授权转场动画<br> UIModalTransitionStyleCoverVertical 表示下推<br>UIModalTransitionStyleFlipHorizontal 表示翻转<br>UIModalTransitionStyleCrossDissolve 表示淡出|

##### 自定义控件
| 属性 | 说明 |
| :-------- | -------- |
| customViewBlock | 设置授权界面自定义控件View的block<br>例如 ：model.customViewBlock = ^(UIView * _Nullable customView) {  /// customView就是授权页的 view，添加控件到 customView 即可} |

##### 背景设置视频
 
| 属性 | 说明 |
| :-------- | -------- |
| localVideoFileName | 设置视频本地名称 例如xx.mp4* |
| isRepeatPlay   | 设置是否重复播放视频，YES 表示重复播放，NO 表示不重复播放|
| videoURL   | 设置网络视频的地址|
| videoViewBlock   |设置自定义视频控件，例如：model.videoViewBlock = ^(UIView * _Nullable videoView) { // videoView 放置视频的控件,[videoView addSubview:视频]}|

##### 背景设置 Gif

| 属性 | 说明 |
| :-------- | -------- |
| animationRepeatCount | 设置动画重复的次数 -1无限重复 |
| animationImages   | 设置图片数组,例如：@[UIImage imageNamed:@"pic_yjdl"]|
| animationDuration   | 设置动画的时长|

##### 状态栏
| 属性                                              | 说明                                                         |
| :-------- | -------- |
| statusBarStyle | 设置状态栏样式<br> iOS13之前 UIStatusBarStyleDefault表示文字黑色，UIStatusBarStyleLightContent表示文字白色<br> iOS13之后 UIStatusBarStyleDefault表示自动选择黑色或白色，UIStatusBarStyleDarkContent 表示文字黑色，UIStatusBarStyleLightContent 表示文字白色|
					
##### 导航栏

| 属性                                              | 说明                                                         |
| :-------- | -------- |
| navBarHidden | 导航栏是否隐藏 |
| navBgColor   | 设置导航栏背景颜色 |
| navText      | 设置导航栏标题 |
| navTextFont  | 设置导航栏标题字体大小|
| navTextColor | 设置导航栏标题字体颜色|
| navTextHidden| 设置导航栏标题是否隐藏，默认不隐藏|
| navReturnImg | 设置导航返回图标，例如：[UIImage imageNamed:@"back-1"] |
| navReturnImgLeftMargin | 设置导航返回图标距离屏幕左边的距离，默认0  |
| navReturnImgBottomMargin | 设置导航返回图标距离屏幕底部的距离，默认0 |
| navReturnImgWidth  | 设置导航返回图标的宽度，默认44 |
| navReturnImgHeight| 设置导航返回图标的高度 ,  默认44 |
| navControl | 设置导航栏右侧自定义控件 传非UIBarButtonItem对象|
| customNavBlock | 设置导航栏上自定义控件, 可在导航栏上自由的添加自己想要的控件<br>例如： model.customNavBlock = ^(UIView * _Nullable customNavView) {/// 添加控件到customNavView上};                                      |

##### 应用 Logo


| 属性                                              | 说明                                                         |
| :-------- | -------- |
| logoIconName | 设置logo图片, 例如 ： model.logoImg = [UIImage imageNamed:@"logo1"]|
| logoWidth   | 设置logo图片宽度 |
| logoHeight  | 设置logo图片高度 |
| logoOffsetTopY  |设置logo图片沿Y轴偏移量， logoOffsetTopY为距离屏幕顶部的距离 ，默认为20|
| logoOffsetX | 设置logo图片沿X轴偏移量，logoOffsetX = 0居中显示|
| logoHidden| 设置logo图片是否隐藏，默认不隐藏|


##### 手机掩码

| 属性                                              | 说明                                                         |
| :-------- | -------- |
| numberColor | 设置手机号码字体颜色|
| numberFont   | 设置手机号码字体大小， 默认18 |
| numberOffsetTopY  | 设置手机号码沿Y轴偏移量，  numberOffsetTopY为距离屏幕顶部的距离 ，默认为100|
| numberOffsetX | 设置logo图片沿X轴偏移量，logoOffsetX = 0居中显示|
| numberHeight| 设置手机号码框的高度 默认27|
| numberBackgroundColor| 设置手机号码的背景颜色|
| numberCornerRadius| 设置手机号码的控件的圆角|
| numberLeftContent| 设置手机号码的左边描述内容，默认为空|
| numberRightContent| 设置手机号码的右边描述内容，默认为空色|


##### 认证品牌

| 属性                                              | 说明                                                         |
| :-------- | -------- |
| brandColor | 设置认证服务品牌文字颜色|
| brandBackgroundColor   | 设置认证服务品牌背景颜色|
| brandFont  | 设置认证服务品牌文字字体 默认12|
| brandWidth | 设置认证服务品牌的宽度， 默认200|
| brandHeight| 设置认证服务品牌的高度， 默认16|
| brandOffsetX| 设置认证服务品牌X偏移量 ，brandOffsetX = 0居中显示|
| brandOffsetTopY| 设置认证服务品牌Y偏移量, brandOffsetTopY为距离屏幕顶部的距离 ，默认为150|
| brandHidden| 设置是否隐藏认证服务品牌，默认显示|

##### 登录按钮


| 属性                                              | 说明                                                         |
| :-------- | -------- |
| logBtnText | 设置登录按钮文本|
| logBtnTextFont   | 设置登录按钮字体大小|
| logBtnTextColor  | 设置登录按钮文本颜色|
| logBtnOffsetTopY | 设置登录按钮Y偏移量 ，logBtnOffsetTopY为距离屏幕顶部的距离 ，默认为200|
| logBtnRadius| 设置登录按钮圆角，默认8|
| logBtnUsableBGColor| 设置登录按钮背景颜色|
| logBtnEnableImg| 设置登录按钮可用状态下的背景图片|
| logBtnHighlightedImg| 登录按钮高亮状态下的背景图片|
| logBtnOriginLeft| 登录按钮的左边距 ，横屏默认40 ，竖屏默认260|
| logBtnOriginRight| 设置登录按钮的左边距，横屏默认40 ，竖屏默认260|
| logBtnHeight| 设置登录按钮的高度，默认44|
| startPoint| 设置设置渐变颜色方向，左上点为(0,0), 右下点为(1,1)|
| endPoint| 设置设置渐变颜色方向，左上点为(0,0), 右下点为(1,1)|
| locations| 设置颜色变化点，取值范围 0.0~1.0 |
| colors| 设置渐变色数组，需要转换为CGColor颜色|

##### 隐私协议
          
若勾选框需要展示，请务必设置勾选框的选中态图片与未选中态图片
协议未勾选时，登录按钮是否可点击可以自定义设置，弹窗提示的样式也可以自定义

| 属性                                              | 说明                                                         |
| :-------- | -------- |
| prograssHUDBlock | 设置协议未勾选时，自定义弹窗样式|
| loadingViewBlock   | 设置自定义Loading View, 点击登录按钮时，可自定义加载进度样式|
| uncheckedImg  | 设置复选框未选中时图片|
| checkedImg | 设置复选框选中时图片|
| checkboxWH| 设置复选框大小（只能正方形) ，默认 12|
| privacyState| 设置复选框默认状态 默认:NO |
| checkBoxAlignment| 设置隐私条款check框位置 <br> NSCheckBoxAlignmentTop 表示相对协议顶对齐<br>NSCheckBoxAlignmentCenter 表示相对协议中对齐 <br>NSCheckBoxAlignmentBottom 表示相对协议下对齐 默认顶对齐|
| checkedSelected| 设置复选框勾选状态，YES:勾选，NO:取消勾选状态|
| checkBoxMargin| 设置复选框距离隐私条款的边距 默认 8|
| appPrivacyOriginLeftMargin| 设置隐私条款距离屏幕左边的距离 默认 60|
| appPrivacyOriginRightMargin| 设置隐私条款距离屏幕右边的距离 默认 40|
| appPrivacyOriginBottomMargin| 设置隐私条款距离屏幕的距离 默认 40|
| privacyNavReturnImg| 设置用户协议界面，导航栏返回图标，默认用导航栏返回图标|
| appPrivacyText| 设置隐私的内容模板：全句可自定义但必须保留"《默认》"字段表明SDK默认协议,否则设置不生效。必设置项（参考SDK的demo）appPrivacyText设置内容：登录并同意《默认》和易盾协议1、网易协议2登录并支持一键登录，展示：登录并同意中国移动条款协议和易盾协议1、网易协议2登录并支持一键登录 |
| appFPrivacyText| 设置开发者隐私条款协议名称（第一个协议）|
| appFPrivacyURL| 设置开发者隐私条款协议url（第一个协议）|
| appSPrivacyText| 设置开发者隐私条款协议名称（第二个协议）|
| appSPrivacyURL| 设置开发者隐私条款协议url（第二个协议）|
| shouldHiddenPrivacyMarks| 设置是否隐藏"《默认》" 两边的《》，默认不隐藏|
| privacyColor| 设置隐私条款名称颜色|
| privacyFont| 设置隐私条款字体的大小|
| protocolColor| 设置协议条款协议名称颜色|
| appPrivacyLineSpacing| 设置隐私协议的行间距, 默认是1|
| appPrivacyWordSpacing| 设置隐私协议的字间距, 默认是0|
| progressColor| 设置用户协议界面，进度条颜色|

##### 弹窗模式

| 属性                                              | 说明                                                         |
| :-------- | -------- |
| popBackgroundColor   | 设置窗口模式的背景颜色|
| authWindowWidth  | 设置弹窗的宽度，竖屏状态下默认是 300，横屏状态下默认是 335 |
| authWindowHeight | 设置弹窗高度，竖屏状态下默认是335， 横屏状态下默认是300  ⚠️底部半屏弹窗模式的高度可通过修改 authWindowHeight，调整高度 默认335pt|
| closePopImg| 设置弹窗模式下关闭按钮的图片，⚠️(必传)|
| closePopImgWidth| 设置弹窗模式下关闭按钮图片的宽度 默认20*|
| closePopImgHeight| 设置弹窗模式下关闭按钮图片的高度 默认20|
| closePopImgOriginY| 设置关闭按钮距离顶部的距离，默认距离顶部10，距离 = 10 + closePopImgOriginY|
| closePopImgOriginX| 设置关闭按钮距离父视图右边的距离，默认距离为10，距离 = 10 + closePopImgOriginX|
| authWindowCenterOriginY| 设置居中弹窗沿Y轴移动的距离。例如 ：authWindowCenterOriginY = 10 表示中间点沿Y轴向下偏移10个像素|
| authWindowCenterOriginX| 设置居中弹窗沿X轴移动的距离。例如 ：authWindowCenterOriginX = 10 表示中间点沿X轴向右偏移10个像素|
| popCenterCornerRadius| 设置居中弹窗模式下，弹窗的圆角，默认圆角为16|
| popBottomCornerRadius| 设置底部弹窗模式下，弹窗的圆角，默认圆角为16，注：只可修改顶部左右二边的值|
| isOpenSwipeGesture| 设置底部弹窗模式下，是否开启轻扫手势，向下轻扫关闭弹窗。默认关闭|


##### 点击事件的回调

| 属性                                              | 说明                                                         |
| :-------- | -------- |
| backActionBlock | 设置返回按钮点击事件回调，例如：model.backActionBlock = ^{NSLog(@"点击了返回按钮");}|
| closeActionBlock | 设置弹窗模式下关闭事件的回调，例如：model.closeActionBlock = ^{NSLog(@"点击了关闭按钮")}|
| loginActionBlock | 设置登录按钮点击事件回调，例如：model.loginActionBlock = ^{NSLog(@"点击了登录按钮")}|
| checkActionBlock | 设置复选框点击事件回调，isChecked 等于 YES 选中复选框； isChecked 等于 NO 取消复选框。例如：model.checkActionBlock = ^(BOOL isChecked) {}|
| privacyActionBlock | 设置协议点击事件回调<br> privacyType 等于 0 表示点击默认协议 <br> privacyType 等于 1 表示点击第1个协议 <br> privacyType 等于 2 表示点击第2个协议 <br>例如：model.privacyActionBlock = ^(int privacyType) {}|
| pageCustomBlock | 设置协议点击事件回调，不会跳转到默认的协议页面。开发者可以在回调里，自行跳转到自定义的协议页面|  

### 6. 调起授权页面

#### 代码说明：
```
quichLoginPlugin.login((success, data) => {
  if (success) {
    // TODO: 授权成功处理，可以进行关闭授权页、服务端验证等
    NativeModules.QuickLoginPlugin.closeAuthController();
  } else {
    // TODO: 授权失败处理
  }
})
```
#### 参数说明：
*  回调参数说明：
 
    |回调参数|类型|描述|
    |----|----|----|
    | success|Boolean|授权是否成功|
	| data.accessToken|String|如果取号成功返回运营商授权码，否则无此字段|
	| data.desc|String|如果取号失败，返回的错误详情, 否则返回"取号成功|

### 7. 事件监听	

#### 代码说明：

```
const emitter = new NativeEventEmitter(NativeModules.QuickLoginPlugin)
emitter.addListener('uiCallback', (value) => {
  // TODO: 处理监听事件
})
```
value 是一个 Map ，里面包含各种 key 和对应的值

* iOS value 值说明：
 
    |回调参数|类型|描述|
    |-----|----|----|
    | value.action|String|authViewDidLoad 表示正在加载授权页<br>authViewWillAppear 表示授权页已经出现 <br> authViewWillDisappear 表示授权页将要消失 <br> authViewDidDisappear表示授权页已经消失<br>authViewDealloc 表示授权页销毁<br> appDPrivacy 表示点击了默认协议<br>appFPrivacy 表示点击了议第一个协议点击 <br> appSPrivacy 表示点击了第二个协议 <br> loginAction 表示点击了登录按钮，data.checked = true 表示在点击登录按钮时复选框选已选中反之<br> checkedAction 表示点击了复选框，data.checked = true 表示复选框已选中反之|
*  Android value 值说明：

    |回调参数|类型|描述|
    |----|----|----|
    | value.viewId | String | 自定义 view 点击，返回自定义 view 的 id |
	| value.lifecycle|String|onCreate - 页面创建，onStart - 页面已开始活动，onResume - 页面展示，onPause - 页面非活动状态，onStop - 页面已停止，onDestroy - 页面销毁|
	| value.clickViewType|String|privacy - 隐私协议点击事件，checkbox - 复选框点击事件（含isCheckboxChecked: 0/1），loginButton - 登录按钮点击事件（含isCheckboxChecked: 0/1），leftBackButton - 左上角返回按钮点击事件|
	| value.isCheckboxChecked|String|协议复选框的值，仅在复选框事件和登录按钮点击事件中存在|

### 8. 关闭授权页面

#### 代码说明：
```
quichLoginPlugin.closeAuthController()
```

### 9. 本机校验

在初始化之后执行，本机校验和一键登录可共用初始化，本机校验界面需自行实现

#### 代码说明：
```
quichLoginPlugin.verifyPhoneNumber(String phoneNumber,(success, data) => {
  if (success) {
    // TODO: 本机校验成功处理
  } else {
    // TODO: 本机校验失败处理
  }
})
```
#### 参数说明：
*  入参说明：

    |参数|类型|是否必填|默认值|描述|
    |----|----|--------|------|----|
    |phoneNumber|String|是|无| 待校验手机号 |
    
*  回调参数说明：
 
    |回调参数|类型|描述|
    |----|----|----|
    | success|Boolean|授权是否成功|
	| data.accessToken|String|如果本机校验返回运营商授权码，否则无此字段|
	| data.desc|String|如果本机校验失败，返回的错误详情, 否则返回"本机校验成功|


