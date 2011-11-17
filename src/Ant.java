


abstract class Ant extends MovTile {	
	public Ant(Tile t, MyBot newparent) {
		super(t.getRow(), t.getCol(), newparent);
	}

	public abstract void calcNextStep();
	public void issueOrder(Aim direction) {
		parent.issueOrder(this, direction);
		move(direction);
	}
	public boolean samePosition(Tile t) {
		return this.getRow() == t.getRow() && this.getCol() == t.getCol();
	}
}