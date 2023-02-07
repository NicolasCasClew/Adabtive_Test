import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    private static final int maxAccel = 4;  // m/s
    private static final int maxBreak = 10; // m/s
    private static double myCarSpeed = 0;
    private static double sensorData =0; //sensorData = carDistance????
    private static double carDistance=2;

    public static void checkCrash() {
        // check if the distance between the two cars is less than or equal to 0
        if(carDistance<=0){
            System.out.println("Catastrofe! CRASH");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        //double myCarSpeed = 0;
        //double sensorData =2;
        //double carDistance=2;
        ArrayList<Double> speeds = new ArrayList<>();
        speeds= getSpeeds(speeds);
        int[] data = new int[]{10,20,50,90,100};
       /** for(int e:data){  //Just Test ing stuff
            System.out.println("Data "+e+" de km/s a m/s ="+kmph_to_mps(e));
            System.out.println("Data "+e+" de m/s a km/h= "+mps_to_kmph(e));
            System.out.println("Distancia de seguridad a "+e+" kmp = "+sec_distance(e));
        }*/

        for(double e:speeds){
            checkCrash();

            carDistance=updateDistance(kmph_to_mps(e),myCarSpeed,carDistance);
            System.out.println("Distancia = "+ carDistance);            //Test
            System.out.println("Su velocidad es = "+ kmph_to_mps(e));   //Test
            System.out.println("Mi velocidad es = "+myCarSpeed);        //Test
            if(carDistance >= sec_distance(myCarSpeed)){
                accelerateCar();
                System.out.println("Acelera hombre que se te escapa"); //Test
                //double desiredDistance = sec_distance(myCarSpeed);
            }else{
                brakeCar();
                System.out.println("Quieto parao que te pasas bruto");  //Test
            }
        }

    }

    private static double updateDistance(double car1Speed, double car2Speed, double initialDistance) {
        return initialDistance + (car1Speed - car2Speed);
    }

    private static void brakeCar() {
        Math.max(myCarSpeed-=(maxBreak*(carDistance*0.5)),0);
    }

    private static void accelerateCar() {
        System.out.println("Acceleracion = "+maxAccel*(carDistance/sec_distance(myCarSpeed))+"\n"+
                "Carrodistancia"+carDistance+"\n"+
                "distancia sefuridad ="+sec_distance(myCarSpeed));
        myCarSpeed+=(maxAccel*(carDistance/sec_distance(myCarSpeed)));
    }


    public static double kmph_to_mps(double kmph){
        return round((0.277778 * kmph));
    }
    public static double mps_to_kmph(double mps) {
        return round((3.6 * mps));
    }
    public static int sec_distance(double speed){
        return (int)((speed/10)*(speed/10)); //legal security distance formula (in spain)
    }
    public static ArrayList<Double> getSpeeds(ArrayList<Double> speeds){
        try {
            Scanner sc = new Scanner(new File("src/speeds.txt"));
            while (sc.hasNextLine()) {
                speeds.add(sc.nextDouble()); //add speeds directly ass m/s??
                //System.out.println("Leido");
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return speeds;
    }
    public static double round(double number){
        return (Math.round(number*100)/100);
    }
}
