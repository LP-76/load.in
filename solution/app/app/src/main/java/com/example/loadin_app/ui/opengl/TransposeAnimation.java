package com.example.loadin_app.ui.opengl;

import com.example.loadin_app.ui.opengl.programs.IMoveable;
import com.example.loadin_app.ui.opengl.programs.IPlaceable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Consumer;

public class TransposeAnimation extends Animation {
    private Vector startLocation;
    private Vector newLocation;
    private Vector transitPath;
    private Vector velocityVector;
    private boolean complete;
    private Consumer<TransposeAnimation> onComplete;

    public TransposeAnimation(IMoveable theTarget, Duration targetCompletion, Duration tick, Vector newLocation, Consumer<TransposeAnimation> onComplete) {
        super(theTarget, targetCompletion, tick);
        this.newLocation = newLocation;
        this.startLocation = theTarget.getWorldOffset();
        updateTransitPath();
        complete = false;
        this.onComplete = onComplete;
    }

    private void updateTransitPath(){
        transitPath = startLocation.add(newLocation.multiply(-1f)).multiply(-1f);
    }

    @Override
    public void performOperationPerTick() {
        float percentComplete = percentComplete();
        Vector l = startLocation.add( transitPath.multiply(percentComplete));
        target.place(l);

        if(LocalDateTime.now().isAfter(end)){
            complete = true;
            target.place(newLocation);  //put it at the right spot
            onComplete.accept(this);
        }

    }

    @Override
    public boolean isComplete() {
        return  complete;
    }
}
