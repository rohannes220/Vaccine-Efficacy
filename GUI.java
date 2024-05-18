import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.List;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Rohan's Clinical Trial Management System");
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLUE);
        frame.add(panel);

        JLabel welcomeLabel = new JLabel("Welcome to the Clinical Trial Management System!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setVerticalAlignment(JLabel.CENTER);

        panel.add(welcomeLabel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.BLUE);

        JButton uploadButton = new JButton("Upload Data");
        uploadButton.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(uploadButton);

        JPanel uploadPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        uploadPanel.setBackground(Color.BLUE);
        uploadPanel.add(uploadButton);
        panel.add(uploadPanel, BorderLayout.SOUTH);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exitPanel.setBackground(Color.BLUE);
        exitPanel.add(exitButton);
        panel.add(exitPanel, BorderLayout.EAST);

        frame.setVisible(true);

        JPanel graphPanel = new JPanel(new BorderLayout());
        panel.add(graphPanel, BorderLayout.WEST);

        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uploadButton.setVisible(false);
                graphPanel.removeAll();

                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getAbsolutePath();
                    System.out.println("Selected file name: " + fileName);

                    ArrayList<Patient> patients = Patient.loadPatientData(fileName);

                    String[] chartTypes = { "Bar Graph", "Pie Chart", "Scatter Plot" };
                    String selectedChartType = (String) JOptionPane.showInputDialog(frame, "Select Chart Type:",
                            "Chart Type", JOptionPane.QUESTION_MESSAGE, null, chartTypes, chartTypes[0]);

                    String[] xAxiscategories = { "Gender", "Age", "Symptoms", "Placebo", "Covid Negative",
                            "Observations", "Heal Time(Days)" };
                    String[] yAxisCategories = { "Placebo Takers", "Medicine Cure Rate", "Medicine Takers",
                            "Cure Rate For Placebo", "Average Age", "Distribution", "Average Heal Time",
                            "Overall Cure Rate" };
                    String selectedXAxis = (String) JOptionPane.showInputDialog(frame, "Select Data for X-axis:",
                            "X-axis Data", JOptionPane.QUESTION_MESSAGE, null, xAxiscategories,
                            xAxiscategories[0]);
                    if (selectedXAxis == null) {
                        JOptionPane.showMessageDialog(frame, "Insufficient data for a graph. Exiting program.");
                        System.exit(0);
                    }

                    System.out.println("Selected X-axis data: " + selectedXAxis);

                    String selectedYAxis = "";
                    if (!selectedChartType.equals("Pie Chart")) {
                        selectedYAxis = (String) JOptionPane.showInputDialog(frame, "Select Data for Y-axis:",
                                "Y-axis Data", JOptionPane.QUESTION_MESSAGE, null, yAxisCategories,
                                yAxisCategories[0]);

                    }

                    if (selectedXAxis.equals(selectedYAxis)) {
                        JOptionPane.showMessageDialog(frame, "The x axis and y axis cannot be equal");
                        System.exit(0);
                    }

                    String chartTitle = JOptionPane.showInputDialog(frame, "Enter Chart Title:", "Chart Title",
                            JOptionPane.QUESTION_MESSAGE);

                    if (selectedChartType.equals("Bar Graph")) {
                        HashMap<String, Integer> input = decideWhichFunction(selectedXAxis, selectedYAxis);
                        BarGraphClass b1 = new BarGraphClass(selectedXAxis, selectedYAxis, chartTitle, patients,
                                input);
                        CategoryChart chart = b1.generateGraph();
                        displayChartAsImage(chart, graphPanel, frame);
                    } else if (selectedChartType.equals("Pie Chart")) {
                        if ("Symptoms".equals(selectedXAxis)) {
                            org.knowm.xchart.PieChart symptomsChart = PieChart
                                    .createSymptomsPieChart("Symptoms Pie Chart", patients);
                            displayPieChart(symptomsChart);
                        } else {
                            org.knowm.xchart.PieChart chart = PieChart.createPieChart(chartTitle, selectedXAxis,
                                    patients);
                            displayPieChart(chart);
                        }
                    } else if (selectedChartType.equals("Scatter Plot")) {
                        CategoryChart scatterPlot = generateScatterPlot(selectedXAxis, selectedYAxis, chartTitle,
                                patients);
                        displayChartAsImage(scatterPlot, graphPanel, frame);
                        if ("Symptoms".equals(selectedXAxis) && ("Overall Cure Rate".equals(selectedYAxis))) {
                            CategoryChart chart = ScatterPlot.plotSymptomsGraph(selectedXAxis, selectedYAxis,
                                    chartTitle, patients, null);
                        } else {
                            org.knowm.xchart.PieChart chart = PieChart.createPieChart(chartTitle, selectedXAxis,
                                    patients);
                            displayPieChart(chart);
                        }
                    }

                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                uploadButton.setVisible(true);
                graphPanel.removeAll();
                panel.remove(graphPanel);
                panel.add(uploadPanel, BorderLayout.SOUTH);
                panel.revalidate();
                panel.repaint();
            }
        });

        frame.setVisible(true);
    }

    private static CategoryChart generateScatterPlot(String selectedXAxis, String selectedYAxis, String graphTitle,
            ArrayList<Patient> patients) {
        CategoryChart chart = null;

        if (selectedXAxis.equals("Age") && selectedYAxis.equals("Average Heal Time")) {
            ArrayList<String> ages = Patient.getAllAges(patients);
            TreeMap<String, Integer> input = Patient.agevsHealTime(patients, ages);
            chart = ScatterPlot.plotLineGraph(selectedXAxis, selectedYAxis, graphTitle, patients, input);
        } else if (selectedXAxis.equals("Age") && selectedYAxis.equals("Overall Cure Rate")) {
            ArrayList<String> ages = Patient.getAllAges(patients);
            TreeMap<String, Integer> input = Patient.agevsCovid(patients, ages);
            chart = ScatterPlot.plotLineGraph(selectedXAxis, selectedYAxis, graphTitle, patients, input);
        } else if (selectedXAxis.equals("Symptoms") && selectedYAxis.equals("Average Heal Time")) {
            ArrayList<String> symptoms = Patient.getAllSyptoms(patients);
            HashMap<String, Integer> input = Patient.symptomsvsHealTime(patients, symptoms);
            chart = ScatterPlot.plotSymptomsGraph(selectedXAxis, selectedYAxis, graphTitle, patients, input);
        }
        return chart;
    }

    private static HashMap<String, Integer> decideWhichFunction(String xAxis, String yAxis) {
        String filePath = "/Users/god/Desktop/Sonnet Projects /fullData.csv";
        ArrayList<Patient> patients = Patient.loadPatientData(filePath);
        HashMap<String, Integer> answer = new HashMap<>();
        ArrayList<String> genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");
        if ("Gender".equals(xAxis) && "Average Age".equals(yAxis)) {
            answer = Patient.AverageAgeForEachGender(patients);
        } else if ("Gender".equals(xAxis) && "Placebo Takers".equals(yAxis)) {
            answer = Patient.PlaceboTakers(patients);
        } else if ("Gender".equals(xAxis) && "Cure Rate For Placebo".equals(yAxis)) {
            answer = Patient.PlaceboCureRateforTakers(patients);
        } else if ("Age".equals(xAxis) && "Medicine Takers".equals(yAxis)) {
            answer = Patient.medicineTakersForAge(patients);
        } else if ("Age".equals(xAxis) && "Medicine Cure Rate".equals(yAxis)) {
            answer = Patient.medicineCureRatevsAge(patients);
        }
        return answer;
    }

    private static HashMap<String, Integer> decideWhichDistributionFunction(String xAxis, String yAxis) {
        String filePath = "/Users/god/Desktop/Sonnet Projects /fullData.csv";
        ArrayList<Patient> patients = Patient.loadPatientData(filePath);
        HashMap<String, Integer> answer = new HashMap<>();
        if ("Symptoms".equals(xAxis) && "Distribution".equals(yAxis)) {
            answer = Patient.symptomsDistribution(patients);
        } else if ("Observations".equals(xAxis) && "Distribution".equals(yAxis)) {
            answer = Patient.finalObservationDistribution(patients);
        } else if ("Gender".equals(xAxis) && "Distribution".equals(yAxis)) {
            answer = Patient.genderDistribution(patients);
        } else if ("Heal Time (Days)".equals(xAxis) && "Distribution".equals(yAxis)) {
            answer = Patient.healTimeDistribution(patients);
        } else if ("Placebo".equals(xAxis) && "Distribution".equals(yAxis)) {
            answer = Patient.placeboDistribution(patients);
        } else if ("Age".equals(xAxis) && "Distribution".equals(yAxis)) {
            answer = Patient.ageDistribution(patients);
        }
        return answer;
    }

    public static void displayPieChart(org.knowm.xchart.PieChart chart) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        BufferedImage chartImage = BitmapEncoder.getBufferedImage(chart);

        ImageIcon icon = new ImageIcon(chartImage);

        JLabel label = new JLabel(icon);
        panel.add(label, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private static void displayChartAsImage(CategoryChart chart, JPanel graphPanel, JFrame frame) {
        BufferedImage bufferedImage = BitmapEncoder.getBufferedImage(chart);
        byte[] imageData = getBytesFromImage(bufferedImage);

        ImageIcon icon = new ImageIcon(imageData);
        graphPanel.removeAll();
        graphPanel.add(new JLabel(icon));
        graphPanel.revalidate();
        graphPanel.repaint();

        graphPanel.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
        frame.revalidate();
        frame.repaint();
    }

    private static byte[] getBytesFromImage(BufferedImage bufferedImage) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
