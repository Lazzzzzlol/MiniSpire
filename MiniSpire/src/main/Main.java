package main;

import java.util.concurrent.ScheduledExecutorService;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Random;

import main.game.Game;

public class Main {
	
	public static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	public static final Scanner scanner = new Scanner(System.in);
	public static final Random random = new Random();
	
	public static final String longLine = "===----------------------------------------------------------------------===";
	public static final String mediumLine = "===------------------------------------------===";
	
	public static void main(String[] args) throws InterruptedException {
		
		//初始化
		Game game = Game.getInstance();
		game.init();
		
		Boolean isGameOver = game.getIsGameOver();
		String userInput = "";
		
		printGameBeginMessage(executor);
		
		//等待用户输入开始游戏
		while (!userInput.equals("s")) {
			userInput = scanner.nextLine();
		}
		
		//游戏主循环
		while (!isGameOver) {
			
			//处理数据
			game.setIsEndTurn(false);
			game.onUpdate();
			
			if (game.getIsGameOver())
	        	break;
			
			//绘制画面
			Util.printBlankLines(3);
			game.onDraw();
			
			//读取操作

	        while (!game.getIsEndTurn()) {
	        	
		        userInput = scanner.nextLine();
		        
		        while (!game.isValidInput(userInput)) {
		            System.out.println(" Invalid input. | Play card - p 1 | Get info - i 1 | End turn - e |");
		            System.out.print("Action >> ");
		            userInput = scanner.nextLine();
		        }
	        	
		        game.onInput(userInput);
		
		        Main.executor.schedule(() -> {
					Util.printBlankLines(3);
				} , 10, TimeUnit.MILLISECONDS);
		        if (!userInput.equals("e"))
		        	game.onDraw();
		        else
		        	game.setIsEndTurn(true);
		
		        if (game.getIsGameOver())
		        	break;
	        }
		}
		
		//游戏结束		
		
		Thread.sleep(3000);
		Util.printBlankLines(30);
		
		printGameOverMessage(executor, game);
		scanner.close();
	}

	//游戏开始信息
	private static void printGameBeginMessage(ScheduledExecutorService executor) {
		
		ArrayList<String> logo = new ArrayList<>();
		logo.add(" 888b     d888 d8b           d8b     .d8888b.           d8b                 ");
		logo.add(" 8888b   d8888 Y8P           Y8P    d88P  Y88b          Y8P                 ");
		logo.add(" 88888b.d88888                      Y88b.                                   ");
		logo.add(" 888Y88888P888 888 88888b.   888     'Y888b.   88888b.  888 888d888 .d88b.  ");
		logo.add(" 888 Y888P 888 888 888  '88b 888         Y88b. 888 '88b 888 888P'  d8P  Y8b ");
		logo.add(" 888  Y8P  888 888 888   888 888          '888 888  888 888 888    88888888 ");
		logo.add(" 888   '   888 888 888   888 888    Y88b  d88P 888  888 888 888    Y8b.     ");
		logo.add(" 888       888 888 888   888 888     'Y8888P'  88888P'  888 888     'Y8888  ");
		logo.add("                                               888                          ");
		logo.add("                                               888                          ");
		logo.add("                                               888                          ");

		System.out.println(longLine);
		System.out.println();
		
		int logoCount = 0;
		for (String line : logo) {
			executor.schedule(() -> {
				System.out.println(line);
			}, logoCount * 200, TimeUnit.MILLISECONDS);
			logoCount++;
		}
		
		executor.schedule(() -> {
			System.out.println();
			System.out.println(" [Type 's' to begin...]");
			System.out.println();
			System.out.println(longLine);
		}, logo.size() * 200 + 500, TimeUnit.MILLISECONDS);
	}
	
	//游戏结束信息
	private static void printGameOverMessage(ScheduledExecutorService executor, Game game) {

		if (game.getIsVictory()) {
			
			System.out.println(mediumLine);
			
			executor.schedule(() -> {
				System.out.print("    You returned with victory.");
			}, 1, TimeUnit.SECONDS);
			
			executor.schedule(() -> {
				System.out.print(".");
			}, 2, TimeUnit.SECONDS);
			
			executor.schedule(() -> {
				System.out.print(".");
			}, 3, TimeUnit.SECONDS);
			
			executor.schedule(() -> {
				System.out.println("and your dog.");
			}, 4, TimeUnit.SECONDS);
			
			executor.schedule(() -> {
				System.out.println("                    Score: " + game.getScore());
				System.out.println(mediumLine);
				executor.shutdown();
			}, 5, TimeUnit.SECONDS);
			
		} else {
			
			System.out.println(mediumLine);
			
			executor.schedule(() -> {
				System.out.print("      You defeated...");
			}, 1, TimeUnit.SECONDS);
			
			executor.schedule(() -> {
				System.out.println("But will return soon.");
			}, 3, TimeUnit.SECONDS);
			
			executor.schedule(() -> {
				System.out.println("                    Score: " + game.getScore());
				System.out.println(mediumLine);
				executor.shutdown();
			}, 4, TimeUnit.SECONDS);
		}
	}

}