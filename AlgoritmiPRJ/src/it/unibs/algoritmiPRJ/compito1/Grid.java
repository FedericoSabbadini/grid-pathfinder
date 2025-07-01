package it.unibs.algoritmiPRJ.compito1;
import java.util.*;

/**
 * Grid rappresenta una griglia di celle, dove ogni cella può essere traversabile o contenere un ostacolo.
 * La griglia è definita da un numero di righe e colonne, e può essere salvata su file o caricata da un file.
 */
public class Grid {
	
	//========================Attributi========================
    private boolean[][] cells; 
    private final int rows;
    private final int cols;
    
    
    //========================Costruttori========================
    /**
     * Costruttore per inizializzare una griglia con un numero specifico di righe e colonne.
	 * @param rows Numero di righe della griglia.
	 * @param cols Numero di colonne della griglia.
     */
    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(cells[i], true);
        }
    }

    
    // ========================Getters========================
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public boolean[][] getCells() { return cells; }
    
    
    // ========================Metodi========================
    /**
	 * Imposta una cella come traversabile.
	 * @param cell La cella da impostare come traversabile.
	 */
    public boolean isTraversable(Cell cell) {
    	int row = cell.getRow();
    	int col = cell.getCol();
        if (row < 0 || row >= getRows() || col < 0 || col >= getCols()) {
            return false;
        }
        return cells[row][col];
    }
    
    /**
     * Imposta una cella come non attraversabile (ostacolo).
     * @param cell La cella da impostare come non attraversabile.
     */
    public void setObstacle(Cell cell) {
    	int row = cell.getRow();
    	int col = cell.getCol();
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            cells[row][col] = false;
        }
    }
    
    /**
	 * Imposta una cella come attraversabile.
	 * @param cell La cella da impostare come attraversabile.
	 */
    public boolean isValid(Cell cell) {
		int row = cell.getRow();
		int col = cell.getCol();
        return row >= 0 && row < getRows() && 
               col >= 0 && col < getCols() && 
               isTraversable(cell);
    }
    
    /**
	 * Restituisce un set di celle che rappresentano gli ostacoli nella griglia.
	 * @return Un set di celle che non sono attraversabili.
	 */
	public Set<Cell> getObstacles() {
		Set<Cell> obstacles = new HashSet<>();
		for (int row = 0; row < getRows(); row++) {
			for (int col = 0; col < getCols(); col++) {
				Cell cell = new Cell(row, col);
				if (!isTraversable(cell)) {
					obstacles.add(cell);
				}
			}
		}
		return obstacles;
	}
}