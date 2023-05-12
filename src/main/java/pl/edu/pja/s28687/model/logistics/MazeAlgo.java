package pl.edu.pja.s28687.model.logistics;

import java.util.*;

public class MazeAlgo {

    public Cell[][] generateMaze(int rows, int cols) {
        CellArray cellArray = new CellArray(rows, cols);
        CellNode[][] cellNodes = cellArray.trimmedMirroredAndFilled();
        Cell[][] cells = new Cell[rows][cols];
        for (int i = 0; i < cellNodes.length; i++) {
            for (int j = 0; j < cellNodes[0].length; j++) {
                CellNode cellNode = cellNodes[i][j];
                if (cellNode.isWall) {
                    cells[i][j] = new Cell(i, j, true);
                } else {
                    cells[i][j] = new Cell(i, j);
                }
            }
        }
        return cells;
    }

    public class CellNode {
        public int x;
        public int y;
        public boolean isWall = false;

        public CellNode(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof CellNode)) return false;
            CellNode c = (CellNode) o;
            return c.x == x && c.y == y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public LinkedList<CellNode> getNeighbours(CellNode[][] cellArray) {
            int startX = x == 0 ? x + 1 : x - 1;
            int endX = x == cellArray.length - 1 ? x - 1 : x + 1;
            int startY = y == 0 ? y + 1 : y - 1;
            int endY = y == cellArray[0].length - 1 ? y - 1 : y + 1;
            LinkedList<CellNode> neighbours = new LinkedList<>();
            for (int i = startX; i <= endX; i++) {
                for (int j = startY; j <= endY; j++) {
                    if (i == x && j == y) continue;
                    if (cellArray[i][j].isWall) continue;
                    neighbours.add(cellArray[i][j]);
                }
            }
            Collections.shuffle(neighbours);
            return neighbours;
        }

        public LinkedList<CellNode> getAdjacentNeighbours(CellNode[][] cellArray) {
            int startX = x == 0 ? x + 1 : x - 1;
            int endX = x == cellArray.length - 1 ? x - 1 : x + 1;
            int startY = y == 0 ? y + 1 : y - 1;
            int endY = y == cellArray[0].length - 1 ? y - 1 : y + 1;
            LinkedList<CellNode> neighbours = new LinkedList<>();
            for (int i = startX; i <= endX; i++) {
                for (int j = startY; j <= endY; j++) {
                    if (i == x && j == y) continue;
                    if (cellArray[i][j].isWall) continue;
                    if (i != x && j != y) continue;
                    neighbours.add(cellArray[i][j]);
                }
            }
            Collections.shuffle(neighbours);
            return neighbours;
        }

    }
        // must have 8 neighbours to be considered marking wall
        public class CellArray {
            private int requestedRows;
            private int requestedCols;
            public CellNode[][] cellNodes;

            public CellArray(int rows, int cols) {
                this.requestedRows = rows;
                this.requestedCols = cols;
                int halfCols = cols / 2;
                cellNodes = new CellNode[rows][halfCols + 1];
                initialize();
                cellNodes[rows / 2][halfCols].isWall = true;
                cellNodes[rows / 2][halfCols - 1].isWall = true;
                cellNodes[rows / 2][halfCols - 2].isWall = true;
                build(cellNodes);
            }

            private void initialize() {
                for (int i = 0; i < cellNodes.length; i++) {
                    for (int j = 0; j < cellNodes[i].length; j++) {
                        cellNodes[i][j] = new CellNode(i, j);
                    }
                }
            }

            public void setCell(int x, int y, CellNode cellNode) {
                cellNodes[x][y] = cellNode;
            }

            public CellNode getCell(int x, int y) {
                return cellNodes[x][y];
            }

            public void build(CellNode[][] cellArray) {
                int x = cellArray.length;
                int y = cellArray[0].length;

                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        CellNode cellNode = cellArray[i][j];
                        if (cellNode.isWall) continue;
                        int maxBlocks = (int) (Math.random() * 3) + 2;
//                        int maxBlocks = 3;
                        List<CellNode> neighbours = recursiveNeighbourFind(cellNode, cellArray, new LinkedList<>(), maxBlocks, new HashSet<>());
                        if (neighbours.size() >= 2) {
                            for (CellNode c : neighbours) {
                                c.isWall = true;
                            }
                        }
                    }
                }
            }

            private List<CellNode> recursiveNeighbourFind(CellNode cellNode, CellNode[][] cellArray, List<CellNode> blocks, int limit, Set<CellNode> visited) {
                if (blocks.size() == limit) return blocks;
                if (cellNode.getNeighbours(cellArray).size() == 8 && !visited.contains(cellNode)) {
                    blocks.add(cellNode);
                    visited.add(cellNode);
                }
                if (blocks.contains(cellNode)) {
                    for (CellNode neighbour : cellNode.getAdjacentNeighbours(cellArray)) {
                        if (visited.contains(neighbour)) continue;
                        recursiveNeighbourFind(neighbour, cellArray, blocks, limit, visited);
                    }
                }
                return blocks;
            }

            public CellNode[][] trimmed() {
                return cellNodes;
//                CellNode[][] trimmed = new CellNode[cellNodes.length][cellNodes[0].length - 1];
//                for (int i = 0; i < cellNodes.length; i++) {
//                    for (int j = 0; j < cellNodes[i].length - 1; j++) {
//                        trimmed[i][j] = cellNodes[i][j];
//                    }
//                }
//                return trimmed;
            }

            public CellNode[][] trimmedMirroredAndFilled() {
                CellNode[][] trimmed = trimmed();
                CellNode[][] mirrored = new CellNode[requestedRows][requestedCols];

                for (int i = 0; i < requestedRows; i++) {
                    for (int j = 0; j < requestedCols / 2 + requestedCols % 2; j++) {
                        mirrored[i][j] = trimmed[i][j];
                        mirrored[i][mirrored[i].length - 1 - j] = trimmed[i][j];
                    }
                }

                List<CellNode> toFill = new LinkedList<>();
                for (int i = 0; i < mirrored.length; i++) {
                    for (int j = 0; j < mirrored[i].length; j++) {
                        if (mirrored[i][j].getNeighbours(mirrored).size() > 7) {
//                            mirrored[i][j].isWall = true;
                            toFill.add(mirrored[i][j]);
                        }
                    }
                }
                for (CellNode c : toFill) {
                    c.isWall = true;
                    System.out.println("filling");
                }
                return mirrored;
            }
        }
    }

