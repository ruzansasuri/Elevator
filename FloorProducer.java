import java.util.concurrent.ThreadLocalRandom;

/**
 * FloorProducer.java
 *
 *  This class technically creates a person and allows him to stand in queue for all the floors.
 *
 * @author: Ghodratollah Aalipour ga5481
 * @author: Ruzan Sasuri rps7183
 * @author: Akash Venkatachalam av2833
 * Version: $Id FloorProducer.java, 10/31/2016$
 *
 */
public class FloorProducer extends Thread
{
    private static final int MAX_WAIT_TIME = 50000;// The maximum wait time before which a person is added to the floor.
    private static final int MIN_WAIT_TIME = 1000;// The minimum wait time before which a person is added to the floor.
    private Floor f; // The floor object for the current floor producer.

    /**
     * Parameterized constructor that adds the instance of te floor to which the producer is linked.
     * @param f The instance f Floor.
     */
    public FloorProducer(Floor f)
    {
        super("Floor " + f.getFloorNumber());
        this.f = f;
    }

    /**
     * The run method of the thread. It will add people into the queue as long as the queue is not full. Once the queue
     * becomes full it will wait.
     */
    public void run()
    {
        while(true)
        {
            int sleeptime = ThreadLocalRandom.current().nextInt(MIN_WAIT_TIME,MAX_WAIT_TIME);
            synchronized (f)
            {
                if (f.queueFullness())
                {
                    try
                    {
                        f.wait();
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    f.addPerson();
                    try
                    {
                        sleep(sleeptime);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
