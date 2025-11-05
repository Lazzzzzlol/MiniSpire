package main.card.attackCard;

import main.buff.DamageCalculator;
import main.enemy.Enemy;
import main.player.Player;

public class Card26Stratagem extends AttackCard{
	
	String name = "Stratagem";
	String info = "During this round, when damage is dealt, gain 1 cost, draw 1 card. Apply 1 round of Vulnerable; Deal 8 damage. ";
	int cost = 3;
	
	@Override
	public void onPlay(Player player, Enemy enemy) {

	}
	
	@Override
	public String getName() {
		return name;
	}
}