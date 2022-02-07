using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Intelligent_Mattress_Data_Visualizer
{
	public partial class Form1: Form
	{
		private List <bool []> motion_sensors_readings;
		private List <double []> body_temperatures, lungs_and_diaphragm_pressure_forces, sound_levels, sweat_content_percentages;
		private List <int []> heart_rates;
		private Sensory_Data sensory_data;
		private Sensory_Data_Receiver sensory_data_receiver;
		private string port;
		private Thread data_collector;

		public Form1 ()
		{
			InitializeComponent ();
			motion_sensors_readings = new List <bool []> ();
			body_temperatures = new List <double []> ();
			lungs_and_diaphragm_pressure_forces = new List <double []> ();
			sound_levels = new List <double []> ();
			sweat_content_percentages = new List <double []> ();
			heart_rates = new List <int []> ();
			port = "";
		}

		private void button1_Click (object sender, EventArgs e)
		{
			startTransmissionToolStripMenuItem.Enabled = true;
			port = textBox1.Text;
			sensory_data_receiver = new Sensory_Data_Receiver (port);
		}

		private void Form1_FormClosing (object sender, FormClosingEventArgs e)
		{
			while (true)
			{
				try
				{
					Environment.Exit (0);
				}
				catch (ThreadAbortException)
				{

				}
				catch (ThreadInterruptedException)
				{

				}
			}
		}

		private void startTransmissionToolStripMenuItem_Click (object sender, EventArgs e)
		{
			sensory_data = new Sensory_Data (sensory_data_receiver);
			endTransmissionToolStripMenuItem.Enabled = true;
			sensory_data_receiver.Start_Reception ();
			data_collector = new Thread (new ThreadStart (Collect_Data));
			data_collector.Start ();
		}

		private void Collect_Data ()
		{
			while (true)
			{
				int index;
				bool [] temporary_array_1 = new bool [sensory_data.Motion_Sensors_Readings.Length];
				double [] temporary_array_2 = new double [sensory_data.Body_Temperatures.Length];
				double [] temporary_array_3 = new double [sensory_data.Lungs_and_Diaphragm_Pressure_Forces.Length];
				double [] temporary_array_4 = new double [sensory_data.Sound_Levels.Length];
				double [] temporary_array_5 = new double [sensory_data.Sweat_Content_Percentages.Length];
				int [] temporary_array_6 = new int [sensory_data.Heart_Rates.Length];
				for (index = 0; index < temporary_array_1.Length; index = index + 1)
				{
					temporary_array_1 [index] = sensory_data.Motion_Sensors_Readings [index];
				}
				for (index = 0; index < temporary_array_2.Length; index = index + 1)
				{
					temporary_array_2 [index] = sensory_data.Body_Temperatures [index];
				}
				for (index = 0; index < temporary_array_3.Length; index = index + 1)
				{
					temporary_array_3 [index] = sensory_data.Lungs_and_Diaphragm_Pressure_Forces [index];
				}
				for (index = 0; index < temporary_array_4.Length; index = index + 1)
				{
					temporary_array_4 [index] = sensory_data.Sound_Levels [index];
				}
				for (index = 0; index < temporary_array_5.Length; index = index + 1)
				{
					temporary_array_5 [index] = sensory_data.Sweat_Content_Percentages [index];
				}
				for (index = 0; index < temporary_array_6.Length; index = index + 1)
				{
					temporary_array_6 [index] = sensory_data.Heart_Rates [index];
				}
				motion_sensors_readings.Add (temporary_array_1);
				body_temperatures.Add (temporary_array_2);
				lungs_and_diaphragm_pressure_forces.Add (temporary_array_3);
				sound_levels.Add (temporary_array_4);
				sweat_content_percentages.Add (temporary_array_5);
				heart_rates.Add (temporary_array_6);
				Thread.Sleep (100);
			}
		}

		private void endTransmissionToolStripMenuItem_Click (object sender, EventArgs e)
		{
			endTransmissionToolStripMenuItem.Enabled = false;
			sensory_data_receiver.Stop_Reception ();
			data_collector.Abort ();
		}

		private void heartRateToolStripMenuItem_Click (object sender, EventArgs e)
		{
			int index;
			chart1.Series [0].Points.Clear ();
			chart2.Series [0].Points.Clear ();
			chart3.Series [0].Points.Clear ();
			chart4.Series [0].Points.Clear ();
			chart5.Series [0].Points.Clear ();
			chart6.Series [0].Points.Clear ();
			chart7.Series [0].Points.Clear ();
			chart8.Series [0].Points.Clear ();
			chart1.ChartAreas [0].AxisY.Title = "Heart Rate (BPM)";
			chart2.ChartAreas [0].AxisY.Title = "Heart Rate (BPM)";
			chart3.ChartAreas [0].AxisY.Title = "Heart Rate (BPM)";
			chart4.ChartAreas [0].AxisY.Title = "Heart Rate (BPM)";
			chart5.ChartAreas [0].AxisY.Title = "Heart Rate (BPM)";
			chart6.ChartAreas [0].AxisY.Title = "Heart Rate (BPM)";
			chart7.ChartAreas [0].AxisY.Title = "Heart Rate (BPM)";
			chart8.ChartAreas [0].AxisY.Title = "Heart Rate (BPM)";
			for (index = 0; index < heart_rates.Count; index = index + 1)
			{
				chart1.Series [0].Points.AddXY ((index + 1) * 0.1, heart_rates.ElementAt (index) [0]);
			}
			for (index = 0; index < heart_rates.Count; index = index + 1)
			{
				chart2.Series [0].Points.AddXY ((index + 1) * 0.1, heart_rates.ElementAt (index) [1]);
			}
			for (index = 0; index < heart_rates.Count; index = index + 1)
			{
				chart3.Series [0].Points.AddXY ((index + 1) * 0.1, heart_rates.ElementAt (index) [2]);
			}
			for (index = 0; index < heart_rates.Count; index = index + 1)
			{
				chart4.Series [0].Points.AddXY ((index + 1) * 0.1, heart_rates.ElementAt (index) [3]);
			}
			for (index = 0; index < heart_rates.Count; index = index + 1)
			{
				chart5.Series [0].Points.AddXY ((index + 1) * 0.1, heart_rates.ElementAt (index) [4]);
			}
			for (index = 0; index < heart_rates.Count; index = index + 1)
			{
				chart6.Series [0].Points.AddXY ((index + 1) * 0.1, heart_rates.ElementAt (index) [5]);
			}
		}

		private void temperatureToolStripMenuItem_Click (object sender, EventArgs e)
		{
			int index;
			chart1.Series [0].Points.Clear ();
			chart2.Series [0].Points.Clear ();
			chart3.Series [0].Points.Clear ();
			chart4.Series [0].Points.Clear ();
			chart5.Series [0].Points.Clear ();
			chart6.Series [0].Points.Clear ();
			chart7.Series [0].Points.Clear ();
			chart8.Series [0].Points.Clear ();
			chart1.ChartAreas [0].AxisY.Title = "Temperature (°C)";
			chart2.ChartAreas [0].AxisY.Title = "Temperature (°C)";
			chart3.ChartAreas [0].AxisY.Title = "Temperature (°C)";
			chart4.ChartAreas [0].AxisY.Title = "Temperature (°C)";
			chart5.ChartAreas [0].AxisY.Title = "Temperature (°C)";
			chart6.ChartAreas [0].AxisY.Title = "Temperature (°C)";
			chart7.ChartAreas [0].AxisY.Title = "Temperature (°C)";
			chart8.ChartAreas [0].AxisY.Title = "Temperature (°C)";
			for (index = 0; index < body_temperatures.Count; index = index + 1)
			{
				chart1.Series [0].Points.AddXY ((index + 1) * 0.1, body_temperatures.ElementAt (index) [0]);
			}
			for (index = 0; index < body_temperatures.Count; index = index + 1)
			{
				chart2.Series [0].Points.AddXY ((index + 1) * 0.1, body_temperatures.ElementAt (index) [1]);
			}
			for (index = 0; index < body_temperatures.Count; index = index + 1)
			{
				chart3.Series [0].Points.AddXY ((index + 1) * 0.1, body_temperatures.ElementAt (index) [2]);
			}
			for (index = 0; index < body_temperatures.Count; index = index + 1)
			{
				chart4.Series [0].Points.AddXY ((index + 1) * 0.1, body_temperatures.ElementAt (index) [3]);
			}
			for (index = 0; index < body_temperatures.Count; index = index + 1)
			{
				chart5.Series [0].Points.AddXY ((index + 1) * 0.1, body_temperatures.ElementAt (index) [4]);
			}
			for (index = 0; index < body_temperatures.Count; index = index + 1)
			{
				chart6.Series [0].Points.AddXY ((index + 1) * 0.1, body_temperatures.ElementAt (index) [5]);
			}
			for (index = 0; index < body_temperatures.Count; index = index + 1)
			{
				chart7.Series [0].Points.AddXY ((index + 1) * 0.1, body_temperatures.ElementAt (index) [6]);
			}
			for (index = 0; index < body_temperatures.Count; index = index + 1)
			{
				chart8.Series [0].Points.AddXY ((index + 1) * 0.1, body_temperatures.ElementAt (index) [7]);
			}
		}

		private void pressureToolStripMenuItem_Click (object sender, EventArgs e)
		{
			int index;
			chart1.Series [0].Points.Clear ();
			chart2.Series [0].Points.Clear ();
			chart3.Series [0].Points.Clear ();
			chart4.Series [0].Points.Clear ();
			chart5.Series [0].Points.Clear ();
			chart6.Series [0].Points.Clear ();
			chart7.Series [0].Points.Clear ();
			chart8.Series [0].Points.Clear ();
			chart1.ChartAreas [0].AxisY.Title = "Pressure Force (N)";
			chart2.ChartAreas [0].AxisY.Title = "Pressure Force (N)";
			chart3.ChartAreas [0].AxisY.Title = "Pressure Force (N)";
			chart4.ChartAreas [0].AxisY.Title = "Pressure Force (N)";
			chart5.ChartAreas [0].AxisY.Title = "Pressure Force (N)";
			chart6.ChartAreas [0].AxisY.Title = "Pressure Force (N)";
			chart7.ChartAreas [0].AxisY.Title = "Pressure Force (N)";
			chart8.ChartAreas [0].AxisY.Title = "Pressure Force (N)";
			for (index = 0; index < lungs_and_diaphragm_pressure_forces.Count; index = index + 1)
			{
				chart1.Series [0].Points.AddXY ((index + 1) * 0.1, lungs_and_diaphragm_pressure_forces.ElementAt (index) [0]);
			}
			for (index = 0; index < lungs_and_diaphragm_pressure_forces.Count; index = index + 1)
			{
				chart2.Series [0].Points.AddXY ((index + 1) * 0.1, lungs_and_diaphragm_pressure_forces.ElementAt (index) [1]);
			}
			for (index = 0; index < lungs_and_diaphragm_pressure_forces.Count; index = index + 1)
			{
				chart3.Series [0].Points.AddXY ((index + 1) * 0.1, lungs_and_diaphragm_pressure_forces.ElementAt (index) [2]);
			}
			for (index = 0; index < lungs_and_diaphragm_pressure_forces.Count; index = index + 1)
			{
				chart4.Series [0].Points.AddXY ((index + 1) * 0.1, lungs_and_diaphragm_pressure_forces.ElementAt (index) [3]);
			}
			for (index = 0; index < lungs_and_diaphragm_pressure_forces.Count; index = index + 1)
			{
				chart5.Series [0].Points.AddXY ((index + 1) * 0.1, lungs_and_diaphragm_pressure_forces.ElementAt (index) [4]);
			}
			for (index = 0; index < lungs_and_diaphragm_pressure_forces.Count; index = index + 1)
			{
				chart6.Series [0].Points.AddXY ((index + 1) * 0.1, lungs_and_diaphragm_pressure_forces.ElementAt (index) [5]);
			}
		}

		private void microphoneToolStripMenuItem_Click (object sender, EventArgs e)
		{
			int index;
			chart1.Series [0].Points.Clear ();
			chart2.Series [0].Points.Clear ();
			chart3.Series [0].Points.Clear ();
			chart4.Series [0].Points.Clear ();
			chart5.Series [0].Points.Clear ();
			chart6.Series [0].Points.Clear ();
			chart7.Series [0].Points.Clear ();
			chart8.Series [0].Points.Clear ();
			chart1.ChartAreas [0].AxisY.Title = "Sound Level (dB)";
			chart2.ChartAreas [0].AxisY.Title = "Sound Level (dB)";
			chart3.ChartAreas [0].AxisY.Title = "Sound Level (dB)";
			chart4.ChartAreas [0].AxisY.Title = "Sound Level (dB)";
			chart5.ChartAreas [0].AxisY.Title = "Sound Level (dB)";
			chart6.ChartAreas [0].AxisY.Title = "Sound Level (dB)";
			chart7.ChartAreas [0].AxisY.Title = "Sound Level (dB)";
			chart8.ChartAreas [0].AxisY.Title = "Sound Level (dB)";
			for (index = 0; index < sound_levels.Count; index = index + 1)
			{
				chart1.Series [0].Points.AddXY ((index + 1) * 0.1, sound_levels.ElementAt (index) [0]);
			}
			for (index = 0; index < sound_levels.Count; index = index + 1)
			{
				chart2.Series [0].Points.AddXY ((index + 1) * 0.1, sound_levels.ElementAt (index) [1]);
			}
			for (index = 0; index < sound_levels.Count; index = index + 1)
			{
				chart3.Series [0].Points.AddXY ((index + 1) * 0.1, sound_levels.ElementAt (index) [2]);
			}
			for (index = 0; index < sound_levels.Count; index = index + 1)
			{
				chart4.Series [0].Points.AddXY ((index + 1) * 0.1, sound_levels.ElementAt (index) [2]);
			}
		}

		private void sweatToolStripMenuItem_Click (object sender, EventArgs e)
		{
			int index;
			chart1.Series [0].Points.Clear ();
			chart2.Series [0].Points.Clear ();
			chart3.Series [0].Points.Clear ();
			chart4.Series [0].Points.Clear ();
			chart5.Series [0].Points.Clear ();
			chart6.Series [0].Points.Clear ();
			chart7.Series [0].Points.Clear ();
			chart8.Series [0].Points.Clear ();
			chart1.ChartAreas [0].AxisY.Title = "Sweat Percentage (%)";
			chart2.ChartAreas [0].AxisY.Title = "Sweat Percentage (%)";
			chart3.ChartAreas [0].AxisY.Title = "Sweat Percentage (%)";
			chart4.ChartAreas [0].AxisY.Title = "Sweat Percentage (%)";
			chart5.ChartAreas [0].AxisY.Title = "Sweat Percentage (%)";
			chart6.ChartAreas [0].AxisY.Title = "Sweat Percentage (%)";
			chart7.ChartAreas [0].AxisY.Title = "Sweat Percentage (%)";
			chart8.ChartAreas [0].AxisY.Title = "Sweat Percentage (%)";
			for (index = 0; index < sweat_content_percentages.Count; index = index + 1)
			{
				chart1.Series [0].Points.AddXY ((index + 1) * 0.1, sweat_content_percentages.ElementAt (index) [0]);
			}
			for (index = 0; index < sweat_content_percentages.Count; index = index + 1)
			{
				chart2.Series [0].Points.AddXY ((index + 1) * 0.1, sweat_content_percentages.ElementAt (index) [1]);
			}
			for (index = 0; index < sweat_content_percentages.Count; index = index + 1)
			{
				chart3.Series [0].Points.AddXY ((index + 1) * 0.1, sweat_content_percentages.ElementAt (index) [2]);
			}
			for (index = 0; index < sweat_content_percentages.Count; index = index + 1)
			{
				chart4.Series [0].Points.AddXY ((index + 1) * 0.1, sweat_content_percentages.ElementAt (index) [3]);
			}
			for (index = 0; index < sweat_content_percentages.Count; index = index + 1)
			{
				chart5.Series [0].Points.AddXY ((index + 1) * 0.1, sweat_content_percentages.ElementAt (index) [4]);
			}
			for (index = 0; index < sweat_content_percentages.Count; index = index + 1)
			{
				chart6.Series [0].Points.AddXY ((index + 1) * 0.1, sweat_content_percentages.ElementAt (index) [5]);
			}
			for (index = 0; index < sweat_content_percentages.Count; index = index + 1)
			{
				chart7.Series [0].Points.AddXY ((index + 1) * 0.1, sweat_content_percentages.ElementAt (index) [6]);
			}
			for (index = 0; index < sweat_content_percentages.Count; index = index + 1)
			{
				chart8.Series [0].Points.AddXY ((index + 1) * 0.1, sweat_content_percentages.ElementAt (index) [7]);
			}
		}

		private void motionToolStripMenuItem_Click (object sender, EventArgs e)
		{
			int index;
			chart1.Series [0].Points.Clear ();
			chart2.Series [0].Points.Clear ();
			chart3.Series [0].Points.Clear ();
			chart4.Series [0].Points.Clear ();
			chart5.Series [0].Points.Clear ();
			chart6.Series [0].Points.Clear ();
			chart7.Series [0].Points.Clear ();
			chart8.Series [0].Points.Clear ();
			chart1.ChartAreas [0].AxisY.Title = "Motion";
			chart2.ChartAreas [0].AxisY.Title = "Motion";
			chart3.ChartAreas [0].AxisY.Title = "Motion";
			chart4.ChartAreas [0].AxisY.Title = "Motion";
			chart5.ChartAreas [0].AxisY.Title = "Motion";
			chart6.ChartAreas [0].AxisY.Title = "Motion";
			chart7.ChartAreas [0].AxisY.Title = "Motion";
			chart8.ChartAreas [0].AxisY.Title = "Motion";
			for (index = 0; index < motion_sensors_readings.Count; index = index + 1)
			{
				chart1.Series [0].Points.AddXY ((index + 1) * 0.1, motion_sensors_readings.ElementAt (index) [0]);
			}
			for (index = 0; index < motion_sensors_readings.Count; index = index + 1)
			{
				chart2.Series [0].Points.AddXY ((index + 1) * 0.1, motion_sensors_readings.ElementAt (index) [1]);
			}
			for (index = 0; index < motion_sensors_readings.Count; index = index + 1)
			{
				chart3.Series [0].Points.AddXY ((index + 1) * 0.1, motion_sensors_readings.ElementAt (index) [2]);
			}
			for (index = 0; index < motion_sensors_readings.Count; index = index + 1)
			{
				chart4.Series [0].Points.AddXY ((index + 1) * 0.1, motion_sensors_readings.ElementAt (index) [3]);
			}
			for (index = 0; index < motion_sensors_readings.Count; index = index + 1)
			{
				chart5.Series [0].Points.AddXY ((index + 1) * 0.1, motion_sensors_readings.ElementAt (index) [4]);
			}
			for (index = 0; index < motion_sensors_readings.Count; index = index + 1)
			{
				chart6.Series [0].Points.AddXY ((index + 1) * 0.1, motion_sensors_readings.ElementAt (index) [5]);
			}
			for (index = 0; index < motion_sensors_readings.Count; index = index + 1)
			{
				chart7.Series [0].Points.AddXY ((index + 1) * 0.1, motion_sensors_readings.ElementAt (index) [6]);
			}
			for (index = 0; index < motion_sensors_readings.Count; index = index + 1)
			{
				chart8.Series [0].Points.AddXY ((index + 1) * 0.1, motion_sensors_readings.ElementAt (index) [7]);
			}
		}
	}
}
