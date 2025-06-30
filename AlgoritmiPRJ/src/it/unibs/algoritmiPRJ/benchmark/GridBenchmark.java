package it.unibs.algoritmiPRJ.benchmark;

import it.unibs.algoritmiPRJ.compito1.GenerationParams;
import it.unibs.algoritmiPRJ.compito1.GridGenerator;

/**
 * GridBenchmark esegue un benchmark per confrontare le prestazioni
 * di due metodi di generazione di griglie: uno ottimizzato e uno legacy.
 * Misura il tempo medio di esecuzione per entrambi i metodi su un numero specificato di esecuzioni.
 */
public class GridBenchmark {

	// Attributi
    private final GridGenerator generator;
    private final GenerationParams params;
    private final int runs;

    /**
	 * Costruttore per inizializzare il benchmark con un generatore di griglie,
	 * parametri di generazione e numero di esecuzioni.
	 *
	 * @param generator Il generatore di griglie da utilizzare.
	 * @param params I parametri di generazione della griglia.
	 * @param runs Il numero di esecuzioni per il benchmark.
	 */
    public GridBenchmark(GridGenerator generator, GenerationParams params, int runs) {
        this.generator = generator;
        this.params = params;
        this.runs = runs;
    }

    public String runBenchmark() {
        long totalOptimized = 0;
        long totalLegacy = 0;

        // Warm-up JVM (opzionale ma consigliato)
        for (int i = 0; i < 100; i++) {
            generator.generateRandomGrid(params);
            //generator.generateRandomGridLegacy(params);
        }

        for (int i = 0; i < runs; i++) {
            long start = System.nanoTime();
            generator.generateRandomGrid(params);
            totalOptimized += System.nanoTime() - start;

            start = System.nanoTime();
            //generator.generateRandomGridLegacy(params);
            totalLegacy += System.nanoTime() - start;
        }

        // Stampa dei risultati del benchmark
        String benchmarkResults = String.format(
			"Benchmark risultati (%d esecuzioni):\n" +
			"Tempo medio ottimizzato: %d ns\n" +
			"Tempo medio legacy: %d ns\n" +
			"Velocità relativa: %.2fx più veloce",
			runs, totalOptimized / runs, totalLegacy / runs,
			(double) totalLegacy / totalOptimized
		);
        
		return benchmarkResults;
    }
}