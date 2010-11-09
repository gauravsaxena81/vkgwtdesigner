package com.vk.gwt.generator.rebind;

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.vk.gwt.generator.client.Export;

public class JsBridgeGenerator extends Generator{
	private int methodNumber = 0;
	private StringBuffer mDBuffer = new StringBuffer();
	@Override
	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
		SourceWriter sw = getSourceWriter(context, logger, "com.vk.gwt.designer.client.designer", "JsBridgableImpl");
		sw.indent();
		sw.print("public void createBridge(Widget widget){");
		sw.println();
		sw.indent();
		TypeOracle typeOracle = context.getTypeOracle();
		JClassType[] classTypes = typeOracle.getTypes();
		for(int i = 0; i < classTypes.length; i++)
		{
			JMethod[] methods = classTypes[i].getMethods();
			for(int j = 0; j < methods.length; j++)
				if(methods[j].getAnnotation(Export.class) != null)
					createBridge(sw, classTypes[i], methods[j]);
		}
		sw.outdent();sw.outdent();
		sw.println();
		sw.print("}");
		sw.println();
		sw.println(mDBuffer.toString());
		sw.commit(logger);
		return "com.vk.gwt.designer.client.designer.JsBridgableImpl";
	}
	private void createBridge(SourceWriter sw, JClassType classType, JMethod jMethod) {
		sw.print("if(widget instanceof ");sw.print(classType.getName());sw.print(")");
			sw.println();sw.indent();sw.indent();sw.print("init" + methodNumber +"((" + classType.getName() + ")widget);");
		sw.println();sw.outdent();sw.outdent();
			
		mDBuffer.append("\n").append("public native void init" + methodNumber++).append("(" + classType.getName() + " widget)").append("/*-{");
			mDBuffer.append("\n\t").append("widget.@").append(classType.getPackage().getName() + "." + classType.getName());
			mDBuffer.append("::getElement()()." + jMethod.getName() + " = $entry(function(");
			StringBuffer args = new StringBuffer();
			for(int i = 0; i < jMethod.getParameters().length; i++)
				args.append("arg" + i + ",");
			if(args.length() > 0)
				mDBuffer.append(args.deleteCharAt(args.length() - 1).toString());
			mDBuffer.append("){ return widget.").append(jMethod.getJsniSignature()).append("(").append(args.toString()).append(")").append(";})");
		mDBuffer.append("\n").append("}-*/;");
	}
	protected SourceWriter getSourceWriter(GeneratorContext context, TreeLogger logger, String packageName, String className)
	{
		PrintWriter printWriter = context.tryCreate(logger, packageName, className);
	    if (printWriter == null) {
	      return null;
	    }
	    ClassSourceFileComposerFactory composerFactory = new ClassSourceFileComposerFactory(packageName, className);
	    
	    composerFactory.addImplementedInterface("com.vk.gwt.generator.client.JsBridgable");
	    composerFactory.addImport("com.google.gwt.user.client.ui.Widget");
	    TypeOracle typeOracle = context.getTypeOracle();
		JClassType[] classTypes = typeOracle.getTypes();
		for(int i = 0; i < classTypes.length; i++)
		{
			JMethod[] methods = classTypes[i].getMethods();
			for(int j = 0; j < methods.length; j++)
				if(methods[j].getAnnotation(Export.class) != null)
					composerFactory.addImport(classTypes[i].getPackage().getName() + "." + classTypes[i].getName());
		}
	    return composerFactory.createSourceWriter(context, printWriter);
	}

}
