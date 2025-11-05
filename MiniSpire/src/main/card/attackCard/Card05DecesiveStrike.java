package main.card.attackCard;

import main.buff.DamageProcessor;
import main.buff.debuff.BuffVulnerable;
import main.enemy.Enemy;
import main.player.Player;

public class Card05DecesiveStrike extends AttackCard{
	
	String name = "Decisive Strike";
	String info = "Deal 15 damage; Apply 1 round Vulnerable. ";
	int cost = 2;

	@Override
	public void onPlay(Player player, Enemy enemy) {

		int baseDamage = 15;

		DamageProcessor.DamageResult result = DamageProcessor.calculateDamageToEnemy(baseDamage, enemy);
		enemy.deductHp(result.getFinalDamage());

		enemy.addBuff(new BuffVulnerable(1), 1);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getCost() {
		return cost;
	}
}