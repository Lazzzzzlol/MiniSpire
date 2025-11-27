package main.card.passiveCard;


import main.buff.positiveBuff.BuffMentalOverload;
import main.player.Player;

public class Card33Overdose extends PassiveCard{

    public Card33Overdose() {
        this.name = "Overdose";
        this.info = "When discarded, apply 1 round Mental Overload.";
        this.rarity = "epic";
        this.cost = 0;
    }

    @Override
    public void onDiscard() {
		Player player = Player.getInstance();
        player.addBuff(new BuffMentalOverload(1), 1);
	}
}
