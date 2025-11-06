package main.card.attackCard;

import main.buff.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card24FlurryOfBlows extends AttackCard{
	
	String name = "Flurry of Blows";
	String info = "Deal 4 damage, draw 1 card. During this fight, when Effect Card is used, gain a Flurry of Blows to hand. ";
	String rarity = "legendary";
	int cost = 0;
	int baseDamage = 4;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {

	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getInfo(){
		return info;
	}

	@Override
	public int getBaseDamage(){
		return baseDamage;
	}
}