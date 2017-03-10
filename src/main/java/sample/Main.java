package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main extends Application {

    final static Map<String, Double> map = new HashMap<>();
    final static String itemA = "A";
    final static String itemB = "B";
    final static String itemC = "F";
    @Override
    public void start(Stage stage) throws Exception{
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        final BarChart<Number, String> bc = new BarChart<>(xAxis, yAxis);
        bc.setTitle("Summary");
        xAxis.setLabel("Value");
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Item");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Metric");
        for(String i : map.keySet()){
            series1.getData().add(new XYChart.Data(map.get(i), i));
        }
        Scene scene = new Scene(bc, 800, 600);
        bc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) throws IOException {
        Loader loader = new Loader();
        Map<String, List<Double>> healthy = loader.loadData("healthy.txt");
        Map<String, List<Double>> ill = loader.loadData("ill.txt");
        List<VariantnessList> data = new ArrayList<>();
        for(String key : healthy.keySet()){
            List<List<Double>> tmp = Arrays.asList(ill.get(key), healthy.get(key));
            data.add(new VariantnessList(tmp, key));
        }
        LemanRozenblutt leman = new LemanRozenblutt();
        StringBuilder sb = new StringBuilder();
        StringBuilder js = new StringBuilder();
        //Drawer.addHeader(js);
        int t = 0;
        List<Map<String, Double>> output = new ArrayList<>();

        for(VariantnessList variantnessList : data){
            List<Double> list1 = healthy.get(variantnessList.getName());
            List<Double> list2 = ill.get(variantnessList.getName());
            List<List<Double>> vis = new ArrayList<>();
            vis.add(list1);
            vis.add(list2);
            //Drawer.addPoints(vis, t * 40, js);
            Double w2 = leman.calculateW2(variantnessList, list1, list2);
            Double h = leman.calculateMarker(w2, list1.size(), list2.size());
            Double value = leman.z(h, list1.size(), list2.size());

            if(value > 0){
                sb.append(value).append(" ");
                sb.append(variantnessList.getName()).append(" ");
                Map<String, Double> x = new HashMap<>();
                x.put(variantnessList.getName(), value);
                map.put(variantnessList.getName(), value);
                output.add(x);
                sb.append("\n");
            }
            t++;
        }
        /*CreateHtml(output);
        Drawer.addTail(js);
        Drawer.CreateFile(js.ToString());*/
        Files.write(Paths.get("outtest.txt"), String.valueOf(sb).getBytes());
        launch(args);
    }
}
