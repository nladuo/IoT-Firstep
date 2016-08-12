//
//  LeSenorObserver.m
//  BLKApp
//
//  Created by kalen blue on 15-10-2.
//  Copyright (c) 2015年 TRY. All rights reserved.
//

#import "LeSensorObserver.h"

@implementation LeSensorObserver

@synthesize observers;

static LeSensorObserver* sensorObserver = nil;


- (instancetype)init
{
    self = [super init];
    if (self) {
        observers = [[NSMutableArray alloc] init];
    }
    return self;
}

+(LeSensorObserver*) getInstance
{
    if (sensorObserver == nil) {
        sensorObserver = [[LeSensorObserver alloc] init];
    }
    
    return sensorObserver;
}


-(void) addLeSensorObserver: (id<BTSmartSensorDelegate>)observed
{
    [observers addObject:observed];
}

-(void) removeLeSensorObserver: (id<BTSmartSensorDelegate>)observed
{
    if ([observers containsObject:observed]) {
        [observers removeObject:observed];
    }
}

-(void)setConnect
{
    id<BTSmartSensorDelegate> observed;
    for (int i = 0; i < observers.count; i++) {
        observed = [observers objectAtIndex:i];
        [observed setConnect];
    }
}

-(void)setDisconnect
{
    id<BTSmartSensorDelegate> observed;
    for (int i = 0; i < observers.count; i++) {
        observed = [observers objectAtIndex:i];
        [observed setDisconnect];
    }
}

- (void) peripheralFound:(CBPeripheral *)peripheral
{
    id<BTSmartSensorDelegate> observed;
    for (int i = 0; i < observers.count; i++) {
        observed = [observers objectAtIndex:i];
        [observed peripheralFound:peripheral];
    }
}

- (void) serialGATTCharValueUpdated: (NSString *)UUID value: (NSData *)data
{
    id<BTSmartSensorDelegate> observed;
    for (int i = 0; i < observers.count; i++) {
        observed = [observers objectAtIndex:i];
        [observed serialGATTCharValueUpdated:UUID value:data];
    }
}


@end
