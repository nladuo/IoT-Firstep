#include <EtherCard.h>
#include <OneWire.h>
#include <DallasTemperature.h>

// 将DS18B20的数据口连接到2号引脚
#define ONE_WIRE_BUS 2
// 初始连接在单总线上的单总线设备
OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);

// ethernet interface mac address, must be unique on the LAN
static byte mymac[] = { 0x74,0x69,0x69,0x2D,0x30,0x31 };

static uint8_t server_ip[] = { 192, 168, 1, 105 };
byte Ethernet::buffer[700];
static byte session;
static uint32_t timer;
char path[100];

static void sendHttpGet () {
  Serial.println("Sending HttpGet...");

  sensors.requestTemperatures(); // 获取温度

  int temp = sensors.getTempCByIndex(0) * 100; //方便转换

  char temp_str[100];
  sprintf(temp_str, "temp is: %d.%d", temp/100, temp%100); //arduino里面没法用%f
  Serial.println(temp_str); //打印温度

  Stash::prepare(PSTR("GET /update?temp=$D.$D HTTP/1.0" "\r\n"
    "Host:" "\r\n"
    "\r\n"), temp/100, temp%100);

  session = ether.tcpSend();
  Serial.println("finished");
}

void setup () {
  Serial.println("Web DS18B20 Demo");
  Serial.begin(9600);

  sensors.begin();// 加载DS18B20库

  if (ether.begin(sizeof Ethernet::buffer, mymac, 10) == 0) 
    Serial.println("Failed to access Ethernet controller");
  else
    Serial.println("Ethernet controller initialized");
  
  if (!ether.dhcpSetup())
    Serial.println("DHCP failed");
  else 
    Serial.println("DHCP succeeded");

  ether.printIp("IP:  ", ether.myip);

  //设置服务器ip和端口号
  ether.copyIp(ether.hisip, server_ip);
  ether.hisport = 8000;
}

void loop () {
  if (millis() > timer) { // 每隔10秒发一次请求
    timer = millis() + 10000;
    //发送请求
    sendHttpGet();
  }

  //接受数据包
  ether.packetLoop(ether.packetReceive());
  const char* reply = ether.tcpReply(session);
  if (reply != 0) {
    Serial.println("Got a response!");
    Serial.println(reply);
    Serial.println();
  }
}
