package byog.game;

import byog.StdDraw;
import java.awt.Font;
import java.awt.event.KeyEvent;

public class Draw {
    public static void main(String[] arg) {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenRadius(0.05);
        StdDraw.setPenColor(StdDraw.WHITE);
        Font font = new Font("Times New Roman Italic", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.7, "CS61B: THE GAME");
        Font font2 = new Font("Times New Roman Italic", Font.BOLD, 20);
        StdDraw.setFont(font2);
        Font font3 = new Font("Arial", Font.BOLD, 20);
        StdDraw.text(0.5, 0.5, "New Game (N)");
        StdDraw.text(0.5, 0.45, "Load Game (L)");
        StdDraw.text(0.5, 0.40, "Quit (Q)");
        String temp = "";
        while (true) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_N)) {
                StdDraw.text(0.5, 0.35, "Please Enter the Seed:");
                StdDraw.nextKeyTyped();
                while (true) {
                    StdDraw.setFont(font3);
                    try {
                        char c = StdDraw.nextKeyTyped();
                        if (c != 's') {
                            temp += c;
                            System.out.println(temp);
                            StdDraw.textLeft(0.3, 0.3, temp);
                            StdDraw.pause(50);
                        } else {
                            System.out.println("there!");
                            break;
                        }
                    } catch (java.util.NoSuchElementException e) {
                    }
                }
                break;
            }
        }
    }
}
