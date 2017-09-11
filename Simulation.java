/**
 * info1103 - assignment 3
 * Brandon Temple
 * BTEM3257
 */


public class Simulation {

	private int day;
	private String wind;
	private boolean fire;
	private double damage;
	private int pollution;
	private double totalTrees;

	private int width;
	private int height;
	private Tree[][] trees;

	/*********************
	*										 *
	*   CONSTRUCTOR(S)   *
	*										 *
	*********************/

	public Simulation () {
	}


	/***************************
	*	  											 *
	*   METHOS AND FUNCTIONS   *
	*													 *
	***************************/

	private int checkBurningNumber (Tree[][] array) {
		int sum = 0;
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				if (array[i][j].getBurning()) {
					sum++;
				}
			}
		}
		return sum;
	}

	private void generateTerrain(int seed) {

		// ###################################
		// ### DO NOT MODIFY THIS FUNCTION ###
		// ###################################

		Perlin perlin = new Perlin(seed);
		double scale = 10.0 / Math.min(width, height);

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double height = perlin.noise(x * scale, y * scale, 0.5);
				height = Math.max(0, height * 1.7 - 0.7) * 10;
				trees[y][x] = new Tree((int) height);
			}
		}
	}

	public void increaseIntensity () {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (trees[y][x].getBurning() == true) {
					if (trees[y][x].getFireIntensity() < 9) {
						trees[y][x].setFireIntensity(trees[y][x].getFireIntensity() + 1);
					}
				}
			}
		}
		return;
	}

	public void decreaseHeight () {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (trees[y][x].getBurning() == true) {
					if (trees[y][x].getFireIntensity() == 9) {
						trees[y][x].setHeight(trees[y][x].getHeight() - 1);
					}
					if (trees[y][x].getHeight() == 0) {
						trees[y][x].setBurnt(true);
						trees[y][x].setBurning(false);
					}
				}
			}
		}
		return;
	}

	public void spreadFire () {
		Tree[][] copy = new Tree[this.height][this.width];
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				copy[i][j] = new Tree(trees[i][j].getHeight());
				if (trees[i][j].getBurning() == true) {
					copy[i][j].setBurning(true);
					copy[i][j].setFireIntensity(1);
				}
			}
		}

		//System.out.println(checkBurningNumber(trees));
		//System.out.println(checkBurningNumber(copy));

		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				//System.out.println(copy[y][x].getBurning());
				if (copy[y][x].getBurning() == true) {
					switch (this.wind) {
						case "east":
							if (x == this.width - 1) {
								break;
							} else if (copy[y][x+1].getHeight() > 0 && copy[y][x+1].getFireIntensity() == 0) {
								this.trees[y][x+1].setBurning(true);
								this.trees[y][x+1].setFireIntensity(1);
							}
							break;
						case "west":
							if (x == 0) {
								break;
							} else if (copy[y][x-1].getHeight() > 0 && copy[y][x-1].getFireIntensity() == 0) {
								this.trees[y][x-1].setBurning(true);
								this.trees[y][x-1].setFireIntensity(1);
							}
							break;
						case "north":
							if (y == 0) {
								break;
							} else if (copy[y-1][x].getHeight() > 0 && copy[y-1][x].getFireIntensity() == 0) {
								this.trees[y-1][x].setBurning(true);
								this.trees[y-1][x].setFireIntensity(1);
							}
							break;
						case "south":
							if (y == this.height - 1) {
								break;
							} else if (copy[y+1][x].getHeight() > 0 && copy[y+1][x].getFireIntensity() == 0) {
								this.trees[y+1][x].setBurning(true);
								this.trees[y+1][x].setFireIntensity(1);
							}
							break;
						case "all":
							if (x == this.width - 1) {

							} else if (copy[y][x+1].getHeight() > 0 && copy[y][x+1].getFireIntensity() == 0) {
								this.trees[y][x+1].setBurning(true);
								this.trees[y][x+1].setFireIntensity(1);
							}
							if (x == 0) {

							} else if (copy[y][x-1].getHeight() > 0 && copy[y][x-1].getFireIntensity() == 0) {
								this.trees[y][x-1].setBurning(true);
								this.trees[y][x-1].setFireIntensity(1);
							}
							if (y == 0) {
							} else if (copy[y-1][x].getHeight() > 0 && copy[y-1][x].getFireIntensity() == 0) {
								this.trees[y-1][x].setBurning(true);
								this.trees[y-1][x].setFireIntensity(1);
							}
							if (y == this.height - 1) {
							} else if (copy[y+1][x].getHeight() > 0 && copy[y+1][x].getFireIntensity() == 0) {
								this.trees[y+1][x].setBurning(true);
								this.trees[y+1][x].setFireIntensity(1);
							}
							break;
						default:
							break;
					}
				}
			}
		}
	}

	public void elapseTime (int days) {
		for (int i = 1; i <= days; i++) {
			decreaseHeight();
			increaseIntensity();
			spreadFire();
			this.day++;
			calcPollution();
			calcDamage();
		}
	}

	public void init (int h, int w, int s) {
		setHeight(h);
		setWidth(w);
		this.trees = new Tree[this.height][this.width];
		day = 1;
		wind = "none";
		fire = false;
		pollution = 0;
		generateTerrain(s);
		this.totalTrees = 0;
		calcTotal();
	}

	public void calcTotal() {
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				if (trees[y][x].getHeight() > 0) {
					this.totalTrees++;
				}
			}
		}
	}

	public void draw (String arg) {
		String border = "+";
		for (int i = 0; i < width; i++) {
			if (i != width - 1) {
				border += "--";
			} else {
				border += "-";
			}
		}
		border += "+";
		System.out.println(border);
		if (arg.equals("h")) {
			for (int i = 0; i < this.height; i++) {
				String line = "|";
      	for (int j = 0; j < this.width; j++) {
					if (trees[i][j].getHeight() == 0 && trees[i][j].getBurnt() == false) {
						line += " ";
					} else if (trees[i][j].getBurnt() == true) {
						line += "x";
					}
					else {
        		line += trees[i][j].getHeight();
					}
					if (j != width - 1) {
						line += " ";
					}
      	}
				line += "|";
				System.out.println(line);
			}
    } else if (arg.equals("f")) {
			for (int i = 0; i < this.height; i++) {
				String line = "|";
				for (int j = 0; j < this.width; j++) {
					if (trees[i][j].getHeight() == 0 && trees[i][j].getBurnt() == false) {
						line += " ";
					} else if (trees[i][j].getBurnt() == true) {
						line += "x";
					} else {
						if (trees[i][j].getFireIntensity() == 0) {
							line += ".";
						} else if (trees[i][j].getFireIntensity() > 0) {
							line += trees[i][j].getFireIntensity();
						}
					}
					if (j != width - 1) {
						line += " ";
					}
				}
				line += "|";
				System.out.println(line);
			}
		}
		System.out.println(border);
	}

	public void burnTree (String coord1, String coord2) {
		int y = 0;
		int x = 0;
		try {
			x = Integer.parseInt(coord1);
			y = Integer.parseInt(coord2);
		} catch (NumberFormatException e) {
			System.out.println("Invalid command");
			return;
		}

		if (y >= this.height || x >= this.width || x < 0 || y < 0) {
			System.out.println("Invalid command");
			return;
		} else if (trees[y][x].getHeight() > 0 && trees[y][x].getBurning() == false) {
				trees[y][x].setBurning(true);
				trees[y][x].setFireIntensity(1);
				System.out.println("Started a fire");
		} else {
			System.out.println("No fires were started");
		}
		return;
	}

	public void burnRegion (String coord1, String coord2, String coord3, String coord4) {
		int y = 0;
		int x = 0;
		int a = 0;
		int b = 0;
		try {
			x = Integer.parseInt(coord1);
			y = Integer.parseInt(coord2);
			b = Integer.parseInt(coord3);
			a = Integer.parseInt(coord4);
		} catch (NumberFormatException e) {
			System.out.println("Invalid command");
			return;
		}

		if (y >= this.height || a > this.height || x >= this.width || b > this.width || y > a || x > b || x < 0 || y < 0 || a < 0 || b < 0) {
			System.out.println("Invalid command");
			return;
		}

		int height = a - y;
		int width = b - x;
		boolean fireStarted = false;

		for (int i = y; i < height; i++) {
			for (int j = x; j < width; j++) {
				if (trees[i][j].getHeight() > 0 && trees[i][j].getBurning() == false) {
					trees[i][j].setBurning(true);
					trees[i][j].setFireIntensity(1);
					fireStarted = true;
				}
			}
		}

		if (fireStarted == true) {
			System.out.println("Started a fire");
		} else {
			System.out.println("No fires were started");
		}
		return;
	}


	public void extinguish (String coord1, String coord2) {
		int y = 0;
		int x = 0;
		try {
			x = Integer.parseInt(coord1);
			y = Integer.parseInt(coord2);
		} catch (NumberFormatException e) {
			System.out.println("Invalid command");
			return;
		}

		if (y >= this.height || x >= this.width || y < 0 || x < 0) {
			System.out.println("Invalid command");
			return;
		}

		if (trees[y][x].getBurning() == true) {
			trees[y][x].setBurning(false);
			trees[y][x].setFireIntensity(0);
			System.out.println("Extinguished fires");
		} else {
			System.out.println("No fires to extinguish");
		}
		return;
	}

	public void extinguishRegion (String coord1, String coord2, String coord3, String coord4) {
		int y = 0;
		int x = 0;
		int a = 0;
		int b = 0;
		try {
			x = Integer.parseInt(coord1);
			y = Integer.parseInt(coord2);
			b = Integer.parseInt(coord3);
			a = Integer.parseInt(coord4);
		} catch (NumberFormatException e) {
			System.out.println("Invalid command");
			return;
		}

		if (y >= this.height || a > this.height || x >= this.width || b > this.width || y > a || x > b || y < 0 || x < 0 || a < 0 || b < 0) {
			System.out.println("Invalid command");
			return;
		}

		int height = a - y;
		int width = b - x;
		boolean firesExtinguished = false;

		for (int i = y; i < height; i++) {
			for (int j = x; j < width; j++) {
				if (trees[i][j].getHeight() > 0 && trees[i][j].getBurning() == true) {
					trees[i][j].setBurning(false);
					trees[i][j].setFireIntensity(0);
					firesExtinguished = true;
				}
			}
		}

		if (firesExtinguished == true) {
			System.out.println("Extinguished fires");
		} else {
			System.out.println("No fires to extinguish");
		}
		return;
	}

	public void calcPollution () {
		int poll = 0;
		int prevDay = this.pollution;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (trees[y][x].getHeight() > 0) {
					poll -= trees[y][x].getHeight();
				}
				if (trees[y][x].getFireIntensity() > 0 && trees[y][x].getBurnt() == false) {
					poll += trees[y][x].getFireIntensity();
				}
			}
		}
		poll += prevDay;
		if (poll < 0) {
			poll = 0;
		}
		this.pollution = poll;
		return;
	}

	public void calcDamage () {
		double burnt = 0;
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				if (trees[y][x].getBurnt() == true) {
					burnt++;
				}
			}
		}
		double damage = (burnt/this.totalTrees)*100;
		this.damage = damage;
		return;
	}

	/**************************
	*		                      *
	*   GETTERS AND SETTERS   *
	*                         *
	**************************/

	public int getPollution () {
		return this.pollution;
	}

	public double getDamage() {
		return this.damage;
	}

	public void setHeight (int height) {
		this.height = height;
		return;
	}

	public int getHeight () {
		return this.height;
	}

	public void setWidth (int width) {
		this.width = width;
		return;
	}

	public int getWidth () {
		return this.width;
	}

	public void setDay (int day) {
		this.day = day;
		return;
	}

	public int getDay () {
		return this.day;
	}

	public void setWind (String wind) {
		this.wind = wind;
		return;
	}

	public String getWind () {
		return this.wind;
	}
}

