import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.Styler;

public class PieChart {

    public static org.knowm.xchart.PieChart createPieChart(String chartTitle, String xAxis, List<Patient> patients) {
        Map<String, Integer> dataMap = new HashMap<>();
        for (Patient patient : patients) {
            String key = "";
            if ("Gender".equals(xAxis)) {
                key = patient.getGender();
            } else if ("Age".equals(xAxis)) {
                key = String.valueOf(patient.getAge());
            } else if ("Symptoms".equals(xAxis)) {
                key = patient.getSymptoms();
            } else if ("Placebo".equals(xAxis)) {
                key = String.valueOf(patient.isTakingPlacebo());
            } else if ("Covid Negative".equals(xAxis)) {
                key = String.valueOf(patient.isCovidNegative());
            } else if ("Observations".equals(xAxis)) {
                key = patient.getObservations();
            }
            dataMap.put(key, dataMap.getOrDefault(key, 0) + 1);
        }

        // Create and configure the pie chart
        org.knowm.xchart.PieChart chart = new PieChartBuilder().width(800).height(600).title(chartTitle).build();
        Styler styler = chart.getStyler();
        styler.setLegendVisible(true);
        styler.setLegendPosition(Styler.LegendPosition.InsideNW);
        styler.setChartTitleFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        styler.setLegendFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 10));
        styler.setLegendPadding(1);

        // Add data to the pie chart
        dataMap.forEach((label, value) -> chart.addSeries(label, value));

        return chart;
    }

    // Function to create a pie chart for symptoms with legend at top-right
    public static org.knowm.xchart.PieChart createSymptomsPieChart(String chartTitle, List<Patient> patients) {
        Map<String, Integer> dataMap = new HashMap<>();
        for (Patient patient : patients) {
            String key = patient.getSymptoms();
            dataMap.put(key, dataMap.getOrDefault(key, 0) + 1);
        }

        // Create and configure the pie chart for symptoms
        org.knowm.xchart.PieChart chart = new PieChartBuilder().width(800).height(600).title(chartTitle).build();
        Styler styler = chart.getStyler();
        styler.setLegendVisible(true);
        styler.setLegendPosition(Styler.LegendPosition.OutsideE);
        styler.setLegendLayout(Styler.LegendLayout.Vertical);
        styler.setChartTitleFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 10));
        styler.setLegendFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 10));
        styler.setLegendPadding(1);

        // Add data to the pie chart for symptoms
        dataMap.forEach((label, value) -> chart.addSeries(label, value));

        return chart;
    }
}
