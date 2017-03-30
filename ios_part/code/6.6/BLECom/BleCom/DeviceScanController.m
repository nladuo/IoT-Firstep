//
//  DeviceScanController.m
//  BleCom
//
//  Created by kalen blue on 15-10-03.
//  Copyright (c) 2015年 TRY. All rights reserved.
//

#import "DeviceScanController.h"
#import "DeviceControlController.h"

@interface DeviceScanController ()

@end

@implementation DeviceScanController

@synthesize tableView;
@synthesize scanBtn;

@synthesize sensor;
@synthesize peripheralArray;


- (instancetype)initWithCoder:(NSCoder *)coder
{
    self = [super initWithCoder:coder];
    if (self) {
        
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];

    tableView.dataSource = self;
    tableView.delegate = self;

    //初始化蓝牙传感器
    sensor = [[SerialGATT alloc] init];
    [sensor setup];
    //把当前的ViewController添加到观察者列表中，接受蓝牙通知
    sensor.delegate = [LeStatusNotificationCenter getInstance];
    [[LeStatusNotificationCenter getInstance] addObserver:self];
    
    peripheralArray = [[NSMutableArray alloc] init];
}

- (void)viewDidUnload
{
    [self setTableView:nil];
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)scanBleDevices:(id)sender {
    if ([sensor activePeripheral]) {
        if (sensor.activePeripheral.state == CBPeripheralStateConnected) {
            [sensor.manager cancelPeripheralConnection:sensor.activePeripheral];
            sensor.activePeripheral = nil;
        }
    }
    
    if ([sensor peripherals]) {
        sensor.peripherals = nil;
        [peripheralArray removeAllObjects];
        [tableView reloadData];
    }
    
    sensor.delegate = [LeStatusNotificationCenter getInstance];

    NSLog(@"beagin scanning");
    [scanBtn setTitle:@"扫描中" forState:UIControlStateNormal];
    [NSTimer scheduledTimerWithTimeInterval:5 target:self selector:@selector(scanTimer:) userInfo:nil repeats:NO];
    
    [sensor findBLKAppPeripherals:5];
}

/*
 * scanTimer
 * when scanBleDevices is timeout, this function will be called
 *
 */
-(void) scanTimer:(NSTimer *)timer
{
    [scanBtn setTitle:@"扫描" forState:UIControlStateNormal];
}

#pragma mark - UITableViewDelegate
-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.peripheralArray count];
}


-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSUInteger row = [indexPath row];

    CBPeripheral *peripheral = [peripheralArray objectAtIndex:row];

    if (sensor.activePeripheral && sensor.activePeripheral != peripheral) {
        [sensor disconnect:sensor.activePeripheral];
    }
    
    sensor.activePeripheral = peripheral;
    [sensor connect:sensor.activePeripheral];
    
    [scanBtn setTitle:@"扫描" forState:UIControlStateNormal];

    [self performSegueWithIdentifier:@"deviceControlSegue" sender:nil];
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *cellId = @"peripheralCell";
    UITableViewCell *cell = [self.tableView dequeueReusableCellWithIdentifier:cellId];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellId];
    }
    
    // Configure the cell
    NSUInteger row = [indexPath row];
    CBPeripheral *peripheral = [peripheralArray objectAtIndex:row];
    cell.textLabel.text = peripheral.name;
    cell.accessoryType = UITableViewCellAccessoryDetailDisclosureButton;
    return cell;
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([segue.identifier isEqual:@"deviceControlSegue"]) {
        DeviceControlController *controller = segue.destinationViewController;
        controller.sensor = sensor;
    }
}

#pragma mark - BTSmartSensorDelegate
-(void) peripheralFound:(CBPeripheral *)peripheral
{
    [peripheralArray addObject:peripheral];
    [tableView reloadData];
}

- (void) serialGATTCharValueUpdated: (NSString *)UUID value: (NSData *)data{}  // do nothing
- (void) setConnect{}      // do nothing
- (void) setDisconnect{} // do nothing

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
