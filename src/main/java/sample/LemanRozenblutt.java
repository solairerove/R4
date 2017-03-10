package sample;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LemanRozenblutt {

    public Double calculateW2(VariantnessList variantnessList, List<Double> list1, List<Double> list2) {
        double n = list1.size();
        double m = list2.size();
        return ((1.0 / 6.0 + sum(variantnessList, list1) / m + sum(variantnessList, list2) / n) / n / m - 2.0 / 3.0);
    }

    public double calculateMarker(double w2, int n, int m) {
        return (double) (n * m) / (n + m) * w2;
    }

    public double m(int n, int m) {
        return 1 / 6.0 * (1 + 1.0 / (m + n));
    }

    public double d(int n, int m) {
        return 1.0 / 45 * (1 + 1.0 / (m + n)) * (1 + 1 / (n + m) - 3.0 / 4 * (1.0 / n + 1.0 / m));
    }

    public double z(double marker, int n, int m) {
        return (marker - m(n, m)) / Math.sqrt(45.0 * d(n, m)) + 1 / 6.0;
    }

    public double t(VariantnessList variantnessList, List<Double> list1, List<Double> list2) {
        double n = list1.size();
        double m = list2.size();
        return (1 / (n * m * (n + m))) * (n * sum(variantnessList, list1) + m * sum(variantnessList, list2)) -
                (4 * n * m - 1) / (6 * (m + n));
    }

    public double sum(VariantnessList variantnessList, List<Double> list)
    {
        double sum = 0.0;
        List<Double> ranks = rankArray(variantnessList, list);
        for (int i = 0; i < list.size(); i++)
        {
            double tmp = ranks.get(i) - (i+1);
            sum += Math.abs(tmp * tmp);
        }
        return sum;
    }

    public List<Double> rankArray(VariantnessList variantnessList, List<Double> list)
    {
        List<Double> f = list.stream().map(variantnessList::rank).collect(Collectors.toList());
        Collections.sort(f);
        return f;
    }


}
