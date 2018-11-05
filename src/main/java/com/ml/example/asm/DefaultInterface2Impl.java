package com.ml.example.asm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.ml.example.service.RobbService;
import com.ml.example.service.impl.HellowServiceImpl;

/**
 * 接口class默认IMPL
 * */
public class DefaultInterface2Impl {

	final static String superName = "java/lang/Object";
	final static String separator = "/";
	/**接口类全名 com/ml/example/service/RobbService */
	final static String kFullInterfaceName = "fullInterfaceName";
	/**接口类名*/
	final static String kInterfaceName = "interfaceName";
	/**接口IMPL类全名 com/ml/example/service/impl/RobbServiceImpl */
	final static String kFullImplName = "fullImplName";
	/**接口IMPL类名 */
	final static String kImplName = "implName";
	/**字段名称*/
	final static String kFiedlName = "fieldName";
	/**字段描述*/
	final static String kFieldDesc = "fieldDesc";
	public static Class buildImplClass(Class interfaceClass) {
		Class implClass = null;
		try {

			InterfaceHandler4Asm handler4Asm = new InterfaceHandler4Asm(interfaceClass.getClassLoader());
			//获取相关签名信息
			ClassPrinter printer = ClassPrinter.getNewPrinter();
			InputStream is = ClassLoader.getSystemResourceAsStream(interfaceClass.getName().replace(".", "/")+".class");
			ClassReader cReader = new ClassReader(is);
			cReader.accept(printer, 0);
			
			Map<String, Object> outPutParams = new HashMap<String, Object>();
			outPutParams.put(kInterfaceName, interfaceClass.getSimpleName());
			outPutParams.put(kFullInterfaceName, interfaceClass.getName().replace('.', '/'));
			outPutParams.put(kImplName, interfaceClass.getSimpleName()+"Impl");
			ClassWriter cw = handler4Asm.buildClassHead(printer.getVisitConstructor(),outPutParams);
			FieldVisitor fv = handler4Asm.buildClassField(cw, printer.getVisitConstructor(),outPutParams);
			handler4Asm.buildClassMethod(cw, printer.getVisitMethods(),outPutParams);
			//写文件
			byte[] code = cw.toByteArray();
			FileOutputStream fos = null;
			String outFile = interfaceClass.getResource("/").getPath()+(String) outPutParams.get(kFullImplName)+".class";
			System.out.println("outFile====="+outFile);
				fos = new FileOutputStream(outFile);
				fos.write(code);
				fos.close();


			implClass = handler4Asm.defineClazz(((String) outPutParams.get(kFullImplName)).replace('/', '.'), code, 0, code.length);
//			implClass = interfaceClass.getClassLoader().loadClass(((String) outPutParams.get(kFullImplName)).replace('/', '.'));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return implClass;
	}
	
	static class InterfaceHandler4Asm extends ClassLoader implements Opcodes {

		public InterfaceHandler4Asm(ClassLoader parent) {
			super(parent);
		}
		//拼装类头信息，构造方法
		public ClassWriter buildClassHead(Map<String, Object> classHead, Map<String, Object> outPutParams) {
			String name = (String) classHead.get(ClassPrinter.pname);
			String nameImpl = StringUtils.substringBeforeLast(name, separator)+separator+"impl"+separator+StringUtils.substringAfterLast(name, separator)+"Impl";
			outPutParams.put(kFullImplName, nameImpl);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			cw.visit(V1_7, 
					ACC_PUBLIC + ACC_SUPER,
					nameImpl, null, superName,
					new String[]{name});
			cw.visitAnnotation("Lorg/springframework/stereotype/Service;", true);
			//空构造
			MethodVisitor mv = cw.visitMethod(ACC_PUBLIC,
					"<init>", "()V", null, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V",false);
			mv.visitInsn(RETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
			return cw;
		}
		
		//拼装字段信息
		public FieldVisitor buildClassField(ClassWriter cw,Map<String, Object> classHead, Map<String, Object> outPutParams) {
			String name = (String) classHead.get(ClassPrinter.pname);
			String sname = StringUtils.substringAfterLast(name, separator);
			String fieldName = StringUtils.lowerCase(StringUtils.substring(sname, 0, 1))+StringUtils.substring(sname, 1);
			String fieldDesc = "L"+name+";";
			outPutParams.put(kFiedlName, fieldName);
			outPutParams.put(kFieldDesc, fieldDesc);
			FieldVisitor fv = cw.visitField(Opcodes.ACC_PRIVATE, fieldName, fieldDesc, null, null);
			
			fv.visitAnnotation("Lorg/springframework/beans/factory/annotation/Autowired;", true);
			fv.visitEnd();
			return fv;
		}
		//拼装方法信息
		public void buildClassMethod(ClassWriter cw, List<Map<String, Object>> classMethods, Map<String, Object> outPutParams) {
			String fieldName = (String) outPutParams.get(kFiedlName);
			String fieldDesc = (String) outPutParams.get(kFieldDesc);
			String fullInterfaceName = (String) outPutParams.get(kFullInterfaceName);
			String fullImplName = (String) outPutParams.get(kFullImplName);
			for (Map<String, Object> classMethod : classMethods) {
				String mDesc = (String)classMethod.get(ClassPrinter.pdesc);
				String mName = (String)classMethod.get(ClassPrinter.pname);
				boolean returnFlag = mDesc.contains(")V") ? false : true;//是否有返回
				MethodVisitor mv = cw.visitMethod((Integer)classMethod.get(ClassPrinter.paccess )- ACC_ABSTRACT, 
						mName, 
						mDesc, 
						(String)classMethod.get(ClassPrinter.psignature), 
						(String[])classMethod.get(ClassPrinter.pexceptions));
				mv.visitCode();
				//字段调用逻辑
				mv.visitVarInsn(ALOAD, 0);//this
				if (mName.startsWith("set")) {
					mv.visitVarInsn(ALOAD, 1);
					mv.visitFieldInsn(PUTFIELD, fullImplName, fieldName, fieldDesc);
				}else {
					mv.visitFieldInsn(GETFIELD, fullImplName, fieldName, fieldDesc);//fieldName
					//入参描述符分割
					buildMethodVisitorArgs(mv, mDesc);
					mv.visitMethodInsn(INVOKEINTERFACE, fullInterfaceName, mName, mDesc, true);//mName

				}

//				mv.visitVarInsn(ALOAD, 1);//入参1
				/**
				for (Method mh : RobbService.class.getDeclaredMethods()) {
					System.out.println("--"+Type.getMethodDescriptor(mh));
					
					for (Type type : Type.getArgumentTypes(mh)) {
						System.out.println("-----"+type.getDescriptor());
					}
				}
				*/
				
				mv.visitInsn(returnFlag?ARETURN:RETURN);
				mv.visitMaxs(3, 1);
				mv.visitEnd();
			}
		}
		
		  /**
		   * 入参描述符分割
		   *  我们知道JAVA类型分为基本类型和引用类型，在JVM中对每一种类型都有与之相对应的类型描述，如下表：
		   Java类型---------------JVM中的描述
		   boolean----------------Z--------------------------iload
		   char---------------------C--------------------------iload
		   byte---------------------B--------------------------iload
		   short--------------------S--------------------------iload
		   int------------------------I--------------------------iload
		   float---------------------F--------------------------fload
		   long---------------------J--------------------------lload
		   double------------- ----D-------------------------dload
		   Object-----------------Ljava/lang/Object;------aload
		   int----------------------[I------------------------  -aload
		   Object-----------------[[Ljava/lang/Object;----aload
       */
		private static  void buildMethodVisitorArgs(MethodVisitor mv,String methodArgsDesc) {
			if (methodArgsDesc.startsWith("()")) {//无参数
				return ;
			}
			
			String argsDesc = StringUtils.substringBefore(StringUtils.substringAfter(methodArgsDesc, "("), ")");
			
			String[] args = StringUtils.split(argsDesc,';');
			List<Integer> loadList = new LinkedList<Integer>();
			boolean skip = false;//跳过标识
			for (int i = 0; i < args.length; i++) {
				for (char ch : args[i].toCharArray()) {
					System.out.println("-------------"+ch+"---"+skip);
					if (ch == 'Z') {
						if (skip) {
							skip = false;
							continue;
						}
						loadList.add(ILOAD);
					}else if(ch == 'C'){
						if (skip) {
							skip = false;
							continue;
						}
						loadList.add(ILOAD);
					}else if(ch == 'B'){
						if (skip) {
							skip = false;
							continue;
						}
						loadList.add(ILOAD);
					}else if(ch == 'S'){
						if (skip) {
							skip = false;
							continue;
						}
						loadList.add(ILOAD);
					}else if(ch == 'I'){
						if (skip) {
							skip = false;
							continue;
						}
						loadList.add(ILOAD);
					}else if(ch == 'F'){
						if (skip) {
							skip = false;
							continue;
						}
						loadList.add(FLOAD);
					}else if(ch == 'J'){
						if (skip) {
							skip = false;
							continue;
						}
						loadList.add(LLOAD);
					}else if(ch == 'D'){
						if (skip) {
							skip = false;
							continue;
						}
						loadList.add(DLOAD);
					}else if(ch == 'L'){
						if (skip) {
							skip = false;
							break;
						}
						loadList.add(ALOAD);
						break;
						
					}else if(ch == '['){
						if (!skip) {
							loadList.add(ALOAD);
						}
						skip = true;
					}else if (ch == '(' || ch == ')') {
						continue;
					}else {
						throw new IllegalArgumentException("deal methodArgsDesc["+methodArgsDesc+"] err:"+ch);
					}
				}
			}
			
			int loadNum = 1;
			for (Integer loadOpcode : loadList) {
				System.out.println(loadOpcode+"-----------"+loadNum);
				if (loadNum == 8) {
					loadNum++;
				}
				mv.visitVarInsn(loadOpcode, loadNum++);
				
			}
			
		}
		
		public final Class<?> defineClazz(String name, byte[] b, int off, int len)
	            throws ClassFormatError
	        {
				try {
					Method cc = ClassLoader.class.getDeclaredMethod("defineClass", String.class,byte[].class,int.class,int.class);
					cc.setAccessible(true);
					return (Class<?>)cc.invoke(getParent(), new Object[]{name,b,off,len});
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
	            return super.defineClass(name, b, off, len, null);
	        }
	}
}
