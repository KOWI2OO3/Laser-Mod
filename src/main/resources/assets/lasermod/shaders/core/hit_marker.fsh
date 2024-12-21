#version 150

in vec4 vertexColor;
in vec2 uv0;

uniform vec4 ColorModulator;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor;
    if (color.a == 0.0) {
        discard;
    }

    // Scaling from [0, 1] to [-1, 1]  
    vec2 uv = (uv0 - vec2(.5, .5)) * 2;

    // the inner radius of the circle
    float inner_radius = .04;

    // calculate the falloff based on the distance from the center
    float falloff = dot(uv, uv) - inner_radius;

    vec4 hitMarker = ColorModulator;

    if(falloff <= 0.0) {
        // Rendering centeral beam of white light
        falloff = 1;
        hitMarker = vec4(1, 1, 1, 1);
    }else {
        // Rendering the falloff of the light
        falloff = (1 - falloff);
        hitMarker = color * vec4(ColorModulator.rgb, falloff);
    }
    
    if (hitMarker.a == 0.0) {
        discard;
    }


    fragColor = hitMarker;
}
