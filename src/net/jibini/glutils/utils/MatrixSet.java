package net.jibini.glutils.utils;

import static org.lwjgl.opengl.GL20.*;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import net.jibini.glutils.GLUtils;
import net.jibini.glutils.Shader;

/**
 * Utility for handling projection, view,
 * and model matrices.
 */
public class MatrixSet
{
	/**
	 * Application projection matrix.
	 */
	public Matrix4f projection;
	
	/**
	 * Application view matrix.
	 */
	public Matrix4f view;
	
	/**
	 * Application model matrix.
	 */
	public Matrix4f model;

	/**
	 * Creates new matrices and sets identity.
	 */
	public MatrixSet()
	{
		projection = new Matrix4f();
		view = new Matrix4f();
		model = new Matrix4f();
		setIdentities();
	}

	/**
	 * Restores matrices to the identity matrix.
	 */
	public void setIdentities()
	{
		projection.setIdentity();
		view.setIdentity();
		model.setIdentity();
	}

	/**
	 * Sends matrices to the given shader.
	 * 
	 * @param shader Shader to send data to.
	 */
	public void setUniforms(Shader shader)
	{
		int projLocation = shader.getUniform(GLUtils.PROJECTION_UNIFORM);
		int viewLocation = shader.getUniform(GLUtils.VIEW_UNIFORM);
		int modeLocation = shader.getUniform(GLUtils.MODEL_UNIFORM);

		uniformMatrix4f(projLocation, projection);
		uniformMatrix4f(viewLocation, view);
		uniformMatrix4f(modeLocation, model);
	}

	/**
	 * Used for setting a mat4 shader uniform.
	 * 
	 * @param location Uniform location of mat4.
	 * @param matrix Matrix to send to uniform.
	 */
	public static void uniformMatrix4f(int location, Matrix4f matrix)
	{
		FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
		matrix.store(matBuffer);
		matBuffer.flip();
		glUniformMatrix4fv(location, false, matBuffer);
	}
}
