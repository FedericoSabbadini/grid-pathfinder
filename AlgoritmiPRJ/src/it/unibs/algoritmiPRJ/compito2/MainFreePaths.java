package it.unibs.algoritmiPRJ.compito2;
import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito1.ArrayGrid;
import it.unibs.algoritmiPRJ.utility.GestioneInput;
import it.unibs.algoritmiPRJ.utility.loader.GridLoader;
import it.unibs.algoritmiPRJ.utility.printer.FreePathsPrinter;

public class MainFreePaths {
    
    public static void main(String[] args) {
        System.out.println("========= CALCOLO CAMMINI LIBERI =========");
        System.out.println("Compito 2 - Calcolo contesto, complemento e distanza libera\n");
        
        GridLoader gridLoader = new GridLoader();
        FreePathsPrinter pathsPrinter = new FreePathsPrinter();
        
        try {
        	// Carica griglia
        	int numGrid = GestioneInput.getIntPosInput(">>> Numero file griglia da caricare: ");
        	String fileName = "Grids/grid_" + numGrid;
            ArrayGrid grid = (ArrayGrid) gridLoader.loadFile(fileName);
            System.out.println("Griglia " + grid.getRows() + "x" + grid.getCols() + " caricata.");

            // Imposta Origine
            Cell origin = getValidCell(grid, "origine");
            FreePaths calculator = new FreePaths(grid, origin);
            System.out.println();
            
            while (true) {
            	System.out.println("Scegli un'operazione:");
                System.out.println("1. Calcola contesto/complemento");
                System.out.println("2. Calcola distanza libera");
                System.out.println("3. Cambia origine");
                System.out.println("0. Esci");
            	int choice = GestioneInput.getIntNonNegInput(">>> ");
            	System.out.println();

                switch (choice) {
                    case 1 -> {
                        if (origin == null) return;
                        
                        calculator.calculateContextAndComplement();
                        System.out.print(pathsPrinter.print(calculator));
                        pathsPrinter.saveToFile(fileName, calculator);
                    }
                    case 2 -> {
                        if (origin == null) return;
                        Cell destinazione = getValidCell(grid, "destinazione");
                        if (destinazione == null) return;
                        
                        double distance = calculator.dLib(destinazione);
                        if (distance == -1) {
                            System.out.println("Nessun cammino libero possibile.\n");
                        } else {
                            System.out.printf("Distanza libera: %.2f\n", distance);
                        }
                    }
                    case 3 -> {
						origin = getValidCell(grid, "nuova origine");
						if (origin == null) return;
						calculator.setOrigin(origin);
					}
                    case 0 -> { 
                        System.out.println("Chiusura di O salvata in: " + fileName);
                        return; 
                    }
                    default -> System.out.println("Scelta non valida.");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println("Errore: " + e.getMessage());
        } finally {
        	GestioneInput.chiudiScanner();
        }
    }
   
    /**
	 * Chiede all'utente di inserire una cella valida (attraversabile) nella griglia.
	 * 
	 * @param grid La griglia in cui cercare la cella.
	 * @param tipo Il tipo di cella da inserire (origine, destinazione, etc.).
	 * @return La cella valida inserita dall'utente.
	 */
    private static Cell getValidCell(ArrayGrid grid, String tipo) {
    	System.out.println("");
    	int row=grid.getRows(), col=grid.getCols(), errorCount = 0, rowO, colO;
    	Cell cell;
    	
    	do {
	    	if (errorCount > 0) {
				System.out.println("Errore: cella non valida o non attraversabile. Riprova.");
			}
	        rowO = GestioneInput.getInputBetween(0, row, ">>> Riga " + tipo + " (0-" + (grid.getRows()-1) + "): ");
	        colO = GestioneInput.getInputBetween(0, col, ">>> Colonna " + tipo + " (0-" + (grid.getCols()-1) + "): ");
	        cell = new Cell(rowO, colO);
			errorCount++;
    	} while (!grid.isTraversable(cell));
    		
        return cell;
    }
}