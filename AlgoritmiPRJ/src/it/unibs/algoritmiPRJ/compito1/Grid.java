package it.unibs.algoritmiPRJ.compito1;
import java.util.List;
import java.util.Set;

/**
 * Interfaccia per rappresentare una griglia di celle.
 * Fornisce metodi per ottenere informazioni sulla griglia,
 * verificare la traversabilità delle celle e gestire gli ostacoli.
 */
public interface Grid {
	
	/**
	 * Restituisce il numero di righe della griglia.
	 * @return Numero di righe.
	 */
    int getRows();
    
    /**
	 * Restituisce il numero di colonne della griglia.
	 * @return Numero di colonne.
	 */
    int getCols();
    
    /**
     * Restituisce true se la cella è traversabile, false altrimenti.
     * @param cell La cella da verificare.
     * @return true se la cella è traversabile, false altrimenti.
     */
    boolean isTraversable(Cell cell);
    
    /**
	 * Restituisce true se la cella specificata da riga e colonna è traversabile, false altrimenti.
	 * @param row La riga della cella.
	 * @param col La colonna della cella.
	 * @return true se la cella è traversabile, false altrimenti.
	 */
	boolean isTraversable(int row, int col);
	
	/**
	 * Imposta una cella come ostacolo.
	 * @param cell La cella da impostare come ostacolo.
	 */
    void setObstacle(Cell cell);
    
    /**
     * Restituisce un elenco di celle traversabili nella griglia.
     * @return Un elenco di celle traversabili nella griglia.
     */
    List<Cell> getTraversableCells();
    
    /**
	 * Restituisce un insieme di celle che rappresentano gli ostacoli nella griglia.
	 * @return Un insieme di celle ostacolo.
	 */
    Set<Cell> getObstacles();
    
    /**
     * Verifica se una cella è valida.
     * @param cell La cella da verificare.
     * @return true se la cella è valida (cioè all'interno della griglia e traversabile), false altrimenti.
     */
    boolean isValid(Cell cell);
    
    /**
	 * Verifica se una cella specificata da riga e colonna è valida.
	 * @param row La riga della cella.
	 * @param col La colonna della cella.
	 * @return true se la cella è valida, false altrimenti.
	 */
    boolean isValid(int row, int col);
    
}