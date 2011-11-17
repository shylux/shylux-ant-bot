

public class RadarAnt extends ListAnt {
	public RadarAnt(Tile t, MyBot newparent) {
		super(t, newparent);
	}
	
	public static int SEARCH_RADIUS = 30;
	
	@Override
	boolean isTargetNode(Node n) {
		return parent.getIlk(n) == Ilk.FOOD || parent.isEnemyHill(n);
	}
	@Override
	boolean isValidNode(Node n) {
		return (n.getStepcount() <= RadarAnt.SEARCH_RADIUS && parent.getIlk(n).isPassableNoSuizide());
	}
	@Override
	void fallbackOrder() {
		RandomAnt.moveRandom(this, parent);
	}
}
