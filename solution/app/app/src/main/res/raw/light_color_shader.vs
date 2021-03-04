uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

uniform vec3 u_LightPos;

attribute vec4 a_Position;
attribute vec4 a_Color;
attribute vec3 a_Normal;
varying vec4 v_Color;

void main()
{
    //vec3 modelViewVertex = vec3(u_MVMatrix * a_Position);
    //vec3 modelViewNormal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));
    //float distance = length(u_LightPos - modelViewVertex);
    //vec3 lightVector = normalize(u_LightPos - modelViewVertex);
    //float diffuse = max(dot(modelViewNormal, lightVector), 0.1);
    //diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance)));
    v_Color = a_Color;  //for now pass color straight through
    gl_Position = projection * view * model * a_Position;
}