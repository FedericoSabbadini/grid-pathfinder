package it.unibs.algoritmiPRJ.compito3;
import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito1.ArrayGrid;
import it.unibs.algoritmiPRJ.compito2.FreePaths;
import it.unibs.algoritmiPRJ.utility.GestioneInput;
import it.unibs.algoritmiPRJ.utility.loader.GridLoader;
import it.unibs.algoritmiPRJ.utility.printer.FreePathsPrinter;
import it.unibs.algoritmiPRJ.utility.printer.GridPrinter;
import it.unibs.algoritmiPRJ.utility.printer.MinimumPathResultPrinter;

public class MainFreePaths {
    
    public static void main(String[] args) {
        System.out.println("========= CALCOLO CAMMINI LIBERI =========");
        System.out.println("Compito 3 - Calcolo contesto, complemento e distanza libera\n");
        
        GridLoader gridLoader = new GridLoader();
        GridPrinter gridPrinter = new GridPrinter();
        FreePathsPrinter pathsPrinter = new FreePathsPrinter();
        MinimumPathResultPrinter minimumPathPrinter = new MinimumPathResultPrinter();
        
        try {
        	// Carica griglia
        	int numGrid = GestioneInput.getIntPosInput(">>> Numero file griglia da caricare: ");
        	String fileName = "Grids/grid_" + numGrid;
            ArrayGrid grid = (ArrayGrid) gridLoader.loadFile(fileName);
            System.out.println(gridPrinter.print(grid));

            // Imposta Origine
        	System.out.println();
            Cell origin = getValidCell(grid, "origine");
            FreePaths calculator = new FreePaths(grid, origin);
            MinimumPathCalculator calculatorMinimumPath = new MinimumPathCalculator(grid);

            System.out.println();
            
            while (true) {
            	System.out.println("Scegli un'operazione:");
                System.out.println("1. Calcola contesto/complemento");
                System.out.println("2. Calcola cammino libero");
                System.out.println("3. Cambia origine");
                System.out.println("0. Esci");
            	int choice = GestioneInput.getIntNonNegInput(">>> ");
            	System.out.println();

                switch (choice) {
                    case 1 -> {
                        calculator.calculateContextAndComplement();
                        System.out.print(pathsPrinter.print(calculator));
                        pathsPrinter.saveToFile(fileName, calculator);
                    }
                    case 2 -> {
                        origin = calculator.getOrigin();
                        Cell destinazione = getValidCell(grid, "destinazione");
                        if (destinazione == null) return;
                        
                        MinimumPathResult result = calculatorMinimumPath.calculateMinimumPath(origin, destinazione, false);
                        
                        Object[] printerInput = {grid, result, origin, destinazione};
                        System.out.println(minimumPathPrinter.print(printerInput));
                        minimumPathPrinter.saveToFile(fileName, printerInput);
                        
                    }
                    case 3 -> {
                        System.out.println(gridPrinter.print(grid));
                        System.out.println();

						origin = getValidCell(grid, "origine");
						if (origin == null) return;
						calculator.setOrigin(origin);
				    	System.out.println("");
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
    	int row=grid.getRows(), col=grid.getCols(), errorCount = 0, rowO, colO;
    	Cell cell;
    	
    	do {
	    	if (errorCount > 0) {
				System.out.println("Errore: cella non valida o non attraversabile. Riprova.");
			}
	        rowO = GestioneInput.getInputBetween(0, row, ">>> Riga " + tipo + " (0-" + (row-1) + "): ");
	        colO = GestioneInput.getInputBetween(0, col, ">>> Colonna " + tipo + " (0-" + (col-1) + "): ");
	        cell = new Cell(rowO, colO);
			errorCount++;
    	} while (!grid.isTraversable(cell));
    		
        return cell;
    }
}