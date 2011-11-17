


public class EliteAnt extends ExploreAnt {

	public EliteAnt(Tile t, MyBot newparent) {
		super(t, newparent);
	}

	boolean isTargetNode(Node n) {
		return parent.getIlk(n) == Ilk.FOOD || parent.isEnemyHill(n);
	}
	
	void fallbackOrder() {
		RandomAnt.moveRandom(this, parent);
	}
}
