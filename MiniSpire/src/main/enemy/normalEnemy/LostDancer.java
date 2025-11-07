package main.enemy.normalEnemy;

import java.util.ArrayList;

import main.Main;
import main.enemy.Enemy;

public class LostDancer extends Enemy{

    private ArrayList<String> markList;
    private boolean hasSpecialContainer = true;

    public LostDancer() {
        super("Lost Dancer", 70 + Main.random.nextInt(11));
        markList = new ArrayList<>();
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
        main.buff.DamageProcessor.applyDamageToPlayer(3 + Main.random.nextInt(2), main.player.Player.getInstance());
        if (!markList.contains("Rose"))
            markList.add("Rose");
    }

    public void risingWindmill(){
        main.buff.DamageProcessor.applyDamageToPlayer(3 + Main.random.nextInt(3), main.player.Player.getInstance());
        if (!markList.contains("Leaf"))
            markList.add("Leaf");
    }

    public void bladeShower(){
        main.buff.DamageProcessor.applyDamageToPlayer(3 + Main.random.nextInt(4), main.player.Player.getInstance());
        if (!markList.contains("Crown"))
            markList.add("Crown");
    }

    public void fountainFall(){
        main.buff.DamageProcessor.applyDamageToPlayer(3 + Main.random.nextInt(5), main.player.Player.getInstance());
        if (!markList.contains("Bird"))
            markList.add("Bird");
    }

    public void flourish(){
        int markCount = markList.size();
        for (int i = 0; i <= markCount; i++)
            main.buff.DamageProcessor.applyDamageToPlayer(3 + Main.random.nextInt(2), main.player.Player.getInstance());
        markList.clear();
    }

    @Override
    public boolean getHasSpecialContainer(){
		return this.hasSpecialContainer;
	}

    @Override
    public String getSpecialContainerString(){

        String result = "[Marks: ";
        for (String mark : markList)
            result += mark + " ";
        result += "]";

        return result;
    }
}
