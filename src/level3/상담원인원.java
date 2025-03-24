package level3;

import java.util.*;

public class 상담원인원 {
    //https://school.programmers.co.kr/learn/courses/30/lessons/214288
    public static void main(String[] args) {
        Solution solution = new Solution();
        //solution.solution(3, 5, new int[][]{{10, 60, 1}, {15, 100, 3}, {20, 30, 1}, {30, 50, 3}, {50, 40, 1}, {60, 30, 2}, {65, 30, 1}, {70, 100, 2}});
        //solution.solution(2, 3, new int[][]{{5, 55, 2}, {10, 90, 2}, {20, 40, 2}, {50, 45, 2}, {100, 50, 2}});
        solution.solution(2, 3, new int[][]{{0,100,1},{5,100,2},{10,100,1},{15,30,2},{20,100,1},{45,10,2},{60,5,2}});
    }

    static class Solution{
        public int solution(int k, int n, int[][] reqs) {
            Map<Integer, List<int[]>> reqsType = new HashMap<>();
            int[][] waitingTimeByType = new int[k][n];

            for (int i = 1; i <= k; i++) {
                List<int[]> temp = new ArrayList<>();
                reqsType.put(i, temp);
            }

            for (int[] req : reqs) {
                int c = req[2];
                reqsType.get(c).add(req);
            }

            for (int e = 1; e <= n; e++) {
                for (int i = 1; i <= k; i++) {
                    int waitingTime = 0;
                    int[] mentorEndTime = new int[e];
                    for (int[] req : reqsType.get(i)) {
                        int startTime = req[0];
                        int requestTime = req[1];
                        int lessEndTimeMentorIndex = 0;
                        int lessEndTimeMentorVaule = Integer.MAX_VALUE;
                        boolean findMentor = false;

                        for (int j = 0; j < e; j++) {
                            if (mentorEndTime[j] < startTime) {
                                mentorEndTime[j] = startTime + requestTime;
                                findMentor = true;
                                break;
                            } else if (mentorEndTime[j] == startTime) {
                                mentorEndTime[j] = mentorEndTime[j] + requestTime;
                                findMentor = true;
                                break;
                            } else {
                                if (mentorEndTime[j] < lessEndTimeMentorVaule) {
                                    lessEndTimeMentorVaule = mentorEndTime[j];
                                    lessEndTimeMentorIndex = j;
                                }
                            }
                        }

                        if (!findMentor) {
                            waitingTime = waitingTime + (mentorEndTime[lessEndTimeMentorIndex] - startTime);
                            mentorEndTime[lessEndTimeMentorIndex] = mentorEndTime[lessEndTimeMentorIndex] + requestTime;
                        }

                    }
                    waitingTimeByType[i-1][e-1] = waitingTime;
                }
            }

            int leftMentor = n - k;
            int[] allCase = new int[leftMentor+1];
            for(int i = 0; i <= leftMentor; i++){
                allCase[i] = i;
            }

            Stack<Integer> stack = new Stack<>();
            List<Integer[]> numberOfCase = new ArrayList<>();
            settingNumberOfCase(leftMentor+1, k, stack, allCase, numberOfCase);

            int minTotalValue = Integer.MAX_VALUE;
            int minIndex = 0;
            int checkIndex = 0;
            for (Integer[] isCase : numberOfCase){
                int minValue = 0;
                int checkMax = 0;
                for (int j = 0; j < k; j++) {
                    minValue += waitingTimeByType[j][isCase[j]];
                    checkMax += isCase[j];
                }

                if(checkMax == leftMentor && minTotalValue > minValue){
                    minTotalValue = minValue;
                    minIndex = checkIndex;
                }
                checkIndex++;
            }

            int answer = 0;

            for(int i = 0; i < k; i++){
                answer += waitingTimeByType[i][numberOfCase.get(minIndex)[i]];
            }

            return answer;
        }

        void settingNumberOfCase(int number, int size, Stack<Integer> stack, int[] arr, List<Integer[]> numberOfCase) {
            if(stack.size() == size){
                numberOfCase.add(stack.toArray(new Integer[size]));
                return;
            }

            for(int i = 0; i < number; i++){
                stack.push(arr[i]);
                settingNumberOfCase(number, size, stack, arr, numberOfCase);
                stack.pop();
            }
        }
    }
}
