using System;
using System.Collections.Generic;
using System.IO.Ports;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Intelligent_Mattress_Data_Visualizer
{
	public class Sensory_Data_Receiver: Subject
	{
		private bool [] motion_sensors_readings;
		private double [] body_temperatures, lungs_and_diaphragm_pressure_forces, sound_levels, sweat_content_percentages;
		private int [] heart_rates;
		private List<Observer> observers;
		private String port;
		private Thread thread;

		public Sensory_Data_Receiver (String port)
		{
			motion_sensors_readings = new bool [8];
			body_temperatures = new double [8];
			lungs_and_diaphragm_pressure_forces = new double [6];
			sweat_content_percentages = new double [8];
			sound_levels = new double [4];
			heart_rates = new int [6];
			observers = new List <Observer> ();
			this.port = port;
		}

		public void Notify_Observers ()
		{
			int index;
			for (index = 0; index < observers.Count; index = index + 1)
			{
				Observer observer = observers.ElementAt (index);
				observer.Update (motion_sensors_readings, body_temperatures, lungs_and_diaphragm_pressure_forces, sound_levels, sweat_content_percentages, heart_rates);
			}
		}

		public void Register_Observer (Observer observer)
		{
			observers.Add (observer);
		}

		public void Remove_Obsever (Observer observer)
		{
			int index = observers.IndexOf (observer);
			if (index >= 0)
			{
				observers.RemoveAt (index);
			}
		}

		public void Receive_Sensory_Data ()
		{
			int index;
			SerialPort serial_port = new SerialPort (port, 9600, Parity.None, 8, StopBits.One);
			try
			{
				serial_port.Open ();
				while (true)
				{
					try
					{
						String [] tokens = serial_port.ReadLine ().Split (' ');
						for (index = 0; index < 4; index = index + 1)
						{
							if (int.Parse (tokens [index + 16]) == 0)
							{
								motion_sensors_readings [index] = false;
							}
							else
							{
								motion_sensors_readings [index] = true;
							}
						}
						for (index = 0; index < 4; index = index + 1)
						{
							if (int.Parse (tokens [index + 36].Trim ()) == 0)
							{
								motion_sensors_readings [index + 4] = false;
							}
							else
							{
								motion_sensors_readings [index + 4] = true;
							}
						}
						for (index = 0; index < 4; index = index + 1)
						{
							body_temperatures [index] = int.Parse (tokens [index + 12]) * 500.0 / 1023.0;
						}
						for (index = 0; index < 4; index = index + 1)
						{
							body_temperatures [index + 4] = int.Parse (tokens [index + 32]) * 500.0 / 1023.0;
						}
						for (index = 0; index < 3; index = index + 1)
						{
							lungs_and_diaphragm_pressure_forces [index] = 10000000.0 * ((5.0 / (int.Parse (tokens [index]) * 5.0 / 1023.0)) - 1.0);
							if (lungs_and_diaphragm_pressure_forces [index] <= 600.0)
							{
								lungs_and_diaphragm_pressure_forces [index] = (((1.0 / lungs_and_diaphragm_pressure_forces [index]) - 0.00075) / 0.00000032639) * 9.81;
							}
							else
							{
								lungs_and_diaphragm_pressure_forces [index] = ((1.0 / lungs_and_diaphragm_pressure_forces [index]) / 0.000000642857) * 9.81;
							}
						}
						for (index = 0; index < 3; index = index + 1)
						{
							lungs_and_diaphragm_pressure_forces [index + 3] = 10000000.0 * ((5.0 / (int.Parse (tokens [index + 20]) * 5.0 / 1023.0)) - 1.0);
							if (lungs_and_diaphragm_pressure_forces [index + 3] <= 600.0)
							{
								lungs_and_diaphragm_pressure_forces [index + 3] = (((1.0 / lungs_and_diaphragm_pressure_forces [index + 3]) - 0.00075) / 0.00000032639) * 9.81;
							}
							else
							{
								lungs_and_diaphragm_pressure_forces [index + 3] = ((1.0 / lungs_and_diaphragm_pressure_forces [index + 3]) / 0.000000642857) * 9.81;
							}
						}
						for (index = 0; index < 2; index = index + 1)
						{
							if (int.Parse (tokens [index + 3]) == 0)
							{
								sound_levels [index] = 0;
							}
							else
							{
								sound_levels [index] = 20 * Math.Log10 ((int.Parse (tokens [index + 3]) * 5.0 / 1023.0) / 0.0001);
							}
						}
						for (index = 0; index < 2; index = index + 1)
						{
							if (int.Parse (tokens [index + 23]) == 0)
							{
								sound_levels [index] = 0;
							}
							else
							{
								sound_levels [index + 2] = 20 * Math.Log10 ((int.Parse (tokens [index + 23]) * 5.0 / 1023.0) / 0.0001);
							}
						}
						for (index = 0; index < 4; index = index + 1)
						{
							sweat_content_percentages [index] = (int.Parse (tokens [index + 5]) / 1023.0) * 100.0;
						}
						for (index = 0; index < 4; index = index + 1)
						{
							sweat_content_percentages [index + 4] = (int.Parse (tokens [index + 25]) / 1023.0) * 100.0;
						}
						for (index = 0; index < 3; index = index + 1)
						{
							heart_rates [index] = int.Parse (tokens [index + 9]);
						}
						for (index = 3; index < 6; index = index + 1)
						{
							heart_rates [index] = int.Parse (tokens [index + 29]);
						}
						Notify_Observers ();
					}
					catch (Exception exception_1)
					{
						for (index = 0; index < motion_sensors_readings.Length; index = index + 1)
						{
							motion_sensors_readings [index] = true;
						}
						for (index = 0; index < body_temperatures.Length; index = index + 1)
						{
							body_temperatures [index] = 0;
						}
						for (index = 0; index < lungs_and_diaphragm_pressure_forces.Length; index = index + 1)
						{
							lungs_and_diaphragm_pressure_forces [index] = 0;
						}
						for (index = 0; index < sound_levels.Length; index = index + 1)
						{
							sound_levels [index] = 0;
						}
						for (index = 0; index < sweat_content_percentages.Length; index = index + 1)
						{
							sweat_content_percentages [index] = 0;
						}
						for (index = 0; index < heart_rates.Length; index = index + 1)
						{
							heart_rates [index] = 0;
						}
						Notify_Observers ();
					}
				}
			}
			catch (Exception exception_2)
			{

			}
			serial_port.Close ();
		}

		public void Start_Reception ()
		{
			thread = new Thread (new ThreadStart (Receive_Sensory_Data));
			thread.Start ();
		}

		public void Stop_Reception ()
		{
			if (thread.ThreadState == ThreadState.Running)
			{
				thread.Suspend ();
			}
		}
	}
}
