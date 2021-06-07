public interface ControllerInterface {

    public void continue_simulation();

    public void pause_simulation();

    public void add_individual(double indicate_mask,int speed,int social_distance,int sociability);

    public void add_individuals_with_bulk(int new_individuls_number);

    public void enter_mortality_rate(double rate);

    public void enter_spreading_factor(double factor);



}
