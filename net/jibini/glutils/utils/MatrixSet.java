package net.jibini.glutils.utils;

import static org.lwjgl.opengl.GL20.*;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import net.jibini.glutils.GLUtils;
import net.jibini.glutils.Shader;

public class MatrixSet
{
	public Matrix4f projection, view, model;

	public MatrixSet()
	{
		projection = new Matrix4f();
		view = new Matrix4f();
		model = new Matrix4f();
		setIdentities();
	}

	public void setIdentities()
	{
		projection.setIdentity();
		view.setIdentity();
		model.setIdentity();
	}

	public void setUniforms(Shader shader)
	{
		int projLocation = shader.getUniform(GLUtils.PROJECTION_UNIFORM);
		int viewLocation = shader.getUniform(GLUtils.VIEW_UNIFORM);
		int modeLocation = shader.getUniform(GLUtils.MODEL_UNIFORM);

		uniformMatrix4f(projLocation, projection);
		uniformMatrix4f(viewLocation, view);
		uniformMatrix4f(modeLocation, model);
	}

	public static void uniformMatrix4f(int location, Matrix4f matrix)
	{
		FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
		matrix.store(matBuffer);
		matBuffer.flip();
		glUniformMatrix4fv(location, false, matBuffer);
	}
}
