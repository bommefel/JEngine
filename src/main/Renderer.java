package main;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import linearalgebra.MatricesFactory;
import linearalgebra.VectorPair;
import tools.Line;
import virtualreality.Camera;
import virtualreality.World;

public class Renderer implements Runnable{
	
	private final Screen screenToDrawOn;
	private final World virtualWorld;
	private final Camera virtualCamera;
	private final JavaEngine engine;
	
	public Renderer(Screen screen, World world, Camera camera, JavaEngine engine){
		screenToDrawOn = screen;
		virtualWorld = world;
		virtualCamera = camera;
		this.engine = engine;
	}

	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()){
			// lock world object and calculate next frame
			synchronized(virtualWorld){
				try {
					virtualWorld.saveObjectState();
					virtualWorld.applyMatrix(MatricesFactory.getTranslationMatrix(virtualCamera.getPosition().invert()));
					virtualWorld.applyMatrix(MatricesFactory.getCameraRotationMatrix(virtualCamera));
					virtualWorld.applyMatrix(MatricesFactory.getProjectionMatrix(2000));
					virtualWorld.updateLines();

					// update screen
					screenToDrawOn.setLinesToDraw(getLinesFromVectorPairList(virtualWorld.getAllVectorPairs()));

					// cleanup
					virtualWorld.restoreObjectState();
					engine.frameHasBeenCalculated();
				} catch (Exception ex){
					ex.printStackTrace();
				}
			}

			synchronized(screenToDrawOn){
				// wait for redraw
				screenToDrawOn.repaint();
				try {
					screenToDrawOn.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// todo method length
	private ArrayList<Line> getLinesFromVectorPairList(List<VectorPair> list){
		ArrayList<Line> returnList = new ArrayList<>();
		for (VectorPair temp : list) {
			// only use valid vectors
			if ((temp.vector1.calculationException) || (temp.vector2.calculationException)) {
				continue;
			}

			Line newElement = new Line();
			newElement.data = new Line2D.Double(
					temp.vector1.getX(),
					temp.vector1.getY(),
					temp.vector2.getX(),
					temp.vector2.getY()
			);

			if (temp.color != null) {
				newElement.color = temp.color;
			} else {
				newElement.color = Color.BLACK;
			}

			returnList.add(newElement);
		}
		return returnList;
	}
}
