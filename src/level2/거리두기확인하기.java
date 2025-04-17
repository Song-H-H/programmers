package level2;

public class 거리두기확인하기 {

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.solution(new String[][] {{"POOOP", "OXXOX", "OPXPX", "OOXOX", "POXXP"}, {"POOPX", "OXPXP", "PXXXO", "OXXXO", "OOOPP"}, {"PXOPX", "OXOXP", "OXPOX", "OXXOP", "PXPOX"}, {"OOOXX", "XOOOX", "OOOXX", "OXOOX", "OOOOO"}, {"PXPXP", "XPXPX", "PXPXP", "XPXPX", "PXPXP"}});

    }

    static class Solution {

        public int[] solution(String[][] places) {

            int[][] checkDirection = new int[][]{{-1, 0},{0, -1},{1, 0},{0, 1}};
            int[] answer = new int[places.length];
            int romNumber = 0;

            for(String[] place : places){
                answer[romNumber] = 1;
                for(int x = 0; x < place.length; x++){
                    String rowPlace = place[x];
                    for(int y = 0; y < rowPlace.length(); y ++){
                        char eachPlace = rowPlace.charAt(y);

                        if('X' == eachPlace) continue;
                        else if('P' == eachPlace){
                            for(int[] direction : checkDirection){
                                int movedX = x + direction[0];
                                int movedY = y + direction[1];

                                if(0 <= movedX && movedX < place.length && 0 <= movedY && movedY < rowPlace.length()){
                                    if(place[movedX].charAt(movedY) == 'P') {
                                        answer[romNumber] = 0;
                                        break;
                                    }
                                }
                            }
                        }else if('O' == eachPlace){
                            int personCount = 0;
                            for(int[] direction : checkDirection){
                                int movedX = x + direction[0];
                                int movedY = y + direction[1];

                                if(0 <= movedX && movedX < place.length && 0 <= movedY && movedY < rowPlace.length()){
                                    if(place[movedX].charAt(movedY) == 'P') {
                                        personCount ++;
                                    }
                                }
                            }

                            if(personCount >= 2) {
                                answer[romNumber] = 0;
                                break;
                            }
                        }
                        if(answer[romNumber] == 0) break;
                    }
                    if(answer[romNumber] == 0) break;
                }
                romNumber++;
            }

            for(int a : answer){
                System.out.println("a = " + a);
            }

            return answer;
        }
    }
}
