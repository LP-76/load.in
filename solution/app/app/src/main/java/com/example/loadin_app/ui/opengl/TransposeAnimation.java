package com.example.loadin_app.ui.opengl;

import java.time.Duration;

public class TransposeAnimation extends Animation {
    private Vector newLocation;
    private Vector transitPath;
    private Vector velocityVector;

    public TransposeAnimation(WorldObject theTarget, Duration targetCompletion, Duration tick, Vector newLocation) {
        super(theTarget, targetCompletion, tick);
        this.newLocation = newLocation;
        updateTransitPath();
        calculateVelocity();

    }

    private void updateTransitPath(){
        transitPath = target.getOffset().add(newLocation.multiply(-1f)).multiply(-1f);
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

        Vector newLocation = target.getOffset().add(velocityVector);  //perform a move according to how much of a vector we need to do
        target.place(newLocation);
        updateTransitPath();
    }

    @Override
    public boolean isComplete() {
        return  transitPath.getLength() <= 2f;  //close enough to count
    }
}
