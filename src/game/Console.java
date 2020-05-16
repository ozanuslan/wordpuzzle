package game;

import enigma.core.Enigma;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import enigma.console.TextAttributes;
import java.awt.Color;
import java.util.Scanner;

public class Console {
    private static enigma.console.Console c;
    private static KeyListener klis;
    private static int keypr; // key pressed?
    private static int rkey;

    public static final TextAttributes greenonblack = new TextAttributes(Color.GREEN);
    public static final TextAttributes redonblack = new TextAttributes(Color.RED);
    public static final TextAttributes blackongreen = new TextAttributes(Color.BLACK, Color.GREEN);
    public static final TextAttributes blackonred = new TextAttributes(Color.BLACK, Color.RED);

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

    // Returns the ascii value of the key pressed
    public static int takeKeyPress() {
        if (keypr == 1) { // if keyboard button pressed
            keypr = 0;
            return rkey;
        }
        return 0;
    }

    // Creates an instance of enigma console
    public static void getConsole(String title, int xSize, int ySize, int fontSize, int fontNo) { 
        c = Enigma.getConsole(title, xSize, ySize, fontSize, fontNo);
    }

    // Println with no textattribute
    public static void println(String str) {
        c.getTextWindow().output(str + "\n");
    }

    // Println with textattribute
    public static void println(String str, TextAttributes t) {
        c.getTextWindow().output(str + "\n", t);
    }

    // Print with no textattribute
    public static void print(String str) {
        c.getTextWindow().output(str);
    }

    // Print with textattribute
    public static void print(String str, TextAttributes t) {
        c.getTextWindow().output(str, t);
    }

    // Sets the cursor position
    public static void setCursorPosition(int x, int y) {
        c.getTextWindow().setCursorPosition(x, y);
    }

    // Clears the console
    public static void clear(){
        for(int i = 0; i < Game.WINY - 1; i++){
            for(int j = 0; j < Game.WINX; j++){
                Console.setCursorPosition(j, i);
                Console.print(" ");
            }
        }
    }

    // Reads a string and returns it
    public static String readLine(){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        sc.close();
        return s;
    }
}