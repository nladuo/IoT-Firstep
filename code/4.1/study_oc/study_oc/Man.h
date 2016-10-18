//
//  Man.h
//  study_oc
//
//  Created by 刘嘉铭 on 16/9/18.
//  Copyright © 2016年 刘嘉铭. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "IWalkable.h"

@interface Man : NSObject<IWalkable>

@property NSString* name;

- (void)walk;

- (instancetype)initWithName: (NSString*) _name;

//函数名为:"say"
-(void) say;
//函数名为:"say:"
-(void) say: (NSString*) what;
//函数名为:"say:AndSay:"
-(void) say: (NSString*) what AndSay:(NSString*) what2;

@end
