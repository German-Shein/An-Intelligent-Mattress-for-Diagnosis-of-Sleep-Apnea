
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class SensoryData implements Observer
{
    private boolean [] motion_sensors_readings;
    private boolean [] prev_motion_sensors_readings;
    private double [] body_temperatures, lungs_and_diaphragm_pressure_forces, sound_levels, sweat_content_percentages;
     private double [] prev_body_temperatures, prev_lungs_and_diaphragm_pressure_forces, prev_sound_levels, prev_sweat_content_percentages;
    private int [] heart_rates;
     private int [] prev_heart_rates;
    private Subject mattress;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    public SensoryData (Subject mattress)
    {
	motion_sensors_readings = new boolean [8];
	body_temperatures = new double [8];
	lungs_and_diaphragm_pressure_forces = new double [6];
	sweat_content_percentages = new double [8];
	sound_levels = new double [4];
	heart_rates = new int [6];
        prev_motion_sensors_readings = new boolean [8];
	prev_body_temperatures = new double [8];
	prev_lungs_and_diaphragm_pressure_forces = new double [6];
	prev_sweat_content_percentages = new double [8];
	prev_sound_levels = new double [4];
	prev_heart_rates = new int [6];
	this.mattress = mattress;
	this.mattress.RegisterObserver (this);
        for(int i=0;i<8;i++)
        {
            if(i<4)
            {
                
                prev_sound_levels[i]=0.0;
            }
            if(i<6)
            {
                prev_lungs_and_diaphragm_pressure_forces[i] =0.0;
                prev_heart_rates[i]=0;
                
            }
            prev_motion_sensors_readings[i]=false;
            prev_body_temperatures[i]=0.0;
            prev_sweat_content_percentages[i]=0.0;
            
            
            
            
            
        }
        
    }
  boolean start =false;
    int temp_zero_count=0;
    public void Update (boolean [] motion_sensors_readings, double [] body_temperatures, double [] lungs_and_diaphragm_pressure_forces, double [] sound_levels, double [] sweat_content_percentages, int [] heart_rates)
    {
        try {
            int index;
            CachedRowSet crs = null;
            int count=0;
            username= "Saurabh";
            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.setUrl("jdbc:derby://localhost:1527/patient");
            crs.setUsername("a");
            crs.setPassword("a");
            java.util.Date date= new java.util.Date();
            Timestamp moment = new Timestamp(date.getTime());
            
            for (index = 0; index < motion_sensors_readings.length; index = index + 1)
            {
                this.motion_sensors_readings [index] = motion_sensors_readings [index];
               
                
            }
         
          
           
          
            for (index = 0; index < body_temperatures.length; index = index + 1)
            {
                this.body_temperatures [index] = body_temperatures [index];         
            }
           
           
             
           
            for (index = 0; index < lungs_and_diaphragm_pressure_forces.length; index = index + 1)
            {
                this.lungs_and_diaphragm_pressure_forces [index] = lungs_and_diaphragm_pressure_forces [index]; 
             
            }   
           
            
          
            for (index = 0; index < sound_levels.length; index = index + 1)
            {
               this.sound_levels [index] = sound_levels [index];
                    
            }
           
            
           
            for (index = 0; index < sweat_content_percentages.length; index = index + 1)
            {
                this.sweat_content_percentages [index] = sweat_content_percentages [index];
               
            }
         
            
           
            for (index = 0; index < heart_rates.length; index = index + 1)
            {
                this.heart_rates [index] = heart_rates [index];
               
            }
            count =0;
          for(int i=0;i<8;i++)
        {
            if(i<4)
            {
                
                if(this.sound_levels[i]==0)
                {
                    ++count;
                }
            }
            if(i<6)
            {
                if( this.lungs_and_diaphragm_pressure_forces[i] ==0.0)
                 {
               ++count;
                 }
                
                if(this.heart_rates[i]==0)
                {
                    ++count;
                }
                
            }
            if( this.body_temperatures[i]==0)
            {
                ++count;
                
            }
             if( this.sweat_content_percentages[i]==0)
            {
                ++count;
                
            }
                    
        }
          System.out.println("THE COUNT IS: "+count);
          
          if(count>=30)
          {
              System.out.println("useless data");
              if(start)
              {
            crs.setCommand("insert into  motions values(?,?,?,?,?,?,?,?,?,?)");
            crs.setString(1,username);
            crs.setTimestamp(2,moment);
            for(int i=0;i<8;i++)
            {
                crs.setBoolean(index+3, prev_motion_sensors_readings[i]);
            }
            crs.execute();
            
            crs.setCommand("insert into  temperatures values(?,?,?,?,?,?,?,?,?,?)");
            crs.setString(1,username);
            crs.setTimestamp(2,moment);
            for(int i=0;i<8;i++)
            {
                crs.setDouble(index+3, this.prev_body_temperatures[i]);
            }
            crs.execute();
            
            crs.setCommand("insert into  pressures values(?,?,?,?,?,?,?,?)");
            crs.setString(1,username);
            crs.setTimestamp(2,moment);
            for(int i=0;i<6;i++)
            {
                crs.setDouble(index+3, this.prev_lungs_and_diaphragm_pressure_forces[i]);
            }
            crs.execute();
            
            crs.setCommand("insert into  microphones values(?,?,?,?,?,?)");
            crs.setString(1,username);
            crs.setTimestamp(2,moment);
            for(int i=0;i<4;i++)
            {
                crs.setDouble(index+3, this.prev_sound_levels[i]);
            }
            crs.execute();
          
            crs.setCommand("insert into  sweats values(?,?,?,?,?,?,?,?,?,?)");
            crs.setString(1,username);
            crs.setTimestamp(2,moment);
            for(int i=0;i<8;i++)
            {
                crs.setDouble(index+3, this.prev_sweat_content_percentages[i]);
            }
            
            crs.setCommand("insert into  heartrates values(?,?,?,?,?,?,?,?,?,?)");
            crs.setString(1,username);
            crs.setTimestamp(2,moment);
            for(int i=0;i<8;i++)
            {
                crs.setDouble(index+3, this.prev_heart_rates[i]);
            }
            crs.execute();
                  
              }
          
             
          }
           else
              {
               //  System.out.println("can insert into database");
                  start=false;
                  crs.setCommand("insert into  motions values(?,?,?,?,?,?,?,?,?,?)");
            crs.setString(1,username);
            crs.setTimestamp(2,moment);
            for(int i=0;i<8;i++)
            {
               // System.out.println("CURRENT MOTION SENSOR READING"+this.motion_sensors_readings[i]);
                crs.setBoolean(i+3, this.motion_sensors_readings[i]);
               prev_motion_sensors_readings[i]= this.motion_sensors_readings[i];
            }
            crs.execute();
            
            crs.setCommand("insert into  temperatures values(?,?,?,?,?,?,?,?,?,?)");
            crs.setString(1,username);
            crs.setTimestamp(2,moment);
            for(int i=0;i<8;i++)
            {
                crs.setDouble(i+3, this.body_temperatures[i]);
                prev_body_temperatures[i]=this.body_temperatures[i];
            }
            crs.execute();
            
            crs.setCommand("insert into  pressures values(?,?,?,?,?,?,?,?)");
            crs.setString(1,username);
            crs.setTimestamp(2,moment);
            for(int i=0;i<6;i++)
            {
                crs.setDouble(i+3, this.lungs_and_diaphragm_pressure_forces[i]);
                prev_lungs_and_diaphragm_pressure_forces[i]=this.lungs_and_diaphragm_pressure_forces[i];
            }
            crs.execute();
            
            crs.setCommand("insert into  microphones values(?,?,?,?,?,?)");
            crs.setString(1,username);
            crs.setTimestamp(2,moment);
            for(int i=0;i<4;i++)
            {
                crs.setDouble(i+3, this.sound_levels[i]);
                prev_sound_levels[i]=this.sound_levels[i];
            }
            crs.execute();
            
            
            crs.setCommand("insert into  heartrates values(?,?,?,?,?,?,?,?)");
            crs.setString(1,username);
            crs.setTimestamp(2,moment);
            for(int i=0;i<6;i++)
            {
                crs.setDouble(i+3, this.heart_rates[i]);
                prev_heart_rates[i]=this.heart_rates[i];    
               
            }
            crs.execute();
            
            crs.setCommand("insert into  sweats values(?,?,?,?,?,?,?,?,?,?)");
            crs.setString(1,username);
            crs.setTimestamp(2,moment);
            for(int i=0;i<8;i++)
            {
                //System.out.println("Sweat percentages"+this.sweat_content_percentages[i]);
                crs.setDouble(i+3, this.sweat_content_percentages[i]);
                prev_sweat_content_percentages[i]=this.sweat_content_percentages[i];
            }
            crs.execute();
            
              } 
          
          
        } catch (SQLException ex) {
            Logger.getLogger(SensoryData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean [] Get_Motion_Sensors_Readings ()
    {
	return motion_sensors_readings;
    }
    
    public double [] Get_Body_Temperatures ()
    {
	return body_temperatures;
    }
    
    public double [] Get_Lungs_And_Diaphragm_Pressure_Forces ()
    {
	return lungs_and_diaphragm_pressure_forces;
    }
    
    public double [] Get_Sound_Levels ()
    {
	return sound_levels;
    }
    
    public double [] Get_Sweat_Content_Percentages ()
    {
	return sweat_content_percentages;
    }
    
    public int [] Get_Heart_Rates ()
    {
	return heart_rates;
    }
    
    public void Output ()
    {
	int index;
	for (index = 0; index < motion_sensors_readings.length; index = index + 1)
	{
	    System.out.print (motion_sensors_readings [index]);
	    System.out.print (" ");
	}
	for (index = 0; index < body_temperatures.length; index = index + 1)
	{
	    System.out.print (body_temperatures [index]);
	    System.out.print (" ");
	}
	for (index = 0; index < lungs_and_diaphragm_pressure_forces.length; index = index + 1)
	{
	    System.out.print (lungs_and_diaphragm_pressure_forces [index]);
	    System.out.print (" ");
	}
	for (index = 0; index < sound_levels.length; index = index + 1)
	{
	    System.out.print (sound_levels [index]);
	    System.out.print (" ");
	}
	for (index = 0; index < sweat_content_percentages.length; index = index + 1)
	{
	    System.out.print (sweat_content_percentages [index]);
	    System.out.print (" ");
	}
	for (index = 0; index < heart_rates.length; index = index + 1)
	{
	    System.out.print (heart_rates [index]);
	    System.out.print (" ");
	}
	System.out.println ();
    }
}