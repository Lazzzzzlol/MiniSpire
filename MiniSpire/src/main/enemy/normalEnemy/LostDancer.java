package main.enemy.normalEnemy;

import java.util.ArrayList;

import main.Main;
import main.enemy.Enemy;
import main.player.Player;

public class LostDancer extends Enemy{

    private ArrayList<String> markList;

    public LostDancer() {
        super("Lost Dancer", 70 + Main.random.nextInt(11));
    }

    public void onMove() {
		
		switch (movementCounter) {
		
			case 0:
				System.out.println(" >> " + this.getName() + " uses Reverse Cascade!");
				reverseCascade();
				movementCounter++;
				break;
				
			case 1:
				System.out.println(" >> " + this.getName() + " uses Rising Windmill!");
				risingWindmill();
				movementCounter++;
				break;
				
			case 2:
				System.out.println(" >> " + this.getName() + " uses Bladeshower!");
				bladeShower();
				movementCounter++;
				break;

            case 3:
				System.out.println(" >> " + this.getName() + " uses Foutainfall!");
				fountainFall();
				movementCounter++;
				break;

            case 4:
				System.out.println(" >> " + this.getName() + " uses Flourish!");
				flourish();
				movementCounter = 0;
				break;
				
			default:
				System.out.println(" >> " + this.getName() + " is confused.");
				movementCounter = 0;
				break;
		}
    }
    
    public void reverseCascade(){
        Player.getInstance().deductHp(3 + Main.random.nextInt(2));
        markList.add("Rose");
    }

    public void risingWindmill(){
        Player.getInstance().deductHp(3 + Main.random.nextInt(3));
        markList.add("Leaf");
    }

    public void bladeShower(){
        Player.getInstance().deductHp(3 + Main.random.nextInt(4));
        markList.add("Crown");
    }

    public void fountainFall(){
        Player.getInstance().deductHp(3 + Main.random.nextInt(5));
        markList.add("Bird");
    }

    public void flourish(){
        int markCount = markList.size();
        for (int i = 0; i <= markCount; i++)
            Player.getInstance().deductHp(4);
    }
}
