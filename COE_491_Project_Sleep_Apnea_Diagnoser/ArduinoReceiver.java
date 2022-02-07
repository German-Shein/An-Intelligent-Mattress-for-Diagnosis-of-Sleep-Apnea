import jssc.SerialPort;
import jssc.SerialPortException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArduinoReceiver implements Subject, Runnable
{
    private ArrayList <Observer> observers;
    private boolean [] motion_sensors_readings;
    private double [] body_temperatures, lungs_and_diaphragm_pressure_forces, sound_levels, sweat_content_percentages;
    private int [] heart_rates;
    private String port;
    private Thread thread;

    public ArduinoReceiver (String port)
    {
	observers = new ArrayList <Observer> ();
	motion_sensors_readings = new boolean [8];
	body_temperatures = new double [8];
	lungs_and_diaphragm_pressure_forces = new double [6];
	sweat_content_percentages = new double [8];
	sound_levels = new double [4];
	heart_rates = new int [6];
	this.port = port;
	thread = new Thread (this);
	thread.start ();
    }

    private static StringBuilder ExtractCommand (int command [])
    {
	int index = 0;
	StringBuilder command_string = new StringBuilder ();
	while (command [index] != 13)
	{
	    command_string.append ((char) command [index]);
	    index = index + 1;
	}
	return command_string;
    }

    public void NotifyObservers ()
    {
	int index;
	for (index = 0; index < observers.size (); index = index + 1)
	{
	    Observer observer = observers.get (index);
	    observer.Update (motion_sensors_readings, body_temperatures, lungs_and_diaphragm_pressure_forces, sound_levels, sweat_content_percentages, heart_rates);
	}
    }

    public void RegisterObserver (Observer observer)
    {
	observers.add (observer);
    }

    public void RemoveObsever (Observer observer)
    {
	int index = observers.indexOf (observer);
	if (index >= 0)
	{
	    observers.remove (index);
	}
    }

    public void ReadSensors () throws SerialPortException
    {
	int index;
	SerialPort serial_port = new SerialPort (port);
	StringBuffer sensory_data_string_buffer = new StringBuffer ();
	try
	{
	    serial_port.openPort ();
	    serial_port.setParams (9600, 8, 1, 0);
	    while (true)
	    {
		byte character = serial_port.readBytes (1) [0];
		if (character != '\n')
		{
		    sensory_data_string_buffer.append ((char) character);
		}
		else
		{
		    try
		    {
			String tokens [] = sensory_data_string_buffer.toString ().split (" ");
			for (index = 0; index < 4; index = index + 1)
			{
			    if (Integer.parseInt (tokens [index + 16]) == 0)
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
			    if (Integer.parseInt (tokens [index + 36].trim ()) == 0)
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
			    body_temperatures [index] = Integer.parseInt (tokens [index + 12]) * 500.0 / 1023.0;
			}
			for (index = 0; index < 4; index = index + 1)
			{
			    body_temperatures [index + 4] = Integer.parseInt (tokens [index + 32]) * 500.0 / 1023.0;
			}
			for (index = 0; index < 3; index = index + 1)
			{
			    lungs_and_diaphragm_pressure_forces [index] = 10000000.0 * ((5.0 / (Integer.parseInt (tokens [index]) * 5.0 / 1023.0)) - 1.0);
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
			    lungs_and_diaphragm_pressure_forces [index + 3] = 10000000.0 * ((5.0 / (Integer.parseInt (tokens [index + 20]) * 5.0 / 1023.0)) - 1.0);
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
			    if (Integer.parseInt (tokens [index + 3]) == 0)
			    {
				sound_levels [index] = 0;
			    }
			    else
			    {
				sound_levels [index] = 20 * Math.log10 ((Integer.parseInt (tokens [index + 3]) * 5.0 / 1023.0) / 0.0001);
			    }
			}
			for (index = 0; index < 2; index = index + 1)
			{
			    if (Integer.parseInt (tokens [index + 23]) == 0)
			    {
				sound_levels [index] = 0;
			    }
			    else
			    {
				sound_levels [index + 2] = 20 * Math.log10 ((Integer.parseInt (tokens [index + 23]) * 5.0 / 1023.0) / 0.0001);
			    }
			}
			for (index = 0; index < 4; index = index + 1)
			{
			    sweat_content_percentages [index] = (Integer.parseInt (tokens [index + 5]) / 1023.0) * 100.0;
			}
			for (index = 0; index < 4; index = index + 1)
			{
			    sweat_content_percentages [index + 4] = (Integer.parseInt (tokens [index + 25]) / 1023.0) * 100.0;
			}
			for (index = 0; index < 3; index = index + 1)
			{
			    heart_rates [index] = Integer.parseInt (tokens [index + 9]);
			}
			for (index = 3; index < 6; index = index + 1)
			{
			    heart_rates [index] = Integer.parseInt (tokens [index + 29]);
			}
			NotifyObservers ();
			sensory_data_string_buffer.setLength (0);
		    }
		    catch (Exception exception_1)
		    {
			for (index = 0; index < motion_sensors_readings.length; index = index + 1)
			{
			    motion_sensors_readings [index] = true;
			}
			for (index = 0; index < body_temperatures.length; index = index + 1)
			{
			    body_temperatures [index] = 0;
			}
			for (index = 0; index < lungs_and_diaphragm_pressure_forces.length; index = index + 1)
			{
			    lungs_and_diaphragm_pressure_forces [index] = 0;
			}
			for (index = 0; index < sound_levels.length; index = index + 1)
			{
			    sound_levels [index] = 0;
			}
			for (index = 0; index < sweat_content_percentages.length; index = index + 1)
			{
			    sweat_content_percentages [index] = 0;
			}
			for (index = 0; index < heart_rates.length; index = index + 1)
			{
			    heart_rates [index] = 0;
			}
			NotifyObservers ();
			sensory_data_string_buffer.setLength (0);
		    }
		}
	    }
	}
	catch (SerialPortException exception_2)
	{
	    System.out.println (exception_2);
	}
	serial_port.closePort ();
    }

    public void run ()
    {
	try
	{
	    ReadSensors ();
	}
	catch (SerialPortException exception)
	{
	    exception.printStackTrace ();
	}
    }
}