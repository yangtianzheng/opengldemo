precision mediump float;
varying vec2 textureCoordinate;
uniform sampler2D vTexture;
void main() {
    vec4 color=texture2D( vTexture, textureCoordinate);
    float rgb = 0.2126 * color.r + 0.7152 * color.g + 0.0722 * color.b;
    vec4 c=vec4(rgb,rgb,rgb,color.a);
    gl_FragColor = c;
}