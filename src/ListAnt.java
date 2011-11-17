

import java.util.LinkedList;
import java.util.List;

abstract class ListAnt extends Ant {
	public ListAnt(Tile t, MyBot newparent) {
		super(t, newparent);
	}
	
	LinkedList<Node> moveStack = new LinkedList<Node>();
	LinkedList<Node> openList;
	LinkedList<Node> closedList;

	@Override
	public void calcNextStep() {
		// Check if target and path are valid. If not calc new path.
		if (moveStack == null || moveStack.size() == 0 || !isTargetNode(moveStack.getLast())) {
			calcPath();
		}
		if (moveStack != null && moveStack.size() != 0 && (!forceValidTarget || isTargetNode(moveStack.getLast()))) {
			// Get next step
			Node nextStepTarget = moveStack.removeFirst();
			
			// Check step
			if (!parent.getIlk(nextStepTarget).isPassableNoSuizide()) {
				//System.err.println("Next step is blocked. Clear Stack.");
				moveStack.clear();
				return;
			}
			
			// Get and check the direction
			List<Aim> direction = parent.getDirection(this, nextStepTarget);
			if (direction.size() != 1) {
				System.err.println("Not one Aim in RadarAnt!");
				moveStack.addFirst(nextStepTarget);
				return;
			}
			
			issueOrder(direction.get(0));
		} else {
			fallbackOrder();
		}
	}
	
	protected LinkedList<Node> calcPath() {
		LinkedList<Node> path = generatePath();
		moveStack = path;
		return path;
	}
	
	protected LinkedList<Node> generatePath() {
		openList = new LinkedList<Node>();
		closedList = new LinkedList<Node>();
		openList.add(new Node(this, null, 0));
		while (!openList.isEmpty()) {
			Node currentNode = openList.removeFirst();
			if (isTargetNode(currentNode)) return generateList(currentNode);
			expandNode(currentNode);
			closedList.add(currentNode);
		}
		if (closedList.size() > 0) {
			Node secondtarget = priorisateTarget(closedList);
			if (secondtarget != null) return generateList(secondtarget);
		}
		return null;
	}
	
	protected void expandNode(Node currentNode) {
		for (Node successor: getSuccessors(currentNode, parent)) {
			if (!isValidNode(successor)) continue;
			if (closedList.contains(successor) || openList.contains(successor)) continue;
			openList.add(successor);
		}
	}
	
	public static List<Node> getSuccessors(Node currentNode, MyBot parent) {
		LinkedList<Node> relist = new LinkedList<Node>();
		for (Aim direction: Aim.values()) {
			relist.add(new Node(parent.getTile(currentNode, direction), currentNode));
		}
		return relist;
	}
	
	protected static LinkedList<Node> generateList(Node destination) {
		LinkedList<Node> relist = new LinkedList<Node>();
		Node currentNode = destination;
		while (currentNode.getPredecessor() != null) {
			relist.addFirst(currentNode);
			currentNode = currentNode.getPredecessor();
		}
		return relist;
	}
	
	/**
	 * Checks if the node is the searched target.
	 * If true the search will be terminated and the path will be generated.
	 * @param Node to check
	 * @return True if the target is found.
	 */
	abstract boolean isTargetNode(Node n);
	
	/**
	 * Checks if the node should be added to the possible path.
	 * @param Node to check
	 * @return True if node match the requirements otherwise false.
	 */
	abstract boolean isValidNode(Node n);
	
	/**
	 * Called if no target found and priorisateTarget returns null.
	 */
	abstract void fallbackOrder();

	/**
	 * If no target match the isTargetNode function this method is called to optionally select lower prio target.
	 * @param All nodes that match the isValidNode function. Greater than zero.
	 * @return A node from the list or null.
	 */
	public Node priorisateTarget(LinkedList<Node> nodelist) {
		return null;
	}
	
	/**
	 * Forces the Ant to regenerate the path.
	 */
	public LinkedList<Node> recalcPath() {
		return calcPath();
	}
	
	/**
	 * If true the destination node have to be a valid target node.
	 * If its not it will execute the fallback.
	 */
	protected boolean forceValidTarget = false;
}
