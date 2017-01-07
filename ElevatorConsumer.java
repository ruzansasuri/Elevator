/**
 * ElevatorConsumer.java
 *
 *  This class removes people from the elevator when their respective floor is reached.
 *
 * @author: Ghodratollah Aalipour ga5481
 * @author: Ruzan Sasuri rps7183
 * @author: Akash Venkatachalam av2833
 * Version: $Id ElevatorConsumer.java, 10/31/2016$
 *
 */
import java.awt.geom.Ellipse2D;

/**
 * Created by Ruzan on 10/29/2016.
 */
public class ElevatorConsumer extends Thread
{
    Elevator e;// The elevator object.
    public ElevatorConsumer(Elevator e)
    {
        super("Elevator Consumer");
        this.e = e;
    }

    /**
     * It will start removing people from the elevator once it reaches a floor, except when the elevator is empty.
     */
    public void run()
    {
        while(true)
        {
            synchronized (e)
            {
                if(e.isEmpty())//Elevator is empty.
                {
                    try
                    {
                        e.notifyAll();
                        e.wait();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                else//Elevator not empty.
                {
                    e.exit();
                    e.notifyAll();
                    try
                    {
                        e.wait();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
