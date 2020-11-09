package com.dj.dagger.bean;

import javax.inject.Inject;

public class Shoe {
    @Inject
    public Shoe() {
    }

    @Override
    public String toString() {
        return "鞋子";
    }
}
