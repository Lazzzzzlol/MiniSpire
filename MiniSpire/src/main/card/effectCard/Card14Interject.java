package main.card.effectCard;

import main.buff.debuff.BuffMuted;
import main.enemy.Enemy;
import main.player.Player;
import main.*; //use to trigger warning, delete it after fill onUse()

public class Card14Interject extends EffectCard{
    
    public Card14Interject() {
        this.name = "Interject";
        this.info = "Makes enemy fail to apply any buff to any target in next round.";
        this.rarity = "rare";
        this.cost = 1;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {
        enemy.addBuff(new BuffMuted(1), 1);
    }
}