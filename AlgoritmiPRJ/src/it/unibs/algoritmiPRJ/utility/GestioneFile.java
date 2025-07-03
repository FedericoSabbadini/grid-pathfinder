package it.unibs.algoritmiPRJ.utility;
import java.io.File;
import java.io.IOException;
import it.unibs.algoritmiPRJ.utility.printer.LegendaPrinter;

/**
 * Classe per la gestione dei file e delle directory.
 * Fornisce metodi per creare directory, scrivere file e contare file in una directory.
 */
public class GestioneFile {

    /**
     * Crea una directory se non esiste.
     * 
     * @param path Percorso della directory.
     * @return true se la directory è stata creata o già esiste.
     */
    private static boolean creaDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return true;
    }

    /**
     * Conta le sottocartelle in una directory, escludendo quelli nascosti.
     * 
     * @param dirPath Percorso della directory.
     * @return Numero di file (non nascosti).
     */
    private static int contaCartelle(String directoryName) {
        File directory = new File(directoryName);
        if (!directory.exists() || !directory.isDirectory()) {
            return 0;
        }

        File[] subdirs = directory.listFiles(File::isDirectory);
        return subdirs != null ? subdirs.length : 0;
    }

    /**
	 * Crea una legenda se non esiste già.
	 * 
	 * @return true se la legenda è stata creata, false se già esiste.
	 * @throws Exception Se ci sono problemi durante la creazione della legenda.
	 */
    public static boolean creaLegenda() throws Exception {
        String legendaPath = "Grids/Legenda.txt";
		File legendaFile = new File(legendaPath);
		if (!legendaFile.exists()) {
			try {
				creaDirectory("Grids");
				LegendaPrinter legendaPrinter = new LegendaPrinter();
				legendaPrinter.saveToFile(legendaPath, null);
				return true;
			} catch (IOException e) {
				System.err.println("Errore nella creazione della Legenda: " + e.getMessage());
			}
		}
		return false;
    }
    
    /**
     * Crea la directory "Grids" se non esiste già.
     * 
     * @return true se la directory "Grids" è stata creata o già esiste, false altrimenti.
     */
    public static boolean creaGrids() {
		String gridsPath = "Grids";
		File gridsDir = new File(gridsPath);
		if (!gridsDir.exists()) {
			return gridsDir.mkdirs();
		}
		return true;
	}
    
    /**
	 * Crea un nuovo grid con un nome unico basato sul conteggio dei file esistenti.
	 * 
	 * @return Il percorso del nuovo grid creato.
	 */
    public static String creaNewGrid () {
    	int fileCount = contaCartelle("Grids");
    	String filename = "Grids/" + "grid_" + (fileCount + 1);
    	File gridsDirP = new File(filename);
    	if (!gridsDirP.exists()) {
			gridsDirP.mkdirs();
		}
		return filename;
    }	
}