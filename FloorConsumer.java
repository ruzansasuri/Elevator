/**
 * FloorConsumer.java
 *
 *  This class allows the people to enter the elevator from the floor queue.
 *
 * @author: Ghodratollah Aalipour ga5481
 * @author: Ruzan Sasuri rps7183
 * @author: Akash Venkatachalam av2833
 * Version: $Id FloorCosumer.java, 10/31/2016$
 *
 */
public class FloorConsumer
{
    private Floor f;// The floor object for the current floor producer.

    /**
     * Parameterized constructor that adds the instance of the floor to which the producer is linked.
      * @param f
     */
    public FloorConsumer(Floor f)
    {
        this.f = f;
    }

    /**
     * Get the next person waiting in the queue.
     * @returnThe next person in the vector.
     */
    public Person getNextInLine()
    {
        Person p = f.getNext();
        return p;
    }

    /**
     * Notifies the floor producer that the queue is not full.
     */
    public void notifyProducer()
    {
        try
        {
            f.notify();
        }
        catch(IllegalMonitorStateException iem)
        {
            //Nothing to do here as if Floor producer has the monitor, then it doesn't need to be notified anyway.
        }
    }

    /**
     * Moves a person into the elevator.
     * @return p. The Person that is entering the elevator.
     */
    public Person enterTheElevator()
    {
        Person p = f.enterTheElevator();
        System.out.println(p + " has entered the elevator at floor " + f.getFloorNumber());
        return p;
    }
}
