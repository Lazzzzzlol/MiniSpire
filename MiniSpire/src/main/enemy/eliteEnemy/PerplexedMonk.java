package main.enemy.eliteEnemy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import main.enemy.Enemy;
import main.player.Player;
import main.Main;
import main.buff.DamageProcessor;
import main.buff.positiveBuff.BuffTough;

public class PerplexedMonk extends Enemy {
	
	private ArrayList<String> chakraList;
	private boolean hasOpoForm = false;
	private boolean masterfulBlitzReady = false;
	private String nextMasterfulBlitz = "";
	
	public PerplexedMonk() {
		super("Perplexed Monk", 130 + Main.random.nextInt(21));
		chakraList = new ArrayList<>();
	}
	
	public void onMove() {
		
		if (masterfulBlitzReady) {
			System.out.println(" >> " + this.getName() + " uses " + nextMasterfulBlitz + "!");
			masterfulBlitz();
			masterfulBlitzReady = false;
			nextMasterfulBlitz = "";
			chakraList.clear();
			movementCounter = 0;
			return;
		}
		
		if (movementCounter < 3) {
			// Form Shift - 3 random attacks
			formShift();
			movementCounter++;
		} else if (movementCounter == 3) {
			// Masterful Blitz Ready
			System.out.println(" >> " + this.getName() + " uses Masterful Blitz Ready!");
			masterfulBlitzReady();
			movementCounter++;
		} else {
			// Should not happen, reset
			movementCounter = 0;
		}
	}
	
	private void formShift() {
		double rand = Main.random.nextDouble();
		
		if (rand < 0.30) {
			// 30% Leaping Opo
			System.out.println(" >> " + this.getName() + " uses Leaping Opo!");
			leapingOpo();
		} else if (rand < 0.50) {
			// 20% Rising Raptor
			System.out.println(" >> " + this.getName() + " uses Rising Raptor!");
			risingRaptor();
		} else {
			// 50% Pouncing Coeurl
			System.out.println(" >> " + this.getName() + " uses Pouncing Coeurl!");
			pouncingCoeurl();
		}
	}
	
	private void leapingOpo() {
		int damage = 9 + Main.random.nextInt(3);
		if (hasOpoForm) {
			damage += 5;
		}
		DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());
		if (!chakraList.contains("Opo")) {
			chakraList.add("Opo");
		}
	}
	
	private void risingRaptor() {
		int damage = 12 + Main.random.nextInt(3);
		int actualDamage = DamageProcessor.calculateDamageToPlayer(damage, Player.getInstance());
		if (actualDamage > 0) {
			DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());
			addBuff(new BuffTough(1), 2);
		}
		if (!chakraList.contains("Raptor")) {
			chakraList.add("Raptor");
		}
	}
	
	private void pouncingCoeurl() {
		int damage = 11 + Main.random.nextInt(3);
		int actualDamage = DamageProcessor.calculateDamageToPlayer(damage, Player.getInstance());
		if (actualDamage > 0) {
			DamageProcessor.applyDamageToPlayer(damage, Player.getInstance());
			hasOpoForm = true;
		}
		if (!chakraList.contains("Coeurl")) {
			chakraList.add("Coeurl");
		}
	}
	
	private void masterfulBlitzReady() {
		Set<String> uniqueChakras = new HashSet<>(chakraList);
		int chakraCount = uniqueChakras.size();
		
		if (chakraCount == 1) {
			nextMasterfulBlitz = "Tornado Kick";
		} else if (chakraCount == 2) {
			nextMasterfulBlitz = "Phantom Rush";
		} else if (chakraCount == 3) {
			nextMasterfulBlitz = "Fires Reply";
		} else {
			nextMasterfulBlitz = "Tornado Kick"; // Default
		}
		
		System.out.println(" >> Masterful Blitz Ready: " + nextMasterfulBlitz + " will be used next turn!");
		masterfulBlitzReady = true;
	}
	
	private void masterfulBlitz() {
		Set<String> uniqueChakras = new HashSet<>(chakraList);
		int chakraCount = uniqueChakras.size();
		
		if (chakraCount == 1) {
			// Tornado Kick
			DamageProcessor.applyDamageToPlayer(20, Player.getInstance());
		} else if (chakraCount == 2) {
			// Phantom Rush
			DamageProcessor.applyDamageToPlayer(30, Player.getInstance());
		} else if (chakraCount == 3) {
			// Fires Reply
			DamageProcessor.applyDamageToPlayer(50, Player.getInstance());
		} else {
			// Default
			DamageProcessor.applyDamageToPlayer(20, Player.getInstance());
		}
	}
	
	@Override
	public boolean getHasSpecialContainer() {
		return true;
	}
	
	@Override
	public String getSpecialContainerString() {
		String result = "[Chakras: ";
		Set<String> unique = new HashSet<>(chakraList);
		for (String chakra : unique) {
			result += chakra + " ";
		}
		if (hasOpoForm) {
			result += "OpoForm ";
		}
		result += "]";
		return result;
	}
}

