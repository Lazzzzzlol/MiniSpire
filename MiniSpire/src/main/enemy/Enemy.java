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
	
	public Enemy(String name, int hp) {
		this.hp = hp;
		this.initialHp = hp;
		this.name = name;
		this.buffList = new ArrayList<Buff>();
	}
	
	public void onMove() {};

	public void onEndTurn() {
		
	    Iterator<Buff> it = buffList.iterator();
	    
	    while (it.hasNext()) {
	        Buff buff = it.next();
	        buff.onEndTurn();
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
		
		this.hp -= damage;
		if (this.hp < 0)
			this.hp = 0;
		
		Main.executor.schedule(() -> {
			System.out.println(" >> " + this.name + " takes " + damage + " damage.");
		}, 1, TimeUnit.SECONDS);
	}
	
	public void healHp(int heal) {
		
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
