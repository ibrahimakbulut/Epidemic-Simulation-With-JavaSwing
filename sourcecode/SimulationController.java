public class SimulationController implements ControllerInterface{

    SimulationModelInterface model;
    SimulationView view;

    int entered_rate_of_spread=0;
    int entered_rate_of_mortality=0;

    public SimulationController(SimulationModelInterface model){

        this.model=model;
        view= new SimulationView(this,model);

        view.createView();
    }

    public void continue_simulation(){

        view.disableContinueMenuItem();
        view.enablePauseMenuItem();

        model.on();
    }

    public void pause_simulation(){
        view.disablePauseMenuItem();
        view.enableContinueMenuItem();

        model.off();
    }

    public void enter_mortality_rate(double rate){

        if(entered_rate_of_mortality==0) {
            model.setMortality(rate);
            entered_rate_of_mortality=1;
        }
    }

    public void enter_spreading_factor(double factor){

        if(entered_rate_of_spread==0) {
            model.setSpreading_Factor(factor);
            entered_rate_of_spread=1;
        }
    }

    public void add_individual(double indicate_mask,int speed,int social_distance,int sociability){

    model.add_individual(indicate_mask,speed,social_distance,sociability);

    }

    public void add_individuals_with_bulk(int new_individuls_number){

        model.add_individuals_with_bulk(new_individuls_number);


    }




}
