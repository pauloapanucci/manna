#include <XBee.h>

XBee xbee = XBee();
const int LM35 = A0;

typedef union {
  float number;
  uint8_t bytes[4];
} FLOATUNION_t;

FLOATUNION_t temperature;

void setup() {
  Serial.begin(9600);
  xbee.setSerial(Serial);
}

void loop() {
  temperature.number = (float(analogRead(LM35)) * 5 / (1023)) /.01;
  XBeeAddress64 addr64 = XBeeAddress64();
  addr64.setMsb(0x0);
  addr64.setLsb(0x0);
  ZBTxRequest zbTx = ZBTxRequest(addr64, temperature.bytes, sizeof(temperature.bytes));
  xbee.send(zbTx);
  delay(1000);
}
