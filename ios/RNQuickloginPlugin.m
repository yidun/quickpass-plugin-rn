
#import "RNQuickloginPlugin.h"
#import <NTESQuickPass/NTESQuickPass.h>
#import "UIColor+NTESQuickPass.h"
#import "UIImage+KKImage.h"

@interface RNQuickloginPlugin () <NTESQuickLoginManagerDelegate>

@property (nonatomic, strong) NTESQuickLoginModel *customModel;

@property (nonatomic, copy) NSDictionary *option;

@end

@implementation RNQuickloginPlugin

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(initQuickLogin:(NSString *)businessID)
{
  dispatch_async(dispatch_get_main_queue(), ^{
    [NTESQuickLoginManager sharedInstance].delegate = self;
    [[NTESQuickLoginManager sharedInstance] registerWithBusinessID:businessID];
  });
}

RCT_EXPORT_METHOD(prefetchNumber:(RCTResponseSenderBlock)callback)
{
  dispatch_async(dispatch_get_main_queue(), ^{

     [[NTESQuickLoginManager sharedInstance] getPhoneNumberCompletion:^(NSDictionary * _Nonnull resultDic) {
          NSNumber *boolNum = [resultDic objectForKey:@"success"];
          BOOL success = [boolNum boolValue];
          dispatch_async(dispatch_get_main_queue(), ^{
             callback(@[@(success), resultDic]);
          });
      }];
  });
}

RCT_EXPORT_METHOD(closeAuthController)
{
   dispatch_async(dispatch_get_main_queue(), ^(){
     [[NTESQuickLoginManager sharedInstance] closeAuthController:^{
       
     }];
   });
}

RCT_EXPORT_METHOD(setUiConfig:(NSDictionary *)option)  {
  dispatch_async(dispatch_get_main_queue(), ^(){
    self.option = option;
    NSDictionary *dict = self.option;
      NTESQuickLoginModel *customModel = [[NTESQuickLoginModel alloc] init];
      customModel.customViewBlock = ^(UIView * _Nullable customView) {
          NSArray *widgets = [dict objectForKey:@"widgets"];
          for (NSInteger i = 0; i < widgets.count; i++) {
              NSDictionary *widgetsDict = widgets[i];
              NSString *type = [widgetsDict objectForKey:@"type"];
              if ([type isEqualToString:@"UIButton"]) {
  //                int buttonType = [[widgetsDict objectForKey:@"UIButtonType"] intValue];
                  NSString *title = [widgetsDict objectForKey:@"title"];
                  NSString *titleColor = [widgetsDict objectForKey:@"titleColor"];
                  int titleFont = [[widgetsDict objectForKey:@"titleFont"] intValue];
                  int cornerRadius = [[widgetsDict objectForKey:@"cornerRadius"] intValue];
                  NSDictionary *frame = [widgetsDict objectForKey:@"frame"];
                  NSString *image = [widgetsDict objectForKey:@"image"];
                  
                  int x = 0;
                  int y = 0;
                  int width = [[frame objectForKey:@"width"] intValue];
                  int height = [[frame objectForKey:@"height"] intValue];
                  if ([frame objectForKey:@"mainScreenLeftDistance"]) {
                      x = [[frame objectForKey:@"mainScreenLeftDistance"] intValue];
                  }
                  if ([frame objectForKey:@"mainScreenCenterXWithLeftDistance"]) {
                      x = ([UIScreen mainScreen].bounds.size.width - width) / 2 - [[frame objectForKey:@"mainScreenCenterXWithLeftDistance"] intValue];
                  }
                  if ([frame objectForKey:@"mainScreenRightDistance"]) {
                      int mainScreenRightDistance = [[frame objectForKey:@"mainScreenRightDistance"] intValue];
                      x = [UIScreen mainScreen].bounds.size.width - mainScreenRightDistance - width;
                  }
                  
                  if ([frame objectForKey:@"mainScreenTopDistance"]) {
                      y = [[frame objectForKey:@"mainScreenTopDistance"] intValue];
                  }
                  if ([frame objectForKey:@"mainScreenBottomDistance"]) {
                      int mainScreenBottomDistance = [[frame objectForKey:@"mainScreenBottomDistance"] intValue];
                      y = [UIScreen mainScreen].bounds.size.height - mainScreenBottomDistance - height;
                  }
                  
                  Class class = NSClassFromString(type);
                  UIButton *button = (UIButton *)[[class alloc] init];
                  [button setImage:[UIImage imageNamed:image] forState:UIControlStateNormal];
                  [button addTarget:self action:@selector(buttonDidTipped:) forControlEvents:UIControlEventTouchUpInside];
                  button.tag = i;
                  button.frame = CGRectMake(x, y, width, height);
                  [button setTitle:title forState:UIControlStateNormal];
                  [button setTitleColor:[UIColor ntes_colorWithHexString:titleColor] forState:UIControlStateNormal];
                  button.titleLabel.font = [UIFont systemFontOfSize:titleFont];
                  button.layer.cornerRadius = cornerRadius;
                  button.layer.masksToBounds = YES;
                  [customView addSubview:button];
              } else if ([type isEqualToString:@"UILabel"]) {
                  NSDictionary *widgetsDict = widgets[i];
                  NSString *type = [widgetsDict objectForKey:@"type"];
                  NSString *text = [widgetsDict objectForKey:@"text"];
                  NSString *textColor = [widgetsDict objectForKey:@"textColor"];
                  int font = [[widgetsDict objectForKey:@"font"] intValue];
                  int textAlignment = [[widgetsDict objectForKey:@"textAlignment"] intValue];
                  NSDictionary *frame = [widgetsDict objectForKey:@"frame"];
                  
                  int x = 0;
                  int y = 0;
                  int width = [[frame objectForKey:@"width"] intValue];
                  int height = [[frame objectForKey:@"height"] intValue];
                  if ([frame objectForKey:@"mainScreenLeftDistance"]) {
                      x = [[frame objectForKey:@"mainScreenLeftDistance"] intValue];
                  }
                  if ([frame objectForKey:@"mainScreenCenterXWithLeftDistance"]) {
                      x = ([UIScreen mainScreen].bounds.size.width - width) / 2 - [[frame objectForKey:@"mainScreenCenterXWithLeftDistance"] intValue];
                  }
                  if ([frame objectForKey:@"mainScreenRightDistance"]) {
                      int mainScreenRightDistance = [[frame objectForKey:@"mainScreenRightDistance"] intValue];
                      x = [UIScreen mainScreen].bounds.size.width - mainScreenRightDistance - width;
                  }
                              
                  if ([frame objectForKey:@"mainScreenTopDistance"]) {
                      y = [[frame objectForKey:@"mainScreenTopDistance"] intValue];
                  }
                  if ([frame objectForKey:@"mainScreenBottomDistance"]) {
                      int mainScreenBottomDistance = [[frame objectForKey:@"mainScreenBottomDistance"] intValue];
                      y = [UIScreen mainScreen].bounds.size.height - mainScreenBottomDistance - height;
                  }
                    
                  Class class = NSClassFromString(type);
                  UILabel *label = (UILabel *)[[class alloc] init];
                  label.tag = i;
                  UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(labelDidTipped:)];
                  [label addGestureRecognizer:tap];
                  label.userInteractionEnabled = YES;
                  label.text = text;
                  label.textColor = [UIColor ntes_colorWithHexString:textColor];
                  label.font = [UIFont systemFontOfSize:font];
                  label.textAlignment = textAlignment;
                  label.frame = CGRectMake(x, y, width, height);

                  [customView addSubview:label];
              }
          }
      };
      
      customModel.backgroundColor = [UIColor ntes_colorWithHexString:[dict objectForKey:@"backgroundColor"]];
      customModel.bgImage = [UIImage imageNamed:[dict objectForKey:@"bgImage"]];
      customModel.navTextFont = [UIFont systemFontOfSize:[[dict objectForKey:@"navTextFont"] intValue]];
      customModel.navText = [dict objectForKey:@"navText"];
      customModel.navTextColor = [UIColor ntes_colorWithHexString:[dict objectForKey:@"navTextColor"]];
      customModel.navBgColor = [UIColor ntes_colorWithHexString:[dict objectForKey:@"navBgColor"]];
      customModel.navTextHidden = [[dict objectForKey:@"navTextHidden"] boolValue];
    customModel.logoImg = [UIImage imageNamed:[dict objectForKey:@"logoIconName"]];
      customModel.numberColor = [UIColor ntes_colorWithHexString:[dict objectForKey:@"numberColor"]];
      customModel.numberFont = [UIFont systemFontOfSize:[[dict objectForKey:@"numberFont"] intValue]];
      customModel.brandColor = [UIColor ntes_colorWithHexString:[dict objectForKey:@"brandColor"]];
      customModel.brandFont = [UIFont systemFontOfSize:[[dict objectForKey:@"brandFont"] intValue]];
      customModel.brandHidden = [[dict objectForKey:@"brandHidden"] boolValue];
      customModel.brandHeight = [[dict objectForKey:@"brandHeight"] intValue];
      customModel.logBtnTextFont = [UIFont systemFontOfSize:[[dict objectForKey:@"loginBtnTextSize"] intValue]];
      customModel.logBtnText = [dict objectForKey:@"logBtnText"];
      customModel.logBtnTextColor = [UIColor ntes_colorWithHexString:[dict objectForKey:@"logBtnTextColor"]];
      customModel.logBtnUsableBGColor = [UIColor ntes_colorWithHexString:[dict objectForKey:@"logBtnUsableBGColor"]];
    customModel.closePopImg = [UIImage imageNamed:[dict objectForKey:@"closePopImg"]];
      customModel.numberBackgroundColor = [UIColor ntes_colorWithHexString:[dict objectForKey:@"numberBackgroundColor"]];
      customModel.numberHeight = [[dict objectForKey:@"numberHeight"] intValue];
      customModel.numberCornerRadius = [[dict objectForKey:@"numberCornerRadius"] intValue];
      customModel.numberLeftContent = [dict objectForKey:@"numberLeftContent"];
      customModel.numberRightContent = [dict objectForKey:@"numberRightContent"];
      customModel.faceOrientation = [[dict objectForKey:@"faceOrientation"] intValue];
      customModel.loginDidDisapperfaceOrientation = [[dict objectForKey:@"loginDidDisapperfaceOrientation"] intValue];
      customModel.logoHeight = [[dict objectForKey:@"logoHeight"] intValue];
      customModel.logoHidden = [[dict objectForKey:@"logoHidden"] boolValue];
      customModel.modalTransitionStyle = [[dict objectForKey:@"modalTransitionStyle"] intValue];
      customModel.privacyFont = [UIFont systemFontOfSize:[[dict objectForKey:@"privacyFont"] intValue]];
      int prograssHUDBlock = [[dict objectForKey:@"prograssHUDBlock"] intValue];
      if (prograssHUDBlock) {
          customModel.prograssHUDBlock = ^(UIView * _Nullable prograssHUDBlock) {
          };
      }
        
      int loadingViewBlock = [[dict objectForKey:@"loadingViewBlock"] intValue];
      if (loadingViewBlock) {
          customModel.loadingViewBlock = ^(UIView * _Nullable customLoadingView) {
                
          };
      }
        
        customModel.appPrivacyText = [dict objectForKey:@"appPrivacyText"];
        customModel.appFPrivacyText = [dict objectForKey:@"appFPrivacyText"];
        customModel.appFPrivacyURL = [dict objectForKey:@"appFPrivacyURL"];
        customModel.appSPrivacyText = [dict objectForKey:@"appSPrivacyText"];
        customModel.appSPrivacyURL = [dict objectForKey:@"appSPrivacyURL"];
        customModel.appPrivacyOriginLeftMargin = [[dict objectForKey:@"appPrivacyOriginLeftMargin"] doubleValue];
        customModel.appPrivacyOriginRightMargin = [[dict objectForKey:@"appPrivacyOriginRightMargin"] doubleValue];
          customModel.appPrivacyOriginBottomMargin = [[dict objectForKey:@"appPrivacyOriginBottomMargin"] doubleValue];
        
        customModel.appFPrivacyTitleText = [dict objectForKey:@"appFPrivacyTitleText"];
        customModel.appPrivacyTitleText = [dict objectForKey:@"appPrivacyTitleText"];
        customModel.appSPrivacyTitleText = [dict objectForKey:@"appSPrivacyTitleText"];
        customModel.appPrivacyAlignment = [[dict objectForKey:@"appPrivacyAlignment"] intValue];
        customModel.isOpenSwipeGesture = [[dict objectForKey:@"isOpenSwipeGesture"] boolValue];
        customModel.logBtnOffsetTopY = [[dict objectForKey:@"logBtnOffsetTopY"] doubleValue];
        customModel.logBtnHeight = [[dict objectForKey:@"logBtnHeight"] doubleValue];
        customModel.brandOffsetTopY = [[dict objectForKey:@"brandOffsetTopY"] doubleValue];
        customModel.brandOffsetX = [[dict objectForKey:@"brandOffsetX"] doubleValue];
        customModel.numberOffsetTopY = [[dict objectForKey:@"numberOffsetTopY"] doubleValue];
        customModel.numberOffsetX = [[dict objectForKey:@"numberOffsetX"] doubleValue];
        customModel.checkBoxAlignment = [[dict objectForKey:@"checkBoxAlignment"] intValue];
        customModel.checkBoxMargin = [[dict objectForKey:@"checkBoxMargin"] intValue];
        customModel.checkboxWH = [[dict objectForKey:@"checkboxWH"] intValue];
        customModel.checkedHidden = [[dict objectForKey:@"checkedHidden"] boolValue];
        customModel.checkedImg = [UIImage imageNamed:@"checkedImageName"];
  //      customModel.checkedImg  = [UIImage imageWithName:@"checkedImageName" class:[self class]];
        customModel.uncheckedImg = [UIImage imageNamed:@"unCheckedImageName"];
  //      customModel.uncheckedImg =  [UIImage imageWithName:@"unCheckedImageName" class:[self class]];
        customModel.logBtnOriginRight = [[dict objectForKey:@"logBtnOriginRight"] intValue];
        customModel.logBtnOriginLeft = [[dict objectForKey:@"logBtnOriginLeft"] intValue];
        
        
        customModel.logoOffsetTopY = [[dict objectForKey:@"logoOffsetTopY"] doubleValue];
        customModel.logoOffsetX = [[dict objectForKey:@"logoOffsetX"] doubleValue];
        customModel.logBtnRadius = [[dict objectForKey:@"logBtnRadius"] intValue];
        customModel.logoWidth = [[dict objectForKey:@"logoWidth"] intValue];
        
        customModel.logoOffsetTopY = [[dict objectForKey:@"logoOffsetTopY"] doubleValue];
        customModel.logoOffsetX = [[dict objectForKey:@"logoOffsetX"] doubleValue];
        customModel.brandBackgroundColor = [UIColor ntes_colorWithHexString:[dict objectForKey:@"brandBackgroundColor"]];
        customModel.privacyColor = [UIColor ntes_colorWithHexString:[dict objectForKey:@"privacyColor"]];
        customModel.protocolColor = [UIColor ntes_colorWithHexString:[dict objectForKey:@"protocolColor"]];
        
        customModel.privacyNavReturnImg = [UIImage imageNamed:@"privacyNavReturnImg"];
        customModel.navReturnImgHeight = [[dict objectForKey:@"navReturnImgHeight"] intValue];
        customModel.navReturnImgLeftMargin = [[dict objectForKey:@"navReturnImgLeftMargin"] intValue];
        customModel.navReturnImgWidth = [[dict objectForKey:@"navReturnImgWidth"] intValue];
        customModel.videoURL = [dict objectForKey:@"videoURL"];
        customModel.navReturnImgBottomMargin = [[dict objectForKey:@"navReturnImgBottomMargin"] intValue];
        customModel.modalTransitionStyle = [[dict objectForKey:@"modalTransitionStyle"] intValue];
        customModel.navReturnImg = [UIImage imageNamed:@"navReturnImg"];
        customModel.logBtnHighlightedImg = [UIImage imageNamed:@"logBtnHighlightedImg"];
        customModel.navBarHidden = [[dict objectForKey:@"navBarHidden"] boolValue];
        customModel.logBtnEnableImg = [UIImage imageNamed:@"logBtnEnableImg"];
        int shouldHiddenPrivacyMarks = [[dict objectForKey:@"shouldHiddenPrivacyMarks"] intValue];
        if (shouldHiddenPrivacyMarks) {
            customModel.shouldHiddenPrivacyMarks = YES;
        } else {
            customModel.shouldHiddenPrivacyMarks = NO;
        }
        
        int navControl = [[dict objectForKey:@"navControl"] intValue];
        int navControlRightMargin = [[dict objectForKey:@"navControlRightMargin"] intValue];
        int navControlBottomMargin = [[dict objectForKey:@"navControlBottomMargin"] intValue];
        int navControlWidth = [[dict objectForKey:@"navControlWidth"] intValue];
        int navControlHeight = [[dict objectForKey:@"navControlHeight"] intValue];
        
        if (navControl) {
            UIView *view = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 40, 40)];
            customModel.navControlRightMargin = navControlRightMargin;
            customModel.navControlBottomMargin = navControlBottomMargin;
            customModel.navControlWidth = navControlWidth;
            customModel.navControlHeight = navControlHeight;
            view.backgroundColor = [UIColor redColor];
            customModel.navControl = view;
        }
        int statusBarStyle = [[dict objectForKey:@"statusBarStyle"] intValue];
        customModel.statusBarStyle = statusBarStyle;
        
      customModel.isRepeatPlay = [[dict objectForKey:@"isRepeatPlay"] boolValue];
        
      //           customModel.faceOrientation = UIInterfaceOrientationLandscapeLeft;
      customModel.animationRepeatCount = [[dict objectForKey:@"animationRepeatCount"] integerValue];
      NSArray *animationImages = [dict objectForKey:@"animationImages"];
      NSMutableArray *array = [NSMutableArray array];
      for (NSDictionary *image in animationImages) {
          [array addObject:[UIImage imageNamed:[image objectForKey:@"imageName"]]];
      }
     customModel.animationImages = array;
     customModel.animationDuration = [[dict objectForKey:@"animationDuration"] integerValue];
      customModel.privacyState = [[dict objectForKey:@"privacyState"] boolValue];
        
      int authWindowPop = [[dict objectForKey:@"authWindowPop"] intValue];
      if (authWindowPop == 0) {
          customModel.authWindowPop = NTESAuthWindowPopFullScreen;
      } else if (authWindowPop == 1) {
          customModel.authWindowPop = NTESAuthWindowPopCenter;
      } else {
          customModel.authWindowPop = NTESAuthWindowPopBottom;
      }
        
      int closePopImgHeight = [[dict objectForKey:@"closePopImgHeight"] intValue];
      int closePopImgWidth = [[dict objectForKey:@"closePopImgWidth"] intValue];
      customModel.closePopImgWidth = closePopImgWidth;
      customModel.closePopImgHeight = closePopImgHeight;
        
      int closePopImgOriginY = [[dict objectForKey:@"closePopImgOriginY"] intValue];
      int closePopImgOriginX = [[dict objectForKey:@"closePopImgOriginX"] intValue];
      customModel.closePopImgOriginX = closePopImgOriginX;
      customModel.closePopImgOriginY = closePopImgOriginY;
        
      float scaleH = [[dict objectForKey:@"scaleH"] floatValue];
      customModel.scaleH = scaleH;
        
      float scaleW = [[dict objectForKey:@"scaleW"] floatValue];
      customModel.scaleW = scaleW;
        
      int authWindowCenterOriginX = [[dict objectForKey:@"authWindowCenterOriginX"] intValue];
      int authWindowCenterOriginY = [[dict objectForKey:@"authWindowCenterOriginY"] intValue];
      customModel.authWindowCenterOriginY = authWindowCenterOriginY;
      customModel.authWindowCenterOriginX = authWindowCenterOriginX;
        
      int popCenterCornerRadius = [[dict objectForKey:@"popCenterCornerRadius"] intValue];
      int popBottomCornerRadius = [[dict objectForKey:@"popBottomCornerRadius"] intValue];
      customModel.popBottomCornerRadius = popBottomCornerRadius;
      customModel.popCenterCornerRadius = popCenterCornerRadius;
      customModel.presentDirectionType = [[dict objectForKey:@"presentDirectionType"] intValue];
      
      customModel.popBackgroundColor = [[UIColor ntes_colorWithHexString:[dict objectForKey:@"popBackgroundColor"]] colorWithAlphaComponent:[[dict objectForKey:@"alpha"] doubleValue]];
      UIViewController *rootController = [UIApplication sharedApplication].delegate.window.rootViewController;
      customModel.currentVC = rootController;
      customModel.rootViewController = rootController;
      [[NTESQuickLoginManager sharedInstance] setupModel:customModel];
      
      customModel.backActionBlock = ^{
          NSMutableDictionary *dict = [NSMutableDictionary dictionary];
          [dict setValue:@"leftBackButton" forKey:@"clickViewType"];
        [self sendEvent:dict];
      };
      customModel.closeActionBlock = ^{
          NSMutableDictionary *dict = [NSMutableDictionary dictionary];
          [dict setValue:@"closeAction" forKey:@"action"];
        [self sendEvent:dict];
      };
      customModel.loginActionBlock = ^(BOOL isChecked) {
          NSMutableDictionary *dict = [NSMutableDictionary dictionary];
          [dict setValue:@"loginButton" forKey:@"clickViewType"];
          [dict setValue:@(isChecked) forKey:@"isCheckboxChecked"];
        [self sendEvent:dict];
      };
      customModel.checkActionBlock = ^(BOOL isChecked) {
          NSMutableDictionary *dict = [NSMutableDictionary dictionary];
          [dict setValue:@"checkbox" forKey:@"clickViewType"];
          [dict setValue:@(isChecked) forKey:@"isCheckboxChecked"];
        [self sendEvent:dict];
          
      };
      customModel.privacyActionBlock = ^(int privacyType) {
          NSMutableDictionary *dict = [NSMutableDictionary dictionary];
          NSString *privacy;
          if (privacyType == 0) {
              privacy = @"appDPrivacy";
          } else if (privacyType == 1) {
              privacy = @"appFPrivacy";
          } else if (privacyType == 2) {
              privacy = @"appSPrivacy";
          }
          [dict setValue:@"privacy" forKey:@"clickViewType"];
        [self sendEvent:dict];
      };
  
  });

}

- (void)buttonDidTipped:(UIButton *)sender {
    NSMutableDictionary *callBackDict = [NSMutableDictionary dictionary];
    NSArray *option = [self.option objectForKey:@"widgets"];
    NSDictionary *action = option[sender.tag];
    NSString *actions = [action objectForKey:@"viewId"];
    [callBackDict setValue:actions forKey:@"viewId"];
    [self sendEvent:callBackDict];
}

- (void)labelDidTipped:(UITapGestureRecognizer *)tap {
  NSMutableDictionary *callBackDict = [NSMutableDictionary dictionary];
  UILabel *label = (UILabel *)tap.view;
  NSArray *option = [self.option objectForKey:@"widgets"];
  NSDictionary *action = option[label.tag];
  NSString *actions = [action objectForKey:@"viewId"];
  [callBackDict setValue:actions forKey:@"viewId"];
  [self sendEvent:callBackDict];
}

/// 授权认证接口
RCT_EXPORT_METHOD(login:(RCTResponseSenderBlock)callback) {
  [[NTESQuickLoginManager sharedInstance] CUCMCTAuthorizeLoginCompletion:^(NSDictionary * _Nonnull resultDic) {
    
    NSNumber *boolNum = [resultDic objectForKey:@"success"];
    BOOL success = [boolNum boolValue];
    callback(@[@(success), resultDic]);
  }];
}

/**
*  @abstract   获取当前上网卡的运营商，0:未知 1:电信 2.移动 3.联通
*/
RCT_EXPORT_METHOD(getCarrier:(RCTResponseSenderBlock)callback)
{
   dispatch_async(dispatch_get_main_queue(), ^(){
     NSInteger getCarrier = [[NTESQuickLoginManager sharedInstance] getCarrier];
     callback(@[@(getCarrier)]);
   });
}


- (NSArray<NSString *> *)supportedEvents
{
   return @[@"uiCallback"];
}

/// 授权页面将要销毁
- (void)authViewDealloc {
  NSMutableDictionary *dict = [NSMutableDictionary dictionary];
  [dict setValue:@"authViewDealloc" forKey:@"lifecycle"];
  [self sendEvent:dict];
}

/// 授权页面已经出现
- (void)authViewDidAppear {
  NSMutableDictionary *dict = [NSMutableDictionary dictionary];
  [dict setValue:@"authViewDidAppear" forKey:@"lifecycle"];
  [self sendEvent:dict];
}

/// 授权页面已经消失
- (void)authViewDidDisappear {
  NSMutableDictionary *dict = [NSMutableDictionary dictionary];
  [dict setValue:@"authViewDidDisappear" forKey:@"lifecycle"];
  [self sendEvent:dict];
}

/// 授权页面加载中
- (void)authViewDidLoad {
  NSMutableDictionary *dict = [NSMutableDictionary dictionary];
  [dict setValue:@"authViewDidLoad" forKey:@"lifecycle"];
  [self sendEvent:dict];
}

/// 授权页面将要出现
- (void)authViewWillAppear {
  NSMutableDictionary *dict = [NSMutableDictionary dictionary];
  [dict setValue:@"authViewWillAppear" forKey:@"lifecycle"];
  [self sendEvent:dict];
}

/// 授权页面将要消失
- (void)authViewWillDisappear {
  NSMutableDictionary *dict = [NSMutableDictionary dictionary];
  [dict setValue:@"authViewWillDisappear" forKey:@"lifecycle"];
  [self sendEvent:dict];
}

- (void)sendEvent:(id)body {
  dispatch_async(dispatch_get_main_queue(), ^(){
   [self sendEventWithName:@"uiCallback" body:body];
  });
}


@end
  
