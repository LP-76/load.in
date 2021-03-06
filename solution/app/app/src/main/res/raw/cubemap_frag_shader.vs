precision mediump float;

uniform samplerCube u_cube;

varying vec4 v_Color;
varying vec3 direction;

void main()
{
    gl_FragColor = v_Color * textureCube(u_cube, direction);
   // gl_FragColor = v_Color ;
}