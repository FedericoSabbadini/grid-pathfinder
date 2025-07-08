# CamminiPRJ - GridPathFinder

Algoritmo ricorsivo per il calcolo di cammini minimi in griglie bidimensionali con ostacoli, sviluppato come progetto per il corso di *Algoritmi e Strutture Dati* (a.a. 2024-25).

## Descrizione

Il progetto si articola in 3 componenti principali:
- **Generatore di griglie** configurabili con ostacoli di diverse tipologie: semplici, agglomerati, diagonali, delimitatori, a barre, ecc.
- **Algoritmo CAMMINOMIN** che, data una griglia e due celle attraversabili (origine e destinazione), calcola un cammino minimo (se esiste), restituendo la sequenza dei landmark e la lunghezza.
- **ExperimentationSystem** che, data una griglia, ne valuta la correttezza e le prestazioni temporali e spaziali a seguito di molteplici invocazioni dell’algoritmo


L'algoritmo sfrutta cammini liberi di tipo 1 e 2, costruisce contesto, complemento e frontiere, e utilizza un approccio ricorsivo per esplorare la griglia con efficienza.

## Funzionalità

- Generazione casuale o semi-casuale di griglie personalizzabili.
- Calcolo del cammino minimo e sua rappresentazione come sequenza di mosse.
- Analisi della distanza libera (`dlib`) tra celle.
- Sperimentazione delle prestazioni in base a parametri configurabili.

## Requisiti

- Linguaggio: Java
- Input/Output da/per file
- attenzione a riuso e manutenibilità del codice
