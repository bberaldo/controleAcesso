#include <DHT.h>
#include <DHT_U.h>
#include <SPI.h>
#include <MFRC522.h>

#define RST_PIN         9          // Configurable, see typical pin layout above
#define SS_PIN          10         // Configurable, see typical pin layout above

#define DHTPIN 2 // pino que estamos conectado o sensor
#define DHTTYPE DHT11 // DHT 11 - temp e umidade

DHT dht(DHTPIN, DHTTYPE);
MFRC522 mfrc522(SS_PIN, RST_PIN);  // Create MFRC522 instance
byte accessUID[4] = {0x9D, 0x4D, 0xC3, 0x55}; //pin do chaveiro

void setup() {
  Serial.begin(9600);
  pinMode(6, OUTPUT);
  dht.begin();
  SPI.begin();
	mfrc522.PCD_Init();	
}
  
void loop() {
  byte inByte;

  if(Serial.available()) {

    inByte = Serial.read();

    if(inByte == 1){

      if ( ! mfrc522.PICC_IsNewCardPresent()) {
        return;
      }

      // Select one of the cards
      if ( ! mfrc522.PICC_ReadCardSerial()) {
        return;
      }
      // lê o id
      int auxiliar = 0;
      // Dump debug info about the card; PICC_HaltA() is automatically called
      for(int i=0; i<4; i++ ){
        if(mfrc522.uid.uidByte[i] == accessUID[i]){
          auxiliar++;
        }
      }

      if(auxiliar == 4){
        Serial.println("Acesso Permitido");
      }else{
        Serial.println("Acesso Negado");
      }

      mfrc522.PICC_HaltA();

    } else if(inByte == 2){
          digitalWrite(6, HIGH);
    } else if(inByte == 3){
          digitalWrite(6, LOW);
    } else if(inByte == 4){
      float h = dht.readHumidity();
      // testa se retorno é valido, caso contrário algo está errado.
      if (isnan(h)) {
        Serial.println("Failed to read from DHT");
      } else {
        Serial.println(h);
        
      }
    } else if(inByte == 5){
      float t = dht.readTemperature();
      // testa se retorno é valido, caso contrário algo está errado.
      if (isnan(t)) {
        Serial.println("Failed to read from DHT");
      } else {
        Serial.println(t);
        
      }
    } else if(inByte == 6){
      for(int i=0; i<4; i++ ){
        Serial.print(mfrc522.uid.uidByte[i]);
      }
    } else if (inByte == 7) {
      
      if (!mfrc522.PICC_IsNewCardPresent() || !mfrc522.PICC_ReadCardSerial()) //VERIFICA SE O CARTÃO PRESENTE NO LEITOR É DIFERENTE DO ÚLTIMO CARTÃO LIDO. CASO NÃO SEJA, FAZ
        return; //RETORNA PARA LER NOVAMENTE
 
      /***INICIO BLOCO DE CÓDIGO RESPONSÁVEL POR GERAR A TAG mfrc522 LIDA***/
      String strID = "";
      for (byte i = 0; i < 4; i++) {
        strID +=
        (mfrc522.uid.uidByte[i] < 0x10 ? "0" : "") +
        String(mfrc522.uid.uidByte[i], HEX) +
        (i!=3 ? " " : "");
      }
      strID.toUpperCase();
      /***FIM DO BLOCO DE CÓDIGO RESPONSÁVEL POR GERAR A TAG mfrc522 LIDA***/
    
      Serial.println(strID); //IMPRIME NA SERIAL O UID DA TAG mfrc522
    
      mfrc522.PICC_HaltA(); //PARADA DA LEITURA DO CARTÃO
      mfrc522.PCD_StopCrypto1(); //PARADA DA CRIPTOGRAFIA NO PCD

    } else{
      
    }
  } 
}
