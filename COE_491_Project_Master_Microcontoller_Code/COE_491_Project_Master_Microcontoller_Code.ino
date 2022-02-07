const int sound_sampling_period = 100;
volatile int heart_rates [6];
volatile short heart_rate_sensory_data [6];

void setup ()
{
  Configure_Interruptions ();
  pinMode (24, INPUT_PULLUP);
  pinMode (26, INPUT_PULLUP);
  pinMode (28, INPUT_PULLUP);
  pinMode (53, INPUT_PULLUP);
  Serial.begin (9600);
  Serial1.begin (9600);
}

void loop () 
{
  boolean need_of_transmission_to_be_restarted = false;
  short index, maximal_signal_level = 0, minimal_signal_level = 1023, readings [40];
  String output;
  long start_of_sound_sampling_period;
  for (index = 0; index < 20; index = index + 1)
  {
    readings [index] = (Serial1.read () << 8) | Serial1.read ();
    if (readings [index] > 1023 || readings [index] < 0)
    {
      need_of_transmission_to_be_restarted = true;
    }
  }
  for (index = 16; index < 20; index = index + 1)
  {
    if (readings [index] > 1)
    {
      need_of_transmission_to_be_restarted = true;  
    }
  }
  for (index = 0; index < 3; index = index + 1)
  {
    readings [index + 20] = analogRead (index);
  }
  for (index = 0; index < 2; index = index + 1)
  {
    start_of_sound_sampling_period = millis ();
    while (millis () - start_of_sound_sampling_period < sound_sampling_period)
    {
      readings [index + 23] = analogRead (index + 3);
      if (readings [index + 23] > maximal_signal_level)
      {
        maximal_signal_level = readings [index + 23];
      }
      else if (readings [index + 23] < minimal_signal_level);
      {
        minimal_signal_level = readings [index + 23];
      }
      readings [index + 23] = maximal_signal_level - minimal_signal_level;
    }
  }
  for (index = 0; index < 2; index = index + 1)
  {
    start_of_sound_sampling_period = millis ();
    while (millis () - start_of_sound_sampling_period < sound_sampling_period)
    {
      if (readings [index + 3] > maximal_signal_level)
      {
        maximal_signal_level = readings [index + 3];
      }
      else if (readings [index + 3] < minimal_signal_level);
      {
        minimal_signal_level = readings [index + 3];
      }
      readings [index + 3] = maximal_signal_level - minimal_signal_level;
    }
  }
  for (index = 0; index < 3; index = index + 1)
  {
    heart_rate_sensory_data [index] = readings [index + 9];
  }
  for (index = 3; index < 6; index = index + 1)
  {
    heart_rate_sensory_data [index] = analogRead (index + 6);
  }
  for (index = 0; index < 4; index = index + 1)
  {
    readings [index + 25] = analogRead (index + 5);
  }
  for (index = 0; index < 4; index = index + 1)
  {
    readings [index + 32] = analogRead (index + 12);
  }
  readings [36] = digitalRead (24);
  readings [37] = digitalRead (26);
  readings [38] = digitalRead (28);
  readings [39] = digitalRead (53);
  if (need_of_transmission_to_be_restarted)
  {
    output = "";
    for (index = 0; index < 39; index = index + 1)
    {
      output = output + 0 + " ";
    }
    output = output + 0;
    Serial.println (output);
    Serial1.end ();
    Serial1.begin (9600);
  }
  else
  {
    output = "";
    for (index = 0; index < 3; index = index + 1)
    {
      readings [index + 9] = heart_rates [index];
    }
    for (index = 3; index < 6; index = index + 1)
    {
      readings [index + 26] = heart_rates [index];
    }
    for (index = 0; index < 39; index = index + 1)
    {
      output = output + readings [index] + " ";
    }
    output = output + heart_rate_sensory_data [39];
    Serial.println (output);
  }
  Serial.end ();
  delay (50);
  Serial.begin (9600);
  delay (50);
}
