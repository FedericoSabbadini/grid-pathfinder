package it.unibs.algoritmiPRJ.compito4;
import java.util.*;
import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito1.Grid;

/**
 * Implementazione della griglia utilizzando BitSet per gestire gli ostacoli.
 * Questa classe fornisce metodi per verificare la traversabilità delle celle,
 * impostare ostacoli e ottenere le celle traversabili.
 */
public class BitSetGrid implements Grid {
	
	
	//========================Costanti========================
	public static final String GRID_TYPE = "BitSet";
	
	
	//========================Attributi========================
    private final int rows, cols;
    private final BitSet obstacles;
    
    
    //========================Costruttori========================
    /**
	 * Crea una griglia con un numero specifico di righe e colonne.
	 * Inizializza un BitSet per gestire gli ostacoli.
	 * 
	 * @param rows Numero di righe della griglia.
	 * @param cols Numero di colonne della griglia.
	 */
    public BitSetGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.obstacles = new BitSet(rows * cols);
    }
    
    
    //========================Getters========================
    @Override
    public int getRows() { return rows; }
    
    @Override
    public int getCols() { return cols; }
    
    
    //========================Metodi========================
    @Override
    public boolean isTraversable(Cell cell) {
        return !obstacles.get(cell.getRow() * cols + cell.getCol());
    }
    @Override
    public boolean isTraversable(int row, int col) {
		return isTraversable(new Cell(row, col));
	}
    
    @Override
    public void setObstacle(Cell cell) {
        obstacles.set(cell.getRow() * cols + cell.getCol());
    }
    
    @Override
    public List<Cell> getTraversableCells() {
        List<Cell> cells = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = new Cell(r, c);
                if (isTraversable(cell)) cells.add(cell);
            }
        }
        return cells;
    }
    
    @Override
    public Set<Cell> getObstacles() {
        Set<Cell> obs = new HashSet<>();
        for (int i = obstacles.nextSetBit(0); i >= 0; i = obstacles.nextSetBit(i + 1)) {
            obs.add(new Cell(i / cols, i % cols));
        }
        return obs;
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