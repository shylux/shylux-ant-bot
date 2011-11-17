


import java.util.ArrayList;
import java.util.List;


public class RandomAnt extends Ant {
	public RandomAnt(Tile t, MyBot newparent) {
		super(t, newparent);
	}

	@Override
	public void calcNextStep() {
    	issueOrder(getRandomAim(this, parent));
	}
	
	public static List<Aim> getAimlist(Tile position, MyBot parent) {
		ArrayList<Aim> aimlist = new ArrayList<Aim>();
    	for (Aim direction: Aim.values()) {
    		Ilk i = parent.getIlk(position, direction);
    		if (i.isPassableNoSuizide()) aimlist.add(direction);
    	}
    	return aimlist;
	}
	
    public static Aim getRandomAim(Tile position, MyBot parent) {
    	List<Aim> aimlist = getAimlist(position, parent);
    	return (aimlist.size() > 0) ? aimlist.get(ShUtil.getRandom(aimlist.size())): null;
    }
    
    public static void moveRandom(Ant ant, MyBot parent) {
    	List<Aim> aimlist = getAimlist(ant, parent);
    	if (aimlist.size() > 0) ant.issueOrder(aimlist.get(ShUtil.getRandom(aimlist.size())));
    }
}
