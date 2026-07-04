import main.BST;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class TestBST {
    @Test
    public void testTree() {
        BST<String> a = new BST<String>("d");
        a.add("b");
        a.add("c");
        a.add("g");
        a.add("a");
        a.add("f");
        a.add("e");
        assertThat(a.inOrderList()).containsExactly("a", "b", "c", "d", "e", "f", "g").inOrder();
        BST<String> b = new BST<String>("B");
        b.add("Cool");
        b.add("cool");
        b.add("pig");
        b.add("aaron");
        b.add("Zephr");
        assertThat(b.inOrderList()).containsExactly("B", "Cool", "Zephr", "aaron", "cool", "pig").inOrder();
    }
}
