package com.example.loadin_app.extensions;

import java.util.Iterator;

public interface IExtendedIterator<T> extends Iterator<T> {
    boolean hasPrevious();
    T previous();
    int size();
    int currentPosition();
    T current();
    void goToEnd();
}
