package main.buff.oneFightBuff;

import main.buff.Buff;

public class BuffDouble implements Buff{

	String name = "Double";
	int duration = 0;
	
	public BuffDouble(int duration) {
		this.duration = duration;
	}
	
	@Override
	public void onEndTurn() {
		this.duration -= 0;
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
		this.duration += 0;
	}
}