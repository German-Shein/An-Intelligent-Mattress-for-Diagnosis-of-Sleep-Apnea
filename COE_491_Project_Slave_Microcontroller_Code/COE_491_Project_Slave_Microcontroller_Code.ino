void setup () 
{
  pinMode (24, INPUT_PULLUP);
  pinMode (26, INPUT_PULLUP);
  pinMode (28, INPUT_PULLUP);
  pinMode (53, INPUT_PULLUP);
  Serial.begin (9600);
  Serial1.begin (9600);
}

void loop () 
{
  short index, sensory_data;
  for (index = 0; index < 16; index = index + 1)
  {
    sensory_data = analogRead (index);
    Serial1.write (sensory_data >> 8);
    Serial1.write (sensory_data);
  }
  sensory_data = digitalRead (24);
  Serial1.write (sensory_data >> 8);
  Serial1.write (sensory_data);
  sensory_data = digitalRead (26);
  Serial1.write (sensory_data >> 8);
  Serial1.write (sensory_data);
  sensory_data = digitalRead (28);
  Serial1.write (sensory_data >> 8);
  Serial1.write (sensory_data);
  sensory_data = digitalRead (53);
  Serial1.write (sensory_data >> 8);
  Serial1.write (sensory_data);
  delay (100);
}
