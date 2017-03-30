//
//  LeStatusNotificationCenter.m
//  BLECom
//
//  Created by 刘嘉铭 on 16/6/5.
//  Copyright © 2016年 KalenBlue. All rights reserved.
//

#import "LeStatusNotificationCenter.h"

@implementation LeStatusNotificationCenter

@synthesize observers;

static LeStatusNotificationCenter* leNC = nil;


- (instancetype)init
{
    self = [super init];
    if (self) {
        observers = [[NSMutableArray alloc] init];
    }
    return self;
}

//单例对象，只有一个广播中心：NotificationCenter
+(LeStatusNotificationCenter*) getInstance
{
    if (leNC == nil) {
        leNC = [[LeStatusNotificationCenter alloc] init];
    }
    
    return leNC;
}

-(void) addObserver: (id<BTSmartSensorDelegate>)observed
{
    [observers addObject:observed];
}

-(void) removeObserver: (id<BTSmartSensorDelegate>)observed
{
    if ([observers containsObject:observed]) {
        [observers removeObject:observed];
    }
}

//把蓝牙连接事件转发给观察者们
-(void)setConnect
{
    id<BTSmartSensorDelegate> observed;
    for (int i = 0; i < observers.count; i++) {
        observed = [observers objectAtIndex:i];
        [observed setConnect];
    }
}

//把蓝牙断开连接事件转发给观察者们
-(void)setDisconnect
{
    id<BTSmartSensorDelegate> observed;
    for (int i = 0; i < observers.count; i++) {
        observed = [observers objectAtIndex:i];
        [observed setDisconnect];
    }
}

//把发现蓝牙外设转发给观察者们
- (void) peripheralFound:(CBPeripheral *)peripheral
{
    id<BTSmartSensorDelegate> observed;
    for (int i = 0; i < observers.count; i++) {
        observed = [observers objectAtIndex:i];
        [observed peripheralFound:peripheral];
    }
}

//把蓝牙接受到的数据转发给观察者们
- (void) serialGATTCharValueUpdated: (NSString *)UUID value: (NSData *)data
{
    id<BTSmartSensorDelegate> observed;
    for (int i = 0; i < observers.count; i++) {
        observed = [observers objectAtIndex:i];
        [observed serialGATTCharValueUpdated:UUID value:data];
    }
}


@end
