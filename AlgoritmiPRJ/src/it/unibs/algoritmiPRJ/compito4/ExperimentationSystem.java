package it.unibs.algoritmiPRJ.compito4;
import java.util.*;
import it.unibs.algoritmiPRJ.compito1.*;

/**
 * Sistema di sperimentazione per analizzare le prestazioni degli algoritmi di cammino minimo
 * su diverse implementazioni di griglie e parametri di generazione.
 * Esegue test di correttezza, analisi delle prestazioni e scalabilità.
 */
public class ExperimentationSystem {
    
	
	//========================Attributi========================
    private final ArrayGrid originalGrid;
    private final GenerationParams params;
    private final List<Cell> traversableCells;
    private final Map<String, Grid> gridImplementations;
    private final MemoryAnalyzer memoryAnalyzer;
    private final PerformanceAnalyzer performanceAnalyzer;
    
    
    //========================Costruttore========================
    /**
     * Costruttore per inizializzare il sistema di sperimentazione.
     * @param grid Griglia originale da testare.
     * @param params Parametri di generazione della griglia.
     */
    public ExperimentationSystem(ArrayGrid grid, GenerationParams params) {
        this.originalGrid = grid;
        this.params = params;
        this.traversableCells = originalGrid.getTraversableCells();
        this.gridImplementations = GridFactory.createAllImplementations(originalGrid, params);
        this.memoryAnalyzer = new MemoryAnalyzer(params, traversableCells.size());
        this.performanceAnalyzer = new PerformanceAnalyzer(originalGrid, traversableCells);
    }
    
    
    //========================Metodi========================
    /**
	 * Esegue la sperimentazione analizzando le prestazioni degli algoritmi di cammino minimo
	 * su diverse implementazioni di griglie e parametri di generazione.
	 * @return Stringa con i risultati della sperimentazione.
	 */
    public String runExperimentation() {
    	
    	StringBuilder output = new StringBuilder();
        long startTime = System.nanoTime();
    	
    	output.append(initializeOutput());
    	output.append(memoryAnalyzer.analyze());
    	output.append(performanceAnalyzer.testSymmetry());
    	output.append(performanceAnalyzer.analyzeByDistance());
    	output.append(performanceAnalyzer.compareImplementations(gridImplementations));
    	output.append(memoryAnalyzer.analyzeScalability());
    	
    	
        long endTime = System.nanoTime();
        String sperimentationTime = String.format(
        	    "\n\n\n\n---------- TEMPO--DI--SPERIMENTAZIONE ----------\n" +
        	    "\nTempo totale: %.3f ms\n", 
        	    (endTime - startTime) / 1_000_000.0
        	);				
        output.append(sperimentationTime);
        
		return output.toString();
    }
    
    
    /**
	 * Inizializza il file di output con le informazioni di base sulla sperimentazione.
	 * @return Stringa di intestazione del file di output.
	 */
    private String initializeOutput() {
		return String.format("\n========== SPERIMENTAZIONE CAMMINO MINIMO E STRUTTURE DATI ==========\n\n" +
							 "Griglia: %s [%dx%d]\nCelle attraversabili: %d\nOstacoli: %.1f%%\n\n",
							 params.getGridType(), params.getRows(), params.getCols(),
							 traversableCells.size(), params.getObstacleRatio() * 100);
	}
}