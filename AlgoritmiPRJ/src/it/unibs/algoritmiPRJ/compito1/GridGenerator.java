package it.unibs.algoritmiPRJ.compito1;
import java.util.*;

/**
 * GridGenerator è responsabile della generazione di una griglia con ostacoli.
 * La griglia è generata in modo casuale in base ai parametri forniti.
 */
public class GridGenerator {
	
	//========================Attributi========================
    private Random random;
    
    
    //========================Costruttori========================
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
    
    
    //========================Setters========================
    public void setSeed(long seed) {this.random.setSeed(seed);}
    

    //========================Metodi========================
    /**
	 * Genera una griglia in base ai parametri specificati.
	 * Il tipo di griglia è determinato dal parametro GridType.
	 * 
	 * @param params Parametri di generazione della griglia, inclusi numero di righe, colonne, percentuale di ostacoli e seed.
	 * @return Una griglia generata in base ai parametri specificati.
	 */
    public ArrayGrid generateGrid(GenerationParams params) {
		long seed = params.getSeed();
    	random.setSeed(seed);
    	
    	switch (params.getGridType()) {
	        case MAZE:
	            return generateMazeGrid(params);
	        case SPARSE:
	            return generateRandomGrid(params);
	        case DENSE:
	            return generateRandomGrid(params);
	        case PATTERN:
	        	Random random = new Random();
	        	boolean rand = random.nextBoolean();
	        	if (rand) {
	        		return generatePatternRegolareDGrid(params);
				} else {
					return generatePatternRegolareOGrid(params);
	        	}
	        case ROOMS_AND_CORRIDORS:
	            return generateRoomsAndCorridorsGrid(params);
	        case RANDOM:
	            return generateRandomGrid(params);
	        default:
	            return generateRandomGrid(params);
    	}
    }

    /**
	 * Genera una griglia casuale con ostacoli in base ai parametri specificati.
	 * Utilizza l'algoritmo Fisher-Yates per mescolare le celle e posizionare gli ostacoli.
	 * Utile per griglie RANDOM, SPARSE e DENSE.
	 * 
	 * @param params Parametri di generazione della griglia, inclusi numero di righe, colonne e percentuale di ostacoli.
	 * @return Una griglia generata casualmente con ostacoli.
	 */
    private ArrayGrid generateRandomGrid(GenerationParams params) {
    	int rows = params.getRows();
    	int cols = params.getCols();
		double obstacleRatio = params.getObstacleRatio();
        
        // Creazione della griglia e calcolo del numero di ostacoli
        ArrayGrid grid = new ArrayGrid(rows, cols);
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
    
    /**
	 * Utilizza un algoritmo di generazione di labirinti con ingresso 
	 * e uscita per creare una griglia con un percorso.
	 * 
     * @param params Parametri di generazione della griglia, inclusi numero di righe e colonne.
     * @return Una griglia generata come labirinto con ingresso e uscita.
     */
    private ArrayGrid generateMazeGrid(GenerationParams params) {
        int rows = params.getRows();
        int cols = params.getCols();

        // Assicurarsi che le dimensioni siano dispari per il labirinto
        if (rows % 2 == 0) rows--;
        if (cols % 2 == 0) cols--;

        ArrayGrid grid = new ArrayGrid(rows, cols);
        // Inizializza tutto come ostacolo
        for (int r = 0; r < rows; r++) 
            for (int c = 0; c < cols; c++) 
                grid.setObstacle(new Cell(r, c));
                
        // Rimuovi ostacoli per creare un accesso
        Cell entrance = new Cell(1, 0);
        grid.removeObstacle(entrance);
        Cell exit = new Cell(rows - 2, cols - 1);
        grid.removeObstacle(exit);

        // Inizia la generazione del labirinto da un punto di partenza
        carveMaze(grid, 1, 1, rows, cols);

        return grid;
    }

    /**
     * Crea un labirinto rimuovendo ostacoli in modo ricorsivo.
     * @param grid La griglia su cui lavorare.
     * @param r la riga corrente.
     * @param c la colonna corrente.
     * @param maxRows il numero massimo di righe della griglia.
     * @param maxCols il numero massimo di colonne della griglia.
     */
    private void carveMaze(ArrayGrid grid, int r, int c, int maxRows, int maxCols) {
        grid.removeObstacle(new Cell(r, c));
        int[][] directions = {{0,2}, {0,-2}, {2,0}, {-2,0}};
        shuffleArray(directions);
        for (int[] d : directions) {
            int nr = r + d[0];
            int nc = c + d[1];
            if (nr > 0 && nr < maxRows && nc > 0 && nc < maxCols && !grid.isTraversable(new Cell(nr, nc))) {
                // Rimuovi muro intermedio
                grid.removeObstacle(new Cell(r + d[0]/2, c + d[1]/2));
                carveMaze(grid, nr, nc, maxRows, maxCols);
            }
        }
    }

    /**
	 * Mescola un array di celle utilizzando l'algoritmo Fisher-Yates.
	 * @param array L'array di celle da mescolare.
	 */
    private void shuffleArray(int[][] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int[] temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }


    /**Genera una griglia con un PATTERN OBLIQUO regolare di ostacoli.
	 * Il pattern può essere basato su righe, colonne o diagonali.
	 * 
	 * @param params Parametri di generazione della griglia, inclusi numero di righe e colonne.
	 * @return Una griglia generata con un pattern regolare di ostacoli.
	 */
    private ArrayGrid generatePatternRegolareDGrid(GenerationParams params) {
        int rows = params.getRows();
        int cols = params.getCols();

        ArrayGrid grid = new ArrayGrid(rows, cols);
        int spacing = random.nextInt(4) + 1;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if ((r + c) % spacing == 0) {
                    grid.setObstacle(new Cell(r, c));
                }
            }
        }
        
        return grid;
    }
    
    /**Genera una griglia con un PATTERN ORTOGONALE regolare di ostacoli.
	 * Il pattern può essere basato su righe, colonne o diagonali.
	 * 
	 * @param params Parametri di generazione della griglia, inclusi numero di righe e colonne.
	 * @return Una griglia generata con un pattern regolare di ostacoli.
	 */
    private ArrayGrid generatePatternRegolareOGrid(GenerationParams params) {
        int rows = params.getRows();
        int cols = params.getCols();

        ArrayGrid grid = new ArrayGrid(rows, cols);
        int spacing = random.nextInt(4) + 1;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if ((c) % spacing == 0) {
                    grid.setObstacle(new Cell(r, c));
                }
            }
        }
        
        return grid;
    }


    /**
     * Genera una griglia con stanze quadrate regolarmente distribuite e corridoi che le collegano.
     * 
     * @param params Parametri di generazione, inclusi numero di righe e colonne.
     * @return Una griglia contenente stanze e corridoi.
     */
    private ArrayGrid generateRoomsAndCorridorsGrid(GenerationParams params) {
        int rows = params.getRows();
        int cols = params.getCols();

        ArrayGrid grid = new ArrayGrid(rows, cols);

        // Imposta tutta la griglia come ostacolo
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid.setObstacle(new Cell(r, c));
            }
        }

        Random random = new Random();
        int roomSize = random.nextInt(3) + 3;     // 3-5
        int spacing = random.nextInt(3) + 1;      // 1-3

        int totalStep = roomSize + spacing;
        int roomsPerRow = rows / totalStep;
        int roomsPerCol = cols / totalStep;

        // Crea stanze
        for (int i = 0; i < roomsPerRow; i++) {
            for (int j = 0; j < roomsPerCol; j++) {
                int rStart = i * totalStep + spacing;
                int cStart = j * totalStep + spacing;

                for (int r = rStart; r < rStart + roomSize && r < rows; r++) {
                    for (int c = cStart; c < cStart + roomSize && c < cols; c++) {
                        grid.removeObstacle(new Cell(r, c));
                    }
                }
            }
        }

        // Crea corridoi per connettere tutte le stanze
        for (int i = 0; i < roomsPerRow; i++) {
            for (int j = 0; j < roomsPerCol; j++) {
                int rMid = i * totalStep + spacing + roomSize / 2;
                int cMid = j * totalStep + spacing + roomSize / 2;

                // Collega in basso, se possibile
                if (i < roomsPerRow - 1) {
                    for (int r = rMid; r <= rMid + totalStep && r < rows; r++) {
                        grid.removeObstacle(new Cell(r, cMid));
                    }
                }

                // Collega a destra, se possibile
                if (j < roomsPerCol - 1) {
                    for (int c = cMid; c <= cMid + totalStep && c < cols; c++) {
                        grid.removeObstacle(new Cell(rMid, c));
                    }
                }
            }
        }

        return grid;
    }
}