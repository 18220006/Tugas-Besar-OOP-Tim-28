import java.util.*;
public class Effectivity {

    private static HashMap<ElementEffectivityKey, Double> effectivityList;

    public Effectivity() {
        effectivityList = new HashMap<ElementEffectivityKey, Double>();
    }

    public void add(ElementEffectivityKey a, double effectivity) {
        effectivityList.put(a, effectivity);
    }

    public static double getEffectivity (ElementEffectivityKey a) {
        double effectivity = effectivityList.get(a);
        return effectivity;
    }
}