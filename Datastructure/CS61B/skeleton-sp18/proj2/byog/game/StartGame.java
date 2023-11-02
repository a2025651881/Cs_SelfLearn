package byog.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

import byog.Core.Game;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class StartGame {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;

    public static void main(String[] arg) {
        TERenderer ter = new TERenderer();
        // Renderer init
        ter.initialize(WIDTH, HEIGHT);

        MapWorld world = new MapWorld();
        // start page
        Game g = new Game();
        String str = g.playWithKeyboard(WIDTH, HEIGHT);
        if (str == "OK") {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("Map2.txt")));
                world = (MapWorld) ois.readObject();
                System.out.println(world.SEED);
                // flage: true 以存档形式开启
                world.renderScreen(world, true);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
            }
        } else {
            world.SEED = Long.parseLong(str);
            System.out.println(world.SEED);
            Random RANDOM = new Random(world.SEED);
            // set tiles
            world.RANDOM = RANDOM;
            TETile[][] tiles = new TETile[WIDTH][HEIGHT];
            world.setTiles(tiles);
            world.Map_init();
            world.Map_generate();
            // flage: false 以存档形式开启
            world.renderScreen(world, false);
        }

        // game render

        // ter.renderFrame(tiles);

    }
}
