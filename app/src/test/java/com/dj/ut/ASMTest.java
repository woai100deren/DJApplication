package com.dj.ut;

import net.bytebuddy.jar.asm.Opcodes;

import org.junit.Test;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ASMTest {
    @Test
    public void test() throws IOException {
        FileInputStream fis = new FileInputStream(new File("src/test/java/com/dj/ut/asm/HelloWorld.class"));

        //以下是ASM的过程
        //class的分析器
        ClassReader cr = new ClassReader(fis);
        //栈帧：class自动计算栈帧和局部变量表的大小
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        //执行分析
        cr.accept(new MyClassVisitor(Opcodes.ASM7,cw),ClassReader.EXPAND_FRAMES);

        byte[] bytes = cw.toByteArray();
        FileOutputStream fos = new FileOutputStream(new File("src/test/java2/com/dj/ut/asm/HelloWorld.class"));
        fos.write(bytes);
        fos.close();
    }

    /**
     * ClassVisitor 只能访问类
     */
    static class MyClassVisitor extends ClassVisitor {
        public MyClassVisitor(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
            return new MyMethodVisitor(api,methodVisitor,access,name,descriptor);
        }
    }

    /**
     * org.ow2.asm:asm-commons:7.1工具包提供了一些便捷的操作，所以这里继承了工具包中的AdviceAdapter，而不是直接继承MethodVisitor
     */
    static class MyMethodVisitor extends AdviceAdapter {
        private int enterIndex;
        private boolean inject = false;
        protected MyMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(api, methodVisitor, access, name, descriptor);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            //判断，只有加了ASMNeed注解的方法，才插装
            if("Ljava/com/dj/ut/asm/ASMNeed;".equals(descriptor)){
                inject = true;
            }else{
                inject = false;
            }
            return super.visitAnnotation(descriptor, visible);
        }

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();
            if(!inject){
                return;
            }
            //方法进入时，插入long l = System.currentTimeMillis();
            invokeStatic(Type.getType("Ljava/lang/System;"),new Method("currentTimeMillis","()J"));
            //创建本地变量的索引
            enterIndex = newLocal(Type.LONG_TYPE);
            //让上一步执行的结果，用一个本地变量接收
            storeLocal(enterIndex);
        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
            if(!inject){
                return;
            }
            //方法退出时，插入
            // long e = System.currentTimeMillis();
            // System.error.println("execute :"+(e-l)+"ms.")

            //方法进入时，插入long l = System.currentTimeMillis();
            invokeStatic(Type.getType("Ljava/lang/System;"),new Method("currentTimeMillis","()J"));
            //创建本地变量的索引
            int s = newLocal(Type.LONG_TYPE);
            //让上一步执行的结果，用一个本地变量接收
            storeLocal(s);

            getStatic(Type.getType("Ljava/lang/System;"),"out",Type.getType("Ljava/io/PrintStream;"));
            newInstance(Type.getType("Ljava/lang/StringBuilder;"));
            dup();

            invokeConstructor(Type.getType("Ljava/lang/StringBuilder;"),new Method("<int>","()V"));
            visitLdcInsn("execute :");

            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new Method("append","(Ljava/lang/String;)Ljava/lang/StringBuilder;"));

            //顺序不能错，先load结束，再load开始
            loadLocal(s);
            loadLocal(enterIndex);
            math(SUB,Type.LONG_TYPE);

            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new Method("append","(J)Ljava/lang/StringBuilder;"));
            visitLdcInsn("ms.");

            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new Method("append","(Ljava/lang/String;)Ljava/lang/StringBuilder;"));
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),new Method("toString","()Ljava/lang/String;"));
            invokeVirtual(Type.getType("Ljava/io/PrintStream;"),new Method("println","(Ljava/lang/String;)V"));
        }
    }
}

