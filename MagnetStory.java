package application;

import java.io.Serializable;

public class MagnetStory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean alive;
	int seonds=0;
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public boolean getAlive() {
		return alive;
	}
	public int getSeonds() {
		return seonds;
	}
	public void setSeonds(int seonds) {
		this.seonds = seonds;
	}
	public MagnetStory(boolean s) {
		// TODO Auto-generated constructor stub
		alive=s;
	}
	

}
