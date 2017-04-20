package ge.edu.tsu.hrs.control_panel.console.fx.ui.component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

public class TCHNumberTextField extends TCHTextField {

    private final NumberFormat nf;
    private ObjectProperty<BigDecimal> number = new SimpleObjectProperty<>();

    public final BigDecimal getNumber() {
        return number.get();
    }

    public final void setNumber(BigDecimal value) {
        number.set(value);
    }

    public ObjectProperty<BigDecimal> numberProperty() {
        return number;
    }

    public TCHNumberTextField(TCHComponentSize size) {
        this(BigDecimal.ZERO, size);
    }

    public TCHNumberTextField(BigDecimal value, TCHComponentSize size) {
        this(value, NumberFormat.getInstance(), size);
        initHandlers();
    }

    public TCHNumberTextField(BigDecimal value, NumberFormat nf, TCHComponentSize size) {
        super(size);
        this.nf = nf;
        initHandlers();
        setNumber(value);
    }

    private void initHandlers() {
        setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                parseAndFormatInput();
            }
        });
        focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue.booleanValue()) {
                    parseAndFormatInput();
                }
            }
        });
        numberProperty().addListener(new ChangeListener<BigDecimal>() {

            @Override
            public void changed(ObservableValue<? extends BigDecimal> obserable, BigDecimal oldValue, BigDecimal newValue) {
                setText(nf.format(newValue));
            }
        });
    }

    private void parseAndFormatInput() {
        try {
            String input = getText();
            if (input == null || input.length() == 0) {
                return;
            }
            Number parsedNumber = nf.parse(input);
            BigDecimal newValue = new BigDecimal(parsedNumber.toString());
            setNumber(newValue);
            selectAll();
        } catch (ParseException ex) {
            setText(nf.format(number.get()));
        }
    }
}