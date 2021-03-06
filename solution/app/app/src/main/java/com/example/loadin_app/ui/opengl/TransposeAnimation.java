package com.example.loadin_app.ui.opengl;

import java.time.Duration;
import java.time.LocalDateTime;

public class TransposeAnimation extends Animation {
    private Vector newLocation;
    private Vector transitPath;
    private Vector velocityVector;
    private boolean complete;

    public TransposeAnimation(WorldObject theTarget, Duration targetCompletion, Duration tick, Vector newLocation) {
        super(theTarget, targetCompletion, tick);
        this.newLocation = newLocation;
        updateTransitPath();
        calculateVelocity();
        complete = false;
    }

    private void updateTransitPath(){
        transitPath = target.getWorldOffset().add(newLocation.multiply(-1f)).multiply(-1f);
    }

    private void calculateVelocity(){
        //how many ticks do we have to meet our timeline goal
        long expectedNumberOfTicks = timeToCompleteAnimation.toNanos() / tick.toNanos();

        //what is the
        float velocity = transitPath.getLength() / expectedNumberOfTicks;  //the length we have to travel per tick

        velocityVector = transitPath.normalize().multiply(velocity);  //this is the change in each direction we have to make

    }

    @Override
    public void performOperationPerTick() {

        Vector l = target.getWorldOffset().add(velocityVector);  //perform a move according to how much of a vector we need to do
        target.place(l);
        updateTransitPath();

        if(LocalDateTime.now().isAfter(end) || transitPath.getLength() <= 1f){
            complete = true;
            target.place(newLocation);  //put it at the right spot
        }

    }

    @Override
    public boolean isComplete() {

        return  complete;
    }
}
