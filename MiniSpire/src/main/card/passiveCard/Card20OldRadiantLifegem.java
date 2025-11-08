package main.card.passiveCard;

import main.buff.HealProcessor;
import main.buff.positiveBuff.BuffRecovering;
import main.player.Player;

public class Card20OldRadiantLifegem extends PassiveCard{
	
	Boolean canPlay = false;
	String name = "Old Radiant Lifegem";
	String info = "When discarded, heal 12 hp and apply 3 round Recovering to self;";
	String rarity = "rare";
	int cost = 0;
	
	@Override
	public void onDiscard() {

		Player player = Player.getInstance();

		int finalHeal = HealProcessor.calculateHeal(player.getBuffList(), 12);
		player.addHp(finalHeal);

		player.addBuff(new BuffRecovering(3), 3);
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
