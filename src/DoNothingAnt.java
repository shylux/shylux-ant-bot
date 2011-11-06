
public class DoNothingAnt extends Ant {

	public DoNothingAnt(int row, int col, Map map) {
		super(row, col, map);
	}

	public DoNothingAnt(Tile tile, Map map) {super(tile, map);}

	@Override
	public void calcNextStep() {}

}
