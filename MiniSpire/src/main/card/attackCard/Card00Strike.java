package main.card.attackCard;

import main.buff.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card00Strike extends AttackCard{
	
	String name = "Strike";
	String info = "Deal 8 damage. ";
	int cost = 1;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {

		int baseDamage = 8;

		DamageProcessor.DamageResult result = DamageProcessor.calculateDamageToEnemy(baseDamage, enemy);
		enemy.deductHp(result.getFinalDamage());
	}
	
	@Override
	public String getName() {
		return name;
	}
}