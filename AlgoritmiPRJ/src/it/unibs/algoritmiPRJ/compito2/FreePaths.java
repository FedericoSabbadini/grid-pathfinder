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
    
	//========================Attributi========================
    protected final Grid grid;
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
        this.context = new HashSet<>();
		this.complement = new HashSet<>();
		this.closure = new HashSet<>();
		this.setOrigin(origin);
    }
    
    
    //=====================Getters==&==Setters=====================
    public int getRows() {return grid.getRows();}
    public int getCols() {return grid.getCols();}
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
       
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                if (!grid.isTraversable(new Cell(row, col)) || (row == origin.getRow() && col == origin.getCol())) {
                    continue;
                }
                
                // Verifica se esiste un cammino libero di tipo 1 o 2
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
	 * @param origin Cella di partenza.
	 * @param destination Cella di destinazione.
	 * @param isType1 Indica se il cammino è di tipo 1 (diagonale -> ortogonale).
	 * @return true se esiste un cammino libero, false altrimenti.
	 */
    private boolean hasValidFreePath(Cell origin, Cell destination, boolean isType1) {
    	
    	int oRow = origin.getRow(), oCol = origin.getCol(), 
    			dRow = destination.getRow(), dCol = destination.getCol(), 
    			deltaX = Math.abs(dCol - oCol), deltaY = Math.abs(dRow - oRow), 
    			rowDir = Integer.compare(dRow, oRow), colDir = Integer.compare(dCol, oCol);
    	Cell dirO = new Cell(0, colDir), dirV = new Cell(rowDir, 0), 
    			dirD = new Cell(rowDir, colDir);

        //=====Casi speciali=====
        if (deltaX == 0) return isPathClear(origin, destination, dirV); // Colonna fissa
        if (deltaY == 0) return isPathClear(origin, destination, dirO); // Riga fissa
        if (deltaX == deltaY) return isPathClear(origin, destination, dirD); // Diagonale perfetta
        //=====Casi misti=====
        int minDelta = Math.min(deltaX, deltaY);
        
        //=====Tipo 1: diagonale -> ortogonale
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
            
        //=====Tipo 2: ortogonale -> diagonale
        } else {
        	
        	// spostamento in orizzontale
            if (deltaX > deltaY) {
            	Cell midCell = new Cell(oRow, oCol + (deltaX - minDelta) * colDir);
                return grid.isValid(midCell) && 
                       isPathClear(origin, midCell, dirO) 			// spostamento orizzontale
                       && isPathClear(midCell, destination, dirD); 	// spostamento diagonale

            // spostamento in verticale
            } else {
                Cell midCell = new Cell(oRow + (deltaY - minDelta) * rowDir, oCol);
                return grid.isValid(midCell) && 
                       isPathClear(origin, midCell, dirV) 			// spostamento verticale
                       && isPathClear(midCell, destination, dirD); 	// spostamento diagonale
            }
        }
    }
    
	/**
	 * Verifica se il cammino tra due celle è libero in una direzione specifica.
	 * 
	 * @param origin Cella di partenza.
	 * @param destination Cella di destinazione.
	 * @param direzione Direzione del cammino (orizzontale, verticale o diagonale).
	 * @return true se il cammino è libero, false altrimenti.
	 */
    private boolean isPathClear(Cell origin, Cell destination, Cell direzione) {
        int row = origin.getRow(), col = origin.getCol();
        
        // Controlla se la cella di destinazione è valida
        while (row != destination.getRow() || col != destination.getCol()) {
            row += direzione.getRow();
            col += direzione.getCol();
            if (!isValid(new Cell(row, col))) return false;
        }
        return true;
    }
    
	/**
	 * Calcola la distanza libera tra la cella di origine e una destinazione.
	 * La distanza è calcolata come la somma delle distanze diagonali e ortogonali.
	 * 
	 * @param destination Cella di destinazione.
	 * @return Distanza libera tra origine e destinazione, o -1 se non traversabile.
	 */
    public double calculateFreeDistance(Cell destination) {
    	// Controlla se le celle di origine e destinazione sono attraversabili
        if (!grid.isTraversable(origin) || !grid.isTraversable(destination)) {
            return -1;
        }
        
    	int dCol = destination.getCol(), dRow = destination.getRow();
        int x = Math.abs(dCol - origin.getCol()), y = Math.abs(dRow - origin.getRow());
        int min = Math.min(x, y), max = Math.max(x, y);
        
        double freeDistance = Math.sqrt(2) * min + (max - min);
        return freeDistance;
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
    	Set <Landmark> frontiera = new HashSet<>();
    	
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