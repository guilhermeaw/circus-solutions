package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import db.managers.ShowManager;

import java.util.List;

import entities.Show;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public class GraphicsController implements Initializable {

    @FXML
    private BarChart<?, ?> showByPriceChart;

    @FXML
    private BarChart<?, ?> showByQuantityChart;

    private List<Show> shows;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        composeShowByPriceChart();
        composeShowByQuantityChart();
    }

    private void composeShowByPriceChart() {
        shows = ShowManager.getInstance().getAll();

        XYChart.Series series = new XYChart.Series<String, Integer>();
        for ( Show show : shows )
        {
            series.getData().add(new XYChart.Data(((Show) shows).toString(), 5000));
            showByPriceChart.getData().addAll(series);
        }

        // XYChart.Series series = new XYChart.Series<String, Integer>();
        // series.getData().add(new XYChart.Data(shows.getCity().toString(), 5000));
        // series.getData().add(new XYChart.Data("Lajeado2 - 05/11/2022", 7000));
        // series.getData().add(new XYChart.Data("Lajeado3 - 05/12/2023", 1000));
        // showByPriceChart.getData().addAll(series);
    }

    private void composeShowByQuantityChart() {
        XYChart.Series series = new XYChart.Series<String, Integer>();
        series.getData().add(new XYChart.Data("Lajeado1 - 05/10/2021", 5000));
        series.getData().add(new XYChart.Data("Lajeado2 - 05/11/2022", 7000));
        series.getData().add(new XYChart.Data("Lajeado3 - 05/12/2023", 1000));
        showByQuantityChart.getData().addAll(series);
    }
}
