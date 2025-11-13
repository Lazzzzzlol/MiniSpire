package main;

import java.util.List;

import main.buff.Buff;

public class Util {

	public static void printBlankLines(int n) {
		for (int i = 0; i < n; i++) 
			System.out.println();
	}
	
	public static int getCenterAlignSpaceNum(String text, int lineWidth) {
		return (lineWidth - text.length()) / 2;
	}

	public static String removeColorCodes(String coloredText) {
		return coloredText.replaceAll("\u001B\\[[;\\d]*m", "");
	}

	public static String getColorBuffName(List<Buff> buffList, String buffName) {
		return buffList.stream()
				.filter(buff -> buffName.equals(buff.getName()))
				.findFirst()
				.map(Buff::getColorName)
				.orElse(buffName);
	}
}
