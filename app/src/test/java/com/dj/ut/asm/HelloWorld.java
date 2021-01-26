package com.dj.ut.asm;

public class HelloWorld {
    @ASMNeed
    public static void main(String[] args) throws InterruptedException {
        long l = System.currentTimeMillis();
        Thread.sleep(1000);
        long e = System.currentTimeMillis();
        System.err.println("execute :"+(e - l)+"ms.");
    }

    public void a(){

    }
}
