public class Demo {

    public static void main(String[] args) {


        SimulationModelInterface a= new SimulationModel();

        ControllerInterface b= new SimulationController(a);

    }
}
