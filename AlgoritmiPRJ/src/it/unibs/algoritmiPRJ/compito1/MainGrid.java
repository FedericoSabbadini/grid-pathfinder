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
        	
        	// Inizializza stampatori
        	GridPrinter gridPrinter = new GridPrinter();
        	ParamsPrinter paramsPrinter = new ParamsPrinter();
        	
        	// Crea cartelle e file di supporto
        	GestioneFile.creaLegenda();
        	GestioneFile.creaGrids();
        	
            // Input parametri
        	int rows = GestioneInput.getIntPosInput(">>> Inserisci numero di righe: ");
        	int cols = GestioneInput.getIntPosInput(">>> Inserisci numero di colonne: ");
        	double obstacleRatio = GestioneInput.getInputObstacleRatio();
        	long seed = GestioneInput.getIntNonNegInput(">>> Inserisci seed (0 per casuale): ");
        	
            // Generazione griglia
            System.out.println();
            GenerationParams params = new GenerationParams(rows, cols, obstacleRatio, seed);
            GridGenerator generator = new GridGenerator();
            Grid grid = generator.generateRandomGrid(params);
            System.out.println(gridPrinter.print(grid));

            if (GestioneInput.confermaSalvataggio()) {
            	String fileName = GestioneFile.creaNewGrid();
            	
    		    // Salva griglia
    		    gridPrinter.saveToFile(fileName, grid);
    		    
    		    // Salva parametri
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