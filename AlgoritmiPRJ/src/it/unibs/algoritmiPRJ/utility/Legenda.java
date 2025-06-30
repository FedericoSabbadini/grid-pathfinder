package it.unibs.algoritmiPRJ.utility;

/**
 * Classe per gestire la legenda della tabella di generazione delle griglie.
 * Contiene i colori e i simboli utilizzati per rappresentare le celle.
 */
public class Legenda {

    // Colori
    public static final String RESET  = "\u001B[0m";
    public static final String RED    = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";

    // Simboli
    public static final String ORIGINE = "O";
    public static final String OSTACOLO = "■";
    public static final String NON_OSTACOLO = ".";
    public static final String CONTESTO = YELLOW + "■" + RESET;
    public static final String CONTESTO_FILE = "+";
    public static final String COMPLEMENTO = RED + "■" + RESET;
    public static final String COMPLEMENTO_FILE = "-";
}