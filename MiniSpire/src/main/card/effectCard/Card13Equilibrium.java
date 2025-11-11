package main.card.effectCard;

import main.processor.HealProcessor;
import main.buff.positiveBuff.BuffRecovering;
import main.enemy.Enemy;
import main.player.Player;

public class Card13Equilibrium extends EffectCard{
    
    public Card13Equilibrium() {
        this.name = "Equilibrium";
        this.info = "Apply 5 round Recovering to self; Heal 10 hp. ";
        this.rarity = "rare";
        this.cost = 1;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {
		player.addBuff(new BuffRecovering(5), 5);
        HealProcessor.applyHeal(player, 10, null);
    }
}