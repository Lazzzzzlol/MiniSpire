package main.card.effectCard;

import main.buff.positiveBuff.BuffReflective;
import main.enemy.Enemy;
import main.player.Player;

public class Card10Comeuppance extends EffectCard{
    
    public Card10Comeuppance() {
        this.name = "Comeuppance";
        this.info = "Apply 1 round Reflective to self.";
        this.rarity = "normal";
        this.cost = 1;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {
        player.addBuff(new BuffReflective(1), 1);
    }
}