
package carnivorebug;

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Bug;
import info.gridworld.grid.Location;

import java.awt.Color;

/**
 * This class runs a world that contains carnivore bugs. <br />
 */
public class CarnivoreBugRunner
{
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        CarnivoreBug alice = new CarnivoreBug(20);
        //alice.setColor(Color.BLUE);
        Bug bob = new Bug();
        world.add(new Location(7, 8), alice);
        world.add(new Location(5, 5), bob);
        world.add(new Location(5,1), new CarnivoreBug(37));
        world.show();
    }
}