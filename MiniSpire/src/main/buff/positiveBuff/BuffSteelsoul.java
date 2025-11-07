package main.buff.positiveBuff;

import main.buff.Buff;

public class BuffSteelsoul implements Buff {

	String name = "Steelsoul";
	int duration = 0;
	int absorbedDamage = 0;
	
	public BuffSteelsoul(int duration) {
		this.duration = duration;
	}
	
	@Override
	public void onEndTurn() {
		this.duration -= 1;
	}
	
	@Override
	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public int getDuration() {
		return this.duration;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void extendDuration(int duration) {
		this.duration += duration;
	}
	
	public void addAbsorbedDamage(int damage) {
		this.absorbedDamage += damage;
	}
	
	public int getAbsorbedDamage() {
		return absorbedDamage;
	}
	
	public void resetAbsorbedDamage() {
		this.absorbedDamage = 0;
	}
}

