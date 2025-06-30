package it.unibs.algoritmiPRJ.compito1;

/**
 * GenerationParams mantiene i parametri per la generazione di una griglia con ostacoli.
 * Questi parametri includono il numero di righe, colonne, la 
 * percentuale di ostacoli e un seed per la generazione casuale.
 */
public class GenerationParams {
	
	// Attributi
    private int rows;
    private int cols;
    private double obstacleRatio;
    private long seed;
    
    //Getters
    public int getRows() {
		return rows;
	}
    public int getCols() {
    	return cols;
    }
    public double getObstacleRatio() {
    	return obstacleRatio;
    }
    public long getSeed() {
		return seed;
	}

    /**
	 * Costruttore per inizializzare i parametri di generazione della griglia.
	 * @param rows Numero di righe della griglia.
	 * @param cols Numero di colonne della griglia.
	 * @param obstacleRatio Percentuale di celle che saranno ostacoli (tra 0.0 e 1.0).
	 * @param seed per la generazione casuale.
	 */
    public GenerationParams(int rows, int cols, double obstacleRatio, long seed) {
        this.rows = rows;
        this.cols = cols;
        this.obstacleRatio = Math.max(0.0, Math.min(1.0, obstacleRatio));
        this.seed = seed;
    }
    
    //Metodi
    /**
	 * Conta il numero di ostacoli nella griglia.
	 * @return Il numero di celle non traversabili (ostacoli).
	 */
    public int countObstacles() {
        int totalCells = rows * cols;
        int obstacleCount = (int) (totalCells * obstacleRatio);
        return obstacleCount;
    }
}