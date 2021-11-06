package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public class GraphicsController implements Initializable {

    @FXML
    private BarChart<?, ?> showByPriceChart;

    @FXML
    private BarChart<?, ?> showByQuantityChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        composeShowByPriceChart();
        composeShowByQuantityChart();
    }

    private void composeShowByPriceChart(){
        XYChart.Series series = new XYChart.Series<String, Integer>();
        series.getData().add(new XYChart.Data("Lajeado1 - 05/10/2021", 5000));
        series.getData().add(new XYChart.Data("Lajeado2 - 05/11/2022", 7000));
        series.getData().add(new XYChart.Data("Lajeado3 - 05/12/2023", 1000));
        showByPriceChart.getData().addAll(series);
    }

    private void composeShowByQuantityChart(){
        
    }

}
