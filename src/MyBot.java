import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Starter bot implementation.
 */
public class MyBot extends Bot {
    public static void main(String[] args) throws IOException {
        new MyBot().readSystemInput();
    }
    
    private Ilk currentMap[][];
    private int currentMapStep[][];
    public static int MAXSTEPS = 100000;
    public static boolean STEP_IMPACT_ENABLED = true;
    public static int STEP_INPACT = 6;
    private List<Ant> myAnts = new ArrayList<Ant>();
    private List<MyHill> myHills = new ArrayList<MyHill>();
    
    //private Ant usedAnt = new RandomAnt(new Tile(0,0), this);
    
    public void setup(int loadTime, int turnTime, int rows, int cols, int turns, int viewRadius2, int attackRadius2, int spawnRadius2) {
    	super.setup(loadTime, turnTime, rows, cols, turns, viewRadius2, attackRadius2, spawnRadius2);
    	currentMapStep = new int[rows][cols];
    	currentMap = getAnts().copyMap();
    	for (int[] row: currentMapStep) {
    		for (int col: row) {
    			row[col] = 0;
    		}
    	}
    	for (Tile hill: getAnts().getMyHills()) {
    		myHills.add(new NoDefHill(hill, this));
    		currentMapStep[hill.row][hill.col] = MAXSTEPS;
    	}
    }

    @Override
    public void doTurn() {
    	//System.err.println("doTurn");
        calculateNextStep();
    }

    private void calculateNextStep() {
    	//System.err.println("calculate step");
		for (Ant a: myAnts) {
			if (preventTimeout()) return;
			a.calcNextStep();
		}
	}
    
    private boolean preventTimeout() {
    	if (getAnts().getTimeRemaining() < 5) {
			System.err.println("Prevent Timeout.");
			return true;
		}
    	return false;
    }
    
	public void beforeUpdate() {
		//System.err.println("before update");
    	super.beforeUpdate();
    }
    public void afterUpdate() {
    	//System.err.println("after update");
    	super.afterUpdate();
    	// copy map
		this.currentMap = getAnts().copyMap();
		// remove killed ants, eaten food and destroyed hills
		synchronize();
    	//System.err.println("after update");
    }

	private void synchronize() {
		// TODO  remove destroyed hills
		ArrayList<MyHill> removehilllist = new ArrayList<MyHill>();
		for (MyHill h: myHills) {
			if (h.checkDestroy()) {
				removehilllist.add(h);
				continue;
			}
			h.update();
		}
		for (MyHill r: removehilllist) {myHills.remove(r);}
		
		ArrayList<Ant> newlist = new ArrayList<Ant>();
		for (Tile t: getAnts().getMyAnts()) {
			for (Ant a: myAnts) {
				if (a.isSamePosition(t)) {
					newlist.add(a);
					break;
				}
			}
		}
		this.myAnts = newlist;
	}
	
	public void addAnt(int row, int col, int owner) {
		super.addAnt(row, col, owner);
		Tile newt = new Tile(row, col);
		// Specify species
		if (owner == 0) {
			for (Ant a: myAnts) {
				if (a.isSamePosition(newt)) return;
			}
			boolean addedDefAntSuccessfully = false;
			if (shouldAddDefAnt()) {
				addedDefAntSuccessfully = addDefenceAnt(newt);
			}
			if (!addedDefAntSuccessfully) myAnts.add(new EliteAnt(newt, this));
			/* It works a little bit
			Object[] params = new Object[2];
			params[0] = new Tile(row, col);
			params[1] = this;
			try {
				myAnts.add((Ant) usedAnt.getClass().getConstructors()[0].newInstance(params));
			} catch (IllegalArgumentException e) {
			} catch (SecurityException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			*/
		}
	}
	
	private boolean shouldAddDefAnt() {
		int def = countDefenceAnts();
		int elite = myAnts.size() - def;
		return (def+1) * 5 < elite;
	}
	
	public void addHill(int row, int col, int owner) {
		super.addHill(row, col, owner);
		Tile nh = new Tile(row, col);
		if (owner == 0) {
			for (MyHill h: myHills) {
				if (nh.isSamePosition(h)) return;
			}
			myHills.add(new StaticDefHill(nh, this));
		}
	}
	
	// Hills
	public void registerAnt(Ant a) {
		myAnts.add(a);
	}
	public void deregisterAnt(Ant a) {
		myAnts.remove(a);
	}
	public int countDefenceAnts() {
		int re = 0;
		for (MyHill h: myHills) {
			re += h.usedAnts();
		}
		return re;
	}
	public boolean addDefenceAnt(Tile t) {
		// at program start
		if (myHills.size() == 0) return false;
		MyHill achill = null;
		for (MyHill h: myHills) {
			if (t.isSamePosition(h)) {
				achill = h;
				break;
			}
		}
		if (achill == null || !achill.needMoreAnts()) return false;
		achill.addAnt(t);
		return true;
	}

	public void issueOrder(Ant ant, Aim direction) {
		Tile newtile = getTile(ant, direction);
		stepOn(newtile);
		setIlk(ant, Ilk.LAND);
		setIlk(newtile, Ilk.MY_ANT);
		getAnts().issueOrder(ant, direction);
	}

	// Map
	public int getRows() {
		return getAnts().getRows();
	}
	public int getCols() {
		return getAnts().getCols();
	}
	// Ilk
    public Ilk getIlk(Tile tile) {
        return currentMap[tile.getRow()][tile.getCol()];
    }
    public Ilk getIlk(Tile tile, Aim direction) {
        Tile newTile = getTile(tile, direction);
        return currentMap[newTile.getRow()][newTile.getCol()];
    }
    public void setIlk(Tile tile, Ilk newilk) {
    	currentMap[tile.getRow()][tile.getCol()] = newilk;
    }
    public boolean isEnemyHill(Tile t) {
    	for (Tile h: getAnts().getEnemyHills()) {
    		if (t.equals(h)) return true;
    	}
    	return false;
    }
    public boolean isMyHillDestroyed(MyHill h) {
    	for (Tile t: getAnts().getMyHills()) {
    		if (h.isSamePosition(t)) return false;
    	}
    	return true;
    }
    // Tile
    public Tile getTile(Tile tile, Aim direction) {
        return getAnts().getTile(tile, direction);
    }
	public Tile getTile(Ant ant, Aim direction) {
		return getAnts().getTile(ant, direction);
	}
    // Aim
    public List<Aim> getDirection(Tile source, Tile target) {
    	return getAnts().getDirections(source, target);
    }
    // Stepcount
    public int getStepCount(Tile t) {
    	return currentMapStep[t.getRow()][t.getCol()];
    }
    public void stepOn(Tile t) {
    	if (MyBot.STEP_IMPACT_ENABLED) {
    		stepOnImpact(t);
    	} else {
    		currentMapStep[t.getRow()][t.getCol()]++;
    	}
    }
    private void stepOnImpact(Tile t) {
    	LinkedList<Node> openlist = new LinkedList<Node>();
    	LinkedList<Node> closedlist = new LinkedList<Node>();
    	openlist.add(new Node(t, null, MyBot.STEP_INPACT));
    	while(!openlist.isEmpty()) {
    		Node currentTile = openlist.removeFirst();
    		currentMapStep[currentTile.getRow()][currentTile.getCol()] += currentTile.getStepcount();
    		currentTile.setStepcount(currentTile.getStepcount()-2);
    		if (currentTile.getStepcount() > 0) {
    			for (Node n: ListAnt.getSuccessors(currentTile, this)) {
    				if (closedlist.contains(n)) continue;
    				n.setStepcount(currentTile.getStepcount());
    				openlist.add(n);
    			}
    		}
    		closedlist.add(currentTile);
    	}
    }
}
