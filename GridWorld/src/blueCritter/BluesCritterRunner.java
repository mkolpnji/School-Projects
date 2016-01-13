
package blueCritter;

import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Location;

import java.awt.Color;


public class BluesCritterRunner
{
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        world.add(new Location (5, 5), new BluesCritter());
        for (int i = 0; i < 10; i++){
        	Rock rock = new Rock();
        	rock.setColor(Color.BLUE);
        	world.add(new Location((int)(Math.random() * world.getGrid().getNumRows()), (int)(Math.random() * world.getGrid().getNumCols())), rock);
        	world.add(new Location((int)(Math.random() * world.getGrid().getNumRows()), (int)(Math.random() * world.getGrid().getNumCols())), new Flower());
        }
        world.show();
    }
}