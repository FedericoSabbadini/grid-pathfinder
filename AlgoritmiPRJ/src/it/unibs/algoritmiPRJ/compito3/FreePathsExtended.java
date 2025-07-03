package it.unibs.algoritmiPRJ.compito3;
import java.util.HashSet;
import java.util.Set;
import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito1.Grid;
import it.unibs.algoritmiPRJ.compito2.FreePaths;

/**
 * Estensione della classe FreePaths per gestire ostacoli aggiuntivi.
 * Questa classe permette di calcolare percorsi liberi considerando ostacoli specifici.
 */
class FreePathsExtended extends FreePaths {
	
	//========================Attributi========================    
	private final Set<Cell> additionalObstacles;
   
	
	//========================Costruttori========================
	/**
	 * Crea un oggetto FreePathsExtended con una griglia, una cella di origine e un insieme di ostacoli aggiuntivi.
	 * 
	 * @param grid La griglia su cui calcolare i percorsi liberi.
	 * @param origin La cella di origine da cui partire.
	 * @param additionalObstacles Un insieme di celle che rappresentano ostacoli aggiuntivi.
	 */
    public FreePathsExtended(Grid grid, Cell origin, Set<Cell> additionalObstacles) {
        super(grid, origin);
        this.additionalObstacles = new HashSet<>(additionalObstacles);
    }
    
    
	//========================Metodi========================    
    /**
	 * Verifica se una cella è attraversabile, considerando gli ostacoli aggiuntivi.
	 * @param cell La cella da verificare.
	 * @return true se la cella è attraversabile, false altrimenti.
	 */
    @Override
    public boolean isTraversable(Cell cell) {
        return super.isTraversable(cell) && !additionalObstacles.contains(cell);
    }
    
    /**
	 * Calcola la distanza libera tra due celle.
	 * La distanza è calcolata come la somma delle distanze diagonali e ortogonali.
	 * 
	 * @param origin Cella di partenza.
	 * @param destination Cella di destinazione.
	 * @return Distanza libera tra origine e destinazione, o -1 se non attraversabile.
	 */
    public double dLib(Cell origin, Cell destination) {
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
}