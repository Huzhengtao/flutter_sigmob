import 'dart:async';
import 'package:flutter/services.dart';

abstract class SigmobEventHandler {
  final Function(SigmobEvents, Map) _listener;

  SigmobEventHandler(Function(SigmobEvents, Map) listener) : _listener = listener;

  Future<dynamic> handleEvent(MethodCall call) async {
    switch (call.method) {
      case 'loadError':
        _listener(SigmobEvents.loadError, call.arguments);
        break;
      case 'loadSuccess':
        _listener(SigmobEvents.loadSuccess, call.arguments);
        break;
      case 'playError':
        _listener(SigmobEvents.playError, call.arguments);
        break;
      case 'closed':
        _listener(SigmobEvents.closed, call.arguments);
        break;
      case 'clicked':
        _listener(SigmobEvents.clicked, call.arguments);
        break;
    }

    return null;
  }

}



enum SigmobEvents {
  loadError,  // 无广告返回
  loadSuccess, // 广告加载成功
  playError, // 播放时发生错误
  closed, // 广告播放完毕并点击了关闭按钮
  clicked, // 广告发生点击
}