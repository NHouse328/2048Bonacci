import java.util.*;

enum Direction {
  UP,
  DOWN,
  LEFT,
  RIGHT
}

class The2048Bonacci {

  private int[][] gameArea;
  private int width;
  private int height;

  private static final ArrayList<Integer> fibSeq = new ArrayList<>();
  private static final Map<Integer, Integer> fibIndex = new HashMap<>();

  static {
    fibSeq.add(1);
    fibSeq.add(1);
    int next;
    while ((next = fibSeq.get(fibSeq.size() - 1) + fibSeq.get(fibSeq.size() - 2)) <= 65535) {
      fibSeq.add(next);
    }
    for (int i = 0; i < fibSeq.size(); i++) {
      fibIndex.put(fibSeq.get(i), i);
    }
  }

  public The2048Bonacci(int[][] gameArea) {
    this.gameArea = gameArea;
    this.width = gameArea[0].length;
    this.height = gameArea.length;
  }

  public int getTile(int x, int y) {
    return gameArea[y][x];
  }

  public void setTile(int x, int y, int fibValue) {
    gameArea[y][x] = fibValue;
  }

  public String getDescription() {
    StringBuilder strBuilder = new StringBuilder();
    for (int[] line : gameArea) {
      String strLine = Arrays.stream(line)
          .mapToObj(fibVal -> String.format("%2d", fibVal))
          .reduce((a, b) -> a + " " + b)
          .orElse("");
      strBuilder.append(strLine).append("\n");
    }
    return strBuilder.toString();
  }

  public void push(Direction dir) {
    for (int i = 0; i < 4; i++) {
      int[] line = extractLine(i, dir);
      if (dir == Direction.RIGHT || dir == Direction.DOWN) {
        reverse(line);
      }
      int[] merged = mergeLine(line);
      if (dir == Direction.RIGHT || dir == Direction.DOWN) {
        reverse(merged);
      }
      setLine(i, merged, dir);
    }
  }

  private int[] extractLine(int index, Direction dir) {
    int[] result = new int[4];
    for (int i = 0; i < 4; i++) {
      switch (dir) {
        case LEFT -> result[i] = gameArea[index][i];
        case RIGHT -> result[i] = gameArea[index][i];
        case UP -> result[i] = gameArea[i][index];
        case DOWN -> result[i] = gameArea[i][index];
      }
    }
    return result;
  }

  private void setLine(int index, int[] line, Direction dir) {
    for (int i = 0; i < 4; i++) {
      switch (dir) {
        case LEFT -> gameArea[index][i] = line[i];
        case RIGHT -> gameArea[index][i] = line[i];
        case UP -> gameArea[i][index] = line[i];
        case DOWN -> gameArea[i][index] = line[i];
      }
    }
  }

  private static void reverse(int[] arr) {
    for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
      int t = arr[i];
      arr[i] = arr[j];
      arr[j] = t;
    }
  }

  private static int[] mergeLine(int[] line) {
    List<Integer> filtered = new ArrayList<>();
    for (int num : line) {
      if (num != 0)
        filtered.add(num);
    }

    List<Integer> result = new ArrayList<>();
    int i = 0;
    while (i < filtered.size()) {
      if (i + 1 < filtered.size() && canFuse(filtered.get(i), filtered.get(i + 1))) {
        result.add(fuse(filtered.get(i), filtered.get(i + 1)));
        i += 2;
      } else {
        result.add(filtered.get(i));
        i++;
      }
    }

    while (result.size() < 4) {
      result.add(0);
    }

    return result.stream().mapToInt(Integer::intValue).toArray();
  }

  private static boolean canFuse(int a, int b) {
    if (!fibIndex.containsKey(a) || !fibIndex.containsKey(b))
      return false;

    int idxA = fibIndex.get(a);
    int idxB = fibIndex.get(b);

    // Permite 1 + 1 = 2 (índices 0 e 1)
    return (a == 1 && b == 1) || Math.abs(idxA - idxB) == 1;
  }

  private static int fuse(int a, int b) {
    int maxIndex = Math.max(fibIndex.get(a), fibIndex.get(b));
    return fibSeq.get(maxIndex + 1);
  }

  public void spawnNewTile() {
    List<int[]> empty = new ArrayList<>();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (gameArea[y][x] == 0) {
          empty.add(new int[] { y, x });
        }
      }
    }
    if (!empty.isEmpty()) {
      int[] pos = empty.get(new Random().nextInt(empty.size()));
      gameArea[pos[0]][pos[1]] = 1;
    }
  }

  public int[][] getCopyOfBoard() {
    int[][] copy = new int[height][width];
    for (int i = 0; i < height; i++) {
      System.arraycopy(gameArea[i], 0, copy[i], 0, width);
    }
    return copy;
  }

  public boolean hasChanged(int[][] previous) {
    for (int y = 0; y < height; y++) {
      if (!Arrays.equals(previous[y], gameArea[y]))
        return true;
    }
    return false;
  }

  public boolean isGameOver() {
    // Verifica se há espaço vazio
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (gameArea[y][x] == 0)
          return false;

        // Verifica vizinhos para possível fusão
        if (x < width - 1 && canFuse(gameArea[y][x], gameArea[y][x + 1]))
          return false;
        if (y < height - 1 && canFuse(gameArea[y][x], gameArea[y + 1][x]))
          return false;
      }
    }
    return true;
  }
}

public class Solution {
  public static void main(String[] args) {

    int[][] initialBoard = {
        { 1, 0, 2, 3 },
        { 0, 3, 2, 1 },
        { 0, 0, 0, 0 },
        { 0, 5, 3, 5 }
    };

    The2048Bonacci boardGame = new The2048Bonacci(initialBoard);

    Scanner scanner = new Scanner(System.in);

    System.out.println("2048-Bonacci Game Started!");
    System.out.println("Initial Board:");
    System.out.println(boardGame.getDescription());

    while (true) {
      System.out.print("Enter direction (UP, DOWN, LEFT, RIGHT) or EXIT: ");
      String input = scanner.nextLine().trim().toUpperCase();

      if (input.equals("EXIT"))
        break;

      try {
        Direction dir = Direction.valueOf(input);
        int[][] before = boardGame.getCopyOfBoard();
        boardGame.push(dir);

        if (boardGame.hasChanged(before)) {
          boardGame.spawnNewTile();
          
          if (boardGame.isGameOver()) {
            System.out.println("Game Over! No more moves possible.");
            break;
          }

        }

        System.out.println("\nBoard after move " + dir + ":");
        System.out.println(boardGame.getDescription());
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid direction. Try again.");
      }
    }

    System.out.println("Game Over.");

  }
}
