package main.card.attackCard;

import main.buff.DamageProcessor;
import main.buff.positiveBuff.BuffStrengthened;
import main.buff.positiveBuff.BuffTough;
import main.enemy.Enemy;
import main.player.Player;

public class Card27InnerRelease extends AttackCard{
	
	String name = "Inner Release";
	String info = "Apply 2 round Strengthen, 2 round Tough to self, then deal 10 damage. If the card is never used in this round, cost change to 0. ";
	String rarity = "normal";
	int cost = 0;
    int baseDamage = 10;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {
        this.cost = 2;
        player.addBuff(new BuffStrengthened(2), 2);
        player.addBuff(new BuffTough(2), 2);
		DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
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