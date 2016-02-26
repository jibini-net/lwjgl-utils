package net.jibini.glutils;

import static org.lwjgl.glfw.GLFW.*;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

/**
 * Utility class for handling GLFW displays.
 */
public class Display
{
	/**
	 * Stores current title for display.
	 */
	private String title = "";
	
	/**
	 * Stores current width for display.
	 */
	private int width = 0;
	
	/**
	 * Stores current height for display.
	 */
	private int height = 0;
	
	/**
	 * Stores width of display from last frame.
	 */
	private int lastWidth;
	
	/**
	 * Stores height of display from last frame.
	 */
	private int lastHeight;
	
	/**
	 * Stores whether VSync is enabled.
	 */
	private boolean vSync = false;
	
	/**
	 * Stores whether display is created.
	 */
	private boolean isCreated = false;
	
	/**
	 * Stores GLFW window object handle.
	 */
	private long window;

	/**
	 * Changes the title of the display.
	 * 
	 * @param title New title for the display.
	 */
	public void setTitle(String title)
	{
		this.title = title;
		if (isCreated)
			glfwSetWindowTitle(window, title);
	}

	/**
	 * Changes the size of the display.
	 * 
	 * @param width New width for the display.
	 * @param height New height for the display.
	 */
	public void setDimensions(int width, int height)
	{
		this.width = width;
		this.height = height;
		if (isCreated)
			glfwSetWindowSize(window, width, height);
	}


	/**
	 * Changes whether VSync is enabled.
	 * 
	 * @param vSync Whether VSync should be enabled.
	 */
	public void setVSync(boolean vSync)
	{
		this.vSync = vSync;
		if (isCreated)
			glfwSwapInterval(vSync ? 1 : 0);
	}


	/**
	 * Initializes the GLFW display and GL capabilities.
	 */
	public void create()
	{
		if (glfwInit() != GLFW_TRUE)
			throw new RuntimeException("Could not init GLFW.");
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		window = glfwCreateWindow(width, height, title, 0, 0);

		if (window == 0)
		{
			glfwTerminate();
			throw new RuntimeException("Window pointer is NULL.");
		}

		glfwMakeContextCurrent(window);
		glfwSwapInterval(vSync ? 1 : 0);
		GL.createCapabilities(true);
		glfwSetInputMode(window, GLFW_STICKY_KEYS, GLFW_TRUE);
		isCreated = true;
	}


	/**
	 * Updates variables from frame to frame and completes
	 * per-frame operations.
	 */
	public void update()
	{
		lastWidth = width;
		lastHeight = height;
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(window, w, h);
		width = w.get(0);
		height = h.get(0);
		glfwSwapBuffers(window);
		glfwPollEvents();
	}


	/**
	 * Requests that the display should close.
	 */
	public void requestClose()
	{
		glfwSetWindowShouldClose(window, GLFW_TRUE);
	}


	/**
	 * Destroys the display and GL capabilities.
	 */
	public void destroy()
	{
		glfwDestroyWindow(window);
		glfwTerminate();
		GL.destroy();
	}


	/**
	 * Decides whether the display has been resized.
	 * 
	 * @return Whether the display has been resized.
	 */
	public boolean wasResized()
	{
		boolean result = (lastWidth != width) | (lastHeight != height);
		return result;
	}


	/**
	 * Decides whether the display should be closed.
	 * 
	 * @return Whether the display should be closed.
	 */
	public boolean isCloseRequested()
	{
		return glfwWindowShouldClose(window) == GLFW_TRUE;
	}


	/**
	 * Decides whether VSync is enabled.
	 * 
	 * @return Whether VSync is enabled.
	 */
	public boolean isVSync()
	{
		return vSync;
	}


	/**
	 * Decides the current width of the display.
	 * 
	 * @return Current width of the display.
	 */
	public int getWidth()
	{
		return width;
	}


	/**
	 * Decides the current height of the display.
	 * 
	 * @return Current height of the display.
	 */
	public int getHeight()
	{
		return height;
	}


	/**
	 * Gives access to the GLFW window object.
	 * 
	 * @return GLFW window object handle.
	 */
	public long getWindow()
	{
		return window;
	}
}
