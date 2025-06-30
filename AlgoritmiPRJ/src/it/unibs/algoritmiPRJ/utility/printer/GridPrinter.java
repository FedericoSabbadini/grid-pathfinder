package it.unibs.algoritmiPRJ.utility.printer;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import it.unibs.algoritmiPRJ.compito1.Grid;
import it.unibs.algoritmiPRJ.utility.Legenda;

/**
 * Classe per la stampa della griglia in un file di testo.
 * Implementa l'interfaccia Printer.
 */
public class GridPrinter implements Printer {

	@Override
	public void saveToFile(String filepath, Object oggetto) throws Exception {

		String printedGrid = print(oggetto);
		
        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath + "/grid.txt"))) {
            writer.print(printedGrid);
        } catch (IOException e) {
			System.err.println("Errore durante il salvataggio della griglia: " + e.getMessage());
		}
	}
	
	@Override
	public String print(Object oggetto) throws Exception {
		Grid grid = (Grid) oggetto;
		StringBuilder sb = new StringBuilder();
		
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++) {
                sb.append(grid.getCells()[i][j] ? Legenda.NON_OSTACOLO : Legenda.OSTACOLO);
                sb.append(" ");
            }
            if (i < grid.getRows() - 1) {
				sb.append("\n");
			}
        }
        return sb.toString();
	}
}