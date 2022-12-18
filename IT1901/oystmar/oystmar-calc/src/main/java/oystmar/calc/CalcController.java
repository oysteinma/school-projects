package oystmar.calc;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;

public class CalcController {

    private Calc calc;

    public CalcController() {
        calc = new Calc(0.0, 0.0, 0.0);
    }

    public Calc getCalc() {
        return calc;
    }

    public void setCalc(Calc calc) {
        this.calc = calc;
        updateOperandsView();
    }

    @FXML
    private ListView<Double> operandsView;

    @FXML
    private Label operandView;

    @FXML
    void initialize() {
        updateOperandsView();
    }

    private void updateOperandsView() {
        List<Double> operands = operandsView.getItems();
        operands.clear();
        int elementCount = Math.min(calc.getOperandCount(), 3);
        for (int i = 0; i < elementCount; i++) {
            operands.add(calc.peekOperand(elementCount - i - 1));
        }
    }

    private String getOperandString() {
        return operandView.getText();
    }

    private boolean hasOperand() {
        return ! getOperandString().isBlank();
    }

    private double getOperand() {
        return Double.valueOf(operandView.getText());
    }
    
    private void setOperand(String operandString) {
        operandView.setText(operandString);
    }

    @FXML
    void handleEnter() {
        if (hasOperand()) {
            calc.pushOperand(getOperand());
        } else {
            calc.dup();
        }
        setOperand("");
        updateOperandsView();
    }

    private void appendToOperand(String s) {
        setOperand(getOperandString() + s);
    }

    @FXML
    void handleDigit(ActionEvent ae) {
        if (ae.getSource() instanceof Labeled l) {
            appendToOperand(l.getText());
        }
    }

    @FXML
    void handlePoint() {
        var operandString = getOperandString();
        if (operandString.contains(".")) {
            setOperand(operandString.substring(0, operandString.indexOf(".") + 1));
        } else {
            appendToOperand(".");
        }
    }

    @FXML
    void handleClear() {
        setOperand("");
        updateOperandsView();
    }

    @FXML
    void handleSwap() {
        calc.swap();
        updateOperandsView();
        
    }

    private void performOperation(UnaryOperator<Double> op) {
        calc.performOperation(op);
        handleClear();
    }

    private void performOperation(boolean swap, BinaryOperator<Double> op) {
        if (hasOperand()) {
            calc.pushOperand(getOperand());
        }
        if (swap) {
            handleSwap();
        }

        calc.performOperation(op);
        handleClear();
    }

    @FXML
    void handleOpAdd() {
        performOperation(false, (x1, x2) -> x1 + x2);
    }

    @FXML
    void handleOpSub() {
        performOperation(false, (x1, x2) -> x1 - x2);
    }

    @FXML
    void handleOpMult() {
        performOperation(false, (x1, x2) -> x1 * x2);
    }

    @FXML
    void handleOpDiv() {
        var op1 = getOperand();
        var op2 = calc.peekOperand();
        boolean bool = false;
        if (op1 > op2) {bool = true;}
        performOperation(bool, (x1, x2) -> x1 / x2);    
    }

    @FXML
    void handleOpSqt() {
        performOperation((x) -> Math.sqrt(x));
    }

    @FXML
    void handleOpPi() {
        performOperation((x) -> Math.PI);
    }

    
}
