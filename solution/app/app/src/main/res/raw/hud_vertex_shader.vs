uniform mat4 model;  //transpose and scale
uniform mat4 view;   //camera
uniform mat4 projection;  //view space

attribute vec4 a_Position;
attribute vec2 a_TexCoord;

varying vec4 v_Color;  //output variables
varying vec2 TexCoord;

void main()
{
    gl_Position = projection * view * model * a_Position;
    TexCoord = a_TexCoord;  //texture coord passthrough
}