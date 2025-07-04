package it.unibs.algoritmiPRJ.compito4;
import java.util.*;
import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito1.Grid;

/**
 * SparseGrid rappresenta una griglia di celle sparse, dove le celle possono essere traversabili o contenere ostacoli.
 * La griglia è definita da un numero di righe e colonne, e gestisce gli ostacoli in modo efficiente.
 */
public class SparseGrid implements Grid {
	
	
	//========================Costanti========================
	public static final String GRID_TYPE = "SparseGrid";
	
	
	//========================Attributi========================
    private final int rows, cols;
    private final Set<Cell> obstacles;
    
    
    //========================Costruttori========================
    /**
	 * Costruttore per inizializzare una griglia con un numero specifico di righe e colonne.
	 * @param rows Numero di righe della griglia.
	 * @param cols Numero di colonne della griglia.
	 */
    public SparseGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.obstacles = new HashSet<>();
    }
    
    
    // ========================Getters========================
    @Override
    public int getRows() { return rows; }
    @Override
    public int getCols() { return cols; }
    
    
    // ========================Metodi========================
    @Override
    public boolean isTraversable(Cell cell) {
        return !obstacles.contains(cell);
    }
    
    @Override
    public boolean isTraversable(int row, int col) {
		return isTraversable(new Cell(row, col));
	}
    
    @Override
    public void setObstacle(Cell cell) {
        obstacles.add(cell);
    }
    
    @Override
    public List<Cell> getTraversableCells() {
        List<Cell> cells = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = new Cell(r, c);
                if (!obstacles.contains(cell)) cells.add(cell);
            }
        }
        return cells;
    }
    
    @Override
    public Set<Cell> getObstacles() {
        return new HashSet<>(obstacles);
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
    
}