package main.card.attackCard;

import main.buff.DamageProcessor;
import main.buff.debuff.BuffWeakened;
import main.enemy.Enemy;
import main.player.Player;

public class Card01Onslaught extends AttackCard{

	String name = "Onslaught";
	String info = "Deal 8 damage; Apply 1 round Weakened. ";
	String rarity = "normal";
	int cost = 1;
	int baseDamage = 6;

	@Override
	public void onPlay(Player player, Enemy enemy) {

		DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
		
		enemy.addBuff(new BuffWeakened(1), 1);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getBaseDamage(){
		return baseDamage;
	}
}