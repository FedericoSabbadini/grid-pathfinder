package it.unibs.algoritmiPRJ.compito3;
import java.util.List;

/**
 * Rappresenta il risultato di un calcolo del cammino minimo.
 * Contiene informazioni sul tempo impiegato, se il cammino è connesso,
 * il numero di chiamate ricorsive, il numero di celle frontiera e
 * il numero di iterazioni false.
 */
public class MinimumPathResult extends PathResult {
		
	//========================Attributi========================    
    final double timeMs;
    final boolean isConnected;
    private int recursiveCalls;
    private int iterationsFalse;
    private int numFrontierCells;
    public boolean isCorrect = false;
    
    //========================Costruttore========================
	/**
	 * 
	 * @return Un nuovo risultato del cammino minimo.
	 */
    public MinimumPathResult(double length, List<Landmark> landmarkSequence, double timeMs, boolean isConnected, int recursiveCalls, int numFrontierCells, int iterationsFalse) {
		super(length, landmarkSequence);
		this.timeMs = timeMs;
		this.isConnected = isConnected;
		this.recursiveCalls = recursiveCalls;
		this.numFrontierCells = numFrontierCells;
		this.iterationsFalse = iterationsFalse;
	}
    
    
    //======================Getters==&==Setters======================
	public double getTimeMs() { return timeMs; }
	public boolean isConnected() { return isConnected; }
    public int getRecursiveCalls() { return recursiveCalls; }
    public void setRecursiveCalls(int recursiveCalls) {this.recursiveCalls = recursiveCalls;}
    public int getIterationsFalse() { return iterationsFalse; }
    public void setIterationsFalse(int iterationsFalse) { this.iterationsFalse = iterationsFalse; }
    public int getFrontierSize() { return numFrontierCells; }
    public void setFrontierSize(int numFrontierCells) { this.numFrontierCells = numFrontierCells; }
    public boolean isCorrect() { return isCorrect; }
    public void setCorrect(boolean isCorrect) { this.isCorrect = isCorrect; }
}