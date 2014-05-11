package edu.jkwsql;

import edu.jkwsql.database.DatabaseProcessor;
import edu.jkwsql.dataobjects.DatabaseSettingsDO;
import edu.jkwsql.dataobjects.PhiOperandsDO;
import edu.jkwsql.gui.InputGUI;
import edu.jkwsql.gui.InputProcessor;
import edu.jkwsql.json.JSONProcessor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Dimitri on 4/18/14.
 * Main class that controls all of the code execution
 */
public class StevensESQLMain {
    //    Class members that are used in different parts of the code
    InputGUI myGUI;
    InputProcessor inputProcessor;

    /*    Main code that will be responsible for calling all other helper methods and creating and manipulating classes
          Because everything will occur after the "Generate Code" button is clicked most of the functionality will be inside the action listener for that button
    */
    public StevensESQLMain(final InputGUI myGUI) {
        this.myGUI = myGUI;
//        Object to process the form inputs and create data objects from inputs
        this.inputProcessor = new InputProcessor(myGUI);
//        TODO add welcome message
        inputProcessor.writeToOutput("Welcome Message\n", Color.BLACK);

        // Main action listener for the "Generate Code" button
        myGUI.getQueryButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myGUI.getTabbedPane().setSelectedIndex(2);

                System.out.println("Button Clicked");

//                Check if the GUI input is valid, if not the InputProcessor will handle errors
                if (isFormValid()) { // TODO implement when valid input
                    DatabaseSettingsDO databaseSettings = getDatabaseSettings();        // Object holds the input DB settings
                    PhiOperandsDO phiOperands = getPhiOperands();                       // Object holds the input phi operands

//                    Save the phi operands to a JSON file
                    String filePath = "phi.json";
                    JSONProcessor.toJSON(filePath, phiOperands);

//                    Get Database connection
                    DatabaseProcessor databaseProcessor = new DatabaseProcessor(databaseSettings);
                    databaseProcessor.connect();

//                    Prompt that everything finished without errors
                    inputProcessor.writeToOutput("Query Processor Generated!\n", Color.GREEN);
                }
            }
        });

//        Add listeners to phi and db file buttons
        ButtonListener listener = new ButtonListener();
        myGUI.getDbJSONButton().addActionListener(listener);
        myGUI.getPhiJSONButton().addActionListener(listener);
    }

    //    Inner listener class for the file buttons
    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JSON Files", "json");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(new Component() {
                @Override
                public String getName() {
                    return super.getName();
                }
            });
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (e.getSource() == myGUI.getDbJSONButton()) {
//                    Read db settings and set the textfields
                    DatabaseSettingsDO jsonDBSettings = (DatabaseSettingsDO) JSONProcessor.fromJSON(chooser.getSelectedFile().getAbsolutePath(), new DatabaseSettingsDO());
                    setDefaultDBSettings(jsonDBSettings);
                } else if (e.getSource() == myGUI.getPhiJSONButton()) {
//                    Read phi settings and set the textfields
                    PhiOperandsDO jsonPhiSettings = (PhiOperandsDO) JSONProcessor.fromJSON(chooser.getSelectedFile().getAbsolutePath(), new PhiOperandsDO());
                    setDefaultPhiSettings(jsonPhiSettings);
                }
            }
        }
    }

    //    The main() will only create a new InputGUI instance and then call edu.jkwsql.StevensESQLMain constructor
    public static void main(String[] args) {
        InputGUI myGUI = new InputGUI();

        new StevensESQLMain(myGUI);
    }

    private void setDefaultDBSettings(DatabaseSettingsDO settings) {
        myGUI.getDatabaseNameField().setText(settings.getDatabaseName());
        myGUI.getTableNameField().setText(settings.getTableName());
        myGUI.getHostField().setText(settings.getHost());
        myGUI.getPortNumberField().setText(Integer.toString(settings.getPortNumber()));
        myGUI.getUsernameField().setText(settings.getUsername());
        myGUI.getPasswordField().setText(settings.getPassword());
    }

    private void setDefaultPhiSettings(PhiOperandsDO phi) {
        myGUI.getSField().setText(phi.getS().toString().replace("[", "").replace("]", "")); //remove the left and right bracket);
        myGUI.getnField().setText(Integer.toString(phi.getN()));
        myGUI.getVField().setText(phi.getV().toString().replace("[", "").replace("]", ""));
        myGUI.getFField().setText(phi.getF().toString().replace("[", "").replace("]", ""));
        myGUI.getSigmaField().setText(phi.getSigma().toString().replace("[", "").replace("]", ""));
        myGUI.getExecuteQueryCheckBox().setSelected(phi.isExecuteQuery());
        myGUI.getGenerateOutputFileCheckBox().setSelected(phi.isGenerateOutputFile());
    }

    private boolean isFormValid() {
        return inputProcessor.isInputsValid();
    }

    private DatabaseSettingsDO getDatabaseSettings() {
        return inputProcessor.getDatabaseSettings();
    }

    private PhiOperandsDO getPhiOperands() {
        return inputProcessor.getPhiOperands();
    }
}
