import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;
import java.util.Map;

public class Particle {
    public ParticleFlavor flavor;
    public int lifespan;

    public static final int PLANT_LIFESPAN = 150;
    public static final int FLOWER_LIFESPAN = 75;
    public static final int FIRE_LIFESPAN = 10;
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                   ParticleFlavor.PLANT, PLANT_LIFESPAN,
                   ParticleFlavor.FIRE, FIRE_LIFESPAN);

    public Particle(ParticleFlavor flavor) {
        this.flavor = flavor;
        if (this.flavor == ParticleFlavor.PLANT || this.flavor == ParticleFlavor.FLOWER || this.flavor == ParticleFlavor.FIRE)
            lifespan = LIFESPANS.get(this.flavor);
        else
        {
            lifespan = -1;
        }
    }

    public Color color() {
        if (flavor == ParticleFlavor.EMPTY) {
            return Color.BLACK;
        }
        if (flavor == ParticleFlavor.SAND)
        {
            return Color.YELLOW;
        }
        if (flavor == ParticleFlavor.BARRIER)
        {
            return Color.GRAY;
        }
        if (flavor == ParticleFlavor.WATER)
        {
            return Color.BLUE;
        }
        if (flavor == ParticleFlavor.FOUNTAIN)
        {
            return Color.CYAN;
        }
        if (flavor == ParticleFlavor.PLANT)
        {
            double ratio = (double) Math.max(0, Math.min(lifespan, PLANT_LIFESPAN)) / PLANT_LIFESPAN;
            int g = 120 + (int) Math.round((255 - 120) * ratio);
            return new Color(0, g, 0);
        }
        if (flavor == ParticleFlavor.FIRE)
        {
            double ratio = (double) Math.max(0, Math.min(lifespan, FIRE_LIFESPAN)) / FIRE_LIFESPAN;
            int r = (int) Math.round(255 * ratio);
            return new Color(r, 0, 0);
        }
        if (flavor == ParticleFlavor.FLOWER)
        {
            double ratio = (double) Math.max(0, Math.min(lifespan, FLOWER_LIFESPAN)) / FLOWER_LIFESPAN;
            int r = 120 + (int) Math.round((255 - 120) * ratio);
            int g = 70 + (int) Math.round((141 - 70) * ratio);
            int b = 80 + (int) Math.round((161 - 80) * ratio);
            return new Color(r, g, b);
        }
        if (flavor == ParticleFlavor.RANDO)
        {
            return new Color(99, 49, 207);
        }
        if (flavor == ParticleFlavor.RANDOM)
        {
            return new Color(188, 138, 16);
        }
        return Color.GRAY;
    }

    public void moveInto(Particle other)
    {
        other.flavor = flavor;
        other.lifespan = lifespan;
        flavor = ParticleFlavor.EMPTY;
        lifespan = -1;
    }

    public void fall(Map<Direction, Particle> neighbors)
    {
        Particle dNeighbor = neighbors.get(Direction.DOWN);
        if (dNeighbor.flavor == ParticleFlavor.EMPTY)
        {
            moveInto(dNeighbor);
        }
    }

    public void flow(Map<Direction, Particle> neighbors)
    {
        int rando = StdRandom.uniformInt(3);
        if (rando == 1 && neighbors.get(Direction.LEFT).flavor == ParticleFlavor.EMPTY)
        {
            moveInto(neighbors.get(Direction.LEFT));
        }
        else if (rando == 2 && neighbors.get(Direction.RIGHT).flavor == ParticleFlavor.EMPTY)
        {
            moveInto(neighbors.get(Direction.RIGHT));
        }
    }

    public void grow(Map<Direction, Particle> neighbors)
    {
        int rando = StdRandom.uniformInt(10);
        Particle p;
        if (rando < 3)
        {
            if (rando == 0)
            {
                p = neighbors.get(Direction.UP);
            }
            else if (rando == 1)
            {
                p = neighbors.get(Direction.LEFT);
            }
            else
            {
                p = neighbors.get(Direction.RIGHT);
            }
            if (p.flavor == ParticleFlavor.EMPTY)
            {
                p.flavor = flavor;
                p.lifespan = LIFESPANS.get(flavor);
            }
        }
    }

    public void burn(Map<Direction, Particle> neighbors)
    {
        if (neighbors.get(Direction.UP).flavor == ParticleFlavor.PLANT || neighbors.get(Direction.UP).flavor == ParticleFlavor.FLOWER)
        {
            int rando = StdRandom.uniformInt(10);
            if (rando < 5)
            {
                neighbors.get(Direction.UP).flavor = ParticleFlavor.FIRE;
                neighbors.get(Direction.UP).lifespan = FIRE_LIFESPAN;
            }
        }
        if (neighbors.get(Direction.DOWN).flavor == ParticleFlavor.PLANT || neighbors.get(Direction.DOWN).flavor == ParticleFlavor.FLOWER)
        {
            int rando = StdRandom.uniformInt(10);
            if (rando < 5)
            {
                neighbors.get(Direction.DOWN).flavor = ParticleFlavor.FIRE;
                neighbors.get(Direction.DOWN).lifespan = FIRE_LIFESPAN;
            }
        }
        if (neighbors.get(Direction.LEFT).flavor == ParticleFlavor.PLANT || neighbors.get(Direction.LEFT).flavor == ParticleFlavor.FLOWER)
        {
            int rando = StdRandom.uniformInt(10);
            if (rando < 5)
            {
                neighbors.get(Direction.LEFT).flavor = ParticleFlavor.FIRE;
                neighbors.get(Direction.LEFT).lifespan = FIRE_LIFESPAN;
            }
        }
        if (neighbors.get(Direction.RIGHT).flavor == ParticleFlavor.PLANT || neighbors.get(Direction.RIGHT).flavor == ParticleFlavor.FLOWER)
        {
            int rando = StdRandom.uniformInt(10);
            if (rando < 5)
            {
                neighbors.get(Direction.RIGHT).flavor = ParticleFlavor.FIRE;
                neighbors.get(Direction.RIGHT).lifespan = FIRE_LIFESPAN;
            }
        }
    }

    public void action(Map<Direction, Particle> neighbors)
    {
        if (this.flavor == ParticleFlavor.EMPTY)
        {
            return;
        }
        if (this.flavor != ParticleFlavor.BARRIER)
        {
            fall(neighbors);
        }
        if (this.flavor == ParticleFlavor.WATER)
        {
            flow(neighbors);
        }
        if (flavor == ParticleFlavor.PLANT || flavor == ParticleFlavor.FLOWER)
        {
            grow(neighbors);
        }
        if (flavor == ParticleFlavor.FIRE)
        {
            burn(neighbors);
        }
    }
    public void decrementLifespan()
    {
        if (lifespan > 0)
        {
            lifespan --;
        }
        if (lifespan == 0)
        {
            flavor = ParticleFlavor.EMPTY;
            lifespan = -1;
        }
    }
}