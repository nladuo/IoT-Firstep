//
//  DeviceControlController.m
//  BleCom
//
//  Created by kalen blue on 15-10-2.
//  Copyright (c) 2015年 TRY. All rights reserved.
//
#import "DeviceControlController.h"

@interface DeviceControlController ()

@end

@implementation DeviceControlController
@synthesize sensor;
@synthesize status;
@synthesize timer;


- (instancetype)initWithCoder:(NSCoder *)coder
{
    self = [super initWithCoder:coder];
    if (self) {
        //
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    if ([self respondsToSelector:@selector(edgesForExtendedLayout)]){
        self.edgesForExtendedLayout = UIRectEdgeNone;
    }
    self.title = self.sensor.activePeripheral.name;
    //添加当前viewcontroller到观察者中，接收蓝牙状态的改变
    [[LeSensorObserver getInstance] addLeSensorObserver:self];


}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

//send data
-(IBAction)sendData:(id)sender {

    UIButton *btn = sender;
    status = [btn tag];




}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(BOOL)textFieldShouldReturn:(UITextField *)textField
{
    [textField resignFirstResponder];
    return YES;
}

-(void) timerLoop:(NSTimer *)t
{
    char charDat = status;
    char *charPointer = &charDat;
    NSData *data = [NSData dataWithBytes:charPointer length:1];
    [sensor write:sensor.activePeripheral data:data];
}

#pragma mark - BTSmartSensorDelegate
-(void)setConnect
{
    //每隔0.1秒发送一次状态
    timer = [NSTimer scheduledTimerWithTimeInterval:0.1 target:self selector:@selector(timerLoop:) userInfo:nil repeats:YES];
}

-(void)setDisconnect
{
    [timer invalidate];
}

-(void) serialGATTCharValueUpdated:(NSString *)UUID value:(NSData *)data{}// do nothing

- (void) peripheralFound:(CBPeripheral *)peripheral{} // do nothing



- (IBAction)sendUp:(id)sender {
}
@end
