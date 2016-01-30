package net.jibini.glutils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import java.nio.FloatBuffer;

public class MeshRenderer
{
	private int vertexCount;
	private int vertexArray;
	private int vertexBuffer;

	public MeshRenderer()
	{
		vertexArray = glGenVertexArrays();
		vertexBuffer = glGenBuffers();
	}

	public void renderInterleaved()
	{
		glBindVertexArray(vertexArray);
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glEnableVertexAttribArray(10);
		glEnableVertexAttribArray(11);
		glEnableVertexAttribArray(12);

		int stride = 9 * 4;
		glVertexAttribPointer(10, 3, GL_FLOAT, false, stride, 0);
		glVertexAttribPointer(11, 4, GL_FLOAT, false, stride, 3 * 4);
		glVertexAttribPointer(12, 2, GL_FLOAT, false, stride, 7 * 4);
		glDrawArrays(GL_TRIANGLES, 0, vertexCount);

		glDisableVertexAttribArray(10);
		glDisableVertexAttribArray(11);
		glDisableVertexAttribArray(12);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	public void createInterleaved(FloatBuffer data, int vertices)
	{
		vertexCount = vertices;
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public void destroy()
	{
		glDeleteBuffers(vertexBuffer);
		glDeleteVertexArrays(vertexArray);
	}

	public int getVertexArray()
	{
		return vertexArray;
	}

	public int getVertexBuffer()
	{
		return vertexBuffer;
	}
}
