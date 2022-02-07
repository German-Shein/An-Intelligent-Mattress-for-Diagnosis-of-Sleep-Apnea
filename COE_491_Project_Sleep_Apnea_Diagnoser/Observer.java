public interface Observer
{
    public void Update (boolean [] motion_sensors_readings, double [] body_temperatures, double [] lungs_and_diaphragm_pressure_forces, double [] sound_levels, double [] sweat_content_percentages, int [] heart_rates);
}