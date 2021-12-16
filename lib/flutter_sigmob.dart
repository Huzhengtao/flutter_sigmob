import 'dart:async';
import 'sigmob_event_handler.dart';
import 'package:flutter/services.dart';

class FlutterSigmob  extends SigmobEventHandler{
  static const MethodChannel _channel =
      const MethodChannel('flutter_sigmob');

  final void Function(SigmobEvents, Map<String, dynamic>) listener;

  FlutterSigmob(this.listener): super(listener) {
    if (listener != null) {
      _channel.setMethodCallHandler(handleEvent);
    }
  }

  /// 初始化
  static Future<void> init(String appId, String apiKey) async {
    _channel.invokeMethod("init", [appId, apiKey]);
  }

  /// 播放视频广告
  Future<void> playVideoAd(String placementId,String uid,Map ectraInfo) async {
    _channel.invokeMethod('playVideoAd', [placementId,uid,ectraInfo]);
  }

  /// 加载开屏广告
  Future<void> playSplashAd(String placementId) async {
    _channel.invokeMethod("playSplashAd", placementId);
  }
}
