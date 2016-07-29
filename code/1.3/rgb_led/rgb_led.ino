int rPin = 3;   //D3接R管脚
int gPin = 5;   //D5接G管脚
int bPin = 6;   //D6接B管脚

/**
 * 根据r,g,b的值给相应管脚输出高低电平,并睡眠1s
 */
void outputColorAndDelay(int r, int g, int b){
  digitalWrite(rPin, r ? HIGH : LOW); //根据r的值在D3输出高低电平
  digitalWrite(gPin, g ? HIGH : LOW); //根据g的值在D5输出高低电平
  digitalWrite(bPin, b ? HIGH : LOW); //根据b的值在D6输出高低电平
  delay(1000);                        //MCU休眠1000ms
}

void setup() {
  pinMode(rPin, OUTPUT);
  pinMode(gPin, OUTPUT);
  pinMode(bPin, OUTPUT);
}

void loop() {
  outputColorAndDelay(1, 0, 0); //输出红色
  outputColorAndDelay(1, 1, 0); //输出黄色
  outputColorAndDelay(0, 1, 0); //输出绿色
  outputColorAndDelay(0, 1, 1); //输出青色
  outputColorAndDelay(0, 0, 1); //输出蓝色
  outputColorAndDelay(1, 0, 1); //输出紫色
}
