package main;

public class TextDisplay {

    public static void printCharWithDelay(String text, int delay) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println();
    }
    
    public static void printLineWithDelay(String line, int lineDelay) {
        System.out.println(line);
        try {
            Thread.sleep(lineDelay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}