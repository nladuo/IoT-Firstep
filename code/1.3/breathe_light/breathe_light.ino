int pin = 3; 
int flag = 0; //flag = 0，暗变亮，flag=1，亮变暗
int duty = 0; //初始化为暗

void setup() {
  // put your setup code here, to run once:
  pinMode(pin, OUTPUT);
}

void loop() {
  analogWrite(pin, duty);
  delay(5);
  if(flag == 0){
    duty++;
    if(duty >= 255) flag = 1;
  }else{
    duty--;
    if(duty <= 0) flag = 0;
  }
}
