package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Loader {

    public void loadRightFile() throws IOException {
        InputStream is = new FileInputStream(new File("out.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        StringBuilder text = new StringBuilder();
        List<String> lines = br.lines().collect(Collectors.toList());

        for(String x : lines){
            String[] i = x.split(" ");
            Main.map.put(i[1], Double.valueOf(i[0]));
        }
    }

    private List<String> loadFile(String path) throws IOException {
        InputStream is = new FileInputStream(new File(path));
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        StringBuilder text = new StringBuilder();
        return br.lines().collect(Collectors.toList());
    }

    public Map<String, List<Double>> loadData(String path) throws IOException {
        List<String> strs = loadFile(path);
        Map<String, List<Double>> result = new HashMap<>();
        String[] metrics = strs.get(0).split(";");
        List<List<Double>> values = new ArrayList<>();
        for (int i = 0; i < strs.size() - 1; i++) {
            values.add(Stream.of(strs.get(i + 1).split(";")).filter(Loader::isNumeric)
                    .map(Double::valueOf).collect(Collectors.toList()));
        }
        values = values.stream().filter(x -> x.size() != 0).collect(Collectors.toList());

        for (int i = 0; i < metrics.length; i++) {
            result.put(metrics[i], values.get(i));
        }

        return result;
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
