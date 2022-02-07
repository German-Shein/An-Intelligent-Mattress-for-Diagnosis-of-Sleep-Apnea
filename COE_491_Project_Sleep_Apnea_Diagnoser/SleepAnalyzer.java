import java.util.ArrayList;

public class SleepAnalyzer
{
       ArrayList<RemInterval> rems= new ArrayList<>();
      ArrayList<NRemInterval> nrems= new ArrayList<>();
      
    private class BreathingCessationEpisode
    {
	public double standard_deviation;
	public int second_of_start, second_of_end;
	
	public BreathingCessationEpisode ()
	{
	    standard_deviation = 0;
	    second_of_start = 0;
	    second_of_end = 0;
	}
	
	public BreathingCessationEpisode (double standard_deviation, int second_of_start, int second_of_end)
	{
	    this.standard_deviation = standard_deviation;
	    this.second_of_start = second_of_start;
	    this.second_of_end = second_of_end;
	}
    }
    ArrayList<RemInterval> intervals = new ArrayList<>();
    private int seconds_of_sleep;
    
    public SleepAnalyzer ()
    {
	seconds_of_sleep = 0;
    }
   int sleepstart;
   void findsleepStart(double temperature_sensor_readings[])
   {
     int i;
     int bogusreadings=1800;
    
     double initialtemp=(((9.0/5.0)*temperature_sensor_readings[bogusreadings])+ 32.0);
     for(i=bogusreadings+1;i<temperature_sensor_readings.length;i++)
     {
         double ftemp= (((9.0/5.0)*temperature_sensor_readings[i])+ 32.0);
         if(ftemp<=initialtemp-1.3)
         {
             sleepstart=i;
             return;
         }
         
     }
       
   }
   public double normalheartrate=0;

    public int getNormalheartrate() {
        return (int) normalheartrate;
    }

    public void setNormalheartrate(double normalheartrate) {
        this.normalheartrate = normalheartrate;
    }
//  void detectSleepStages(double[] heartRates, boolean motion1[],boolean motion2[],boolean motion3[],boolean motion4[],boolean motion5[],boolean motion6[],boolean motion7[],boolean motion8[])
//  {
//      int remstart = -1,nremstart =-1,remend=-1,nremend = -1;
//      boolean inRem =false, inNrem = false;
//      for(int i=sleepstart;i<heartRates.length;i++)
//      {
//          if(heartRates[i]>=(1.07*heartRates[i]))
//          {
//          if(!inRem)
//          {
//              
//             
//              inRem = true;
//              inNrem=false;
//              nremend =i;
//              NRemInterval nrinterval = new NRemInterval(remstart,remend);
//              nrems.add(nrinterval);
//              remstart = i;
//                 
//          }
//          
//          }
//          else
//          {
//              if(inRem || i==sleepstart || motion1[i]|| motion2[i]|| motion3[i]|| motion4[i]|| motion5[i]|| motion6[i]|| motion7[i]|| motion8[i])
//              {
//                  inRem=false;
//                  inNrem =true;
//                  if(i>sleepstart)
//                  {
//                  remend = i-1;
//                  RemInterval rinterval = new RemInterval(remstart,remend);
//                  rems.add(rinterval);
//                  }
//                  nremstart=i;
//                  
//              }
//                  
//          }
//      }
//  }
 void detectSleepStages(double[] heartRates, boolean motion1[],boolean motion2[],boolean motion3[],boolean motion4[],boolean motion5[],boolean motion6[],boolean motion7[],boolean motion8[])
  {
      int remstart = -1,nremstart =-1,remend=-1,nremend = -1;
      boolean inRem =false, inNrem = false;
      for(int i=sleepstart;i<heartRates.length;i++)
      {
          if((heartRates[i]>=(1.07*heartRates[sleepstart]))&&!(motion1[i]|| motion2[i]|| motion3[i]|| motion4[i]|| motion5[i]|| motion6[i]|| motion7[i]|| motion8[i]))
          {
              int savei = i;
              
         remstart = i;
         System.out.println(heartRates[i]);
         while(((i+1)<heartRates.length)&&((heartRates[i+1]>=(1.07*60))&&!(motion1[i+1]|| motion2[i+1]|| motion3[i+1]|| motion4[i+1]|| motion5[i+1]|| motion6[i+1]|| motion7[i+1]|| motion8[i+1])))
         {
             ++i;
             System.out.println(heartRates[i]);
          
            
             
         }
               remend = i;  
               if(remend>savei)
               {
                   RemInterval rinterval = new RemInterval(remstart,remend);
                   
                   rems.add(rinterval);
               }
               
          }
          
          
          else
          {
              
              if((heartRates[i]<(1.07*heartRates[sleepstart]))||(motion1[i]|| motion2[i]|| motion3[i]|| motion4[i]|| motion5[i]|| motion6[i]|| motion7[i]|| motion8[i]))
              {
                  nremstart = i;
                  int savei = i;
                    while(((i+1)<heartRates.length)&&((heartRates[i+1]<(1.07*60))||(motion1[i+1]|| motion2[i+1]|| motion3[i+1]|| motion4[i+1]|| motion5[i+1]|| motion6[i+1]|| motion7[i+1]|| motion8[i+1])))
         {
             ++i;
            
         }
               nremend = i;  
               if(nremend>savei)
               {
                   NRemInterval nrinterval = new NRemInterval(nremstart,nremend);
                   nrems.add(nrinterval);
               }
                  
              }
                  
          }
      
      }
  }
//   boolean analyzesoundForOBSA(double[] sounds)
//    {
//       int n=sounds.length;
//       int count=0;
//        for(int i=0;i<n-3600;i++)
//        {
//            if(sounds[i]>=70)
//            {
//                count=0;
//                for(int j=i;j<3600+i;j++)
//                {
//                   int savej = j;
//                    while((sounds[j]>=70)&&j<(3600 + i))
//                   {
//                       ++j;
//                   }
//                    if(j>savej)
//                    {
//                        ++count;
//                        if(count == 5)
//                        {
//                            return true;
//                        }
//                    }
//                }
//                
//            }
//        }
//        return false;
//       
//    }
 boolean analyzesoundForOBSA(double[] sounds)
    {
       int n=sounds.length;
       int count=0;
      int i = sleepstart;
        for(;i<n-3600;i++)
        {
        for(int j=i;j<3600+i;j++)
        {
            int savej=j;
            if(sounds[j]>=70)
            {
                while(sounds[j]>=70)
                {
                    ++j;
                }
                
            }
            if(j>savej)
            {
                ++count;
                if(count>=5)
                {
                    return true;
                }
            }
            
        }
                   
               
        }
                
            //}
        
        return false;
       
    }
    
//     boolean analyzesoundForCSA(double[] sounds)
//    {
//       int n=sounds.length;
//       int count=0;
//        for(int i=0;i<n-3600;i++)
//        {
//            if(sounds[i]>=0 && sounds[i]<=20)
//            {
//                count=0;
//               
//                for(int j=i;j<3600+i;j++)
//                {
//                     int savej = j;
//                    while((sounds[j]>=0 && sounds[j]<=20)&&j<3600+i)
//                    {
//                       ++j;
//                    }
//                    if(j>savej)
//                    {
//                     ++count;
//                        if(count>=5)
//                        {
//                            return true;
//                        }
//                    }
//                }
//                
//            }
//        }
//        return false;
//       
//    }
  boolean analyzesoundForCSA(double[] sounds)
    {
       int n=sounds.length;
       int count=0;
       int i = sleepstart;
        for(;i<n-3600;i++)
        {
        for(int j=i;j<3600+i;j++)
        {
            int savej=j;
            if(sounds[j]<=20)
            {
                while(sounds[j]<=20)
                {
                    ++j;
                }
                
            }
            if(j>savej)
            {
                ++count;
                if(count>=5)
                {
                    return true;
                }
            }
            
        }
                   
               
        }
                
            //}
        
        return false;
       
    }
    public boolean DetectBreathingPausesSymptom (double [] pressure_sensors_readings_over_night)
    {
	ArrayList <BreathingCessationEpisode> breathing_cessation_episodes = new ArrayList <BreathingCessationEpisode> ();
       
	double [] standard_deviations;
	int index1, index2, index3 = 0, number_of_samples = pressure_sensors_readings_over_night.length / 5;
	double [] [] samples_of_pressure_sensors_readings_over_night;
	if (pressure_sensors_readings_over_night.length % 5 != 0)
	{
	    number_of_samples = number_of_samples + 1;
	}
	standard_deviations = new double [number_of_samples];
	samples_of_pressure_sensors_readings_over_night = new double [number_of_samples] [5];	
	for (index1 = 0; index1 < number_of_samples - 1; index1 = index1 + 1)
	{
	    for (index2 = 0; index2 < 5; index2 = index2 + 1)
	    {
		samples_of_pressure_sensors_readings_over_night [index1] [index2] = pressure_sensors_readings_over_night [index3];
		index3 = index3 + 1;
	    }
	}
	for (index1 = 0; index1 < samples_of_pressure_sensors_readings_over_night.length - index3; index1 = index1 + 1)
	{
	    samples_of_pressure_sensors_readings_over_night [number_of_samples - 1] [index1] = pressure_sensors_readings_over_night [index3];
	    index3 = index3 + 1;
	}
	for (index2 = index1; index2 < 5; index2 = index2 + 1)
	{
	    samples_of_pressure_sensors_readings_over_night [number_of_samples - 1] [index2] = 0;
	}
	for (index1 = 0; index1 < number_of_samples; index1 = index1 + 1)
	{
	    double [] temporary_array = new double [5];
	    for (index2 = 0; index2 < 5; index2 = index2 + 1)
	    {
		temporary_array [index2] = samples_of_pressure_sensors_readings_over_night [index1] [index2];
	    }
	    standard_deviations [index1] = StatisticalFunctions.CalculateStandardDeviation (temporary_array);
	    if (standard_deviations [index1] < 6.0)
	    {
		breathing_cessation_episodes.add (new BreathingCessationEpisode (standard_deviations [index1], 5 * index1, 5 * index1 + 2));
	    }
	}
	if (breathing_cessation_episodes.size () < 5)
	{
	    return false;
	}
	for (index1 = 0; index1 < breathing_cessation_episodes.size () - 4; index1 = index1 + 1)
	{
	    if (breathing_cessation_episodes.get (index1 + 4).second_of_end - breathing_cessation_episodes.get (index1).second_of_start <= 3600)
	    {
		return true;
	    }
	}
    
       
	return false;
    }
    

    /**
     *
     * @param heartrates
     * @return
     */
    int avg=0;
//public boolean analyzeNRemPulses(int[] heartrates)
//{
//   ArrayList<Boolean> analyzedPulses = new ArrayList();
//  for(int i=0;i<nrems.size();i++)
//  {
//      NRemInterval nint;
//       nint =new NRemInterval(nrems.get(i).startTime,nrems.get(i).endTime);
//       
//       double[] hrs= new double[(int)((nrems.get(i).startTime-nrems.get(i).endTime)+1)];
//       int sum =0;
//       int n=(int) ((nrems.get(i).startTime-nrems.get(i).endTime)+1);
//   
//       for(int j = (int)nrems.get(i).startTime, k=0;j<=nrems.get(i).endTime;j++,k++)
//       {
//           hrs[k]= heartrates[j];
//           sum=sum+heartrates[j];
//       }
//       avg=sum/n;
//       if(StatisticalFunctions.CalculateStandardDeviation(hrs)>=12 && (avg>=63 && avg<=87))
//       {
//           avg=0;
//           return true;
//       }
//  }
//  return false;
//   
//}                           
//public boolean analyzeRemPulses(double[] heartrates)
//{
//   ArrayList<Boolean> analyzedPulses = new ArrayList();
//  for(int i=0;i<nrems.size();i++)
//  {
//      RemInterval rint;
//       rint =new RemInterval(rems.get(i).startTime,rems.get(i).endTime);
//       
//       double[] hrs= new double[(int)((rems.get(i).startTime-rems.get(i).endTime)+1)];
//       int sum =0;
//       int n=(int) ((rems.get(i).startTime-rems.get(i).endTime)+1);
//   
//       for(int j = (int)rems.get(i).startTime, k=0;j<=rems.get(i).endTime;j++,k++)
//       {
//           hrs[k]= heartrates[j];
//           sum=(int)(sum+heartrates[j]);
//       }
//       avg=sum/n;
//       if(StatisticalFunctions.CalculateStandardDeviation(hrs)>=10 && (avg>=60 && avg<=80))
//       {
//          
//           return true;
//       }
//       avg=0;
//  }
//  return false;
//   
//}
       public  boolean analyzeNRemPulses(double[] heartrates)
{
    int avg =0;
   ArrayList<Boolean> analyzedPulses = new ArrayList();
  for(int i=0;i<nrems.size();i++)
  {
      NRemInterval nint;
       nint =new NRemInterval(nrems.get(i).startTime,nrems.get(i).endTime);
       
       double[] hrs= new double[(int)((nrems.get(i).endTime-nrems.get(i).startTime)+1)];
       int sum =0;
       int n=(int) ((nrems.get(i).endTime-nrems.get(i).startTime)+1);
   
       for(int j = (int)nrems.get(i).startTime, k=0;j<=nrems.get(i).endTime;j++,k++)
       {
           hrs[k]= heartrates[j];
           sum=(int)(sum+heartrates[j]);
       }
       avg=sum/n;
       if(StatisticalFunctions.CalculateStandardDeviation(hrs)>=12 && (avg>=63 && avg<=87))
       {
           avg=0;
           return true;
       }
  }
  return false;
   
}
    public  boolean analyzeRemPulses(double[] heartrates)
{
   ArrayList<Boolean> analyzedPulses = new ArrayList();
  for(int i=0;i<rems.size();i++)
  {
      RemInterval rint;
      int avg = 0;
       rint =new RemInterval(rems.get(0).startTime,rems.get(0).endTime);
       
       double[] hrs= new double[(int)((rems.get(i).endTime-rems.get(i).startTime)+1)];
       int sum =0;
       int n=(int) ((rems.get(i).endTime-rems.get(i).startTime)+1);

       for(int j = (int)rems.get(i).startTime, k=0;j<=rems.get(i).endTime;j++,k++)
       {
           hrs[k]= heartrates[j];
           sum=(int)(sum+heartrates[j]);
       }
       avg=sum/n;
      
       if(StatisticalFunctions.CalculateStandardDeviation(hrs)>=10 && (avg>=60 && avg<=80))
       {
          
           return true;
       }
       avg=0;
  }
  return false;
   
}
}
