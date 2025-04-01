package level3;

import java.util.HashMap;
import java.util.Map;

public class 추석트래픽 {

    public static void main(String[] args) {
        Solution solution = new Solution();
        //solution.solution(new String[]{"2016-09-15 01:00:04.001 2.0s", "2016-09-15 01:00:07.000 2s"});
        //solution.solution(new String[]{"2016-09-15 01:00:04.002 2.0s", "2016-09-15 01:00:07.000 2s"});
        solution.solution(new String[]{"2016-09-15 20:59:57.421 0.351s",
                "2016-09-15 20:59:58.233 1.181s",
                "2016-09-15 20:59:58.299 0.8s",
                "2016-09-15 20:59:58.688 1.041s",
                "2016-09-15 20:59:59.591 1.412s",
                "2016-09-15 21:00:00.464 1.466s",
                "2016-09-15 21:00:00.741 1.581s",
                "2016-09-15 21:00:00.748 2.31s",
                "2016-09-15 21:00:00.966 0.381s",
                "2016-09-15 21:00:02.066 2.62s"});

    }

    static class Solution {
        public int solution(String[] lines) {

            int[] startTime = new int[lines.length];
            int[] endTime = new int[lines.length];

            for(int i = 0; i < lines.length; i++){
                String line = lines[i];
                String[] splitLine = line.split(" ");
                String[] splitTime = splitLine[1].split(":");

                double startSecond = Double.parseDouble(splitTime[0]) * 60 * 60+ Double.parseDouble(splitTime[1]) * 60 + Double.parseDouble(splitTime[2]) - Double.parseDouble(splitLine[2].replace("s", ""));
                double endSecond = Double.parseDouble(splitTime[0]) * 60 * 60 + Double.parseDouble(splitTime[1]) * 60 + Double.parseDouble(splitTime[2]);

                startTime[i] = (int) ((startSecond +0.001) * 1000);
                endTime[i] = (int) (endSecond * 1000) + 1000;
            }

            int answer = 0;
            for(int i = 0; i < lines.length; i++) {
                int cnt = 0;
                for(int j = i; j < lines.length; j++) {
                    if(endTime[i] > startTime[j]) cnt++;
                }

                answer = Math.max(answer, cnt);
            }
            return answer;
        }
    }
}
