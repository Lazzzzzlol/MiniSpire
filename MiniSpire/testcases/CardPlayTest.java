package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.card.Card;
import main.card.attackCard.AttackCard;
import main.card.attackCard.Card25TheSinisterBlade;
import main.card.attackCard.Card26Stratagem;
import main.enemy.Enemy;
import main.player.Player;
import main.processor.DamageProcessor;
import main.buff.Buff;
import main.buff.oneRoundBuff.BuffStratagem;
import main.buff.debuff.BuffVulnerable;
import main.resourceFactory.CardFactory;
import main.game.Game;

import java.util.List;

public class CardPlayTest {

    private Player player;
    private Enemy enemy;
    private Game game;

    @Before
    public void setUp() {
        try {
            java.lang.reflect.Field instanceField = Player.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
            
            java.lang.reflect.Field gameInstanceField = Game.class.getDeclaredField("INSTANCE");
            gameInstanceField.setAccessible(true);
            gameInstanceField.set(null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        player = Player.getInstance();
        game = Game.getInstance();
        enemy = new Enemy("Test Enemy", 50, "normal");
        
        game.init();
    }

    @Test
    public void testCard25TheSinisterBladePlay() {
        // Test Card 25 as sample
        Card25TheSinisterBlade sinisterBlade = new Card25TheSinisterBlade();
        
        // Test getters and attributes
        assertEquals("The Sinister Blade", sinisterBlade.getName());
        assertEquals("Deal 6 damage, draw 1 card. If Attack Card is drawn, gain 2 cost.", sinisterBlade.getInfo());
        assertEquals(1, sinisterBlade.getCost());
        assertEquals("epic", sinisterBlade.getRarity());
        assertEquals(6, sinisterBlade.getBaseDamage());
        
        int initialEnemyHp = enemy.getHp();
        int initialHandSize = player.getHandCardList().size();
        int initialActionPoints = player.getActionPoints();

        sinisterBlade.onPlay(player, enemy);
        
        // Test damage
        assertTrue("Enemy should take damage", enemy.getHp() < initialEnemyHp);
        
        // Test draw
        assertTrue("Player should draw cards", player.getHandCardList().size() > initialHandSize);
    }

    @Test
    public void testCard26StratagemPlay() {
        // Test Card 26 as sample
        Card26Stratagem stratagem = new Card26Stratagem();
        
        // Test getters and attributes
        assertEquals("Stratagem", stratagem.getName());
        assertEquals("During this round, when damage is dealt, draw 1 card, gain 1 cost. Apply 1 round of Vulnerable; Deal 8 damage.", stratagem.getInfo());
        assertEquals(3, stratagem.getCost());
        assertEquals("legendary", stratagem.getRarity());
        assertEquals(8, stratagem.getBaseDamage());
        
        int initialEnemyHp = enemy.getHp();
        
        stratagem.onPlay(player, enemy);
        
        // Test damage
        assertEquals("Enemy should take 8 damage", initialEnemyHp - 8, enemy.getHp());
        
        // Test buff
        boolean hasStratagemBuff = player.getBuffList().stream()
                .anyMatch(buff -> "Stratagem".equals(buff.getName()));
        assertTrue("Player should have Stratagem buff", hasStratagemBuff);
        
        // Test debuff
        boolean hasVulnerableDebuff = enemy.getBuffList().stream()
                .anyMatch(buff -> "Vulnerable".equals(buff.getName()));
        assertTrue("Enemy should have Vulnerable debuff", hasVulnerableDebuff);
    }

    @Test
    public void testDamageProcessor() {
        // Test damage proccessor
        int baseDamage = 10;
        int initialEnemyHp = enemy.getHp();

        DamageProcessor.applyDamageToEnemy(baseDamage, player, enemy);
        
        // Test damage
        assertTrue("Enemy HP should decrease", enemy.getHp() < initialEnemyHp);
        assertEquals("Enemy should take correct damage", initialEnemyHp - baseDamage, enemy.getHp());
    }

    @Test
    public void testBuffApplication() {
        // Test buff application
        BuffStratagem stratagemBuff = new BuffStratagem(1);
        BuffVulnerable vulnerableDebuff = new BuffVulnerable(1);
        
        player.addBuff(stratagemBuff, 1);
        enemy.addBuff(vulnerableDebuff, 1);
        
        // Test buff existence
        assertTrue("Player should have Stratagem buff", 
                player.getBuffList().stream().anyMatch(buff -> "Stratagem".equals(buff.getName())));
        
        assertTrue("Enemy should have Vulnerable debuff", 
                enemy.getBuffList().stream().anyMatch(buff -> "Vulnerable".equals(buff.getName())));
        
        // Test buff time
        Buff playerBuff = player.getBuffList().stream()
                .filter(buff -> "Stratagem".equals(buff.getName()))
                .findFirst()
                .orElse(null);
        assertNotNull("Player buff should not be null", playerBuff);
        assertEquals("Buff duration should be 1", 1, playerBuff.getDuration());
    }

    @Test
    public void testActionPointManagement() {
        // Test Cost
        int initialActionPoints = player.getActionPoints();
        
        // Test add cost
        player.changeCurrentActionPoint(2);
        assertEquals("Action points should increase", initialActionPoints + 2, player.getActionPoints());
        
        // Test reduce cost
        player.changeCurrentActionPoint(-1);
        assertEquals("Action points should decrease", initialActionPoints + 1, player.getActionPoints());
    }

    @Test
    public void testCardDrawing() {
        // Test Draw Card
        int initialHandSize = player.getHandCardList().size();
        
        List<Card> drawnCards = player.drawHandCardsWithDetails(2, 100);
        
        // Test Draw
        assertEquals("Should draw 2 cards", 2, drawnCards.size());
        assertEquals("Hand size should increase by 2", initialHandSize + 2, player.getHandCardList().size());

        for (Card card : drawnCards) {
            assertNotNull("Drawn card should not be null", card);
            assertNotNull("Card name should not be null", card.getName());
        }
    }

    @Test
    public void testAttackCardType() {
        // Test card attributes
        Card25TheSinisterBlade sinisterBlade = new Card25TheSinisterBlade();
        
        assertEquals("Card should be Attack type", "Attack", sinisterBlade.getType());
        assertTrue("Card should be playable", sinisterBlade.getCanPlay());
        assertFalse("Card should not be disposable", sinisterBlade.getDisposable());
        assertFalse("Card should not be temporary", sinisterBlade.getTemporary());
    }
}