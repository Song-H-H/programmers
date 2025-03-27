package level3;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class 양과늑대 {
    //https://school.programmers.co.kr/learn/courses/30/lessons/92343
    public static void main(String[] args) {
        Solution solution = new Solution();
        //solution.solution(new int[] {0,1,0,1,1,0,1,0,0,1,0}, new int[][] {{0,1},{0,2},{1,3},{1,4},{2,5},{2,6},{3,7},{4,8},{6,9},{9,10}});
        solution.solution(new int[] {0,0,1,1,1,0,1,0,1,0,1,1}, new int[][] {{0,1},{1,2},{1,4},{0,8},{8,7},{9,10},{9,11},{4,3},{6,5},{4,6},{8,9}});

    }

    static class Solution {

        int[] animalInfo = null;
        Map<String, String> treeInfo = new HashMap<>();
        Queue<Map<String, String>> queue = new LinkedList<>();
        List<Integer> resultList = new ArrayList<>();
        Function<String, List> splitToList = (x) -> new ArrayList<>(Arrays.stream(x.split(",")).filter(z -> !z.isEmpty()).distinct().collect(Collectors.toList()));
        BiFunction<List<String>, String, String> listToJoiningString = (x, z) -> x.stream().filter(y -> !y.equals(z)).collect(Collectors.joining(","));

        public int solution(int[] info, int[][] edges) {
            animalInfo = info;
            arrayTreeIntoMap(edges);

            addQeueue(0,0,"0","");
            findHighestSheep();

            int answer = resultList.stream().mapToInt(Integer::intValue).max().getAsInt();
            System.out.println("answer = " + answer);
            return answer;
        }

        void arrayTreeIntoMap(int[][] edges){
            for(int[] edge : edges){
                String key = String.valueOf(edge[0]);
                String value = String.valueOf(edge[1]);
                if(!treeInfo.containsKey(key)){
                    treeInfo.put(key, value);
                }else{
                    String orDefault = treeInfo.get(key);
                    orDefault += ","+value;
                    treeInfo.put(key, orDefault);
                }
            }
        }

        void addQeueue(int sheep, int wolf, String current, String possibleNode){
            Map<String, String> map = new HashMap<>();
            map.put("sheep", String.valueOf(sheep));
            map.put("wolf", String.valueOf(wolf));
            map.put("current", current);
            map.put("possibleNode", possibleNode);

            queue.add(map);
        }

        void findHighestSheep(){
            while(queue.size() > 0){
                Map poll = queue.poll();
                String sheep = (String) poll.get("sheep");
                String wolf = (String) poll.get("wolf");
                String current = (String) poll.get("current");
                String possibleNode = (String) poll.get("possibleNode");
                findNextNode(Integer.parseInt(sheep), Integer.parseInt(wolf), current, possibleNode);
            }
        }

        void findNextNode(int sheep, int wolf, String current, String possibleNode){
            int typeAnimal = animalInfo[Integer.parseInt(current)];

            if (typeAnimal == 0) sheep++;
            else wolf++;

            if(wolf >= sheep) resultList.add(sheep);
            else{
                List<String> possibleNodeList = splitToList.apply(possibleNode);

                if(treeInfo.containsKey(current)){
                    List<String> goNodeList = splitToList.apply(treeInfo.get(current));
                    goNodeList.addAll(possibleNodeList);

                    for(int i = 0; i < goNodeList.size(); i++){
                        String goNode = goNodeList.get(i);
                        addQeueue(sheep, wolf, goNode, listToJoiningString.apply(goNodeList, goNode));
                    }

                }else{
                    if(possibleNodeList.isEmpty()) resultList.add(sheep);
                    else{
                        for(int i = 0; i < possibleNodeList.size(); i++){
                            List<String> copyPossibleNode = new ArrayList<>(possibleNodeList);
                            copyPossibleNode.remove(i);
                            addQeueue(sheep, wolf, possibleNodeList.get(i), listToJoiningString.apply(copyPossibleNode, ""));
                        }

                    }
                }
            }
        }
    }
}