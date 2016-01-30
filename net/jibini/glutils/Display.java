package net.jibini.glutils;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import static org.lwjgl.glfw.GLFW.*;

public class Display
{
	private String title = "";
	private int width = 0, height = 0;
	private int lastWidth, lastHeight;
	private boolean vSync = false;
	private boolean isCreated = false;
	private long window;

	public void setTitle(String title)
	{
		this.title = title;
		if (isCreated)
			glfwSetWindowTitle(window, title);
	}

	public void setDimensions(int width, int height)
	{
		this.width = width;
		this.height = height;
		if (isCreated)
			glfwSetWindowSize(window, width, height);
	}

	public void setVSync(boolean vSync)
	{
		this.vSync = vSync;
		if (isCreated)
			glfwSwapInterval(vSync ? 1 : 0);
	}

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

	public void requestClose()
	{
		glfwSetWindowShouldClose(window, GLFW_TRUE);
	}

	public void destroy()
	{
		glfwDestroyWindow(window);
		glfwTerminate();
		GL.destroy();
	}

	public boolean wasResized()
	{
		boolean result = (lastWidth != width) | (lastHeight != height);
		return result;
	}

	public boolean isCloseRequested()
	{
		return glfwWindowShouldClose(window) == GLFW_TRUE;
	}

	public boolean isVSync()
	{
		return vSync;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public long getWindow()
	{
		return window;
	}
}
