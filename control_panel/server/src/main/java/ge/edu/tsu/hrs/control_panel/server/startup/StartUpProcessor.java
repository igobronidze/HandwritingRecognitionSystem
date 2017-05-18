package ge.edu.tsu.hrs.control_panel.server.startup;

import ge.edu.tsu.hrs.control_panel.server.caching.CachedProductionNetwork;
import ge.edu.tsu.hrs.control_panel.server.caching.CachedWords;
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
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    @StartUp
    private void setFailedNetworkInfos() {
        networkInfoDAO.setFailedNetworkInfos();
    }

    @StartUp
    private void loadProductionNetwork() {
        Thread thread = new Thread(null, CachedProductionNetwork::loadData);
        thread.start();
    }

    @StartUp
    private void loadWords() {
        Thread thread = new Thread(null, CachedWords::loadData);
        thread.start();
    }
}
