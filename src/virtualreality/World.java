package virtualreality;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import linearalgebra.Matrix;
import linearalgebra.VectorPair;

public class World {
	
	private ArrayList<Object3D> objectsInWorld;
	private byte[] objectBackup = null;
	
	public World(){
		objectsInWorld = new ArrayList<>();
	}
	
	public void add(Object3D newObject3D){
		objectsInWorld.add(newObject3D);
	}
	
	public boolean delete(Object3D object3D){
		return objectsInWorld.remove(object3D);
	}
	
	public List<Object3D> getCurrentObjects3D(){
		List<Object3D> returnList = new ArrayList<>(objectsInWorld);
		returnList = Collections.unmodifiableList(returnList);

		return returnList;
	}
	
	public void applyMatrix(Matrix matrix){
		if (matrix.isProjectionMatrix){
			for (Object3D object3D : objectsInWorld){
				object3D.applyProjectionMatrix(matrix);
			}
		} else {
			for (Object3D object3D : objectsInWorld){
				object3D.applyMatrix(matrix);
			}
		}
	}
	
	public void updateLines(){
		for (Object3D object3D : objectsInWorld){
			object3D.updateLinesFromVectorPoints();
		}
	}
	
	public List<VectorPair> getAllVectorPairs(){
		List<VectorPair> returnList = new ArrayList<>();
		for (Object3D object3D : objectsInWorld){
			returnList.addAll(object3D.getVectorPairs());
		}
		return returnList;
	}
	
	public void saveObjectState() throws Exception{
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
		objectStream.writeObject(objectsInWorld);
		objectBackup = byteStream.toByteArray();
	}
	
	@SuppressWarnings("unchecked")
	public void restoreObjectState() throws Exception{
		ByteArrayInputStream byteStream = new ByteArrayInputStream(objectBackup);
		ObjectInputStream objectStream = new ObjectInputStream(byteStream);
		objectsInWorld = (ArrayList<Object3D>) objectStream.readObject();
	}
}
