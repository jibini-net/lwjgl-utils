package net.jibini.glutils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL32.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

public class Framebuffer
{
	private int framebuffer;
	private int depthRenderbuffer;
	private int renderTexture;
	private MeshRenderer fboMesh;
	private int width, height;

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

		float[] fboMeshData =
		{ -1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
				1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
				1.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, -1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f,
				0.0f, 0.0f };

		FloatBuffer fboMeshBuffer = BufferUtils.createFloatBuffer(fboMeshData.length);
		fboMesh = new MeshRenderer();
		fboMesh.createInterleaved(fboMeshBuffer, fboMeshData.length / GLUtils.COORD_ELEMENTS);
	}

	public void bind()
	{
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);
	}

	public void destroy()
	{
		glDeleteFramebuffers(framebuffer);
		glDeleteRenderbuffers(depthRenderbuffer);
		glDeleteTextures(renderTexture);
	}

	public void viewport()
	{
		glViewport(0, 0, width, height);
	}

	public void bindTexture()
	{
		glBindTexture(GL_TEXTURE_2D, renderTexture);
	}

	public void renderOut()
	{
		bindTexture();
		fboMesh.renderInterleaved();
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getFramebuffer()
	{
		return framebuffer;
	}

	public int getDepthRenderbuffer()
	{
		return depthRenderbuffer;
	}

	public int getRenderTexture()
	{
		return renderTexture;
	}
}
