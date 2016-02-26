package net.jibini.glutils;

import static org.lwjgl.opengl.GL11.*;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

/**
 * Utility class for handling textures.
 */
public class Texture
{
	/**
	 * Stores texture object handle.
	 */
	private int texture;
	
	/**
	 * Stores the width of the texture.
	 */
	private int width;
	
	/**
	 * Stores the height of the texture.
	 */
	private int height;

	/**
	 * Creates a texture from given data.
	 * 
	 * @param data Array of pixels in order R, G, B, A, ...
	 * @param width Width of the texture in pixels.
	 * @param height Height of the texture in pixels.
	 * @param filter Minification and magnification filter.
	 */
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

	/**
	 * Binds the texture.
	 */
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, texture);
	}

	/**
	 * Destroys the texture.
	 */
	public void destroy()
	{
		glDeleteTextures(texture);
	}

	/**
	 * Decides the width of the texture.
	 * 
	 * @return Width of the texture.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Decides the height of the texture.
	 * 
	 * @return Height of the texture.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Gives access to the texture object.
	 * 
	 * @return Texture object handle.
	 */
	public int getTexture()
	{
		return texture;
	}
}
