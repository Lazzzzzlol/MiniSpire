package main.card.effectCard;

import main.buff.positiveBuff.BuffBloodLeeching;
import main.enemy.Enemy;
import main.player.Player;

public class Card08Bloodbath extends EffectCard{
    
    public Card08Bloodbath() {
        this.name = "Bloodbath";
        this.info = "Apply 1 round BloodLeeching to self.";
        this.rarity = "normal";
        this.cost = 0;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {
        player.addBuff(new BuffBloodLeeching(1), 1);
    }
}