package ge.edu.tsu.hrs.control_panel.model.exception;

public class ControlPanelException extends Exception {

    private Exception ex;

    public ControlPanelException() {
    }

    public ControlPanelException(String msg) {
        super(msg);
    }

    public ControlPanelException(Exception ex) {
        super(ex);
    }
}
