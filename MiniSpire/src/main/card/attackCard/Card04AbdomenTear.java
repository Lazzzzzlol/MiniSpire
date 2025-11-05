package main.card.attackCard;

import main.buff.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card04AbdomenTear extends AttackCard{
	
	String name = "Abdomen Tear";
	String info = "Deal 12 damage; Take 3 damage. ";
	int cost = 1;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {

		int baseDamage = 12;

		DamageProcessor.applyDamageToEnemy(baseDamage, enemy);

		int baseSelfDamage = 3;
		DamageProcessor.applyDamageToPlayer(baseSelfDamage, player);
	}
	
	@Override
	public String getName() {
		return name;
	}
}