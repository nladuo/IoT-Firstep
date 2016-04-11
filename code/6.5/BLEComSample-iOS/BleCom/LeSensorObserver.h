//
//  LeSenorObserver.h
//  BLKApp
//
//  Created by kalen blue on 15-10-2.
//  Copyright (c) 2015年 TRY. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SerialGATT.h"

/*
 * 用于通知观察者们接收到消息
 *
 */
@interface LeSensorObserver : NSObject<BTSmartSensorDelegate>

+(LeSensorObserver*) getInstance;
-(void) addLeSensorObserver: (id<BTSmartSensorDelegate>)observed;
-(void) removeLeSensorObserver: (id<BTSmartSensorDelegate>)observed;

@property (nonatomic, retain) NSMutableArray *observers;
@end
