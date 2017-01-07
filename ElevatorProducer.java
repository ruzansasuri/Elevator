/**
 * ElevatorProducer.java
 *
 *  This class takes in the people standing in the queue / takes input from the floor consumer class and put people inside the elevator list.
 *
 * @author: Ghodratollah Aalipour ga5481
 * @author: Ruzan Sasuri rps7183
 * @author: Akash Venkatachalam av2833
 * Version: $Id ElevatorProducer.java, 10/31/2016$
 *
 */
public class ElevatorProducer extends Thread
{
    Elevator e; // The elevator object.
    FloorConsumer fcons; // The floor consumer.

    /**
     * Parameterized constructor. Takes in current floor and the elevator.
     * @param e The elevator object.
     * @param f The first floor.
     */
    public ElevatorProducer(Elevator e, Floor f)
    {
        super("Elevator Producer");
        this.e = e;
        fcons = f.getConsumer();
    }

    /**
     * Changes the floor consumer to the current floor.
     * @param f
     */
    public void changeFloor(Floor f)
    {
        fcons = f.getConsumer();
    }

    /**
     * Method to run the thread. It will keep adding people to the elevator till either the elevator is full or te floor queue is empty.
     */
    public void run()
    {
        while(true)
        {
            synchronized (e)
            {
                Person p = fcons.getNextInLine();
                if (p == null)//Floor queue is empty.
                {
                    e.nextFloor();
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
                else if (!e.check_capacity(p))//If elevator is full.
                {
                    e.nextFloor();
                    try
                    {
                        e.notify();
                        e.wait();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                else//Space in elevator and people waiting
                {
                    fcons.notifyProducer();
                    while (p != null && e.check_capacity(p))
                    {
                        e.enter(fcons.enterTheElevator());
                        p = fcons.getNextInLine();
                    }
                    try
                    {
                        e.notify();
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
