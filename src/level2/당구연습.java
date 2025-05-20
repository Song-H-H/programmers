package level2;

public class 당구연습 {

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.solution(10, 10, 3, 7, new int[][] {{7, 7}, {2, 7}, {7, 3}});

    }

    static class Solution {

        int startX = 0;
        int startY = 0;
        int m = 0;
        int n = 0;

        public int[] solution(int m, int n, int startX, int startY, int[][] balls) {
            this.startX = startX;
            this.startY = startY;
            this.m = m;
            this.n = n;


            int[] answer = new int[balls.length];
            for(int i = 0; i < balls.length; i++){
                int lowDistance = findLowDistance(balls[i][0], balls[i][1]);
                answer[i] = lowDistance;
            }

            return answer;
        }

        int findLowDistance(int targetX, int targetY){
            int lowDistance = Integer.MAX_VALUE;

            lowDistance = Math.min(lowDistance, hitTop(targetX, targetY));
            lowDistance = Math.min(lowDistance, hitBottom(targetX, targetY));
            lowDistance = Math.min(lowDistance, hitRight(targetX, targetY));
            lowDistance = Math.min(lowDistance, hitLeft(targetX, targetY));

            return lowDistance;
        }

        int hitTop(int targetX, int targetY){
            if(this.startX == targetX && this.startY < targetY) return Integer.MAX_VALUE;

            int absX = Math.abs(this.startX - targetX);
            int absY = (this.n - targetY) + this.n - startY;

            return (int) (Math.pow(absX, 2) + Math.pow(absY, 2));
        }

        int hitBottom(int targetX, int targetY){
            if(this.startX == targetX && this.startY > targetY) return Integer.MAX_VALUE;

            int absX = Math.abs(this.startX - targetX);
            int absY = startY + targetY;

            return (int) (Math.pow(absX, 2) + Math.pow(absY, 2));
        }

        int hitRight(int targetX, int targetY){
            if(this.startX < targetX && this.startY == targetY) return Integer.MAX_VALUE;

            int absX = (this.m - targetX) + this.m - this.startX;
            int absY = Math.abs(this.startY - targetY);

            return (int) (Math.pow(absX, 2) + Math.pow(absY, 2));
        }

        int hitLeft(int targetX, int targetY){
            if(this.startX > targetX && this.startY == targetY) return Integer.MAX_VALUE;

            int absX = this.startX + targetX;
            int absY = Math.abs(this.startY - targetY);

            return (int) (Math.pow(absX, 2) + Math.pow(absY, 2));
        }
    }

}
