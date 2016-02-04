package net.jibini.glutils;

import static org.lwjgl.opengl.GL11.*;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

public class Texture
{
	private int texture;
	private int width, height;

	public Texture(float[] data, int width, int height, int filter)
	{
		this.width = width;
		this.height = height;
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_FLOAT, buffer);
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, texture);
	}

	public void destroy()
	{
		glDeleteTextures(texture);
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getTexture()
	{
		return texture;
	}
}
