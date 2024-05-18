import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;

public class HistogramClass {
    String variable;
    String graphTitle;
    Map<String, Integer> data;
    ArrayList<Patient> patients;

    public HistogramClass(String variable, String graphTitle, HashMap<String, Integer> data,
            ArrayList<Patient> patients) {
        this.variable = variable;
        this.graphTitle = graphTitle;
        this.data = data;
        this.patients = patients;
    }

    public void createHistogram() {
        TreeMap<String, Integer> sortedData = new TreeMap<>(data);

        ArrayList<String> xAxis = new ArrayList<>();
        ArrayList<Integer> yAxis = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedData.entrySet()) {
            xAxis.add(entry.getKey());
            yAxis.add(entry.getValue());
        }

        CategoryChart chart = new CategoryChartBuilder()
                .width(1000)
                .height(900)
                .title(graphTitle)
                .xAxisTitle(variable)
                .yAxisTitle("Frequency")
                .build();

        chart.addSeries("Data", xAxis, yAxis);

        chart.setTitle(graphTitle);
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setXAxisLabelRotation(45);

        new SwingWrapper<>(chart).displayChart();
    }

    public void createSymptomsHistogram() {
        TreeMap<String, Integer> sortedData = new TreeMap<>(data);

        ArrayList<String> xAxis = new ArrayList<>();
        ArrayList<Integer> yAxis = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedData.entrySet()) {
            xAxis.add(entry.getKey());
            yAxis.add(entry.getValue());
        }

        CategoryChart chart = new CategoryChartBuilder()
                .width(1000)
                .height(900)
                .title(graphTitle)
                .xAxisTitle(variable)
                .yAxisTitle("Frequency")
                .build();

        chart.addSeries("Symptoms Data", xAxis, yAxis);

        chart.setTitle(graphTitle);
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setXAxisLabelRotation(45); // Adjust the rotation angle as needed

        new SwingWrapper<>(chart).displayChart();
    }

    public static void main(String[] args) {
        String path = "/Users/god/Desktop/fullData.csv";
        ArrayList<Patient> patients = Patient.loadPatientData(path);
        String variable = "Symptoms";
        String title = "Symptoms Distribution";
        HashMap<String, Integer> data = Patient.symptomsDistribution(patients); // Assuming you have this method
        HistogramClass h1 = new HistogramClass(variable, title, data, patients);
        h1.createSymptomsHistogram();
    }
}
