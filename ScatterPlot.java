import org.knowm.xchart.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ScatterPlot {
    String xAxis;
    String yAxis;
    String graphTitle;
    ArrayList<Patient> subjects;
    Map<String, Integer> data;

    public ScatterPlot(String xAxis, String yAxis, String title, ArrayList<Patient> medicineData,
            Map<String, Integer> finalData) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.graphTitle = title;
        this.subjects = medicineData;
        this.data = finalData;
    }

    public static CategoryChart plotLineGraph(String xAxis, String yAxis, String graphTitle,
            ArrayList<Patient> medicineData, Map<String, Integer> finalData) {
        // Separate Data
        List<String> xData = new ArrayList<>(); // Use String instead of Double for X-axis data
        List<Double> yData = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : finalData.entrySet()) {
            // Add X-axis data (categories) to xData
            xData.add(entry.getKey());
            // Add Y-axis data (patient values) to yData
            yData.add(Double.valueOf(entry.getValue()));
        }

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder()
                .width(1000)
                .height(600)
                .title(graphTitle)
                .xAxisTitle(xAxis)
                .yAxisTitle(yAxis)
                .build();

        // Populate data
        chart.addSeries("Data", xData, yData);

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Line);
        chart.getStyler().setMarkerSize(8);
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendPosition(org.knowm.xchart.style.Styler.LegendPosition.InsideNW);
        chart.getStyler().setAxisTitlesVisible(true);

        return chart;
    }

    public static CategoryChart plotSymptomsGraph(String xAxis, String yAxis, String graphTitle,
            ArrayList<Patient> medicineData, Map<String, Integer> finalData) {
        // Separate Data
        List<String> xData = new ArrayList<>(); // Use String instead of Double for X-axis data
        List<Double> yData = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : finalData.entrySet()) {
            // Add X-axis data (categories) to xData
            xData.add(entry.getKey());
            // Add Y-axis data (patient values) to yData
            yData.add(Double.valueOf(entry.getValue()));
        }

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title(graphTitle)
                .xAxisTitle(xAxis)
                .yAxisTitle(yAxis)
                .build();

        // Populate data
        chart.addSeries("Data", xData, yData);

        // Customize Chart
        chart.getStyler().setDefaultSeriesRenderStyle(CategorySeries.CategorySeriesRenderStyle.Scatter);
        chart.getStyler().setMarkerSize(10);
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendPosition(org.knowm.xchart.style.Styler.LegendPosition.InsideNW);
        chart.getStyler().setAxisTitlesVisible(true);
        chart.getStyler().setXAxisLabelRotation(88); // Rotate x-axis labels by 45 degrees
        return chart;
    }


    public static void main(String[] args) {
        String path = "/Users/god/Desktop/fullData.csv";
        ArrayList<Patient> patients = Patient.loadPatientData(path);
        String x = "Symptoms";
        String y = "Average Heal Time";
        
       
    }
    

  

    
}
