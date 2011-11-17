
public class Node extends Tile {
	// Vorgänger
	private Node predecessor;
	
	// Wegkosten zu diesem Node
	private int stepcount;
	
	
	public Node(int row, int col, Node pre, int stepcount) {
		super(row, col);
		this.predecessor = pre;
		this.stepcount = stepcount;
	}
	
	public Node(Tile startnode, Node pre, int stepcount) {
		this(startnode.getRow(), startnode.getCol(), pre, stepcount);
	}
	
	public Node(Tile startnode, Node pre) {
		this(startnode , pre, pre.getStepcount()+1);
	}

	public void setPredecessor(Node predecessor) {
		this.predecessor = predecessor;
	}
	public Node getPredecessor() {
		return this.predecessor;
	}
	public int getStepcount() {
		return this.stepcount;
	}
	public void setStepcount(int c) {
		this.stepcount = c;
	}
}
