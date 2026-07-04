import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.

    @Test
    public void task6()
    {
        LinkedListDeque61B<Integer> aList = new LinkedListDeque61B<>();
        assertWithMessage("Size should be zero").that(aList.size()).isEqualTo(0);
        aList.addFirst(2);
        assertThat(aList.size()).isEqualTo(1);
        aList.addFirst(200);
        assertThat(aList.size()).isEqualTo(2);
        LinkedListDeque61B<Integer> bList = new LinkedListDeque61B<>();
        assertWithMessage("Falsey claiming to have entries").that(bList.isEmpty()).isTrue();
        bList.addLast(400001);
        assertWithMessage("Falsey claiming to NOT have entries").that(bList.isEmpty()).isFalse();
    }

    @Test
    public void task7()
    {
        LinkedListDeque61B<String> aList = new LinkedListDeque61B<>();
        assertWithMessage("Shouldn't return a value from empty list besides null for getFirst").that(aList.getFirst()).isNull();
        aList.addLast("Boy");
        aList.addLast("Soy");
        aList.addFirst("Coy");
        assertThat(aList.getFirst()).isEqualTo("Coy");
        assertThat(aList.getLast()).isEqualTo("Soy");
        LinkedListDeque61B<Double> bList = new LinkedListDeque61B<>();
        assertWithMessage("Shouldn't return a value from empty list besides null for getLast").that(bList.getLast()).isNull();
    }

    @Test
    public void task8() {
        LinkedListDeque61B<String> aList = new LinkedListDeque61B<>();
        // iterative get tests
        assertWithMessage("Can't pull a element from an empty list besides null").that(aList.get(0)).isNull();
        aList.addFirst("Boom");
        aList.addFirst("KaBlama");
        aList.addLast("Tick");
        aList.addLast("Shnip");
        assertWithMessage("Invalid index should return null, out of bounds").that(aList.get(4)).isNull();
        assertWithMessage("Invalid index should return null, negative index").that(aList.get(-1)).isNull();
        assertThat(aList.get(0)).isEqualTo("KaBlama");
        assertThat(aList.get(3)).isEqualTo("Shnip");
        assertThat(aList.get(1)).isEqualTo("Boom");

        LinkedListDeque61B<String> bList = new LinkedListDeque61B<>();
        // recursive get tests
        assertWithMessage("Can't pull a element from an empty list besides null").that(bList.getRecursive(0)).isNull();
        bList.addFirst("Boom");
        bList.addFirst("KaBlama");
        bList.addLast("Tick");
        bList.addLast("Shnip");
        bList.toList();
        assertWithMessage("Invalid index should return null, out of bounds").that(bList.getRecursive(4)).isNull();
        assertWithMessage("Invalid index should return null, out of bounds").that(bList.getRecursive(-1)).isNull();
        assertThat(bList.getRecursive(0)).isEqualTo("KaBlama");
        assertThat(bList.getRecursive(3)).isEqualTo("Shnip");
        assertThat(bList.getRecursive(1)).isEqualTo("Boom");
    }

    @Test
    public void task9()
    {
        LinkedListDeque61B<Integer> aList = new LinkedListDeque61B<>();
        aList.addFirst(2);
        aList.addLast(4);
        aList.addLast(6);
        aList.addFirst(0);
        assertThat(aList.removeFirst()).isEqualTo(0);
        assertThat(aList.toList()).containsExactly(2, 4, 6).inOrder();

        LinkedListDeque61B<Integer> bList = new LinkedListDeque61B<>();
        bList.addLast(5);
        bList.addFirst(3);
        bList.addFirst(1);
        bList.addLast(7);
        assertThat(bList.removeLast()).isEqualTo(7);
        assertThat(bList.toList()).containsExactly(1, 3, 5).inOrder();

        LinkedListDeque61B<Integer> cList = new LinkedListDeque61B<>();
        assertWithMessage("With nothing, removing from the front should grab null").that(cList.removeFirst()).isNull();
        assertWithMessage("With nothing, removing from the back should grab null").that(cList.removeLast()).isNull();
    }

}