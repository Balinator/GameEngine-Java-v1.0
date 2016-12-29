#version 440 core

in vec3 position;
in vec2 texture_cord;

out vec2 pass;

void main(void){
	gl_Position = vec4(position,1.0);
	
	pass = texture_cord;
}
