public class Individual {

    int x,y;

    double wearMaskOrNot;
    int speed;
    int social_distance;
    int sociability;

    boolean infected;

    int direction; //4: east, 8: north , 6: west , 2: south

    public Individual(){

        x= (int) (Math.random() * 997);

        y= (int) (Math.random() * 597);

        speed= (int) (Math.random() * 500) + 1;

        social_distance= (int) (Math.random() * 10);

        sociability= (int) (Math.random() * 5) + 1;

        int temp;

        temp= (int) (Math.random() * 500) + 1;

        if(temp%2==0){
            wearMaskOrNot=0.2;
        }
        else{
            wearMaskOrNot=1.0;
        }

        infected=false;

        int temp2= (int) (Math.random() * 4) + 1;

        if(temp2==1){
            direction=4;
        }
        else if(temp2==2){
            direction=8;
        }
        else if(temp2==3){
            direction=6;
        }
        else if(temp2==4){
            direction=2;
        }

    }

    public void setX(int x){
        this.x=x;
    }

    public void setY(int y){
        this.y=y;
    }
    public void setWearMaskOrNot(double indicating_mask){
        this.wearMaskOrNot=indicating_mask;
    }

    public void setSociability(int sociability) {
        this.sociability = sociability;
    }

    public void setSocial_distance(int social_distance) {
        this.social_distance = social_distance;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public void setInfected(boolean infected){
        this.infected=infected;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getSociability() {
        return sociability;
    }

    public int getSocial_distance() {
        return social_distance;
    }

    public double getWearMaskOrNot() {
        return wearMaskOrNot;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean getInfected(){
        return this.infected;
    }

    public int getDirection(){
        return this.direction;
    }

    public void randomDirection(){
        int temp2= (int) (Math.random() * 4) + 1;

        if(temp2==1){
            direction=4;
        }
        else if(temp2==2){
            direction=8;
        }
        else if(temp2==3){
            direction=6;
        }
        else if(temp2==4){
            direction=2;
        }


    }

    public void movement(){

        if(direction==4){

            if(x-speed>=0){
                x-=speed;
            }
            else if(x==0){

                randomDirection();
                movement();
            }

            else{

                x=0;
            }
        }

        else if(direction==8){

            if(y-speed>=0){
                y-=speed;
            }
            else if(y==0){

              randomDirection();
                movement();
            }

            else{

                y=0;
            }

        }

        else if(direction==6){
            if(x+speed<=996){
                x+=speed;
            }
            else if(x==996){

                randomDirection();
                movement();
            }

            else{

                x=996;
            }

        }

        else if(direction==2){
            if(y+speed<=596){
                y+=speed;
            }
            else if(y==596){

                int temp2= (int) (Math.random() * 4) + 1;

                randomDirection();
                movement();
            }

            else{

                y=596;
            }

        }

    }
}
