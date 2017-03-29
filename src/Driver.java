import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Driver extends Canvas implements Runnable
{
    private boolean running = false;
    private Integer frames;
    private Thread thread;
    public JFrame frame;

    private Window window;
    private BufferedImage img;


    public Driver()
    {
        window = new Window("Dominion", this);

        Square[] test = new Square[1000];
        AnimManager manager = AnimManager.get();

        for(int i=0; i<1000; i++) {
            test[i] = new Square(0,0);
            int[] vals = {rand(), rand(), rand()/10, rand()/10};
            int[] vals2 = {rand(), rand(), rand()/10, rand()/10};
            String begin = manager.pack('(', vals)+"={5}="+manager.pack('(', vals2);
            AnimManager.get().animate(test[i], 4, begin+"={3}=[100,100,10,10]={3}");
        }
    }

    public int rand() {
        return (int)Math.floor(Math.random()*600);
    }

    public void tick()
    {
        AnimManager.get().tick();
    }

    public void render()
    {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null)
        {
            this.createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        //Start Graphics

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, window.getWidth(), window.getHeight());

        g.setColor(Color.WHITE);

        AnimManager.get().render(g);

        //End Graphics
        g.dispose();
        bs.show();
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running)
                render();

            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }
    public static void main(String[] args)
    {
        new Driver();
    }
}
