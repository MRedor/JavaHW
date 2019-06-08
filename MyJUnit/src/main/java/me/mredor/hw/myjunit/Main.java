package me.mredor.hw.myjunit;

/** Console application for MyJUnit */
public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Expected one parameter -- name of class to test");
            return;
        }

        Class clazz;

        try {
            clazz = Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            System.out.println("Wrong class " + args[0]);
            //System.out.println(args[0]);
            return;
        }

        var invoker = new MyJUnitInvoker(clazz);
        try {
            invoker.run();
        } catch (MultipleAnnotationException e) {
            System.out.println(e.getMessage());
        }
    }
}
