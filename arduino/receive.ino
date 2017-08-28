#include <XBee.h>

XBee xbee = XBee();
XBeeResponse response = XBeeResponse();
ZBRxResponse rx = ZBRxResponse();

typedef union {
  float number;
  uint8_t bytes[4];
} FLOATNUMBER_t;

FLOATNUMBER_t temperature;
int contador = 0;

void setup() {
  Serial.begin(9600);
  xbee.begin(Serial);
}

void loop() {
  xbee.readPacket(); 
    if (xbee.getResponse().isAvailable()) {
      //Serial.println(xbee.getResponse().getApiId());
      if (xbee.getResponse().getApiId() == ZB_RX_RESPONSE) {
        xbee.getResponse().getZBRxResponse(rx);
        for (int i = 0; i < rx.getDataLength(); i++) {
          temperature.bytes[i] = rx.getData(i);
        }
        Serial.println(temperature.number);
      }
    }else if (xbee.getResponse().isError()) {
      Serial.println("Error reading packet.  Error code: ");  
      Serial.println(xbee.getResponse().getErrorCode());
  } 
  delay(100);
}
