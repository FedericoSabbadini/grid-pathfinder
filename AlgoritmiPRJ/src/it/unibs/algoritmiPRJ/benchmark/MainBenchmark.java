package it.unibs.algoritmiPRJ.benchmark;

import it.unibs.algoritmiPRJ.compito1.GenerationParams;
import it.unibs.algoritmiPRJ.compito1.GridGenerator;


public class MainBenchmark {
    public static void main(String[] args) {
    	
    	// Inizializzazione dei parametri di generazione della griglia
		// 100 righe, 100 colonne, 10% di ostacoli, seed 42
    	// e 1000 esecuzioni per il benchmark
    	GenerationParams params = new GenerationParams(100, 100, 0.1, 42L);
        GridGenerator generator = new GridGenerator();

        // Creazione del benchmark con i parametri specificati
        GridBenchmark benchmark = new GridBenchmark(generator, params, 1000);
        System.out.println(benchmark.runBenchmark());
    }
}