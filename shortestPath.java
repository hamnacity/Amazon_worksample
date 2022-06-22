import java.util.LinkedList;
import java.awt.Point;
// I used the idea of a breadth-first search to get the shortest path
public class shortestPath {
    int numofobsiticles=0;

        private static final boolean DEBUG = false;


        public Point[] findRoute(int[][] map, Point startingPosition, Point deliveryPoint) {
            if (isOutOfMap(map, startingPosition)) {

                return null;

            }
            if (isOutOfMap(map, deliveryPoint)) {
                System.out.println("out of bound");

                return null;
            }
            if (isBlocked(map, startingPosition)) {
               // System.out.println("unable to reach destination");
                return null;
            }
            if (isBlocked(map, deliveryPoint)) {
               System.out.println("unable to reach delivery point");
                return null;


            }
            LinkedList<Point> queue1 = new LinkedList<>();
            LinkedList<Point> queue2 = new LinkedList<>();

            queue1.add(startingPosition);
            map[startingPosition.y][startingPosition.x] = -1;
            int stepCount = 2;
            while (!queue1.isEmpty()) {
                if(queue1.size() >= map.length * map[0].length){
                    throw new Error("Map overloaded");
                }
                for (Point point : queue1) {
                    if (point.x == deliveryPoint.x && point.y == deliveryPoint.y) {
                        Point[] optimalPath = new Point[stepCount - 1];
                        makeSolution(map, point.x, point.y, stepCount - 1, optimalPath);
                        resetMap(map);
                        System.out.println("number of steps:");
                        System.out.println(stepCount);
                        System.out.println("Route:");
                        return optimalPath;
                    }
                    LinkedList<Point> finalQueue = queue2;
                    int finalStepCount = stepCount;
                    lookAround(map, point, (x, y) -> {
                        if (isBlocked(map, x, y)) {
                            return;
                        }
                        Point e = new Point(x, y);

                        finalQueue.add(e);
                        map[e.y][e.x] = -finalStepCount;
                    });
                }

                if (DEBUG) {
                    printMap(map);
                }

                queue1 = queue2;
                queue2 = new LinkedList<>();
                stepCount++;
            }
            resetMap(map);
            return null;
        }

        private void resetMap(int[][] map) {
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[0].length; x++) {
                    if (map[y][x] < 0) {
                        map[y][x] = 0;
                    }
                }
            }
        }

        private boolean isBlocked(int[][] map, Point p) {
            return isBlocked(map, p.x, p.y);
        }

        private boolean isBlocked(int[][] map, int x, int y) {
            int i = map[y][x];

            return i < 0 || i == 1;
        }

        private void printMap(int[][] map) {
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0, mapLength = map.length; i < mapLength; i++) {
                int[] aMap = map[i];
                for (int x = 0; x < map[0].length; x++) {
                    System.out.print(aMap[x] + "\t");
                }
                System.out.println();
            }

        }

        private void makeSolution(int[][] map, int x, int y, int stepCount, Point[] bestPath) {
            if (isOutOfMap(map, x, y) || map[y][x] == 0) {
                return;
            }

            if ( -stepCount != map[y][x]) {
                return;
            }

            Point p = new Point(x, y);
            bestPath[stepCount - 1] = p;
            lookAround(map, p, (x1, y1) -> makeSolution(map, x1, y1, stepCount - 1, bestPath));
        }


        private void lookAround(int[][] map, Point p, Call call) {
            call.look(map, p.x + 1, p.y + 1);
            call.look(map, p.x - 1, p.y + 1);
            call.look(map, p.x - 1, p.y - 1);
            call.look(map, p.x + 1, p.y - 1);
            call.look(map, p.x + 1, p.y);
            call.look(map, p.x - 1, p.y);
            call.look(map, p.x, p.y + 1);
            call.look(map, p.x, p.y - 1);
        }


        private static boolean isOutOfMap(int[][] map, Point p) {
            return isOutOfMap(map, p.x, p.y);
        }

        private static boolean isOutOfMap(int[][] map, int x, int y) {
            //int numofonstacle=0;
            if (x < 0 || y < 0) {




                return true;

            }
            //numofonstacle= numofonstacle+1;
            //System.out.println(numofonstacle);


            return map.length <= y || map[0].length <= x;
        }


        private interface Call {
            default void look(int[][] map, int x, int y) {
                if (isOutOfMap(map, x, y)) {
                    return;
                }
                onLook(x, y);
            }

            void onLook(int x, int y);
        }

    public static void main(String... args) {
            // 1 represent obstacle
        int[][] Map = {
                {0, 0, 0, 0, 1, 0, 0, 1, 0,0},
                {0, 1, 0, 1, 0, 0, 1, 0, 0,1},
                {0, 0, 0, 0, 0, 0, 0, 1, 0,0},
                {0, 0, 0, 1, 1, 1, 0, 0, 1,0},
                {0, 1, 0, 0, 0, 1, 0, 0, 0,1},
                {0, 0, 0, 0, 1, 0, 0, 0, 0,0},
                {1, 0, 1, 0, 0, 1, 0, 1, 1,0},
                {1, 0, 1, 0, 0, 1, 0, 0, 0,0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0,0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0,0},
        };

        Point[] route = new shortestPath().findRoute(Map, new Point(0, 0), new Point(9, 9));
        for (Point point : route) {
            System.out.println(point.x + ", " + point.y);

        }
    }
}
