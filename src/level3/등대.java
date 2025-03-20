package level3;

import java.util.*;

public class 등대 {

    public static void main(String[] args) {
        Solution solution = new Solution();
        //solution.solution(8, new int[][]{{1, 2}, {1, 3}, {1, 4}, {1, 5}, {5, 6}, {5, 7}, {5, 8}});
        //solution.solution(10, new int[][]{{4, 1}, {5, 1}, {5, 6}, {7, 6}, {1, 2}, {1, 3}, {6, 8}, {2, 9}, {9, 10}});
        //solution.solution(14, new int[][]{{1,2}, {1,3}, {2,4}, {2,5}, {2,6}, {3,7}, {3,8}, {4,9}, {4,10}, {5,11}, {7,12}, {8,13}, {8,14}});
        solution.solution(8, new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 7}, {7, 8}});
    }

    static class Solution {

        int[][] lighthouse;
        int turnOnLight = 0;
        Map<Integer, List<Integer>> lightConnectionMap = new HashMap<>();
        int[] visitNode;

        public int solution(int n, int[][] lighthouse) {
            this.lighthouse = lighthouse;
            this.visitNode = new int[n+1];

            for (int[] light : lighthouse) {
                List<Integer> left = lightConnectionMap.getOrDefault(light[0], new ArrayList<>());
                List<Integer> right = lightConnectionMap.getOrDefault(light[1], new ArrayList<>());

                left.add(light[1]);
                right.add(light[0]);

                lightConnectionMap.put(light[0], left);
                lightConnectionMap.put(light[1], right);
            }

            findMinLight(1,0);

            System.out.println("this.turnOnLight = " + this.turnOnLight);

            return this.turnOnLight;
        }

        int findMinLight(int dept, int previous){
            List<Integer> connectionList = lightConnectionMap.get(dept);

            if(connectionList.size() == 1 && connectionList.contains(previous)){
                return 1;
            }

            int minLight = 0;
            for (Integer conn : connectionList) {
                if (conn != dept && conn != previous) {
                    minLight += findMinLight(conn, dept);
                }
            }

            if(minLight == 0){
                return 1;
            }

            System.out.println("dept = " + dept);
            this.turnOnLight++;
            return 0;
        }
    }
}
