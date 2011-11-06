import java.util.ArrayList;


public class RandomAnt extends Ant {

	public RandomAnt(Tile t, Map map) {
		super(t, map);
	}

	public RandomAnt(int row, int col, Map map) {
		super(row, col, map);
	}

	@Override
	public void calcNextStep() {
		ArrayList<Aim> aimlist = new ArrayList<Aim>();
    	for (Aim direction: Aim.values()) {
    		Ilk i = map.getIlk(this, direction);
    		if (!i.isMyAntOrWater()) aimlist.add(direction);
    	}
    	Aim movedirection = aimlist.get(getRandom(aimlist.size()));
    	issueOrder(movedirection);
	}
    
    public static int getRandom(int arrlength) {
    	if (arrlength == 0) return 0;
    	return (int) Math.round(Math.random()*(arrlength-1));
    }
}
