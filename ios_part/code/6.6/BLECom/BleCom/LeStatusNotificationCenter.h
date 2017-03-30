//
//  LeStatusNotificationCenter.h
//  BLECom
//
//  Created by 刘嘉铭 on 16/6/5.
//  Copyright © 2016年 KalenBlue. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SerialGATT.h"

@interface LeStatusNotificationCenter : NSObject

//获取 通知中心 的单例
+(LeStatusNotificationCenter*) getInstance;
//添加观察者
-(void) addObserver: (id<BTSmartSensorDelegate>)observed;
//删除观察者
-(void) removeObserver: (id<BTSmartSensorDelegate>)observed;

@property (nonatomic, retain) NSMutableArray *observers;

@end
