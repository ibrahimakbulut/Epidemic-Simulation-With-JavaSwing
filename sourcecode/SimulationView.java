import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationView implements  PopulationStatesObserver {

    SimulationModelInterface model;

    ControllerInterface controller;

    JFrame viewFrame;
    JPanel viewPanel;
    JPanel viewPanel2;
    JPanel viewPanel3;

    JLabel label1,label2,label3,label4,label5,label6,label7,label8,label9,label10,label11,label12,label13,label14,label15,label16,label17;


    JTextField textField1,textField2,textField3,textField4,textField5,textField6,textField7;


    JMenuBar menuBar;
    JMenu menu;
    JMenuItem pauseMenuItem;
    JMenuItem continueMenuItem;

    JButton add_individual;
    JButton add_individual_with_bulk;

    JButton enter_mortality_rate;
    JButton enter_spreading_factor;

    public SimulationView(ControllerInterface controller, SimulationModelInterface model){

        this.controller= controller;

        this.model=model;

        model.registerObserver(this);

    }

    public void createView(){

        viewFrame = new JFrame("Epidemic Simulation");
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewFrame.setSize(1400, 1000);

        menuBar= new JMenuBar();

        menu= new JMenu("Manage Simulation");

        menuBar.add(menu);

        pauseMenuItem= new JMenuItem("Pause");

        continueMenuItem= new JMenuItem("Continue");

        menu.add(pauseMenuItem);
        menu.add(continueMenuItem);

        viewPanel= new JPanel();

        label1= new JLabel("Enter individual wear mask 0.2 or 1.0         ");
        label2= new JLabel("Enter individual speed, range [1,500]               ");
        label3= new JLabel("Enter social distance of individual, range [0,9]");
        label4= new JLabel("Enter how individual social, range [1,5]        ");

        label6= new JLabel("              Count of Deads                    ");

        label7= new JLabel("                       0                        ");

        label8= new JLabel("              Count of İnfecteds                ");

        label9= new JLabel("                        0                       ");

        label10= new JLabel("              Count of Healthy                  ");

        label11= new JLabel("                       0                        ");

        label12= new JLabel("             Count of Hospitalized             ");

        label13= new JLabel("                       0                        ");

        label14= new JLabel("          Mortality Rate ( Z ) for once, range [0.1,0.9]    ");
        label15= new JLabel("        Spreading Factor ( R ) for once,range [0.5,1.0]     ");

        label16= new JLabel("                     Time in seconds                         ");
        label17 = new JLabel("                          0                                 ");



        textField1= new JTextField(20);
        textField2= new JTextField(20);
        textField3= new JTextField(20);
        textField4= new JTextField(20);

        JPanel line1= new JPanel();

        line1.add(label1);
        line1.add(textField1);

        JPanel line2= new JPanel();

        line2.add(label2);
        line2.add(textField2);

        JPanel line3= new JPanel();

        line3.add(label3);
        line3.add(textField3);

        JPanel line4= new JPanel();

        line4.add(label4);
        line4.add(textField4);

        enter_mortality_rate= new JButton("Enter");
        enter_spreading_factor= new JButton("Enter And Start Simulation");

        add_individual= new JButton("add individual");

        add_individual.addActionListener(new ActionListener() {
                                             @Override
                                             public void actionPerformed(ActionEvent evt) {
                                                 double indicate_mask= Double.parseDouble(textField1.getText());
                                                 int speed= Integer.parseInt(textField2.getText());
                                                 int social_distance= Integer.parseInt(textField3.getText());
                                                 int sociability= Integer.parseInt(textField4.getText());

                                                 controller.add_individual(indicate_mask,speed,social_distance,sociability);
                                             }
                                         });

        enter_spreading_factor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                double sprading_rate= Double.parseDouble(textField7.getText());
                controller.enter_spreading_factor(sprading_rate);
            }
        });

        enter_mortality_rate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                double mortality_rate= Double.parseDouble(textField6.getText());
                controller.enter_mortality_rate (mortality_rate);
            }
        });
       pauseMenuItem.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent evt) {

               controller.pause_simulation();
           }
       });

        continueMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                controller.continue_simulation();
            }
        });

        JPanel line5= new JPanel();
        line5.add(add_individual);

        viewPanel2 = new JPanel();

        label5= new JLabel("Enter how many individual will be created?");
        textField5= new JTextField(20);
        add_individual_with_bulk= new JButton("add individual with bulk");

        add_individual_with_bulk.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){

                int new_individual_numbers= Integer.parseInt(textField5.getText());

                controller.add_individuals_with_bulk(new_individual_numbers);
            }

        });

       viewPanel.setLayout(new FlowLayout());

        FlowLayout temp= (FlowLayout)viewPanel.getLayout();

        temp.setVgap(0);


        JPanel line14= new JPanel();
        JPanel line15= new JPanel();

        line14.add(enter_mortality_rate);
        line15.add(enter_spreading_factor);

        textField6= new JTextField(20);
        textField7= new JTextField(20);

        JPanel line12= new JPanel();
        JPanel line13= new JPanel();

        line12.add(label14);
        line12.add(textField6);

        line13.add(label15);
        line13.add(textField7);


        final Box southBox = new Box(BoxLayout.PAGE_AXIS);

        southBox.add(line12);
        southBox.add(line14);
        southBox.add(line13);
        southBox.add(line15);
        southBox.add(line1);
        southBox.add(line2);
        southBox.add(line3);
        southBox.add(line4);
        southBox.add(line5);

        viewPanel.add(southBox);

        Box southBox4 = new Box(BoxLayout.PAGE_AXIS);

        JPanel line6= new JPanel();

        line6.add(label5);
        line6.add(textField5);

        southBox4.add(line6);

        southBox4.add(add_individual_with_bulk);

        viewPanel2.add(southBox4);


        viewPanel3= new PopulationCanvas();
        viewPanel3.setPreferredSize(new Dimension(1000,600));

        ((PopulationCanvas)viewPanel3).setPopulation(((SimulationModel)model).getPopulation());

        JPanel temp2= new JPanel(new FlowLayout());

        final Box southBox2 = new Box(BoxLayout.PAGE_AXIS);

        southBox2.add(viewPanel);
        southBox2.add(viewPanel2);

        Box southBox5 = new Box(BoxLayout.PAGE_AXIS);

        JPanel line7= new JPanel();
        JPanel line8= new JPanel();
        JPanel line9= new JPanel();
        JPanel line10= new JPanel();

        JPanel line16= new JPanel(); //*

        line7.add(label6);
        line7.add(label7);
        line8.add(label8);
        line8.add(label9);
        line9.add(label10);
        line9.add(label11);
        line10.add(label12);
        line10.add(label13);

        line16.add(label16);
        line16.add(label17);

        southBox5.add(line7);
        southBox5.add(line8);
        southBox5.add(line9);
        southBox5.add(line10);
        southBox5.add(line16);

        JPanel temp5= new JPanel();

        temp5.add(southBox5);

        southBox2.add(temp5);
        temp2.add(southBox2);

        final Box southBox3 = new Box(BoxLayout.LINE_AXIS);

        southBox3.add(viewPanel3);
        southBox3.add(temp2);

        JPanel temp3= new JPanel(new FlowLayout());

        temp3.add(southBox3);


      //  viewFrame.getContentPane().add(BorderLayout.CENTER ,viewPanel);
      //  viewFrame.getContentPane().add(BorderLayout.PAGE_END ,viewPanel2);
        viewFrame.getContentPane().add(BorderLayout.NORTH ,menuBar);

        viewFrame.getContentPane().add(BorderLayout.CENTER, temp3);
        viewFrame.setVisible(true);

    }

    public void update(){

        label7.setText(Integer.toString  (((SimulationModel)model).getTotal_number_of_deads()));

        label9.setText(Integer.toString  (((SimulationModel)model).getTotal_number_of_infecteds()));

        label11.setText(Integer.toString  (((SimulationModel)model).getTotal_number_of_healthy()));

        label13.setText(Integer.toString  (((SimulationModel)model).getTotal_number_of_hospitalized()));

        label17.setText(Integer.toString  (((SimulationModel)model).getSeconds()));

        ((PopulationCanvas)viewPanel3).setPopulation(((SimulationModel)model).getPopulation());

        viewPanel3.repaint();

    }

    public void enablePauseMenuItem(){
        pauseMenuItem.setEnabled(true);
    }

    public void disablePauseMenuItem(){
        pauseMenuItem.setEnabled(false);
    }

    public void enableContinueMenuItem(){
        continueMenuItem.setEnabled(true);
    }

    public void disableContinueMenuItem(){
        continueMenuItem.setEnabled(false);
    }

}
