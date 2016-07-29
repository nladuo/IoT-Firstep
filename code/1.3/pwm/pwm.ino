int pin = 3;
int t = 28;
// int T = 255 隐藏大T为255, 占空比= t:T

void setup() {
  pinMode(pin, OUTPUT);
  analogWrite(pin, t);
}

void loop() {}
