import main.NGramMap;
import main.WordNetGraph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;

public class TestkDoesNotEqual0 {
    @Test
    public void testKFunc() {
        NGramMap ngm = new NGramMap("./data/word_history_eecs.csv", "./data/year_history.csv");
        WordNetGraph a = new WordNetGraph("./data/synsets_eecs.txt", "./data/hyponyms_eecs.txt", ngm);
        assertThat(a.findHyponyms("EECS16A", 1, 1900, 2020)).containsExactly("EECS16A");
        assertThat(a.findHyponyms("EECS16A", 4, 1900, 2020)).containsExactly("EE120", "EE126", "EECS16A", "EECS16B").inOrder();
        assertThat(a.findHyponyms("EE105", 10, 1900, 2020)).containsExactly("EE105", "EE113", "EE130", "EE140", "EE142").inOrder();
        assertThat(a.findHyponyms("EE117", 1, 2019, 2020)).containsExactly();
        assertThat(a.findHyponyms("EE120", 1, 1900, 2017)).containsExactly();

        ArrayList<String> inputs = new ArrayList<>();
        inputs.add("CS61A");
        inputs.add("CS70");
        assertThat(a.findHyponyms(inputs, 1, 1900, 2020)).containsExactly("CS170");
        assertThat(a.findHyponyms(inputs, 5, 1900, 2020)).containsExactly("CS161", "CS162", "CS170", "CS188", "CS189").inOrder();
        inputs.add("CS170");
        assertThat(a.findHyponyms(inputs, 4, 1900, 2020)).containsExactly("CS170", "CS172", "CS174", "CS176").inOrder();
    }
}
