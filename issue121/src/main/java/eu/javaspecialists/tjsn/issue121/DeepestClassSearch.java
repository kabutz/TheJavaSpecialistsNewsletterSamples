package eu.javaspecialists.tjsn.issue121;


import java.io.*;
import java.util.*;
import java.util.jar.*;

public class DeepestClassSearch {
    public static void main(String... args) throws Exception {
        MakeClasses.main("30");
        String java_class_path = System.getProperty(
                "java.class.path");
        System.out.println("java_class_path = " + java_class_path);
        String separator = System.getProperty("path.separator");
        String[] jars = java_class_path.split(separator);

        Collection<ClassInfo> result = new TreeSet<ClassInfo>();
        for (String jarFileName : jars) {
            if (jarFileName.endsWith(".jar") ||
                    jarFileName.endsWith(".zip")) {
                File jarFile = new File(jarFileName);
                if (jarFile.exists()) {
                    System.out.println("Scanning: " + jarFile);
                    extractClasses(jarFile, result);
                }
            } else {
                // might be directory
                File directory = new File(jarFileName);
                if (directory.exists() && directory.isDirectory()) {
                    System.out.println("Scanning: " + directory);
                    extractClassesFromDirectory(directory, result);
                }

            }
        }
        for (ClassInfo info : result) {
            System.out.println(info);
        }
    }

    private static void extractClasses(File file,
                                       Collection<ClassInfo> result)
            throws Exception {
        Enumeration<JarEntry> en = new JarFile(file).entries();
        while (en.hasMoreElements()) {
            JarEntry je = en.nextElement();
            String className = je.getName();
            if (className.endsWith(".class")) {
                className = className.replaceAll("\\.class", "");
                className = className.replaceAll("/", ".");
                try {
                    Class clazz = Class.forName(className, false,
                            ClassLoader.getSystemClassLoader());
                    int depth = checkDepth(clazz);
                    result.add(new ClassInfo(className, depth));
                } catch (Throwable er) {
                    System.err.println(className + ": " + er);
                }
            }
        }
    }

    private static void extractClassesFromDirectory(File rootDir,
                                                   Collection<ClassInfo> result) throws Exception {
        if (!rootDir.isDirectory()) {
            throw new IllegalArgumentException("Not a directory: " + rootDir);
        }

        List<File> classFiles = new ArrayList<File>();
        findClassFiles(rootDir, classFiles);

        final int basePathLength = rootDir.getAbsolutePath().length() + 1;

        for (File classFile : classFiles) {
            String fullPath = classFile.getAbsolutePath();
            String className = fullPath
                    .substring(basePathLength, fullPath.length() - 6) // remove ".class"
                    .replace(File.separatorChar, '.');

            try {
                Class<?> clazz = Class.forName(className, false,
                        ClassLoader.getSystemClassLoader());
                int depth = checkDepth(clazz);
                result.add(new ClassInfo(className, depth));
            } catch (Throwable er) {
                System.err.println("Failed to load class " + className + ": " + er);
            }
        }
    }

    private static void findClassFiles(File dir, List<File> classFiles) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                findClassFiles(file, classFiles);
            } else if (file.getName().endsWith(".class")) {
                classFiles.add(file);
            }
        }
    }

    public static int checkDepth(Class clazz) {
        int level = 0;
        while (clazz != null) {
            level++;
            clazz = clazz.getSuperclass();
        }
        return level;
    }
}
