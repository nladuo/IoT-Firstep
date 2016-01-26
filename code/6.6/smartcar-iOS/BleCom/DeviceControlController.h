//
//  DeviceControlController.h
//  BleCom
//
//  Created by kalen blue on 15-10-2.
//  Copyright (c) 2015年 TRY. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SerialGATT.h"
#import "LeSensorObserver.h"

//#define RSSI_THRESHOLD -60
//#define WARNING_MESSAGE @"z"

@class CBPeripheral;
@class SerialGATT;

@interface DeviceControlController : UIViewController<BTSmartSensorDelegate>

@property (strong, nonatomic) SerialGATT *sensor;

// 0 ---> 停止
// 1 ---> 上
// 2 ---> 下
// 3 ---> 左
// 4 ---> 右
@property int status;

- (IBAction)sendData:(id)sender;
@property (strong, nonatomic) NSTimer *timer;
@end
