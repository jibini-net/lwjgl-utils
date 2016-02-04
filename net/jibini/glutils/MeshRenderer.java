package net.jibini.glutils;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
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
