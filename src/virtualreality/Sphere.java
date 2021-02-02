package virtualreality;

import java.awt.Color;

import linearalgebra.MatricesFactory;
import linearalgebra.Vector;
import linearalgebra.VectorPair;

public class Sphere extends Object3D {

    private static final long serialVersionUID = 6140151262718204646L;
    private final int resolution;

    public Sphere(Vector startPos, int radius, int resolution) {
        this.resolution = resolution;
        Vector helper = new Vector(0, -radius, 0, 1);

        // todo nesting too high -> extract
        for (int i = 1; i < resolution; i++) {
            // rotate around x-axis
            double factorUp = (double) i / (double) resolution;
            Vector helperUp = (MatricesFactory.getRotationMatrixXAxis(factorUp * Math.PI)).multiply(helper);
            for (int j = 1; j <= resolution; j++) {
                // rotate around y-axis
                double factorRotate = (double) j / (double) resolution;
                Vector newPointSphere = (MatricesFactory.getRotationMatrixYAxis(factorRotate * 2 * Math.PI)).multiply(helperUp);
                objectVectors.add(startPos.add(newPointSphere));
            }
        }

        int linesCount = resolution * (resolution - 1); // slices
        linesCount = linesCount + (resolution * (resolution - 2)); // vertical connections
        initLinesArray(linesCount);

        // calculate center
        center = startPos;
        System.out.println("sphere initialized: " + linesCount + " lines present");
    }

    @Override
    public void updateLinesFromVectorPoints() {
        int lineCount = 0;

        // todo nesting too high -> extract
        // slices
        for (int i = 1; i < resolution; i++) {
            for (int j = 1; j <= resolution; j++) {
                int augmentation = (i - 1) * resolution;
                int firstPoint = augmentation + j - 1;
                int secondPoint = augmentation + (j % resolution);
                linesToDraw.set(lineCount, new VectorPair(objectVectors.get(firstPoint), objectVectors.get(secondPoint), Color.BLUE));
                lineCount++;
            }
        }

        // todo nesting too high -> extract
        // vertical connections
        for (int i = 1; i < resolution - 1; i++) {
            for (int j = 1; j <= resolution; j++) {
                int augmentation = (i - 1) * resolution;
                int firstPoint = augmentation + j - 1;
                int secondPoint = augmentation + j - 1 + resolution;
                linesToDraw.set(lineCount, new VectorPair(objectVectors.get(firstPoint), objectVectors.get(secondPoint), Color.GRAY));
                lineCount++;
            }
        }
    }
}
