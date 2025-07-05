package it.unibs.algoritmiPRJ.compito3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito1.Grid;

/**
 * Classe per il calcolo del cammino minimo tra due celle in una griglia,
 * considerando ostacoli e percorsi liberi.
 */
public class MinimumPathCalculator {
	
	//========================Attributi========================    
    private final Grid grid;
    private int totalRecursiveCalls;
    private int iterationsFalse;
    private int numFrontierCells;
    
    private final Map<Cell, FreePathsExtended> freePathsCache = new ConcurrentHashMap<>();
    private final Map<String, PathResult> pathCache = new ConcurrentHashMap<>();
    
    //========================Costruttore========================
    /**
	 * Costruttore della classe MinimumPathCalculator.
	 * 
	 * @param implGrid La griglia su cui calcolare il cammino minimo.
	 */
    public MinimumPathCalculator(Grid grid) {
        this.grid = grid;
        this.totalRecursiveCalls = 0;
        this.iterationsFalse = 0;
        this.numFrontierCells = 0;
    }
    
    
    //========================Metodi========================   
    /**
     * Calcola il cammino minimo tra due celle della griglia, escludendo gli ostacoli.
     * @param origin La cella di partenza.
     * @param destination La cella di destinazione.
     * @return Un oggetto MinimumPathResult contenente la lunghezza del cammino minimo e la sequenza di landmark.
     */
    public MinimumPathResult calculateMinimumPath(Cell origin, Cell destination, boolean testCorrect) {
    	// Ottieni gli ostacoli dalla griglia
        Set<Cell> obstacles = grid.getObstacles();
        
        
        long startTime = System.nanoTime();
        PathResult result = camminoMinOpt(origin, destination, obstacles);
        long endTime = System.nanoTime();

        MinimumPathResult minimumPathResult = new MinimumPathResult(
				result.getLength(),
				result.getLandmarkSequence(),
				(endTime - startTime)/ 1_000_000.0, // Tempo di esecuzione in millisecondi
		        !Double.isInfinite(result.getLength()),
				totalRecursiveCalls,
				numFrontierCells,
				iterationsFalse
		);
        
        if (testCorrect) {
        	PathResult resultCorrect = camminoMinOpt(origin, destination, obstacles);
        	minimumPathResult.setCorrect(isCorrect(result, resultCorrect));
        }
        totalRecursiveCalls = 0; // Reset per il prossimo calcolo
        iterationsFalse = 0; // Reset per il prossimo calcolo
        numFrontierCells = 0; // Reset per il prossimo calcolo
        
        
        return minimumPathResult;
    }
    
    /**
	 * Algoritmo ricorsivo per calcolare il cammino minimo tra due celle, escludendo gli ostacoli.
	 * @param origin La cella di partenza.
	 * @param destination La cella di destinazione.
	 * @param obstacles Un insieme di celle che rappresentano gli ostacoli.
	 * @return Un oggetto MinimumPathResult contenente la lunghezza del cammino minimo e la sequenza di landmark.
	 */
    private PathResult camminoMin(Cell origin, Cell destination, Set<Cell> obstacles) {
    	
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
            return new PathResult(dlib, seqMin);        }
        
        // ---------- CASO 2: D appartiene al complemento ----------
        Set<Cell> complement = freePathsCalculator.getComplement();
        if (complement.contains(destination)) {
            double dlib = freePathsCalculator.dLib(destination);
            seqMin = compatta(
                    Arrays.asList(new Landmark(origin, 0), new Landmark(destination, 2)),
                    null
                    );
            return new PathResult(dlib, seqMin);
        }
        
        // ---------- CASO 3: D non appartiene né al contesto né al complemento ----------
        Set<Landmark> frontier = freePathsCalculator.getFrontiera();
        if (frontier.isEmpty()) 
            return new PathResult(Double.POSITIVE_INFINITY, new ArrayList<>());
        
        // Converti il Set in una List e ordina per distanza libera dalla destinazione (criterio euristico goloso)
        List<Landmark> sortedFrontier = new ArrayList<>(frontier);
        sortedFrontier.sort((l1, l2) -> {
            double dist1 = freePathsCalculator.dLib(l1.getCell(), destination);
            double dist2 = freePathsCalculator.dLib(l2.getCell(), destination);
            return Double.compare(dist1, dist2);
        });
        numFrontierCells += sortedFrontier.size();
        
        
        // Inizializzazione per il caso ricorsivo
        double lunghezzaMin = Double.POSITIVE_INFINITY;
        
        for (Landmark frontierLandmark : sortedFrontier) { // Itera sui landmark della frontiera ordinata
            Cell frontierCell = frontierLandmark.getCell();
            int t = frontierLandmark.getType();
            
            double lF = freePathsCalculator.dLib(frontierCell);
            double lFD = freePathsCalculator.dLib(frontierCell, destination);
            
            if (lF + lFD >= lunghezzaMin) {
				iterationsFalse++;
				continue;
            } else {
            	
                Set<Cell> newObstacles = new HashSet<>();
                newObstacles.addAll(obstacles);
                newObstacles.addAll(context);
                newObstacles.addAll(complement);
                
                PathResult recursiveResult = camminoMin(frontierCell, destination, newObstacles);
                
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
        return new PathResult(lunghezzaMin, seqMin);
    }

    
    private PathResult camminoMinOpt(Cell origin, Cell destination, Set<Cell> obstacles) {
    	
        totalRecursiveCalls++;
        String key = origin + "->" + destination + "|" + obstacles.hashCode();
        if (pathCache.containsKey(key)) return pathCache.get(key);
        FreePathsExtended freePathsCalculator = freePathsCache.computeIfAbsent(origin, cell -> {
            FreePathsExtended fpe = new FreePathsExtended(grid, cell, obstacles);
            fpe.calculateContextAndComplement();
            return fpe;
        });
        List<Landmark> seqMin = new ArrayList<>();
        
        // ---------- CASO 1: D appartiene al contesto ----------
        Set<Cell> context = freePathsCalculator.getContext();        
        if (context.contains(destination)) {
            double dlib = freePathsCalculator.dLib(destination);
            seqMin = compatta(
                    Arrays.asList(new Landmark(origin, 0), new Landmark(destination, 1)),
                    null
                    );
            PathResult result = new PathResult(dlib, seqMin);
            pathCache.put(key, result);
            return result;        }
        
        // ---------- CASO 2: D appartiene al complemento ----------
        Set<Cell> complement = freePathsCalculator.getComplement();
        if (complement.contains(destination)) {
            double dlib = freePathsCalculator.dLib(destination);
            seqMin = compatta(
                    Arrays.asList(new Landmark(origin, 0), new Landmark(destination, 2)),
                    null
                    );
            PathResult result = new PathResult(dlib, seqMin);
            pathCache.put(key, result);
            return result;
        }
        
        // ---------- CASO 3: D non appartiene né al contesto né al complemento ----------
        Set<Landmark> frontier = freePathsCalculator.getFrontiera();
        if (frontier.isEmpty()) {
            PathResult result = new PathResult(Double.POSITIVE_INFINITY, new ArrayList<>());
            pathCache.put(key, result);
            return result;
        }
        // Converti il Set in una List e ordina per distanza libera dalla destinazione (criterio euristico goloso)
        List<Landmark> sortedFrontier = new ArrayList<>(frontier);
        sortedFrontier.sort((l1, l2) -> {
            double dist1 = freePathsCalculator.dLib(l1.getCell(), destination);
            double dist2 = freePathsCalculator.dLib(l2.getCell(), destination);
            return Double.compare(dist1, dist2);
        });
        numFrontierCells += sortedFrontier.size();
        
        
        // Inizializzazione per il caso ricorsivo
        double lunghezzaMin = Double.POSITIVE_INFINITY;
        
        for (Landmark frontierLandmark : sortedFrontier) { // Itera sui landmark della frontiera ordinata
            Cell frontierCell = frontierLandmark.getCell();
            int t = frontierLandmark.getType();
            
            double lF = freePathsCalculator.dLib(frontierCell);
            double lFD = freePathsCalculator.dLib(frontierCell, destination);
            
            if (lF + lFD >= lunghezzaMin) {
				iterationsFalse++;
				continue;
            } else {
            	
                Set<Cell> newObstacles = new HashSet<>();
                newObstacles.addAll(obstacles);
                newObstacles.addAll(context);
                newObstacles.addAll(complement);
                
                PathResult recursiveResult = camminoMinOpt(frontierCell, destination, newObstacles);
                
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
        
        PathResult result = new PathResult(lunghezzaMin, seqMin);
        pathCache.put(key, result);
        return result;
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
    
    public boolean isCorrect(PathResult result1, PathResult result2) {

    	if (result1.getLength() == Double.POSITIVE_INFINITY && result2.getLength() == Double.POSITIVE_INFINITY) {
    		return true; // Entrambi i risultati indicano che la destinazione non è raggiungibile
		} else {
			double diff = Math.abs(result1.getLength() - result2.getLength());
    		return diff < 1e-6; // Considera corretto se la differenza è molto piccola		
		}
    }
    
}