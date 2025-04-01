package level3;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class 주사위고르기 {
    //https://school.programmers.co.kr/learn/courses/30/lessons/258709
    public static void main(String[] args) {
        Solution solution = new Solution();
        //solution.solution(new int[][]{{1, 2, 3, 4, 5, 6},{3, 3, 3, 3, 4, 4},{1, 3, 3, 4, 4, 4},{1, 1, 4, 4, 5, 5}});
        //solution.solution(new int[][]{{1, 2, 3, 4, 5, 6}, {2, 2, 4, 4, 6, 6}});
        solution.solution(new int[][]{{40, 41, 42, 43, 44, 45}, {43, 43, 42, 42, 41, 41}, {1, 1, 80, 80, 80, 80}, {70, 70, 1, 1, 70, 70}});
    }

    static class Solution{
        int[][] inputDice;
        int maxDiceCount = 0;
        List<Integer[]> numberOfDiceCase = new ArrayList<>();
        Stack<Integer> st = new Stack<>();
        int[] arr = new int[] {1,2,3,4,5,6};
        Integer winnerDiceCount = 0;
        List<Integer> winnerDiceGroup = new ArrayList<>();


        public int[] solution(int[][] inputDice) {
            this.inputDice = inputDice;
            this.maxDiceCount = inputDice.length;
            settingNumberOfDiceCase(6,maxDiceCount/2);

            List<Integer> A_Dice = settingFirstDice();
            List<Integer> B_Dice = anotherDiceGroup(A_Dice);
            compareDice(A_Dice, B_Dice);

            int loofCount = (int) (factorial(maxDiceCount) / (Math.pow(factorial(maxDiceCount / 2), 2)));

            for (int i = 1; i < loofCount; i++) {
                nextDiceGroup(A_Dice);
                B_Dice = anotherDiceGroup(A_Dice);
                compareDice(A_Dice, B_Dice);
            }

            System.out.println("winnerDiceCount = " + winnerDiceCount);
            System.out.println("winnerDiceGroup = " + winnerDiceGroup);


            int[] answer = winnerDiceGroup.stream().mapToInt(i -> i).toArray();
            return answer;
        }

        void settingNumberOfDiceCase(int number, int size) {
            if(st.size() == size){
                numberOfDiceCase.add(st.toArray(new Integer[size]));
                return;
            }

            for(int i = 0; i < number; i++){
                st.push(arr[i]);
                settingNumberOfDiceCase(number, size);
                st.pop();
            }
        }

        public List<Integer> settingFirstDice() {
            int getDice = maxDiceCount / 2;

            List<Integer> dice = new ArrayList<>();
            for (int i = 1; i <= getDice; i++) {
                dice.add(i);
            }

            return dice;
        }

        public void nextDiceGroup(List<Integer> dice){
            settingUpperDice(dice, dice.size()-1);
        }

        public void settingUpperDice(List<Integer> dice, int index){
            Integer diceNumber = dice.get(index);
            int indexMaxDiceCount = this.maxDiceCount - (dice.size() - (index + 1));

            if (index == 0 && diceNumber == indexMaxDiceCount) {
                return;
            } else if (diceNumber + 1 > indexMaxDiceCount) {
                settingUpperDice(dice, --index);
            } else {
                settingUnderDice(dice, index);
            }
        }

        public void settingUnderDice(List<Integer> dice, int index){
            int value = dice.get(index);

            for (int i = index; i < dice.size(); i++) {
                dice.set(i, ++value);
            }
        }

        public List<Integer> anotherDiceGroup(List<Integer> dice) {
            return IntStream.range(1, maxDiceCount+1).filter(x -> !dice.contains(x)).boxed().collect(Collectors.toList());
        }

        public void compareDice(List<Integer> A_Dice, List<Integer> B_Dice){
            SortedMap<Integer, Integer> A_SumDiceCount = new TreeMap<>();
            SortedMap<Integer, Integer> B_SumDiceCount = new TreeMap<>();

            for (Integer[] caseNumber : numberOfDiceCase) {
                int A_SumDice = 0;
                int B_SumDice = 0;

                for (int i = 0; i < A_Dice.size(); i++) {
                    A_SumDice += this.inputDice[A_Dice.get(i)-1][caseNumber[i]-1];
                    B_SumDice += this.inputDice[B_Dice.get(i)-1][caseNumber[i]-1];
                }

                A_SumDiceCount.compute(A_SumDice, (key, oldValue) -> oldValue == null ? 1 : oldValue + 1);
                B_SumDiceCount.compute(B_SumDice, (key, oldValue) -> oldValue == null ? 1 : oldValue + 1);
            }

            int winDiceCount = 0;
            for (Map.Entry<Integer, Integer> A_Map : A_SumDiceCount.entrySet()) {
                Integer A_Key = A_Map.getKey();

                for (Map.Entry<Integer, Integer> B_Map : B_SumDiceCount.entrySet()) {
                    Integer B_Key = B_Map.getKey();

                    if (A_Key > B_Key) {
                        winDiceCount += (A_Map.getValue() * B_Map.getValue());
                    }
                }
            }

            if (winnerDiceCount < winDiceCount) {
                winnerDiceCount = winDiceCount;
                winnerDiceGroup = new ArrayList<>(A_Dice);
            }
        }

        public int factorial(int number) {
            int result = 1;

            for (int factor = 2; factor <= number; factor++) {
                result *= factor;
            }

            return result;
        }
    }
}
