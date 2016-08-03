unsigned char temp;
int rPin = 3;
int gPin = 5;
int bPin = 6;   


void setup() {
  Serial.begin(9600);
  pinMode(rPin, OUTPUT);
  pinMode(gPin, OUTPUT);
  pinMode(bPin, OUTPUT);
  analogWrite(rPin, 0);
  analogWrite(gPin, 0);
  analogWrite(bPin, 0);
}

void loop() {
  while(Serial.available()){ //如果电脑给单片机发送了数据,就把同样的数据发送回去.
    temp = Serial.read();
    if(temp < 50){
      analogWrite(rPin, temp * 5);
    }else if(temp < 100){
      analogWrite(gPin, (temp - 50) * 5);
    }else if(temp < 150){
      analogWrite(bPin, (temp - 100) * 5);
    }
  }
  delay(1);
}

