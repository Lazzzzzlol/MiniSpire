package main.enemy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import main.Colors;
import main.Main;
import main.buff.Buff;
import main.game.Game;
import main.node.NodeBoss;
import main.player.Player;
import main.processor.HealProcessor;

public class Enemy {
	
	private String name;
	private int hp;
	private int initialHp;
	protected int movementCounter = 0;
	protected ArrayList<Buff> buffList;
	private boolean hasSpecialContainer;
	protected boolean isDied;
	private int steelsoulAbsorbedDamage = 0; // 钢魂吸收的伤害
	private String type;
	
	public Enemy(String name, int hp, String type) {
		this.hp = hp;
		this.initialHp = hp;
		this.name = name;
		this.buffList = new ArrayList<Buff>();
		this.hasSpecialContainer = false;
		this.isDied = false;
		this.type = type;
	}
	
	public void onMove() {
		if (this.getHp() <= 0 || isDied) {
			return;
		}
	};

	public void onEndTurn() {

		boolean hasRecovering = buffList.stream().anyMatch(buff -> "Recovering".equals(buff.getName()));

		if (hasRecovering) {
			Buff recoveringBuff = null;
			for (Buff buff : buffList) {
				if (buff.getName().equals("Recovering")) {
					recoveringBuff = buff;
					break;
				}
        	}
			HealProcessor.applyHeal(this, recoveringBuff.getDuration(), null);
		}
		
	    Iterator<Buff> it = buffList.iterator();
	    
	    while (it.hasNext()) {
	        Buff buff = it.next();
	        buff.onEndTurn();
	        if (buff.getDuration() == 0) {
	        	// 如果 Steelsoul 消失，返还所有吸收的伤害
	        	if ("Steelsoul".equals(buff.getName()) && steelsoulAbsorbedDamage > 0) {
	        		int damageToReturn = steelsoulAbsorbedDamage;
	        		steelsoulAbsorbedDamage = 0;
					Player.getInstance().deductHp(damageToReturn);
	        		Main.executor.schedule(() -> {
	        			System.out.println(" >> " + this.name + " returns " + damageToReturn + " absorbed damage from Steelsoul!");
	        		}, 1, TimeUnit.SECONDS);
	        	}
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
			System.out.println(" >> " + Colors.colorOnForEnemyName(this.name, this.type) + " obtains buff " + Colors.colorOnForBuff(buff.getName(), buff.getType()));
		}, 1001, TimeUnit.MILLISECONDS);
	}
	
	public int getHp() {
		return hp;
	}
	
	public void deductHp(int damage) {
		
		this.hp -= damage;
		if (this.hp < 0)
			this.hp = 0;
		
		Main.executor.schedule(() -> {
			// System.out.println(" >> " + this.name + " takes " + damage + " damage.");
			if (this.hp <= 0)
				onDie();
		}, 1, TimeUnit.SECONDS);
	}
	
	public void addHp(int heal) {
		
		this.hp += heal;
		if (this.hp > initialHp)
			this.hp = initialHp;
		
		Main.executor.schedule(() -> {
			System.out.println(" >> " + this.name + " heals " + heal + " HP ");
		}, 1, TimeUnit.SECONDS);
	}

	public void onDie(){

		if (Game.getInstance().getCurrentNode().getClass() != NodeBoss.class)
		Main.executor.schedule(() -> {
			List<String> messages = Arrays.asList(
				"dies", "is defeated", "falls", "meets the end", 
				"breathes the last", "is gone", "perishes"
			);
			Random random = new Random();
			String action = messages.get(random.nextInt(messages.size()));
			System.out.println(" >> " + this.name + " " + action);
		}, 1, TimeUnit.SECONDS);

		boolean hasResurrection = buffList.stream()
                .anyMatch(buff -> "Resurrection".equals(buff.getName()));

		if (!hasResurrection) {
			this.isDied = true;
			return;
		}

		buffList.removeIf(buff -> "Resurrection".equals(buff.getName()));
		Main.executor.schedule(() -> {
			System.out.println("\n >> " + this.name + " resurrected!");
		}, 1, TimeUnit.SECONDS);
		hp = initialHp;
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
			result += Colors.colorOnForBuff(buffList.get(i).getName(), buffList.get(i).getType()) + "(" + buffList.get(i).getDuration() + "), ";
		result += Colors.colorOnForBuff(buffList.get(buffList.size() - 1).getName(), buffList.get(buffList.size() - 1).getType()) + "(" + buffList.get(buffList.size() - 1).getDuration() + ")";
		
		return "[Buff: " + result + "]";
	}


	public Boolean getIsDied(){
		return isDied;
	}
	
	/**
	 * 吸收伤害（用于 Steelsoul）
	 */
	public void absorbDamageWithSteelsoul(int damage) {
		this.steelsoulAbsorbedDamage += damage;
	}
	
	/**
	 * 获取当前吸收的伤害
	 */
	public int getSteelsoulAbsorbedDamage() {
		return steelsoulAbsorbedDamage;
	}

	public String getType() {
		return type;
	}
}
