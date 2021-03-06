uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

attribute vec4 a_Position;
attribute vec4 a_Color;

varying vec4 v_Color;

void main()
{
    v_Color = a_Color;  //for now pass color straight through
    gl_Position = projection * view * model * a_Position;
}