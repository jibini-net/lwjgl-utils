## LWJGL-Utils - Inactive and Outdated
Higher level wrappers for LWJGL 3 functionality.

Efforts have been moved towards JGLOOm, see that [organization](https://github.com/team-jgloom), as well as [Swiss GL](https://github.com/jibini-media/swiss-gl).

For new programmers or people learning the ways of OpenGL graphics, the confusing method names and pointer handling can be quite difficult.  This is a library with classes that are built for beginners, with features tailored for those who are making the move from LWJGL 2 to LWJGL 3.  Several core features and display handling are now included, and soon to come are other input handlers and possibly new shading resources.

One noticable difference between LWJGL 2 and LWJGL 3 is that the old `Display` and other built-in classes were removed.  Its replacement was the fully functional and capable GLFW library originally written in C.  To replace these missing classes, this library now has its own that utilize the GLFW library in as efficient of a way as possible.

## Dependencies
 - LWJGL 3, available at their [website](https://www.lwjgl.org/).
 - lwjgl_util.jar, available from LWJGL 2's [download](http://sourceforge.net/projects/java-game-lib/files/Official%20Releases/LWJGL%202.9.3/lwjgl-2.9.3.zip/download).

## Features
### Display
For easier management of the application's window.

	Display display = new Display();
	display.setTitle("Display Test");
	display.setDimensions(800, 450);
	display.setVSync(true);
	display.create();

### Framebuffer
Render more easily into textures and use for post processing.

	Framebuffer framebuffer = new Framebuffer(1920, 1080);
	framebuffer.bind();
	// Render into framebuffer.
	framebuffer.release();
	// Modify matrices and set uniforms.
	freambuffer.renderOut();

### Matrix Set
Keeps track of projection, view and model matrices.

	MatrixSet matrices = new MatrixSet();
	matrices.setIdentities();
	matrices.setUniforms(shader);

### Mesh Renderer
Renders interleaved vertex data up to `GL3.3` standards.

	/* Interleaved   X, Y, Z,   R, G, B, A,   S, T */
	float[] data = { 0, 0, 0,   1, 1, 1, 1,   0, 0 };
	FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(data.length);
	dataBuffer.put(data);
	dataBuffer.flip();
	MeshRenderer renderer = new MeshRenderer();
	renderer.createInterleaved(dataBuffer, 1);
	renderer.renderInterleaved();

### Shader
Create GLSL shaders for lighting and effects.

	Shader stdShader = Shader.createStdShader();
	stdShader.bind();
	stdShader.setStdUniforms(matrices, 0);

### Texture
Make your rendering more colorful and realistic.

	float[] stdTextureData =
	{
		1, 1, 1, 1,
		1, 1, 1, 1,
		1, 1, 1, 1,
		1, 1, 1, 1
	};

	Texture stdTexture = new Texture(stdTextureData, 2, 2, GL_LINEAR);
	stdTexture.bind();

## Example Program

	private Display display;
	private Texture stdTexture;
	private Shader stdShader;
	private MatrixSet matrices;
	MeshRenderer renderer;

	public void start()
	{
		display = new Display();
		display.setTitle("Display");
		display.setDimensions(1920, 1080);
		display.setVSync(true);
		display.create();

		float[] stdTextureData =
		{ 
			1, 1, 1, 1, 
			1, 1, 1, 1, 
			1, 1, 1, 1, 
			1, 1, 1, 1 
		};
			
		float[] rendererData =
		{ 
			0, 0, 0, 
			1, 1, 1, 1, 
			0, 0, 
			
			1, 0, 0, 
			1, 1, 1, 1, 
			1, 0, 
			
			0, 1, 0, 
			1, 1, 1, 1,
			0, 1 
		};

		try
		{
			stdShader = Shader.createStdShader();
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		glEnable(GL_TEXTURE_2D);
		renderer = new MeshRenderer();
		FloatBuffer buffer = BufferUtils.createFloatBuffer(rendererData.length);
		buffer.put(rendererData);
		buffer.flip();
		renderer.createInterleaved(buffer, 3);
		matrices = new MatrixSet();
		stdTexture = new Texture(stdTextureData, 2, 2, GL_LINEAR);
		while (!display.isCloseRequested())
			render();
		renderer.destroy();
		stdShader.destroy();
		stdTexture.destroy();
		display.destroy();
		System.exit(0);
	}

	public void render()
	{
		glClear(GL_COLOR_BUFFER_BIT);
		stdTexture.bind();
		stdShader.bind();
		stdShader.setStdUniforms(matrices, 0);
		glViewport(0, 0, display.getWidth(), display.getHeight());
		renderer.renderInterleaved();
		display.update();
	}
