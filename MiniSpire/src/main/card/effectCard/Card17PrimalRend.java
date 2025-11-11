package main.card.effectCard;

import java.util.ArrayList;

import main.enemy.Enemy;
import main.player.Player;
import main.processor.DamageProcessor;
import main.card.Card;
import main.card.attackCard.AttackCard;

public class Card17PrimalRend extends EffectCard {
    
    public Card17PrimalRend() {
        this.name = "Primal Rend";
        this.info = "Deal damage that same as the total damage of Attack Cards in hand cards.";
        this.rarity = "epic";
        this.cost = 3;
    }

    @Override
    public void onUse(Player player, Enemy enemy) {

        int baseDamage = 0;

        ArrayList<Card> handCards = player.getHandCardList();
        for (Card card : handCards) {
            if (card.getType().equals("attack")) {
                AttackCard attackCard = (AttackCard) card;
                baseDamage += attackCard.getBaseDamage();
            }
        }

        DamageProcessor.applyDamageToEnemy(baseDamage, enemy);
	}
}