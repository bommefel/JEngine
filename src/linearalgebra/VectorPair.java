package linearalgebra;

import java.awt.Color;
import java.io.Serializable;

public class VectorPair implements Serializable {
	
	private static final long serialVersionUID = 5802347391428561803L;
	public Vector vector1;
	public Vector vector2;
	public Color color = null;
	
	public VectorPair(Vector v1, Vector v2){
		vector1 = v1;
		vector2 = v2;
	}
	
	public VectorPair(Vector v1, Vector v2, Color c){
		vector1 = v1;
		vector2 = v2;
		color = c;
	}
}
