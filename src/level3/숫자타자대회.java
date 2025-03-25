package level3;

import java.util.*;

public class 숫자타자대회 {
    //https://school.programmers.co.kr/learn/courses/30/lessons/136797
    public static void main(String[] args) {
        Solution solution = new Solution();
        //solution.solution("1756");
        //solution.solution("5123");
        //solution.solution("5321");
        //solution.solution("51238");
        //solution.solution("0202");
        //solution.solution("023692");
        //solution.solution("0172");
        //solution.solution("9037173");
        //solution.solution("703936");
        solution.solution("709876");

    }

    static class Solution {

        public final int[][] cost = {
                { 1, 7, 6, 7, 5, 4, 5, 3, 2, 3 },
                { 7, 1, 2, 4, 2, 3, 5, 4, 5, 6 },
                { 6, 2, 1, 2, 3, 2, 3, 5, 4, 5 },
                { 7, 4, 2, 1, 5, 3, 2, 6, 5, 4 },
                { 5, 2, 3, 5, 1, 2, 4, 2, 3, 5 },
                { 4, 3, 2, 3, 2, 1, 2, 3, 2, 3 },
                { 5, 5, 3, 2, 4, 2, 1, 5, 3, 2 },
                { 3, 4, 5, 6, 2, 3, 5, 1, 2, 4 },
                { 2, 5, 4, 5, 3, 2, 3, 2, 1, 2 },
                { 3, 6, 5, 4, 5, 3, 2, 4, 2, 1 }
        };

        public String[] splitNumber;
        public int[][][] dp;

        public int solution(String numbers) {

            int numbersSize = numbers.length();
            dp = new int[numbersSize][10][10];
            splitNumber = numbers.split("");

            int lowCost = findLowCost(0, 4, 6);
            return lowCost;
        }

        public int findLowCost(int dept, int left, int right){
            if(dept >= splitNumber.length ) return 0;
            if(dp[dept][left][right] != 0) return dp[dept][left][right];

            int targetNumber = Integer.parseInt(splitNumber[dept]);
            int moveLeftHand = Integer.MAX_VALUE;
            int moveRightHand = Integer.MAX_VALUE;

            if(right != targetNumber){
                moveLeftHand = findLowCost(dept+1, targetNumber, right) + cost[left][targetNumber];
            }

            if(left != targetNumber){
                moveRightHand = findLowCost(dept+1, left, targetNumber) + cost[right][targetNumber];;
            }
            int lowCost = Math.min(moveLeftHand, moveRightHand);

            this.dp[dept][left][right] = lowCost;

            return lowCost;
        }
    }
}
