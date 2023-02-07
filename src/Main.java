import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLACK = "\u001B[30m";
    private static final int maxAccel = 4;  // m/s
    private static final int maxBreak = 10; // m/s
    private static double myCarSpeed = 0;
    private static double carDistance=2;

    public static void checkCrash() {
        // check if the distance between the two cars is less than or equal to 0
        if(carDistance<=0){
            System.out.println(ANSI_RED+"Catastrophe! CRASH!");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        double nowSpeed = 0;
        double beforeSpeed=0;
        ArrayList<Double> speeds = new ArrayList<>();

        speeds= getSpeeds(speeds);

        for(double e:speeds){
            nowSpeed = e;

            checkCrash();

            carDistance=updateDistance(e,myCarSpeed,carDistance);
            getData(e);
            if(carDistance >= sec_distance(mps_to_kmph(myCarSpeed))){
                accelerateCar(e, isBraking(nowSpeed,beforeSpeed));


            }else{
                brakeCar(e);

            }

            beforeSpeed = nowSpeed;
        }
        System.out.println(ANSI_GREEN+"Car arrived safely at destination :)");
    }

    private static double updateDistance(double car1Speed, double car2Speed, double initialDistance) {
        return initialDistance + (car1Speed - car2Speed);
    }

    private static void brakeCar(double otherCarSpeed) {
        if(myCarSpeed>otherCarSpeed){
            myCarSpeed-=Math.min(myCarSpeed-otherCarSpeed,maxBreak);
        }else{
        myCarSpeed-=Math.min(maxBreak-(sec_distance(mps_to_kmph(myCarSpeed))-carDistance),0);
    }}

    private static void accelerateCar(double otherCarSpeed, boolean isBraking) {
        if(otherCarSpeed >= myCarSpeed && (myCarSpeed-otherCarSpeed)<=4){
            myCarSpeed+= Math.min(otherCarSpeed-myCarSpeed,maxAccel);
        }else if (isBraking){
            brakeCar(otherCarSpeed);
            //myCarSpeed += maxAccel;
        }else{
            //mantain speed
        }
    }

    public static double kmph_to_mps(double kmph){
        return round((0.277778 * kmph));
    }
    public static double mps_to_kmph(double mps) {
        return round((3.6 * mps));
    }
    public static double sec_distance(double speed){
        return (((speed/10)*(speed/10))+1); //legal security distance formula (in spain)
    }
    public static ArrayList<Double> getSpeeds(ArrayList<Double> speeds){
        try {
            Scanner sc = new Scanner(new File("src/speeds2.txt"));
            while (sc.hasNextLine()) {
                speeds.add(kmph_to_mps(sc.nextDouble())); //add speeds directly ass m/s??

            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
        return speeds;
    }
    public static double round(double number){
        return (Math.round(number*100)/100);
    }
    public static void spacer(){
        System.out.println(ANSI_BLACK+"________________________________________________________________");
    }
    public static double distanceRatio(){


        return 8.9;
    }

    public static boolean isBraking(double now, double before){
        if(now-before>0){
            return true;
        }else{
            return false;
        }

    }
    public static void getData(double e){
        System.out.println(ANSI_CYAN+"Distance between cars = "+ carDistance);
        System.out.println(ANSI_CYAN+"Front car speed = "+ e);
        System.out.println(ANSI_CYAN+"My car's speed = "+myCarSpeed);
        spacer();
    }
}
