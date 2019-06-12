package byow.lab12;
import edu.princeton.cs.introcs.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    private static class Position {
        int x;
        int y;
        Position(int xp, int yp) {
            x = xp;
            y = yp;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    /**
     * calculate the width of rows of hexagon
     * @param size size of hexagon
     * @param height height of row
     * @return width of row
     */
    private static int calWidth(int size, int height) {
        if (height >= size) {
            height = 2 * size - height - 1;
        }
        return size + 2 * height;
    }

    /**
     *
     * @param size size of hexagonal
     * @param height height of row
     * @return xoffset of position
     */
    private static int xOffSet(int size, int height) {
        if (height >= size) {
            height = 2 * size - height - 1;
        }
        return size - height - 1;
    }

    /**
     * add a hexagon of size s with t at p
     * @param world the given world
     * @param p the leftlow of the position of hexagon
     * @param s the size of position
     * @param t the content of tile
     */
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        for (int h = 0; h < 2 * s; h += 1) {
            int width = calWidth(s, h);
            for (int i = 0; i < width; i += 1) {
                int xoffset = xOffSet(s, h);
                world[p.getX() + i + xoffset][p.getY() + h] = t;
            }
        }
    }

    /**
     * calculate all positions of hexagons
     * @param p position of top center hexagon
     * @param s size of hexagon
     * @return all positions of hexagons
     */
    private static List<Position> calPostions(Position p, int s) {
        ArrayList<Position> results = new ArrayList<>();
        int yoffset = - 2 * s;
        for (int i = 0; i < 3; i += 1) {
            for (int j = 0; j < 5 - i; j += 1) {
                int xp = p.getX() + i * (2 * s - 1);
                int yp = p.getY() - i * s;
                Position item = new Position(xp, yp + j * yoffset);
                results.add(item);
                if (i > 0) {
                    xp = p.getX() - i * (2 * s - 1);
                    item = new Position(xp, yp + j * yoffset);
                    results.add(item);
                }
            }
        }
        return results;
    }

    /**
     * return a random TETtile
     * @return
     */
    private static TETile randTETile() {
        int tileNum = StdRandom.uniform(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.WATER;
            default: return Tileset.FLOOR;
        }
    }

    /**
     * draw tesselationhexagon in the given world
     * @param world the given world
     * @param p the position of top center hexagon
     * @param s size of hexagon
     */
    public static void drawTesselationHexagon(TETile[][] world, Position p, int s) {
        List<Position> positions = calPostions(p, s);
        for (Position item : positions) {
            addHexagon(world, item, s, randTETile());
        }
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(60, 60);

        // initialize tiles
        TETile[][] world = new TETile[60][60];
        for (int x = 0; x < 60; x += 1) {
            for (int y = 0; y < 60; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // add a hexagon
        Position p = new Position(30, 50);
        drawTesselationHexagon(world, p, 4);

        // draws the world to the screen
        ter.renderFrame(world);
    }
}
