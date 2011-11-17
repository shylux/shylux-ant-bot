
public class DefenceAnt extends TargetAnt {
	MyHill dhill;

	public DefenceAnt(Tile t, MyHill dhill, MyBot newparent) {
		super(t, newparent);
		this.dhill = dhill;
	}
}
