import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF fullArea;
    private WeightedQuickUnionUF percArea;
    private int dimension;
    private boolean[][] openSpots;
    private int openCount;
    private int topIndex;
    private int bottomIndex;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("The argument of " + N + " must be bigger than 0");
        }
        fullArea = new WeightedQuickUnionUF(N * N + 1);
        percArea = new WeightedQuickUnionUF(N * N + 2);
        dimension = N;
        openSpots = new boolean[N][N];
        openCount = 0;
        topIndex = N * N;
        bottomIndex = topIndex + 1;
        for (int i = 0; i < N; i++) {
            fullArea.union(topIndex, i);
            percArea.union(topIndex, i);
        }
        for (int i = N * N - N; i < N * N; i++) {
            percArea.union(bottomIndex, i);
        }
    }

    public void open(int row, int col) {
        if (row >= dimension || col >= dimension) {
            throw new IndexOutOfBoundsException("either row: " + row + " or col: " + col + " is not in range");
        }
        if (isOpen(row, col)) {
            return;
        }
        int areaCode = rowAndColToNumber(row, col);
        openCount++;
        openSpots[row][col] = true;
        if (dimension == 1) {
            return;
        } else if (row == 0 && col == 0) {
            if (isOpen(row + 1, col)) {
                fullArea.union(areaCode, rowAndColToNumber(row + 1, col));
                percArea.union(areaCode, rowAndColToNumber(row + 1, col));
            }
            if (isOpen(row, col + 1)) {
                fullArea.union(areaCode, rowAndColToNumber(row, col + 1));
                percArea.union(areaCode, rowAndColToNumber(row, col + 1));
            }
        } else if (row == dimension - 1 && col == 0) {
            if (isOpen(row - 1, col)) {
                fullArea.union(areaCode, rowAndColToNumber(row - 1, col));
                percArea.union(areaCode, rowAndColToNumber(row - 1, col));
            }
            if (isOpen(row, col + 1)) {
                fullArea.union(areaCode, rowAndColToNumber(row, col + 1));
                percArea.union(areaCode, rowAndColToNumber(row, col + 1));
            }
        } else if (row == 0 && col == dimension - 1) {
            if (isOpen(row + 1, col)) {
                fullArea.union(areaCode, rowAndColToNumber(row + 1, col));
                percArea.union(areaCode, rowAndColToNumber(row + 1, col));
            }
            if (isOpen(row, col - 1)) {
                fullArea.union(areaCode, rowAndColToNumber(row, col - 1));
                percArea.union(areaCode, rowAndColToNumber(row, col - 1));
            }
        } else if (row == dimension - 1 && col == dimension - 1) {
            if (isOpen(row - 1, col)) {
                fullArea.union(areaCode, rowAndColToNumber(row - 1, col));
                percArea.union(areaCode, rowAndColToNumber(row - 1, col));
            }
            if (isOpen(row, col - 1)) {
                fullArea.union(areaCode, rowAndColToNumber(row, col - 1));
                percArea.union(areaCode, rowAndColToNumber(row, col - 1));
            }
        } else if (row == 0 || row == dimension - 1 || col == 0 || col == dimension - 1) {
            borderCasesOpen(row, col, areaCode);
        } else {
            if (isOpen(row + 1, col)) {
                fullArea.union(areaCode, rowAndColToNumber(row + 1, col));
                percArea.union(areaCode, rowAndColToNumber(row + 1, col));
            }
            if (isOpen(row - 1, col)) {
                fullArea.union(areaCode, rowAndColToNumber(row - 1, col));
                percArea.union(areaCode, rowAndColToNumber(row - 1, col));
            }
            if (isOpen(row, col + 1)) {
                fullArea.union(areaCode, rowAndColToNumber(row, col + 1));
                percArea.union(areaCode, rowAndColToNumber(row, col + 1));
            }
            if (isOpen(row, col - 1)) {
                fullArea.union(areaCode, rowAndColToNumber(row, col - 1));
                percArea.union(areaCode, rowAndColToNumber(row, col - 1));
            }
        }

    }

    public boolean isOpen(int row, int col) {
        if (row >= dimension || col >= dimension) {
            throw new IndexOutOfBoundsException("row: " + row + " or col: " + col + " is not in range");
        }

        return openSpots[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row >= dimension || col >= dimension) {
            throw new IndexOutOfBoundsException("either row: " + row + " or col: " + col + " is not in range");
        }
        if (isOpen(row, col)) {
            return fullArea.find(rowAndColToNumber(row, col)) == fullArea.find(topIndex);
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        if (dimension == 1 && !isOpen(0, 0)) {
            return false;
        }
        return percArea.find(topIndex) == percArea.find(bottomIndex);
    }

    private int rowAndColToNumber(int row, int col) {
        return dimension * row + col;
    }
    private void borderCasesOpen(int row, int col, int areaC) {
        if (row == 0) {
            if (isOpen(row + 1, col)) {
                fullArea.union(areaC, rowAndColToNumber(row + 1, col));
                percArea.union(areaC, rowAndColToNumber(row + 1, col));
            }
            if (isOpen(row, col + 1)) {
                fullArea.union(areaC, rowAndColToNumber(row, col + 1));
                percArea.union(areaC, rowAndColToNumber(row, col + 1));
            }
            if (isOpen(row, col - 1)) {
                fullArea.union(areaC, rowAndColToNumber(row, col - 1));
                percArea.union(areaC, rowAndColToNumber(row, col - 1));
            }
        } else if (row == dimension - 1) {
            if (isOpen(row - 1, col)) {
                fullArea.union(areaC, rowAndColToNumber(row - 1, col));
                percArea.union(areaC, rowAndColToNumber(row - 1, col));
            }
            if (isOpen(row, col + 1)) {
                fullArea.union(areaC, rowAndColToNumber(row, col + 1));
                percArea.union(areaC, rowAndColToNumber(row, col + 1));
            }
            if (isOpen(row, col - 1)) {
                fullArea.union(areaC, rowAndColToNumber(row, col - 1));
                percArea.union(areaC, rowAndColToNumber(row, col - 1));
            }
        } else if (col == 0) {
            if (isOpen(row + 1, col)) {
                fullArea.union(areaC, rowAndColToNumber(row + 1, col));
                percArea.union(areaC, rowAndColToNumber(row + 1, col));
            }
            if (isOpen(row - 1, col)) {
                fullArea.union(areaC, rowAndColToNumber(row - 1, col));
                percArea.union(areaC, rowAndColToNumber(row - 1, col));
            }
            if (isOpen(row, col + 1)) {
                fullArea.union(areaC, rowAndColToNumber(row, col + 1));
                percArea.union(areaC, rowAndColToNumber(row, col + 1));
            }
        } else {
            if (isOpen(row + 1, col)) {
                fullArea.union(areaC, rowAndColToNumber(row + 1, col));
                percArea.union(areaC, rowAndColToNumber(row + 1, col));
            }
            if (isOpen(row - 1, col)) {
                fullArea.union(areaC, rowAndColToNumber(row - 1, col));
                percArea.union(areaC, rowAndColToNumber(row - 1, col));
            }
            if (isOpen(row, col - 1)) {
                fullArea.union(areaC, rowAndColToNumber(row, col - 1));
                percArea.union(areaC, rowAndColToNumber(row, col - 1));
            }
        }
    }
}
