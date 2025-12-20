package it.unibs.algoritmiPRJ.utility.printer;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito1.ArrayGrid;
import it.unibs.algoritmiPRJ.compito3.Landmark;
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
		ArrayGrid grid = (ArrayGrid) oggetto;
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
	
	/**
	 * Stampa la griglia con i landmark in un formato leggibile.
	 * 
	 * @param grid La griglia da stampare.
	 * @param sequence La sequenza di landmark da stampare.
	 * @return Una stringa che rappresenta la griglia con i landmark.
	 * @throws Exception Se si verifica un errore durante la stampa.
	 */
	public String printLandmark(ArrayGrid grid, List<Landmark> sequence) throws Exception {
		StringBuilder sb = new StringBuilder();
		
        if (grid == null || sequence == null || sequence.isEmpty()) 
        	return new StringBuilder("").toString();
        
        Cell origine = sequence.get(0).getCell();
        Cell destinazione = sequence.get(sequence.size() - 1).getCell();
        
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++) {
            	Cell cell = new Cell(i, j);
            	if (cell.equals(origine)) 
					sb.append(Legenda.ORIGINE);
				else if (cell.equals(destinazione)) 
					sb.append(Legenda.DESTINAZIONE);
            	else if (sequence.contains(new Landmark(cell, 1)))
            		sb.append(Legenda.LANDMARK);
            	else if (sequence.contains(new Landmark(cell, 2)))
            		sb.append(Legenda.LANDMARK);
            	else
            		sb.append(grid.getCells()[i][j] ? Legenda.NON_OSTACOLO : Legenda.OSTACOLO);
            	
                sb.append(" ");
            }
            if (i < grid.getRows() - 1) {
				sb.append("\n");
			}
        }
        sb.append("\n");
        return sb.toString();
	}
}