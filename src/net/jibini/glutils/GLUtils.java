package net.jibini.glutils;

public class GLUtils
{
	/**
	 * Position of the vertex shader attribute.
	 */
	public static final int VERTEX_ATTRIB = 10;

	/**
	 * Position of the color shader attribute.
	 */
	public static final int COLOR_ATTRIB = 11;

	/**
	 * Position of the texture coordinate shader 
	 * attribute.
	 */
	public static final int TEX_COORD_ATTRIB = 12;

	/**
	 * Number of floats in one interleaved 
	 * coordinate.
	 */
	public static final int COORD_ELEMENTS = 9;

	/**
	 * Name of the projection matrix shader uniform.
	 */
	public static final String PROJECTION_UNIFORM = "projection_matrix";

	/**
	 * Name of the view matrix shader uniform.
	 */
	public static final String VIEW_UNIFORM = "view_matrix";

	/**
	 * Name of the model matrix shader uniform.
	 */
	public static final String MODEL_UNIFORM = "model_matrix";

	/**
	 * Name of the texture sampler shader uniform.
	 */
	public static final String SAMPLER_UNIFORM = "sampler";
}
