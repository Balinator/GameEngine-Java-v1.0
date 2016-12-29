#version 440 core

in vec2 pass;

out vec4 out_color;

uniform sampler2D textureSampler;

void main(void){

	out_color = texture(textureSampler,pass);
	
}
