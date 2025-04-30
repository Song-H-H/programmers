package level2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

public class 단체사진찍기 {
    //https://school.programmers.co.kr/learn/courses/30/lessons/1835
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.solution(2,new String[] {"N~F=0", "R~T>2"}); //3648;
        //solution.solution(2,new String[] {"M~C<2", "C~M>1"}); //0;
    }

    static class Solution {
        public List<String> allList = new ArrayList<>();

        public int solution(int n, String[] dataList) {
            int answer = 0;
            allList = new ArrayList<>();

            this.permutation("","ACFJMNRT");

            for (String data : dataList) {
                String first = data.substring(0,1);
                String second = data.substring(2,3);
                String compare = data.substring(3,4);
                int count = Integer.parseInt(data.substring(4,5));

                Predicate<String> predicate = null;
                Function<String, Integer> function = (z) -> Math.abs(z.indexOf(first) - z.indexOf(second)) -1;

                if ("=".equals(compare)) predicate = (x -> function.apply(x) == count);
                else if (">".equals(compare)) predicate = (x -> function.apply(x) > count);
                else if ("<".equals(compare)) predicate = (x -> function.apply(x) < count);

                allList = allList.stream().filter(predicate).collect(Collectors.toList());
            }

            answer = allList.size();
            return answer;
        }

        public void permutation(String prefix, String s) {
            int n = s.length();
            if(n==0)
                allList.add(prefix); // all에 삽입
            else {
                for(int i=0; i<n; i++) {
                    permutation(prefix + s.charAt(i), s.substring(0, i) + s.substring(i+1));
                }
            }
        }
    }
}
