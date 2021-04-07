package com.example.loadin_app.ui.opengl;

import com.example.loadin_app.Load;
import com.example.loadin_app.LoadPlan;
import com.example.loadin_app.extensions.IExtendedIterator;

import java.util.ArrayList;
import java.util.Stack;

public class LoadPlanStepIterator implements IExtendedIterator<LoadPlanStep>{
    private LoadPlan myPlan;

    private ArrayList<LoadPlanStep> steps;
    int index;
    LoadPlanStep current;

    public LoadPlanStepIterator(LoadPlan plan){
        myPlan = plan;
        steps = new ArrayList<LoadPlanStep>();
        IExtendedIterator<Load> loadIterator = plan.iterator();

        while(loadIterator.hasNext()){
            Load l = loadIterator.next();
            IExtendedIterator<Box> bit = l.iterator();
            Stack<Box> boxStack = new Stack<Box>();
            int index = 0;
            while(bit.hasNext()){
                Box b = bit.next();

                LoadPlanStep s = new LoadPlanStep();
                s.loading = true;
                s.currentBox = b;
                s.loadNumber = loadIterator.currentPosition();
                s.totalLoads = loadIterator.size();
                s.currentLoad = l;
                s.stepNumber = index++;
                s.steps = bit.size();
                steps.add(s);
                boxStack.push(b);
            }
            index = 0;
            while(boxStack.size() > 0){
                Box b = boxStack.pop();
                LoadPlanStep s = new LoadPlanStep();
                s.loading = false;
                s.currentBox = b;
                s.currentLoad = l;
                s.stepNumber = index++;
                s.steps = bit.size();
                s.loadNumber = loadIterator.currentPosition();
                s.totalLoads = loadIterator.size();

                steps.add(s);
            }

        }

        index = -1;

    }

    @Override
    public boolean hasNext() {
        return index + 1 < steps.size();
    }

    @Override
    public LoadPlanStep next() {
        current = null;

        if(hasNext()){
            index++;
            current = steps.get(index);
        }
        return current;
    }

    @Override
    public boolean hasPrevious() {
      return  index - 1 >= 0;
    }

    @Override
    public LoadPlanStep previous() {
        current = null;
       if(hasPrevious()){
           index --;
           current = steps.get(index);
       }

        return current;
    }

    @Override
    public int size() {
        return steps.size();
    }

    @Override
    public int currentPosition() {
        return index;
    }

    @Override
    public LoadPlanStep current() {
        return current;
    }

    @Override
    public void goToEnd() {
        while(hasNext())
            next();
    }
}
