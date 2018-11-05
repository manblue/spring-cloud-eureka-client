package com.ml.example.asm;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * ��ȡ�ӿ�class �ļ���Ϣ��
 * */
public class ClassPrinter extends ClassVisitor {

	public final static String pversion = "version";
	public final static String paccess = "access";
	public final static String pname = "name";
	public final static String psignature = "signature";
	public final static String psuperName = "superName";
	public final static String pinterfaces = "interfaces";
	public final static String pdesc = "desc";
	public final static String pvalue = "value";
	public final static String pexceptions = "exceptions";
	public final static String pflag = "flag";
	
	private List<Map<String, Object>> visitMethods = new LinkedList<Map<String,Object>>();
	private Map<String, Object> visitConstructor = new LinkedHashMap<String, Object>();

	private Class interfaceClass;
	
	private ClassPrinter() {
		super(Opcodes.ASM5);
	}
	
	public static ClassPrinter getNewPrinter() {
//		if (interfaceClass.isInterface()) {
//			return new ClassPrinter();
//		}
		return new ClassPrinter();

//		throw new IllegalArgumentException(interfaceClass+"must be Interface class");
	}

	@Override
	public void visit(int version, int access, String name, String signature,
			String superName, String[] interfaces) {
		// TODO Auto-generated method stub
		super.visit(version, access, name, signature, superName, interfaces);
		visitConstructor.put(pflag, "visit");
		visitConstructor.put(pversion, version);
		visitConstructor.put(paccess, access);
		visitConstructor.put(pname, name);
		visitConstructor.put(psignature, signature);
		visitConstructor.put(psuperName, superName);
		visitConstructor.put(pinterfaces, interfaces);
		if (ArrayUtils.isNotEmpty(interfaces)) {
			StringBuffer sb = new StringBuffer("");
			for (String string : interfaces) {
				sb.append(string).append(";");
			}
			visitConstructor.put("interfaces1", sb.toString());
		}
		System.out.println("params:"+visitConstructor);
	}
	
	@Override
	public FieldVisitor visitField(int access, String name, String desc,
			String signature, Object value) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put(pflag, "visitField");
		params.put(paccess, access);
		params.put(pname, name);
		params.put(psignature, signature);
		params.put(pdesc, desc);
		params.put(pvalue, value);
		System.out.println("params:"+params);

		return super.visitField(access, name, desc, signature, value);

	}
	
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put(pflag, "visitMethod");
		params.put(paccess, access);
		params.put(pname, name);
		params.put(psignature, signature);
		params.put(pdesc, desc);
		params.put(pexceptions, exceptions);
		if (ArrayUtils.isNotEmpty(exceptions)) {
			StringBuffer sb = new StringBuffer("");
			for (String string : exceptions) {
				sb.append(string).append(";");
			}
			params.put("exceptions1", sb.toString());
		}
		if (access > Opcodes.ACC_ABSTRACT) {
			params.put("access1", access -  Opcodes.ACC_ABSTRACT);
		}
		System.out.println("params:"+params);

		visitMethods.add(params);
		return super.visitMethod(access, name, desc, signature, exceptions);

	}

	public List<Map<String, Object>> getVisitMethods() {
		return visitMethods;
	}

	public Map<String, Object> getVisitConstructor() {
		return visitConstructor;
	}

}
