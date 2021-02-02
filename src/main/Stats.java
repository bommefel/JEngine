package main;

public class Stats implements Runnable {

    private int calculatedFrames = 0;

    public synchronized void incFrames() {
        calculatedFrames++;
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (this) {
                System.out.println("fps: " + calculatedFrames);
                calculatedFrames = 0;
            }
        } while (!Thread.currentThread().isInterrupted());
    }
}
