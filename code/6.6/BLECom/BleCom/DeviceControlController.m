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

@synthesize receiveDataTv;
@synthesize sendDataTf;

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
    [[LeStatusNotificationCenter getInstance] addObserver:self];

    self.receiveDataTv.editable = FALSE;

}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

//send data
-(IBAction)sendData:(id)sender {
    NSData *data = [sendDataTf.text dataUsingEncoding:NSUTF8StringEncoding];
    [sensor write:sensor.activePeripheral data:data];
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


#pragma mark - BTSmartSensorDelegate
-(void)setConnect
{
    receiveDataTv.text = @"Device Connected\n";
}

-(void)setDisconnect
{
    receiveDataTv.text = [receiveDataTv.text stringByAppendingString:@"\nDevice Lost\n"];
}

//recv data
-(void) serialGATTCharValueUpdated:(NSString *)UUID value:(NSData *)data
{
    NSString *value = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    if (value != nil) { //点击发送太快的话，接受value可能为nil
        receiveDataTv.text = [receiveDataTv.text stringByAppendingString:value];
    }

}

- (void) peripheralFound:(CBPeripheral *)peripheral{} // do nothing



@end
