#include <OneWire.h>
#include <DallasTemperature.h>

// 将DS18B20的数据口连接到2号引脚
#define ONE_WIRE_BUS 2
// 初始连接在单总线上的单总线设备
OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);

void setup(void)
{
  // 串口设置
  Serial.begin(9600);
  Serial.println("Dallas Temperature IC Control Library Demo");

  // 加载库
  sensors.begin();
}

void loop(void)
{ 
  Serial.print("Requesting temperatures...");
  sensors.requestTemperatures(); // 获取温度
  Serial.println("DONE");
 
  Serial.print("Temperature for the device 1 (index 0) is: ");
  Serial.println(sensors.getTempCByIndex(0));  
}
