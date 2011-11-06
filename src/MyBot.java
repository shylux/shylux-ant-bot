import java.io.IOException;
import java.util.ArrayList;

/**
 * Starter bot implementation.
 */
public class MyBot extends Bot {
	
	Map ants;
	Ilk tmpmap[][];
	
    /**
     * Main method executed by the game engine for starting the bot.
     * @param args command line arguments
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        new MyBot().readSystemInput();
    }
    
    /**
     * For every ant check every direction in fixed order (N, E, S, W) and move it if the tile is
     * passable.
     */
    @Override
    public void doTurn() {
        this.ants = (Map) getAnts();
        ants.update();
        for (Tile myAnt : ants.getMyAnts()) {
        	this.findFoodv1(myAnt);
        }
    }
    
    public static int getRandom(int arrlength) {
    	return (int) Math.round(Math.random()*(arrlength-1));
    }
    
    public void findFoodv1(Tile ant) {
    	for (Aim direction : Aim.values()) {
    		Tile t = ants.getTile(ant, direction);
    		for (Aim direction2 : Aim.values()) {
    			if (ants.getIlk(t, direction2).isFood()) {
    				ants.issueOrder(ant, direction);
    				return;
    			}
    		}
    	}
    	lowestStepNoCollNoWater(ant);
    }
    
    public void randomStepNoCollNoWater(Tile ant) {
    	ArrayList<Aim> aimlist = new ArrayList<Aim>();
    	for (Aim direction: Aim.values()) {
    		Ilk i = ants.getIlk(ant, direction);
    		if (!i.isMyAntOrWater()) aimlist.add(direction);
    	}
    	Aim movedirection = aimlist.get(getRandom(aimlist.size()));
    	ants.issueOrder(ant, movedirection);
    }
    
    public void lowestStepNoCollNoWater(Tile ant) {
    	ArrayList<Aim> aimlist = new ArrayList<Aim>();
    	for (Aim direction: Aim.values()) {
    		Ilk i = ants.getIlk(ant, direction);
    		if (!i.isMyAntOrWater()) aimlist.add(direction);
    	}
    	ants.issueOrder(ant, ants.getLowestAim(ant, aimlist));
    }
}
