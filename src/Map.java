import java.util.ArrayList;


public class Map extends Ants {
	
	private Ilk acmap[][];
	private int stepcount[][];
	private final int maxstep = 100000;

	public Map(int loadTime, int turnTime, int rows, int cols, int turns, int viewRadius2, int attackRadius2, int spawnRadius2) {
		super(loadTime, turnTime, rows, cols, turns, viewRadius2, attackRadius2, spawnRadius2);
		
		this.acmap = getMap();
		
		stepcount = new int[getRows()][getCols()];
		for (int x = 0; x < getRows(); x++) {
			for (int y = 0; y < getCols(); y++) {
				stepcount[x][y] = 0;
			}
		}
		for (Tile t: getMyHills()) {
			stepcount[t.getRow()][t.getCol()] = maxstep;
		}
	}
	
	public void update() {
		acmap = getMap().clone();
	}
    
	public Tile getTile(Tile tile, Aim direction) {
        int row = (tile.getRow() + direction.getRowDelta()) % getRows();
        if (row < 0) {
            row += getRows();
        }
        int col = (tile.getCol() + direction.getColDelta()) % getCols();
        if (col < 0) {
            col += getCols();
        }
        return new Tile(row, col);
    }
	
	public Ilk getIlk(Tile tile) {
		return acmap[tile.getRow()][tile.getCol()];
	}
    public void setIlk(Tile tile, Ilk ilk) {
        acmap[tile.getRow()][tile.getCol()] = ilk;
    }
    public Ilk getIlk(Tile tile, Aim direction) {
        Tile newTile = getTile(tile, direction);
        return acmap[newTile.getRow()][newTile.getCol()];
    }
    
    // STEP STUFF
    
    public int getStepCount(int x, int y) {
    	return stepcount[x][y];
    }
    
    public Aim getLowestAim(Tile t, ArrayList<Aim> aimlist) {
    	Aim reaim = null;
    	int steps = maxstep;
    	for (Aim a: aimlist) {
    		Tile tmp = getTile(t, a);
    		if (reaim == null || steps > stepcount[tmp.getRow()][tmp.getCol()]) {
    			steps = stepcount[tmp.getRow()][tmp.getCol()];
    			reaim = a;
    		}
    	}
    	return reaim;
    }

	public void issueOrder(Tile ant, Aim direction) {
		setIlk(ant, Ilk.LAND);
		Tile newtile = getTile(ant, direction);
		setIlk(newtile, Ilk.MY_ANT);
		stepcount[newtile.getRow()][newtile.getCol()]++;
		super.issueOrder(ant, direction);
	}

}
