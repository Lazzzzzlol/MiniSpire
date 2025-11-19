package main.enemy.eliteEnemy;

import java.util.HashSet;

import main.enemy.Enemy;
import main.player.Player;
import main.Main;
import main.buff.positiveBuff.BuffTough;
import main.processor.DamageProcessor;

public class PerplexedMonk extends Enemy {

	private HashSet<String> chakras = new HashSet<>();
	private boolean haveOpoStance = false;
	private boolean hasSpecialContainer = true;

	public PerplexedMonk() {
		super("Perplexed Monk", 130 + Main.random.nextInt(21),"elite");
	}

	@Override
	public boolean onMove() {

		if (!super.onMove())
			return false;

		switch (movementCounter) {
			case 0:
			case 1:
			case 2:
				System.out.println("\n >> " + this.getName() + " uses Form Shift!");
				formShiftAttack();
				movementCounter++;
				break;
			case 3:
				System.out.println(" >> " + this.getName() + " prepares Masterful Blitz!");
				movementCounter++;
				break;
			case 4:
				System.out.println("\n >> " + this.getName() + " uses Masterful Blitz!");
				masterfulBlitz();
				movementCounter = 0;
				chakras.clear();
				haveOpoStance = false;
				break;
			default:
				movementCounter = 0;
		}
		return true;
	}

	private void formShiftAttack() {
		int roll = Main.random.nextInt(100); // 0-99
		if (roll < 30) { // 30%
			leapingOpo();
			return;
		}
		if (roll < 50) { // next 20%
			risingRaptor();
			return;
		}
		pouncingCoeurl(); // remaining 50%
	}

	private void leapingOpo() {
		System.out.println(" >> Leaping Opo!");
		int dmg = 9 + Main.random.nextInt(3); // 9-11
		if (haveOpoStance) dmg += 5;
		DamageProcessor.applyDamageToPlayer(dmg, this, Player.getInstance());
		chakras.add("Opo");
		haveOpoStance = false;
	}

	private void risingRaptor() {
		System.out.println(" >> Rising Raptor!");
		int dmg = 12 + Main.random.nextInt(3); // 12-14
		DamageProcessor.applyDamageToPlayer(dmg, this, Player.getInstance());
		chakras.add("Raptor");
		addBuff(new BuffTough(2), 2);
	}

	private void pouncingCoeurl() {
		System.out.println(" >> Poucing Coeurl!");
		int dmg = 11 + Main.random.nextInt(3); // 11-13
		DamageProcessor.applyDamageToPlayer(dmg, this, Player.getInstance());
		chakras.add("Coeurl");
		haveOpoStance = true;
	}

	private void masterfulBlitz() {
		int types = chakras.size();
		if (types <= 0) {
			System.out.println(" >> (Awkward scilence...)");
		};
		if (types == 1) {
			System.out.println(" >> Tornado Kick!");
			DamageProcessor.applyDamageToPlayer(20, this, Player.getInstance());
		}
		if (types == 2) {
			System.out.println(" >> Phantom Rush!");
			DamageProcessor.applyDamageToPlayer(30, this, Player.getInstance());
		}
		if (types == 2) {
			System.out.println(" >> Fires Reply!");
			DamageProcessor.applyDamageToPlayer(50, this, Player.getInstance());
		}

		chakras.clear();
	}

	@Override
    public boolean getHasSpecialContainer(){
		return this.hasSpecialContainer;
	}

    @Override
    public String getSpecialContainerString(){

        String result = "[Chakra: ";
        for (String chakra : chakras)
            result += chakra + " ";
        result += "]";
		
		if (haveOpoStance)
			result += " (Opo Stance ready)";

        return result;
    }
}


