package main.node;

import java.util.ArrayList;

import main.Main;

public class NodeDecider {
	
	
	private static NodeDecider INSTANCE = null;
	
	public static NodeDecider getInstance() {
		if (INSTANCE == null)
			INSTANCE = new NodeDecider();
		return INSTANCE;
	}
	
	public Node decideNode(int index, ArrayList<Node> nodeHistory, int eliteEncounterCount) {
		
		switch (index) {
			case 1:
				return new NodeBattle("normal");
				
			case 2:
				System.out.println("Generating node 2");
				switch (Main.random.nextInt(2)) {
					case 0:
						System.out.println("Generating battle");
						return new NodeBattle("normal");
					case 1:
						System.out.println("Generating sanctuary");
						return new NodeSanctuary();
				}
				
			case 3:
				if (nodeHistory.get(1).getClass() == NodeSanctuary.class)
					return new NodeBattle("normal");
				else
					return new NodeSanctuary();
				
			case 4:
				return new NodeShop();
				
			case 5:
				switch (Main.random.nextInt(10)) {
					case 0:
						return new NodeBattle("elite");
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
					case 9:
						return new NodeBattle("normal");
				}
				
			case 6:
				switch (Main.random.nextInt(10)) {
					case 0:
					case 1:
						return new NodeBattle("elite");
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
					case 9:
						return new NodeBattle("normal");
			}
				
			case 7:
				return new NodeSanctuary();
				
			case 8:
				if (eliteEncounterCount < 1)
					return new NodeBattle("elite");
				else
					switch (Main.random.nextInt(10)) {
						case 0:
						case 1:
						case 2:
							return new NodeBattle("elite");
						case 3:
						case 4:
						case 5:
						case 6:
						case 7:
						case 8:
						case 9:
							return new NodeBattle("normal");
					}
				
			case 9:
				if (eliteEncounterCount < 2)
					return new NodeBattle("elite");
				else
					switch (Main.random.nextInt(2)) {
						case 0:
							switch (Main.random.nextInt(10)) {
							case 0:
							case 1:
							case 2:
							case 3:
							case 4:
								return new NodeBattle("elite");
							case 5:
							case 6:
							case 7:
							case 8:
							case 9:
								return new NodeBattle("normal");
							}
						case 1:
							return new NodeShop();
				}
				
			case 10:
				if (nodeHistory.get(8).getClass() == NodeBattle.class)
					return new NodeShop();
				else
					if (eliteEncounterCount < 2)
						return new NodeBattle("elite");
					else
						switch (Main.random.nextInt(2)) {
						case 0:
							switch (Main.random.nextInt(10)) {
							case 0:
							case 1:
							case 2:
							case 3:
							case 4:
								return new NodeBattle("elite");
							case 5:
							case 6:
							case 7:
							case 8:
							case 9:
								return new NodeBattle("normal");
							}
						}
				
			case 11:
				return new NodeSanctuary();
				
			case 12:
				return new NodeBoss();
				
			default:
				throw new IllegalArgumentException("Invalid node index: " + index);
		}
	}
}
