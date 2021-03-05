

uniform samplerCube u_cube;

varying vec4 v_Color;
varying vec3 direction;

void main()
{
    gl_FragColor = v_Color * texture(u_cube, direction);
}