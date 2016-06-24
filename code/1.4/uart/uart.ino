char val;

void setup() {
  Serial.begin(9600);
}

void loop() {
  
  val=Serial.read();
  if(val != -1){
    Serial.println(val);
  }
}
