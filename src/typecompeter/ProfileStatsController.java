/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import java.util.Calendar;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 *
 * @author moss_899291
 */
public class ProfileStatsController {
    public Profile prf;
    @FXML private LineChart<Integer,Integer> lineChart_Profile;
    @FXML private TableView tableView_topScores;
    @FXML private Label label_averageWPM;
    @FXML private Label label_wpmHigh;
    @FXML private Label label_racesWon;
    @FXML private Label label_racesPlayed;
    @FXML private Label label_user;
    
    @FXML
    public void initialize() {
        
    }
    
    public void setProfile(Profile p) {
        this.prf = p;
        loadData();
    }

    private void loadData() {
        label_user.setText(prf.getName());
        label_racesPlayed.setText("Races Played: " + prf.getPlayed());
        label_racesWon.setText("Races Won: " + prf.getWins());
        Calendar mCalendar = Calendar.getInstance();    
        String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        lineChart_Profile.getYAxis().setLabel("WPM");
        lineChart_Profile.getXAxis().setLabel("Day of " + month + " " + mCalendar.get(Calendar.YEAR));
        if (prf.getResults().size() > 0) {
            label_wpmHigh.setText("Highest WPM: " + prf.getTop().getWpm());
            label_averageWPM.setText("Average WPM: " + prf.getAverage());
            XYChart.Series<Integer,Integer> chart = new XYChart.Series();
            for (int i : prf.getDailyAverages().keySet()) {
                chart.getData().add(new XYChart.Data<Integer,Integer>(i, prf.getDailyAverages().get(i)));
            }
            lineChart_Profile.setLegendVisible(false);
            lineChart_Profile.getData().addAll(chart);
        }
        
    }
}
