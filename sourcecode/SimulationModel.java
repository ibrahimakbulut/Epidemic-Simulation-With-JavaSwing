import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SimulationModel  implements SimulationModelInterface  {

    private double Mortality_Rate=-1.0;
    private double Spreading_Factor=-1.0;


    private ArrayList<Individual> population;

    private ArrayList<Integer> population_collide_information;
    private ArrayList<Boolean> population_infected_information;
    private  ArrayList<ArrayList<Individual>> collides_of_individuals;
    private ArrayList<Integer> infected_seconds_of_individuals;
    private  ArrayList<Individual> individulas_in_hospital_queue;
    private ArrayList<Integer> Individual_Remain_Hospital_time;

    private int capacity_h;

    private ArrayList<Individual> last_colliding_individual;

    private Integer total_number_of_deads;
    private Integer total_number_of_hospitalized;
    private Integer total_number_of_infecteds;
    private Integer total_number_of_healthy;

    private boolean first_run=false;
    private boolean first_infected_choosen;
    private boolean call_from_inner=false;

    private ArrayList observers= new ArrayList();


    Timer timer ;
    Integer lock_object;

    int time_count=0;


    public SimulationModel(){
        population= new ArrayList<Individual>();

        population_collide_information= new ArrayList<Integer>();

        population_infected_information= new ArrayList<Boolean>();

        collides_of_individuals= new ArrayList<ArrayList<Individual>>();

        last_colliding_individual= new ArrayList<Individual>();

        infected_seconds_of_individuals= new ArrayList<Integer>();

        individulas_in_hospital_queue= new ArrayList<Individual>();
        Individual_Remain_Hospital_time= new ArrayList<Integer>();

        total_number_of_deads=0;

        total_number_of_healthy=0;
        total_number_of_hospitalized=0;
        total_number_of_infecteds=0;

        lock_object= new Integer(10);

        timer = new Timer();

        initialize();
    }

    public void removeObserver(PopulationStatesObserver o){

        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }

    }

    public void registerObserver(PopulationStatesObserver o){

        observers.add(o);
    }

    public void notifyObservers(){

        for (int i = 0; i < observers.size(); i++) {
            PopulationStatesObserver observer = (PopulationStatesObserver)observers.get(i);
            observer.update();
        }
    }

    class MediatorTask extends TimerTask {
        @Override
        public void run() {

            if(Spreading_Factor<=0 || Mortality_Rate<=0){
                return;
            }

            if(first_run==false){
                capacity_h= population.size();
                first_run=true;
            }

            time_count+=1;
             synchronized (lock_object) {

            ArrayList<Individual> temp = new ArrayList<Individual>();

            for (int i = 0; i < population.size(); ++i) {

                if(population.get(i).getInfected()){

                    // Z
                    if(infected_seconds_of_individuals.get(i)>= (100* (1- Mortality_Rate))){

                        population.remove(i);
                        infected_seconds_of_individuals.remove(i);
                        //index_of_infecteds.remove(index_of_infecteds.indexOf(i));
                        population_infected_information.remove(i);

                        population_collide_information.remove(i);
                        collides_of_individuals.remove(i);

                        last_colliding_individual.remove(i);
                        i -= 1;

                        total_number_of_deads+=1;
                        total_number_of_infecteds-=1;

                        continue;
                    }

                    else if(infected_seconds_of_individuals.get(i)==25){

                        int hospital_capacity;

                         synchronized (individulas_in_hospital_queue) {
                        hospital_capacity = individulas_in_hospital_queue.size();

                        if(hospital_capacity < ( (int)(capacity_h/100.0) ) ) {
                            individulas_in_hospital_queue.add(population.get(i));
                            Individual_Remain_Hospital_time.add(0);
                            population.remove(i);
                            infected_seconds_of_individuals.remove(i);
                            //index_of_infecteds.remove(index_of_infecteds.indexOf(i));

                            population_infected_information.remove(i);

                            population_collide_information.remove(i);
                            collides_of_individuals.remove(i);
                            last_colliding_individual.remove(i);

                            total_number_of_hospitalized+=1;
                            total_number_of_infecteds-=1;
                            i -= 1;
                            continue;
                        }
                        else{

                            infected_seconds_of_individuals.set(i,infected_seconds_of_individuals.get(i)+1);
                        }
                         }
                    }

                    else /*(infected_seconds_of_individuals.get(i)<25) */{

                        infected_seconds_of_individuals.set(i,infected_seconds_of_individuals.get(i)+1);
                    }

                }

                if (temp.contains(population.get(i))) {
                    continue;
                }

                if (population_collide_information.get(i) > 0) {
                    population_collide_information.set(i, population_collide_information.get(i) - 1);
                    continue;
                }

                int x = population.get(i).getX();

                int y = population.get(i).getY();

                boolean collide = false;

                int count=0;

                for (int j = 0; j < population.size(); ++j) {

                    if (j == i) {
                        continue;
                    }

                    if (population_collide_information.get(j) > 0) {
                        continue;
                    }


                    if(last_colliding_individual.get(i)==population.get(j)){
                        continue;
                    }

                    if(population.get(j).getInfected()){

                        if (infected_seconds_of_individuals.get(j) >= (100 * (1 - Mortality_Rate))) {
                            continue;
                        } else if (infected_seconds_of_individuals.get(j) == 25) {
                            int hospital_capacity;

                             synchronized (individulas_in_hospital_queue) {
                            hospital_capacity = individulas_in_hospital_queue.size();

                            if (hospital_capacity < ((int) (capacity_h /100.0))) {
                                individulas_in_hospital_queue.add(population.get(j));
                                Individual_Remain_Hospital_time.add(0);

                                population.remove(j);
                                infected_seconds_of_individuals.remove(j);

                                population_infected_information.remove(j);
                                population_collide_information.remove(j);
                                collides_of_individuals.remove(j);
                                last_colliding_individual.remove(j);

                                total_number_of_hospitalized+=1;
                                total_number_of_infecteds-=1;

                                count+=1;
                                j -= 1;
                                continue;
                            }
                             }

                        }
                    }

                    int other_x = population.get(j).getX();
                    int other_y = population.get(j).getY();

                    int social_distance;

                    int social_time;

                    if (population.get(i).getSocial_distance() < population.get(j).getSocial_distance()) {

                        social_distance = population.get(i).getSocial_distance();
                    } else {
                        social_distance = population.get(j).getSocial_distance();
                    }

                    if (population.get(i).getSociability() < population.get(j).getSociability()) {

                        social_time = population.get(j).getSociability();
                    } else {
                        social_time = population.get(i).getSociability();
                    }

                    boolean passed=false;
                    if (( (Math.abs(x - other_x) +5) == social_distance && y == other_y) || ((Math.abs(y - other_y)+5 ) == social_distance && x == other_x))
                        passed=true;
                    else if(population.get(i).getDirection()==6 && population.get(j).getDirection()==4 && (population.get(j).getX()<population.get(i).getX()) && ((population.get(j).getX()+population.get(j).getSpeed())>population.get(i).getX()) && (population.get(i).getY()==population.get(j).getY()) )
                        passed=true;
                    else if(population.get(i).getDirection()==4 && population.get(j).getDirection()==6 && (population.get(i).getX()<population.get(j).getX()) && ((population.get(j).getX()-population.get(j).getSpeed())<population.get(i).getX()) && (population.get(i).getY()==population.get(j).getY())  )
                        passed=true;

                    else if(population.get(i).getDirection()==8 && population.get(j).getDirection()==2 && (population.get(j).getY()>population.get(i).getY()) && ((population.get(j).getY()-population.get(j).getSpeed())<population.get(i).getY()) && (population.get(i).getX()==population.get(j).getX())    )
                        passed=true;

                    else if(population.get(i).getDirection()==2 && population.get(j).getDirection()==8 && (population.get(i).getY()>population.get(j).getY()) && ((population.get(j).getY()+population.get(j).getSpeed())>population.get(i).getY()) && (population.get(i).getX()==population.get(j).getX())  )
                        passed=true;

                    if (passed) {

                        collide = true;

                        population_collide_information.set(i, social_time);
                        population_collide_information.set(j, social_time);

                        last_colliding_individual.set(i,population.get(j));
                        last_colliding_individual.set(j,population.get(i));

                        collides_of_individuals.get(i).add(population.get(j));
                        collides_of_individuals.get(j).add(population.get(i));

                        population.get(i).randomDirection();
                        population.get(j).randomDirection();

                        temp.add(population.get(j));

                        if ( (population_infected_information.get(j) == true || population_infected_information.get(i)==true) && !( population_infected_information.get(j) == true && population_infected_information.get(i)==true) ) {

                            double probality= Spreading_Factor* (1 + social_time/10.0) *population.get(i).getWearMaskOrNot()*population.get(j).getWearMaskOrNot()*(1- social_distance/10.0);

                            if(probality>=1){ //calculate probability
                                probality=1.0;
                            }

                             //probality= 1.0;

                            Random random = new Random();
                            boolean infected_or_not = (random.nextInt(100) < (probality * 100)) ? true : false;

                            if(population_infected_information.get(i)==false) {
                                population_infected_information.set(i, infected_or_not);
                                if (infected_or_not == true) {

                                    population.get(i).setInfected(true);
                                    total_number_of_healthy -= 1;
                                    total_number_of_infecteds += 1;
                                }
                            }

                            else{
                                population_infected_information.set(j, infected_or_not);
                                if (infected_or_not == true) {

                                    population.get(j).setInfected(true);
                                    total_number_of_healthy -= 1;
                                    total_number_of_infecteds += 1;
                                }

                            }

                        }

                        break;
                    }
                }

                if (!collide)
                    population.get(i).movement();
            }

            notifyObservers();

                }
        }
    }

    class HospitalTask extends TimerTask {
        @Override
        public void run() {
            if(Spreading_Factor<0 || Mortality_Rate<0)
                return;
             synchronized (individulas_in_hospital_queue) {  // lock hospital queue to synchronize

            for (int i = 0; i < individulas_in_hospital_queue.size(); ++i) {

                if( Individual_Remain_Hospital_time.get(i)==10){

                    call_from_inner=true;
                    add_individual(individulas_in_hospital_queue.get(i).getWearMaskOrNot(),individulas_in_hospital_queue.get(i).getSpeed(),individulas_in_hospital_queue.get(i).getSocial_distance(),individulas_in_hospital_queue.get(i).getSociability());
                    call_from_inner=false;
                    individulas_in_hospital_queue.remove(i);
                    Individual_Remain_Hospital_time.remove(i);

                    total_number_of_hospitalized-=1;

                    i-=1;
                }
                else{

                    Individual_Remain_Hospital_time.set(i,Individual_Remain_Hospital_time.get(i)+1);
                }
            }
             }
        }
    }

    public void on(){

        synchronized (lock_object){
            timer = new Timer();
            timer.schedule(new MediatorTask(),1000,1000);
            timer.schedule(new HospitalTask(),1000,1000);
        }
    }

    public void off(){

        synchronized (lock_object){ //lock object, because we dont want to cancel before all jobs finished in thread
            timer.cancel();
        }
    }

    public void initialize(){

        first_infected_choosen=false;
        timer.schedule(new MediatorTask(),1000,1000);
        timer.schedule(new HospitalTask(),1000,1000);
    }

    public void add_individual(double indicate_mask,int speed,int social_distance,int sociability){

        if( (Spreading_Factor<=0 || Mortality_Rate<=0) ||call_from_inner==true) {

            synchronized (lock_object) {

                IndividualBuilder builder = new IndividualBuilder();

                builder.buildMaskIndicator(indicate_mask);
                builder.buildSpeed(speed);
                builder.buildSocialDistance(social_distance);
                builder.buildSociability(sociability);
                population.add(builder.getIndividual());


                population_collide_information.add(0);

                collides_of_individuals.add(new ArrayList<Individual>());

                last_colliding_individual.add(null);

                if (first_infected_choosen == false) {
                    population_infected_information.add(true);

                    // index_of_infecteds.add(0);
                    population.get(population.size() - 1).setInfected(true);
                    infected_seconds_of_individuals.add(0);

                    first_infected_choosen = true;
                    total_number_of_infecteds += 1;
                } else {
                    population_infected_information.add(false);
                    total_number_of_healthy += 1;

                    infected_seconds_of_individuals.add(0);
                }

            }

            notifyObservers();

        }

    }

    public void add_individuals_with_bulk(int new_individuls_number) {

        if( (Spreading_Factor<=0 || Mortality_Rate<=0) ||call_from_inner==true) {


            synchronized (lock_object) {

                Individual new_individual;
                for (int i = 0; i < new_individuls_number; ++i) {
                    new_individual = new Individual();
                    population.add(new_individual);

                    population_collide_information.add(0);

                    population_infected_information.add(false);

                    infected_seconds_of_individuals.add(0);

                    total_number_of_healthy += 1;

                    collides_of_individuals.add(new ArrayList<Individual>());

                    last_colliding_individual.add(null);

                }

                if (first_infected_choosen == false) {

                    int range = new_individuls_number;
                    int random_number = (int) (Math.random() * range) + 1;

                    population_infected_information.set(population_infected_information.size() - new_individuls_number + random_number - 1, true);
                    population.get(population.size() - new_individuls_number + random_number - 1).setInfected(true);
                    //index_of_infecteds.add(population_infected_information.size()-new_individuls_number+random_number-1);

                    total_number_of_healthy -= 1;
                    total_number_of_infecteds += 1;
                    first_infected_choosen = true;
                }

            }
            notifyObservers();

        }
    }

    public ArrayList<Individual> getPopulation(){
        return population;
    }

    public void setMortality(Double rate){
        this.Mortality_Rate=rate;
    }

    public void setSpreading_Factor(Double factor){
        this.Spreading_Factor=factor;
    }

    public int getTotal_number_of_deads(){

        return total_number_of_deads;
    }

    public int getTotal_number_of_healthy() {
        return total_number_of_healthy;
    }

    public int getTotal_number_of_hospitalized() {
        return total_number_of_hospitalized;
    }

    public int getTotal_number_of_infecteds() {
        return total_number_of_infecteds;
    }

    public int getSeconds(){
        return time_count;

    }
}
