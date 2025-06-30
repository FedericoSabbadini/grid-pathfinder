package it.unibs.algoritmiPRJ.compito2;
import it.unibs.algoritmiPRJ.compito1.Grid;
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
            Grid grid = (Grid) gridLoader.loadFile(fileName);
            System.out.println("Griglia " + grid.getRows() + "x" + grid.getCols() + " caricata.\n");

            // Imposta Origine
            int[] origin = getValidCell(grid, "origine");
            FreePaths calculator = new FreePaths(grid, origin);
            System.out.println();
            
            while (true) {
            	System.out.println("Scegli un'operazione:");
                System.out.println("1. Calcola contesto/complemento");
                System.out.println("2. Calcola distanza libera");
                System.out.println("3. Cambia origine");
                System.out.println("0. Esci");
            	int choice = GestioneInput.getIntNonNegInput(">>> ");

                switch (choice) {
                    case 1 -> {
                        if (origin == null) return;
                        
                        calculator.calculateContextAndComplement();
                        pathsPrinter.print(calculator);
                        pathsPrinter.saveToFile(fileName, calculator);
                        System.out.println("Contesto e Complemento salvati in: " + fileName);
                    }
                    case 2 -> {
                        if (origin == null) return;
                        int[] destinazione = getValidCell(grid, "destinazione");
                        if (destinazione == null) return;
                        
                        double distance = calculator.calculateFreeDistance(destinazione[0], destinazione[1]);
                        if (distance == -1) {
                            System.out.println("Nessun cammino libero possibile.\n");
                        } else {
                            System.out.printf("Distanza libera: %.2f\n", distance);
                        }
                    }
                    case 3 -> {
						origin = getValidCell(grid, "nuova origine");
						if (origin == null) return;
						calculator.setOrigin(origin[0], origin[1]);
					}
                    case 0 -> { 
                        System.out.println("Contesto e Complemento salvati in: " + fileName);
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
   
    
    private static int[] getValidCell(Grid grid, String tipo) {
        int row = GestioneInput.getIntNonNegInput(">>> Riga " + tipo + " (0-" + (grid.getRows()-1) + "): ");
        int col = GestioneInput.getIntNonNegInput(">>> Colonna " + tipo + " (0-" + (grid.getCols()-1) + "): ");
        
        if (row < 0 || row >= grid.getRows() || col < 0 || col >= grid.getCols() || 
            !grid.isTraversable(row, col)) {
            System.out.println("Cella non valida.");
            return null;
        }
        return new int[]{row, col};
    }
}