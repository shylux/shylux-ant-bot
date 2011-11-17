

import java.util.LinkedList;

public class ExploreAnt extends ListAnt {
	public ExploreAnt(Tile t, MyBot newparent) {
		super(t, newparent);
	}
	
	public static int SEARCH_RADIUS = 15;

	@Override
	boolean isTargetNode(Node n) {
		return parent.getStepCount(n) == 0;
	}

	@Override
	boolean isValidNode(Node n) {
		return parent.getIlk(n).isPassableNoSuizide() && n.getStepcount() < ExploreAnt.SEARCH_RADIUS;
	}
	
	public Node priorisateTarget(LinkedList<Node> nodelist) {
		Node bestnode = nodelist.get(0);
		for (Node n: nodelist) {
			if (parent.getStepCount(n) < parent.getStepCount(bestnode)) bestnode = n;
		}
		return bestnode;
	}

	@Override
	void fallbackOrder() {
		RandomAnt.moveRandom(this, parent);		
	}
	
	protected static LinkedList<Node> generateList(Node destination) {
		LinkedList<Node> relist;
		relist = ListAnt.generateList(destination);
		int cutpos = (int) Math.ceil(relist.size()/2);
		return (LinkedList<Node>) relist.subList(0, cutpos);
	}
}
