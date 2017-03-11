#include <EtherCard.h>

// ethernet interface mac address, must be unique on the LAN
static byte mymac[] = { 0x74,0x69,0x69,0x2D,0x30,0x31 };

static uint8_t server_ip[] = { 192, 168, 1, 105 };
byte Ethernet::buffer[700];
static byte session;
static uint32_t timer;

static void sendHttpGet () {
  Serial.println("Sending HttpGet...");

  Stash::prepare(PSTR("GET / HTTP/1.0" "\r\n"
    "Host:" "\r\n"
    "\r\n"));

  session = ether.tcpSend();
  Serial.println("finished");
}

void setup () {
  Serial.begin(9600);
  Serial.println("httpGet demo");

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
