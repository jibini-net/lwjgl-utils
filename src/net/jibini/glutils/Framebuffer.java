package net.jibini.glutils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

/**
 * Utility class for handling framebuffers.
 */
public class Framebuffer
{
	/**
	 * Stores the framebuffer object handle.
	 */
	private int framebuffer;
	
	/**
	 * Stores the depth renderbuffer object handle.
	 */
	private int depthRenderbuffer;
	
	/**
	 * Stores the render texture object handle.
	 */
	private int renderTexture;
	
	/**
	 * Mesh renderer for rendering out the framebuffer.
	 */
	private MeshRenderer fboMesh;
	
	/**
	 * Stores the width of the framebuffer.
	 */
	private int width;
	
	/**
	 * Stores the height of the framebuffer.
	 */
	private int height;

	/**
	 * Initializes framebuffer and mesh renderer.
	 * 
	 * @param width Width of the framebuffer.
	 * @param height Height of the framebuffer.
	 */
	public Framebuffer(int width, int height)
	{
		this.width = width;
		this.height = height;
		framebuffer = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);

		renderTexture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, renderTexture);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		depthRenderbuffer = glGenRenderbuffers();
		glBindRenderbuffer(GL_RENDERBUFFER, depthRenderbuffer);
		glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
		glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, depthRenderbuffer);

		glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, renderTexture, 0);
		IntBuffer drawBuffers = BufferUtils.createIntBuffer(1);
		drawBuffers.put(0, GL_COLOR_ATTACHMENT0);
		glDrawBuffers(drawBuffers);
		glBindFramebuffer(GL_FRAMEBUFFER, 0);

		// @formatter:off
		float[] fboMeshData =
		{ 
			-1.0f, -1.0f, 0.0f, 
			1.0f, 1.0f, 1.0f, 1.0f, 
			0.0f, 0.0f, 

			1.0f, -1.0f, 0.0f, 
			1.0f, 1.0f, 1.0f, 1.0f, 
			1.0f, 0.0f, 

			1.0f, 1.0f, 0.0f, 
			1.0f, 1.0f, 1.0f, 1.0f, 
			1.0f, 1.0f, 

			1.0f, 1.0f, 0.0f,
			1.0f, 1.0f, 1.0f, 1.0f,
			1.0f, 1.0f,

			-1.0f, 1.0f, 0.0f, 
			1.0f, 1.0f, 1.0f, 1.0f, 
			0.0f, 1.0f, 

			-1.0f, -1.0f, 0.0f, 
			1.0f, 1.0f, 1.0f, 1.0f, 
			0.0f, 0.0f 
		};
		// @formatter:on

		FloatBuffer fboMeshBuffer = BufferUtils.createFloatBuffer(fboMeshData.length);
		fboMesh = new MeshRenderer();
		fboMesh.createInterleaved(fboMeshBuffer, fboMeshData.length / GLUtils.COORD_ELEMENTS);
	}

	/**
	 * Binds the framebuffer.
	 */
	public void bind()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
	}

	/**
	 * Releases all framebuffers (binds 0).
	 */
	public void release()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}

	/**
	 * Destroys framebuffer, renderbuffer, and texture.
	 */
	public void destroy()
	{
		fboMesh.destroy();
		glDeleteFramebuffers(framebuffer);
		glDeleteRenderbuffers(depthRenderbuffer);
		glDeleteTextures(renderTexture);
	}

	/**
	 * Sets the current viewport to the framebuffer size.
	 */
	public void viewport()
	{
		glViewport(0, 0, width, height);
	}

	/**
	 * Binds the render texture.
	 */
	public void bindTexture()
	{
		glBindTexture(GL_TEXTURE_2D, renderTexture);
	}

	/**
	 * Binds the render texture and renders out 
	 * the framebuffer.
	 */
	public void renderOut()
	{
		bindTexture();
		fboMesh.renderInterleaved();
	}

	/**
	 * Decides the current width of the framebuffer.
	 * 
	 * @return Current width of the framebuffer.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Decides the current height of the framebuffer.
	 * 
	 * @return Current height of the framebuffer.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Gives access to the framebuffer object.
	 * 
	 * @return Framebuffer object handle.
	 */
	public int getFramebuffer()
	{
		return framebuffer;
	}

	/**
	 * Gives access to the depth renderbuffer object.
	 * 
	 * @return Depth renderbuffer object handle.
	 */
	public int getDepthRenderbuffer()
	{
		return depthRenderbuffer;
	}

	/**
	 * Gives access to the render texture object.
	 * 
	 * @return Render texture object handle.
	 */
	public int getRenderTexture()
	{
		return renderTexture;
	}
}
