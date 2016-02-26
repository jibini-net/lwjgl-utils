package net.jibini.glutils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import java.io.IOException;
import net.jibini.glutils.utils.FileLoader;
import net.jibini.glutils.utils.MatrixSet;

/**
 * Utility class for handling shader 
 * programs.
 */
public class Shader
{
	/**
	 * Stores the shader program handle.
	 */
	private int program;

	/**
	 * Creates and compiles shaders and attaches
	 * them to the program.
	 * 
	 * @param vertex Vertex shader source code.
	 * @param fragment Fragment shader source code.
	 */
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

	/**
	 * Checks if a shader is compiled and checks
	 * for errors.
	 */
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

	/**
	 * Sets standard uniforms (matrices and sampler).
	 * 
	 * @param matrices Current set of matrices.
	 * @param sampler Currently bound texture number.
	 */
	public void setStdUniforms(MatrixSet matrices, int sampler)
	{
		matrices.setUniforms(this);
		int samplerLocation = getUniform(GLUtils.SAMPLER_UNIFORM);
		glUniform1i(samplerLocation, sampler);
	}

	/**
	 * Binds the shader program.
	 */
	public void bind()
	{
		glUseProgram(program);
	}

	/**
	 * Destroys the shader program.
	 */
	public void destroy()
	{
		glDeleteProgram(program);
	}

	/**
	 * Retrieves a shader uniform location.
	 * 
	 * @param name Name of the shader uniform.
	 * @return Location of the given uniform.
	 */
	public int getUniform(String name)
	{
		return glGetUniformLocation(program, name);
	}

	/**
	 * Retrieves a shader attribute location.
	 * 
	 * @param name Name of the shader attribute.
	 * @return Location of the given attribute.
	 */
	public int getAttrib(String name)
	{
		return glGetAttribLocation(program, name);
	}

	/**
	 * Gives access to the shader program.
	 * 
	 * @return Shader program handle.
	 */
	public int getProgram()
	{
		return program;
	}

	/**
	 * Creates a simple default shader program.
	 * 
	 * @return Simple default shader program.
	 * @throws IOException if a file loading error occurs.
	 */
	public static Shader createStdShader() throws IOException
	{
		String base = "/assets/shaders/standard";
		String vertex = FileLoader.loadFile(base + ".vsh");
		String fragment = FileLoader.loadFile(base + ".fsh");
		Shader result = new Shader(vertex, fragment);
		return result;
	}
}
