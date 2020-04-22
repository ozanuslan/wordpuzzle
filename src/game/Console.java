package game;

import java.awt.Color;
import enigma.console.TextAttributes;
import enigma.core.Enigma;

public class Console {
    private static enigma.console.Console c;
    public static TextAttributes greenonblack = new TextAttributes(Color.GREEN);
    public static TextAttributes redonblack = new TextAttributes(Color.RED);
    public static TextAttributes blueonblack = new TextAttributes(Color.BLUE);

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

    public static void setCursorPosition(int x, int y){
        c.getTextWindow().setCursorPosition(x, y);
    }
}