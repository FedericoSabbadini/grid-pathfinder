package it.unibs.algoritmiPRJ.utility.loader;
import java.io.File;
import java.util.Scanner;

import it.unibs.algoritmiPRJ.compito1.Cell;
import it.unibs.algoritmiPRJ.compito1.GenerationParams;
import it.unibs.algoritmiPRJ.compito1.Grid;
import it.unibs.algoritmiPRJ.utility.Legenda;

/**
 * Classe per il caricamento della griglia da un file.
 * La griglia è rappresentata da un file di testo che contiene le celle,
 * dove ogni cella può essere un ostacolo o vuota.
 */
public class GridLoader implements Loader {

	@Override
	public Object loadFile(String filePath) throws Exception {

		String fileGrid = filePath + "/grid.txt";
		int [] gridSize = getGridSize(filePath);
		int rows = gridSize[0];
		int cols = gridSize[1];
    	
        try (Scanner scanner = new Scanner(new File(fileGrid))) {; 
            Grid grid = new Grid(rows, cols);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    String cellValue = scanner.next();
                    if (cellValue.equals(Legenda.OSTACOLO)) {
                        grid.setObstacle(new Cell(i, j));
                    }
                }
            }
            
            return grid;
        } catch (Exception e) {
			throw new Exception("Errore durante il caricamento della griglia: " + e.getMessage());
		}
	}
	
	private int[] getGridSize(String filePath) throws Exception {
		ParamsLoader paramsLoader = new ParamsLoader();
		GenerationParams params = (GenerationParams) paramsLoader.loadFile(filePath);
		
		if (params == null) {
			throw new Exception("Impossibile caricare i parametri della griglia da: " + filePath);
		}
		int rows = params.getRows();
		int cols = params.getCols();
		
		return new int[]{rows, cols};
	}
}