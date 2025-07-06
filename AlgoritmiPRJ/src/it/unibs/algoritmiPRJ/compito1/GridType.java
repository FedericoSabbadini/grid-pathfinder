package it.unibs.algoritmiPRJ.compito1;

/**
 * Enum che rappresenta i tipi di griglia disponibili per la generazione.
 * Ogni tipo di griglia ha caratteristiche specifiche per la sperimentazione.
 */
public enum GridType {
    RANDOM, // Griglia con ostacoli casuali, caso classico
    MAZE, // Griglia tipo labirinto, con percorsi e ostacoli
    PATTERN, // Griglia con un pattern regolare di ostacoli
    ROOMS_AND_CORRIDORS, // Griglia con stanze e corridoi
    SPARSE, // Griglia con pochi ostacoli, per esempio 5% di celle ostacolo
    DENSE // Griglia con molti ostacoli, per esempio 55% di celle ostacolo
}