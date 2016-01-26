//
//  DeviceScanController.h
//  BleCom
//
//  Created by kalen blue on 15-10-2.
//  Copyright (c) 2015年 TRY. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SerialGATT.h"
#import "LeSensorObserver.h"

@class DeviceControlController;

@interface DeviceScanController : UIViewController<BTSmartSensorDelegate, UITableViewDelegate, UITableViewDataSource>

@property (strong, nonatomic) SerialGATT *sensor;
@property (nonatomic, retain) NSMutableArray *peripheralArray;

- (IBAction)scanBleDevices:(id)sender;

-(void) scanTimer:(NSTimer *)timer;

@property (weak, nonatomic) IBOutlet UITableView *tableView;
@property (weak, nonatomic) IBOutlet UIButton *scanBtn;

@end
