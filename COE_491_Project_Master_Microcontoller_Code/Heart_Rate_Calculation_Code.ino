volatile boolean first_heartbeat_flags [6] = {true, true, true, true, true, true}, second_heartbeat_flags [6] = {true, true, true, true, true, true}, pulse_flags [6] = {false, false, false, false, false, false}, quantified_self_flags [6] = {false, false, false, false, false, false};
volatile int analog_signals [6], pulse_wave_amplitudes [6] = {100, 100, 100, 100, 100, 100}, pulse_wave_peaks [6] = {512, 512, 512, 512, 512, 512}, pulse_wave_troughs [6]  = {512, 512, 512, 512, 512, 512}, thresholds [6] = {512, 512, 512, 512, 512, 512};
volatile double interbeat_intervals [6] = {600.0, 600.0, 600.0, 600.0, 600.0, 600.0}, last_heartbeat_times [6] = {0, 0, 0, 0, 0, 0}, latest_interbeat_intervals [6] [10], pulse_sampling_timers [6] = {0, 0, 0, 0, 0, 0}, sums_of_interbeat_intervals [6] = {0, 0, 0, 0, 0, 0}, times_since_the_last_heartbeat [6];

void Configure_Interruptions ()
{
  TCCR2A = 0x02;
  TCCR2B = 0x06; 
  OCR2A = 0x7C;
  TIMSK2 = 0x02;
  sei ();
}

ISR (TIMER2_COMPA_vect)
{
  int index_1, index_2;
  cli ();
  for (index_1 = 0; index_1 < 6; index_1 = index_1 + 1)
  {
    analog_signals [index_1] = heart_rate_sensory_data [index_1];
    pulse_sampling_timers [index_1] = pulse_sampling_timers [index_1] + 2;
    times_since_the_last_heartbeat [index_1] = pulse_sampling_timers [index_1] - last_heartbeat_times [index_1];
    if (analog_signals [index_1] < thresholds [index_1] && times_since_the_last_heartbeat [index_1] > (interbeat_intervals [index_1] / 5) * 3)
    {
      if (analog_signals [index_1] < pulse_wave_troughs [index_1])
      {
        pulse_wave_troughs [index_1] = analog_signals [index_1];
      }
    }
    if (analog_signals [index_1] > thresholds [index_1] && analog_signals [index_1] > pulse_wave_peaks [index_1])
    {
      pulse_wave_peaks [index_1] = analog_signals [index_1];
    }
    if (times_since_the_last_heartbeat [index_1] > 250)
    {
      if ((analog_signals [index_1] > thresholds [index_1]) && (pulse_flags [index_1] == false) && (times_since_the_last_heartbeat [index_1] > (interbeat_intervals [index_1] / 5) * 3) )
      {        
        pulse_flags [index_1] = true;
        interbeat_intervals [index_1] = pulse_sampling_timers [index_1] - last_heartbeat_times [index_1];
        last_heartbeat_times [index_1] = pulse_sampling_timers [index_1];
        if (first_heartbeat_flags [index_1])
        {
          first_heartbeat_flags [index_1] = false;
          return;
        }   
        if (second_heartbeat_flags [index_1])
        {
          second_heartbeat_flags [index_1] = false;
          for (index_2 = 0; index_2 < 10; index_2 = index_2 + 1)
          {
            latest_interbeat_intervals [index_1] [index_2] = interbeat_intervals [index_1];                      
          }
        }
        for (index_2 = 0; index_2 < 9; index_2 = index_2 + 1)
        {
          latest_interbeat_intervals [index_1] [index_2] = latest_interbeat_intervals [index_1] [index_2 + 1]; 
          sums_of_interbeat_intervals [index_1] = sums_of_interbeat_intervals [index_1] + latest_interbeat_intervals [index_1] [index_2];
        }
        latest_interbeat_intervals [index_1] [9] = interbeat_intervals [index_1];
        sums_of_interbeat_intervals [index_1] = sums_of_interbeat_intervals [index_1] + latest_interbeat_intervals [index_1] [9];
        sums_of_interbeat_intervals [index_1] = sums_of_interbeat_intervals [index_1] / 10;
        heart_rates [index_1] = (int) (60000 / sums_of_interbeat_intervals [index_1]);
        quantified_self_flags [index_1] = true;
      }                       
    }
    if (analog_signals [index_1] < thresholds [index_1] && pulse_flags [index_1] == true)
    {
      pulse_flags [index_1] = false;
      pulse_wave_amplitudes [index_1] = pulse_wave_peaks [index_1] - pulse_wave_troughs [index_1];
      thresholds [index_1] = pulse_wave_amplitudes [index_1] / 2 + pulse_wave_troughs [index_1];
      pulse_wave_peaks [index_1] = thresholds [index_1];
      pulse_wave_troughs [index_1] = thresholds [index_1];
    }
    if (times_since_the_last_heartbeat [index_1] > 2500)
    {
      thresholds [index_1] = 512;
      pulse_wave_peaks [index_1] = 512;
      pulse_wave_troughs [index_1] = 512;
      last_heartbeat_times [index_1] = pulse_sampling_timers [index_1];     
      first_heartbeat_flags [index_1] = true;
      second_heartbeat_flags [index_1] = true;
    }
  }
  sei();
}
