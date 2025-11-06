package main.card.attackCard;

import main.buff.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card04AbdomenTear extends AttackCard{
	
	String name = "Abdomen Tear";
	String info = "Deal 12 damage; Take 3 damage. ";
	String rarity = "normal";
	int cost = 1;
	int baseDamage = 12;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {
		DamageProcessor.applyDamageToEnemy(baseDamage, enemy);

		int baseSelfDamage = 3;
		DamageProcessor.applyDamageToPlayer(baseSelfDamage, player);
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