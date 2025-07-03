package it.unibs.algoritmiPRJ.utility.printer;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito3.MinimumPathResult;
import it.unibs.algoritmiPRJ.utility.loader.MinimumPathResultLoader;

/**
 * Classe per stampare i risultati del calcolo del cammino minimo.
 * Implementa l'interfaccia Printer.
 */
public class MinimumPathResultPrinter implements Printer {

	@Override
	public void saveToFile(String fileName, Object oggetto) throws Exception {
    	String filepath = fileName + "/paths.txt";

		MinimumPathResultLoader minimumPathResultLoader = new MinimumPathResultLoader();
		String newPath = print(oggetto);
		String oldPath = (String) minimumPathResultLoader.loadFile(filepath);
		
		if (oldPath == null || oldPath.isEmpty()) 
			oldPath = "# File contenente i cammini minimi calcolati\n";
		String concatPaths = oldPath + newPath;
		
		try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
			writer.print(concatPaths);
		} catch (IOException e) {
			System.err.println("Errore durante il salvataggio: " + e.getMessage());
		} 
		
		
	}

	@Override
	public String print(Object oggetto) throws Exception {

	    Object[] array = (Object[]) oggetto;
	    MinimumPathResult result = (MinimumPathResult) array[0];
	    Cell origin = (Cell) array[1];
	    Cell destination = (Cell) array[2];
		
		StringBuilder sb = new StringBuilder();
		sb.append("\n  Origine: ").append(origin.toString()).append("\n");
		sb.append("  Destinazione: ").append(destination.toString()).append("\n");

		if (result.getLength() == Double.POSITIVE_INFINITY) {
			sb.append("Destinazione non raggiungibile!\n");
			sb.append("Lunghezza: ∞\n");
			sb.append("Sequenza landmark: []\n");
		} else {
			sb.append("Lunghezza cammino minimo: ").append(String.format("%.2f", result.getLength())).append("\n");
			sb.append("Sequenza landmark: ").append(result.getLandmarkSequence()).append("\n");
			sb.append("Numero di chiamate ricorsive: ").append(result.getRecursiveCalls()).append("\n");
		}
		return sb.toString();
	}
}