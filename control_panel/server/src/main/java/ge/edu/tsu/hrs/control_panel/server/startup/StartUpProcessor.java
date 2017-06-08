package ge.edu.tsu.hrs.control_panel.server.startup;

import ge.edu.tsu.hrs.control_panel.model.sysparam.Parameter;
import ge.edu.tsu.hrs.control_panel.server.caching.CachedProductionNetwork;
import ge.edu.tsu.hrs.control_panel.server.caching.CachedWords;
import ge.edu.tsu.hrs.control_panel.server.dao.networkinfo.NetworkInfoDAO;
import ge.edu.tsu.hrs.control_panel.server.dao.networkinfo.NetworkInfoDAOImpl;
import ge.edu.tsu.hrs.control_panel.server.processor.systemparameter.SystemParameterProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StartUpProcessor {

    private NetworkInfoDAO networkInfoDAO = new NetworkInfoDAOImpl();

    private SystemParameterProcessor systemParameterProcessor = new SystemParameterProcessor();

    private Parameter cacheWords = new Parameter("cachWords", "true");

    private Parameter loadProductionNetwork = new Parameter("loadProductionNetwork", "true");

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
        if (systemParameterProcessor.getBooleanParameterValue(loadProductionNetwork)) {
            Thread thread = new Thread(null, CachedProductionNetwork::loadData);
            thread.start();
        }
    }

    @StartUp
    private void loadWords() {
        if (systemParameterProcessor.getBooleanParameterValue(cacheWords)) {
            Thread thread = new Thread(null, CachedWords::loadData);
            thread.start();
        }
    }
}
