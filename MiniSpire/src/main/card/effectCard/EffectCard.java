package main.card.effectCard;

import main.card.Card;
import main.enemy.Enemy;
import main.player.Player;

public abstract class EffectCard extends Card {
    
    public EffectCard() {
        this.type = "Effect";
        this.canPlay = true;
    }
    
    public abstract void onUse(Player player, Enemy enemy);
    
    public void onPlay(Player player, Enemy enemy) {
        onUse(player, enemy);
    }
}