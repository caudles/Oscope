/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oscope;

import java.lang.Math;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 *
 * @author sean
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button SineButton;
    @FXML
    private LineChart<?, ?> Display;
    @FXML
    private Button SquareButton;
    @FXML
    private Button SawtoothButton;
    @FXML
    private Button TriangleButton;
    @FXML
    private Slider FrequencySlider;
    @FXML
    private Slider AmplitudeSlider;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private ComboBox<String> MultiplierBox;
    @FXML
    private Label freqValue;
    
    private double frequency = 1.0;
    private double multi = 1.0;
    private double amplitude = 1.0;
    private int functionType;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FrequencySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            frequency = newValue.intValue();
            freqValue.setText(Double.toString(frequency));
            plotFunction();
        });
        
        AmplitudeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            amplitude = newValue.intValue();
            plotFunction();
        });
       
        MultiplierBox.getItems().addAll("Hz", "kHz", "MHz");
        MultiplierBox.setValue("Hz");
        MultiplierBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            MultiplierBox.setValue(newValue);
            if (newValue=="Hz"){
                multi = 1.0;
            }else if(newValue=="kHz"){
                multi = 1000.0;
            }else if(newValue=="MHz"){
                multi = 1000000.0;
            }
        });
        
    }  
    
    private void plotFunction(){
        Display.getData().clear();
        XYChart.Series series = new XYChart.Series();
        //yAxis.setLowerBound(-amplitude);
        //yAxis.setUpperBound(amplitude);
        //xAxis.setLowerBound(-0.5/(frequency));
        //xAxis.setUpperBound(0.5/(frequency));
        double xMin = xAxis.getLowerBound();
        double xMax = xAxis.getUpperBound();
        double inc = (xMax-xMin)/1000.0;
        
        switch(functionType){
            case 1: for(double t = xMin; t < xMax; t += inc){
                        series.getData().add(new XYChart.Data(t, amplitude*Math.sin(2*Math.PI*frequency*t)));
                    }
                    break;
            case 2: for(double t = xMin; t < xMax; t += inc){
                        series.getData().add(new XYChart.Data(t, Math.pow(-amplitude, Math.floor(frequency*multi*t))));
                    }
                    break;
            case 3: for(double t = xMin; t < xMax; t += inc){
                        //insert sawtooth function
                    }
                    break;
            case 4: for(double t = xMin; t < xMax; t += inc){
                        //insert triangle function
                    }
                    break;
        }
        Display.getData().add(series);
    }
    

    @FXML
    private void handleSineButtonAction(ActionEvent event) {
        functionType = 1;
        plotFunction();
//        Display.getData().clear();
//        XYChart.Series series = new XYChart.Series();
//        double xMin = xAxis.getLowerBound();
//        double xMax = xAxis.getUpperBound();
//        double inc = (xMax-xMin)/1000.0;
//        for(double t = xMin; t < xMax; t += inc){
//            series.getData().add(new XYChart.Data(t, Math.sin(2*Math.PI*frequency*multi*t)));
//        }
//        Display.getData().add(series);
    }

    @FXML
    private void handleSquareButtonAction(ActionEvent event) {
        functionType = 2;
        plotFunction();
    }

    @FXML
    private void handleSawtoothButtonAction(ActionEvent event) {
        functionType = 3;
        plotFunction();
    }

    @FXML
    private void handleTriangleButtonAction(ActionEvent event) {
        functionType = 4;
        plotFunction();
    }
    
}
