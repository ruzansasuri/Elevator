import javafx.beans.property.IntegerProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.Thread.sleep;
/**
 * RAGElevators.java
 *
 *  This class contains the main and it defines the number of floors and the maximum queue length for all the floors.
 *
 * @author: Ghodratollah Aalipour ga5481
 * @author: Ruzan Sasuri rps7183
 * @author: Akash Venkatachalam av2833
 * Version: $Id RAGElevators.java, 10/24/2016$
 *
 */
public class RAGElevators
{
    /**
     * The main method. In this method, we define the number of floors and the length of queue in a floor and also
     * initializes the elevator by calling its constructor. Also initializes the floors and their producer.
     * @param ag Not used.
     * @throws IOException
     */
    public static void main(String ag[])throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter the number of floors in your building: ");
        int n = Integer.parseInt(br.readLine());
        if(n < 2)
        {
            System.out.println("Your building should at least have 2 floors.");
            System.exit(1);
        }
        System.out.print("Please enter the queue size for each floor: ");
        int k = Integer.parseInt(br.readLine());
        if(k < 1)
        {
            System.out.println("A floor should at least allow one person to wait.");
            System.exit(1);
        }
        Floor.qlength = k;
        System.out.println("Creating a building with " + n + " floors.");
        System.out.println("Each floor will have a maximum of " + k + " people waiting at a time.");
        Elevator.floors = new Floor[n];
        Floor f = new Floor(1);
        Elevator e = new Elevator(n,f);
        Elevator.floors[0] = f;
        Elevator.floors[0].beginFloor();
        try
        {
            sleep(1000);
        }
        catch(InterruptedException ie)
        {
            ie.printStackTrace();
        }
        for(int i = 1; i < Elevator.floors.length; i++)
        {
            Elevator.floors[i] = new Floor(i + 1);
            Elevator.floors[i].beginFloor();
        }
        e.run();
        //e.start();
    }
}
