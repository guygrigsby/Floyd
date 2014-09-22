import java.util.Scanner;

public class Floyd {

  private static int INF = Integer.MAX_VALUE;

  private static int[][] testWeights = new int[][]{
    {  0,   1, INF,   1,   5},
    {  9,   0,   3,   2, INF},
    {INF, INF,   0,   4, INF},
    {INF, INF,   2,   0,   3},
    {  3, INF, INF, INF,   0}
  };

  private int[][] weights;
  private int[][] intermediates;
  
  private Scanner keys;
  
  public static void main(String[] args) {
    boolean debug = false;
    if (args.length > 0 && args[0].equals("d")) {
      debug = true;
    }
    new Floyd().doIt(debug);
  }

  private void doIt(boolean debug) {
    int vCount;
    if (!debug) {
      keys = new Scanner(System.in);
      pl("Enter vertex count");
      vCount = keys.nextInt();
    } else {
      vCount = 5;
    }
    weights = debug? testWeights : getWeights(vCount);
    intermediates = new int[vCount][vCount];
    pl("Grid 0");
    printGrid();
    for (int k = 0; k < weights.length; k++) {
      calculateLayer(k);
    }
  }

  private int[][] getWeights(int vCount) {
    int[][] weights = new int[vCount][vCount];
    for (int k = 0; k < vCount; k++) {
      for (int j = 0; j < vCount; j++) {
        if (k == j) {
          weights[k][j] = 0;
        } else {
          pl("Enter weight from " + k + " to " + j);
          int next = keys.nextInt();
          next = next < 0 ? INF : next;
          weights[k][j] = keys.nextInt();
        }
      }
    }
    return weights;
  }
  private void calculateLayer(int layer) {
    for (int k = 0; k < weights.length; k++) {
      if (k == layer) {
        continue;
      }
      for (int j = 0; j < weights[0].length; j++){
        if (j == layer) {
          continue;
        }
        int possible = weights[k][layer] + weights[layer][j];
        if (possible > 0 && possible < weights[k][j]) {
          weights[k][j] = possible;
          intermediates[k][j] = layer + 1;
        }
      }
    }
    pl("Grid " + (layer + 1));
    printGrid();
  }
  private void pl(Object out) {
    System.out.println(out);
  }
  public void printGrid() {
    System.out.printf("%8d %8d %8d %8d %8d\n", 1, 2, 3, 4, 5 );
    for(int k = 0; k < weights.length; k++) {
      System.out.printf("%d", k+1);
      for(int j = 0; j < weights[0].length; j++) {
        String weight = weights[k][j] == INF? "-" : weights[k][j]+"";
        System.out.printf("%5s (%d)", weight, intermediates[k][j]);
      }
      System.out.println();
    }
  }
}
