
abstract class Ant extends Tile implements MyAnt{
	Map map;

	public Ant(Tile t, Map newmap) {
		super(t.getRow(), t.getCol());
		this.map = newmap;
	}
	public Ant(int row, int col, Map newmap) {
		super(row, col);
		this.map = newmap;
	}

	public abstract void calcNextStep();
	public void issueOrder(Aim direction) {
		map.issueOrder(this, direction);
		this.setTile(map.getTile(this, direction));
	}
}

