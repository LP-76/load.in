package com.example.loadin_app.ui.opengl;

import java.util.Arrays;
import java.util.stream.Stream;

public class Color implements IConvertToFloat{
    public float red, green, blue, alpha;
    public Color(){
        red = 0f;
        green = 0f;
        blue = 0f;
        alpha = 0f;
    }

    public Color(float red, float green, float blue, float alpha){
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public Color adjust(float percentage){
        return new Color(
                this.red * percentage,
                this.green * percentage,
                this.blue * percentage,
                this.alpha
        );
    }

    @Override
    public Stream<Float> asFloats() {
        return Arrays.stream(new Float[]{
           red,
           green,
           blue,
                alpha
        });
    }
}
