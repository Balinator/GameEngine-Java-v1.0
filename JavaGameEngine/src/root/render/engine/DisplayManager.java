package root.render.engine;

import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import root.settings.Settings;
import root.settings.SettingsManager;

public class DisplayManager {
	private static final int FPS_CAP = 120;
	
	private static long window;
	
	private static int sWheight;
	private static int sHeight;
	
	private static long lastFrameTime;
	private static float delta;
	private static long variableYieldTime;
	private static long lastTime;
	
	public static void createDisplay(){
		glfwInit();
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		sWheight = gd.getDisplayMode().getWidth();
		sHeight = gd.getDisplayMode().getHeight();
		
		Settings settings = SettingsManager.getCopyOfSettings();
		
		//TODO: set up display as the settings says
		
		window = glfwCreateWindow(sWheight,sHeight,"Game",MemoryUtil.NULL,MemoryUtil.NULL);
		
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
		
		GL.createCapabilities();
		
		lastFrameTime = getCurrentTime();
	}
	
	public static void updateDisplay(){
		sync(FPS_CAP);
		glfwSwapBuffers(window);
		glfwPollEvents();
		long  currentFarmeTime = getCurrentTime();
		delta = (currentFarmeTime - lastFrameTime) / 1000.0f;
		lastFrameTime = currentFarmeTime;
	}
	
	public static float getFrameTimeSeconds(){
		return delta;
	}
	
	public static void closeDisplay(){
		
		glfwDestroyWindow(window);
		glfwTerminate();
		
	}
	
	private static long getCurrentTime(){
		return System.currentTimeMillis();
	}
	
	public static boolean isCloseRequested(){
		
		return glfwWindowShouldClose(window);
		
	}
	
	public static long getWindow(){
		return window;
	}
    
    /**
     * An accurate sync method that adapts automatically
     * to the system it runs on to provide reliable results.
     * 
     * @param fps The desired frame rate, in frames per second
     * @author kappa (On the LWJGL Forums)
     */
    private static void sync(int fps) {
        if (fps <= 0) return;
          
        long sleepTime = 1000000000 / fps; // nanoseconds to sleep this frame
        // yieldTime + remainder micro & nano seconds if smaller than sleepTime
        long yieldTime = Math.min(sleepTime, variableYieldTime + sleepTime % (1000000));
        long overSleep = 0; // time the sync goes over by
          
        try {
            while (true) {
                long t = System.nanoTime() - lastTime;
                  
                if (t < sleepTime - yieldTime) {
                    Thread.sleep(1);
                }else if (t < sleepTime) {
                    // burn the last few CPU cycles to ensure accuracy
                    Thread.yield();
                }else {
                    overSleep = t - sleepTime;
                    break; // exit while loop
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            lastTime = System.nanoTime() - Math.min(overSleep, sleepTime);
             
            // auto tune the time sync should yield
            if (overSleep > variableYieldTime) {
                // increase by 200 microseconds (1/5 a ms)
                variableYieldTime = Math.min(variableYieldTime + 200000, sleepTime);
            }
            else if (overSleep < variableYieldTime - 200*1000) {
                // decrease by 2 microseconds
                variableYieldTime = Math.max(variableYieldTime - 2000, 0);
            }
        }
    }
}
