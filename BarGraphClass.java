import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;

public class BarGraphClass {
    String xAxis;
    String yAxis;
    String graphTitle;
    ArrayList<Patient> subjects;
    HashMap<String, Integer> data;
    TreeMap<String, Integer> data2;

    // Constructor for HashMap data
    public BarGraphClass(String xData, String yData, String title, ArrayList<Patient> medicineData,
            HashMap<String, Integer> finalData) {
        this.xAxis = xData;
        this.yAxis = yData;
        this.graphTitle = title;
        this.subjects = medicineData;
        this.data = finalData;
    }

    // Constructor for TreeMap data
    public BarGraphClass(String xData, String yData, String title, ArrayList<Patient> patients,
            TreeMap<String, Integer> data2) {
        this.xAxis = xData;
        this.yAxis = yData;
        this.graphTitle = title;
        this.subjects = patients;
        this.data2 = data2;
    }

    public CategoryChart generateGraph() {
        CategoryChart chart = new CategoryChartBuilder()
                .width(700)
                .height(700)
                .title(graphTitle)
                .xAxisTitle(xAxis)
                .yAxisTitle(yAxis)
                .build();

        Map<String, Integer> categoryIndices;
        if (data != null) {
            categoryIndices = new HashMap<>(data.size());
            int index = 0;
            for (String category : data.keySet()) {
                categoryIndices.put(category, index++);
            }
        } else if (data2 != null) {
            categoryIndices = new HashMap<>(data2.size());
            int index = 0;
            for (String category : data2.keySet()) {
                categoryIndices.put(category, index++);
            }
        } else {
            throw new IllegalArgumentException("Both data and data2 are null.");
        }

        for (Map.Entry<String, Integer> entry : (data != null ? data.entrySet() : data2.entrySet())) {
            String category = entry.getKey();
            int value = entry.getValue();
            Integer categoryIndex = categoryIndices.get(category);
            if (categoryIndex != null) {
                chart.addSeries(category, new int[] { categoryIndex }, new int[] { value });
            }
        }

        chart.getStyler().setXAxisLabelRotation(45);
        chart.getStyler().setXAxisTicksVisible(false);
        chart.getStyler().setXAxisTickMarkSpacingHint(1);

        return chart;
    }

    public static void main(String[] args) {
        String path = "/Users/god/Desktop/fullData.csv";
        ArrayList<Patient> patients = Patient.loadPatientData(path);
        String xData = "Age";
        String yData = "Medicine Takers";
        ArrayList<String> ages = Patient.getAllAges(patients);
        HashMap<String, Integer> data = Patient.PlaceboCureRatevsAge(patients);
        BarGraphClass b1 = new BarGraphClass(xData, yData, "Graph Title", patients, data);
        CategoryChart chart = b1.generateGraph();
        new SwingWrapper<>(chart).displayChart();
    }
}
