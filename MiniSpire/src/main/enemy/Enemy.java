package main.enemy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import main.Main;
import main.buff.Buff;

public class Enemy {
	
	private String name;
	private int hp;
	private int initialHp;
	protected int movementCounter = 0;
	protected ArrayList<Buff> buffList;
	private boolean hasSpecialContainer;
	
	public Enemy(String name, int hp) {
		this.hp = hp;
		this.initialHp = hp;
		this.name = name;
		this.buffList = new ArrayList<Buff>();
		this.hasSpecialContainer = false;
	}
	
	public void onMove() {};

	public void onEndTurn() {
		
	    Iterator<Buff> it = buffList.iterator();
	    
	    while (it.hasNext()) {
	        Buff buff = it.next();
	        
	        // Handle Steelsoul - return absorbed damage when removed
	        if (buff.getName().equals("Steelsoul") && buff.getDuration() == 1) {
	        	main.buff.positiveBuff.BuffSteelsoul steelsoul = (main.buff.positiveBuff.BuffSteelsoul) buff;
	        	int absorbedDamage = steelsoul.getAbsorbedDamage();
	        	if (absorbedDamage > 0) {
	        		System.out.println(" >> Steelsoul: Returning " + absorbedDamage + " absorbed damage!");
	        		deductHp(absorbedDamage);
	        		steelsoul.resetAbsorbedDamage();
	        	}
	        }
	        
	        buff.onEndTurn();
	        
	        // Handle Recovering - heal equal to remaining duration
	        if (buff.getName().equals("Recovering")) {
	        	int duration = buff.getDuration();
	        	if (duration > 0) {
	        		addHp(duration);
	        	}
	        }
	        
	        if (buff.getDuration() == 0) {
	            it.remove();
	        }
	    }
	}
	
	public void addBuff(Buff buff, int duration) {
		
		for (Buff existBuff : buffList)
			if (existBuff.getName().equals(buff.getName())) {
				existBuff.extendDuration(duration);
				return;
			}
				
		
		buffList.add(buff);
		Main.executor.schedule(() -> {
			System.out.println(" >> " + this.name + " gains buff " + buff.getName());
		}, 1, TimeUnit.SECONDS);
	}
	
	public int getHp() {
		return hp;
	}
	
	public void deductHp(int damage) {
		
		// Check for Steadfast - if damage > 15, gain Invincible next turn
		for (Buff buff : buffList) {
			if (buff.getName().equals("Steadfast") && damage > 15) {
				// Remove Steadfast and add Invincible for next turn
				buffList.remove(buff);
				addBuff(new main.buff.positiveBuff.BuffInvincible(1), 1);
				System.out.println(" >> Steadfast: Damage exceeded 15! Gained Invincible for next turn!");
				break;
			}
		}
		
		this.hp -= damage;
		
		// Check for Resurrection
		if (this.hp <= 0) {
			main.buff.oneFightBuff.BuffResurrection resBuff = null;
			for (Buff buff : buffList) {
				if (buff instanceof main.buff.oneFightBuff.BuffResurrection) {
					main.buff.oneFightBuff.BuffResurrection res = (main.buff.oneFightBuff.BuffResurrection) buff;
					if (!res.isUsed()) {
						resBuff = res;
						break;
					}
				}
			}
			
			if (resBuff != null) {
				// Resurrect
				resBuff.setUsed(true);
				buffList.remove(resBuff);
				this.hp = this.initialHp;
				System.out.println(" >> " + this.name + " resurrects with full HP!");
				return;
			}
		}
		
		if (this.hp < 0)
			this.hp = 0;
		
		Main.executor.schedule(() -> {
			System.out.println(" >> " + this.name + " takes " + damage + " damage.");
		}, 1, TimeUnit.SECONDS);
	}
	
	public void addHp(int heal) {
		
		this.hp += heal;
		if (this.hp > initialHp)
			this.hp = initialHp;
		
		Main.executor.schedule(() -> {
			System.out.println(" >> " + this.name + " heals " + heal + " HP.");
		}, 1, TimeUnit.SECONDS);
	}
	
	public int getInitialHp() {
		return initialHp;
	}
	
	public String getName() {
		return name;
	}

	public ArrayList<Buff> getBuffList() {
		return buffList;
	}

	public boolean getHasSpecialContainer(){
		return hasSpecialContainer;
	}

	public String getSpecialContainerString(){
		return "[]";
    }

	public String getBuffListString() {
		
		if (buffList.size() == 0) 
			return "[Buff: ]";
		
		String result = "";
		for (int i = 0; i < buffList.size() - 1; i++)
			result += buffList.get(i).getName() + "(" + buffList.get(i).getDuration() + "), ";
		result += buffList.get(buffList.size() - 1).getName() + "(" + buffList.get(buffList.size() - 1).getDuration() + ")";
		
		return "[Buff: " + result + "]";
	}
}
