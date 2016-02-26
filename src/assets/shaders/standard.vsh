#version 330 core

uniform mat4 projection_matrix;
uniform mat4 view_matrix;
uniform mat4 model_matrix;

layout (location = 10) in vec3 vertex_in;
layout (location = 11) in vec4 color_in;
layout (location = 12) in vec2 tex_coord_in;

out vec4 frag_color;
out vec2 tex_coord;

void main()
{
	gl_Position = projection_matrix * view_matrix *
			model_matrix * vec4(vertex_in, 1.0);
	frag_color = color_in;
	tex_coord = tex_coord_in;
}
