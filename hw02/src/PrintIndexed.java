public class PrintIndexed {
   /**
     * Prints each character of a given string followed by the reverse of its index.
     * Example: printIndexed("hello") -> h4e3l2l1o0
     */
   public static void printIndexed(String s) {
      // TODO: Fill in this function
      int i = 0;
      int maxInd = s.length() - 1;
      while(i <= maxInd)
      {
         IO.print(s.substring(i, i + 1));
         IO.print(maxInd - i);
         i ++;
      }
   }

   public static void main(String[] args) {
      printIndexed("hello");
      printIndexed("cat"); // should print c2a1t0
   }
}