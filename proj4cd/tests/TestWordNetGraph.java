import main.NGramMap;
import main.WordNetGraph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;

public class TestWordNetGraph {
    @Test
    public void testWordNetOne() {
        NGramMap ngm = new NGramMap("./data/word_history_size14377.csv", "./data/year_history.csv");
        WordNetGraph a = new WordNetGraph("./data/synsets_size10.txt", "./data/hyponyms_size10.txt", ngm);
        assertThat(a.vertices()).containsExactly(35992, 73801);
        assertThat(a.edges(35992)).containsExactly(5783, 39501, 43343, 80883);
        assertThat(a.edges(73801)).containsExactly(36289, 40607, 78590, 78880);

        assertThat(a.findIds("stroke")).containsExactly(73801);
        assertThat(a.findIds("dog's-tooth_violet")).containsExactly(35992);
        assertThat(a.findIds("dogtooth_violet")).containsExactly(35992);
        assertThat(a.findIds("crhvhce")).containsExactly();

        assertThat(a.findNames(78880)).containsExactly("upstroke");
        assertThat(a.findNames(80883)).containsExactly("blonde_lilian", "white_dog's-tooth_violet", "white_dogtooth_violet", "Erythronium_albidum");
        assertThat(a.findNames(101)).containsExactly();
        IO.print("a".compareTo("b"));
    }
    @Test
    public void testWordNetTwo() {
        NGramMap ngm = new NGramMap("./data/word_history_size14377.csv", "./data/year_history.csv");
        WordNetGraph a = new WordNetGraph("./data/synsets_size82191.txt", "./data/hyponyms_size82191.txt", ngm);
        assertThat(a.findHyponyms("pet")).containsExactly("chosen", "darling", "dearie", "deary", "ducky", "favorite", "favourite", "macushla", "mollycoddle", "pet", "teacher's_pet").inOrder();
        assertThat(a.findHyponyms("berry")).containsExactly("European_blueberry", "West_Indian_cherry", "acerola", "bacca", "baneberry", "barbados_cherry", "berry", "bilberry", "black_currant", "blackberry", "blueberry", "boxberry", "boysenberry", "checkerberry", "cowberry", "cranberry", "currant", "dewberry", "gooseberry", "hackberry", "huckleberry", "juneberry", "lingonberry", "loganberry", "lowbush_cranberry", "mountain_cranberry", "mulberry", "persimmon", "raspberry", "red_currant", "saskatoon", "serviceberry", "shadberry", "simple_fruit", "spiceberry", "strawberry", "sugarberry", "surinam_cherry", "teaberry", "whortleberry", "wintergreen").inOrder();
    }
    @Test
    public void testWordNetThree() {
        NGramMap ngm = new NGramMap("./data/word_history_size14377.csv", "./data/year_history.csv");
        WordNetGraph a = new WordNetGraph("./data/synsets_size82191.txt", "./data/hyponyms_size82191.txt", ngm);
        ArrayList<String> input1 = new ArrayList<>();
        input1.add("rock");
        input1.add("music");
        assertThat(a.findHyponyms(input1)).containsExactly("acid_rock", "art_rock", "heavy_metal", "heavy_metal_music", "progressive_rock", "psychedelic_rock", "punk", "punk_rock", "rock", "rock'n'roll", "rock-and-roll", "rock_'n'_roll", "rock_and_roll", "rock_music").inOrder();
        ArrayList<String> input2 = new ArrayList<>();
        input2.add("music");
        input2.add("rock");
        assertThat(a.findHyponyms(input2)).containsExactly("acid_rock", "art_rock", "heavy_metal", "heavy_metal_music", "progressive_rock", "psychedelic_rock", "punk", "punk_rock", "rock", "rock'n'roll", "rock-and-roll", "rock_'n'_roll", "rock_and_roll", "rock_music").inOrder();
        ArrayList<String> input3 = new ArrayList<>();
        input3.add("animal");
        input3.add("reptile");
        input3.add("snake");
        input3.add("boa");
        assertThat(a.findHyponyms(input3)).containsExactly("Charina_bottae", "Constrictor_constrictor", "Eunectes_murinus", "Indian_python", "Lichanura_trivirgata", "Morelia_spilotes_variegatus", "Python_molurus", "Python_reticulatus", "Python_sebae", "Python_variegatus", "amethystine_python", "anaconda", "boa", "boa_constrictor", "carpet_snake", "python", "reticulated_python", "rock_python", "rock_snake", "rosy_boa", "rubber_boa", "tow-headed_snake").inOrder();
        ArrayList<String> input4 = new ArrayList<>();
        input4.add("eagle");
        input4.add("owl");
        assertThat(a.findHyponyms(input4)).containsExactly();
        ArrayList<String> input5 = new ArrayList<>();
        input5.add("object");
        input5.add("owl");
        IO.print(input5);
        a.findHyponyms(input5);
    }
    @Test
    public void speedTest1() {
        NGramMap ngm = new NGramMap("./data/word_history_size14377.csv", "./data/year_history.csv");
        WordNetGraph a = new WordNetGraph("./data/synsets_size82191.txt", "./data/hyponyms_size82191.txt", ngm);
        ArrayList<String> input5 = new ArrayList<>();
        input5.add("object");
        input5.add("person");
        ArrayList<String> output1 = new ArrayList<>();
        for (String word: a.findHyponyms(input5.get(0))) {
            output1.add(word);
        }
        ArrayList<String> output2 = new ArrayList<>();
        for (String word: a.findHyponyms(input5.get(1))) {
            output2.add(word);
        }
        ArrayList<String> output3 = new ArrayList<>();
        for (String word: output1) {
            if (output2.contains(word)) {
                output3.add(word);
            }
        }
        IO.print(output3);
    }
    @Test
    public void speedTest2() {
        NGramMap ngm = new NGramMap("./data/word_history_size14377.csv", "./data/year_history.csv");
        WordNetGraph a = new WordNetGraph("./data/synsets_size82191.txt", "./data/hyponyms_size82191.txt", ngm);
        a.findHyponyms("animal");
    }
}
