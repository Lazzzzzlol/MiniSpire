package main;

import java.util.concurrent.ScheduledExecutorService;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Random;

import main.game.Game;
import main.node.*;

public class Main {
	
	public static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	public static final Scanner scanner = new Scanner(System.in);
	public static final Random random = new Random();
	
	public static final String longLine = "===----------------------------------------------------------------------===";
	public static final String mediumLine = "===------------------------------------------===";
	
	public static void main(String[] args) throws InterruptedException {
		
		//inti
		Game game = Game.getInstance();
		game.init();
		
		Boolean isGameOver = game.getIsGameOver();
		String userInput = "";
		
		printGameBeginMessage(executor);
		
		//input s to start
		while (!userInput.equals("s")) {
			userInput = scanner.nextLine();
		}
		
		//main loop
		while (!isGameOver) {
			
			//onUpdate - for data process
			game.setIsEndTurn(false);
			game.onUpdate();
			
			if (game.getIsGameOver())
	        	break;
			
			//onDraw - print user interface
			game.onDraw();
			
			//onInupt - get user inputs

	        while (!game.getIsEndTurn()) {
	        	
		        userInput = scanner.nextLine();
		        
		        while (!game.isValidInput(userInput)) {

		            /* System.out.println(" Invalid input. | Play card - p 1 | Get info - i 1 | End turn - e |");
		            System.out.print("Action >> ");
		            userInput = scanner.nextLine(); */

					Node currentNode = game.getCurrentNode();
					if (currentNode instanceof NodeBattle) {
						NodeBattle battleNode = (NodeBattle) currentNode;
						if (battleNode.getIsWin()) {
							System.out.println(" Invalid input. | Choose card or action - c 1 |");
						} else {
							System.out.println(" Invalid input. | Play card - p 1 | Get info - i 1 | End turn - e |");
						}
					} else if (currentNode instanceof NodeSanctuary) {
						System.out.println(" Invalid input. | Choose action - c 1 |");
					} else if (currentNode instanceof NodeShop) {
						System.out.println(" Invalid input. | Buy - b 1 | Refresh - r 1 | Remove - r 2 | Check - c 1 | Leave - l 1 |");
					} else {
						System.out.println(" Invalid input. ");
					}
					System.out.print("Action >> ");
					userInput = scanner.nextLine();
		        }
	        	
		        game.onInput(userInput);
		
		        if (!userInput.equals("e"))
		        	game.onDraw();
		        else
		        	game.setIsEndTurn(true);
		
		        if (game.getIsGameOver())
		        	break;
	        }
		}
		
		//gameover	
		
		Thread.sleep(1000);
		Util.printBlankLines(30);
		
		if (game.getIsVictory())
			while (!game.getBossSpeechDone())
				Thread.sleep(100);

		printGameOverMessage(executor, game);
		scanner.close();
	}

	//begin message
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
		logo.add("                                               888               |~~        ");
		logo.add("                          -+)                  888               ^          ");
		logo.add("                           |                   888              / `>        ");
		logo.add("                         O/                                    <   |        ");
		logo.add("                        /|                                     |   `>       ");
		logo.add("              __________/ >__________                          /     |      ");
		logo.add("         ___/                        |                       _/       |     ");

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
	
	//end message
	private static void printGameOverMessage(ScheduledExecutorService executor, Game game) {

		if (game.getIsVictory()) {
			
			System.out.println(mediumLine);
			
			executor.schedule(() -> {
				System.out.print("   You returned with victory.");
			}, 1, TimeUnit.SECONDS);
			
			executor.schedule(() -> {
				System.out.print(".");
			}, 2, TimeUnit.SECONDS);
			
			executor.schedule(() -> {
				System.out.print(".");
			}, 3, TimeUnit.SECONDS);
			
			executor.schedule(() -> {
				System.out.println(" and your dog.");
			}, 4, TimeUnit.SECONDS);
			
			executor.schedule(() -> {
				System.out.println();
				System.out.println("Score: " + game.getScore());
				System.out.println(mediumLine);
				executor.shutdown();
			}, 5, TimeUnit.SECONDS);
			
		} else {
			
			System.out.println(mediumLine);
			
			executor.schedule(() -> {
				System.out.print("   You are defeated... ");
			}, 1, TimeUnit.SECONDS);
			
			executor.schedule(() -> {
				System.out.println("But will return soon.");
			}, 3, TimeUnit.SECONDS);
			
			executor.schedule(() -> {
				System.out.println();
				System.out.println(" [Score: " + game.getScore() + "]");
				System.out.println(mediumLine);
				executor.shutdown();
			}, 4, TimeUnit.SECONDS);
		}
	}

}