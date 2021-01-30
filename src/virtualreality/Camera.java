package virtualreality;

import linearalgebra.Matrix;
import linearalgebra.Vector;

public class Camera {
	
	private Vector cameraPosition;
	private Vector cameraRight;
	private Vector cameraUp;
	private Vector cameraFront;
	
	public Camera(){
		cameraPosition = new Vector(0, 0, 0, 1);
		cameraRight = new Vector(1, 0, 0, 1);
		cameraUp = new Vector(0, 1, 0, 1);
		cameraFront = new Vector(0, 0, 1, 1);
	}
	
	public Vector getPosition(){
		return cameraPosition;
	}
	
	public Vector getRight(){
		return cameraRight;
	}
	
	public Vector getUp(){
		return cameraUp;
	}
	
	public Vector getFront(){
		return cameraFront;
	}
	
	public void applyMatrix(Matrix matrix){
		cameraRight = matrix.multiply(cameraRight);
		cameraUp = matrix.multiply(cameraUp);
		cameraFront = matrix.multiply(cameraFront);
	}
	
	public void move(Vector vector){
		cameraPosition = cameraPosition.add(vector);
	}
	
	public void setPosition(Vector vector){
		cameraPosition = vector;
	}
}
