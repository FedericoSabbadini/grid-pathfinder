package it.unibs.algoritmiPRJ.compito3;
import java.util.List;

/**
 * Rappresenta il risultato del calcolo del cammino minimo tra due landmark.
 * Contiene la lunghezza del cammino e la sequenza di landmark attraversati.
 */
public class MinimumPathResult {
	
	//========================Attributi========================    
	private final double length;
	private final List<Landmark> landmarkSequence;
    private int recursiveCalls;
    
    
    //========================Costruttore========================
    /**
	 * Crea un nuovo risultato del cammino minimo.
	 * 
	 * @param length La lunghezza del cammino minimo.
	 * @param landmarkSequence La sequenza di landmark attraversati nel cammino.
	 */
    public MinimumPathResult(double length, List<Landmark> landmarkSequence) {
        this.length = length;
        this.landmarkSequence = landmarkSequence;
		this.recursiveCalls = 0;
    }
    
    
    //======================Getters==&==Setters======================
    public double getLength() {return length;}
	public List<Landmark> getLandmarkSequence() {return landmarkSequence;}
    public int getRecursiveCalls() { return recursiveCalls; }
    public void setRecursiveCalls(int recursiveCalls) {this.recursiveCalls = recursiveCalls;}
}