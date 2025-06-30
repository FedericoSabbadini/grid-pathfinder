package it.unibs.algoritmiPRJ.compito3;

import it.unibs.algoritmiPRJ.compito1.Cell;

public class Landmark {
    public final Cell cell;
    public final int type;

    public Landmark(Cell cell, int type) {
        this.cell = cell;
        this.type = type;
    }
    
    // Getters
    public int getRow() {
		return cell.getRow();
	}
    public int getCol() {
    	return cell.getCol();
    }
    public Cell getCell() {
		return cell;
	}
    public int getType() {
    	return type;
    }

    @Override
    public String toString() {
        return "(" + cell.toString() + ": " + type + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Landmark)) return false;
		Landmark other = (Landmark) obj;
		return cell.equals(other.cell) && type == other.type;
	}
    
    @Override
	public int hashCode() {
		return 31 * cell.hashCode() + type;
	}
}