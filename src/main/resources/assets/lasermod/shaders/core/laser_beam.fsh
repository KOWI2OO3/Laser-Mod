#version 150

in vec4 vertexColor;
in vec2 uv0;

uniform vec4 ColorModulator;
uniform float GameTime;

uniform float frequency;
uniform float heightOffset;
uniform float heightMul;
uniform float speed;

// length of the laser in blocks
uniform float laserLength;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor;
    if (color.a == 0.0) {
        discard;
    }

    float _frequenct = frequency * laserLength;
    float _speed = speed / laserLength;

    // variable to control the edge cuttoff speed
    float offset = GameTime * -1500 * _speed;

    float xTop = heightMul * (sin((uv0.x + offset) * _frequenct) + heightOffset);
    float xBottom = heightMul * (cos((uv0.x + offset) * _frequenct) + heightOffset);

    // Cutting of the edges of the laser
    if(uv0.y > xTop || uv0.y < -xBottom) {
        discard;
    }

    fragColor = color * ColorModulator;

    // Adding a gradient to the inner laser, note the uv0.y is from a range -1 to 1 and we want 1 to 0 to 1
    fragColor.xyz += vec3(.5, .5, .5) * (1-abs(uv0.y));
}
