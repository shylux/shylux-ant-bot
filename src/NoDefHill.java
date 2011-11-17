
public class NoDefHill extends MyHill {

	public NoDefHill(Tile t, MyBot parent) {
		super(t, parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean needMoreAnts() {
		return false;
	}

	@Override
	void addAnt(Tile t) {}

	@Override
	protected void cleanupHill() {}

	@Override
	void update() {}

	@Override
	int usedAnts() {
		return 0;
	}

}
