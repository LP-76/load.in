package com.example.loadin_app.ui.opengl;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Animation {

    protected WorldObject target;
    protected Duration timeToCompleteAnimation;
    protected Duration tick;
    protected LocalDateTime end;

    public Animation(WorldObject theTarget, Duration targetCompletion, Duration tick){
        target = theTarget;
        this.tick = tick;
        timeToCompleteAnimation = targetCompletion;
        end = LocalDateTime.now().plus(timeToCompleteAnimation);
    }

    public abstract void performOperationPerTick();

    public abstract boolean isComplete();




}
