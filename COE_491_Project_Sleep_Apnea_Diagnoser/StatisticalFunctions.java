public class StatisticalFunctions
{
    public static double CalculateMean (double [] data)
    {
	double mean = 0;
	int index;
	for (index = 0; index < data.length; index = index + 1)
	{
	    mean = mean + data [index];
	}
	mean = mean / (data.length * 1.0);
	return mean;
    }
    
    public static double CalculateStandardDeviation (double [] data)
    {
	double mean = CalculateMean (data), standard_deviation = 0;
	int index;
	for (index = 0; index < data.length; index = index + 1)
	{
	    standard_deviation = standard_deviation + (data [index] - mean) * (data [index] - mean);
	}
	standard_deviation = Math.sqrt (standard_deviation / ((data.length - 1) * 1.0));
	return standard_deviation;
    }
}
