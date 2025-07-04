package it.unibs.algoritmiPRJ.compito1;
import it.unibs.algoritmiPRJ.utility.GestioneFile;
import it.unibs.algoritmiPRJ.utility.GestioneInput;
import it.unibs.algoritmiPRJ.utility.printer.GridPrinter;
import it.unibs.algoritmiPRJ.utility.printer.ParamsPrinter;

public class MainGrid {

    public static void main(String[] args) {
        System.out.println("========= GENERATORE DI GRIGLIE =========");
        System.out.println("Compito 1 - Disposizione casuale degli ostacoli\n");

        try {
        	
        	GridPrinter gridPrinter = new GridPrinter();
        	ParamsPrinter paramsPrinter = new ParamsPrinter();
        	
        	GestioneFile.creaLegenda();
        	GestioneFile.creaGrids();
        	
        	GridType gridType = GestioneInput.getGridType(">>> Scegli il tipo di griglia (RANDOM, SPARSE, DENSE, MAZE, PATTERN, ROOMS_AND_CORRIDORS): ");
        	int rows = GestioneInput.getIntPosInput(">>> Inserisci numero di righe: ");
        	int cols = GestioneInput.getIntPosInput(">>> Inserisci numero di colonne: ");
        	long seed = GestioneInput.getIntNonNegInput(">>> Inserisci seed (0 per casuale): ");
        	GenerationParams params;
        	if (gridType == GridType.RANDOM) {
        		double obstacleRatio = GestioneInput.getInputObstacleRatio();
        		params = new GenerationParams(rows, cols, obstacleRatio, seed);
        	} else {
        		params = new GenerationParams(rows, cols, seed, gridType);
        	}
        	
            // Generazione griglia
            System.out.println();
            GridGenerator generator = new GridGenerator();
            ArrayGrid grid = generator.generateGrid(params);
            params.setObstacleRatio(grid);
            
            System.out.println(gridPrinter.print(grid));

            if (GestioneInput.confermaSalvataggio()) {
            	String fileName = GestioneFile.creaNewGrid();
            	
    		    gridPrinter.saveToFile(fileName, grid);
    		    paramsPrinter.saveToFile(fileName, params);
    		    System.out.println("Griglia e parametri salvati in: " + fileName);
            }
        } catch (Exception e) {
            System.err.println("Errore: " + e.getMessage());
        } finally {
            GestioneInput.chiudiScanner();
        }   
    }
}