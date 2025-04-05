package level3;

import java.util.*;
import java.util.stream.Collectors;

public class 카드게임 {
    //https://school.programmers.co.kr/learn/courses/30/lessons/258707
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.solution(4, new int[] {3, 6, 7, 2, 1, 10, 5, 9, 8, 12, 11, 4});
        //solution.solution(3, new int[] {1, 2, 3, 4, 5, 8, 6, 7, 9, 10, 11, 12});
        //solution.solution(2, new int[] {5, 8, 1, 2, 9, 4, 12, 11, 3, 10, 6, 7});
        //solution.solution(10, new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18});
        //solution.solution(4, new int[] {1, 12, 2, 11, 3, 10, 4, 9, 4, 6, 4, 6});
    }

    static class Solution{

        Set<Integer> gameCardList = new LinkedHashSet<>();
        int targetNumber = 0;
        int gameCardIndex = 0;
        int finalGameRound = 0;

        public int solution(int coin, int[] cards) {
            return startGame(coin, cards);
        }

        public int startGame(int coin, int[] cards){
            Set<Integer> myCardList = settingGame(cards);
            findHighGameRound(myCardList, coin,0);

            System.out.println("this.finalGameRound = " + this.finalGameRound);
            return this.finalGameRound;
        }

        public Set<Integer> settingGame(int[] cards) {
            Set<Integer> myCardList = new HashSet<>();
            this.targetNumber = cards.length +1;

            for(int i = 0; i < cards.length/3; i++){
                myCardList.add(cards[i]);
            }

            for (int i = cards.length/3; i < cards.length; i++) {
                gameCardList.add(cards[i]);
            }

            return myCardList;
        }

        public void findHighGameRound(Set<Integer> myCardList, int myCoin, int gameRound) {
            gameRound++;
            boolean retryValue = true;

            if (this.gameCardList.size() <= this.gameCardIndex) {
                this.finalGameRound = gameRound;
                return;
            }

            this.gameCardIndex += 2;

            for (int myCard : myCardList) {
                if (myCardList.contains(this.targetNumber - myCard)) {
                    myCardList.remove(myCard);
                    myCardList.remove(this.targetNumber - myCard);
                    retryValue = false;
                    break;
                }
            }

            if (retryValue && myCoin >= 1) {
                List<Integer> findCardList = this.gameCardList.stream().limit(this.gameCardIndex).collect(Collectors.toList());;
                for (int gameCard : findCardList) {
                    if (myCardList.contains(this.targetNumber - gameCard)) {
                        myCardList.remove(this.targetNumber - gameCard);
                        this.gameCardList.remove(gameCard);

                        this.gameCardIndex--;
                        myCoin--;
                        retryValue = false;
                        break;
                    }
                }
            }

            if (retryValue && myCoin >= 2) {
                List<Integer> findCardList = this.gameCardList.stream().limit(this.gameCardIndex).collect(Collectors.toList());;
                for (int gameCard : findCardList) {
                    if (findCardList.contains(this.targetNumber - gameCard)) {
                        this.gameCardList.remove(this.targetNumber - gameCard);
                        this.gameCardList.remove(gameCard);

                        this.gameCardIndex -= 2;
                        myCoin -= 2;
                        retryValue = false;
                        break;
                    }
                }
            }

            if (retryValue) {
                this.finalGameRound = gameRound;
            } else {
                findHighGameRound(myCardList, myCoin, gameRound);
            }
        }
    }
}
