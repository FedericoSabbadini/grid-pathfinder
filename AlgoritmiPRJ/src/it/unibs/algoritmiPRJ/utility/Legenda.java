package it.unibs.algoritmiPRJ.utility;

/**
 * Classe per gestire la legenda della tabella di generazione delle griglie.
 * Contiene i colori e i simboli utilizzati per rappresentare le celle.
 */
public class Legenda {

    // Colori 
    public static final String RESET  = "\u001B[0m";
    public static final String ROSSO = "\u001B[31m";
    public static final String AZZURRO = "\u001B[36m";
    public static final String ROSSO_CHIARO = "\u001B[38;5;218m";
    public static final String AZZURRO_CHIARO = "\u001B[96m";

    // Simboli
    public static final String ORIGINE = "O";
    public static final String DESTINAZIONE = "D";
    public static final String OSTACOLO = "■";
    public static final String NON_OSTACOLO = ".";
    public static final String CONTESTO = AZZURRO + "■" + RESET;
    public static final String CONTESTO_FILE = "+";
    public static final String COMPLEMENTO = ROSSO + "■" + RESET;
    public static final String COMPLEMENTO_FILE = "-";
    public static final String FRONTIERA_CONTESTO_FILE = "*";
    public static final String FRONTIERA_COMPLEMENTO_FILE = "/";
    public static final String FRONTIERA_CONTESTO =  AZZURRO_CHIARO + "■" + RESET;
    public static final String FRONTIERA_COMPLEMENTO =  ROSSO_CHIARO + "■" + RESET;
    public static final String LANDMARK = "□";
    
}