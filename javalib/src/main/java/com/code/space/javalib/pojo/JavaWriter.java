package com.code.space.javalib.pojo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.lang.model.element.TypeElement;

/**
 * Created by shangxuebin on 16-5-18.
 */
public class JavaWriter extends PrintWriter{

    private int tabIndex = 0;
    private StringBuilder mSB = new StringBuilder();
    private boolean imported;
    public static final String TAB = "\t";
    public static final String LINE_END = ";";
    public static final String SPACE = " ";
    public static final String PACKAGE = "package";
    public static final String STATIC = "static";
    public static final String FINAL = "final";
    public static final String BLOCK_BEGIN = "{";
    public static final String BLOCK_END = "}";
    public static final String PARAM_BEGIN = "(";
    public static final String PARAM_END = ")";
    public static final String COMMA = ",";
    public static final String WRITE = "=";

    private String packageName;
    private String classSimpleName;

    public String getPackageName(){
        return packageName;
    }

    public String getClassName(){
        initSB();
        return mSB.append(getPackageName()).append(".").append(getClassSimpleName()).toString();
    }

    public String getClassSimpleName(){
        return classSimpleName;
    }



    public JavaWriter(Writer out) {
        super(out);
    }

    public JavaWriter(Writer out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public JavaWriter(OutputStream out) {
        super(out);
    }

    public JavaWriter(OutputStream out, boolean autoFlush) {
        super(out, autoFlush);
    }

    public JavaWriter(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    public JavaWriter(String fileName, String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(fileName, csn);
    }

    public JavaWriter(File file) throws FileNotFoundException {
        super(file);
    }

    public JavaWriter(File file, String csn) throws FileNotFoundException, UnsupportedEncodingException {
        super(file, csn);
    }

    private void initSB(){
        mSB.delete(0,mSB.length());
    }

    public JavaWriter writeSB(){
        println(mSB.toString());
        initSB();
        return this;
    }

    public JavaWriter appendTab(){
        for(int i=0; i<tabIndex; i++){
            mSB.append(TAB);
        }
        return this;
    }

    private void blockBegin(){
        mSB.append(BLOCK_BEGIN);
        moveTab(1);
    }

    public JavaWriter moveTab(int count){
        tabIndex += count;
        return this;
    }


    private void blockEnd(){
        moveTab(-1);
        appendTab();
        mSB.append(BLOCK_END);
        writeSB();
    }

    private void endLine(){
        mSB.append(LINE_END);
    }


    public JavaWriter writePackage(TypeElement element){
        String pack = element.getQualifiedName().toString();
        pack = pack.substring(0, pack.lastIndexOf('.'));
        return writePackage(pack);
    }

    public JavaWriter writePackage(String packageName){
        //package com.code.space.javalib.pojo;
        this.packageName = packageName;
        mSB.append(PACKAGE).append(SPACE).append(packageName);
        endLine();
        writeSB();
        println();
        return this;
    }

    public JavaWriter writeImport(String importClass){
        //import java.io.File;
        imported = true;
        mSB.append("import").append(SPACE).append(importClass);
        endLine();
        writeSB();
        return this;
    }

    public JavaWriter classBegin(String className){
        classBegin(Accesible.PUBLIC,className);
        return this;
    }

    public JavaWriter classBegin(Accesible accesible, String className){
        classBegin(accesible,className,null,null);
        return this;
    }

    public JavaWriter classBegin(Accesible accesible, String className, SuperClass superClass, String superClassName){
        return classBegin(accesible,false,false,className,superClass,superClassName);
    }

    public JavaWriter classBegin(Accesible accesible, boolean isStatic, boolean isFinal, String className, SuperClass superClass, String superClassName){
        if(imported) println();
        //public class JavaWriter extends PrintWriter{
        mSB.append(accesible.getName()).append(SPACE);
        if(isStatic) mSB.append(STATIC).append(SPACE);
        if(isFinal) mSB.append(FINAL).append(SPACE);
        mSB.append("class").append(SPACE).append(className);
        if(superClass!=null) mSB.append(SPACE).append(superClass.getName()).append(SPACE).append(superClassName);
        blockBegin();
        writeSB();
        this.classSimpleName = className;
        return this;
    }


    public void classEnd() {
        blockEnd();
    }


    public JavaWriter methodBegin(Accesible accesible, String returnName, String methodName, String params){
        println();
        appendTab();
        mSB.append(accesible.getName()).append(SPACE).append(returnName).append(SPACE).append(methodName).append(PARAM_BEGIN);
        if(params!=null) mSB.append(params);
        mSB.append(PARAM_END);
        blockBegin();
        writeSB();
        return this;
    }

    public JavaWriter methodBegin(Accesible accesible,boolean isStatic, boolean isFinal, String returnName, String methodName, String params){
        println();
        appendTab();
        mSB.append(accesible.getName()).append(SPACE);
        if(isStatic) mSB.append(STATIC).append(SPACE);
        if(isFinal) mSB.append(FINAL).append(SPACE);
        mSB.append(returnName).append(SPACE).append(methodName).append(PARAM_BEGIN);
        if(params!=null) mSB.append(params);
        mSB.append(PARAM_END);
        blockBegin();
        writeSB();
        return this;
    }



    public JavaWriter methodEnd(){
        blockEnd();
        return this;
    }

    public JavaWriter writeField(Accesible accesible,boolean isStatic, boolean isFinal, String type, String filedName, String writeValue){
        appendTab();
        mSB.append(accesible.getName()).append(SPACE);
        if(isStatic) mSB.append(STATIC).append(SPACE);
        if(isFinal) mSB.append(FINAL).append(SPACE);
        mSB.append(type).append(SPACE).append(filedName);
        if(writeValue!=null) mSB.append(WRITE).append(writeValue);
        endLine();
        blockBegin();
        writeSB();
        return this;
    }

    public JavaWriter writeNomal(String javaCode){
        appendTab();
        mSB.append(javaCode);
        writeSB();
        return this;
    }

    public JavaWriter writeNomal(String... javaCodes){
        appendTab();
        for(String code:javaCodes) mSB.append(code);
        writeSB();
        return this;
    }


}
