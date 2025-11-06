package main.card.attackCard;

import main.buff.DamageProcessor;
import main.buff.debuff.BuffVulnerable;
import main.enemy.Enemy;
import main.player.Player;

public class Card05DecesiveStrike extends AttackCard{
	
	String name = "Decisive Strike";
	String info = "Deal 15 damage; Apply 1 round Vulnerable. ";
	String rarity = "rare";
	int cost = 2;
	int baseDamage = 15;

	@Override
	public void onPlay(Player player, Enemy enemy) {

		DamageProcessor.applyDamageToEnemy(baseDamage, enemy);

		enemy.addBuff(new BuffVulnerable(1), 1);
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
	public int getCost() {
		return cost;
	}

	@Override
	public int getBaseDamage(){
		return baseDamage;
	}
}