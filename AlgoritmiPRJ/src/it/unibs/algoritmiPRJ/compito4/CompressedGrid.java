package it.unibs.algoritmiPRJ.compito4;
import java.util.*;
import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito1.Grid;

/**
 * Classe per rappresentare una griglia compressa.
 * Utilizza un array di byte per memorizzare gli ostacoli in modo compatto.
 * Le celle traversabili sono rappresentate da bit a 0, mentre gli ostacoli da bit a 1.
 */
public class CompressedGrid implements Grid {
	
	
	//========================Costanti========================
	public static final String GRID_TYPE = "CompressedGrid";
	
	
	//========================Attributi========================
    private final int rows, cols;
    private final byte[] cells;
    
    
    //========================Costruttore========================
    /**
	 * Costruttore per inizializzare una griglia compressa con un numero specifico di righe e colonne.
	 * @param rows Numero di righe della griglia.
	 * @param cols Numero di colonne della griglia.
	 */
    public CompressedGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new byte[(rows * cols + 7) / 8];
    }
    
    
    //========================Getters========================
    @Override
    public int getRows() { return rows; }
    
    @Override
    public int getCols() { return cols; }
    
    
	//========================Metodi========================
    @Override
    public boolean isTraversable(Cell cell) {
        int index = cell.getRow() * cols + cell.getCol();
        return (cells[index / 8] & (1 << (index % 8))) == 0;
    }
    @Override
    public boolean isTraversable(int row, int col) {
		return isTraversable(new Cell(row, col));
	}
    
    @Override
    public void setObstacle(Cell cell) {
        int index = cell.getRow() * cols + cell.getCol();
        cells[index / 8] |= (1 << (index % 8));
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
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = new Cell(r, c);
                if (!isTraversable(cell)) obs.add(cell);
            }
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