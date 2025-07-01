package it.unibs.algoritmiPRJ.compito3;
import it.unibs.algoritmiPRJ.compito1.Cell;

/**
 * Rappresenta un landmark in un percorso minimo.
 * Un landmark è definito da una cella e un tipo.
 */
public class Landmark {
	
	//========================Attributi========================    
    public final Cell cell;
    public final int type;

    
    //========================Costruttori========================
    /**
	 * Crea un landmark associato a una cella e a un tipo.
	 * 
	 * @param cell La cella associata al landmark.
	 * @param type Il tipo del landmark (0 per origine, 1 per tipo-1, 2 per tipo-2).
	 */
    public Landmark(Cell cell, int type) {
        this.cell = cell;
        this.type = type;
    }
    
    
    //========================Getters========================
    public int getRow() {return cell.getRow();}
    public int getCol() {return cell.getCol();}
    public Cell getCell() {return cell;}
    public int getType() {return type;}

    
    //========================Metodi========================
    /**
	 * Restituisce una rappresentazione testuale del landmark.
	 * @return Una stringa che rappresenta il landmark.
	 */
    @Override
    public String toString() {
        return "(" + cell.toString() + ": " + type + ")";
    }
    
    /**
     * Confronta due landmark per verificare se sono uguali.
     * @param obj L'oggetto da confrontare con questo landmark.
     */
    @Override
    public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Landmark)) return false;
		Landmark other = (Landmark) obj;
		return cell.equals(other.cell) && type == other.type;
	}
    
    /**
	 * Calcola l'hash code del landmark.
	 * @return L'hash code del landmark.
	 */
    @Override
	public int hashCode() {
		return 31 * cell.hashCode() + type;
	}
}