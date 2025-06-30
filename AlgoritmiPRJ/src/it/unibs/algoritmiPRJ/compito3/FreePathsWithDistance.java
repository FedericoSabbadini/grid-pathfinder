package it.unibs.algoritmiPRJ.compito3;

import java.util.*;
import it.unibs.algoritmiPRJ.compito1.Grid;

/**
 * Implementazione corretta per il calcolo di cammini liberi, contesto e complemento.
 */
public class FreePathsWithDistance {
    
    private Grid grid;
    private Set<String> context = new HashSet<>();
    private Set<String> complement = new HashSet<>();
    private Map<String, Double> distances = new HashMap<>();
    
    public FreePathsWithDistance(Grid grid) {
        this.grid = grid;
    }
    
    /**
     * Calcola contesto e complemento per una cella origine O.
     */
    public void calculateContextAndComplement(int originRow, int originCol) {
        if (!grid.isTraversable(originRow, originCol)) {
            throw new IllegalArgumentException("La cella origine deve essere traversabile");
        }
        
        context.clear();
        complement.clear();
        distances.clear();
        
        // Aggiungi origine al contesto
        String origin = key(originRow, originCol);
        context.add(origin);
        distances.put(origin, 0.0);
        
        // Esplora tutte le celle della griglia
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                if (!grid.isTraversable(row, col) || (row == originRow && col == originCol)) {
                    continue;
                }
                
                analyzeFreePathsToCell(originRow, originCol, row, col);
            }
        }
    }
    
    /**
     * Analizza i cammini liberi verso una cella specifica e determina tipo e appartenenza.
     */
    private void analyzeFreePathsToCell(int oRow, int oCol, int dRow, int dCol) {
        int deltaX = Math.abs(dCol - oCol);
        int deltaY = Math.abs(dRow - oRow);
        
        String cellKey = key(dRow, dCol);
        double freeDistance = calculateFreeDistance(oRow, oCol, dRow, dCol);
        
        // Controlla se esistono cammini liberi validi
        boolean hasType1 = hasValidFreePath(oRow, oCol, dRow, dCol, true);
        boolean hasType2 = hasValidFreePath(oRow, oCol, dRow, dCol, false);
        
        if (hasType1) {
            context.add(cellKey);
            distances.put(cellKey, freeDistance);
        } else if (hasType2) {
            complement.add(cellKey);
            distances.put(cellKey, freeDistance);
        }
    }
    
    /**
     * Verifica se esiste un cammino libero valido di tipo specificato.
     */
    private boolean hasValidFreePath(int oRow, int oCol, int dRow, int dCol, boolean isType1) {
        int deltaX = Math.abs(dCol - oCol);
        int deltaY = Math.abs(dRow - oRow);
        
        // Determina direzioni
        int rowDir = Integer.compare(dRow, oRow);
        int colDir = Integer.compare(dCol, oCol);
        
        // Casi speciali: movimento solo su un asse
        if (deltaX == 0) { // Solo verticale
            return isPathClear(oRow, oCol, dRow, dCol, rowDir, 0);
        }
        if (deltaY == 0) { // Solo orizzontale
            return isPathClear(oRow, oCol, dRow, dCol, 0, colDir);
        }
        if (deltaX == deltaY) { // Solo diagonale
            return isPathClear(oRow, oCol, dRow, dCol, rowDir, colDir);
        }
        
        // Casi generali: movimento misto
        int minDelta = Math.min(deltaX, deltaY);
        
        if (isType1) {
            // Tipo 1: prima diagonale, poi ortogonale
            return checkType1Path(oRow, oCol, dRow, dCol, rowDir, colDir, minDelta);
        } else {
            // Tipo 2: prima ortogonale, poi diagonale
            return checkType2Path(oRow, oCol, dRow, dCol, rowDir, colDir, minDelta);
        }
    }
    
    /**
     * Verifica cammino tipo 1: diagonale → ortogonale.
     */
    private boolean checkType1Path(int oRow, int oCol, int dRow, int dCol, 
                                   int rowDir, int colDir, int minDelta) {
        // Fase diagonale
        int midRow = oRow + minDelta * rowDir;
        int midCol = oCol + minDelta * colDir;
        
        if (!isPathClear(oRow, oCol, midRow, midCol, rowDir, colDir)) {
            return false;
        }
        
        // Fase ortogonale
        if (Math.abs(dCol - oCol) > Math.abs(dRow - oRow)) {
            // Continua orizzontalmente
            return isPathClear(midRow, midCol, dRow, dCol, 0, colDir);
        } else {
            // Continua verticalmente
            return isPathClear(midRow, midCol, dRow, dCol, rowDir, 0);
        }
    }
    
    /**
     * Verifica cammino tipo 2: ortogonale → diagonale.
     */
    private boolean checkType2Path(int oRow, int oCol, int dRow, int dCol, 
                                   int rowDir, int colDir, int minDelta) {
        // Determina quale fase ortogonale fare prima
        int deltaX = Math.abs(dCol - oCol);
        int deltaY = Math.abs(dRow - oRow);
        
        if (deltaX > deltaY) {
            // Prima orizzontale, poi diagonale
            int midRow = oRow;
            int midCol = oCol + (deltaX - minDelta) * colDir;
            
            if (!isValid(midRow, midCol) || !isPathClear(oRow, oCol, midRow, midCol, 0, colDir)) {
                return false;
            }
            return isPathClear(midRow, midCol, dRow, dCol, rowDir, colDir);
        } else {
            // Prima verticale, poi diagonale
            int midRow = oRow + (deltaY - minDelta) * rowDir;
            int midCol = oCol;
            
            if (!isValid(midRow, midCol) || !isPathClear(oRow, oCol, midRow, midCol, rowDir, 0)) {
                return false;
            }
            return isPathClear(midRow, midCol, dRow, dCol, rowDir, colDir);
        }
    }
    
    /**
     * Verifica se il percorso tra due punti è libero.
     */
    private boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol, int rowDir, int colDir) {
        int row = fromRow;
        int col = fromCol;
        
        while (row != toRow || col != toCol) {
            row += rowDir;
            col += colDir;
            
            if (!isValid(row, col)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Calcola distanza libera tra due celle (formula corretta con √2).
     */
    public double calculateFreeDistance(int oRow, int oCol, int dRow, int dCol) {
        if (!grid.isTraversable(oRow, oCol) || !grid.isTraversable(dRow, dCol)) {
            return -1;
        }
        
        int x = Math.abs(dCol - oCol);
        int y = Math.abs(dRow - oRow);
        int min = Math.min(x, y);
        int max = Math.max(x, y);
        
        return Math.sqrt(2) * min + (max - min);
    }
    
    // Utility methods
    private String key(int row, int col) {
        return row + "," + col;
    }
    
    private boolean isValid(int row, int col) {
        return row >= 0 && row < grid.getRows() && 
               col >= 0 && col < grid.getCols() && 
               grid.isTraversable(row, col);
    }
    
    // Getters
    public boolean isInContext(int row, int col) {
        return context.contains(key(row, col));
    }
    
    public boolean isInComplement(int row, int col) {
        return complement.contains(key(row, col));
    }
    
    public double getDistance(int row, int col) {
        return distances.getOrDefault(key(row, col), -1.0);
    }
    
    public Set<String> getContext() {
        return new HashSet<>(context);
    }
    
    public Set<String> getComplement() {
        return new HashSet<>(complement);
    }
    
    // Metodi di stampa
    public void printContext(int originRow, int originCol) {
        System.out.println("Contesto (Tipo 1) della cella (" + originRow + "," + originCol + "):");
        printGrid(context, originRow, originCol);
    }
    
    public void printComplement(int originRow, int originCol) {
        System.out.println("Complemento (Tipo 2) della cella (" + originRow + "," + originCol + "):");
        printGrid(complement, originRow, originCol);
    }
    
    private void printGrid(Set<String> set, int originRow, int originCol) {
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++) {
                if (i == originRow && j == originCol) {
                    System.out.print("  O  ");
                } else if (!grid.isTraversable(i, j)) {
                    System.out.print("  #  ");
                } else if (set.contains(key(i, j))) {
                    System.out.printf("%5.1f", getDistance(i, j));
                } else {
                    System.out.print("  .  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}