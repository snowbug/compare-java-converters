package me.sokolenko.test.compareConverters;

import me.sokolenko.test.compareConverters.dozer.DozerConverter;
import me.sokolenko.test.compareConverters.manual.ManualConverter;
import me.sokolenko.test.compareConverters.model.source.Category;
import me.sokolenko.test.compareConverters.orika.OrikaConverter;
import me.sokolenko.test.compareConverters.selma.SelmaConverter;
import org.apache.commons.lang3.StringUtils;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anatoliy Sokolenko
 */
public class Main {

    private static final Map<String, Class> CONVERTERS = new HashMap<String, Class>() {{
//        put("dozer", DozerConverter.class);
        put("orika", OrikaConverter.class);
//        put("selma", SelmaConverter.class);
//        put("manual", ManualConverter.class);
    }};

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Not all mandatory properties specified. " +
                    "Please specify converter name and number of iterations you would like to perform");
        }
        int iterationsCount = getIterationsCount(args);
        PodamFactory randomizer = new PodamFactoryImpl();
        Converter converter = null;

        for (Class c : CONVERTERS.values()) {
            try {
                converter = (Converter) c.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalArgumentException("Converter class should have default public constructor");
            }

            // warm up
            loops(converter, randomizer, 1000);

            // real run
            int[] latencies = loops(converter, randomizer, iterationsCount);
            System.out.println("Using " + converter.getClass().getSimpleName());
            printResults(latencies);
        }
    }

    private static int[] loops(Converter converter, PodamFactory randomizer, int iterationsCount) {
        int[] latencies = new int[iterationsCount];
        for (int i = 0; i < iterationsCount; i++) {
            Category category = randomizer.manufacturePojo(Category.class);

            long start = System.nanoTime();
            me.sokolenko.test.compareConverters.model.target.Category result = converter.map(category);
            long end = System.nanoTime();

            latencies[i] = (int) (end - start);
        }
        return latencies;
    }

    private static void printResults(int[] latencies) {
        Arrays.sort(latencies);
        DecimalFormat df = new DecimalFormat("#,###,##0");

        System.out.println("Min " + df.format(latencies[0]));
        System.out.println("Mid " + df.format(latencies[latencies.length / 2]));
        System.out.println("90% " + df.format(latencies[((int) (latencies.length * 0.9))]));
        System.out.println("95% " + df.format(latencies[((int) (latencies.length * 0.95))]));
        System.out.println("99% " + df.format(latencies[((int) (latencies.length * 0.99))]));
        System.out.println("99.9% " + df.format(latencies[((int) (latencies.length * 0.999))]));
        System.out.println("99.99% " + df.format(latencies[((int) (latencies.length * 0.9999))]));
        System.out.println("99.999% " + df.format(latencies[((int) (latencies.length * 0.99999))]));
        System.out.println("99.9999% " + df.format(latencies[((int) (latencies.length * 0.999999))]));
        System.out.println("Max " + df.format(latencies[latencies.length - 1]));
    }

    /**
     * @param args
     * @return
     * @deprecated
     */
    private static Converter getConverter(String[] args) {
        try {
            String conterterName = args[0];
            Class converterClass = CONVERTERS.get(conterterName);
            if (converterClass == null) {
                throw new IllegalArgumentException("Please specify known converter: " + StringUtils.join(CONVERTERS.keySet().toArray()));
            }
            return (Converter) converterClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Converter class should have default public constructor");
        }
    }

    private static int getIterationsCount(String[] args) {
        try {
            int iterationsCount;
            String iterationsString = args[0];
            iterationsCount = Integer.valueOf(iterationsString);
            if (iterationsCount <= 0) {
                throw new IllegalArgumentException("Iterations count should be greater than zero");
            }
            return iterationsCount;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Iterations count should be valid integer");
        }
    }

}
