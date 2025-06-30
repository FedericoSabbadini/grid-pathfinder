package it.unibs.algoritmiPRJ.compito1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cell {

	//Attributi
	private int row;
	private int col;
	
	//Costruttore
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	//Getters
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	
    @Override
    public String toString() {
        return "(" + row +  ", " + col + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Cell)) return false;
		Cell other = (Cell) obj;
		return row == other.row && col == other.col;
	}
    
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
    
    /**
     * Ottiene tutti i vicini di una cella (8-connected)
     */
    public List<Cell> getAllNeighbors() {
        List<Cell> neighbors = new ArrayList<>();
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        
        for (int i = 0; i < 8; i++) {
            neighbors.add(new Cell(getRow() + dx[i], getCol() + dy[i]));
        }
        
        return neighbors;
    }
	
}