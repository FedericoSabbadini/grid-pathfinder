package it.unibs.algoritmiPRJ.compito3;
import java.util.List;

/**
 * Rappresenta il risultato del calcolo del cammino minimo tra due landmark.
 * Contiene la lunghezza del cammino e la sequenza di landmark attraversati.
 */
public class PathResult {
	
	//========================Attributi========================    
	private final double length;
	private final List<Landmark> landmarkSequence;
    
    //========================Costruttore========================
    /**
	 * Crea un nuovo risultato del cammino minimo.
	 * 
	 * @param length La lunghezza del cammino minimo.
	 * @param landmarkSequence La sequenza di landmark attraversati nel cammino.
	 */
    public PathResult(double length, List<Landmark> landmarkSequence) {
        this.length = length;
        this.landmarkSequence = landmarkSequence;
    }
    
    
    //======================Getters==&==Setters======================
    public double getLength() {return length;}
	public List<Landmark> getLandmarkSequence() {return landmarkSequence;}
}