package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        Loader loader = new Loader();
        Map<String, List<Double>> helthy = loader.loadData("file1.txt");
        Map<String, List<Double>> ill = loader.loadData("file2.txt");
        List<VariantnessList> data = new ArrayList<>();
        for(String key : helthy.keySet()){
            List<List<Double>> tmp = Arrays.asList(ill.get(key), helthy.get(key));
            data.add(new VariantnessList(tmp, key));
        }
        LemanRozenblutt leman = new LemanRozenblutt();
        StringBuilder sb = new StringBuilder();
        StringBuilder js = new StringBuilder();
        //Drawer.addHeader(js);
        int t = 0;
        List<Map<String, Double>> output = new ArrayList<>();

        for(VariantnessList variantnessList : data){
            List<Double> list1 = helthy.get(variantnessList.getName());
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
                output.add((Map<String, Double>) new HashMap<>().put(variantnessList.getName(), value));
                sb.append("\n");
            }
            t++;
        }
        /*CreateHtml(output);
        Drawer.addTail(js);
        Drawer.CreateFile(js.ToString());*/
        String content = "Hello World !!";
        Files.write(Paths.get("out.txt"), String.valueOf(sb).getBytes());
        launch(args);
    }
}
