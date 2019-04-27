package creatures;

import huglife.*;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.*;


public class TestClorus {
    @Test
    public void testBasics() {
        Clorus p = new Clorus(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), p.color());
        p.move();
        assertEquals(1.97, p.energy(), 0.001);
        p.move();
        assertEquals(1.94, p.energy(), 0.001);
        p.stay();
        assertEquals(1.93, p.energy(), 0.001);
        p.stay();
        assertEquals(1.92, p.energy(), 0.001);
    }

    @Test
    public void testReplicate() {
        Clorus p = new Clorus(2);
        Clorus rep = p.replicate();
        assertEquals(1.0, p.energy(), 0.01);
        assertEquals(1.0, rep.energy(), 0.01);
        assertNotEquals(p, rep);
    }

    @Test
    public void testAtack() {
        Clorus c = new Clorus(2);
        Plip p = new Plip(1);
        c.attack(p);
        assertEquals(3.0, c.energy(), 0.01);
    }

    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Clorus p = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Plip());

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // Any Cloruss; attack one of them;
        p = new Clorus(1.2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<Direction, Occupant>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Plip());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        actual = p.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        p = new Clorus(1.2);
        HashMap<Direction, Occupant> topEmpty2 = new HashMap<Direction, Occupant>();
        topEmpty2.put(Direction.TOP, new Empty());
        topEmpty2.put(Direction.BOTTOM, new Impassible());
        topEmpty2.put(Direction.LEFT, new Impassible());
        topEmpty2.put(Direction.RIGHT, new Impassible());

        actual = p.chooseAction(topEmpty2);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected, actual);


        // Energy < 1; stay.
        p = new Clorus(.99);

        actual = p.chooseAction(topEmpty2);
        expected = new Action(Action.ActionType.MOVE, Direction.TOP);

        assertEquals(expected, actual);

    }
}
