package main.card.effectCard;

import main.buff.positiveBuff.BuffStrengthened;
import main.enemy.Enemy;
import main.player.Player;

public class Card11Rage extends EffectCard{
    
    public Card11Rage() {
        this.name = "Rage";
        this.info = "Apply 2 round Strengthened to self; Draw 1 card.";
        this.rarity = "rare";
        this.cost = 1;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {
        player.addBuff(new BuffStrengthened(2), 2);
        player.drawHandCards(2, null);
    }
}