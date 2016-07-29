char temp;

void setup() {
  Serial.begin(9600);
}

void loop() {
  while(Serial.available()){ //如果电脑给单片机发送了数据,就把同样的数据发送回去.
    temp=Serial.read();
    Serial.print(temp);
    Serial.print(" ");
  }
  delay(1);
}

