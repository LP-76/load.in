precision mediump float;

uniform sampler2D u_Texture;

varying vec4 v_Color;
varying vec2 TexCoord;

void main()
{
    gl_FragColor = v_Color * texture2D(u_Texture,TexCoord);
}