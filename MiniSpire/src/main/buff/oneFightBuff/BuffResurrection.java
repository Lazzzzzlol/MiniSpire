package main.buff.oneFightBuff;

import main.buff.Buff;

public class BuffResurrection implements Buff {

    String name = "Resurrection";
	int duration = 0;
	
	public BuffResurrection(int duration) {
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
