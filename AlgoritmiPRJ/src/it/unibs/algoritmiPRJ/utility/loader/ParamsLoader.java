package it.unibs.algoritmiPRJ.utility.loader;
import java.io.File;
import java.util.Scanner;
import it.unibs.algoritmiPRJ.compito1.GenerationParams;

/**
 * Classe per il caricamento dei parametri di generazione da un file.
 * Implementa l'interfaccia Loader.
 */
public class ParamsLoader implements Loader {

	@Override
	public Object loadFile(String filePath) throws Exception {
    	String fileParams = filePath + "/params.txt";

    	try (Scanner scanner = new Scanner(new File(fileParams))) {
			int rows = -1;
			int cols = -1;
			double obstacleRatio = -1.0;
			long seed = -1;

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.startsWith("Righe:")) {
					rows = Integer.parseInt(line.substring("Righe:".length()).trim());
				} else if (line.startsWith("Colonne:")) {
					cols = Integer.parseInt(line.substring("Colonne:".length()).trim());
				} else if (line.startsWith("Ostacoli:")) {
					obstacleRatio = Double.parseDouble(line.substring("Ostacoli:".length()).trim());
				} else if (line.startsWith("Seed:")) {
					seed = Long.parseLong(line.substring("Seed:".length()).trim());
				}
			}
			return new GenerationParams(rows, cols, obstacleRatio, seed);
			
		} catch (Exception e) {
			throw new Exception("Errore durante il caricamento dei parametri: " + e.getMessage());
		}
	}
}