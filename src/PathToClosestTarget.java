

import java.util.LinkedList;
import java.util.List;


@Deprecated
public class PathToClosestTarget {
	private LinkedList<Node> openlist = new LinkedList<Node>();
	private LinkedList<Node> closedlist = new LinkedList<Node>();
	private MyBot parent;
	private int radius;
	
	public PathToClosestTarget(Tile startposition, MyBot parent, int radius) {
		this.parent = parent;
		this.radius = radius;
		openlist.add(new Node(startposition, null, 0));
	}
	
	public LinkedList<Tile> calcPath() {
		while (!openlist.isEmpty()) {
			Node currentNode = openlist.removeFirst();
			if (isTarget(currentNode)) return generateList(currentNode);
			expandNode(currentNode);
			closedlist.add(currentNode);
		}
		return null;
	}
	
	public void expandNode(Node currentNode) {
		for (Node successor: getSuccessors(currentNode, parent)) {
			if (successor.getStepcount() > radius) continue;
			if (closedlist.contains(successor)) continue;
			if (openlist.contains(successor)) continue;
			if (parent.getIlk(successor).isPassableNoSuizide()) openlist.add(successor);
		}
	}

	public static List<Node> getSuccessors(Node currentNode, MyBot parent) {
		LinkedList<Node> relist = new LinkedList<Node>();
		for (Aim direction: Aim.values()) {
			relist.add(new Node(parent.getTile(currentNode, direction), currentNode));
		}
		return relist;
	}
	
	public LinkedList<Tile> generateList(Node destination) {
		LinkedList<Tile> relist = new LinkedList<Tile>();
		Node currentNode = destination;
		while (currentNode.getPredecessor() != null) {
			relist.addFirst(currentNode);
			currentNode = currentNode.getPredecessor();
		}
		return relist;
	}

	public boolean isTarget(Node n) {
		return parent.getIlk(n) == Ilk.FOOD;
	}
	
	public static List<Node> getNearNodes(Ant a, int limit, MyBot parent) {
		LinkedList<Node> openlist = new LinkedList<Node>();
    	LinkedList<Node> closedlist = new LinkedList<Node>();
    	openlist.add(new Node(a, null, 0));
    	while(!openlist.isEmpty()) {
    		Node currentNode = openlist.removeFirst();
    		if (currentNode.getStepcount() <= limit) {
    			for (Node n: PathToClosestTarget.getSuccessors(currentNode, parent)) {
    				if (closedlist.contains(n)) continue;
    				if (parent.getIlk(n).isPassableNoSuizide()) openlist.add(n);
    			}
    		}
    		closedlist.add(currentNode);
    	}
    	return closedlist;
	}
}
