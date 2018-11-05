package com.ml.example.asm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class InterfaceHandler4Asm extends ClassLoader  implements Opcodes {

	public static Object buildClass(Class clazz) {
		String name = clazz.getSimpleName();
		String nameImpl = name + "Impl";
		String iter = clazz.getName().replace(".", "/");
		System.out.println("clazz.getSimpleName():"+clazz.getSimpleName());
		System.out.println("clazz.getName():"+clazz.getName());
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		cw.visit(V1_7, 
				ACC_PUBLIC + ACC_SUPER, 
				nameImpl, null, "java/lang/Object",
				new String[]{iter});
		cw.visitAnnotation("Lorg/springframework/stereotype/Service;", true);
		//空构造
		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
				"<init>", "()V", null, null);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V",false);
		mv.visitInsn(RETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();
		//添加类的属性
		cw.visitField(Opcodes.ACC_PRIVATE, "hellowService", "Lcom/ml/example/service/HellowService;", null, null)
		.visitAnnotation("Lorg/springframework/beans/factory/annotation/Autowired;", true).visitEnd();
		
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			System.out.println("method:"+method.getName());
			buildMethod(cw, method.getName(), nameImpl);
		}
		
		cw.visitEnd();
		
		//写文件
		byte[] code = cw.toByteArray();
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(nameImpl+".class");
			fos.write(code);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InterfaceHandler4Asm loader = new InterfaceHandler4Asm();
		Class exampleClass = loader.defineClass(nameImpl, code, 0, code.length);
		
		Object obj = null;
		try {
			obj = exampleClass.getConstructor(null).newInstance(null);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	private static void buildMethod(ClassWriter cw,String methodName, String className) {
		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
				methodName, 
				"()V", 
				null,  null);
		mv.visitCode();
//		Label l0 = new Label();
//		mv.visitLabel(l0);
//		mv.visitLineNumber(10, l0);
		mv.visitFieldInsn(GETSTATIC,"java/lang/System",
	            "out",
	            "Ljava/io/PrintStream;");
		mv.visitLdcInsn("调用方法 [" + methodName + "]");
		mv.visitMethodInsn(INVOKEVIRTUAL,
	            "java/io/PrintStream",
	            "println",
	            "(Ljava/lang/String;)V",false);
//	        Label l1 = new Label();
//	        mv.visitLabel(l1);
//	        mv.visitLineNumber(9, l1);
	        mv.visitInsn(RETURN);
//	        Label l2 = new Label();
//	        mv.visitLabel(l2);
//	        mv.visitLocalVariable("this",
//	            "L" + className + ";",
//	            null,
//	            l0,
//	            l2,
//	            0);
	        mv.visitMaxs(2, 1);
	        mv.visitEnd();		
	}
}























































