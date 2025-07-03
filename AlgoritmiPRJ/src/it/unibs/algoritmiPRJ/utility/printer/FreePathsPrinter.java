package it.unibs.algoritmiPRJ.utility.printer;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Set;
import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito2.FreePaths;
import it.unibs.algoritmiPRJ.compito3.Landmark;
import it.unibs.algoritmiPRJ.utility.Legenda;

/**
 * Classe per la stampa di un oggetto FreePaths.
 * Implementa l'interfaccia Printer.
 */
public class FreePathsPrinter implements Printer {

	@Override
	public void saveToFile(String filepath, Object oggetto) throws Exception {
    	try {
    		FreePaths freePaths = (FreePaths) oggetto;
    		String printedGrid = printToFile(filepath, freePaths);
    		Cell origin = freePaths.getOrigin();
    		filepath = filepath + "/O=" + origin.toString() + ".txt";
 
            try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
                writer.println("# Chiusura della cella origine O = "+ origin.toString() + "\n");
                writer.print(printedGrid);
                writer.print("\nData generazione: " + new Date());
            }
        } catch (IOException e) {
            System.err.println("Errore salvataggio: " + e.getMessage());
        }
	}
	
	@Override
	public String print(Object oggetto) throws Exception {
		FreePaths freePaths = (FreePaths) oggetto;
		Cell origin = freePaths.getOrigin();
		Set<Landmark> frontiera = freePaths.getFrontiera();

		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < freePaths.getRows(); i++) {
			for (int j = 0; j < freePaths.getCols(); j++) {
				Cell newCell = new Cell(i, j);
				
				if (i == origin.getRow() && j == origin.getCol()) {
					sb.append(Legenda.ORIGINE);
				} else if (!freePaths.isTraversable(newCell)) {
					sb.append(Legenda.OSTACOLO);
				} else if (frontiera.contains(new Landmark(newCell, 1))) {
					sb.append(Legenda.FRONTIERA_CONTESTO);
				} else if (freePaths.getContext().contains(newCell)) {
					sb.append(Legenda.CONTESTO);
				} else if (frontiera.contains(new Landmark(newCell, 2))) {
					sb.append(Legenda.FRONTIERA_COMPLEMENTO);
				} else if (freePaths.getComplement().contains(newCell)) {
					sb.append(Legenda.COMPLEMENTO);
				} else {
					sb.append(Legenda.NON_OSTACOLO);
				}
				sb.append(" ");
			}
			sb.append("\n");
		}
		sb.append("\n");
		return sb.toString();
	}
	
	private String printToFile(String filepath, FreePaths freePaths) throws Exception {
		Cell origin = freePaths.getOrigin();
		Set<Landmark> frontiera = freePaths.getFrontiera();
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < freePaths.getRows(); i++) {
			for (int j = 0; j < freePaths.getCols(); j++) {
				Cell newCell = new Cell(i, j);

				if (i == origin.getRow() && j == origin.getCol()) {
					sb.append(Legenda.ORIGINE);
				} else if (!freePaths.isTraversable(newCell)) {
					sb.append(Legenda.OSTACOLO);
				} else if (frontiera.contains(new Landmark(newCell, 1))) {
					sb.append(Legenda.FRONTIERA_CONTESTO_FILE);
				} else if (freePaths.getContext().contains(newCell)) {
					sb.append(Legenda.CONTESTO_FILE);
				} else if (frontiera.contains(new Landmark(newCell, 2))) {
					sb.append(Legenda.FRONTIERA_COMPLEMENTO_FILE);
				} else if (freePaths.getComplement().contains(newCell)) {
					sb.append(Legenda.COMPLEMENTO_FILE);
				} else {
					sb.append(Legenda.NON_OSTACOLO);
				}
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}