/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Solver
{
    private SearchNode searchnode;
    private boolean flag;
    private int moves;
    public Solver(Board initial)
    {
        if (initial == null)
            throw new java.lang.IllegalArgumentException("Argument cannot be null");

        MinPQ<SearchNode> neighbours = new MinPQ<>();
        neighbours.insert(new SearchNode(initial,0, null));

        MinPQ<SearchNode> twinneighbours = new MinPQ<>();
        twinneighbours.insert(new SearchNode(initial.twin(),0, null));
        int i = 0;

        while (true)
        {
            System.out.println(i++);
            searchnode = neighbours.delMin();
            SearchNode tsn = twinneighbours.delMin();

            if (searchnode.initial.isGoal())
            {
                flag = true;
                moves = searchnode.moves;
                break;
            }

            if (tsn.initial.isGoal())
            {
                flag = false;
                moves = -1;
                break;
            }

                for (Board b : searchnode.initial.neighbors()) {
                    if (searchnode.prev != null && b.equals(searchnode.prev.initial))
                        continue;
                        else
                        neighbours.insert(new SearchNode(b, (searchnode.moves + 1), searchnode));
                }


                for (Board b : tsn.initial.neighbors()) {
                    if (tsn.prev != null && b.equals(tsn.prev.initial))
                        continue;
                        else
                        neighbours.insert(new SearchNode(b, (tsn.moves + 1), tsn));
                }
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board initial;
        private int moves;
        private SearchNode prev;
        private int mp, hp;

        public SearchNode(Board initial, int moves, SearchNode prev) {
            this.initial = initial;
            this.moves = moves;
            this.mp = initial.manhattan();
            this.hp = initial.hamming();
            this.prev = prev;
        }

        public int compareTo(SearchNode n) {
            if ((mp + moves) == (n.mp + n.moves))
            {
                // if ((hp + moves) == (n.hp + n.moves))
                //     return 0;
                // else if ((hp + moves) > (n.hp + n.moves))
                //     return +1;
                // else
                //     return -1;
                return 0;
            }
            else if ((mp + moves) > (n.mp + n.moves))
                return  +1;
            else
                return -1;
        }

    }


    public boolean isSolvable()
    {
        return flag;
    }


    public int moves()
    {
        return moves;
    }


    public Iterable<Board> solution()
    {
        Stack<Board> s = new Stack<Board>();
        while (searchnode.prev != null)
        {
            s.push(searchnode.initial);
            searchnode = searchnode.prev;
        }
        return s;
    }



    public static void main(String[] args) {
        // create initial board from file
        In in = new In(StdIn.readString());
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

