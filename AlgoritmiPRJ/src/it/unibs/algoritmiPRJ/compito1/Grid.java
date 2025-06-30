package it.unibs.algoritmiPRJ.compito1;
import java.util.*;

/**
 * Grid rappresenta una griglia di celle, dove ogni cella può essere traversabile o contenere un ostacolo.
 * La griglia è definita da un numero di righe e colonne, e può essere salvata su file o caricata da un file.
 */
public class Grid {
	
	//Attributi
    private boolean[][] cells; // Matrice che rappresenta la griglia, dove true indica una cella traversabile e false un ostacolo
    private int rows; // Numero di righe della griglia
    private int cols; // Numero di colonne della griglia
    
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

    // Getters
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public boolean[][] getCells() { return cells; }
    
    
    // Metodi
    /**
	 * Verifica se una cella specificata da riga e colonna è attraversabile.
	 * @param row La riga della cella da verificare.
	 * @param col La colonna della cella da verificare.
	 * @return true se la cella è attraversabile, false altrimenti.
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
	 * Imposta una cella specificata da riga e colonna come ostacolo (non attraversabile).
	 * @param row La riga della cella da modificare.
	 * @param col La colonna della cella da modificare.
	 */
    public void setObstacle(Cell cell) {
    	int row = cell.getRow();
    	int col = cell.getCol();
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            cells[row][col] = false;
        }
    }
    
    /**
     * Verifica se una cella è valida (attraversabile e all'interno della griglia).
     * @param row Riga della cella.
     * @param col Colonna della cella.
     * @return true se la cella è valida (all'interno della griglia e attraversabile), false altrimenti.
     */
    public boolean isValid(Cell cell) {
		int row = cell.getRow();
		int col = cell.getCol();
        return row >= 0 && row < getRows() && 
               col >= 0 && col < getCols() && 
               isTraversable(cell);
    }
}