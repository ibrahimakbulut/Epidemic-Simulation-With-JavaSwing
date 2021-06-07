import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class PopulationCanvas extends JPanel
{

    private ArrayList<Individual> population;

    private int d = 25;

    @Override
    public void paint(Graphics g) {

        super.paint(g);
        Graphics2D graphic2d = (Graphics2D) g;
        graphic2d.setColor(Color.BLUE);


        if(population!=null){

            for(int i=0;i<population.size();++i){

                if(population.get(i).getInfected()){
                    graphic2d.setColor(Color.RED);
                    graphic2d.fillRect(population.get(i).getX(),population.get(i).getY(),5,5);

                }
                else {
                    graphic2d.setColor(Color.BLUE);
                    graphic2d.fillRect(population.get(i).getX(), population.get(i).getY(), 5, 5);
                }
            }

        }
    }

    public void setD(int newD)
    {
        d = (newD >= 0 ? newD : 10); //if number is less than zero it will use 10 for diameter(compressed if statement)
        repaint();

    }

    public void setPopulation(ArrayList<Individual> population) {
        this.population = population;
    }

    public Dimension getPrefferedSize()
    {

        return new Dimension(200, 200);
    }

    public Dimension getMinimumSize()
    {
        return getPrefferedSize();
    }

}