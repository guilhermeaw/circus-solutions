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
    private BarChart<String, Double> showByPriceChart;

    @FXML
    private BarChart<String, Long> showByQuantityChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        composeShowByPriceChart();
        composeShowByQuantityChart();
    }

    private void composeShowByPriceChart() {
        XYChart.Series<String, Double> series = new XYChart.Series<String, Double>();
        List<ShowTicketWrapper> showTicketWrappers = ShowChartManager.getInstance().getShowsPerTotalTicketValue();
        
        for (ShowTicketWrapper wrapper : showTicketWrappers) {
            series.getData().add(new XYChart.Data<String, Double>(wrapper.getShow().toString(), wrapper.getTotalTicketValue()));
        }

        showByPriceChart.getData().addAll(series);
        showByPriceChart.getYAxis().setLabel("Valor arrecadado (R$)");
        showByPriceChart.getXAxis().setLabel("Espetáculos");
    }

    private void composeShowByQuantityChart() {
        XYChart.Series<String, Long> series = new XYChart.Series<String, Long>();
        List<ShowTicketWrapper> showTicketWrappers = ShowChartManager.getInstance().getShowsPerTicketSellQuantity();
        
        for (ShowTicketWrapper wrapper : showTicketWrappers) {
            series.getData().add(new XYChart.Data<String, Long>(wrapper.getShow().toString(), wrapper.getQuantity()));
        }

        showByQuantityChart.getData().addAll(series);
        showByQuantityChart.getYAxis().setLabel("Quantidade de ingressos vendidos");
        showByQuantityChart.getXAxis().setLabel("Espetáculos");
    }
}
