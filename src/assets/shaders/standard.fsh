#version 330 core

uniform sampler2D sampler;

in vec4 frag_color;
in vec2 tex_coord;

layout (location = 0) out vec4 final_color;

void main()
{
    vec4 texel = texture(sampler, tex_coord);
    final_color = frag_color * texel;
}
