import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ArrayOtherTests
{
    @Test
    public void testTask12()
    {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        a.addFirst(4);
        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        assertThat(a).containsExactly(1, 2, 3, 4).inOrder();
    }
    @Test public void testTask13()
    {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        a.addFirst(4);
        a.addFirst(3);
        a.addFirst(2);
        a.addFirst(1);
        assertThat(a).isNotEqualTo("b");
        assertThat(a).isNotEqualTo(new ArrayDeque61B<Integer>());

        ArrayDeque61B<Integer> b = new ArrayDeque61B<>();
        b.addFirst(4);
        b.addFirst(3);
        b.addFirst(2);
        b.addFirst(0);
        assertThat(a).isNotEqualTo(b);

        ArrayDeque61B<Integer> c = new ArrayDeque61B<>();
        c.addFirst(4);
        c.addFirst(3);
        c.addFirst(2);
        c.addFirst(1);
        assertThat(a).isEqualTo(c);
    }
    @Test
    public void testTask14()
    {
        ArrayDeque61B<String> a = new ArrayDeque61B<>();
        a.addFirst("Gerlicious");
        a.addFirst("Fra");
        a.addFirst("Cala");
        a.addFirst("Super");
        assertThat(a.toString()).isEqualTo("[Super, Cala, Fra, Gerlicious]");
        IO.print(a);
    }
    @Test
    public void personalTest()
    {
        ArrayDeque61B<String> a = new ArrayDeque61B<>();
        a.addFirst("Doscious");
        a.addFirst("Alla");
        a.addFirst("Expie");
        a.addFirst("Gerlicious");
        a.addFirst("Fra");
        a.addFirst("Cala");
        a.addFirst("Super");
        assertThat(new ArrayDeque61B<String>().removeMiddle()).isNull();
        assertThat(a.removeMiddle()).isEqualTo("Gerlicious");
        assertThat(a.toList()).containsExactly("Super", "Cala", "Fra", "Expie", "Alla", "Doscious", null).inOrder();

        ArrayDeque61B<Integer> b = new ArrayDeque61B<>();
        b.addLast(1);
        b.addLast(2);
        b.addLast(3);
        b.addLast(4);
        b.addLast(5);

        assertThat(b.removeMiddle()).isEqualTo(3);
        List<Integer> c = new ArrayList<>();
        c.add(1);
        c.add(2);
        c.add(4);
        c.add(5);
        c.add(null);
        assertThat(b.toList()).isEqualTo(c);
    }
}
