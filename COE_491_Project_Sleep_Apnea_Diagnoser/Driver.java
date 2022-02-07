
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Vector;
import java.util.stream.Stream;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;




public class Driver 
{
    public static boolean awake = true;
    public static boolean onbed=false;
    public static Timestamp onbedts;
    public static Timestamp offbedts;
    public static boolean returnsweat(ArrayList<Double> sweats)
    {
        int sweatcount=0;
        for(int i=0;i<sweats.size();i++)
        {
            if(sweats.get(i)>0)
            {
                ++sweatcount;
                if(sweatcount>5)
                {
                    return true;
                }
            }
        }
        return false;
    }
    static double[] returnDoubleArray(ArrayList<Double> x)
    {
       double[] darry = new double[x.size()];
       for(int i=0;i<x.size();i++)
       {
           darry[i] = x.get(i).doubleValue();
       }
       return darry;
    }
    static boolean[] returnBooleanArray(ArrayList<Boolean> x)
    {
       boolean[] barry = new boolean[x.size()];
       for(int i=0;i<x.size();i++)
       {
           barry[i] = x.get(i).booleanValue();
       }
       return barry;
    }
    
    public static void main (String[] args) throws InterruptedException, SQLException 
    {
        ArduinoReceiver test = new ArduinoReceiver ("COM4");
        int tempcount=0;
	SensoryData thing = new SensoryData (test);
       
        double[] latest_temperatures;
        
         while(!onbed)
         {
             double[] latest_pressures = thing.Get_Lungs_And_Diaphragm_Pressure_Forces();
            
             int zerocount=0;
             latest_temperatures= thing.Get_Body_Temperatures();
             for(int i=0;i<8;i++)
             {
                 if(latest_temperatures[i]==0)
                {
                    ++tempcount;
                } 
             }
            
            for(int i=0;i<6;i++)
            {
                
                
                if(latest_pressures[i]==0)
                {
                    ++zerocount;
                }
               
            }
            if((zerocount<6)&&(tempcount!=0))
            {
                onbed=true;
               System.out.println("ZEROCOUNT= " +zerocount);
                onbedts = new Timestamp(new Date().getTime());
        for(int i=0;i<latest_pressures.length;i++)
        {
             System.out.println(latest_pressures[i]);
        }
                break;
            }
         
         
         System.out.println("Please lay down");
         
         }
         
         System.out.println("PERSON DETECTED AT " + onbedts);
	while (onbed)
	{
	    thing.Output ();
	    Thread.sleep (1000);
            double[] latest_pressures = thing.Get_Lungs_And_Diaphragm_Pressure_Forces();
            int zerocount=0;
            tempcount=0;
            latest_temperatures= thing.Get_Body_Temperatures();
             for(int i=0;i<8;i++)
             {
                 if(latest_temperatures[i]==0)
                {
                    ++tempcount;
                } 
             }
            for(int i=0;i<6;i++)
            {
                if(latest_pressures[i]==0)
                {
                    ++zerocount;
                }
            }
            if((zerocount==6)&&tempcount!=8)
            {
                onbed=false;
                System.out.println("Onbed= false");
                offbedts = new Timestamp(new Date().getTime());
              
                break;
                
                
            }
        }
                
                     
	
         System.out.println("PERSON GOT OFF AT "+ offbedts);
        
       
        Timestamp t = java.sql.Timestamp.valueOf("2016-12-03 19:05:34.922");
      
       CachedRowSet crs = null;
            String username= "Saurabh";
            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl("jdbc:derby://localhost:1527/patient");
            crs.setUsername("a");
            crs.setPassword("a");
            ArrayList<Timestamp> times = new ArrayList();
            ArrayList<Double> pressures= new ArrayList(); 
            
            ArrayList<Double> pressure1= new ArrayList(); 
            ArrayList<Double> pressure2= new ArrayList(); 
            ArrayList<Double> pressure3= new ArrayList(); 
            ArrayList<Double> pressure4= new ArrayList(); 
            ArrayList<Double> pressure5= new ArrayList(); 
            ArrayList<Double> pressure6= new ArrayList();
            
            ArrayList<Double> sounds= new ArrayList(); 
            
            ArrayList<Boolean> motion1= new ArrayList();
            ArrayList<Boolean> motion2= new ArrayList();
            ArrayList<Boolean> motion3= new ArrayList();
            ArrayList<Boolean> motion4= new ArrayList();
            ArrayList<Boolean> motion5= new ArrayList();
            ArrayList<Boolean> motion6= new ArrayList();
            ArrayList<Boolean> motion7= new ArrayList();
            ArrayList<Boolean> motion8= new ArrayList();
           
            ArrayList<Double> sweat1= new ArrayList(); 
            ArrayList<Double> sweat2= new ArrayList(); 
            ArrayList<Double> sweat3= new ArrayList(); 
            ArrayList<Double> sweat4= new ArrayList(); 
            ArrayList<Double> sweat5= new ArrayList(); 
            ArrayList<Double> sweat6= new ArrayList(); 
            ArrayList<Double> sweat7= new ArrayList(); 
            ArrayList<Double> sweat8= new ArrayList(); 
              
            ArrayList<Double> hearts= new ArrayList();
            
            ArrayList<Double> mic1= new ArrayList(); 
            ArrayList<Double> mic2= new ArrayList(); 
            ArrayList<Double> mic3= new ArrayList(); 
            ArrayList<Double> mic4= new ArrayList(); 
            
            ArrayList<Double> temperatures= new ArrayList(); 
            
            ArrayList<Double> temp1= new ArrayList(); 
            ArrayList<Double> temp2= new ArrayList(); 
            ArrayList<Double> temp3= new ArrayList(); 
            ArrayList<Double> temp4= new ArrayList(); 
            ArrayList<Double> temp5= new ArrayList(); 
            ArrayList<Double> temp6= new ArrayList(); 
            ArrayList<Double> temp7= new ArrayList(); 
            ArrayList<Double> temp8= new ArrayList(); 
            crs.setCommand("select * from pressures where username= ? and  moment>= ? and moment<= ?");
            crs.setTimestamp(2,onbedts);
            crs.setTimestamp(3,offbedts);
            crs.setString(1,username);
            crs.execute(); 
            double ps1=0,ps2=0,ps3=0,ps4=0,ps5=0,ps6=0;
            while(crs.next())
            {
                Timestamp t1 = crs.getTimestamp("moment");
                ps1 = crs.getDouble("press1");
                ps2 = crs.getDouble("press2");
                ps3 = crs.getDouble("press3");
                ps4 = crs.getDouble("press4");
                ps5 = crs.getDouble("press5");
                ps6 = crs.getDouble("press6");
                pressure1.add(ps1);
                pressure2.add(ps2);
                pressure3.add(ps3);
                pressure4.add(ps4);
                pressure5.add(ps5);
                pressure6.add(ps6);
                times.add(t1);
                pressures.add(ps1);
                pressures.add(ps2);
                pressures.add(ps3);
                pressures.add(ps4);
                pressures.add(ps5);
                pressures.add(ps6);
                
            }
            for(int i=0;i<pressure1.size();i++)
            {
                System.out.println(pressure4.get(i));
            }
            crs.setCommand("select * from heartrates where username=? and  moment>= ? and moment<= ? ");
            crs.setTimestamp(2, onbedts);
            crs.setTimestamp(3, offbedts);
            crs.setString(1,username);
            crs.execute();      
            int i= 0;
            while(crs.next() && i<pressure1.size())
            {
                t = crs.getTimestamp("moment");
                double hr1 = crs.getDouble("heart1");
                double hr2 = crs.getDouble("heart2");
                double hr3 = crs.getDouble("heart3");
                double hr4 = crs.getDouble("heart4");
                double hr5 = crs.getDouble("heart5");
                double hr6 = crs.getDouble("heart6");
                if((pressure1.get(i)+pressure4.get(i))>(pressure2.get(i)+pressure5.get(i)))
                {
                    if(pressure1.get(i)>pressure4.get(i))
                    {
                        hearts.add(hr1);
                    }
                    else
                    {
                        hearts.add(hr4);
                    }
                    
                }
                else if((pressure2.get(i)+pressure5.get(i))>(pressure3.get(i)+pressure6.get(i)))
                {
                     if(ps2>ps5)
                    {
                        hearts.add(hr2);
                    }
                    else
                    {
                        hearts.add(hr5);
                    }
                }
                
                else 
                {
                     if(pressure3.get(i)>pressure6.get(i))
                    {
                        hearts.add(hr3);
                    }
                    else
                    {
                        hearts.add(hr6);
                    }
                }
                ++i;
               
                
            }
      
            crs.setCommand("select * from motions where username=? and  moment>= ? and moment<=? ");
            crs.setTimestamp(2, onbedts);
             crs.setTimestamp(3, offbedts);
            crs.setString(1,username);
            crs.execute(); 
         
            i =0;
            while(crs.next())
            {
                  t = crs.getTimestamp("moment");
               
                motion1.add(crs.getBoolean("motion1"));
                motion2.add(crs.getBoolean("motion2"));
                motion3.add(crs.getBoolean("motion3"));
                motion4.add(crs.getBoolean("motion4"));
                motion5.add(crs.getBoolean("motion5"));
                motion6.add(crs.getBoolean("motion6"));
                motion7.add(crs.getBoolean("motion7"));
                motion8.add(crs.getBoolean("motion8"));
                ++i;
            }
            
           
             
            crs.setCommand("select * from sweats where username= ? and  moment>= ? and moment<= ?");
            crs.setTimestamp(2, onbedts);
            crs.setTimestamp(3, offbedts);
            crs.setString(1,username);
            crs.execute(); 
         
            i =0;
            while(crs.next())
            {
            
               
                sweat1.add(crs.getDouble("sweat1"));
                sweat2.add(crs.getDouble("sweat2"));
                sweat3.add(crs.getDouble("sweat3"));
                sweat4.add(crs.getDouble("sweat4"));
                sweat5.add(crs.getDouble("sweat5"));
                sweat6.add(crs.getDouble("HEART6"));
                sweat7.add(crs.getDouble("sweat7"));
                sweat8.add(crs.getDouble("sweat8"));
                ++i;
            }
            
            crs.setCommand("select * from microphones where username= ? and  moment>= ? and moment<= ? ");
            crs.setTimestamp(2, onbedts);
            crs.setString(1,username);
            crs.setTimestamp(3, offbedts);
            crs.execute(); 
         boolean hassweat =( returnsweat(sweat1)||returnsweat(sweat2)||returnsweat(sweat3)||returnsweat(sweat4)||returnsweat(sweat5)||returnsweat(sweat6)||returnsweat(sweat7)||returnsweat(sweat8));
         
            i =0;
            while(crs.next())
            {
            
               
                mic1.add(crs.getDouble("microphone1"));
                mic2.add(crs.getDouble("microphone2"));
                mic3.add(crs.getDouble("microphone3"));
                mic4.add(crs.getDouble("microphone4"));
                
                ++i;
            }
             crs.setCommand("select * from temperatures where username= ? and  moment>= ? and moment<= ? ");
            crs.setTimestamp(2, onbedts);
            crs.setTimestamp(3, offbedts);
            crs.setString(1,username);
            crs.execute(); 
         
            i =0;
            while(crs.next())
            {
            
               
                temp1.add(crs.getDouble("temp1"));
                temp2.add(crs.getDouble("temp2"));
                temp3.add(crs.getDouble("temp3"));
                temp4.add(crs.getDouble("temp4"));
                temp5.add(crs.getDouble("temp5"));
                temp6.add(crs.getDouble("temp6"));
                temp7.add(crs.getDouble("temp7"));
                temp8.add(crs.getDouble("temp8"));
                System.out.println("Temp1 "+temp1.get(i));
                ++i;
            }
            i=0;
            while(i<temp1.size())
            {
                if((temp1.get(i)+temp2.get(i))>(temp3.get(i)+temp4.get(i)))
                {
                if((temp1.get(i)+temp2.get(i))>(temp2.get(i)+temp3.get(i)))
                {
                    if(temp1.get(i)>temp2.get(i))
                    {
                        temperatures.add(temp1.get(i));
                    }
                    else
                    {
                        
                        temperatures.add(temp2.get(i));
                    }
                }
                else
                {
                  if(temp2.get(i)>temp3.get(i))
                    {
                        temperatures.add(temp2.get(i));
                    }
                    else
                    {
                        
                        temperatures.add(temp3.get(i));
                    }
                }
                }
                else 
                {
                if((temp3.get(i)+temp4.get(i))>(temp2.get(i)+temp3.get(i)))
                {
                    if(temp3.get(i)>temp4.get(i))
                    {
                        temperatures.add(temp3.get(i));
                    }
                    else
                    {
                        
                        temperatures.add(temp4.get(i));
                    }
                    
                }
                else
                {
                  if(temp2.get(i)>temp3.get(i))
                    {
                        temperatures.add(temp2.get(i));
                    }
                    else
                    {
                        
                        temperatures.add(temp3.get(i));
                    }
                }
                }
                ++i;
            }
           System.out.println("Sizze of tem perature"+temperatures.size());



       

            
         double hrts[] = returnDoubleArray(hearts);
         
         boolean mtn1[] = returnBooleanArray(motion1);
         boolean mtn2[] = returnBooleanArray(motion2);
         boolean mtn3[] = returnBooleanArray(motion3); 
         boolean mtn4[] = returnBooleanArray(motion4);
         boolean mtn5[] = returnBooleanArray(motion5);
         boolean mtn6[] = returnBooleanArray(motion6);       
         boolean mtn7[] = returnBooleanArray(motion7);
         boolean mtn8[] = returnBooleanArray(motion8);
         
         double swt1[] = returnDoubleArray(sweat1);
         double swt2[] = returnDoubleArray(sweat2);
         double swt3[] = returnDoubleArray(sweat3);
         double swt4[] = returnDoubleArray(sweat4);
         double swt5[] = returnDoubleArray(sweat5);
         double swt6[] = returnDoubleArray(sweat6);
         double swt7[] = returnDoubleArray(sweat7);
         double swt8[] = returnDoubleArray(sweat8);
         
         double prs1[] = returnDoubleArray(pressure1);
         double prs2[] = returnDoubleArray(pressure2);
         double prs3[] = returnDoubleArray(pressure3);
         double prs4[] = returnDoubleArray(pressure4);
         double prs5[] = returnDoubleArray(pressure5);
         double prs6[] = returnDoubleArray(pressure6);
         
         double temps[] = returnDoubleArray(temperatures);
         
         double m1[] = returnDoubleArray(mic1);
         double m2[] = returnDoubleArray(mic2);
         double m3[] = returnDoubleArray(mic3);
         double m4[] = returnDoubleArray(mic4);
         for( i=0;i<mtn1.length;i++)
         {
             System.out.println("MTN1 "+mtn1[i]);
         }
         for(i=0;i<prs2.length;i++)
         {
             System.out.println("PRS2 "+ prs2[i]);
         }
         SleepAnalyzer analyzer= new SleepAnalyzer();
         analyzer.findsleepStart(temps);
         boolean m1OSAanalyze = analyzer.analyzesoundForOBSA(m1);
         boolean m2OSAanalyze = analyzer.analyzesoundForOBSA(m2);
         boolean m3OSAanalyze = analyzer.analyzesoundForOBSA(m3); 
         boolean m4OSAanalyze = analyzer.analyzesoundForOBSA(m4);
         
         boolean m1CSAanalyze = analyzer.analyzesoundForCSA(m1);
         boolean m2CSAanalyze = analyzer.analyzesoundForCSA(m2);
         boolean m3CSAanalyze = analyzer.analyzesoundForCSA(m3); 
         boolean m4CSAanalyze = analyzer.analyzesoundForCSA(m4);
         
         boolean snoring=false,cessation=false;
         if(m1OSAanalyze||m2OSAanalyze||m3OSAanalyze||m4OSAanalyze)
         {
             snoring = true;
         }
         if(m1CSAanalyze||m2CSAanalyze||m3CSAanalyze||m4CSAanalyze)
         {
             cessation = true;
         }
        analyzer.detectSleepStages(hrts, mtn1, mtn2, mtn3, mtn4, mtn5, mtn6, mtn7, mtn8);
       boolean abNREMPulse = analyzer.analyzeNRemPulses(hrts);
       boolean abREMPulse = analyzer.analyzeRemPulses(hrts);
       ArrayList<Double> press;
        press = new ArrayList();
        double maxPress;
        for(i=0;i<pressure1.size();i++)
        {
            double[] tempArray = new double[6];
            tempArray[0]= pressure1.get(i);
            tempArray[1]= pressure2.get(i);
            tempArray[2]= pressure3.get(i);
            tempArray[3]= pressure4.get(i);
            tempArray[4]= pressure5.get(i);
            tempArray[5]= pressure6.get(i);
            maxPress= tempArray[0];
            for(int j=1;j<6;j++)
            {
             if(tempArray[j]>maxPress)
             {
                 maxPress = tempArray[j];
             }
            }
            
            press.add(maxPress);
        }
       boolean pressureCSA=analyzer.DetectBreathingPausesSymptom(returnDoubleArray(press));
       boolean soundCSA=false,cessations_present=false;
       if(m1CSAanalyze||m2CSAanalyze||m3CSAanalyze||m4CSAanalyze)
       {
        soundCSA=true;   
       }
       if(soundCSA && pressureCSA)
       {
           cessations_present=true;
       }
       if(m1OSAanalyze||m2OSAanalyze||m3OSAanalyze||m4OSAanalyze)
       {
           snoring =true;
       }
       String risk="";
      boolean[] decarr= new boolean[3];
      decarr[1]=cessations_present;
      decarr[2]=snoring;
      decarr[3]=(abNREMPulse||abREMPulse);
      int count=0;
      for( i=0;i<3;i++)
      {
          if(decarr[i])
          {
           ++count;   
          }
      }
      if(hassweat){++count;};
      if(count>=3)
      {
          risk="High Risk";
      }
      if(count==2)
      {
          risk="Medium Risk";
      }
       if(count==1)
      {
          risk="Low Risk";
      }
      if(count==0)
      {
          risk="No Risk";
      }
      crs.setCommand("insert into results values(?,?,?)");
      crs.setString(1, "Saurabh");
      crs.setTimestamp(2, new Timestamp(new Date().getTime()));
      crs.setString(3,risk);
      crs.execute();
      
    }
}