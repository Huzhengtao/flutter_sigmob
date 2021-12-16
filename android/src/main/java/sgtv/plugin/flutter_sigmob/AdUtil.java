package sgtv.plugin.flutter_sigmob;

import com.sigmob.sdk.common.models.AdStatus;
import com.sigmob.windad.Splash.WindSplashAD;
import com.sigmob.windad.Splash.WindSplashADListener;
import com.sigmob.windad.Splash.WindSplashAdRequest;
import com.sigmob.windad.WindAdError;
import com.sigmob.windad.WindAdOptions;
import com.sigmob.windad.WindAds;
import com.sigmob.windad.rewardedVideo.WindRewardAdRequest;
import com.sigmob.windad.rewardedVideo.WindRewardInfo;
import com.sigmob.windad.rewardedVideo.WindRewardedVideoAd;
import com.sigmob.windad.rewardedVideo.WindRewardedVideoAdListener;

import java.util.Map;


public class AdUtil {
    private static WindRewardedVideoAd videoAd;

    public static void init(final String appId, final String apiKey){
        WindAds ads = WindAds.sharedAds();
        //是否未成年/true成年/flase未成年
        ads.setAdult(true);
        //是否关闭个性化推荐接口/true关闭/false开启
        ads.setPersonalizedAdvertisingOn(true);
        //useMediation:true代表使用聚合服务;false:代表单接SigMob
        ads.startWithOptions(FlutterSigmobPlugin.activity, new WindAdOptions(appId, apiKey, false));
    }
    public static void playVideoAd(String pid, String uid, Map<String, Object> extraInfo){
        if(videoAd!=null&&(videoAd.adOutStatus!= AdStatus.AdStatusNone&&videoAd.adOutStatus!= AdStatus.AdStatusClose)){
            if (FlutterSigmobPlugin.channel!=null){
                FlutterSigmobPlugin.channel.invokeMethod("busy", videoAd.adOutStatus.name());
            }
            return;
        }
        videoAd = new WindRewardedVideoAd(FlutterSigmobPlugin.activity,new WindRewardAdRequest(pid,uid,extraInfo));

        videoAd.setWindRewardedVideoAdListener(new WindRewardedVideoAdListener() {
            //仅sigmob渠道有回调，聚合其他平台无次回调
            @Override
            public void onVideoAdPreLoadSuccess(String placementId) {
                if (FlutterSigmobPlugin.channel!=null){
                    FlutterSigmobPlugin.channel.invokeMethod("preLoadSuccess", null);
                }
            }

            //仅sigmob渠道有回调，聚合其他平台无次回调
            @Override
            public void onVideoAdPreLoadFail(String placementId) {
                if (FlutterSigmobPlugin.channel!=null){
                    FlutterSigmobPlugin.channel.invokeMethod("preLoadFail", null);
                }
            }

            @Override
            public void onVideoAdLoadSuccess(String placementId) {
                if (FlutterSigmobPlugin.channel!=null){
                    FlutterSigmobPlugin.channel.invokeMethod("loadSuccess", null);
                }
                videoAd.show(FlutterSigmobPlugin.activity, null);
            }

            @Override
            public void onVideoAdPlayStart(String placementId) {
                if (FlutterSigmobPlugin.channel!=null){
                    FlutterSigmobPlugin.channel.invokeMethod("playStart", null);
                }
            }

            @Override
            public void onVideoAdPlayEnd(String placementId) {
                if (FlutterSigmobPlugin.channel!=null){
                    FlutterSigmobPlugin.channel.invokeMethod("playEnd", null);
                }
            }

            @Override
            public void onVideoAdClicked(String placementId) {
                if (FlutterSigmobPlugin.channel!=null){
                    FlutterSigmobPlugin.channel.invokeMethod("clicked", null);
                }

            }

            @Override
            public void onVideoAdClosed(WindRewardInfo windRewardInfo, String placementId) {
                if (FlutterSigmobPlugin.channel!=null){
                    FlutterSigmobPlugin.channel.invokeMethod("closed", JsonUtil.from(windRewardInfo));
                }
            }

            @Override
            public void onVideoAdLoadError(WindAdError windAdError, String placementId) {
                if (FlutterSigmobPlugin.channel!=null){
                    FlutterSigmobPlugin.channel.invokeMethod("loadError", JsonUtil.from(windAdError));
                }
            }

            @Override
            public void onVideoAdPlayError(WindAdError windAdError, String placementId) {
                if (FlutterSigmobPlugin.channel!=null){
                    FlutterSigmobPlugin.channel.invokeMethod("playError", JsonUtil.from(windAdError));
                }
            }

        });
        videoAd.loadAd();
    }

    public static void playSplashAd(String pid) {
        WindSplashAdRequest splashAdRequest = new WindSplashAdRequest(pid, "",null);
        splashAdRequest.setFetchDelay(5);
        WindSplashAD mWindSplashAD = new WindSplashAD(FlutterSigmobPlugin.activity, splashAdRequest, new WindSplashADListener() {
            @Override
            public void onSplashAdSuccessPresent() {
            }

            @Override
            public void onSplashAdSuccessLoad() {
            }

            @Override
            public void onSplashAdFailToLoad(WindAdError windAdError, String s) {
            }

            @Override
            public void onSplashAdClicked() {
            }

            @Override
            public void onSplashClosed() {
            }
        });
        mWindSplashAD.loadAdAndShow(null);
    }
}
