package virtualreality;

import java.io.Serializable;
import java.util.ArrayList;
import linearalgebra.Matrix;
import linearalgebra.Vector;
import linearalgebra.VectorPair;

public abstract class Object3D implements Serializable {
	
	private static final long serialVersionUID = -1297248152053718181L;
	protected ArrayList<Vector> objectVectors;
	protected ArrayList<VectorPair> linesToDraw;
	protected Vector center = null; // todo no direct access for children
	
	public Object3D(){
		objectVectors = new ArrayList<>();
		linesToDraw = new ArrayList<>();
	}
	
	public ArrayList<VectorPair> getVectorPairs() {
		return linesToDraw;
	}
	
	public void applyMatrix(Matrix matrix) {
		for (int i = 0; i< objectVectors.size(); i++){
			objectVectors.set(i, matrix.multiply(objectVectors.get(i)));
		}
	}
	
	public void applyProjectionMatrix(Matrix matrix) {
		for (int i = 0; i< objectVectors.size(); i++){
			if (objectVectors.get(i).getZ() > 0.1){
				objectVectors.set(i, matrix.multiply(objectVectors.get(i)));
			} else {
				objectVectors.get(i).calculationException = true; // low z-values can lead to too large values in x and y
			}
		}
	}
	
	public void initLinesArray(int size){
		for (int i=0; i<size; i++){
			linesToDraw.add(null);
		}
	}
	
	public Vector getCenter(){
		return center;
	}
	
	public abstract void updateLinesFromVectorPoints();
}
