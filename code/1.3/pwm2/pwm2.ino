int rPin = 3;
int gPin = 5;
int bPin = 6;   
int rDuty = 255;
int gDuty = 0;
int bDuty = 0 ;
//flag = 0,红->黄
//     = 1,黄->绿
//     = 2,绿->青
//     = 3,青->蓝
//     = 4,蓝->紫
//     = 5,紫->红
int flag = 0;

void setup() {
  pinMode(rPin, OUTPUT);
  pinMode(gPin, OUTPUT);
  pinMode(bPin, OUTPUT);
}

void loop() {
  analogWrite(rPin, rDuty);
  analogWrite(gPin, gDuty);
  analogWrite(bPin, bDuty);
  delay(5);
  switch(flag){
    case 0:       
      gDuty++;
      if(gDuty >= 255) flag++;
      break;
    case 1:
      rDuty--;
      if(rDuty <= 0) flag++;
      break;
    case 2:
      bDuty++;
      if(bDuty >= 255) flag++;
      break;
    case 3:       
      gDuty--;
      if(gDuty <= 0) flag++;
      break;
    case 4:
      rDuty++;
      if(rDuty >= 255) flag++;
      break;
    case 5:
      bDuty--;
      if(bDuty <= 0) flag = 0;
      break;
  }
}
