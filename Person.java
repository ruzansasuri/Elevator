/**
 * Person.java
 *
 *  This class defines the characteristics  like id(name), weight and their desired floor for every single person.
 *
 * @author: Ghodratollah Aalipour ga5481
 * @author: Ruzan Sasuri rps7183
 * @author: Akash Venkatachalam av2833
 * Version: $Id Person.java, 10/31/2016$
 *
 */
public class Person
{
    private static int id_total = 0; // To create a unique id for each person.
    private int id; //  The person's unique id.
    private int weight; // The person's weight.
    private int floorOff; // The floor on which the person will get off.

    /**
     * The parametrized constructor.
     * @param wt The weight of the person(random).
     * @param floor The floor on which the person will get off,
     */
    public Person(int wt, int floor)
    {
        id = ++id_total;
        weight = wt;
        floorOff = floor;
    }

    /**
     * Getter function for floorOff.
     * @return floorOff
     */
    public int getFloorOff()
    {
        return floorOff;
    }

    /**
     * Getter function for weight.
     * @return weight
     */
    public int getWeight()
    {
        return weight;
    }

    /**
     * Converts the person to a string as inherited from object.
     * @return  the id, weight and floorOff of a person.
     */
    public String toString()
    {
        return "ID=" + id + ", W=" + weight + ", F=" + floorOff;
    }
}
