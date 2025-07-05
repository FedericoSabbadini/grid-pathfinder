package it.unibs.algoritmiPRJ.utility.printer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import it.unibs.algoritmiPRJ.compito4.ExperimentationSystem;

/**
 * Classe per la stampa dei risultati di sperimentazione.
 * Implementa l'interfaccia Printer.
 */
public class ExperimentationPrinter implements Printer {

	@Override
	public void saveToFile(String filepath, Object oggetto) throws Exception {
		
		ExperimentationSystem system = (ExperimentationSystem) oggetto;
		
		String printedSperimentation = system.runExperimentation();
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath + "/sperimentationOpt.txt"))) {
            writer.print(printedSperimentation);
            
            System.out.println("\nSperimentazione completata!");
            System.out.println("File salvato in: " + filepath + "/sperimentationOpt.txt");

        } catch (IOException e) {
			System.err.println("Errore durante il salvataggio della griglia: " + e.getMessage());
		}
	}

	@Override
	public String print(Object oggetto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}