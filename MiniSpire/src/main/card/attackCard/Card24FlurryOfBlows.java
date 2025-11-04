package main.card.attackCard;

import main.buff.DamageCalculator;
import main.enemy.Enemy;
import main.player.Player;

public class Card24FlurryOfBlows extends AttackCard{
	
	String name = "Flurry of Blows";
	String info = " ";
	int cost = 0;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {

	}
	
	@Override
	public String getName() {
		return name;
	}
}