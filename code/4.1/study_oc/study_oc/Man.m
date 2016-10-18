//
//  Man.m
//  study_oc
//
//  Created by 刘嘉铭 on 16/9/18.
//  Copyright © 2016年 刘嘉铭. All rights reserved.
//

#import "Man.h"

@implementation Man

@synthesize name;

- (void)walk
{
    NSLog(@"%@ walking", self.name);
}

- (instancetype)initWithName: (NSString*) _name
{
    self = [super init];
    if (self) {
        self.name = _name;
    }
    return self;
}

-(void) say
{
    NSLog(@"say:None");
}

-(void) say:(NSString *)what
{
    NSLog(@"say:%@", what);
}

-(void) say: (NSString*) what AndSay:(NSString*) what2
{
    NSLog(@"say:%@ and say:%@", what, what2);
}

@end
