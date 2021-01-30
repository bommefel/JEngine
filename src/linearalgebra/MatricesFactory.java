package linearalgebra;

import virtualreality.Camera;

public class MatricesFactory {
	
	public static Matrix getProjectionMatrix(double distanceToPlane){
		Matrix returnMatrix = new Matrix(
				distanceToPlane, 0, 0, 0,
				0, distanceToPlane, 0, 0,
				0, 0, distanceToPlane, 0,
				0, 0, 1, 0
		);

		returnMatrix.isProjectionMatrix = true;
		returnMatrix.distanceToPlane = distanceToPlane;

		return returnMatrix;
	}
	
	public static Matrix getCameraRotationMatrix(Camera camera){
		return new Matrix(
				camera.getRight().getX(), camera.getRight().getY(), camera.getRight().getZ(), 0,
				camera.getUp().getX(), camera.getUp().getY(), camera.getUp().getZ(), 0,
				camera.getFront().getX(), camera.getFront().getY(), camera.getFront().getZ(), 0,
				0, 0, 0, 1
		);
	}

	// todo currently not in use as its solved via saveState
	public static Matrix getCameraRotationMatrixInv(Camera camera){
		return new Matrix(
				camera.getRight().getX(), camera.getUp().getX(), camera.getFront().getX(), 0,
				camera.getRight().getY(), camera.getUp().getY(), camera.getFront().getY(), 0,
				camera.getRight().getZ(), camera.getUp().getZ(), camera.getFront().getZ(), 0,
				0, 0, 0, 1
		);
	}
	
	public static Matrix getTranslationMatrix(Vector vector){
		return new Matrix(
				1, 0, 0, vector.getX(),
				0, 1, 0, vector.getY(),
				0, 0, 1, vector.getZ(),
				0, 0, 0, 1
		);
	}
	
	public static Matrix getRotationMatrixXAxis(double angle){
		return new Matrix(
				1, 0, 0, 0,
				0, Math.cos(angle), -Math.sin(angle), 0,
				0, Math.sin(angle), Math.cos(angle), 0,
				0, 0, 0, 1
		);
	}
	
	public static Matrix getRotationMatrixYAxis(double angle){
		return new Matrix(
				Math.cos(angle), 0, Math.sin(angle), 0,
				0, 1, 0, 0,
				-Math.sin(angle), 0, Math.cos(angle), 0,
				0, 0, 0, 1
		);
	}
	
	public static Matrix getRotationMatrixZAxis(double angle){
		return new Matrix(
				Math.cos(angle), -Math.sin(angle), 0, 0,
				Math.sin(angle), Math.cos(angle), 0, 0,
				0, 0, 1, 0,
				0, 0, 0, 1
		);
	}
	
	public static Matrix getRotationMatrixAroundVector(Vector vector, double angle){
		Vector n = vector.normalize();
		return new Matrix(
				n.getX()*n.getX()*(1-Math.cos(angle))+Math.cos(angle), n.getX()*n.getY()*(1-Math.cos(angle))-n.getZ()*Math.sin(angle), n.getX()*n.getZ()*(1-Math.cos(angle))+n.getY()*Math.sin(angle), 0,
				n.getY()*n.getX()*(1-Math.cos(angle))+n.getZ()*Math.sin(angle), n.getY()*n.getY()*(1-Math.cos(angle))+Math.cos(angle), n.getY()*n.getZ()*(1-Math.cos(angle))-n.getX()*Math.sin(angle), 0,
				n.getZ()*n.getX()*(1-Math.cos(angle))-n.getY()*Math.sin(angle), n.getZ()*n.getY()*(1-Math.cos(angle))+n.getX()*Math.sin(angle), n.getZ()*n.getZ()*(1-Math.cos(angle))+Math.cos(angle), 0,
				0, 0, 0, 1
		);
	}
}
