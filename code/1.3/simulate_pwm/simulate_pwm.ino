int pin = 3;    //D3管脚,接R管脚
int T = 100;
int t = 50;    //占空比= t:T
int count = 0;  //计数器

void setup() {
  pinMode(pin, OUTPUT);
}

void loop() {
  if(count < t) {
    digitalWrite(pin, HIGH);
  }else{
    digitalWrite(pin, LOW);
  }
  count++;
  if(count >= T){
    count = 0;
  }
  delayMicroseconds(10);//睡眠10微秒
}
