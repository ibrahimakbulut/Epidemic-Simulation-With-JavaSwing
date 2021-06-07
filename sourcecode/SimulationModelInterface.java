import java.util.Observer;

public interface SimulationModelInterface {

    public void removeObserver(PopulationStatesObserver o);

    public void registerObserver(PopulationStatesObserver o);

    public void notifyObservers();

    public void setMortality(Double rate);

    public void setSpreading_Factor(Double factor);

    public void on();

    public void off();

    public void initialize();

    public void add_individual(double indicate_mask,int speed,int social_distance,int sociability);

    public void add_individuals_with_bulk(int new_individuls_number);
}

