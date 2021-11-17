package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import db.managers.ShowChartManager;
import db.managers.ShowManager;

import java.util.List;

import entities.Show;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import wrappers.ShowTicketWrapper;

public class GraphicsController implements Initializable {

    @FXML
    private BarChart<?, ?> showByPriceChart;

    @FXML
    private BarChart<String, Long> showByQuantityChart;

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
            series.getData().add(new XYChart.Data(((Show) show).toString(), 5000));
            showByPriceChart.getData().addAll(series);
        }

        // XYChart.Series series = new XYChart.Series<String, Integer>();
        // series.getData().add(new XYChart.Data(shows.getCity().toString(), 5000));
        // series.getData().add(new XYChart.Data("Lajeado2 - 05/11/2022", 7000));
        // series.getData().add(new XYChart.Data("Lajeado3 - 05/12/2023", 1000));
        // showByPriceChart.getData().addAll(series);
    }

    private void composeShowByQuantityChart() {
        XYChart.Series<String, Long> series = new XYChart.Series<String, Long>();
        List<ShowTicketWrapper> showTicketWrappers = ShowChartManager.getInstance().getShowsPerTicketSellQuantity();
        
        for (ShowTicketWrapper wrapper : showTicketWrappers) {
            series.getData().add(new XYChart.Data<String, Long>(wrapper.getShow().toString(), wrapper.getQuantity()));
        }

        showByQuantityChart.getData().addAll(series);
    }
}
