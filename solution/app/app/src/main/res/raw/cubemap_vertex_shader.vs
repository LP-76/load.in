uniform mat4 model;  //transpose and scale
uniform mat4 view;   //camera
uniform mat4 projection;  //view space

attribute vec4 a_Position;
attribute vec4 a_Color;
attribute vec3 a_tex_direction;


varying vec4 v_Color; //output variables
varying vec3 direction;


void main()
{
    v_Color = a_Color;  //main color passthrough
    gl_Position = projection * view * model * a_Position;
    direction = a_tex_direction;  //texture coord passthrough
}