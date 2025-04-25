package level2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class 교점에별만들기 {
    //https://school.programmers.co.kr/learn/courses/30/lessons/87377
    public static void main(String[] args) {
        //3,8 testCase Fail
        //26 memory over
        Solution solution = new Solution();
        //solution.solution(new int[][]{{1, -1, 0}, {2, -1, 0}});
        solution.solution(new int[][]{{2, -1, 4}, {-2, -1, 4}, {0, -1, 1}, {5, -8, -12}, {5, 8, 12}});
        //solution.solution(new int[][]{{0, 1, -1},{1, 0, -1},{1, 0, 1}});
    }

    static class Solution {

        public String[] solution(int[][] line) {
            List<long[]> starLocation = new ArrayList<>();

            long MAX_X = 0;
            long MAX_Y = 0;
            long MIN_X = 0;
            long MIN_Y = 0;

            for (int i = 0; i < line.length-1; i++) {
                double A_X = line[i][0];
                double A_Y = line[i][1];
                double A_C = line[i][2];
                for(int j = i+1; j < line.length; j++) {
                    double B_X = line[j][0];
                    double B_Y = line[j][1];
                    double B_C = line[j][2];

                    if (A_X * B_Y - B_X * A_Y == 0) continue;

                    double CAC_X = (A_Y * B_C - B_Y * A_C) / (A_X * B_Y - B_X * A_Y);
                    double CAC_Y = (B_X * A_C - A_X * B_C) / (A_X * B_Y - B_X * A_Y);

                    if(isInteger(CAC_X) && isInteger(CAC_Y)) {
                        starLocation.add(new long[]{(long) CAC_Y, (long) CAC_X});
                        if(MAX_X < CAC_X) MAX_X = (long) CAC_X;
                        if(MAX_Y < CAC_Y) MAX_Y = (long) CAC_Y;
                        if(MIN_X > CAC_X || (i == 0 && j == 1)) MIN_X = (long) CAC_X;
                        if(MIN_Y > CAC_Y || (i == 0 && j == 1)) MIN_Y = (long) CAC_Y;
                    }
                }
            }

            MAX_X = MAX_X - MIN_X;
            MAX_Y = MAX_Y - MIN_Y;
            for(long[] x : starLocation){
                x[1] = x[1] - MIN_X;
                x[0] = x[0] - MIN_Y;
            }

            String[] answer = new String[(int) (MAX_Y+1)];
            StringBuilder star = new StringBuilder();
            for(int i=0; i <= MAX_X; i++) {
                star.append(".");
            }

            for(int i=0; i <= MAX_Y; i++) {
                answer[i] = star.toString();
            }

            for(long[] x : starLocation){
                String strValue = answer[(int) x[0]];
                String beforeStr = strValue.substring(0, (int) x[1]) + '*';
                String afterStr = x[1] == MAX_X ? "" : strValue.substring((int) (x[1] + 1));
                answer[(int) x[0]] = beforeStr + afterStr;
            }

            List<String> reverse = Arrays.asList(answer);
            Collections.reverse(reverse);
            answer = reverse.toArray(answer);

            for (String x : answer) {
                System.out.println("x = " + x);
            }


            return answer;
        }

        public boolean isInteger(double num) {
            return num % 1 == 0.0;
        }
    }
}

