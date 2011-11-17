
public class TargetAnt extends ListAnt {

	public TargetAnt(Tile t, MyBot newparent) {
		super(t, newparent);
		this.target = t;
	}
	
	private Tile target;
	boolean forceValidTarget = true;
	
	public void setTarget(Tile t) {
		System.err.println("set target");
		if (t.equals(target)) return;
		target = t;
		calcPath();
	}
	public Tile getTarget() {
		return target;
	}

	@Override
	boolean isTargetNode(Node n) {
		return n.isSamePosition(target);
	}

	@Override
	boolean isValidNode(Node n) {
		return parent.getIlk(n).isPassable();
	}

	@Override
	void fallbackOrder() {
		// just stay where you are
	}

}
