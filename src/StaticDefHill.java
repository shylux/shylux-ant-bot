import java.util.LinkedList;


public class StaticDefHill extends MyHill {

	public StaticDefHill(Tile t, MyBot parent) {
		super(t, parent);
		neededDefAnts = getOpenDefPoints().size();
	}
	
	private int neededDefAnts;
	LinkedList<TargetAnt> defAnts = new LinkedList<TargetAnt>();

	@Override
	boolean needMoreAnts() {
		return defAnts.size() < neededDefAnts;
	}

	@Override
	void addAnt(Tile t) {
		if (!needMoreAnts()) return;
		TargetAnt ta = new TargetAnt(t, parent);
		ta.setTarget(getOpenDefPoints().get(0));
		defAnts.add(ta);
		parent.registerAnt(ta);
	}

	@Override
	protected void cleanupHill() {
		for (TargetAnt a: defAnts) {
			parent.deregisterAnt(a);
		}
	}
	
	private LinkedList<Tile> getOpenDefPoints() {
		LinkedList<Tile> openlist = getDefPositions();
		for (TargetAnt a: defAnts) {
			openlist.remove(a.getTarget());
		}
		for (Tile t: getDefPositions()) {
			if (!parent.getIlk(t).isPassable()) openlist.remove(t);
		}
		return openlist;
	}
	
	private LinkedList<Tile> getDefPositions() {
		LinkedList<Tile> positions = new LinkedList<Tile>();
		positions.add(new Tile(getRow()+1, getCol()+1));
		positions.add(new Tile(getRow()-1, getCol()-1));
		positions.add(new Tile(getRow()+1, getCol()-1));
		positions.add(new Tile(getRow()-1, getCol()+1));		
		return positions;
	}

	@Override
	void update() {
		LinkedList<TargetAnt> survivors = new LinkedList<TargetAnt>();
		for (TargetAnt a: defAnts) {
			if (parent.getIlk(a) == Ilk.MY_ANT) survivors.add(a);
		}
		defAnts = survivors;
	}

	@Override
	int usedAnts() {
		return defAnts.size();
	}

}
