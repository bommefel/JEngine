package main;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import linearalgebra.Vector;
import virtualreality.Camera;
import virtualreality.World;

public class JavaEngine extends JFrame{

	private static final long serialVersionUID = -5542814476711437429L;
	private final Renderer renderer;
	private final Controller controller;
	private final Stats stats;

	public JavaEngine(){
		super("JavaEngine");

		// init engine components
		Screen screen = new Screen(1600, 1200);
		World world = new World();
		Camera camera = new Camera();
		renderer = new Renderer(screen, world, camera, this);
		controller = new Controller(world, camera, 5, 2000);
		stats = new Stats();

		// continue swing init
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(screen, BorderLayout.NORTH);
		addKeyListener(controller);
		addMouseMotionListener(controller);
		addMouseListener(controller);
		setSize(1600, 1200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void startRendering(){
		Thread renderThread = new Thread(renderer);
		renderThread.setName("renderThread");
		renderThread.setPriority(Thread.NORM_PRIORITY);
		renderThread.start();

		Thread statsThread = new Thread(stats);
		statsThread.setName("statsThread");
		statsThread.setPriority(Thread.MAX_PRIORITY);
		statsThread.start();

		Thread controllerThread = new Thread(controller);
		controllerThread.setName("controllerThread");
		controllerThread.setPriority(Thread.MAX_PRIORITY);
		controllerThread.start();
	}
	
	public Controller getController(){
		return controller;
	}
	
	public void frameHasBeenCalculated(){
		stats.incFrames();
	}
	
	public static void main(String[] args) {
		try {
			// create a virtual 3d world, add objects at a certain resolution etc
			JavaEngine engine = new JavaEngine();

			Vector sphereStartingPosition = new Vector(2100, 1900, 5000, 1);
			engine.getController().addSphere(sphereStartingPosition, 2000, 50);

			Vector cubeStartingPosition = new Vector(-2000, 1900, 5000, 1);
			engine.getController().addCube(cubeStartingPosition, 1000);

			Vector cameraStartingPosition = new Vector(-3500, -3500, -10000, 1);
			engine.getController().getCamera().setPosition(cameraStartingPosition);

			engine.startRendering();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
