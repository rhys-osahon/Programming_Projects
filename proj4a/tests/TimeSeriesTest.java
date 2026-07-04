import main.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1992);
        expectedYears.add(1994);
        expectedYears.add(1995);

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(0.0);
        expectedTotal.add(100.0);
        expectedTotal.add(600.0);
        expectedTotal.add(500.0);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }
    @Test
    public void testTimeSeries1()
    {
        //second contructor test

        TimeSeries a = new TimeSeries();
        a.put(1900, 21.8);
        a.put(1905, 22.5);
        a.put(1910, 22.3);
        TimeSeries b = new TimeSeries(a, 1900, 1905);
        assertThat(b.get(1900)).isEqualTo(a.get(1900));
        assertThat(b.get(1901)).isNull();
        assertThat(b.lastKey()).isEqualTo(1905);
        TimeSeries c = new TimeSeries(a, 1912, 2000);
        assertThat(c.get(1900)).isNull();
        assertThat(c.get(1910)).isNull();

        // years() test
        assertThat(b.years()).containsExactly(1900, 1905).inOrder();
        assertThat(a.years()).containsExactly(1900, 1905, 1910).inOrder();
        assertThat(c.years()).containsExactly();
    }
    @Test
    public void testTimeSeries2()
    {
        // data() test
        TimeSeries a = new TimeSeries();
        assertThat(a.data()).containsExactly();
        a.put(1900, 21.8);
        a.put(1905, 22.5);
        a.put(1910, 22.3);
        assertThat(a.data()).containsExactly(21.8, 22.5, 22.3).inOrder();

        // plus() test

        TimeSeries b = new TimeSeries();
        TimeSeries c = new TimeSeries();
        assertThat(b.plus(c).size()).isEqualTo(0);
        assertThat(a.plus(b).years()).containsExactly(1900, 1905, 1910);
        assertThat(a.plus(b).data()).containsExactly(21.8, 22.5, 22.3);
        b.put(1900, 20.52);
        b.put(1912, 22.35);
        assertThat(a.plus(b).years()).containsExactly(1900, 1905, 1910, 1912).inOrder();
        assertThat(a.plus(b).data()).containsExactly(42.32, 22.5, 22.3, 22.35).inOrder();
        c.put(1899, 2.2);
        c.put(1900, 2.2);
        c.put(1905, 2.2);
        c.put(1910, 2.2);
        assertThat(a.plus(c).years()).containsExactly(1899, 1900, 1905, 1910).inOrder();
        assertThat(a.plus(c).data()).containsExactly(2.2, 24.0, 24.7, 24.5).inOrder();

        // dividedBy() test
        assertThat(new TimeSeries().dividedBy(a).size()).isEqualTo(0);

        TimeSeries d = new TimeSeries();

        //a.dividedBy(d); passed as test

        d.put(1900, 2.0);
        d.put(1905, 2.0);
        d.put(1910, 2.0);
        assertThat(a.dividedBy(d).years()).containsExactly(1900, 1905, 1910).inOrder();
        assertThat(a.dividedBy(d).data()).containsExactly(10.9, 11.25, 11.15).inOrder();
        b.put(1905, 1.0);
        b.put(1910, 2.0);
        assertThat(a.dividedBy(b).years()).containsExactly(1900, 1905, 1910).inOrder();
        assertThat(a.dividedBy(b).data()).containsExactly(21.8 / 20.52 , 22.5, 11.15).inOrder();

        TimeSeries e = new TimeSeries();
        e.put(1900, 200.0);
        e.put(1901, 3.0);
        // a.dividedBy(e); passed as test
    }
} 