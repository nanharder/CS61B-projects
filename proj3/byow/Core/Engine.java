package byow.Core;

import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Engine {
    /* fixed parameters for the settings of game */
    private static final int TILE_SIZE = 16;
    private static final int WIDTH = 31;
    private static final int HEIGHT = 31;
    private static final int YOFFSET = 4;
    private int windingPercent = 30;
    private Direction direction = new Direction();
    private TERenderer ter;

    /* helpful variable to build map */
    private Random rd;
    private List<Room> rooms;
    private HashMap<Position, Integer> regions;
    private Set<Position> carvedPoints;
    private int currentRegion;

    private StringBuilder actions;
    private Position door;
    private TETile[][] world = new TETile[WIDTH][HEIGHT];
    private TETile[][] darkworld = new TETile[WIDTH][HEIGHT];
    private Position avater;

    private boolean isplaying;
    private boolean iswin;

    /**
     * the constructor of class of engine
     */
    public Engine() {
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT + YOFFSET, 0, YOFFSET);
    }

    /**
     * initialize the engine, used when begin a new map
     */
    public void initialize(){
        rd = new Random();
        rooms = new ArrayList<>();
        regions = new HashMap<>();
        currentRegion = -1;
        carvedPoints = new HashSet<>();
        actions = new StringBuilder();
        iswin = false;
        isplaying = false;
    }



    /**
     * useful class to build the world
     */

    /**
     * class represents the room in the map
     */
    private class Room {
        private int xp;
        private int yp;
        private int width;
        private int height;

        Room(int x, int y, int width, int height) {
            xp = x;
            yp = y;
            this.width = width;
            this.height = height;
        }

        private boolean isOverlap(Room r) {
            return (this.xp + this.width >= r.xp && r.xp + r.width >= this.xp &&
                    this.yp + this.height >= r.yp && r.yp + r.height >= this.yp);
        }
    }

    /**
     * class represents the position as (x, y)
     */
    private class Position {
        private int xp;
        private int yp;

        Position(int x, int y) {
            xp = x;
            yp = y;
        }

        private Position plus(Position p) {
            return new Position(this.xp + p.xp, this.yp + p.yp);
        }

        private Position subtract(Position p) {
            return new Position(this.xp - p.xp, this.yp - p.yp);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (o.getClass() != Position.class) {
                return false;
            }
            Position p =(Position) o;
            return (this.xp == p.xp && this.yp == p.yp);
        }

        @Override
        public int hashCode() {
            return this.xp + this.yp * WIDTH;
        }

        private int distance(Position p) {
            Position pos = this.subtract(p);
            return (pos.xp * pos.xp + pos.yp * pos.yp);
        }
    }

    /**
     * class to present directions with positions
     */
    private class Direction {
        private final Position UP = new Position(0, 1);
        private final Position DOWN = new Position(0, -1);
        private final Position LEFT = new Position(-1, 0);
        private final Position RIGHT = new Position(1, 0);
        private final Position UPLEFT = new Position(-1 ,1);
        private final Position UPRIGHT = new Position(1, 1);
        private final Position DOWNLEFT = new Position(-1, -1);
        private final Position DOWNRIGHT = new Position(1, -1);
        private Position[] cardinal = {UP, DOWN, LEFT, RIGHT};
        private Position[] fullCardinals = {UP, DOWN, LEFT, RIGHT, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT};

    }



    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        drawInitialInterface();
        while (true) {
            if (isplaying) {
                drawPress();
            }
            if (StdDraw.hasNextKeyTyped()) {
                char action = StdDraw.nextKeyTyped();
                actionHandler(action);
                if (isplaying) {
                    showMap();
                }
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        StringInputDevice sd = new StringInputDevice(input);
        actions = new StringBuilder();

        char cur = sd.getNextKey();
        while (sd.possibleNextInput() && cur != 'n') {
            cur = sd.getNextKey();
        }
        if (cur != 'n') {
            throw new IllegalArgumentException();
        }
        actions.append('n');
        cur = sd.getNextKey();
        StringBuilder sb = new StringBuilder();
        while (sd.possibleNextInput() && cur != 's') {
            sb.append(cur);
            cur = sd.getNextKey();
        }
        if (cur != 's') {
            throw new IllegalArgumentException();
        }
        int seed = Integer.parseInt(sb.toString());
        actions.append(seed);
        actions.append('s');
        buildMap(seed);

        isplaying = true;
        while (sd.possibleNextInput()) {
            cur = sd.getNextKey();
            actionHandler(cur);
        }
        return world;
    }




    /**
     * Phase1:built map based the seed.
     * @source http://journal.stuffwithstuff.com/2014/12/21/rooms-and-mazes/
     */
    public void buildMap(int seed) {
        //set the random seed
        rd.setSeed(seed);

        // initialize tiles
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.WALL;
                darkworld[x][y] = Tileset.NOTHING;
            }
        }

        //add random rooms 500 times
        for (int i = 0; i < 500; i += 1) {
            //To get random width and height of room
            int size = RandomUtils.uniform(rd, 1, 2) * 2 + 1;
            int rectangular = RandomUtils.uniform(rd, 0, size / 2 + 1) * 2;
            int width = size;
            int height = size;
            if (RandomUtils.bernoulli(rd)) {
                width += rectangular;
            } else {
                height += rectangular;
            }

            //To get the random position of room
            int xPosition = RandomUtils.uniform(rd, 0, (WIDTH - width) / 2) * 2 + 1;
            int yPosition = RandomUtils.uniform(rd, 0, (HEIGHT - height) / 2) * 2 + 1;

            //check if the room isoverlap
            Room room = new Room(xPosition, yPosition, width, height);
            boolean isoverlap = false;
            for (Room r : rooms) {
                if (room.isOverlap(r)) {
                    isoverlap = true;
                    break;
                }
            }
            if (isoverlap) {
                continue;
            }

            //carve the valid room
            rooms.add(room);
            startRegion();
            for (int x = room.xp; x < room.xp + width; x += 1) {
                for (int y = room.yp; y < room.yp + height; y += 1) {
                    Position pos = new Position(x, y);
                    carve(pos);
                }
            }
        }

        //add maze
        for (int y = 1; y < HEIGHT; y += 2) {
            for (int x = 1; x < WIDTH; x += 2) {
                if ((isWall(x - 1, y) &&
                        isWall(x + 1, y) &&
                        isWall(x, y - 1) &&
                        isWall(x, y + 1))) {
                    Position start = new Position(x, y);
                    growMaze(start);
                }
            }
        }

        //connect the regions
        connectRegions();
        //remove the deadends
        boolean done = false;
        while (!done) {
            done = true;
            for (int i = 1; i < WIDTH; i += 1) {
                for (int j =1; j < HEIGHT; j += 1) {
                    Position pos = new Position(i, j);
                    if (isWall(pos)) {
                        continue;
                    }
                    int exit = 0;
                    for (Position dir : direction.cardinal) {
                        if (!isWall(pos.plus(dir))) {
                            exit += 1;
                        }
                    }
                    if (exit > 1) {
                        continue;
                    }
                    setTile(pos, Tileset.WALL);
                    done = false;
                    carvedPoints.remove(pos);
                }
            }
        }
        //remove unnecessary wall
        done = false;
        while (!done) {
            done = true;

            for (int i = 0; i < WIDTH; i += 1) {
                for (int j = 0; j < HEIGHT; j += 1) {
                    Position pos = new Position(i, j);
                    if (!isWall(pos)) {
                        continue;
                    }
                    int wallCount = 0;
                    for (Position dir : direction.fullCardinals) {
                        if (isWall(pos.plus(dir)) || isNothing(pos.plus(dir))) {
                            wallCount += 1;
                        }
                    }
                    if (wallCount < 8) {
                        continue;
                    }
                    setTile(pos, Tileset.NOTHING);
                    done = false;
                }
            }
        }

        //carve the door
        ArrayList<Position> doors = new ArrayList<>();
        for (int i = 1; i < WIDTH; i+= 1) {
            for (int j = 1; j <HEIGHT; j+= 1) {
                Position pos = new Position(i, j);
                if (!isWall(pos)) {
                    continue;
                }
                int exit = 0;
                int wall = 0;
                int nothing = 0;
                for (Position dir: direction.fullCardinals) {
                    Position unchecked = pos.plus(dir);
                    if (isWall(unchecked)) {
                        wall += 1;
                    } else if (isNothing(unchecked)) {
                        nothing += 1;
                    } else {
                        exit += 1;
                    }
                    if (exit == 3 && wall == 2 && nothing == 3) {
                        doors.add(pos);
                    }
                }
            }
        }
        door = doors.get(RandomUtils.uniform(rd, 0,doors.size()));
        setTile(door, Tileset.LOCKED_DOOR);

        //set the avater
        int maxDistance = 0;
        avater = door;
        for (Position pos : carvedPoints) {
            int dis = pos.distance(door);
            if (dis > maxDistance) {
                avater = pos;
                maxDistance = dis;
            }
        }
        setTile(avater, Tileset.AVATAR);

        //build the dark world
        copyTileToDark(avater);
        for (Position dir : direction.fullCardinals) {
            copyTileToDark(avater.plus(dir));
        }
        copyTileToDark(door);
    }

    public void showMap() {
        iswin = false;
        isplaying = true;

        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE);
        StdDraw.setFont(font);
        ter.renderFrame(darkworld);
        drawHud();
    }


    /**
     * useful utils in phase1
     */

    /**
     * set the tile in darkworld is same as world
     */
    private void copyTileToDark(Position pos) {
        darkworld[pos.xp][pos.yp] = world[pos.xp][pos.yp];
    }

    /**
     * add the index of region
     */
    private void startRegion() {
        currentRegion += 1;
    }

    /**
     * check if the postion is in the map
     */
    private boolean isValidatePos(Position p) {
        return (!(p.xp < 0 || p.xp >= WIDTH || p.yp < 0 || p.yp >= HEIGHT));

    }

    /**
     * check if the position is nothing or out of the map
     */
    private boolean isNothing(Position p) {
        if (!isValidatePos(p)) {
            return true;
        }
        return (world[p.xp][p.yp] == Tileset.NOTHING);
    }

    /**
     * check if the position is the tile
     */
    private boolean checkTile(Position pos, TETile tile) {
        if (!isValidatePos(pos)) {
            return false;
        }
        return (world[pos.xp][pos.yp] == tile);
    }

    /**
     * check if the tile is wall
     */
    private boolean isWall(Position pos) {
        return checkTile(pos, Tileset.WALL);
    }

    private boolean isWall(int x, int y) {
        Position pos = new Position(x, y);
        return isWall(pos);
    }

    /**
     * set the position as the tile
     */
    private void setTile(Position pos, TETile tile) {
        world[pos.xp][pos.yp] = tile;
    }

    /**
     * set the position in the darkworld as the tile
     */
    private void setTileInDark(Position pos) {
        darkworld[pos.xp][pos.yp] = Tileset.NOTHING;
    }

    /**
     * carve the position as floor
     */
    private void carve(Position pos) {
        if (isValidatePos(pos)) {
            setTile(pos, Tileset.FLOOR);
            regions.put(pos, currentRegion);
            carvedPoints.add(pos);
        }
    }

    /**
     *check if the direction of postion could be carved
     */
    private boolean canCarve(Position pos, Position dir) {
        Position target = pos.plus(dir);
        Position start = pos.subtract(target);
        if (!isWall(target.xp, target.yp)) {
            return false;
        }
        for (Position d : direction.cardinal) {
            if (d.equals(start)) {
                continue;
            }
            Position unchecked = target.plus(d);
            if (!isWall(unchecked.xp, unchecked.yp)) {
                return false;
            }
        }
        return true;
    }

    /**
     * glow tree algorithms to grow maze.
     */
    private void growMaze(Position start) {
    //create new
        LinkedList<Position> cells = new LinkedList<>();
        Position lastDir = null;

        //add a new maze
        startRegion();
        carve(start);

        //add the start position to the cell
        cells.add(start);
        while (!cells.isEmpty()) {
            Position cell = cells.getLast();
            ArrayList<Position> unmadeCells = new ArrayList<>();

            //add potential cancarved tile to unmadecells
            for (Position dir : direction.cardinal) {
                if (canCarve(cell, dir)) {
                    unmadeCells.add(dir);
                }
            }

            if (!unmadeCells.isEmpty()) {
                Position dir;
                if (unmadeCells.contains(lastDir) && RandomUtils.uniform(rd, 100) > windingPercent) {
                    dir = lastDir;
                } else {
                    dir = unmadeCells.get(RandomUtils.uniform(rd, unmadeCells.size()));
                }

                carve(cell.plus(dir));
                carve(cell.plus(dir).plus(dir));
                if (isValidatePos(cell.plus(dir).plus(dir))) {
                    cells.add(cell.plus(dir).plus(dir));
                } else {
                    cells.add(cell.plus(dir));
                }
                lastDir = dir;
            } else {
                cells.removeLast();
                lastDir = null;
            }

        }

    }

    private void connectRegions() {
        // find all potential connections
        HashMap<Position, ArrayList<Integer>> connections = new HashMap<>();
        ArrayList<Position> connectors = new ArrayList<>();
        for (int i = 1; i < WIDTH - 1; i += 1) {
            for (int j = 1; j < HEIGHT - 1; j += 1) {
                if (!isWall(i, j)) {
                    continue;
                }

                Position unchecked = new Position(i , j);
                int regionCount = 0;
                ArrayList<Integer> region = new ArrayList<>();

                for (Position dir : direction.cardinal) {
                    Position target = unchecked.plus(dir);
                    if (checkTile(target, Tileset.FLOOR)) {
                        int r = regions.get(target);
                        if (!region.contains(r)) {
                            region.add(r);
                            regionCount += 1;
                        }
                    }
                }

                if (regionCount < 2) {
                    continue;
                }
                connections.put(unchecked, region);
                connectors.add(unchecked);
            }
        }

        int[] merged = new int[currentRegion + 1];
        Set<Integer> openRegions = new HashSet<>();
        for (int i = 0; i <= currentRegion; i += 1) {
            merged[i] = i;
            openRegions.add(i);
        }

        while (openRegions.size() > 1) {
            Position connector = connectors.get(RandomUtils.uniform(rd, connectors.size()));

            setTile(connector, Tileset.FLOOR);
            carvedPoints.add(connector);

            ArrayList<Integer> connectedRegions = new ArrayList<>();
            for (int i : connections.get(connector)) {
                connectedRegions.add(merged[i]);
            }
            int target = connectedRegions.get(0);
            List<Integer> sources =connectedRegions.subList(1, connectedRegions.size());
            for (int i = 1; i < merged.length; i += 1) {
                if (connectedRegions.contains(merged[i])) {
                    merged[i] = target;
                }
            }

            openRegions.removeAll(sources);
            // remove all connectors not necessary
            connectors.removeIf((pos) -> {
                if (pos.distance(connector) < 2) {
                    return true;
                }

                Set<Integer> r = new HashSet<>();
                for (int i : connections.get(pos)) {
                    r.add(merged[i]);
                }
                if (r.size() > 1) {
                    return false;
                }

                if (RandomUtils.uniform(rd, 100) > 95) {
                    setTile(pos, Tileset.FLOOR);
                    carvedPoints.add(pos);
                }

                return true;

            });
        }
    }



    /**
     *Phase2: build the UI for users.
     */

    private void drawSeedInterface() {
        StdDraw.clear(Color.BLACK);
        int midWidth = WIDTH / 2;
        int midHeight = HEIGHT / 2;
        drawTitle();
        Font context = new Font("Monaco", Font.BOLD, TILE_SIZE + 2);
        StdDraw.setFont(context);
        StdDraw.text(midWidth, midHeight, "Please input a number as seed(0 ~ 1000000):");
        StdDraw.text(midWidth, midHeight - 2.4, "Start (S)");
        StdDraw.text(midWidth, midHeight - 3.6, "Quit (Q)");
    }

    private void drawInitialInterface() {
        int midWidth = WIDTH / 2;
        int midHeight = HEIGHT / 2;

        drawTitle();
        Font context = new Font("Monaco", Font.BOLD, TILE_SIZE + 2);
        StdDraw.setFont(context);
        StdDraw.text(midWidth, midHeight, "New Game (N)");
        StdDraw.text(midWidth, midHeight - 1.2, "Load Game (L)");
        StdDraw.text(midWidth, midHeight - 2.4, "Quit (Q)");
        if (iswin) {
            StdDraw.text(midWidth, midHeight + 2.4,
                    "Congratulations, you finally leave the dungeon!");
            StdDraw.text(midWidth, midHeight + 1.2, "Now you can start again!");
        }
        StdDraw.show();


        initialize();
    }

    private void drawHud() {
        setStd();
        String interval = "     ";
        StdDraw.textLeft( 0.5, 2.7 ,  "Up (W)" + interval
                        + "Down (S)" + interval + "Left (A)" + interval
                        + "Right (D)" + interval + "Save (:Q)");
        StdDraw.line(0.5, 2, WIDTH - 0.5, 2);
        StdDraw.textLeft( 0.5, 1.3, "Tips: Click the tile to get the description.");
        StdDraw.show();
    }

    private void drawPress() {
        setStd();
        if (StdDraw.isMousePressed()) {
            clearBottom();
            int x = (int) StdDraw.mouseX();
            int y = (int) StdDraw.mouseY() - YOFFSET;
            Position pos = new Position(x, y);
            if (isValidatePos(pos)) {
                TETile target = darkworld[x][y];
                if (target == Tileset.WALL) {
                    StdDraw.textLeft( 0.5, 0.3, "It's " +
                                target.description() +", you couldn't go through it.");
                } else if (target == Tileset.FLOOR) {
                    StdDraw.textLeft( 0.5, 0.3, "It's " +
                            target.description() +", you could go through it.");
                } else if (target == Tileset.NOTHING) {
                    if (world[x][y] == Tileset.NOTHING) {
                        StdDraw.textLeft( 0.5, 0.3, "It's " +
                                target.description() +", don't care about it");
                    } else {
                        StdDraw.textLeft( 0.5, 0.3, "It's in the dark, " +
                                "you could only see the tile close to you.");
                    }
                } else if (target == Tileset.LOCKED_DOOR) {
                    StdDraw.textLeft( 0.5, 0.3, "It's " +
                            target.description() +", your goal is to get to it.");
                }
            }
            StdDraw.show();
        }
    }

    private void actionHandler(char action) {
        actions.append(action);
        if (action >= 'A' && action <= 'Z') {
            action += 32;
        }
        if (isplaying) {
            switch (action) {
                case ('w') :
                    move(avater.plus(direction.UP));
                    break;
                case ('s') :
                    move(avater.plus(direction.DOWN));
                    break;
                case ('a') :
                    move(avater.plus(direction.LEFT));
                    break;
                case ('d') :
                    move(avater.plus(direction.RIGHT));
                    break;
                case ('q') :
                    char lastAction = actions.charAt(actions.length() - 2);
                    if (lastAction == ':') {
                        saveGame();
                        System.exit(0);
                    }
                    break;
            }
        } else {
            switch (action) {
                case ('n') :
                    int seed = getSeed();
                    buildMap(seed);
                    actions.append(seed);
                    actions.append('s');
                    showMap();
                    break;
                case ('l') :
                    String actions = loadGame();
                    if (actions.length() > 0) {
                        interactWithInputString(actions);
                        showMap();
                    } else {
                        StdDraw.text(WIDTH / 2, HEIGHT / 4,
                                "Sorry, you don't have any save now.");
                    }
                    break;
                case ('q') :
                    System.exit(0);
            }
        }
    }

    private void move(Position pos) {
        if (checkTile(pos, Tileset.FLOOR)) {
            //set old world to dark
            setTileInDark(avater);
            for (Position dir : direction.fullCardinals) {
                setTileInDark(avater.plus(dir));
            }

            // change the tile
            setTile(avater, Tileset.FLOOR);
            setTile(pos, Tileset.AVATAR);
            avater = pos;

            // light new avater and neighbors
            copyTileToDark(avater);
            for (Position dir : direction.fullCardinals) {
                copyTileToDark(avater.plus(dir));
            }
        } else if (checkTile(pos, Tileset.LOCKED_DOOR)) {
            setTile(avater, Tileset.FLOOR);
            setTile(pos, Tileset.AVATAR);
            iswin = true;
            drawInitialInterface();
        }
    }


    /**
     *useful utils in phase2
     */

    private int getSeed() {
        int midWidth = WIDTH / 2;
        int midHeight = HEIGHT / 2;
        drawSeedInterface();
        StdDraw.show();

        int seed = 0;
        boolean done = false;
        while (!done) {
            if (StdDraw.hasNextKeyTyped()) {
                char input = StdDraw.nextKeyTyped();
                if (input == 'Q' || input == 'q') {
                    System.exit(0);
                } else if (input == 'S' || input == 's') {
                    done = true;
                } else if (input >= '0' && input <= '9') {
                    if (seed >= 100000) {
                        drawSeedInterface();
                        StdDraw.text(midWidth, midHeight - 1.2, String.valueOf(seed)
                                    + "(The seed could't be more than 1000000!)");
                        StdDraw.show();
                    }
                    seed = seed * 10 + input - '0';
                    drawSeedInterface();
                    StdDraw.text(midWidth, midHeight - 1.2, String.valueOf(seed));
                    StdDraw.show();
                }
            }
        }
        return seed;
    }

    private void saveGame() {
        File f = new File("./save_data.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            String save = actions.substring(0, actions.length() - 2);
            os.writeObject(save);
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private String loadGame() {
        File f = new File("./save_data.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                return (String) os.readObject();
            } catch (FileNotFoundException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        return "";
    }

    private void drawTitle() {
        StdDraw.clear(Color.black);
        Font title = new Font("Monaco", Font.BOLD, TILE_SIZE * 2);
        StdDraw.setFont(title);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH / 2,HEIGHT / 4 * 3, "CS61B: Dungeon Game");
    }

    private void setStd() {
        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
    }

    private void clearBottom() {
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(WIDTH / 2, 0.5, WIDTH / 2, 0.5);
        StdDraw.setPenColor(Color.WHITE);

    }

}

