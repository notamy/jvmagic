package date.willnot.amy.jvmagic;

import sun.management.VMManagement;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author amy
 * @since 12/6/16.
 */
@SuppressWarnings("unused")
public class JVMagic {
    public static void main(final String[] args) {
        try {
            final File libFile = NativeUtils.loadLibraryFromJar("/jvmagic-native-0.0.1.so");
            System.out.println("Running with PID: " + getPid());
            //new JVMagic().jniCall();
            /*final List<Class<?>> classesFromJar = ClassEnumerator.getClassesFromJar(
                    new File(System.getProperty("java.home") + File.separator + "../lib/tools.jar").getAbsoluteFile(),
                    JVMagic.class.getClassLoader());*/
            try {
                final Class<?> virtualMachineClass = Class.forName("com.sun.tools.attach.VirtualMachine");
                final Method attachMethod = virtualMachineClass.getDeclaredMethod("attach", String.class);
                final Object vmInstance = attachMethod.invoke(null, getPid() + "");
                final Method loadAgentPathMethod = virtualMachineClass.getDeclaredMethod("loadAgentPath", String.class);
                loadAgentPathMethod.invoke(vmInstance, libFile.getAbsoluteFile().getAbsolutePath());
                virtualMachineClass.getDeclaredMethod("detach").invoke(vmInstance);
            } catch(final ClassNotFoundException e) {
                System.err.println("Couldn't load tools.jar!");
                System.exit(1);
            }
        } catch(final Exception e) {
            e.printStackTrace();
        }
    }
    
    private static int getPid() throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        final RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        final Field jvm = runtime.getClass().getDeclaredField("jvm");
        jvm.setAccessible(true);
        final VMManagement mgmt = (VMManagement) jvm.get(runtime);
        final Method pid_method = mgmt.getClass().getDeclaredMethod("getProcessId");
        pid_method.setAccessible(true);
        
        return (int) (Integer) pid_method.invoke(mgmt);
    }
    
    /*
    public native void jniCall();
    */
}
