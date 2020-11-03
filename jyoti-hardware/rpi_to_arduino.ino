#include <Servo.h>
Servo myservo1,myservo2;
int r=1;
void setup()
{
  myservo1.attach(9,600,2300);
  myservo2.attach(6,600,2300);
  Serial.begin(9600);
}
void loop()
{
  if(Serial.available())
 {
   r=r*(Serial.read());
   Serial.println((r));
   if(r==97)
   {
     Serial.println('a');
     //myservo1.write(0);  
     //delay(1000);
     myservo1.write(150);
     delay(1000);
     myservo1.write(100);  
     delay(1000);
   }
   else if(r==98)
   {
     Serial.println('b');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==99)
   {
     Serial.println('c');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==100)
   {
     Serial.println('d');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==101)
   {
     Serial.println('e');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==102)
   {
     Serial.println('f');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==103)
   {
     Serial.println('g');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==104)
   {
     Serial.println('h');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==105)
   {
     Serial.println('i');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==106)
   {
     Serial.println('j');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==107)
   {
     Serial.println('k');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==108)
   {
     Serial.println('l');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==109)
   {
     Serial.println('m');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==110)
   {
     Serial.println('n');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r=111)
   {
     Serial.println('o');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==112)
   {
     Serial.println('p');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==113)
   {
     Serial.println('q');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==114)
   {
     Serial.println('r');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==115)
   {
     Serial.println('s');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==116)
   {
     Serial.println('t');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==117)
   {
     Serial.println('u');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==118)
   {
     Serial.println('v');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==119)
   {
     Serial.println('w');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==-1)
   {
     Serial.println('b');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==98)
   {
     Serial.println('b');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   else if(r==98)
   {
     Serial.println('b');
     //myservo2.write(0);  
     //delay(1000);
     myservo2.write(135);
     delay(1000);
     myservo2.write(0);  
     delay(1000);
   }
   r=1;
 } 
}
