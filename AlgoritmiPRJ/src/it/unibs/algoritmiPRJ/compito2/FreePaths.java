package it.unibs.algoritmiPRJ.compito2;
import java.util.HashSet;
import java.util.Set;
import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito1.Grid;
import it.unibs.algoritmiPRJ.compito3.Landmark;

/**
 * Classe per calcolare cammini liberi in una griglia.
 * Gestisce contesto e complemento per celle attraversabili.
 * Versione ottimizzata con riduzione di allocazioni.
 */
public class FreePaths {
    
	//========================Attributi========================
    protected final Grid grid;
    private final int rows, cols;

    private Set<Cell> context;
    private Set<Cell> complement;
    private Set<Landmark> closure;
    private Cell origin;
    
    
	//========================Costruttori========================
    /**
	 * Costruttore che accetta una griglia.
	 * @param grid2 Griglia su cui calcolare i cammini liberi.
	 * @param origin Cella di origine da cui partire per i calcoli.
	 */
    public FreePaths(Grid grid, Cell origin) {
        this.grid = grid;
        this.rows = grid.getRows();
        this.cols = grid.getCols();
		this.setOrigin(origin);
    }
    
    
    //=====================Getters==&==Setters=====================
    public int getRows() {return rows;}
    public int getCols() {return cols;}
    public Set<Cell> getContext() {return context;}
    public Set<Cell> getComplement() {return complement;}
    public Set<Landmark> getClosure() {return closure;}
    public Cell getOrigin() {return origin;}
    
    /**
     * Imposta la cella di origine.
     * Aggiunge la cella di origine al contesto e alla chiusura.
     * @param origin Cella di origine da impostare.
     */
    public void setOrigin(Cell origin) {
        if (!grid.isTraversable(origin)) {
            throw new IllegalArgumentException("Cella origine non attraversabile");
        }
        this.origin = origin;
        this.context = new HashSet<>();
        this.complement = new HashSet<>();
        this.closure = new HashSet<>();
        
        context.add(origin);
        closure.add(new Landmark(origin, 0));
	}

    
    //========================Metodi========================
	/**
	 * Calcola il contesto e il complemento per la cella di origine.
	 * Aggiunge le celle attraversabili al contesto o al complemento
	 * in base alla presenza di cammini liberi di tipo 1 o 2.
	 */
    public void calculateContextAndComplement() {
        int oRow = origin.getRow();
        int oCol = origin.getCol();
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Skip celle non attraversabili e origine
                if (!grid.isTraversable(row, col) || (row == oRow && col == oCol)) {
                    continue;
                }
                
                // Verifica Type1 prima, se fallisce prova Type2
                if (hasValidFreePath(oRow, oCol, row, col, true)) {
                    Cell cell = new Cell(row, col);
                    context.add(cell);
                    closure.add(new Landmark(cell, 1));
                } else if (hasValidFreePath(oRow, oCol, row, col, false)) {
                    Cell cell = new Cell(row, col);
                    complement.add(cell);
                    closure.add(new Landmark(cell, 2));
                }
            }
        }
    }

	/**
	 * Verifica se esiste un cammino libero tra due celle.
	 * Evita creazione di oggetti Cell non necessari.
	 * @param oRow Riga di origine
	 * @param oCol Colonna di origine
	 * @param dRow Riga di destinazione
	 * @param dCol Colonna di destinazione
	 * @param isType1 Indica se il cammino è di tipo 1 (diagonale -> ortogonale) 
	 * 			 o di tipo 2 (ortogonale -> diagonale)
	 * @return true se esiste un cammino libero, false altrimenti
	 */
    private boolean hasValidFreePath(int oRow, int oCol, int dRow, int dCol, boolean isType1) {
        // Early termination
        if (oRow == dRow && oCol == dCol) return true;
        
    	int deltaX = Math.abs(dCol - oCol);
    	int deltaY = Math.abs(dRow - oRow);
    	int rowDir = Integer.compare(dRow, oRow);
    	int colDir = Integer.compare(dCol, oCol);

        //=====Casi speciali=====
        if (deltaX == 0) return isPathClear(oRow, oCol, dRow, dCol, rowDir, 0);
        if (deltaY == 0) return isPathClear(oRow, oCol, dRow, dCol, 0, colDir);
        if (deltaX == deltaY) return isPathClear(oRow, oCol, dRow, dCol, rowDir, colDir);
        
        //=====Casi misti=====
        int minDelta = Math.min(deltaX, deltaY);
        
        //=====Tipo 1: diagonale -> ortogonale
        if (isType1) {
        	int midRow = oRow + minDelta * rowDir;
        	int midCol = oCol + minDelta * colDir;
        	
            if (!grid.isValid(midRow, midCol)) {
                return false;
            }
            if (!isPathClear(oRow, oCol, midRow, midCol, rowDir, colDir)) return false;
            
            if (deltaX > deltaY) {
                return isPathClear(midRow, midCol, dRow, dCol, 0, colDir);
            } else {
                return isPathClear(midRow, midCol, dRow, dCol, rowDir, 0);
            }
            
        //=====Tipo 2: ortogonale -> diagonale
        } else {
            if (deltaX > deltaY) {
            	int midRow = oRow;
            	int midCol = oCol + (deltaX - minDelta) * colDir;
                
                if (!grid.isValid(midRow, midCol)) {
                    return false;
                }
                
            	return isPathClear(oRow, oCol, midRow, midCol, 0, colDir) && 
                       isPathClear(midRow, midCol, dRow, dCol, rowDir, colDir);

            } else {
                int midRow = oRow + (deltaY - minDelta) * rowDir;
                int midCol = oCol;
               
                if (!grid.isValid(midRow, midCol)) {
                    return false;
                }
                
                return isPathClear(oRow, oCol, midRow, midCol, rowDir, 0) && 
                       isPathClear(midRow, midCol, dRow, dCol, rowDir, colDir);
            }
        }
    }
    
	/**
	 * Verifica se il cammino tra due celle è libero in una direzione specifica.
	 * Controlla se tutte le celle intermedie sono attraversabili.
	 * @param startRow Riga di partenza
	 * @param startCol Colonna di partenza
	 * @param endRow Riga di destinazione
	 * @param endCol Colonna di destinazione
	 * @param rowDir Direzione della riga (+1, -1 o 0)
	 * @param colDir Direzione della colonna (+1, -1 o 0)
	 * @return true se il cammino è libero, false altrimenti
	 */
    private boolean isPathClear(int startRow, int startCol, int endRow, int endCol, int rowDir, int colDir) {
        int row = startRow;
        int col = startCol;
        
        while (true) {
            if (row == endRow && col == endCol) 
                return true;
            
            row += rowDir;
            col += colDir;
            
            if (!grid.isValid(row, col)) 
                return false;
        }
    }
    
    /**
	 * Calcola la distanza libera tra la cella di origine e una destinazione.
	 * Utilizza la formula di Manhattan per il calcolo della distanza.
	 * 
	 * @param destination Cella di destinazione.
	 * @return La distanza libera tra l'origine e la destinazione, o -1 se non è possibile raggiungere la destinazione.
	 */
    public double dLib(Cell destination) {
        if (!grid.isTraversable(origin) || 
            !grid.isTraversable(destination)) 
            return -1;
        
    	int dCol = destination.getCol(), dRow = destination.getRow();
        int x = Math.abs(dCol - origin.getCol()), y = Math.abs(dRow - origin.getRow());
        int min = Math.min(x, y), max = Math.max(x, y);
        
        return Math.sqrt(2) * min + (max - min);
    }

    /**
     *  Verifica se una cella è attraversabile nella griglia.
     * @param cell Cella da verificare.
     * @return true se la cella è attraversabile, false altrimenti.
     */
    public boolean isTraversable(Cell cell) {
		return grid.isTraversable(cell);
	}
    
    /**
	 * Verifica se una cella è valida nella griglia.
	 * @param cell Cella da verificare.
	 * @return true se la cella è valida, false altrimenti.
	 */
    public boolean isValid(Cell cell) {
    	return grid.isValid(cell);
    }
    
    /**
     *  Restituisce l'insieme delle celle di frontiera.
     * @return Un insieme di celle di frontiera.
     */
    public Set<Landmark> getFrontiera() {
    	Set<Landmark> frontiera = new HashSet<>();
    	
        for (Landmark landmark : this.getClosure()) {
            if (isFrontierCell(landmark.getCell())) {
                frontiera.add(landmark);
            }
        }
        return frontiera;
    }

    /**
	 *  Verifica se una cella è una cella di frontiera.
	 *  Una cella è considerata di frontiera se ha almeno un vicino attraversabile
	 *  che non è presente nel contesto o nel complemento.
	 * @param cell Cella da verificare.
	 * @return true se la cella è una cella di frontiera, false altrimenti.
	 */
	private boolean isFrontierCell(Cell cell) {
        for (Cell neighbor : cell.getAllNeighbors()) {
            if (isValid(neighbor) && isTraversable(neighbor) && 
            		!getContext().contains(neighbor) && !getComplement().contains(neighbor)) 
                return true;
        }
        return false;
    }
}