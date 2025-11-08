package main.card.passiveCard;

import main.buff.HealProcessor;
import main.player.Player;

public class Card19RecoveryStone extends PassiveCard{
	
	Boolean canPlay = false;
	String name = "Recovery Stone";
	String info = "When discarded, heal 6 hp. ";
	String rarity = "normal";
	int cost = 0;
	
	@Override
	public void onDiscard() {

		Player player = Player.getInstance();

		int finalHeal = HealProcessor.calculateHeal(player.getBuffList(), 6);
		player.addHp(finalHeal);
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
