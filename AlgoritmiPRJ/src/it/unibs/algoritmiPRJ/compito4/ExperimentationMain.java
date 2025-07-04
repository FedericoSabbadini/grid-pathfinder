package it.unibs.algoritmiPRJ.compito4;
import it.unibs.algoritmiPRJ.compito1.GenerationParams;
import it.unibs.algoritmiPRJ.compito1.ArrayGrid;
import it.unibs.algoritmiPRJ.utility.GestioneInput;
import it.unibs.algoritmiPRJ.utility.loader.GridLoader;
import it.unibs.algoritmiPRJ.utility.loader.ParamsLoader;
import it.unibs.algoritmiPRJ.utility.printer.ExperimentationPrinter;

public class ExperimentationMain {
    
    public static void main(String[] args) {
    	
        try {
            System.out.println("============ SPERIMENTAZIONE ============");
            System.out.println("Compito 4 - Sperimentazione su griglie\n");
            
            ExperimentationPrinter printer = new ExperimentationPrinter();
            
            GridLoader gridLoader = new GridLoader();
            ParamsLoader paramsLoader = new ParamsLoader();
            
            // Chiede quale griglia testare
            int gridNumber = GestioneInput.getIntPosInput(
                ">>> Inserisci il numero della griglia da testare: ");
            
            String gridPath = "Grids/grid_" + gridNumber;
            System.out.println("\nCaricamento griglia " + gridNumber + "...");
            
            // Carica griglia e parametri
            ArrayGrid grid = (ArrayGrid) gridLoader.loadFile(gridPath);
            GenerationParams params = (GenerationParams) paramsLoader.loadFile(gridPath);
            
            System.out.println("Griglia caricata: " + params.getGridType() + 
                             " [" + params.getRows() + "x" + params.getCols() + "]");
            System.out.println("Avvio sperimentazione...\n");
            
            // Esegue sperimentazione
            ExperimentationSystem system = new ExperimentationSystem(grid, params);
            printer.saveToFile(gridPath, system);
            
            System.out.println("\nSperimentazione completata!");
            System.out.println("File salvato in: " + gridPath + "/sperimentazione.txt");
            
        } catch (Exception e) {
            System.err.println("Errore: " + e.getMessage());
            e.printStackTrace();
        }
    }
}