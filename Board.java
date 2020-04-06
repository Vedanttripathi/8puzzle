/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board
{
    private final int[][] tiles;
    private int n = 0;

    public Board(int[][] tiles) {
        this.tiles = copyof(tiles, tiles.length);
        n = dimension();
    }

    // private Board(int t[][], int m)
    // {
    //     this.tiles = copyof(t, t.length);
    //     this.moves = m;
    // }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public int dimension()
    {
        return tiles.length;
    }

    public int hamming()
    {
        int c = 0, h = 0;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                ++c;
                if (tiles[i][j] == 0) {
                    continue;
                }
                else if (tiles[i][j] != c) {
                    h++;
                }
            }
        }
        return h;
    }

    public int manhattan()
    {
        int c = 1, row = 0, col = 0, m = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n-1 && j == n-1) {
                    continue;
                }
                else if (tiles[i][j] == c) {
                    c++;
                    continue;
                }
                else {
                    task:
                    for (int k = 0; k < n; k++) {
                        for (int t = 0; t < n; t++) {
                            if (tiles[k][t] == c) {
                                c++;
                                row = k;
                                col = t;
                                break task;
                            }
                        }
                    }
                    if (row > i)
                        row = row - i;
                    else
                        row = i - row;
                    if (col > j)
                        col = col - j;
                    else
                        col = j - col;
                    m += row + col;
                }
            }
        }
        return m;
    }

    public boolean isGoal()
    {
        // int t[][] = new int[n][n];
        // int c = 1;
        // for (int i = 0; i < n; i++)
        // {
        //     for (int j = 0; j < n; j++)
        //     {
        //         if (i == n-1 && j == n-1)
        //             break;
        //         t[i][j] = c++;
        //     }
        // }
        // return Arrays.deepEquals(this.tiles, t);
        return hamming() == 0;
    }

    public boolean equals(Object y)
    {
        if (y == null)
            return false;
        if (y == this)
            return true;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    private int[] locOf()
    {
        int a[] = new int[2];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (tiles[i][j] == 0)
                {
                    a[0] = i;
                    a[1] = j;
                    break;
                }
            }
        }
        return a;
    }

    public Iterable<Board> neighbors()
    {
        int []p = locOf();
        int x = p[0];
        int y = p[1];
        int t[][] = new int [n][n];
        t = copyof(this.tiles, n);
        Stack<Board> s = new Stack<Board>();
        if (x != 0)
        {
            exchg(t, x, y, x-1, y);
            s.push(new Board(t));
            exchg(t, x, y, x-1, y);
        }
        if (y != 0)
        {
            exchg(t, x, y, x, y-1);
            s.push(new Board(t));
            exchg(t, x, y, x, y-1);
        }
        if (x != n - 1)
        {
            exchg(t, x, y, x+1, y);
            s.push(new Board(t));
            exchg(t, x, y, x+1, y);
        }
        if (y != n - 1)
        {
            exchg(t, x, y, x, y+1);
            s.push(new Board(t));
            exchg(t, x, y, x, y+1);
        }
        return s;
    }


    private void exchg(int matrix[][], int i, int j, int k, int l)
    {
        int temp = matrix[i][j];
        matrix[i][j] = matrix[k][l];
        matrix[k][l] = temp;
    }


    private int[][] copyof(int a[][], int size)
    {
        int b[][] = new int[size][size];
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                b[i][j] = a[i][j];
            }
        }
        return b;
    }

    public Board twin()
    {
        int x1 = 0, y1 = 0, x2 = 0, y2 = 1, temp = 0;
        boolean f = true;
        Board b = new Board(copyof(this.tiles, n));
        int []p = locOf();
        if (tiles[x1][y1] == 0) {
                x1++;
            }
        if (tiles[x2][y2] == 0) {
            x2++;
        }
        temp = b.tiles[x1][y1];
        b.tiles[x1][y1] = b.tiles[x2][y2];
        b.tiles[x2][y2] = temp;
        return b;
    }


    public static void main(String[] args) {
        int a[][] = {{1, 2, 3}, {4, 0, 5}, {6, 7, 8}};
        // int a[][] = {{1, 2}, {0, 3}};
        Board b = new Board(a);
        System.out.println("Original board : \n" + b);
        System.out.println("Hamming value : "+b.hamming());
        System.out.println("Manhattan value : "+b.manhattan());
        System.out.println("Twin  \n"+b.twin());
        int p[] = b.locOf();
        System.out.println("Location of blank tile : ["+p[0] + ", " + p[1]+"]");
        System.out.println("Neighbours : ");
        for (Board board : b.neighbors())
            StdOut.println(board);


    }
}
