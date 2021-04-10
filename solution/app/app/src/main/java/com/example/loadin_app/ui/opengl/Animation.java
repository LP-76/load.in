package com.example.loadin_app.ui.opengl;

import com.example.loadin_app.ui.opengl.programs.IMoveable;
import com.example.loadin_app.ui.opengl.programs.IPlaceable;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Animation {

    protected IMoveable target;
    protected Duration timeToCompleteAnimation;
    protected Duration tick;
    protected LocalDateTime end;
    protected LocalDateTime start;

    public Animation(IMoveable theTarget, Duration targetCompletion, Duration tick){
        target = theTarget;
        this.tick = tick;
        timeToCompleteAnimation = targetCompletion;
        end = LocalDateTime.now().plus(timeToCompleteAnimation);
        start = LocalDateTime.now();
    }

    public float percentComplete(){
        long millisSinceStart = Duration.between(start, LocalDateTime.now()).toMillis();
        return (float)millisSinceStart / (float)timeToCompleteAnimation.toMillis();
    }

    public abstract void performOperationPerTick();

    public abstract boolean isComplete();




}
