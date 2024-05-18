# Vaccine Efficacy Analysis and Visualization

This project aims to analyze the efficacy of potential vaccines developed by a company and visualize the data through various types of graphs. The project includes classes for creating bar graphs, pie charts, scatter plots, and histograms using data provided in a hashmap format. Additionally, it features a `Patient` class that processes data from a CSV file to derive insights about patient outcomes, such as the number of patients cured, average age of patients, and average healing time.

## Project Description

The project is designed to help visualize and analyze important data regarding vaccine efficacy. It includes a graphical user interface (GUI) that allows users to select and compare different data sets and choose the type of graph they want to generate.

### Classes

1. **BarGraphClass**
   - Takes in data in the form of a hashmap and creates bar graphs based on the provided variables.

2. **PieChartClass**
   - Takes in data in the form of a hashmap and creates pie charts based on the provided variables.

3. **ScatterPlotClass**
   - Takes in data in the form of a hashmap and creates scatter plots based on the provided variables.

4. **HistogramClass**
   - Takes in data in the form of a hashmap and creates histograms based on the provided variables.

5. **PatientClass**
   - Reads data from a CSV file and analyzes it to determine:
     - Number of patients cured.
     - Average age of patients.
     - Average time taken for patients to heal.
   - Provides essential insights and statistics from the patient data.

6. **GUIClass**
   - Serves as the user interface where users can:
     - Select the data they want to compare, such as gender vs. heal time.
     - Choose the type of graph they want to generate, such as bar graph, pie chart, scatter plot, or line graph.

## Usage

1. **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/your-repository.git
    ```
2. **Open the project in your preferred Java development environment:**
    Open the cloned repository folder in an IDE like IntelliJ IDEA, Eclipse, or NetBeans.

3. **Compile and run the GUI class to start the application:**
    - The `GUIClass` serves as the entry point for the application.
    - Users can interact with the interface to select and compare different data sets and generate various types of graphs.

## Example Code

Here's an example of how to use the `PatientClass` to read and analyze data from a CSV file:

```java
public class Main {
    public static void main(String[] args) {
        // Initialize the Patient class with the path to the CSV file
        PatientClass patientData = new PatientClass("path/to/patient_data.csv");
        
        // Analyze the patient data
        int curedPatients = patientData.getCuredPatientsCount();
        double averageAge = patientData.getAverageAge();
        double averageHealTime = patientData.getAverageHealTime();

        // Print the analysis results
        System.out.println("Number of patients cured: " + curedPatients);
        System.out.println("Average age of patients: " + averageAge);
        System.out.println("Average heal time: " + averageHealTime);
    }
}
Contributing
Feel free to fork this repository and contribute by submitting pull requests. Please ensure that your contributions are well-documented and tested.

License
This project is licensed under the MIT License.

Contact
If you have any questions or suggestions, feel free to reach out:

Email: rohankumar.id@gmail.com
GitHub: rohannes220
