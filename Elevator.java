import java.util.Iterator;
import java.util.Vector;

import static java.lang.Thread.sleep;
/**
 * Elevator.java
 *
 *  This class defines the character of an elevator like its capacity, the number of floors it can propagate and also the floor at which it is currently being operated.
 *
 * @author: Ghodratollah Aalipour ga5481
 * @author: Ruzan Sasuri rps7183
 * @author: Akash Venkatachalam av2833
 * Version: $Id Elevator.java, 10/31/2016$
 *
 */
/**
 * Created by Ruzan on 10/29/2016.
 */
public class Elevator
{
    static final int WEIGHT_CAPACITY = 300; // The maximum weight limit the elevator can hold at any instant.
    static final int MIN_WEIGHT = 40; // Minimum weight of a person.
    static final int MAX_WEIGHT = 100; // Maximum weight of a person.
    static Floor floors[]; // The floor array.
    static int lastFloor; // The topmost floor.
    private Vector<Person> elevator_people; // Vector that contains the people currently present inside the elevator.
    int current_floor; // The current floor at which the elevator is in at present.
    boolean going_up; // When the elevator starts from floor 1, it is going up, hence it is set as true.
    int current_weight; // The current weight of all the people inside the elevator.
    ElevatorConsumer econs; // The Elevator consumer thread.
    ElevatorProducer eprod; // The Elevator producer thread.

    /**
     * Parameterized constructor that creates the elevator and producer and onsumer threads.
     * @param lf Last floor in the building.
     * @param f The first floor object.
     */
    public Elevator(int lf, Floor f)
    {
        lastFloor = lf;
        current_floor = 1;
        elevator_people = new Vector();
        going_up = true;
        current_weight = 0;
        econs = new ElevatorConsumer(this);
        eprod = new ElevatorProducer(this, f);
    }

    /**
     * Checks if the elevator is empty.
     * @return true if empty else false.
     */
    public boolean isEmpty()
    {
        boolean emptyness = false;
        if(current_weight == 0)
        {
            System.out.println("Elevator is empty");
            emptyness = true;
        }
        return emptyness;
    }

    /**
     * Starts the elevator producer and consumer threads.
     */
    public void run()
    {
        System.out.println("The Elevator has started");
        econs.start();
        try
        {
            sleep(100);
        }
        catch(InterruptedException ie)
        {
            ie.printStackTrace();
        }
        eprod.start();
    }

    /**
     * Changes the floor based on the direction the elevator is going. If the last floor or the first floor is reached
     * the elevator changes its  direction.
     */
    public void nextFloor()
    {
        System.out.println();
        try
        {
            sleep(5000);
        }
        catch(InterruptedException ie)
        {
            ie.printStackTrace();
        }
        if(going_up)
        {
            current_floor++;
            System.out.println("The elevator has moved up to floor " + current_floor);
            System.out.println("the elevator has a weight of " + current_weight);
        }
        else
        {
            current_floor--;
            System.out.println("The elevator has moved down to floor " + current_floor);
            System.out.println("the elevator has a weight of " + current_weight);
        }/*
        if(current_floor == 1)
        {
            System.exit(0);
        }*/
        if(current_floor == lastFloor || current_floor == 1)
        {
            going_up = !going_up;
        }
        eprod.changeFloor(floors[current_floor - 1]);
        System.out.println("----------------------");
    }

    /**
     * Adds a person to the elevator(vector).
     * @param p The person to be added.
     */
    public void enter(Person p)
    {
        elevator_people.addElement(p);
        current_weight += p.getWeight();
    }

    /**
     * Checks if the capacity is full when adding a person.
     * @param p Person to be checked.
     * @return true if the persnon can be safely added, else false.
     */
    public boolean check_capacity(Person p)
    {
        int weight = current_weight + p.getWeight();
        if(weight <= WEIGHT_CAPACITY)
        {
            return true;
        }
        return false;
    }

    /**
     * Removes all people from the elevator(vector) who want to get off on that floor.
     */
    public void exit()
    {
        Iterator<Person> ip = elevator_people.iterator();
        while(ip.hasNext())
        {
            Person p = ip.next();
            if(p.getFloorOff() == current_floor)
            {
                ip.remove();
                current_weight -= p.getWeight();
                System.out.println(p + " has left the elevator");
            }
        }
    }
}
