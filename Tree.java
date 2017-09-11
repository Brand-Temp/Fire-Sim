/**
 * info1103 - assignment 3
 * Brandon Temple
 * BTEM3257
 */

public class Tree {

	private int height;
	private boolean burning;
	private boolean burnt;
	private int fireIntensity;
	private boolean spread;


	public Tree(int height) {
		this.height = height;
		this.burning = false;
		this.burnt = false;
		this.fireIntensity = 0;
		this.spread = false;
	}

	public void setHeight (int height) {
		this.height= height;
		return;
	}

	public int getHeight () {
		return this.height;
	}

	public void setBurning (boolean burning) {
		this.burning = burning;
		return;
	}

	public boolean getBurning () {
		return this.burning;
	}

	public void setBurnt (boolean burnt) {
		this.burnt = burnt;
		return;
	}

	public boolean getBurnt() {
		return this.burnt;
	}

	public int getFireIntensity () {
		return this.fireIntensity;
	}

	public void setFireIntensity (int intensity) {
		this.fireIntensity = intensity;
		return;
	}

	public boolean getSpread () {
		return this.spread;
	}

	public void setSpread (boolean spread) {
		this.spread = spread;
		return;
	}
}

