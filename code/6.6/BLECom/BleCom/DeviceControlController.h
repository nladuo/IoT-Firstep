//
//  DeviceControlController.h
//  BleCom
//
//  Created by kalen blue on 15-10-2.
//  Copyright (c) 2015年 TRY. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SerialGATT.h"
#import "LeStatusNotificationCenter.h"

@class CBPeripheral;
@class SerialGATT;

@interface DeviceControlController : UIViewController<BTSmartSensorDelegate>

@property (strong, nonatomic) SerialGATT *sensor;

- (IBAction)sendData:(id)sender;
@property (weak, nonatomic) IBOutlet UITextView *receiveDataTv;
@property (weak, nonatomic) IBOutlet UITextField *sendDataTf;

@end
