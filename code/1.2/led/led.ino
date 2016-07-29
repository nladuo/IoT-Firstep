int pin = 13;               //定义管脚为D13

void setup() {
  pinMode(pin, OUTPUT);     //设置D13管脚模式为输出
}

void loop() {
  //digitalWrite(pin, HIGH);  //在D13管脚输出高电平
  delay(1000);              //MCU休眠1000ms
  digitalWrite(pin, LOW);   //在D13管脚输出低电平
  delay(1000);              //MCU休眠1000ms
}
