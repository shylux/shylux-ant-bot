


public class MovTile extends Tile {
	public MyBot parent;
	
    public MovTile(int row, int col, MyBot parent) {
    	super(row, col);
    	this.parent = parent;
    }
    public MovTile(Tile t, MyBot parent) {
    	this(t.getRow(), t.getCol(), parent);
    }
    
    public void setRow(int newrow) {
    	this.row = newrow;
    }
    public void setCol(int newcol) {
    	this.col = newcol;
    }
    
    public void setPosition(Tile t) {
    	this.setRow(t.getRow());
    	this.setCol(t.getCol());
    }
    
    public void move(Aim direction) {
    	int row = (this.getRow() + direction.getRowDelta()) % parent.getRows();
        if (row < 0) {
            row += parent.getRows();
        }
        int col = (this.getCol() + direction.getColDelta()) % parent.getCols();
        if (col < 0) {
            col += parent.getCols();
        }
        this.row = row;
        this.col = col;
    }
}
