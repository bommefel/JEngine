package linearalgebra;

public class Matrix {
	
	public double a11;
	public double a12;
	public double a13;
	public double a14;
	
	public double a21;
	public double a22;
	public double a23;
	public double a24;
	
	public double a31;
	public double a32;
	public double a33;
	public double a34;
	
	public double a41;
	public double a42;
	public double a43;
	public double a44;
	
	public boolean isProjectionMatrix = false;
	public double distanceToPlane = 0;

	public Matrix(
			double a11,
			double a12,
			double a13,
			double a14,
			double a21,
			double a22,
			double a23,
			double a24,
			double a31,
			double a32,
			double a33,
			double a34,
			double a41,
			double a42,
			double a43,
			double a44)
	{
		this.a11 = a11;
		this.a12 = a12;
		this.a13 = a13;
		this.a14 = a14;
		
		this.a21 = a21;
		this.a22 = a22;
		this.a23 = a23;
		this.a24 = a24;
		
		this.a31 = a31;
		this.a32 = a32;
		this.a33 = a33;
		this.a34 = a34;
		
		this.a41 = a41;
		this.a42 = a42;
		this.a43 = a43;
		this.a44 = a44;
	}
	
	public Vector multiply(Vector vector){
		double xResult = a11 * vector.getX() + a12 * vector.getY() + a13 * vector.getZ() + a14 * 1;
		double yResult = a21 * vector.getX() + a22 * vector.getY() + a23 * vector.getZ() + a24 * 1;
		double zResult = a31 * vector.getX() + a32 * vector.getY() + a33 * vector.getZ() + a34 * 1;
		double wResult = a41 * vector.getX() + a42 * vector.getY() + a43 * vector.getZ() + a44 * 1;
		return new Vector(xResult, yResult, zResult, wResult);
	}
}
