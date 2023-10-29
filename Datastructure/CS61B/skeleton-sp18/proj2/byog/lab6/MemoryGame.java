package byog.lab6;

import byog.StdDraw;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private int playerTurn = 0;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = { "You can do this!", "I believe in you!",
            "You got this!", "You're a star!", "Go Bears!",
            "Too easy for you!", "Wow, so impressive!" };

    public static void main(String[] args) {
        // if (args.length < 1) {
        // System.out.println("Please enter a seed");
        // return;
        // }

        // long seed = Integer.parseInt(args[0]);
        long seed = 234;
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, long seed) {
        /*
         * Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as
         * its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is
         * (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        // TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        // TODO: Generate random string of letters of length n
        int temp;
        String str = "";
        while (n != 0) {
            temp = this.rand.nextInt(CHARACTERS.length);
            str += CHARACTERS[temp];
            n--;
        }
        return str;
    }

    public void drawFrame() {
        // TODO: Take the string and display it in the center of the screen
        // TODO: If game is not over, display relevant game information at the top of
        // the screen
        if (gameOver == true) {
            playerTurn = playerTurn % ENCOURAGEMENT.length;
            StdDraw.text(0.09 * this.width, 0.97 * this.height, "Round:" + Integer.toString(this.round));
            StdDraw.text(0.5 * this.width, 0.97 * this.height, ENCOURAGEMENT[playerTurn]);
            StdDraw.line(0, 0.95 * height, width, 0.95 * height);
            StdDraw.text(0.87 * this.width, 0.97 * this.height, "You're a star!");
        }
    }

    public void flashSequence(String letters) {
        // TODO: Display each character in letters, making sure to blank the screen
        // between letters
        StdDraw.clear(Color.BLACK);
        for (int i = 0; i < letters.length(); i++) {
            drawFrame();
            StdDraw.text(0.5 * this.width, 0.5 * this.height, Character.toString(letters.charAt(i)));
            StdDraw.show();
            StdDraw.pause(1000);
            StdDraw.clear(StdDraw.BLACK);
            drawFrame();
            StdDraw.show();
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        // TODO: Read n letters of player input

        String str = "";
        while (str.length() != n) {
            try {
                StdDraw.clear(StdDraw.BLACK);
                char c = StdDraw.nextKeyTyped();
                if ('a' <= c && c <= 'z') {
                    str += c;
                }
                StdDraw.text(0.5 * this.width, 0.5 * this.height, str);
                StdDraw.pause(50);
                playerTurn++;
                drawFrame();
                StdDraw.show();
            } catch (java.util.NoSuchElementException e) {
            }
        }
        return str;
    }

    public void startGame() {
        // TODO: Set any relevant variables before the game starts
        this.round = 1;
        Font font = new Font("Monaco", Font.BOLD, 25);
        StdDraw.setFont(font);
        StdDraw.setPenColor(StdDraw.WHITE);
        // TODO: Establish Game loop
        while (true) {
            StdDraw.clear(StdDraw.BLACK);
            drawFrame();
            StdDraw.text(0.5 * this.width, 0.5 * this.height, "Round:" + Integer.toString(round) + "  Good Luck!");
            this.gameOver = true;
            StdDraw.show();
            StdDraw.pause(1000);
            String aimStr = generateRandomString(round);
            flashSequence(aimStr);
            String userStr = solicitNCharsInput(round);
            StdDraw.clear(StdDraw.BLACK);
            if (aimStr.compareTo(userStr) == 0) {
                drawFrame();
                StdDraw.text(0.5 * this.width, 0.5 * this.height, "Current,Well Done!");
                StdDraw.show();
                StdDraw.pause(1000);
                round++;
            } else {
                break;
            }
        }
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.text(0.5 * this.width, 0.5 * this.height,
                "Game over! You made it to round:" + Integer.toString(round));
        StdDraw.show();
    }

}
