package it.unibs.algoritmiPRJ.compito4;
import java.util.*;
import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito1.Grid;
import it.unibs.algoritmiPRJ.compito3.MinimumPathCalculator;
import it.unibs.algoritmiPRJ.compito3.MinimumPathResult;

/**
 * Classe per analizzare le prestazioni di diverse implementazioni di griglie
 * e calcolare il cammino minimo tra celle traversabili.
 * Fornisce metodi per testare la simmetria, analizzare le prestazioni per distanza
 * e confrontare implementazioni diverse.
 */
public class PerformanceAnalyzer {

	//========================Costanti========================
	private static int NUM_TESTS;
	

	//========================Attributi========================
    private final Grid grid;
    private final List<Cell> traversableCells;
    private final Random random = new Random();
    
    
    //========================Costruttore========================
    /**
	 * Costruttore della classe PerformanceAnalyzer.
	 * 
	 * @param grid La griglia su cui eseguire i test.
	 * @param traversableCells Lista delle celle traversabili nella griglia.
	 */
    public PerformanceAnalyzer(Grid grid, List<Cell> traversableCells) {
        this.grid = grid;
        this.traversableCells = traversableCells;
        NUM_TESTS = Math.min(15, traversableCells.size() / 2);
    }
    
    
    //========================Metodi========================
    /**
     * Testa la correttezza tra coppie di celle attraversabili.
     * 
     * @return Stringa che riassume i risultati del test di simmetria tra coppie di celle.
     */
    public String testSymmetry() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n----------ANALISI--CORRETTEZZA----------\n");
		
		int errors = 0;
		
		// Seleziona casualmente coppie di celle traversabili e calcola il cammino minimo
		for (int i = 0; i < NUM_TESTS; i++) {
			Cell[] cells = selectRandomCell();
			if (cells == null) continue;
			
			MinimumPathResult result = new MinimumPathCalculator(grid).calculateMinimumPath(cells[0], cells[1]);
			
			if (!result.isCorrect) errors++;
			
			sb.append(String.format("Test %2d: %-7s<-> %-7s | FreeDistance: %7.2f %s\n", 
				    i + 1, cells[0], cells[1], result.getLength(), result.isCorrect ? "" : "| ERRORE"));

		}
		
		sb.append(String.format("\n>>> Risultato: %d/%d test corretti (%.1f%%)\n\n", 
				NUM_TESTS - errors, NUM_TESTS, (NUM_TESTS - errors) * 100.0 / NUM_TESTS));
		
		return sb.toString();
	}

    /**
     * Analizza le prestazioni del calcolo del cammino minimo
     * in base alla distanza tra le celle. Suddivide i risultati in tre categorie:
     * "Vicine" (d < 10), "Medie" (10 <= d < 25) e "Lontane" (d >= 25).
     * 
     * @return Stringa che riassume i risultati dell'analisi delle prestazioni per distanza.
     */
    public String analyzeByDistance() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n--------------------ANALISI--DISTANZA/TEMPO--------------------\n");
		
		Map<String, List<Double>> timesByDistance = new HashMap<>();
		String[] categories = {"Vicine (d < 10)", "Medie (10 <= d < 25)", "Lontane (d >= 25)"};
		
		Arrays.stream(categories).forEach(c -> timesByDistance.put(c, new ArrayList<>()));
		
		for (int i = 0; i < NUM_TESTS*1.2; i++) {
			Cell[] cells = selectRandomCell();
			if (cells == null) continue;
			
			MinimumPathResult result = new MinimumPathCalculator(grid)
				.calculateMinimumPath(cells[0], cells[1]);
			
			double distance = result.getLength();
			
			if (result.isConnected()) {
				String category = categories[distance < 10 ? 0 : distance < 25 ? 1 : 2];
				timesByDistance.get(category).add(result.getTimeMs());
				sb.append(String.format("Test %2d | FreeDistance: %8.4f | Tempo: %6.2f ms\n", 
                        i + 1, distance, result.getTimeMs()));
			}
		}
		
		sb.append("\n>>> Statistiche per categoria:\n");
		timesByDistance.forEach((cat, times) -> {
			if (!times.isEmpty()) {
				sb.append(String.format("  [%-20s]: %6.2f ms (media su %2d campioni)\n",
				        cat,
				        times.stream().mapToDouble(Double::doubleValue).average().orElse(0),
				        times.size()));

			}
		});
		sb.append("\n");
		
		return sb.toString();
	}

    /**
     * Analizza le prestazioni tra diverse implementazioni di griglie 
     * utilizzando le stesse coppie di celle attraversabili. 
     * 
     * @param implementations Mappa delle implementazioni di griglie da confrontare.
     * @return Stringa che riassume il confronto delle prestazioni tra le implementazioni.
     */
    public String compareImplementations(Map<String, Grid> implementations) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n------------------------------CONFRONTO--STRUTTURE--DATI------------------------------\n");
		
		List<Cell[]> cellsList = new ArrayList<>();
		for (int i = 0; i < NUM_TESTS; i++) {
			Cell[] pair = selectRandomCell();
			if (pair != null) 
				cellsList.add(pair);
		}
		
		if (cellsList.isEmpty()) {
			sb.append("Nessuna coppia valida trovata per il test.\n\n");
			return sb.toString();
		}
				
		Map<String, double[]> stats = new HashMap<>();
		
		// Per ogni implementazione, calcola il cammino minimo per ogni coppia di celle
		implementations.forEach((name, implGrid) -> {
			sb.append(String.format("\n  [  %s  ]\n", name));
			List<Double> times = new ArrayList<>();
			List<Integer> calls = new ArrayList<>(), frontier = new ArrayList<>(), falseIter = new ArrayList<>();
			
			for (int i = 0; i < cellsList.size(); i++) {
				Cell[] cells = cellsList.get(i);

				MinimumPathResult result = new MinimumPathCalculator(implGrid).calculateMinimumPath(cells[0], cells[1]);
				
				if (result.isConnected()) {
					times.add(result.getTimeMs());
					calls.add(result.getRecursiveCalls());
					frontier.add(result.getFrontierSize());
					falseIter.add(result.getIterationsFalse());
					
					sb.append(String.format(
						    "Coppia %2d: (%2d,%2d) → (%2d,%2d) | Tempo: %6.4f ms | Chiamate: %-4d | Frontiere: %-3d | Riga16 Falsi: %-3d\n",
						    i + 1,
						    cells[0].getRow(), cells[0].getCol(),
						    cells[1].getRow(), cells[1].getCol(),
						    result.getTimeMs(),
						    result.getRecursiveCalls(),
						    result.getFrontierSize(),
						    result.getIterationsFalse()
						));

				}
			}
            if (!times.isEmpty()) {
                double[] avgs = {
                    times.stream().mapToDouble(Double::doubleValue).average().orElse(0),
                    calls.stream().mapToInt(Integer::intValue).average().orElse(0),
                    frontier.stream().mapToInt(Integer::intValue).average().orElse(0),
                    falseIter.stream().mapToInt(Integer::intValue).average().orElse(0)
                };
                stats.put(name, avgs);
            }
        });
		
		// Calcola le statistiche medie per ogni implementazione
		sb.append("\n>>> CONFRONTO STATISTICHE\n");
		sb.append(String.format("   %-12s | %-10s | %-9s | %-11s | %-9s\n", 
		                        "Griglia", "Tempo (ms)", "Chiamate", "FrontCells", "Riga16 F"));
		sb.append("-".repeat(65)).append("\n");

		stats.forEach((impl, avgs) -> {
		    sb.append(String.format("   %-12s | %10.2f | %9.0f | %11.0f | %9.0f\n", 
		                            impl, avgs[0], avgs[1], avgs[2], avgs[3]));
		});
		sb.append("\n");
		return sb.toString(); 
    }
					
	/**
	 * Seleziona una coppia casuale di celle attraversabili dalla lista.
	 * 
	 * @return Un array contenente due celle attraversabili, o null se non ci sono abbastanza celle.
	 */
    private Cell[] selectRandomCell() {
        if (traversableCells.size() < 2) return null;
        
        int idx1 = random.nextInt(traversableCells.size());
        int idx2;
        do {
            idx2 = random.nextInt(traversableCells.size());
        } while (idx1 == idx2 );
        
        try {
        	return new Cell[] { traversableCells.get(idx1), traversableCells.get(idx2) };
        } catch (Exception e) {
        	e.getMessage();
        }
		return selectRandomCell();
    }
}