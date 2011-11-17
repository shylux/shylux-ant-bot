abstract class MyHill extends Tile {
	MyBot parent;
	
	public MyHill(Tile t, MyBot parent) {
		super(t.getRow(), t.getCol());
		this.parent = parent;
	}
	
	public boolean checkDestroy() {
		if (parent.isMyHillDestroyed(this)) {
			cleanupHill();
			return true;
		}
		return false;
	}
	
	abstract boolean needMoreAnts();
	abstract void addAnt(Tile t);
	abstract protected void cleanupHill();
	abstract void update();
	abstract int usedAnts();
}
