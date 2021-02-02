package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import virtualreality.Camera;
import virtualreality.Cube;
import virtualreality.Object3D;
import virtualreality.Sphere;
import virtualreality.World;
import linearalgebra.MatricesFactory;
import linearalgebra.Matrix;
import linearalgebra.Vector;

public class Controller implements KeyListener, MouseMotionListener, MouseListener, Runnable {

    private final World attachedWorld;
    private final Camera attachedCamera;
    private final int resolution;
    private final double deltaPerSecond;
    private boolean wKey = false;
    private boolean sKey = false;
    private boolean aKey = false;
    private boolean dKey = false;
    private int xMouseValue;
    private int yMouseValue;

    public Controller(World world, Camera camera, int resolution, double deltaPerSecond) {
        attachedWorld = world;
        attachedCamera = camera;
        this.resolution = resolution;
        this.deltaPerSecond = deltaPerSecond;
    }

    // todo have a common add method and have a builder for 3d objects
    public void addCube(Vector startPos, double sideLength) {
        attachedWorld.add(new Cube(startPos, sideLength));
    }

    public void addSphere(Vector startPos, int radius, int resolution) {
        attachedWorld.add(new Sphere(startPos, radius, resolution));
    }

    public void addObject3D(Object3D object3D) {
        attachedWorld.add(object3D);
    }

    public Camera getCamera() {
        return attachedCamera;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        // todo solve via array
        synchronized (this) {
            if (event.getKeyChar() == 'w') {
                wKey = true;
            } else if (event.getKeyChar() == 's') {
                sKey = true;
            } else if (event.getKeyChar() == 'a') {
                aKey = true;
            } else if (event.getKeyChar() == 'd') {
                dKey = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        // todo solve via array
        synchronized (this) {
            if (event.getKeyChar() == 'w') {
                wKey = false;
            } else if (event.getKeyChar() == 's') {
                sKey = false;
            } else if (event.getKeyChar() == 'a') {
                aKey = false;
            } else if (event.getKeyChar() == 'd') {
                dKey = false;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (this) {
                synchronized (attachedWorld) {
                    // todo separate into functions

                    double cameraTranslationDelta = deltaPerSecond * ((double) resolution / 1000);

                    // todo extract the following logic into more appropriate places
                    // camera movement
                    if (wKey) {
                        attachedCamera.move(attachedCamera.getFront().multiply(cameraTranslationDelta));
                    }
                    if (sKey) {
                        attachedCamera.move(attachedCamera.getFront().multiply(-cameraTranslationDelta));
                    }
                    if (aKey) {
                        attachedCamera.move(attachedCamera.getRight().multiply(-cameraTranslationDelta));
                    }
                    if (dKey) {
                        attachedCamera.move(attachedCamera.getRight().multiply(cameraTranslationDelta));
                    }

                    double objectRotationDelta = deltaPerSecond / 1200 * ((double) resolution / 2000);

                    // move objects in world
                    // todo have it dynamic
                    for (Object3D object3D : attachedWorld.getCurrentObjects3D()) {
                        object3D.applyMatrix(MatricesFactory.getTranslationMatrix(object3D.getCenter().invert()));

                        object3D.applyMatrix(MatricesFactory.getRotationMatrixYAxis(objectRotationDelta));
                        object3D.applyMatrix(MatricesFactory.getRotationMatrixXAxis(objectRotationDelta));

                        object3D.applyMatrix(MatricesFactory.getTranslationMatrix(object3D.getCenter()));
                    }
                }
            }

            try {
                Thread.sleep(resolution);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        double deltaX = event.getX() - xMouseValue;
        double deltaY = event.getY() - yMouseValue;

        Matrix rotationY = MatricesFactory.getRotationMatrixAroundVector(attachedCamera.getUp(), -deltaX / 2000);
        Matrix rotationX = MatricesFactory.getRotationMatrixAroundVector(attachedCamera.getRight(), -deltaY / 2000);

        synchronized (attachedWorld) {
            attachedCamera.applyMatrix(rotationY);
            attachedCamera.applyMatrix(rotationX);
        }
        xMouseValue = event.getX();
        yMouseValue = event.getY();
    }

    @Override
    public void mouseMoved(MouseEvent event) {
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
        xMouseValue = event.getX();
        yMouseValue = event.getY();
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }
}
