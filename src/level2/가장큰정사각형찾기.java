package level2;

public class 가장큰정사각형찾기 {

    public static void main(String[] args) {
        Solution solution = new Solution();
        //solution.solution(new int[][] {{0,1,1,1},{1,1,1,1},{1,1,1,1},{0,0,1,0}});
        //solution.solution(new int[][] {{0,0,1,1},{1,1,1,1}});
        //solution.solution(new int[][] {{0,0,0,0}});
        solution.solution(new int[][] {{0,0,0,1}});
    }

    static class Solution {

        public int solution(int [][]board) {
            int[][] direction = new int[][]{{-1, 0},{0, -1},{-1, -1}};
            int maxValue = 0;

            for (int y= 0; y < board.length; y++) {
                for (int x = 0; x < board[0].length; x++) {
                    if(board[y][x] == 0) continue;

                    int value1 = 0 <= y + direction[0][0] && 0 <= x + direction[0][1] ? board[y + direction[0][0]][x + direction[0][1]] : 0;
                    int value2 = 0 <= y + direction[1][0] && 0 <= x + direction[1][1] ? board[y + direction[1][0]][x + direction[1][1]] : 0;
                    int value3 = 0 <= y + direction[2][0] && 0 <= x + direction[2][1] ? board[y + direction[2][0]][x + direction[2][1]] : 0;

                    int minValue = Math.min(value1, value2);
                    minValue = Math.min(minValue, value3);

                    board[y][x] = minValue +1;
                    maxValue = Math.max(maxValue, minValue +1);
                }
            }

            System.out.println("maxValue = " + maxValue);

            return maxValue * maxValue;
        }

    }
}
