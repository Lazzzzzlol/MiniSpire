package main.card.effectCard;

import main.buff.debuff.BuffMuted;
import main.enemy.Enemy;
import main.player.Player;

public class Card14Interject extends EffectCard{
    
    public Card14Interject() {
        this.name = "Interject";
        this.info = "Makes enemy fail to move in next round.";
        this.rarity = "rare";
        this.cost = 1;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {
        enemy.addBuff(new BuffMuted(1), 1);
    }
}