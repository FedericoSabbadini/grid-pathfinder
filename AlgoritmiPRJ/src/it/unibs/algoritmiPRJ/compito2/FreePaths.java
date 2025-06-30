package it.unibs.algoritmiPRJ.compito2;
import java.util.HashSet;
import java.util.Set;
import it.unibs.algoritmiPRJ.compito1.Grid;

/**
 * Classe per calcolare cammini liberi in una griglia.
 * Gestisce contesto e complemento per celle attraversabili.
 */
public class FreePaths {
    
	// Attributi
    private final Grid grid;
    private Set<String> context;
    private Set<String> complement;    
    private int[] origin;
    
    /**
	 * Costruttore che accetta una griglia.
	 * @param grid Griglia su cui calcolare i cammini liberi.
	 */
    public FreePaths(Grid grid, int[] origin) {
        this.grid = grid;
        this.context = new HashSet<>();
		this.complement = new HashSet<>();
		this.setOrigin(origin[0], origin[1]);
    }
    
    // Getters e Setters
    public Grid getGrid() {
		return grid;
	}
    public void clear() {
		context.clear();
		complement.clear();
	}
    public boolean isInContext(int row, int col) { 
    	return context.contains(key(row, col)); 
    }
    public boolean isInComplement(int row, int col) { 
    	return complement.contains(key(row, col)); 
    }
    public Set<String> getContext() { 
    	return context; 
    }
    public Set<String> getComplement() { 
    	return complement; 
    }
    public void setOrigin(int originRow, int originCol) {
        if (!grid.isTraversable(originRow, originCol)) {
            throw new IllegalArgumentException("Cella origine non traversabile");
        }
        this.origin = new int[] { originRow, originCol };
        context.add(key(originRow, originCol));
	}
    public int[] getOrigin() {
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
                if (!grid.isTraversable(row, col) || (row == origin[0] && col == origin[1])) {
                    continue;
                }
                
                boolean hasType1 = hasValidFreePath(origin[0], origin[1], row, col, true);
                boolean hasType2 = hasValidFreePath(origin[0], origin[1], row, col, false);
                
                if (hasType1) {
                    context.add(key(row, col));
                } else if (hasType2) {
                    complement.add(key(row, col));
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
    private boolean hasValidFreePath(int oRow, int oCol, int dRow, int dCol, boolean isType1) {
        int deltaX = Math.abs(dCol - oCol);
        int deltaY = Math.abs(dRow - oRow);
        int rowDir = Integer.compare(dRow, oRow);
        int colDir = Integer.compare(dCol, oCol);
        
        // Casi speciali
        if (deltaX == 0) return isPathClear(oRow, oCol, dRow, dCol, rowDir, 0); // Colonna fissa
        if (deltaY == 0) return isPathClear(oRow, oCol, dRow, dCol, 0, colDir); // Riga fissa
        if (deltaX == deltaY) return isPathClear(oRow, oCol, dRow, dCol, rowDir, colDir); // Diagonale perfetta
        
        
        // Casi misti
        int minDelta = Math.min(deltaX, deltaY);
        
        // Tipo 1: diagonale -> ortogonale
        if (isType1) {
        	// spostamento diagonale
        	int midRow = oRow + minDelta * rowDir;
            int midCol = oCol + minDelta * colDir;
            if (!isPathClear(oRow, oCol, midRow, midCol, rowDir, colDir)) return false;
            // spostamento ortogonale
            if (deltaX > deltaY) {
            	// spostamento in orizzontale
                return isPathClear(midRow, midCol, dRow, dCol, 0, colDir);
            } else {
            	// spostamento in verticale
                return isPathClear(midRow, midCol, dRow, dCol, rowDir, 0);
            }
        } else /* Tipo 2: ortogonale -> diagonale */ {
        	// spostamento ortogonale
            if (deltaX > deltaY) {
            	// spostamento in orizzontale
                int midRow = oRow;
                int midCol = oCol + (deltaX - minDelta) * colDir;
                return isValid(midRow, midCol) && 
                       isPathClear(oRow, oCol, midRow, midCol, 0, colDir) // spostamento orizzontale
                       && isPathClear(midRow, midCol, dRow, dCol, rowDir, colDir); // spostamento diagonale
            } else {
            	// spostamento in verticale
                int midRow = oRow + (deltaY - minDelta) * rowDir;
                int midCol = oCol;
                return isValid(midRow, midCol) && 
                       isPathClear(oRow, oCol, midRow, midCol, rowDir, 0) // spostamento verticale
                       && isPathClear(midRow, midCol, dRow, dCol, rowDir, colDir); // spostamento diagonale
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
    private boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol, int rowDir, int colDir) {
        int row = fromRow, col = fromCol;
        while (row != toRow || col != toCol) {
            row += rowDir;
            col += colDir;
            if (!isValid(row, col)) return false;
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
    public double calculateFreeDistance(int dRow, int dCol) {
        if (!grid.isTraversable(origin[0], origin[1]) || !grid.isTraversable(dRow, dCol)) {
            return -1;
        }
        
        int x = Math.abs(dCol - origin[1]);
        int y = Math.abs(dRow - origin[0]);
        int min = Math.min(x, y);
        int max = Math.max(x, y);
        
        double freeDistance = Math.sqrt(2) * min + (max - min);
        return freeDistance;
    }
    
    /**
	 * Genera una chiave unica per identificare una cella nella griglia.
	 * 
	 * @param row Riga della cella.
	 * @param col Colonna della cella.
	 * @return Chiave unica per la cella.
	 */
    public String key(int row, int col) { return row + "," + col; }
    
    /**
     * Verifica se una cella è valida (attraversabile e all'interno della griglia).
     * @param row Riga della cella.
     * @param col Colonna della cella.
     * @return true se la cella è valida (all'interno della griglia e attraversabile), false altrimenti.
     */
    private boolean isValid(int row, int col) {
        return row >= 0 && row < grid.getRows() && 
               col >= 0 && col < grid.getCols() && 
               grid.isTraversable(row, col);
    }
}