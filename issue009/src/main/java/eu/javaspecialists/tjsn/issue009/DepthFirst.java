package eu.javaspecialists.tjsn.issue009;

import java.lang.reflect.*;

public class DepthFirst {
    public static class Top {
        private Method getPolymorphicMethod(Object param) {
            try {
                Class cl = getClass();  // the bottom-most class
                // we start at the bottom and work our way up
                Class[] paramTypes = {param.getClass()};
                while (!cl.equals(Top.class)) {
                    try {
                        // this way we find the actual method
                        return cl.getDeclaredMethod("f", paramTypes);
                    } catch (NoSuchMethodException ex) {}
                    cl = cl.getSuperclass();
                }
                return null;
            } catch (RuntimeException ex) {throw ex;} catch (Exception ex) {return null;}
        }

        public void f(Object object) {
            Method downPolymorphic = getPolymorphicMethod(object);
            if (downPolymorphic == null) {
                System.out.println("Top.f(Object)");
            } else {
                try {
                    downPolymorphic.invoke(this, new Object[]{object});
                } catch (RuntimeException ex) {throw ex;} catch (Exception ex) {
                    throw new RuntimeException(ex.toString());
                }
            }
        }
    }

    public static class Middle extends Top {
        public void f(String s) {
            System.out.println("Middle.f(String)");
        }
    }

    public static class Bottom extends Middle {
        public void f(Integer i) {
            System.out.println("Bottom.f(Integer)");
        }
    }

    public static class RockBottom extends Bottom {
        public void f(String s) {
            System.out.println("RockBottom.f(String)");
        }
    }

    public static void main(String[] args) {
        Top top = new RockBottom();
        top.f(new java.util.Vector());
        top.f("hello");
        top.f(new Integer(42));
        top = new Bottom();
        top.f(new java.util.Vector());
        top.f("hello");
        top.f(new Integer(42));
    }
}
