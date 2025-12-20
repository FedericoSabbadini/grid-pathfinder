package it.unibs.algoritmiPRJ.utility;
import java.util.InputMismatchException;
import java.util.Scanner;
import it.unibs.algoritmiPRJ.compito1.GridType;

/**
 * Classe per la gestione dell'input da console.
 * Fornisce metodi per ottenere input validi e gestire errori di input.
 */
public class GestioneInput {

    private static final Scanner scanner = new Scanner(System.in);

    /**
	 * Ottiene un input intero positivo dall'utente.
	 *
	 * @param message Messaggio da visualizzare all'utente.
	 * @return Un numero intero positivo inserito dall'utente.
	 */
    public static int getIntPosInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                int num = scanner.nextInt();
                if (num <= 0) throw new IllegalArgumentException("Non valido");
                return num;
            } catch (InputMismatchException e) {
                System.out.println("Errore: Inserire un numero intero valido.");
                scanner.nextLine(); // Pulisce l’input errato
            } catch (IllegalArgumentException e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }
    
    /**
     * Ottiene un input intero compreso tra due valori specificati dall'utente.
     * @param min Il valore minimo accettabile.
     * @param max Il valore massimo accettabile.
     * @return Un numero intero compreso tra min e max inserito dall'utente.
     */
    public static int getInputBetween(int min, int max, String message) {
		while (true) {
			try {
				System.out.print(message);
				int num = scanner.nextInt();
				if (num < min || num > max) {
					throw new IllegalArgumentException("Deve essere compreso tra " + min + " e " + max);
				}
				return num;
			} catch (InputMismatchException e) {
				System.out.println("Errore: Inserire un numero intero valido.");
				scanner.nextLine(); // Pulisce l’input errato
			} catch (IllegalArgumentException e) {
				System.out.println("Errore: " + e.getMessage());
			}
		}
	}
    
    /**
     * Ottiene un input intero non negativo dall'utente.
     * 
     * @param message Messaggio da visualizzare all'utente.
     * @return Un numero intero non negativo inserito dall'utente.
     */
    public static int getIntNonNegInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                int num = scanner.nextInt();
                if (num < 0) throw new IllegalArgumentException("Non valido");
                return num;
            } catch (InputMismatchException e) {
                System.out.println("Errore: Inserire un numero intero valido.");
                scanner.nextLine(); // Pulisce l’input errato
            } catch (IllegalArgumentException e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }

    /**
	 * Ottiene un input intero positivo dall'utente con un messaggio predefinito.
	 *
	 * @return Un numero intero positivo inserito dall'utente.
	 */
    public static double getInputObstacleRatio() {
        while (true) {
            try {
                System.out.print(">>> Inserisci percentuale di ostacoli (0.00-1.00): ");
                String input = scanner.next().replace(",", ".");
                double ratio = Double.parseDouble(input);
                if (ratio < 0.0 || ratio > 1.0) {
                    throw new IllegalArgumentException("Percentuale ostacoli deve essere tra 0.0 e 1.0");
                }
                return ratio;
            } catch (NumberFormatException e) {
                System.out.println("Errore: Inserire un numero decimale valido.");
            } catch (IllegalArgumentException e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
    }
    
    /**
	 * Ottiene un tipo di griglia dall'utente.
	 * @return Il tipo di griglia scelto dall'utente.
	 */
    public static GridType getGridType(String message) {
		while (true) {
			try {
				System.out.print(message);
				String type = scanner.next().toUpperCase();
				if (!type.equals("RANDOM") && !type.equals("SPARSE") && !type.equals("DENSE") 
						&& !type.equals("MAZE") && !type.equals("PATTERN") && !type.equals("ROOMS_AND_CORRIDORS")) {
					throw new IllegalArgumentException("Tipo di griglia non valido");
				}
				return GridType.valueOf(type);
			} catch (IllegalArgumentException e) {
				System.out.println("Errore: " + e.getMessage());
			}
		}
	}

    /**
     * Ottiene un input booleano dall'utente.
     * @return true se l'utente risponde affermativamente, false altrimenti.
     */
    public static boolean confermaSalvataggio() {
        System.out.print(">>> Salvare su file? (s/n): ");
        scanner.nextLine(); // Pulisce newline precedente
        String risposta = scanner.nextLine().toLowerCase();
        return risposta.equals("s") || risposta.equals("si") || risposta.equals("y") || risposta.equals("yes");
    }

    /**
     * Chiude lo scanner utilizzato per l'input.
	 */
    public static void chiudiScanner() {
        scanner.close();
    }
}