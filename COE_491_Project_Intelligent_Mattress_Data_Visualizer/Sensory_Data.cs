using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Intelligent_Mattress_Data_Visualizer
{
	public class Sensory_Data: Observer
	{
		private bool [] motion_sensors_readings;
		private double [] body_temperatures, lungs_and_diaphragm_pressure_forces, sound_levels, sweat_content_percentages;
		private int [] heart_rates;
		private Subject mattress;

		public Sensory_Data (Subject mattress)
		{
			motion_sensors_readings = new bool [8];
			body_temperatures = new double [8];
			lungs_and_diaphragm_pressure_forces = new double [6];
			sweat_content_percentages = new double [8];
			sound_levels = new double [4];
			heart_rates = new int [6];
			this.mattress = mattress;
			mattress.Register_Observer (this);
		}

		public void Update (bool [] motion_sensors_readings, double [] body_temperatures, double [] lungs_and_diaphragm_pressure_forces, double [] sound_levels, double [] sweat_content_percentages, int [] heart_rates)
		{
			int count_of_zeros = 0, index;
			//Random random = new Random ();
			for (index = 0; index < motion_sensors_readings.Length; index = index + 1)
			{
				if (motion_sensors_readings [index] == false);
				{
					count_of_zeros = count_of_zeros + 1;
				}
			}
			for (index = 0; index < body_temperatures.Length; index = index + 1)
			{
				if (body_temperatures [index] == 0)
				{
					count_of_zeros = count_of_zeros + 1;
				}
			}
			for (index = 0; index < lungs_and_diaphragm_pressure_forces.Length; index = index + 1)
			{
				if (lungs_and_diaphragm_pressure_forces [index] == 0)
				{
					count_of_zeros = count_of_zeros + 1;
				}
			}
			for (index = 0; index < sound_levels.Length; index = index + 1)
			{
				if (sound_levels [index] == 0)
				{
					count_of_zeros = count_of_zeros + 1;
				}
			}
			for (index = 0; index < sweat_content_percentages.Length; index = index + 1)
			{
				if (sweat_content_percentages [index] == 0)
				{
					count_of_zeros = count_of_zeros + 1;
				}
			}
			for (index = 0; index < heart_rates.Length; index = index + 1)
			{
				if (heart_rates [index] == 0)
				{
					count_of_zeros = count_of_zeros + 1;
				}
			}
			if (count_of_zeros < 40)
			{
				for (index = 0; index < motion_sensors_readings.Length; index = index + 1)
				{
					this.motion_sensors_readings [index] = motion_sensors_readings [index];
					/*int temp = random.Next (0, 1);
					if (temp == 0)
					{
						this.motion_sensors_readings [index] = false;
					}
					else
					{
						this.motion_sensors_readings [index] = true;
					}*/
				}
				for (index = 0; index < body_temperatures.Length; index = index + 1)
				{
					this.body_temperatures [index] = body_temperatures [index];
					//this.body_temperatures [index] = random.Next (300000, 400000) / 10000.0;
				}
				for (index = 0; index < lungs_and_diaphragm_pressure_forces.Length; index = index + 1)
				{
					this.lungs_and_diaphragm_pressure_forces [index] = lungs_and_diaphragm_pressure_forces [index];
					//this.lungs_and_diaphragm_pressure_forces [index] = random.Next (0, 100000) / 1000.0;
				}
				for (index = 0; index < sound_levels.Length; index = index + 1)
				{
					this.sound_levels [index] = sound_levels [index];
					//this.sound_levels [index]  = random.Next (0, 100000) / 1000.0;
				}
				for (index = 0; index < sweat_content_percentages.Length; index = index + 1)
				{
					this.sweat_content_percentages [index] = sweat_content_percentages [index];
					//this.sweat_content_percentages [index]  = random.Next (0, 100000) / 1000.0;
				}
				for (index = 0; index < heart_rates.Length; index = index + 1)
				{
					this.heart_rates [index] = heart_rates [index];
					//this.heart_rates [index] = random.Next (40, 160);
				}
			}
		}

		public bool [] Motion_Sensors_Readings
		{
			get
			{
				return motion_sensors_readings;
			}
		}

		public double [] Body_Temperatures
		{
			get
			{
				return body_temperatures;
			}
		}

		public double [] Lungs_and_Diaphragm_Pressure_Forces
		{
			get
			{
				return lungs_and_diaphragm_pressure_forces;
			}
		}

		public double [] Sound_Levels
		{
			get
			{
				return sound_levels;
			}
		}

		public double [] Sweat_Content_Percentages
		{
			get
			{
				return sweat_content_percentages;
			}
		}

		public int [] Heart_Rates
		{
			get
			{
				return heart_rates;
			}
		}
	}
}
