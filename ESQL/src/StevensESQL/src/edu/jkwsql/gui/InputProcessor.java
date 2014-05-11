package edu.jkwsql.gui;

import edu.jkwsql.dataobjects.DatabaseSettingsDO;
import edu.jkwsql.dataobjects.PhiOperandsDO;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dimitri on 4/18/14.
 * Class to process the GUI inputs:
 * Check if fields are not empty
 * Check for valid number inputs
 */
public class InputProcessor {
    private DatabaseSettingsDO databaseSettings;
    private PhiOperandsDO phiOperands;

    private InputGUI inputGUI;

    public InputProcessor(InputGUI inputGUI) {
        this.inputGUI = inputGUI;
    }

    public DatabaseSettingsDO getDatabaseSettings() {
        String databaseName = inputGUI.getDatabaseNameField().getText();
        String tableName = inputGUI.getTableNameField().getText();
        String host = inputGUI.getHostField().getText();
        int portNumber = getTextFieldNumericValue(inputGUI.getPortNumberField());
        String username = inputGUI.getUsernameField().getText();
        String password = new String(inputGUI.getPasswordField().getPassword());

        databaseSettings = new DatabaseSettingsDO(databaseName, tableName, host, portNumber, username, password);
        return databaseSettings;
    }

    public PhiOperandsDO getPhiOperands() {
        List<String> S = new ArrayList<String>(Arrays.asList(inputGUI.getSField().getText().replaceAll(" ", "").split(",")));
        int n = getTextFieldNumericValue(inputGUI.getnField());
        List<String> V = new ArrayList<String>(Arrays.asList(inputGUI.getVField().getText().replaceAll(" ", "").split(",")));
        List<String> F = new ArrayList<String>(Arrays.asList(inputGUI.getFField().getText().replaceAll(" ", "").split(",")));
        List<String> sigma = new ArrayList<String>(Arrays.asList(inputGUI.getSigmaField().getText().replaceAll(" ", "").split(",")));
        boolean isExecuteQuery = inputGUI.getExecuteQueryCheckBox().isSelected();
        boolean isGenerateOutputFile = inputGUI.getGenerateOutputFileCheckBox().isSelected();

        phiOperands = new PhiOperandsDO(S, n, V, F, sigma, isExecuteQuery, isGenerateOutputFile);
        return phiOperands;
    }

    //    Database connection settings
    public boolean isInputsValid() {
        boolean status = true;
        try {
            // Database settings
            isFieldEmpty(inputGUI.getDatabaseNameField(), "Database Name");
            isFieldEmpty(inputGUI.getTableNameField(), "Table Name");
            isFieldEmpty(inputGUI.getTableNameField(), "Host");
            isFieldEmpty(inputGUI.getPortNumberField(), "Port Number");
            isFieldValidNumber(inputGUI.getPortNumberField(), "Port Number");
            isFieldEmpty(inputGUI.getUsernameField(), "Username");
            isFieldEmpty(inputGUI.getPasswordField(), "Password");

            // Phi operands
            isFieldEmpty(inputGUI.getSField(), "Select Attributes(S)");
            isFieldEmpty(inputGUI.getnField(), "Number of Grouping Variables(n)");
            isFieldValidNumber(inputGUI.getnField(), "Number of Grouping Variables(n)");
            isFieldEmpty(inputGUI.getVField(), "Grouping Attributes(V)");
            isFieldEmpty(inputGUI.getFField(), "F-Vect(F)");
            isFieldEmpty(inputGUI.getSigmaField(), "Select Condition Vector(σ)");
        } catch (FieldIsEmptyException e) {
            this.writeToOutput(e.getMessage(), Color.RED);
            status = false;
        } catch (FieldIsInvalidException e) {
            this.writeToOutput(e.getMessage(), Color.RED);
            status = false;
        }
        return status;
    }

    private void isFieldEmpty(JTextField textField, String fieldName) throws FieldIsEmptyException {
        if (textField.getText().isEmpty())
            throw new FieldIsEmptyException(fieldName);
    }

    private void isFieldValidArray(JTextField textField, String fieldName) throws FieldIsInvalidException {
//        TODO check for valid list
    }

    private void isFieldValidNumber(JTextField textField, String fieldName) throws FieldIsInvalidException {
        if (getTextFieldNumericValue(textField) == -1)
            throw new FieldIsInvalidException(fieldName);
    }

    private int getTextFieldNumericValue(JTextField textField) {
        int n = -1;
        try {
            n = Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
        }

        return n;
    }

    public void writeToOutput(String msg, Color c) {
        JTextPane tp = inputGUI.getOutputTextPane();
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}

class FieldIsEmptyException extends Exception {
    //Parameterless Constructor
    public FieldIsEmptyException() {
        super("Field cannot be empty\n");
    }

    //Constructor that accepts a message
    public FieldIsEmptyException(String inputField) {
        super(inputField + " cannot be empty\n");
    }
}

class FieldIsInvalidException extends Exception {
    //Parameterless Constructor
    public FieldIsInvalidException() {
        super("Field must be a valid format\n");
    }

    //Constructor that accepts a message
    public FieldIsInvalidException(String inputField) {
        super(inputField + " must be a valid input\n");
    }
}


