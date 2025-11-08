package main.card.passiveCard;

import main.buff.positiveBuff.BuffStrengthened;
import main.buff.positiveBuff.BuffTough;
import main.player.Player;

public class Card21BlessingOfTheErdtree extends PassiveCard{
	
	Boolean canPlay = false;
	String name = "Blessing of the Erdtree";
	String info = "When discarded, apply 3 round Strengthened, 3 round Tough to self. ";
	String rarity = "epic";
	int cost = 0;
	
	@Override
	public void onDiscard() {
		
		Player player = Player.getInstance();

		player.addBuff(new BuffStrengthened(3), 3);
		player.addBuff(new BuffTough(3), 3);
	}

	@Override
	public String getInfo(){
		return info;
	}
	
	@Override
	public String getName() {
		return name;
	}
}
