package main.card.effectCard;

import main.buff.positiveBuff.BuffRecovering;
import main.enemy.Enemy;
import main.player.Player;

public class Card13Equilibrium extends EffectCard{
    
    public Card13Equilibrium() {
        this.name = "Equilibrium";
        this.info = "Heal 10 hp; Apply 5 round Recovering to self.";
        this.rarity = "rare";
        this.cost = 1;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {
        player.addHp(10);
		player.addBuff(new BuffRecovering(5), 5);
    }
}