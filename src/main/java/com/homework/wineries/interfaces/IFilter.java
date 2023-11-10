package com.homework.wineries.interfaces;

public interface IFilter<T> {
    T execute(T input);
}

