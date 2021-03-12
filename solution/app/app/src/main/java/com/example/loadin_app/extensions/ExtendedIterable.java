package com.example.loadin_app.extensions;

import androidx.annotation.NonNull;

import java.util.Iterator;

public interface ExtendedIterable<T> extends Iterable<T>{

    @Override
    @NonNull
    IExtendedIterator<T> iterator();
}
