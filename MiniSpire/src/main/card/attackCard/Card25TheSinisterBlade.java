package main.card.attackCard;

import main.buff.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card25TheSinisterBlade extends AttackCard{
	
	String name = "The Sinister Blade";
	String info = "Deal 6 damage, draw 1 card. If Attack Card is drawn, gain 2 cost. ";
	String rarity = "epic";
	int cost = 1;
	int baseDamage = 6;
	
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