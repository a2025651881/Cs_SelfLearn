package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world that contains RANDOM tiles.
 */
public class RandomWorldDemo {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    private static void singleHexagon(int x, int y, int size, TETile[][] Tiles) {
        TETile fill = randomTile();
        int count = size;
        for (int j = 0; j < count; j++) {
            for (int i = 0; i < size; i++) {
                Tiles[x + i][y] = fill;
            }
            if (j == count - 1) {
                y--;
                break;
            }
            x--;
            y--;
            size += 2;
        }
        for (int j = 0; j < count; j++) {
            for (int i = 0; i < size; i++) {
                Tiles[x + i][y] = fill;
            }
            x++;
            y--;
            size -= 2;
        }
    }

    private static int offset_x(int size) {
        return 2 * size - 1;
    }

    private static int offset_y(int size) {
        return size;
    }

    private static void addHexagon(int center_x, int center_y, int size, TETile[][] tiles) {
        singleHexagon(center_x, center_y, size, tiles);
        singleHexagon(center_x - offset_x(size), center_y - offset_y(size), size, tiles);
        singleHexagon(center_x + offset_x(size), center_y - offset_y(size), size, tiles);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                singleHexagon(center_x + 2 * (j - 1) * offset_x(size), center_y - offset_y(size) * (2 + i * 2), size,
                        tiles);
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                singleHexagon(center_x + (2 * j - 1) * offset_x(size), center_y - offset_y(size) * (3 + i * 2), size,
                        tiles);
            }
        }
        singleHexagon(center_x - offset_x(size), center_y - offset_y(size) * 7, size, tiles);
        singleHexagon(center_x + offset_x(size), center_y - offset_y(size) * 7, size, tiles);
        singleHexagon(center_x, center_y - offset_y(size) * 8, size, tiles);
    }

    /**
     * Fills the given 2D array of tiles with RANDOM tiles.
     * 
     * @param tiles
     */
    public static void fillWithRandomTiles(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                // tiles[x][y] = randomTile();
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Picks a RANDOM tile with a 33% change of being
     * a wall, 33% chance of being a flower, and 33%
     * chance of being empty space.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0:
                return Tileset.GRASS;
            case 1:
                return Tileset.FLOWER;
            case 2:
                return Tileset.SAND;
            case 3:
                return Tileset.MOUNTAIN;
            case 4:
                return Tileset.TREE;
            default:
                return Tileset.NOTHING;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];

        fillWithRandomTiles(randomTiles);
        addHexagon(25, 45, 4, randomTiles);
        ter.renderFrame(randomTiles);
    }

}
