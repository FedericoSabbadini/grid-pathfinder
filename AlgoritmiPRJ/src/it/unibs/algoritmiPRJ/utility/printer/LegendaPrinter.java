package it.unibs.algoritmiPRJ.utility.printer;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import it.unibs.algoritmiPRJ.utility.Legenda;

/**
 * Classe per stampare la legenda della tabella.
 * Implementa l'interfaccia Printer.
 */
public class LegendaPrinter implements Printer {

	@Override
	public String print(Object oggetto) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append("Legenda tabella:\n");
        sb.append("--> " + Legenda.ORIGINE).append(" = origine\n");
        sb.append("--> " + Legenda.OSTACOLO + " Nero").append(" = ostacolo\n");
        sb.append("--> " + Legenda.NON_OSTACOLO).append(" = non ostacolo\n\n");
        
        sb.append("--> " + Legenda.OSTACOLO + " Azzurro").append(" = cella del contesto\n");
        sb.append("--> " + Legenda.OSTACOLO + " Azzurro Chiaro").append(" = cella frontiera del contesto\n");
        sb.append("--> " + Legenda.CONTESTO_FILE).append(" = cella del contesto (file)\n\n");
        
        sb.append("--> " + Legenda.OSTACOLO + " Rosso").append(" = cella del complemento\n");
        sb.append("--> " + Legenda.OSTACOLO + " Rosso Chiaro").append(" = cella frontiera del complemento\n");
        sb.append("--> " + Legenda.COMPLEMENTO_FILE).append(" = cella del complemento (file)");
               
        return sb.toString();
	}

	@Override
	public void saveToFile(String filepath, Object oggetto) throws Exception {
		String printedLegenda = print(null);
		
		try (PrintWriter writer = new PrintWriter(new FileWriter(filepath))) {
			writer.print(printedLegenda);
		} catch (IOException e) {
			System.err.println("Errore durante il salvataggio della legenda: " + e.getMessage());
		}		
	}
}