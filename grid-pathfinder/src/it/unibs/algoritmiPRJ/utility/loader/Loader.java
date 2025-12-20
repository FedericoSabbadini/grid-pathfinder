package it.unibs.algoritmiPRJ.utility.loader;

/**
 * Interfaccia per il caricamento di file.
 */
public interface Loader {

	/**
	 * Metodo per caricare un file specificato dal percorso.
	 * @param filePath il percorso del file da caricare
	 * @return un oggetto rappresentante il contenuto del file caricato
	 * @throws Exception 
	 */
	Object loadFile(String filePath) throws Exception;
}