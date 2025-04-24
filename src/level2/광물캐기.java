package level2;

import java.util.*;

public class 광물캐기 {
    //https://school.programmers.co.kr/learn/courses/30/lessons/172927
    public static void main(String[] args) {
        Solution solution = new Solution();
        //solution.solution2(new int[]{1, 3, 2}, new String[]{"diamond", "diamond", "diamond", "iron", "iron", "diamond", "iron", "stone"});
        solution.solution(new int[]{0, 1, 1}, new String[]{"diamond", "diamond", "diamond", "diamond", "diamond", "iron", "iron", "iron", "iron", "iron", "diamond"});
    }

    static class Solution {

        final Map<String, Integer> convertStone = new HashMap(){{
            put("diamond", 25);
            put("iron"   ,  5);
            put("stone"  ,  1);
        }};

        int answer = 0;

        public int solution(int[] picks, String[] minerals) {
            int totalPicks = Arrays.stream(picks).sum();
            boolean isEnoughMinerals = totalPicks * 5 > minerals.length ? true : false;
            if (!isEnoughMinerals) minerals = Arrays.copyOfRange(minerals, 0, totalPicks * 5);

            List<Map<String, Integer>> groupingData = new ArrayList<>();
            Integer fiveStoneEnergy = 0;
            Integer diamondCount = 0;
            Integer ironCount = 0;
            Integer stoneCount = 0;
            int count = 0;
            for(int i = 0; i < minerals.length; i++) {
                String mineral = minerals[i];
                fiveStoneEnergy += convertStone.get(mineral);

                if("diamond".equals(mineral)) diamondCount++;
                else if("iron".equals(mineral)) ironCount++;
                else if("stone".equals(mineral)) stoneCount++;

                count++;
                if(count == 5 || i == minerals.length-1){
                    Map<String, Integer> data = new HashMap<>();
                    data.put("fiveStoneEnergy", fiveStoneEnergy);
                    data.put("diamond", diamondCount);
                    data.put("iron", ironCount);
                    data.put("stone", stoneCount);
                    groupingData.add(data);

                    fiveStoneEnergy = 0;
                    diamondCount = 0;
                    ironCount = 0;
                    stoneCount = 0;
                    count = 0;
                }
            }

            Collections.sort(groupingData, (o1, o2) -> (o2.get("fiveStoneEnergy")).compareTo(o1.get("fiveStoneEnergy")));


            findLow(picks[0], "diamond", groupingData);
            findLow(picks[1], "iron", groupingData);
            findLow(picks[2], "stone", groupingData);

            return answer;
        }

        void findLow(int pickCount, String pickType, List<Map<String, Integer>> groupingStoneEnergyByFive){
            for(int i = 0; i < pickCount; i++){
                if(groupingStoneEnergyByFive.isEmpty()) return;

                Integer diamondCount = groupingStoneEnergyByFive.get(0).getOrDefault("diamond",0);
                Integer ironCount = groupingStoneEnergyByFive.get(0).getOrDefault("iron",0);
                Integer stoneCount = groupingStoneEnergyByFive.get(0).getOrDefault("stone",0);

                Integer totalMineral = diamondCount + ironCount + stoneCount;

                if("diamond".equals(pickType)){
                    answer += totalMineral;
                }else if("iron".equals(pickType)) {
                    answer += totalMineral - diamondCount + (diamondCount * 5);
                }else if("stone".equals(pickType)) {
                    answer += totalMineral - (diamondCount + ironCount) + (diamondCount * 25) + (ironCount * 5);
                }

                groupingStoneEnergyByFive.remove(0);
            }
        }

    }
}
