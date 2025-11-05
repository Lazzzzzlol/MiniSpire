package main.card.attackCard;

import main.buff.DamageProcessor;
import main.enemy.Enemy;
import main.player.Player;

public class Card06LifeDrain extends AttackCard{
	
	String name = "Life Drain";
	String info = "Deal 6 damage; Heal for the damage dealt. ";
	int cost = 2;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {

		int baseDamage = 12;

		DamageProcessor.DamageResult damageResult = DamageProcessor.calculateDamageToEnemy(baseDamage, enemy);
		int finalDamage = damageResult.getFinalDamage();

		enemy.deductHp(finalDamage);

		if (finalDamage > 0) {
			player.addHp(finalDamage);
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
}