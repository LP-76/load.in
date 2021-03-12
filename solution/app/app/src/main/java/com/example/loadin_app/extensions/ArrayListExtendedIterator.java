package com.example.loadin_app.extensions;

import com.example.loadin_app.Load;
import com.example.loadin_app.ui.opengl.Box;

import java.util.ArrayList;

public class ArrayListExtendedIterator<T> implements IExtendedIterator<T>{
    ArrayList<T> data;
    T current;
    int position;
    public ArrayListExtendedIterator(ArrayList<T> list) {
        data = list;
        position = -1;
        current = null;
    }

    @Override
    public boolean hasPrevious() {
        return position > 0;
    }

    @Override
    public T previous() {
        if(!hasPrevious())
            return  null;
        return current =  data.get(--position);
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public int currentPosition() {
        return position;
    }

    @Override
    public T current() {
        return current;
    }

    @Override
    public void goToEnd() {
        position = data.size() - 1;
        current = data.get(position);
    }

    @Override
    public boolean hasNext() {
        return position < data.size() - 1;
    }

    @Override
    public T next() {
        if(!hasNext())
            return  null;
        return current = data.get(++position);
    }
}
