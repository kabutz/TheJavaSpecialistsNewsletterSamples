package eu.javaspecialists.tjsn.issue167;

import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.lang.model.type.*;
import javax.lang.model.util.*;
import javax.tools.*;
import java.util.*;

@SupportedAnnotationTypes(
        "eu.javaspecialists.tools.apt.NoArgsConstructor")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class NoArgsConstructorProcessor extends AbstractProcessor {
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment env) {
        for (TypeElement type : annotations) {
            processNoArgsConstructorClasses(env, type);
        }
        return true;
    }

    private void processNoArgsConstructorClasses(
            RoundEnvironment env, TypeElement type) {
        for (Element element : env.getElementsAnnotatedWith(type)) {
            processClass(element);
        }
    }

    private void processClass(Element element) {
        if (!doesClassContainNoArgsConstructor(element)) {
            processingEnv.getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    "Class " + element + " needs a No-Args Constructor");
        }
    }

    private boolean doesClassContainNoArgsConstructor(Element el) {
        for (Element subelement : el.getEnclosedElements()) {
            if (subelement.getKind() == ElementKind.CONSTRUCTOR &&
                    subelement.getModifiers().contains(Modifier.PUBLIC)) {
                TypeMirror mirror = subelement.asType();
                if (mirror.accept(noArgsVisitor, null)) return true;
            }
        }
        return false;
    }

    private static final TypeVisitor<Boolean, Void> noArgsVisitor =
            new SimpleTypeVisitor6<Boolean, Void>() {
                public Boolean visitExecutable(ExecutableType t, Void v) {
                    return t.getParameterTypes().isEmpty();
                }
            };
}