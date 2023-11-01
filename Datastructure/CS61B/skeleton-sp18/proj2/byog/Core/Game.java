package byog.Core;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import byog.StdDraw;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.game.MapWorld;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;

    /**
     * Method used for playing a fresh game. The game should start from the main
     * menu.
     */
    public String playWithKeyboard(int width, int height) {

        Font font = new Font("Times New Roman Italic", Font.BOLD, 40);
        Font font2 = new Font("Times New Roman Italic", Font.BOLD, 20);
        Font font3 = new Font("Arial", Font.BOLD, 20);
        /**
         * 绘制开始页面
         */
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(font);
        StdDraw.text(0.5 * width, 0.7 * height, "CS61B: THE GAME");
        StdDraw.setFont(font2);
        StdDraw.text(0.5 * width, 0.5 * height, "New Game (N)");
        StdDraw.text(0.5 * width, 0.45 * height, "Load Game (L)");
        StdDraw.text(0.5 * width, 0.40 * height, "Quit (Q)");
        StdDraw.show();
        String temp = "";
        /*
         * 监听键盘事件
         */
        while (true) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_N)) {
                StdDraw.text(0.5 * width, 0.35 * height, "Please Enter the Seed:");
                StdDraw.nextKeyTyped();
                StdDraw.show();
                while (true) {
                    StdDraw.setFont(font3);
                    try {
                        char c = StdDraw.nextKeyTyped();
                        if ('0' <= c && c <= '9') {
                            temp += c;
                            System.out.println(temp);
                            StdDraw.textLeft(0.4 * width, 0.3 * height, temp);
                            StdDraw.pause(50);
                            StdDraw.show();
                        } else if (c == 'q') {
                            System.exit(0);
                        } else if (c == 's') {
                            System.out.println("there!");
                            return temp;
                        }
                    } catch (java.util.NoSuchElementException e) {
                    }
                }
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_Q)) {
                System.exit(0);
            } else if (StdDraw.isKeyPressed(KeyEvent.VK_L)) {
                try {
                    System.out.println("124");
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("Map2.txt")));
                    MapWorld world = (MapWorld) ois.readObject();
                    return "OK";
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                } catch (ClassNotFoundException e) {
                }
            }
        }

    }

    /**
     * Method used for autograding and testing the game code. The input string will
     * be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww"). The
     * game should
     * behave exactly as if the user typed these characters into the game after
     * playing
     * playWithKeyboard. If the string ends in ":q", the same world should be
     * returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should
     * return the same
     * world. However, the behavior is slightly different. After playing with
     * "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string
     * "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the
     * saved game.
     * 
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }
}
