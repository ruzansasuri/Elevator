import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Floor.java
 *
 *  This class is considered as the queue for each floor in which people wait for the elevator.
 *
 * @author: Ghodratollah Aalipour ga5481
 * @author: Ruzan Sasuri rps7183
 * @author: Akash Venkatachalam av2833
 * Version: $Id Floor.java, 10/31/2016$
 *
 */
public class Floor
{
    Vector<Person> floorQueue;// The people waiting for the elevator in a queue.
    static int qlength;// The maximum size of the queue on all the floors.
    private int floorNumber;// The floor number.
    private FloorProducer fprod;//The floor producer thread that creates random people.
    private FloorConsumer fcons;//The floor consumer object that sends people from the queue when te elevator is on this floor using FIFO.
    private int queue_count;//The number of people waiting for the elevator in each floor.

    /**
     * The parameterized constructor.
     * @param n The floor number.
     */
    public Floor(int n)
    {
        floorNumber = n;
        floorQueue = new Vector();
        queue_count = 0;
        fprod = new FloorProducer(this);
        fcons = new FloorConsumer(this);
    }

    /**
     * Starts the Floor Producer thread.
     */
    public void beginFloor()
    {
        fprod.start();
    }

    /**
     * Getter method for fcons.
     * @return fcons. The floor consumer.
     */
    public FloorConsumer getConsumer()
    {
        return fcons;
    }

    /**
     * Returns the Next person waiting in the queue if the queue has people waiting in it.
     * @return Person object, or null if no one is waiting.
     */
    public Person getNext()
    {
        if(queue_count == 0)
        {
            return null;
        }
        Person p = floorQueue.firstElement();
        return p;
    }

    /**
     * Getter for the floorNumber.
     * @return floorNumber.
     */
    public int getFloorNumber()
    {
        return floorNumber;
    }

    /**
     * Removes the first person in the queue, decreases the queue ount an returns the person thhat has been removed.
     * @return The person object.
     */
    public Person enterTheElevator()
    {
        Person p = floorQueue.firstElement();
        floorQueue.removeElementAt(0);
        queue_count--;
        return p;
    }

    /**
     * Checks if the queue is full or not.
     * @return true if queue is full, false otherwise.
     */
    public boolean queueFullness()
    {
        boolean full;
        if(queue_count == qlength)
        {
            full = true;
        }
        else
        {
            full = false;
        }
        return full;
    }

    /**
     * Adds a random person to the queue at a random interval of time.
     */
    public void addPerson()
    {
        int pweight = ThreadLocalRandom.current().nextInt(Elevator.MIN_WEIGHT, Elevator.MAX_WEIGHT + 1);
        int pfloor;
        do
        {
            pfloor = ThreadLocalRandom.current().nextInt(1, Elevator.lastFloor + 1);
        }while(pfloor == floorNumber);
        Person p = new Person(pweight,pfloor);
        floorQueue.addElement(p);
        queue_count++;
        System.err.println(p + " added to floor " + floorNumber);
    }
}
