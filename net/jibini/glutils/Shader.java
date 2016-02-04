package net.jibini.glutils;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUseProgram;
import java.io.IOException;
import net.jibini.glutils.utils.FileLoader;
import net.jibini.glutils.utils.MatrixSet;

public class Shader
{
	private int program;

	public Shader(String vertex, String fragment)
	{
		int vertShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertShader, vertex);
		glCompileShader(vertShader);
		checkCompileStatus(vertShader);

		int fragShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragShader, fragment);
		glCompileShader(fragShader);
		checkCompileStatus(fragShader);

		program = glCreateProgram();
		glAttachShader(program, vertShader);
		glAttachShader(program, fragShader);
		glLinkProgram(program);

		glDeleteShader(vertShader);
		glDeleteShader(fragShader);
	}

	private void checkCompileStatus(int shader)
	{
		int status = glGetShaderi(shader, GL_COMPILE_STATUS);

		if (status == GL_FALSE)
		{
			int logLength = glGetShaderi(shader, GL_INFO_LOG_LENGTH);
			String log = glGetShaderInfoLog(shader, logLength);
			System.out.println("Shader error:\n" + log);
		}
	}

	public void setStdUniforms(MatrixSet matrices, int sampler)
	{
		matrices.setUniforms(this);
		int samplerLocation = getUniform(GLUtils.SAMPLER_UNIFORM);
		glUniform1i(samplerLocation, sampler);
	}

	public void bind()
	{
		glUseProgram(program);
	}

	public void destroy()
	{
		glDeleteProgram(program);
	}

	public int getUniform(String name)
	{
		return glGetUniformLocation(program, name);
	}

	public int getAttrib(String name)
	{
		return glGetAttribLocation(program, name);
	}

	public int getProgram()
	{
		return program;
	}

	public static Shader createStdShader() throws IOException
	{
		String base = "/assets/shaders/standard";
		String vertex = FileLoader.loadFile(base + ".vsh");
		String fragment = FileLoader.loadFile(base + ".fsh");
		Shader result = new Shader(vertex, fragment);
		return result;
	}
}
