package level3;

import java.util.*;

public class 인사고과 {
    //https://school.programmers.co.kr/learn/courses/30/lessons/152995
    public static void main(String[] args) {

        Solution solution = new Solution();
        solution.solution(new int[][] {{2,2},{1,4},{3,2},{3,2},{2,1}});
        //solution.solution(new int[][] {{1,1},{1,4},{3,2},{3,2},{2,1}});
        //solution.solution(new int[][] {{3,2},{1,4},{3,2},{3,2},{2,1}});
        //solution.solution(new int[][] {{3,2}});
        //solution.solution(new int[][] {{3,2},{2,3},{2,3},{2,3},{3,2},{3,2}});
        //solution.solution(new int[][] {{1,3},{1,3},{2,3},{2,3},{3,3},{3,3},{4,3},{14,3},{15,3},{10,3},{13,3}});
        //solution.solution(new int[][] {{7,3},{1,3},{2,3},{2,3},{3,3},{3,3},{4,3},{14,3},{15,3},{10,3},{13,3}});
        //solution.solution(new int[][] {{3,2},{2,3},{3,2},{2,3}});
        //solution.solution(new int[][] {{1,0},{0,0},{2,1},{0,1}});
        //solution.solution(new int[][] {{4,3}, {5,2}, {5,1}, {4,5}, {4,4}});
        solution.solution(new int[][] {{7, 1}, {6, 6}, {5, 4}, {5, 4}, {6, 6}});

    }

    static class Solution{

        public int solution(int[][] scores) {
            SortedMap<Integer, Integer> keyMaxValue = new TreeMap<>();
            Map<String, String> passedMap = new HashMap<>();
            Map<String, String> failMap = new HashMap<>();
            int targetKey = scores[0][0];
            int targetValue = scores[0][1];
            int answer = 1;

            for (int[] score : scores) {
                int a = score[0];
                int b = score[1];

                Integer value = keyMaxValue.getOrDefault(a, 0);
                value = Math.max(value, b);

                keyMaxValue.put(a, value);
            }

            for (int[] score : scores) {
                int aKey = score[0];
                int aValue = score[1];
                boolean addList = true;

                String stringKeyValue = Integer.toString(aKey) + Integer.toString(aValue);
                if(passedMap.containsKey(stringKeyValue)){
                    if(targetKey + targetValue < aKey + aValue){
                        answer++;
                    }
                    continue;
                }
                if(failMap.containsKey(stringKeyValue)){
                    continue;
                }

                for(Map.Entry<Integer, Integer> list : keyMaxValue.tailMap(aKey).entrySet()){
                    Integer bKey = list.getKey();
                    int bValue = list.getValue();

                    if(aKey < bKey && aValue < bValue){
                        addList = false;
                        failMap.putIfAbsent(stringKeyValue, stringKeyValue);

                        if (aKey == targetKey && aValue == targetValue) {
                            return -1;
                        }
                    }
                }

                if (addList) {
                    passedMap.putIfAbsent(stringKeyValue, stringKeyValue);

                    if(targetKey + targetValue < aKey + aValue){
                        answer++;
                    }
                }
            }

            return answer;
        }
    }
}
