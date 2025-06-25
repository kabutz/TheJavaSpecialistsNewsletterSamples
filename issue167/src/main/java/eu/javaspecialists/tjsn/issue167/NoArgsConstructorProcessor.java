/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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