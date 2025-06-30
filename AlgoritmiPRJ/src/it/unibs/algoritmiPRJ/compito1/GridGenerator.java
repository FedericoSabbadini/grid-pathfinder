package it.unibs.algoritmiPRJ.compito1;

import java.util.*;

/**
 * GridGenerator è responsabile della generazione di una griglia con ostacoli.
 * La griglia è generata in modo casuale in base ai parametri forniti.
 */
public class GridGenerator {
	
	// Attributi
    private Random random;
    
    /**
	 * Costruttore per inizializzare il generatore di griglie con un seed casuale.
	 */
    public GridGenerator() {
        this.random = new Random();
    }
    /**
     * Costruttore per inizializzare il generatore di griglie con un seed specifico.
     * @param seed per la generazione casuale della griglia.
     */
    public GridGenerator(long seed) {
        this.random = new Random(seed);
    }
    
    // Setters
    public void setSeed(long seed) {
		this.random.setSeed(seed);
	}

    //Metodi
    
    /**
     * Genera una griglia con ostacoli in base ai parametri specificati.
     * Utilizza l'algoritmo Fisher-Yates per posizionare gli ostacoli in modo casuale.
     * Tempo medio ottimizzato: 36926 ns
     * 
     * @param params Parametri di generazione della griglia, inclusi numero di righe, colonne, percentuale di ostacoli e seed.
     * @return Una griglia generata casualmente con gli ostacoli posizionati.
     */
    public Grid generateRandomGrid(GenerationParams params) {
    	
    	int rows = params.getRows();
    	int cols = params.getCols();
		long seed = params.getSeed();
		double obstacleRatio = params.getObstacleRatio();
		
    	
    	// Impostazione seed per garanzia di riproducibilità
        random.setSeed(seed);
        
        // Creazione della griglia e calcolo del numero di ostacoli
        Grid grid = new Grid(rows, cols);
        int totalCells = rows * cols;
        int obstacleCount = (int) Math.round(totalCells * obstacleRatio);
        
        // Array di indici lineari invece di int[][]
        int[] indices = new int[totalCells];
        for (int i = 0; i < totalCells; i++) {
            indices[i] = i;
        }
        
        // Algoritmo Fisher-Yates per mescolare gli indici
        for (int i = 0; i < Math.min(obstacleCount, totalCells); i++) {
            int j = i + random.nextInt(totalCells - i);
            int temp = indices[i];
            indices[i] = indices[j];
            indices[j] = temp;
            
            // Converto da indice lineare a coordinate
            int row = indices[i] / cols;
            int col = indices[i] % cols;
            grid.setObstacle(new Cell(row, col));
        }
        
        return grid;
    }
}