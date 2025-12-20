package it.unibs.algoritmiPRJ.compito1;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * La classe Cell rappresenta una cella in una griglia, identificata da una riga e una colonna.
 * Fornisce metodi per ottenere le coordinate della cella, verificare l'uguaglianza tra celle,
 * calcolare l'hash code e ottenere le celle vicine.
 */
public class Cell {

	//========================Attributi========================
	private final int row;
	private final int col;
	
	
	//========================Costruttori========================
	/**
	 * Costruttore della cella.
	 * @param row La riga della cella.
	 * @param col La colonna della cella.
	 */
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	
	//========================Getters========================
	public int getRow() {return row;}
	public int getCol() {return col;}
	
	
	//========================Metodi========================
	/**
	 * Restituisce una rappresentazione testuale della cella.
	 * @return Una stringa che rappresenta la cella nel formato "(riga, colonna)".
	 */
    @Override
    public String toString() {
        return "(" + row +  "," + col + ")";
    }
    
    /**
	 * Verifica se due celle sono uguali.
	 * Due celle sono considerate uguali se hanno la stessa riga e colonna.
	 * @param obj L'oggetto da confrontare con questa cella.
	 * @return true se le celle sono uguali, false altrimenti.
	 */
    @Override
    public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Cell)) return false;
		Cell other = (Cell) obj;
		return row == other.row && col == other.col;
	}
    
    /**
	 * Calcola l'hash code della cella.
	 * L'hash code è basato sui valori di riga e colonna.
	 * @return L'hash code della cella.
	 */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
    
    /**
     * Ottiene una lista di tutte le celle vicine a questa cella.
     * Le celle vicine sono quelle che si trovano in una delle otto direzioni (orizzontale, verticale e diagonale).
     * @return Una lista di celle vicine.
     */
    public List<Cell> getAllNeighbors() {
        List<Cell> neighbors = new ArrayList<>();
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        
        for (int i = 0; i < 8; i++) {
            neighbors.add(new Cell(getRow() + dx[i], getCol() + dy[i]));
        }
        return neighbors;
    }
}