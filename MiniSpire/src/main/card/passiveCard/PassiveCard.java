package main.card.passiveCard;

import main.card.Card;
import main.enemy.Enemy;
import main.player.Player;

public abstract class PassiveCard extends Card {
    
    public PassiveCard() {
        this.type = "Passive";
        this.canPlay = false;
        this.cost = 0;
    }
    
    public void onPlay(Player player, Enemy enemy) {
        System.out.println("Passive cards cannot be played directly.");
    }
    
    public abstract void onDiscard();
}