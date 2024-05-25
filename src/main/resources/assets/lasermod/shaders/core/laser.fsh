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
    vec2 distanceFromCentre = vec2((uv0.x - .5) * 2, 0);
    
    // the inner radius of the circle
    float inner_radius = .04; 

    // calculate the falloff based on the distance from the center
    float falloff = dot(distanceFromCentre, distanceFromCentre) - inner_radius; 

    vec4 hitMarker = ColorModulator;

    if(falloff <= 0.0) {
        // Rendering centeral beam of white light
        falloff = 1;
        hitMarker = vec4(1, 1, 1, 1);
    }else {
        // Rendering the falloff of the light
        falloff = (1 - falloff);

        // Handling the end of the laser being less to hide the inperfections with transparency of the hit marker
        float finalFalloff = uv0.y - .9;
        if(finalFalloff > 0.0)
            falloff *= min(1 - (finalFalloff * 10) + .5, 1);

        hitMarker = color * vec4(ColorModulator.rgb, falloff);
    }

    fragColor = hitMarker;
}
