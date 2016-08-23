package com.code.space.javalib.viewinject;

import com.code.space.javalib.pojo.Accesible;
import com.code.space.javalib.pojo.JavaWriter;
import com.code.space.javalib.pojo.SuperClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by shangxuebin on 2016/5/17.
 */
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class InjectProcessor extends AbstractProcessor {

    static final String FILE_NAME = "ViewFinder";
    static final String ROUTER_PACKAGE_NAME = "com.code.space";
    static final String ROUTER_CLASS_NAME = "BinderRouterImpl";

    static final String ROUTER_FULL_NAME = ROUTER_PACKAGE_NAME + "." + ROUTER_CLASS_NAME;

    private String builderFile = null;

    @Override
    public Set<String> getSupportedAnnotationTypes() {

        Set<String> typeSet = new LinkedHashSet<String>();

        typeSet.add(FindView.class.getCanonicalName());
        return typeSet;
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Map<TypeElement, List<Element>> elementMap = new HashMap<>();
        Map<String, String> finderMap = new HashMap<>();

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(FindView.class);
        int index = 0;
        TypeElement typeElement;
        List<Element> elementList;
        for (Element e : elements) {
            if (e.getKind() == ElementKind.CLASS) {
                continue;
            }
            typeElement = (TypeElement) e.getEnclosingElement();
            elementList = elementMap.get(typeElement);
            if (elementList == null) {
                elementList = new ArrayList<Element>();
                elementMap.put(typeElement, elementList);
            }
            elementList.add(e);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "My Processor element index=" + index++);
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "My Processor element " + e.getSimpleName());
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "My Processor element " + ((TypeElement) e.getEnclosingElement()).getQualifiedName());
        }

        Iterator<Map.Entry<TypeElement, List<Element>>> iterator = elementMap.entrySet().iterator();
        Map.Entry<TypeElement, List<Element>> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            typeElement = entry.getKey();
            elementList = entry.getValue();
            try {
                String binderTargetName = typeElement.getQualifiedName().toString();
                String binderTargeSimpletName = typeElement.getSimpleName().toString();
                String binderFllName = binderTargetName + FILE_NAME;
                String binderSimpleName = typeElement.getSimpleName() + FILE_NAME;
                JavaFileObject f = processingEnv.getFiler().createSourceFile(binderFllName);
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "My Processor java file " + f.toUri());
                JavaWriter java = new JavaWriter(f.openWriter());
                java.writePackage(typeElement);
                java.writeImport(IViewFinder.class.getName());
                java.writeImport(ROUTER_FULL_NAME);
                java.classBegin(Accesible.PUBLIC, binderSimpleName, SuperClass.IMPLEMENT, IViewFinder.class.getSimpleName());


                java.methodBegin(Accesible.PUBLIC, "void", "handleObject", "Object target, Object viewHolder");
                for (Element element : elementList) {
                    // target.field = viewHolder.findViewById(int id);

                    java.writeNomal(
                            "((" + binderTargeSimpletName + ")target).", element.getSimpleName().toString(), JavaWriter.WRITE,
                            //"viewHolder.findViewById(", Integer.toString(element.getAnnotation(FindView.class).value()), JavaWriter.PARAM_END);
                            ROUTER_CLASS_NAME + ".findView(viewHolder," + element.getAnnotation(FindView.class).value() + ");");
                }
                java.methodEnd();
                java.classEnd();
                finderMap.put(typeElement.getQualifiedName().toString(), binderFllName);
                java.close();
            } catch (Exception ex) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, ex.toString());
            }
        }
        if (builderFile == null) {
            try {
                JavaFileObject f = processingEnv.getFiler().createSourceFile(ROUTER_FULL_NAME);
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "My Processor java file " + f.toUri().toString());
                builderFile = f.toUri().toString();
                JavaWriter java = new JavaWriter(f.openWriter());
                java.writePackage(ROUTER_PACKAGE_NAME);

                java.writeImport(IBinderRouter.class.getName());
                java.writeImport(IViewFinder.class.getName());

                java.writeImport("android.app.Activity");
                java.writeImport("android.view.View");

                java.classBegin(Accesible.PUBLIC, ROUTER_CLASS_NAME, SuperClass.IMPLEMENT, IBinderRouter.class.getSimpleName());
                //IViewFinder getViewFinder(Object target);
                java.methodBegin(Accesible.PUBLIC, "IViewFinder", "getViewFinder", "Object target");

                Iterator<Map.Entry<String, String>> binderMapIterator = finderMap.entrySet().iterator();
                Map.Entry<String, String> bindEntry;
                while (binderMapIterator.hasNext()) {
                    bindEntry = binderMapIterator.next();
                    String targetName = bindEntry.getKey();
                    String finderName = bindEntry.getValue();
                /*
                 * if(target.getClass().getSimpleName().equals(targetName)){
                 *      return new finderName();
                 * }
                 */
                    java.writeNomal("if(target instanceof " + targetName + "){");
                    java.writeNomal("\treturn new " + finderName + "();");
                    java.writeNomal("}");
                }
                java.writeNomal("return null;");
                java.methodEnd();
                java.methodBegin(Accesible.PUBLIC, true, false, "<VIEW extends View>VIEW", "findView", "Object parent, int id");
                java.writeNomal("if(parent instanceof Activity) return (VIEW)((Activity)parent).findViewById(id);");
                java.writeNomal("if(parent instanceof View) return (VIEW)((View)parent).findViewById(id);");
                java.writeNomal("return null;");
                java.methodEnd();
                java.classEnd();
                java.close();
            } catch (Exception ex) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, ex.toString());
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, Arrays.toString(ex.getStackTrace()));
            }
        }
        return false;
    }

}
