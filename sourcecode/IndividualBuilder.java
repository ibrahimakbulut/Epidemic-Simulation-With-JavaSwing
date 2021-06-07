public class IndividualBuilder {

    private Individual individual;

    public IndividualBuilder(){

        individual= new Individual();
    }

    public void buildXCoordinate(int x){
        individual.setX(x);
    }

    public void buildYCoordinate(int y){

        individual.setY(y);
    }

    public void buildMaskIndicator(double indicator){
        individual.setWearMaskOrNot(indicator);
    }
    public void buildSociability(int sociability){
        individual.setSociability(sociability);
    }
    public void buildSocialDistance(int social_distance){
        individual.setSocial_distance(social_distance);
    }
    public void buildSpeed(int speed){
        individual.setSpeed(speed);
    }

    public Individual getIndividual(){

        return individual;
    }

}
