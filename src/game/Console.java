package game;

import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import enigma.console.TextAttributes;
import java.awt.Color;

public class Console {
    private static enigma.console.Console c;
    public static final TextAttributes greenonblack = new TextAttributes(Color.GREEN);
    public static final TextAttributes redonblack = new TextAttributes(Color.RED);
    public static final TextAttributes blueonblack = new TextAttributes(Color.BLUE);
    public static final TextAttributes blackongreen = new TextAttributes(Color.BLACK, Color.GREEN);

    private static KeyListener klis;
    private static int keypr; // key pressed?
    private static int rkey;

    public static void setup() throws Exception { // --- Contructor
        // ------ Standard code for mouse and keyboard ------ Do not change
        klis = new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                if (keypr == 0) {
                    keypr = 1;
                    rkey = e.getKeyCode();
                }
            }

            public void keyReleased(KeyEvent e) {
            }
        };
        c.getTextWindow().addKeyListener(klis);
        // ----------------------------------------------------
    }

    public static int takeKeyPress() {
        if (keypr == 1) { // if keyboard button pressed
            keypr = 0;
            return rkey;
        }
        return 0;
    }

    public static void getConsole(String title, int xSize, int ySize, int fontSize, int fontNo) {
        c = Enigma.getConsole(title, xSize, ySize, fontSize, fontNo);
    }

    public static void println(String str) {
        c.getTextWindow().output(str + "\n");
    }

    public static void println(String str, TextAttributes t) {
        c.getTextWindow().output(str + "\n", t);
    }

    public static void print(String str) {
        c.getTextWindow().output(str);
    }

    public static void print(String str, TextAttributes t) {
        c.getTextWindow().output(str, t);
    }

    public static void setCursorPosition(int x, int y) {
        c.getTextWindow().setCursorPosition(x, y);
    }
}