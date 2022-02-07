using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Intelligent_Mattress_Data_Visualizer
{
	public interface Observer
	{
		void Update (bool [] motion_sensors_readings, double [] body_temperatures, double [] lungs_and_diaphragm_pressure_forces, double [] sound_levels, double [] sweat_content_percentages, int [] heart_rates);
	}
}
