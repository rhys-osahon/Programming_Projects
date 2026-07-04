import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest
{
    @Test
    public void testTask3()
    {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        a.addFirst(400);
        assertThat(a.size()).isEqualTo(1);
        assertThat(a.get(0)).isEqualTo(400);
        a.addFirst(4);
        assertThat(a.get(0)).isEqualTo(4);
        assertThat(a.get(1)).isEqualTo(400);


        ArrayDeque61B<Integer> b = new ArrayDeque61B<>();
        b.addLast(4000);
        assertThat(b.size()).isEqualTo(1);
        assertThat(b.get(0)).isEqualTo(4000);
        b.addLast(40);
        assertThat(b.get(1)).isEqualTo(40);
        assertThat(b.get(0)).isEqualTo(4000);
    }
    @Test
    public void testTask4()
    {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        assertThat(a.getFirst()).isNull();
        assertThat(a.getLast()).isNull();
        a.addFirst(1);
        a.addFirst(2);
        assertThat(a.getFirst()).isEqualTo(2);
        a.addLast(4);
        a.addLast(3);
        assertThat(a.getLast()).isEqualTo(3);
    }
    @Test
    public void testTask5()
    {
        ArrayDeque61B<String> a = new ArrayDeque61B<>();
        assertThat(a.get(0)).isNull();
        a.addFirst("a");
        a.addLast("b");
        a.addFirst("c");
        a.addLast("d");
        assertWithMessage("Not at index 0").that(a.get(0)).isEqualTo("c");
        assertWithMessage("Not at index 3").that(a.get(3)).isEqualTo("d");
        assertWithMessage("Not at index 1").that(a.get(1)).isEqualTo("a");
        assertWithMessage("Not at index 2").that(a.get(2)).isEqualTo("b");
        assertThat(a.get(-1)).isNull();
        assertThat(a.get(10)).isNull();
    }
    @Test
    public void testTask6()
    {
        ArrayDeque61B<String> a = new ArrayDeque61B<>();
        assertThat(a.isEmpty()).isTrue();
        assertThat(a.size()).isEqualTo(0);
        a.addFirst("Greg");
        assertThat(a.isEmpty()).isFalse();
        assertThat(a.size()).isEqualTo(1);
    }
    @Test
    public void testTask7()
    {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        assertThat(a.toList()).containsExactly().inOrder();
        a.addFirst(2);
        a.addFirst(20);
        a.addLast(200);
        assertThat(a.toList()).containsExactly(20, 2, 200).inOrder();
    }
    @Test
    public void testTask8()
    {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        a.addLast(5);
        a.addLast(4);
        a.addLast(3);
        a.addLast(2);
        a.addLast(1);
        assertThat(a.removeFirst()).isEqualTo(5);
        assertThat(a.size()).isEqualTo(4);
        assertThat(a.getFirst()).isEqualTo(4);

        assertThat(a.removeLast()).isEqualTo(1);
        assertThat(a.size()).isEqualTo(3);
        assertThat(a.getLast()).isEqualTo(2);
    }
    @Test
    public void testTask9()
    {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        int i = 0;
        while (i < 8)
        {
            a.addLast(i);
            i++;
        }
        assertThat(a.size()).isEqualTo(8);
        a.addLast(8);
        assertThat(a.size()).isEqualTo(9);
        assertThat(a.getLast()).isEqualTo(8);
        assertThat(a.get(2)).isEqualTo(2);
    }
    @Test
    public void testTask10()
    {
        ArrayDeque61B<Integer> a = new ArrayDeque61B<>();
        int i = 0;
        while (i < 8)
        {
            a.addLast(i);
            i++;
        }
        a.addLast(8);
        i = 0;
        while (i < 5)
        {
            a.removeLast();
            i++;
        }
        assertThat(a.size()).isEqualTo(4);
        a.removeLast();
        assertThat(a.size()).isEqualTo(3);
    }
}
