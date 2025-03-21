package level3;

public class 미로탈출명령어 {

    public static void main(String[] args) {
        Solution solution = new Solution();
        String solution1 = solution.solution(3, 4, 2, 3, 3, 1, 5);

        System.out.println("solution1 = " + solution1);

    }

    static class Solution {

        private String DOWN = "d";
        private String UP = "up";
        private String LEFT = "l";
        private String RIGHT = "r";
        private int r;
        private int c;
        private int n;
        private int m;
        private int k;

        public String solution(int n, int m, int x, int y, int r, int c, int k) {
            this.r = r;
            this.c = c;
            this.n = n;
            this.m = m;
            this.k = k;

            int cacArrival = cacArrival(x, y, r, c);
            if((k - cacArrival) % 2 != 0) return "impossible";

            StringBuilder stringBuilder = new StringBuilder();

            String answer = dfs(x, y, 0, stringBuilder);;
            return answer;
        }

        int cacArrival(int XFrom, int Xto, int YFrom, int Yto){
            return Math.abs(XFrom - YFrom) + Math.abs(Xto - Yto);
        }

        String dfs(int x, int y , int count, StringBuilder sb){
            if(count == k && x == r && y == c) return sb.toString();
            if(count > k) return "";

            if(0 < y) {
                dfs(x, y -1, count +1, new StringBuilder(sb).append(this.DOWN));
            }

            if(0 < x) {
                dfs(x -1, y, count +1, new StringBuilder(sb).append(this.LEFT));
            }

            if(x < m) {
                dfs(x +1, y, count +1, new StringBuilder(sb).append(this.RIGHT));
            }

            if(y < n){
                dfs(x, y +1, count +1, new StringBuilder(sb).append(this.UP));
            }

            return "";

        }
    }


}
