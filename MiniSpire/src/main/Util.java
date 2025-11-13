package main;

public class Util {

	public static void printBlankLines(int n) {
		for (int i = 0; i < n; i++) 
			System.out.println();
	}
	
	public static int getCenterAlignSpaceNum(String text, int lineWidth) {
		return (lineWidth - text.length()) / 2;
	}

}
