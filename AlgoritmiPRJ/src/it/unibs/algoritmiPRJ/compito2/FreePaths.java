package it.unibs.algoritmiPRJ.compito2;
import java.util.HashSet;
import java.util.Set;
import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito1.Grid;
import it.unibs.algoritmiPRJ.compito3.Landmark;

/**
 * Classe per calcolare cammini liberi in una griglia.
 * Gestisce contesto e complemento per celle attraversabili.
 * Versione ottimizzata con cache e riduzione di allocazioni.
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
	 * @param grid Griglia su cui calcolare i cammini liberi.
	 */
    public FreePaths(Grid grid, Cell origin) {
        this.grid = grid;
        this.rows = grid.getRows();
        this.cols = grid.getCols();
        this.context = new HashSet<>();
		this.complement = new HashSet<>();
		this.closure = new HashSet<>();		
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
        
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
            	
                Cell cell = new Cell(row, col);

                // Skip celle non attraversabili e origine
                if (!grid.isTraversable(cell) || (cell.equals(origin))) {
                    continue;
                }
                
                // Verifica Type1 prima, se fallisce prova Type2
                if (hasValidFreePath(origin, cell, true)) {
                    context.add(cell);
                    closure.add(new Landmark(cell, 1));
                } else if (hasValidFreePath(origin, cell, false)) {
                    complement.add(cell);
                    closure.add(new Landmark(cell, 2));
                }
            }
        }
    }

	/**
	 * Verifica se esiste un cammino libero tra due celle.
	 * 
	 * @param origin Cella di partenza.
	 * @param destination Cella di destinazione.
	 * @param isType1 Indica se il cammino è di tipo 1 (diagonale -> ortogonale).
	 * @return true se esiste un cammino libero, false altrimenti.
	 */
    private boolean hasValidFreePath(Cell origin, Cell destination, boolean isType1) {
        // Early termination
        if (origin.equals(destination)) return true;
        
    	int oRow = origin.getRow(), oCol = origin.getCol(), 
    			dRow = destination.getRow(), dCol = destination.getCol(), 
    			deltaX = Math.abs(dCol - oCol), deltaY = Math.abs(dRow - oRow), 
    			rowDir = Integer.compare(dRow, oRow), colDir = Integer.compare(dCol, oCol);

        //=====Casi speciali=====
        if (deltaX == 0) return isPathClearOptimized(oRow, oCol, dRow, dCol, rowDir, 0); // Colonna fissa
        if (deltaY == 0) return isPathClearOptimized(oRow, oCol, dRow, dCol, 0, colDir); // Riga fissa
        if (deltaX == deltaY) return isPathClearOptimized(oRow, oCol, dRow, dCol, rowDir, colDir); // Diagonale perfetta
        
        //=====Casi misti=====
        int minDelta = Math.min(deltaX, deltaY);
        
        //=====Tipo 1: diagonale -> ortogonale
        if (isType1) {
        	// spostamento diagonale
        	int midRow = oRow + minDelta * rowDir;
        	int midCol = oCol + minDelta * colDir;
        	
        	// Controlla se la cella di mezzo è valida e attraversabile
            if (!grid.isValid(midRow, midCol) || !isTraversable(new Cell(midRow, midCol))) {
                return false;
            }
            // Verifica se il cammino diagonale è libero
            if (!isPathClearOptimized(oRow, oCol, midRow, midCol, rowDir, colDir)) return false;
            
            // spostamento ortogonale
            if (deltaX > deltaY) {
            	// spostamento in orizzontale
                return isPathClearOptimized(midRow, midCol, dRow, dCol, 0, colDir);
            } else {
            	// spostamento in verticale
                return isPathClearOptimized(midRow, midCol, dRow, dCol, rowDir, 0);
            }
            
        //=====Tipo 2: ortogonale -> diagonale
        } else {
        	// spostamento in orizzontale
            if (deltaX > deltaY) {
            	int midRow = oRow;
            	int midCol = oCol + (deltaX - minDelta) * colDir;
                
            	// Controlla se la cella di mezzo è valida e attraversabile
                if (!grid.isValid(midRow, midCol) || !isTraversable(new Cell(midRow, midCol))) {
                    return false;
                }
                
                // Verifica se il cammino orizzontale è libero
            	return grid.isValid(midRow, midCol) && 
                       isPathClearOptimized(oRow, oCol, midRow, midCol, 0, colDir) && 
                       isPathClearOptimized(midRow, midCol, dRow, dCol, rowDir, colDir);

            // spostamento in verticale
            } else {
                int midRow = oRow + (deltaY - minDelta) * rowDir;
                int midCol = oCol;
               
                // Controlla se la cella di mezzo è valida e attraversabile
                if (!grid.isValid(midRow, midCol) || !isTraversable(new Cell(midRow, midCol))) {
                    return false;
                }
                
                // Verifica se il cammino verticale è libero
                return grid.isValid(midRow, midCol) && 
                       isPathClearOptimized(oRow, oCol, midRow, midCol, rowDir, 0) && 
                       isPathClearOptimized(midRow, midCol, dRow, dCol, rowDir, colDir);
            }
        }
    }
    
	/**
	 * Verifica se il cammino tra due celle è libero in una direzione specifica.
	 * Versione ottimizzata che evita creazione di oggetti Cell.
	 * 
	 * @param startRow Riga di partenza.
	 * @param startCol Colonna di partenza.
	 * @param endRow Riga di destinazione.
	 * @param endCol Colonna di destinazione.
	 * @param rowDir Direzione riga (-1, 0, 1).
	 * @param colDir Direzione colonna (-1, 0, 1).
	 * @return true se il cammino è libero, false altrimenti.
	 */
    private boolean isPathClearOptimized(int startRow, int startCol, int endRow, int endCol, int rowDir, int colDir) {
        int row = startRow;
        int col = startCol;
        
        // Verifica tutte le celle lungo il percorso, inclusa la destinazione
        while (true) {
            // Se siamo arrivati alla destinazione, il percorso è libero
            if (row == endRow && col == endCol) {
                return true;
            }
            
            // Muovi alla prossima cella
            row += rowDir;
            col += colDir;
            
            // Verifica se la cella è attraversabile
            if (!grid.isValid(row, col) || !isTraversable(new Cell(row, col))) {
                return false;
            }
        }
    }
    
	/**
	 * Calcola la distanza libera tra la cella di origine e una destinazione.
	 * La distanza è calcolata come la somma delle distanze diagonali e ortogonali.
	 * 
	 * @param destination Cella di destinazione.
	 * @return Distanza libera tra origine e destinazione, o -1 se non traversabile.
	 */
    public double dLib(Cell destination) {
    	// Controlla se le celle di origine e destinazione sono attraversabili
        if (!grid.isTraversable(origin) || 
            !grid.isTraversable(destination)) {
            return -1;
        }
        
    	int dCol = destination.getCol(), dRow = destination.getRow();
        int x = Math.abs(dCol - origin.getCol()), y = Math.abs(dRow - origin.getRow());
        int min = Math.min(x, y), max = Math.max(x, y);
        
        return Math.sqrt(2) * min + (max - min);
    }
    
    /**
     * Verifica se una cella è attraversabile.
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
	 * Restituisce l'insieme delle celle di frontiera.
	 * Le celle di frontiera sono quelle che hanno almeno un vicino attraversabile
	 * che non appartiene al contesto o al complemento.
	 * 
	 * @return Set di Landmark che rappresentano le celle di frontiera.
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
	 * Verifica se una cella è una cella di frontiera.
	 * Una cella di frontiera ha almeno un vicino attraversabile
	 * che non appartiene al contesto o al complemento.
	 * 
	 * @param cell Cella da verificare.
	 * @return true se la cella è di frontiera, false altrimenti.
	 */
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