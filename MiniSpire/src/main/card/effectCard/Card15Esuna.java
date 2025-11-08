package main.card.effectCard;

import main.buff.positiveBuff.BuffStrengthened;
import main.enemy.Enemy;
import main.player.Player;

public class Card15Esuna extends EffectCard{
    
    public Card15Esuna() {
        this.name = "Esuna";
        this.info = "Remove all buff of self, then apply 3 round Strengthened to self.";
        this.rarity = "rare";
        this.cost = 2;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {
        player.getBuffList().clear();
		player.addBuff(new BuffStrengthened(3), 3);
    }
}