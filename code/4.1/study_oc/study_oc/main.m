#import <Foundation/Foundation.h>
#import "Man.h"

void NSLogTest()//可以使用c语言的函数语法
{
    NSString* nsstr = @"Hello World!";
    int int_val = 10;
    char* c_str = "Hello World!";
    NSLog(@"\nThe NSString is: %@;\nThe int val is: %d;\nThe C String is: %s", nsstr, int_val, c_str);
}

int main(int argc, const char * argv[]) {
    @autoreleasepool {
        NSLogTest();
        
        Man *man = [[Man alloc] init];
        
        //方法调用
        [man say];
        [man say:@"HaHa"];
        [man say:@"HaHa" AndSay:@"HeHe"];
        
        //属性
        [man setName:@"Kalen"];
        NSLog(@"%@", [man name]);
        
        //点语法
        man.name = @"Bill";
        NSLog(@"%@", man.name);
        
        //初始化参数
        Man* man2 = [[Man alloc] initWithName:@"Lily"];
        NSLog(@"%@", man2.name);
        
        //将对象指向“接口”
        id<IWalkable> walkable = man2;
        [walkable walk];
        
        
    }
    return 0;
}
