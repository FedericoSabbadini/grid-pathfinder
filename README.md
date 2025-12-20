# 🗺️ Grid Pathfinder

[![Java](https://img.shields.io/badge/Java-100%25-orange.svg)](https://openjdk.org)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

**Algoritmo ricorsivo per il calcolo di cammini minimi in griglie bidimensionali con ostacoli.**

*Progetto per il corso di Algoritmi e Strutture Dati (a.a. 2024-25).*

## Descrizione

Il progetto si articola in 3 componenti principali:

1. **Generatore di griglie** — Configurabili con ostacoli di diverse tipologie: semplici, agglomerati, diagonali, delimitatori, a barre, ecc.

2. **Algoritmo CAMMINOMIN** — Data una griglia e due celle attraversabili (origine e destinazione), calcola un cammino minimo (se esiste), restituendo la sequenza dei landmark e la lunghezza.

3. **ExperimentationSystem** — Data una griglia, ne valuta la correttezza e le prestazioni temporali e spaziali a seguito di molteplici invocazioni dell'algoritmo.

## Come Funziona

L'algoritmo sfrutta:
- Cammini liberi di tipo 1 e 2
- Costruzione di contesto, complemento e frontiere
- Approccio ricorsivo per esplorare la griglia con efficienza

## Funzionalità

- Generazione casuale o semi-casuale di griglie personalizzabili
- Calcolo del cammino minimo e sua rappresentazione come sequenza di mosse
- Analisi della distanza libera (`dlib`) tra celle
- Sperimentazione delle prestazioni in base a parametri configurabili

## Requisiti

- Linguaggio: Java
- Input/Output da/per file
- Attenzione a riuso e manutenibilità del codice

## Struttura del Progetto

```
grid-pathfinder/
├── grid-pathfinder/    # Implementazione JAVA
|   ├── bin          # File compilati
|   ├── src          # Codice sorgente
|   ├── Grids        # Griglie pre-generate, già utilizzabili
├── Elaborato/       # Specifica del progetto
├── Relazione/       
|   ├── Relazione    # Relazione finale
|   ├── use cases    # Griglie generate cin tutti le richieste eseguite su esse.
└── README.md
```

## License

MIT License — See [LICENSE](LICENSE) for details.
