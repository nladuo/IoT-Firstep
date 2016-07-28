void setup() {
  // 设置串口波特率为9600
  Serial.begin(9600);
}

void loop() {
  // 每两秒在串口监视器中打印一个 Hello, Arduino!
  Serial.println("Hello, Arduino!");
  delay(2000);
}
