package it.unibs.algoritmiPRJ.compito2;
import java.util.HashSet;
import java.util.Set;

import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito1.Grid;
import it.unibs.algoritmiPRJ.compito3.Landmark;

/**
 * Classe per calcolare cammini liberi in una griglia.
 * Gestisce contesto e complemento per celle attraversabili.
 */
public class FreePaths {
    
	// Attributi
    private final Grid grid;
    private Set<Cell> context;
    private Set<Cell> complement;
    private Set<Landmark> closure;
    private Cell origin;
    
    /**
	 * Costruttore che accetta una griglia.
	 * @param grid Griglia su cui calcolare i cammini liberi.
	 */
    public FreePaths(Grid grid, Cell origin) {
        this.grid = grid;
        this.context = new HashSet<>();
		this.complement = new HashSet<>();
		this.closure = new HashSet<>();
		
		// Imposta l'origine e aggiunge la cella al contesto
		this.setOrigin(origin);
    }
    
    // Getters e Setters
    public int getRows() {
		return grid.getRows();
	}
    public int getCols() {
    	return grid.getCols();
    }
    public void clear() {
		context.clear();
		complement.clear();
	}
    public Set<Cell> getContext() { 
    	return context; 
    }
    public Set<Cell> getComplement() { 
    	return complement; 
    }
    public Set<Landmark> getClosure() {
		return closure;
	}
    public void setOrigin(Cell origin) {
        if (!grid.isTraversable(origin)) {
            throw new IllegalArgumentException("Cella origine non traversabile");
        }
        this.origin = origin;
        context.add(origin);
        closure.add(new Landmark(origin, 0));
	}
    public Cell getOrigin() {
		return origin;
	}
    
    // Metodi
    /**
	 * Calcola il contesto e il complemento a partire da una cella di origine.
	 * La cella di origine deve essere attraversabile.
	 * 
	 * @param originRow Riga della cella di origine.
	 * @param originCol Colonna della cella di origine.
	 */
    public void calculateContextAndComplement() {
       
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                if (!grid.isTraversable(new Cell(row, col)) || (row == origin.getRow() && col == origin.getCol())) {
                    continue;
                }
                
                boolean hasType1 = hasValidFreePath(origin, new Cell(row, col), true);
                boolean hasType2 = hasValidFreePath(origin, new Cell(row, col), false);
                Cell cell = new Cell(row, col);
                
                if (hasType1) {
                    context.add(cell);
                    closure.add(new Landmark(cell, 1));
                } else if (hasType2) {
                    complement.add(cell);
                    closure.add(new Landmark(cell, 2));
                }
            }
        }
    }

    /**
	 * Verifica se esiste un cammino libero tra due celle.
	 * 
	 * @param oRow Riga di partenza.
	 * @param oCol Colonna di partenza.
	 * @param dRow Riga di destinazione.
	 * @param dCol Colonna di destinazione.
	 * @param isType1 Indica se il cammino è di tipo 1 (diagonale → ortogonale).
	 * @return true se esiste un cammino libero, false altrimenti.
	 */
    private boolean hasValidFreePath(Cell origin, Cell destination, boolean isType1) {
    	
    	int oRow = origin.getRow(), oCol = origin.getCol(), dRow = destination.getRow(), dCol = destination.getCol(), 
    		deltaX = Math.abs(dCol - oCol), deltaY = Math.abs(dRow - oRow), 
    		rowDir = Integer.compare(dRow, oRow), colDir = Integer.compare(dCol, oCol);
    	
    	Cell dirO = new Cell(0, colDir), dirV = new Cell(rowDir, 0), dirD = new Cell(rowDir, colDir);

        
        // Casi speciali
        if (deltaX == 0) return isPathClear(origin, destination, dirV); // Colonna fissa
        if (deltaY == 0) return isPathClear(origin, destination, dirO); // Riga fissa
        if (deltaX == deltaY) return isPathClear(origin, destination, dirD); // Diagonale perfetta
        
        
        // Casi misti
        int minDelta = Math.min(deltaX, deltaY);
        
        // Tipo 1: diagonale -> ortogonale
        if (isType1) {
        	// spostamento diagonale
        	
        	Cell midCell = new Cell(oRow + minDelta * rowDir, oCol + minDelta * colDir);
            if (!isPathClear(origin, midCell, dirD)) return false;
            // spostamento ortogonale
            if (deltaX > deltaY) {
            	// spostamento in orizzontale
                return isPathClear(midCell, destination, dirO);
            } else {
            	// spostamento in verticale
                return isPathClear(midCell, destination, dirV);
            }
        } else /* Tipo 2: ortogonale -> diagonale */ {
        	// spostamento ortogonale
            if (deltaX > deltaY) {
            	// spostamento in orizzontale
            	Cell midCell = new Cell(oRow, oCol + (deltaX - minDelta) * colDir);
                return grid.isValid(midCell) && 
                       isPathClear(origin, midCell, dirO) // spostamento orizzontale
                       && isPathClear(midCell, destination, dirD); // spostamento diagonale
            } else {
            	// spostamento in verticale
                Cell midCell = new Cell(oRow + (deltaY - minDelta) * rowDir, oCol);
                return grid.isValid(midCell) && 
                       isPathClear(origin, midCell, dirV) // spostamento verticale
                       && isPathClear(midCell, destination, dirD); // spostamento diagonale
            }
        }
    }
    
    /**
	 * Verifica se il cammino tra due celle è libero.
	 * 
	 * @param fromRow Riga di partenza.
	 * @param fromCol Colonna di partenza.
	 * @param toRow Riga di destinazione.
	 * @param toCol Colonna di destinazione.
	 * @param rowDir Direzione riga (+1, -1 o 0).
	 * @param colDir Direzione colonna (+1, -1 o 0).
	 * @return true se il cammino è libero, false altrimenti.
	 */
    private boolean isPathClear(Cell origin, Cell destination, Cell direzione) {
        int row = origin.getRow(), col = origin.getCol();
        while (row != destination.getRow() || col != destination.getCol()) {
            row += direzione.getRow();
            col += direzione.getCol();
            if (!grid.isValid(new Cell(row, col))) return false;
        }
        return true;
    }
    
    /**
     * Calcola la distanza libera tra due celle.
     * 
	 * @param oRow Riga di partenza.
	 * @param oCol Colonna di partenza.
	 * @param dRow Riga di destinazione.
	 * @param dCol Colonna di destinazione.
     * @return Distanza libera tra le due celle, o -1 se non esiste un cammino libero.
     */
    public double calculateFreeDistance(Cell destination) {
    	int dCol = destination.getCol(), dRow = destination.getRow();
        if (!grid.isTraversable(origin) || !grid.isTraversable(destination)) {
            return -1;
        }
        
        int x = Math.abs(dCol - origin.getCol()), y = Math.abs(dRow - origin.getRow());
        int min = Math.min(x, y), max = Math.max(x, y);
        
        double freeDistance = Math.sqrt(2) * min + (max - min);
        return freeDistance;
    }
    
    public boolean isTraversable(Cell cell) {
		return grid.isTraversable(cell);
	}
    public boolean isValid(Cell cell) {
    	return grid.isValid(cell);
    }
    
    public Set<Landmark> getFrontiera() {
    	Set <Landmark> frontiera = new HashSet<>();
        for (Landmark landmark : this.getClosure()) {
        	
            if (isFrontierCell(landmark.getCell())) {
                frontiera.add(landmark);
            }
        }
        
        return frontiera;
    }

 
	private boolean isFrontierCell(Cell cell) {
        for (Cell neighbor : cell.getAllNeighbors()) {
            if (isValid(neighbor) && isTraversable(neighbor) && 
            		!getContext().contains(neighbor) && !getComplement().contains(neighbor)) {
                return true;
            }
        }
        return false;
    }
}