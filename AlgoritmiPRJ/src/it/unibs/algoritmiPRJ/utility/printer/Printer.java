package it.unibs.algoritmiPRJ.utility.printer;

/**
 * Interfaccia per la stampa e il salvataggio di oggetti in file.
 * Implementazioni specifiche possono definire come gli oggetti devono essere stampati
 * e salvati su file.
 */
public interface Printer {

	/**
	 * Salva l'oggetto specificato in un file.
	 *
	 * @param filepath il percorso del file in cui salvare l'oggetto
	 * @param oggetto l'oggetto da salvare
	 * @throws Exception se si verifica un errore durante il salvataggio
	 */
	void saveToFile(String filepath, Object oggetto) throws Exception;
	
	/**
	 * Stampa una rappresentazione testuale dell'oggetto specificato.
	 *
	 * @param oggetto l'oggetto da stampare
	 * @return una stringa che rappresenta l'oggetto
	 * @throws Exception se si verifica un errore durante la stampa
	 */
	String print(Object oggetto) throws Exception;
}