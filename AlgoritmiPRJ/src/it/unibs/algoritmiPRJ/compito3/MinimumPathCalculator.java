package it.unibs.algoritmiPRJ.compito3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito1.Grid;

/**
 * Classe per il calcolo del cammino minimo tra due celle in una griglia,
 * considerando ostacoli e percorsi liberi.
 */
class MinimumPathCalculator {
	
	//========================Attributi========================    
    private final Grid grid;
    private int totalRecursiveCalls;
    
    
    //========================Costruttore========================
    /**
	 * Costruttore della classe MinimumPathCalculator.
	 * 
	 * @param grid La griglia su cui calcolare il cammino minimo.
	 */
    public MinimumPathCalculator(Grid grid) {
        this.grid = grid;
        this.totalRecursiveCalls = 0;
    }
    
    
    //========================Metodi========================   
    /**
     * Calcola il cammino minimo tra due celle della griglia, escludendo gli ostacoli.
     * @param origin La cella di partenza.
     * @param destination La cella di destinazione.
     * @return Un oggetto MinimumPathResult contenente la lunghezza del cammino minimo e la sequenza di landmark.
     */
    public MinimumPathResult calculateMinimumPath(Cell origin, Cell destination) {
    	// Ottieni gli ostacoli dalla griglia
        Set<Cell> obstacles = grid.getObstacles();
        
        MinimumPathResult result = camminoMin(origin, destination, obstacles);
        result.setRecursiveCalls(totalRecursiveCalls);
        totalRecursiveCalls = 0; // Reset per il prossimo calcolo
        
        return result;
    }
    
    /**
	 * Algoritmo ricorsivo per calcolare il cammino minimo tra due celle, escludendo gli ostacoli.
	 * @param origin La cella di partenza.
	 * @param destination La cella di destinazione.
	 * @param obstacles Un insieme di celle che rappresentano gli ostacoli.
	 * @return Un oggetto MinimumPathResult contenente la lunghezza del cammino minimo e la sequenza di landmark.
	 */
    private MinimumPathResult camminoMin(Cell origin, Cell destination, Set<Cell> obstacles) {
    	
        totalRecursiveCalls++;
        FreePathsExtended  freePathsCalculator = new FreePathsExtended(grid, origin, obstacles);
        freePathsCalculator.calculateContextAndComplement();
        List<Landmark> seqMin = new ArrayList<>();
        
        // ---------- CASO 1: D appartiene al contesto ----------
        Set<Cell> context = freePathsCalculator.getContext();        
        if (context.contains(destination)) {
            double dlib = freePathsCalculator.dLib(destination);
            seqMin = compatta(
                    Arrays.asList(new Landmark(origin, 0), new Landmark(destination, 1)),
                    null
                    );
            return new MinimumPathResult(dlib, seqMin);        }
        
        // ---------- CASO 2: D appartiene al complemento ----------
        Set<Cell> complement = freePathsCalculator.getComplement();
        if (complement.contains(destination)) {
            double dlib = freePathsCalculator.dLib(destination);
            seqMin = compatta(
                    Arrays.asList(new Landmark(origin, 0), new Landmark(destination, 2)),
                    null
                    );
            return new MinimumPathResult(dlib, seqMin);
        }
        
        // ---------- CASO 3: D non appartiene né al contesto né al complemento ----------
        Set<Landmark> frontier = freePathsCalculator.getFrontiera();
        if (frontier.isEmpty()) 
            return new MinimumPathResult(Double.POSITIVE_INFINITY, new ArrayList<>());
        
        // Inizializzazione per il caso ricorsivo
        double lunghezzaMin = Double.POSITIVE_INFINITY;
        
        for (Landmark frontierLandmark : frontier) {
            Cell frontierCell = frontierLandmark.getCell();
            int t = frontierLandmark.getType();
            
            double lF = freePathsCalculator.dLib(frontierCell);
            double lFD = freePathsCalculator.dLib(frontierCell, destination);

            if (lF + lFD < lunghezzaMin) {
            	
                Set<Cell> newObstacles = new HashSet<>(obstacles);
                newObstacles.addAll(context);
                newObstacles.addAll(complement);
                
                MinimumPathResult recursiveResult = camminoMin(frontierCell, destination, newObstacles);
                
                double lTot = lF + recursiveResult.getLength();
                
                if (lTot < lunghezzaMin) {
                    lunghezzaMin = lTot;
                    seqMin = compatta(
                        Arrays.asList(new Landmark(origin, 0), new Landmark(frontierCell, t)),
                        recursiveResult.getLandmarkSequence()
                    );
                }
            }
        }
        return new MinimumPathResult(lunghezzaMin, seqMin);
    }
    
    /**
	 * Compattta due sequenze di landmark, mantenendo il primo della prima e tutti tranne il primo della seconda.
	 * @param seq1 La prima sequenza di landmark.
	 * @param seq2 La seconda sequenza di landmark.
	 * @return Una nuova lista di landmark compatta.
	 */
    private List<Landmark> compatta(List<Landmark> seq1, List<Landmark> seq2) {
    	
        if (seq2==null || seq2.isEmpty()) return new ArrayList<>(seq1);
        
        List<Landmark> result = new ArrayList<>(seq1);
        // Omette il primo elemento della seconda sequenza (che dovrebbe essere uguale all'ultimo della prima)
        for (int i = 1; i < seq2.size(); i++) {
            result.add(seq2.get(i));
        }
        return result;
    }
}