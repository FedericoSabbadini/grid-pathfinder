package it.unibs.algoritmiPRJ.utility.loader;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Classe per il caricamento dei risultati del calcolo del cammino minimo.
 * Implementa l'interfaccia Loader.
 */
public class MinimumPathResultLoader implements Loader {

	@Override
	public Object loadFile(String fileName) throws Exception {
    	
    	File paths = new File(fileName);
    	if (!paths.exists()) 
			paths.createNewFile();
    	
    	try (Scanner scanner = new Scanner(paths)) {
        	
    		StringBuilder content = new StringBuilder();
    		while (scanner.hasNextLine()) 
				content.append(scanner.nextLine()).append("\n");
			return content.toString();
			
		} catch (IOException e) {
			throw new Exception("Errore durante il caricamento dei risultati del cammino minimo: " + e.getMessage());
		}
	}
}