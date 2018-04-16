attribute vec4 vPosition;
attribute vec2 vCoord;
uniform mat4 vMatrix;

varying vec2 textureCoordinate;

void main(){
    gl_Position = vMatrix*vPosition;
    gl_Position = vec4(gl_Position.x, -gl_Position.y, gl_Position.z, gl_Position.w);
    textureCoordinate = vCoord;
}