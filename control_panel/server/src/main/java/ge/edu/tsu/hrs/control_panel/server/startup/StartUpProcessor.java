package ge.edu.tsu.hrs.control_panel.server.startup;

import ge.edu.tsu.hrs.control_panel.server.dao.networkinfo.NetworkInfoDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.networkinfo.NetworkInfoDAOImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StartUpProcessor {

    private NetworkInfoDAO networkInfoDAO = new NetworkInfoDAOImpl();

    public static void process() {
        Class clazz = StartUpProcessor.class;
        for (Method method : clazz.getDeclaredMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (annotation instanceof StartUp) {
                    try {
                        method.invoke(new StartUpProcessor());
                    } catch (IllegalAccessException | InvocationTargetException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }
    }

    @StartUp
    private void setFailedNetworkInfos() {
        networkInfoDAO.setFailedNetworkInfos();
    }
}
