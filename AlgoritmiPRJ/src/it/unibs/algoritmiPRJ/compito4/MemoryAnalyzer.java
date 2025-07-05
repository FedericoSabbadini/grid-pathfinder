package it.unibs.algoritmiPRJ.compito4;
import it.unibs.algoritmiPRJ.compito1.GenerationParams;

/**
 * Classe per analizzare l'occupazione della memoria e le prestazioni di diverse implementazioni di griglie.
 * Fornisce metodi per calcolare la memoria teorica utilizzata e raccomandare la griglia più adatta
 * in base alla densità degli ostacoli.
 */
public class MemoryAnalyzer {
	
		
	//========================Attributi========================
    private final GenerationParams params;
    private final int traversableCount;
    
    
    //========================Costruttore========================
    /**
	 * Costruttore per inizializzare l'analizzatore di memoria con i parametri della griglia e il conteggio delle celle traversabili.
	 * 
	 * @param params Parametri di generazione della griglia.
	 * @param traversableCount Numero di celle traversabili nella griglia.
	 */
    public MemoryAnalyzer(GenerationParams params, int traversableCount) {
        this.params = params;
        this.traversableCount = traversableCount;
    }
    
    
    //========================Metodi========================
    /**
     * Analizza l'occupazione della memoria per le diverse implementazioni di griglie
     * e fornisce raccomandazioni basate sulla densità degli ostacoli.
     * 
     * @return Una stringa che contiene l'analisi dell'occupazione della memoria per le diverse implementazioni di griglie.
     */
    public String analyze() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n\n----------OCCUPAZIONE--IN--MEMORIA----------\n");
		
		int totalCells = params.getRows() * params.getCols();
		int obstacles = totalCells - traversableCount;
		
		// Calcolo della memoria teorica utilizzata da ciascuna implementazione
		long[] memories = {
			totalCells,                    // Array2D
			(totalCells + 7) / 8 + 64,     // BitSet
			obstacles * 32L + 64,          // Sparse
			(totalCells + 7) / 8 + 32      // Compressed
		};
		String[] names = {"Array2D (boolean[][])", "BitSet", "Sparse (Set<Cell>)", "Compressed (byte[])"};
		
		sb.append("Memoria teorica utilizzata:\n");
		for (int i = 0; i < memories.length; i++) {
		    sb.append(String.format("%2d. %-21s:", i + 1, names[i]));
		    sb.append(String.format("%8d bytes", memories[i]));
		    sb.append(String.format(" (%5.2f kB)", memories[i] / 1024.0));
		    
		    if (i > 0) {
		        double savingPercent = (1 - memories[i] / (double) memories[0]) * 100;
		        sb.append(String.format("   --> Risparmio: %6.1f%%", savingPercent));
		    } else {
		        sb.append("   --> Default");
		    }
		    sb.append("\n");
		}

		sb.append("\n");
		return sb.toString();
	}
    
    /**
     * Analizza la scalabilità delle implementazioni di griglie in base alla dimensione della griglia e al rapporto di ostacoli.
     * 
     * @return Una stringa che contiene l'analisi della scalabilità delle implementazioni di griglie.
     */
    public String analyzeScalability() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n------------------------------ANALISI--SCALABILITA'------------------------------");
				
		int currentSize = params.getRows() * params.getCols();

		int[] sizes = {10000, 200000, 5000000};
		double obstacleRatio = params.getObstacleRatio();
		
		for (int size : sizes) {
			if (size <= currentSize) continue;
			
			int obstacles = (int)(size * obstacleRatio);
			sb.append(String.format("\n\nGriglia %d celle (%.0fx%.0f circa):\n", size, Math.sqrt(size), Math.sqrt(size)));
			sb.append(String.format("  %-10s | %10.1f kB\n", "Array2D", size / 1024.0));
			sb.append(String.format("  %-10s | %10.1f kB\n", "BitSet", ((size + 7) / 8 + 64) / 1024.0));
			sb.append(String.format("  %-10s | %10.1f kB", "Sparse", (obstacles * 32L + 64) / 1024.0));
		}
		
		return sb.toString();
	}
}