package it.unibs.algoritmiPRJ.compito1;

/**
 * GenerationParams mantiene i parametri per la generazione di una griglia con ostacoli.
 * Questi parametri includono il numero di righe, colonne, la 
 * percentuale di ostacoli e un seed per la generazione casuale.
 */
public class GenerationParams {
	
	//========================Attributi========================
    private final int rows;
    private final int cols;
    private double obstacleRatio;
    private final long seed;
    private final GridType gridType;

    
    //========================Costruttore========================
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
        this.gridType = GridType.RANDOM;
    }
    
    /**
	 * Costruttore per inizializzare i parametri di generazione della griglia.
	 * @param rows Numero di righe della griglia.
	 * @param cols Numero di colonne della griglia.
	 * @param gridType Tipo di griglia da generare (SPARSE, DENSE, etc.).
	 * @param seed per la generazione casuale.
	 */
    public GenerationParams(int rows, int cols, long seed, GridType gridType) {
        this.rows = rows;
        this.cols = cols;
        this.seed = seed;
        this.gridType = gridType;
        switch (this.gridType) {
			case SPARSE:
		        this.obstacleRatio = 0.05;
				break;
			case DENSE:
		        this.obstacleRatio = 0.5;
		        break;
			default:
		        this.obstacleRatio = 0;
		}
    }
    
    /**
	 * Costruttore per inizializzare i parametri di generazione della griglia.
	 * @param rows Numero di righe della griglia.
	 * @param cols Numero di colonne della griglia.
	 * @param obstacleRatio Percentuale di celle che saranno ostacoli (tra 0.0 e 1.0).
	 * @param seed per la generazione casuale.
	 * @param gridType Tipo di griglia da generare (SPARSE, DENSE, etc.).
	 */
    public GenerationParams(int rows, int cols, double obstacleRatio, long seed, GridType gridType) {
        this.rows = rows;
        this.cols = cols;
        this.obstacleRatio = Math.max(0.0, Math.min(1.0, obstacleRatio));
        this.seed = seed;
        this.gridType = gridType;
    }
    
    
    //===================Getters==&==Setters===================
    public int getRows() {return rows;}
    public int getCols() {return cols;}
    public double getObstacleRatio() {return obstacleRatio;}
    public long getSeed() {return seed;}
    public GridType getGridType() {return gridType;}
    public void setObstacleRatio(ArrayGrid grid) {
    	double totalCells = getRows() * getCols();
    	double obstacleCount = grid.getObstacles().size();
    	this.obstacleRatio = obstacleCount / totalCells;
	}

    
    //========================Metodi========================
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