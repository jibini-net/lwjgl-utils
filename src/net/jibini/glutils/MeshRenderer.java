package net.jibini.glutils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import java.nio.FloatBuffer;

/**
 * Renders interleaved vertices compliantly.
 */
public class MeshRenderer
{
	/**
	 * Stores the amount of vertices in the mesh.
	 */
	private int vertexCount;
	
	/**
	 * Stores the vertex array object handle.
	 */
	private int vertexArray;
	
	/**
	 * Stores the vertex buffer object handle.
	 */
	private int vertexBuffer;

	/**
	 * Creates vertex array and vertex buffer objects.
	 */
	public MeshRenderer()
	{
		vertexArray = glGenVertexArrays();
		vertexBuffer = glGenBuffers();
	}

	/**
	 * Renders the stored interleaved data.
	 */
	public void renderInterleaved()
	{
		glBindVertexArray(vertexArray);
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glEnableVertexAttribArray(GLUtils.VERTEX_ATTRIB);
		glEnableVertexAttribArray(GLUtils.COLOR_ATTRIB);
		glEnableVertexAttribArray(GLUtils.TEX_COORD_ATTRIB);

		int stride = GLUtils.COORD_ELEMENTS * 4;
		glVertexAttribPointer(GLUtils.VERTEX_ATTRIB, 3, GL_FLOAT, false, stride, 0);
		glVertexAttribPointer(GLUtils.COLOR_ATTRIB, 4, GL_FLOAT, false, stride, 3 * 4);
		glVertexAttribPointer(GLUtils.TEX_COORD_ATTRIB, 2, GL_FLOAT, false, stride, 7 * 4);
		glDrawArrays(GL_TRIANGLES, 0, vertexCount);

		glDisableVertexAttribArray(GLUtils.VERTEX_ATTRIB);
		glDisableVertexAttribArray(GLUtils.COLOR_ATTRIB);
		glDisableVertexAttribArray(GLUtils.TEX_COORD_ATTRIB);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	/**
	 * Stores interleaved data in the buffer.
	 */
	public void createInterleaved(FloatBuffer data, int vertices)
	{
		vertexCount = vertices;
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	/**
	 * Destroys the buffer and vertex array.
	 */
	public void destroy()
	{
		glDeleteBuffers(vertexBuffer);
		glDeleteVertexArrays(vertexArray);
	}

	/**
	 * Gives access to the vertex array object.
	 * 
	 * @return Vertex array object handle.
	 */
	public int getVertexArray()
	{
		return vertexArray;
	}

	/**
	 * Gives access to the vertex buffer object.
	 * 
	 * @return Vertex buffer object handle.
	 */
	public int getVertexBuffer()
	{
		return vertexBuffer;
	}
}
