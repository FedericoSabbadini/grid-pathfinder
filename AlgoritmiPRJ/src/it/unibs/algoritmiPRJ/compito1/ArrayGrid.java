package it.unibs.algoritmiPRJ.compito1;
import java.util.*;

/**
 * Grid rappresenta una griglia di celle, dove ogni cella può essere traversabile o contenere un ostacolo.
 * La griglia è definita da un numero di righe e colonne, e può essere salvata su file o caricata da un file.
 */
public class ArrayGrid implements Grid {
	
	//========================Costanti========================
	public static final String GRID_TYPE = "Array2D";
	
	
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
    public ArrayGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(cells[i], true);
        }
    }

    
    // ========================Getters========================
    @Override
    public int getRows() { return rows; }
    @Override
    public int getCols() { return cols; }
    public boolean[][] getCells() { return cells; }
    
    
    // ========================Metodi========================
    @Override
    public boolean isTraversable(Cell cell) {
    	return isTraversable(cell.getRow(), cell.getCol());
    }
    @Override
    public boolean isTraversable(int row, int col) {
        if (row < 0 || row >= getRows() || col < 0 || col >= getCols()) {
            return false;
        }
        return cells[row][col];
    }
    
    @Override
    public void setObstacle(Cell cell) {
    	int row = cell.getRow();
    	int col = cell.getCol();
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            cells[row][col] = false;
        }
    }
    
    /**
	 * Rimuove un ostacolo da una cella, rendendola attraversabile.
	 * @param cell La cella da rendere tatraversabile.
	 */
    public void removeObstacle(Cell cell) {
		int row = cell.getRow();
		int col = cell.getCol();
		if (row >= 0 && row < rows && col >= 0 && col < cols) {
			cells[row][col] = true;
		}
	}
   
    @Override
    public boolean isValid(Cell cell) {
		return isValid(cell.getRow(), cell.getCol());
    }
    @Override
    public boolean isValid(int row, int col) {
        return row >= 0 && row < getRows() && 
               col >= 0 && col < getCols() && 
               isTraversable(row, col);
    }
    
    @Override
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
    
    @Override
	public List<Cell> getTraversableCells() {
		List<Cell> traversableCells = new ArrayList<>();
		for (int row = 0; row < getRows(); row++) {
			for (int col = 0; col < getCols(); col++) {
				Cell cell = new Cell(row, col);
				if (isTraversable(cell)) {
					traversableCells.add(cell);
				}
			}
		}
		return traversableCells;
	}
}