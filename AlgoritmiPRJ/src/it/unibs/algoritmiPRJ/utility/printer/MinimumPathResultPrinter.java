package it.unibs.algoritmiPRJ.utility.printer;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito1.ArrayGrid;
import it.unibs.algoritmiPRJ.compito3.Landmark;
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

		GridPrinter gridPrinter = new GridPrinter();
		
	    Object[] array = (Object[]) oggetto;
	    ArrayGrid grid = (ArrayGrid) array[0];
	    MinimumPathResult result = (MinimumPathResult) array[1];
	    List<Landmark> sequence = result.getLandmarkSequence();
	    Cell origin = (Cell) array[2];
	    Cell destination = (Cell) array[3];
		
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
		}
		sb.append("Numero di chiamate ricorsive: ").append(result.getRecursiveCalls()).append("\n");
		sb.append("Numero di celle di frontiera: ").append(result.getFrontierSize()).append("\n");
		sb.append("Numero iterazioni [lF + lFD < lunghezzaMin]==false: ").append(result.getIterationsFalse()).append("\n");
		sb.append(" > Prestazioni: ").append(String.format("%.2f ms", result.getTimeMs())).append("\n");

		sb.append(gridPrinter.printLandmark(grid, sequence));
		return sb.toString();
	}
}