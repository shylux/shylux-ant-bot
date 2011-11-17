

public class SmellAnt extends Ant {
	public SmellAnt(Tile t, MyBot newparent) {
		super(t, newparent);
	}
	
	@Override
	public void calcNextStep() {
    	for (Aim direction : Aim.values()) {
    		Tile t = parent.getTile(this, direction);
    		// Check a field more
    		if (parent.getIlk(t).isPassableNoSuizide()) {
	    		for (Aim direction2 : Aim.values()) {
	    			if (parent.getIlk(t, direction2) == Ilk.FOOD) {
	    				issueOrder(direction);
	    				return;
	    			}
	    		}
    		}
    	}
    	// No food found. walk random.
    	issueOrder(RandomAnt.getRandomAim(this, parent));
	}
}
