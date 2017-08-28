#include <XBee.h>

XBee xbee = XBee();

typedef union{
  float number;
  uint8_t bytes[4];
}FLOATUNION_t;

//float temp;
const int LM35 = A0;

uint8_t payload[] = {0, 0, 0, 0};
XBeeAddress64 addr64 = XBeeAddress64(0x0000, 0xFFFF);
ZBTxRequest zbTx = ZBTxRequest(addr64, payload, sizeof(payload));
ZBTxStatusResponse txStatus = ZBTxStatusResponse();

void setup() {
  Serial.begin(9600);
  xbee.setSerial(Serial);
}

void loop() {
  FLOATUNION_t temp;
  temp.number = (float(analogRead(LM35))*5/(1023)) / 0.01;
  for(int i = 0; i < 4; i++){
    Serial.print(temp.bytes[i]);
    Serial.print(",");
    payload[i] = temp.bytes[i];
  }
  Serial.println("");
  Serial.println(temp.number);
  xbee.send(zbTx);
  delay(2000);
} 
