import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_sigmob/flutter_sigmob.dart';
import 'package:flutter_sigmob/sigmob_event_handler.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    // initPlatformState();
  }

  FlutterSigmob sigmob;

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    await FlutterSigmob.init("15899", "566e4931fc46092d");


    sigmob = FlutterSigmob((SigmobEvents events, Map args) {
      print("-------------------");
      print(events.toString()+"\t"+args.toString());
      print("-------------------");
    });

  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: <Widget>[
              RaisedButton(
                onPressed: () {
                  initPlatformState();
                },
                child: Text("初始化"),
              ),
              RaisedButton(
                onPressed: () {
                  sigmob.playVideoAd("ede5fa91dad",0,null);
                },
                child: Text("视频广告"),
              ),
              RaisedButton(
                onPressed: () {
                  sigmob.playSplashAd("ee22c693cc1");
                },
                child: Text("开屏广告"),
              ),
            ],
          )
        ),
      ),
    );
  }
}
