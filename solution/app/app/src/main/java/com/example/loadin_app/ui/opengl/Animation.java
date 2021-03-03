package com.example.loadin_app.ui.opengl;

import java.time.Duration;

public abstract class Animation {

    protected WorldObject target;
    protected Duration timeToCompleteAnimation;
    protected Duration tick;

    public Animation(WorldObject theTarget, Duration targetCompletion, Duration tick){
        target = theTarget;
        this.tick = tick;
        timeToCompleteAnimation = targetCompletion;
    }

    public abstract void performOperationPerTick();

    public abstract boolean isComplete();




}
