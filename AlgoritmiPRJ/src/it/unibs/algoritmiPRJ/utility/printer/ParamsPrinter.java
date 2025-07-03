package it.unibs.algoritmiPRJ.utility.printer;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import it.unibs.algoritmiPRJ.compito1.GenerationParams;

/**
 * Classe che implementa l'interfaccia Printer per stampare
 * i parametri di generazione su file.
 * 
 */
public class ParamsPrinter implements Printer {

	@Override
	public void saveToFile(String filepath, Object oggetto) throws Exception {
        
		String printedParams = print(oggetto);

		try (PrintWriter writer = new PrintWriter(new FileWriter(filepath + "/params.txt"))) {
			writer.print(printedParams);
		} catch (IOException e) {
			throw new IOException("Errore durante il salvataggio dei parametri su file: " + e.getMessage());            
        }
	}
	
	@Override
	public String print(Object oggetto) throws Exception {
		GenerationParams params = (GenerationParams) oggetto;
		StringBuilder sb = new StringBuilder();
		
		sb.append("Tipo di griglia: ").append(params.getGridType()).append("\n");
		sb.append("Righe: ").append(params.getRows()).append("\n");
		sb.append("Colonne: ").append(params.getCols()).append("\n");
		sb.append("Percentuale ostacoli: ").append(params.getObstacleRatio()).append("\n");
		sb.append("Ostacoli generati: ").append(params.countObstacles()).append("\n");
		sb.append("Seed: ").append(params.getSeed()).append("\n");
		sb.append("Data generazione: ").append(new Date());
		
		return sb.toString();
	}
}