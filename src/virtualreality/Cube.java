package virtualreality;

import java.awt.Color;

import linearalgebra.Vector;
import linearalgebra.VectorPair;

public class Cube extends Object3D {

    private static final long serialVersionUID = -7509754370676768110L;

    public Cube(Vector startPos, double sideLength) {
        // helper vectors (basis vectors)
        Vector e1 = new Vector(sideLength, 0, 0, 1);
        Vector e2 = new Vector(0, sideLength, 0, 1);
        Vector e3 = new Vector(0, 0, sideLength, 1);

        objectVectors.add(0, startPos);
        objectVectors.add(1, startPos.add(e1));
        objectVectors.add(2, startPos.add(e1).add(e3));
        objectVectors.add(3, startPos.add(e3));

        objectVectors.add(4, startPos.add(e2));
        objectVectors.add(5, startPos.add(e2).add(e1));
        objectVectors.add(6, startPos.add(e2).add(e1).add(e3));
        objectVectors.add(7, startPos.add(e2).add(e3));

        initLinesArray(12);

        // calculate center
        center = startPos.add(e1.multiply(0.5)).add(e2.multiply(0.5)).add(e3.multiply(0.5));

        System.out.println("cube initialized: 12 lines present");
    }

    @Override
    public void updateLinesFromVectorPoints() {
        // todo via loop
        linesToDraw.set(0, new VectorPair(objectVectors.get(0), objectVectors.get(1), Color.GRAY));
        linesToDraw.set(1, new VectorPair(objectVectors.get(1), objectVectors.get(2), Color.RED));
        linesToDraw.set(2, new VectorPair(objectVectors.get(2), objectVectors.get(3), Color.BLUE));
        linesToDraw.set(3, new VectorPair(objectVectors.get(3), objectVectors.get(0), Color.RED));
        linesToDraw.set(4, new VectorPair(objectVectors.get(4), objectVectors.get(5), Color.GRAY));
        linesToDraw.set(5, new VectorPair(objectVectors.get(5), objectVectors.get(6), Color.RED));
        linesToDraw.set(6, new VectorPair(objectVectors.get(6), objectVectors.get(7), Color.BLUE));
        linesToDraw.set(7, new VectorPair(objectVectors.get(7), objectVectors.get(4), Color.RED));
        linesToDraw.set(8, new VectorPair(objectVectors.get(0), objectVectors.get(4), Color.GRAY));
        linesToDraw.set(9, new VectorPair(objectVectors.get(1), objectVectors.get(5), Color.GRAY));
        linesToDraw.set(10, new VectorPair(objectVectors.get(2), objectVectors.get(6), Color.BLUE));
        linesToDraw.set(11, new VectorPair(objectVectors.get(3), objectVectors.get(7), Color.BLUE));
    }

}
