package nce.majorproject.util;

import nce.majorproject.recommendation.entity.DataSetReferer;

import java.util.*;
import java.util.stream.Stream;

public class CosineHelper {

    public static Map<Long,Double> cosineSimilarity(int rowIndex, List<DataSetReferer> data,List<DataSetReferer> userData) {
//cosine similarity value
        double dotProduct = 0.0, firstNorm = 0.0, secondNorm = 0.0;
        double cosinSimilarity;
        ArrayList<Double> similarRows = new ArrayList<>();
        Map<Long,Double> map = new HashMap<>();

        for(int dataBrowser = 0; dataBrowser < data.size(); dataBrowser++){
            if(!(data.get(dataBrowser).getRating()).equals("0.0")){
                if(data.get(dataBrowser).getSubSubCategory().equalsIgnoreCase(userData.get(rowIndex).getSubSubCategory())){
                    System.out.println("elf"+userData.get(rowIndex).getRating());
                    System.out.println("ight"+data.get(dataBrowser).getRating()+dataBrowser);
                    dotProduct +=  (Integer.valueOf(userData.get(rowIndex).getRating())) * Integer.valueOf(data.get(dataBrowser).getRating());
                    firstNorm += Math.pow(Integer.valueOf(userData.get(rowIndex).getRating()),2);
                    secondNorm +=  Math.pow(Integer.valueOf(data.get(dataBrowser).getRating()), 2);
            // Matrix f = D.getMatrix(dataBrowser, userBrowser);

                cosinSimilarity = (dotProduct / (Math.sqrt(firstNorm) * Math.sqrt(secondNorm)));
    //            similarRows.add(data.get(dataBrowser).getSn(), cosinSimilarity);
                map.put(data.get(dataBrowser).getId(),cosinSimilarity);
            }
            }
        }
        return map;
    }
    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =  new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0) return 1;
                else return compare;
            }
        };
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
    public static  <K, V> Optional<K> keys(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey).findAny();
    }

}
