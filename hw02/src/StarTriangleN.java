public class StarTriangleN {
   /**
     * Prints a right-aligned triangle of stars ('*') with N lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle(int N) {
      // TODO: Fill in this function
      for(int i = 1; i <= N; i++)
      {
         int spaces = N - i;
         int stars = i;
         String result = "";
         while(spaces > 0)
         {
            result = result + " ";
            spaces --;
         }
         while(stars > 0)
         {
            result = result + "*";
            stars --;
         }
         IO.println(result);
      }
   }
   
   public static void main(String[] args) {
      starTriangle(7);
   }
}