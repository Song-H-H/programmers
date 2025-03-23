package level3;

import java.util.*;

public class 부대복귀 {

    public static void main(String[] args) {
        Solution solution = new Solution();
        //solution.solution(3, new int[][]{{1, 2}, {2, 3}}, new int[]{2, 3}, 1); //1, 2
        //solution.solution(5, new int[][]{{1, 2}, {1, 4}, {2, 4}, {2, 5}, {4, 5}}, new int[]{1, 3, 5}, 5); //2 -1 0
        //solution.solution(4, new int[][]{{1, 2}, {2, 3}, {3, 4}}, new int[]{1}, 3); //31
        //solution.solution(13, new int[][]{{1, 2}, {2, 4}, {6, 4}, {6, 7}, {8, 7}, {7, 1}, {1, 3}, {3, 5}, {1, 10}, {10, 11}, {12, 11}, {12, 13}, {13, 5}}, new int[]{1}, 6); //2
        solution.solution(100000, new int[][]{{1, 2}, {2, 4}, {6, 4}, {6, 7}, {8, 7}, {7, 1}, {1, 3}, {3, 5}, {1, 10}, {10, 11}, {12, 11}, {12, 13}, {13, 5}}, new int[]{1}, 6); //1
    }

    static class Solution {

        Map<Integer, List<Integer>> roadMap = new HashMap<>();
        int[] roadCount;
        int destination;
        int[] previous;

        public int[] solution(int n, int[][] roads, int[] sources, int destination) {
            this.destination = destination;
            this.roadCount = new int[n + 1];
            this.previous = new int[n + 1];

            for (int[] road : roads) {
                List<Integer> left = roadMap.getOrDefault(road[0], new ArrayList<>());
                List<Integer> right = roadMap.getOrDefault(road[1], new ArrayList<>());

                left.add(road[1]);
                right.add(road[0]);

                roadMap.put(road[0], left);
                roadMap.put(road[1], right);
            }

            findRoad(Collections.singletonList(new int[]{destination, 0}));

            int[] answer = new int[sources.length];
            for(int i = 0; i < sources.length; i++){

                int move = roadCount[sources[i]];
                if(move == 0) move = -1;
                if(destination == sources[i]) move = 0;

                answer[i] = move;
            }

            return answer;
        }

        void findRoad(List<int[]> dataList){
            List<int[]> reSearch = new ArrayList<>();

            for (int[] data : dataList) {
                int location = data[0];
                int count = data[1];

                if(previous[location] == 1) continue;
                else previous[location] = 1;

                if(roadCount[location] == 0) roadCount[location] = count;
                else roadCount[location] = Math.min(roadCount[location], count);
                if(roadCount[location] > 0 && roadCount[location] < count) continue;

                List<Integer> nearbyRoadList = roadMap.getOrDefault(location, new ArrayList<>());
                if(nearbyRoadList.isEmpty()) continue;
                if(nearbyRoadList.size() == 1 && previous[nearbyRoadList.get(0)] == 1) continue;

                for (int nearbyRoad : nearbyRoadList) {
                    if (nearbyRoad != location && previous[nearbyRoad] == 0) {
                        reSearch.add(new int[]{nearbyRoad, count+1});
                    }
                }
            }

            if(!reSearch.isEmpty()) findRoad(reSearch);
        }
    }
}
