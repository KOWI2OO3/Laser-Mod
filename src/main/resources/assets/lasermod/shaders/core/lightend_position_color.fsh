#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
in vec4 normal;
in float alpha;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0);
    if (alpha < 0.0) {
        discard;
    }
    color *= vertexColor * ColorModulator;
    color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
    color *= lightMapColor;
    fragColor = vec4(linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor).rgb, 0.5);
}
