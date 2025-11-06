package main.card.effectCard;

import main.enemy.Enemy;
import main.player.Player;

public class Card17PrimalRend extends EffectCard{
	
	String name = "Primal Rend";
	String info = "Deal damage that same as the total damage of Attack Cards in hand cards.";
	String rarity = "epic";
	int cost = 3;
	
	@Override
	public void onUse(Player player, Enemy enemy) {
		
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