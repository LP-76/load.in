precision mediump float;

uniform samplerCube u_cube;

varying vec4 v_Color;
varying vec3 direction;

void main()
{

    if(v_Color.x == 0.0 && v_Color.y == 0.0 && v_Color.z == 0.0){
        gl_FragColor = textureCube(u_cube, direction);
    }
    else {
        vec4 color1 = v_Color * 0.5;
        vec4 color2 = textureCube(u_cube, direction) * 0.5;
        vec4 color3 = color1 + color2;
        gl_FragColor = color3;
    }


   // gl_FragColor = v_Color ;
}