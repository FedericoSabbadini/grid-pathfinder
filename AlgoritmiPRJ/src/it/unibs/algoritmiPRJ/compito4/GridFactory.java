package it.unibs.algoritmiPRJ.compito4;
import java.util.LinkedHashMap;
import java.util.Map;
import it.unibs.algoritmiPRJ.compito1.GenerationParams;
import it.unibs.algoritmiPRJ.compito1.Grid;

/**
 * Factory per creare diverse implementazioni di griglie.
 * Fornisce un metodo per generare tutte le implementazioni a partire da una griglia originale.
 */
public class GridFactory {
    
    //========================Metodi========================
	/**
	 * Crea una Map contenente tutte le implementazioni di griglie a partire da una griglia originale.
	 * Le implementazioni includono Array2D, BitSet, Sparse e Compressed.
	 * 
	 * @param originalGrid La griglia originale da cui copiare gli ostacoli.
	 * @param params Parametri di generazione per le griglie.
	 * @return Una mappa con i nomi delle implementazioni come chiavi e le rispettive griglie come valori.
	 */
    public static Map<String, Grid> createAllImplementations(Grid originalGrid, GenerationParams params) {
        Map<String, Grid> grids = new LinkedHashMap<>();
        
        grids.put("Array2D", originalGrid);
        Grid[] implementations = {
            new BitSetGrid(params.getRows(), params.getCols()),
            new SparseGrid(params.getRows(), params.getCols()),
            new CompressedGrid(params.getRows(), params.getCols())
        };
        String[] names = {"BitSet", "Sparse", "Compressed"};
        
        for (int i = 0; i < implementations.length; i++) {
            copyObstacles(originalGrid, implementations[i]);
            grids.put(names[i], implementations[i]);
        }
        
        return grids;
    }
    
    
    /**
	 * Copia gli ostacoli dalla griglia di origine alla griglia di destinazione,
	 * così che entrambe le griglie abbiano gli stessi ostacoli e siano coerenti.
	 * 
	 * @param source La griglia di origine da cui copiare gli ostacoli.
	 * @param target La griglia di destinazione in cui impostare gli ostacoli.
	 */
    private static void copyObstacles(Grid source, Grid target) {
        source.getObstacles().forEach(target::setObstacle);
    }
}