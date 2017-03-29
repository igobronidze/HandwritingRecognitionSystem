package ge.edu.tsu.hrs.control_panel.service.startup;

import ge.edu.tsu.hrs.control_panel.server.startup.StartUpProcessor;

public class StartUpServiceImpl implements StartUpService {

    @Override
    public void process() {
        StartUpProcessor.process();
    }
}
