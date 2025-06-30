package it.unibs.algoritmiPRJ.utility.printer;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import it.unibs.algoritmiPRJ.compito1.Grid;
import it.unibs.algoritmiPRJ.compito2.FreePaths;
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
    		int[] origin = freePaths.getOrigin();
    		filepath = filepath + "/O=("+ origin[0] + ","+ origin[1] + ").txt";
 
            try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
                writer.println("Contesto e Complemento per cella origine O = ("+ origin[0] + ", "+ origin[1] + ")\n");
                writer.print(printedGrid);
                writer.println("\nData generazione: " + new Date());
            }
        } catch (IOException e) {
            System.err.println("Errore salvataggio: " + e.getMessage());
        }
	}
	
	@Override
	public String print(Object oggetto) throws Exception {
		FreePaths freePaths = (FreePaths) oggetto;
		Grid grid = freePaths.getGrid();
		int[] origin = freePaths.getOrigin();
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < grid.getRows(); i++) {
			for (int j = 0; j < grid.getCols(); j++) {
				
				if (i == origin[0] && j == origin[1]) {
					sb.append(Legenda.ORIGINE);
				} else if (!grid.isTraversable(i, j)) {
					sb.append(Legenda.OSTACOLO);
				} else if (freePaths.getContext().contains(freePaths.key(i, j))) {
					sb.append(Legenda.CONTESTO);
				} else if (freePaths.getComplement().contains(freePaths.key(i, j))) {
					sb.append(Legenda.COMPLEMENTO);
				} else {
					sb.append(Legenda.NON_OSTACOLO);
				}
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private String printToFile(String filepath, FreePaths freePaths) throws Exception {
		Grid grid = freePaths.getGrid();
		int[] origin = freePaths.getOrigin();
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < grid.getRows(); i++) {
			for (int j = 0; j < grid.getCols(); j++) {
				if (i == origin[0] && j == origin[1]) {
					sb.append(Legenda.ORIGINE);
				} else if (!grid.isTraversable(i, j)) {
					sb.append(Legenda.OSTACOLO);
				} else if (freePaths.getContext().contains(freePaths.key(i, j))) {
					sb.append(Legenda.CONTESTO_FILE);
				} else if (freePaths.getComplement().contains(freePaths.key(i, j))) {
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