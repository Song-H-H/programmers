package level2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class 귤고르기 {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int solution1 = solution.solution(6, new int[]{1, 3, 2, 5, 4, 5, 2, 3});
        //int solution1 = solution.solution(4, new int[]{1, 3, 2, 5, 4, 5, 2, 3});
        //int solution1 = solution.solution(2, new int[]{1, 1, 1, 1, 2, 2, 2, 3});

        System.out.println("solution1 = " + solution1);

    }

    static class Solution{
        public int solution(int k, int[] tangerine) {
            int answer = 0;


            List<Long> collect = Arrays.stream(tangerine)
                    .boxed()
                    .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
                    .values()
                    .stream()
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());

            long sum = 0;
            long lastUpdate = 0;

            for (Long c : collect) {
                if (sum + c >= k) {
                    sum += c;
                    answer++;
                    break;
                } else {
                    sum += c;
                    lastUpdate = c;
                    answer++;
                }
            }

            return answer;
        }
    }
}
