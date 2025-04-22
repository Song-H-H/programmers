package level2;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class 과제진행하기 {
    //https://school.programmers.co.kr/learn/courses/30/lessons/176962
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.solution(solution.dataSet());
    }
    static class Solution {
        public String[] solution(String[][] plans) {
            List<Plan> result = new ArrayList();
            List<Plan> processList = new ArrayList();
            Comparator<Plan> compare = Comparator.comparing(Plan::getStartDateTime);

            List<String[]> list = Arrays.asList(plans);
            List<Plan> collect = list.stream().map(x -> new Plan(x[0], x[1], x[2]))
                    .collect(Collectors.toList())
                    .stream()
                    .sorted(compare)
                    .collect(Collectors.toList());

            int i = 0;
            while (i < collect.size()-1) {
                Plan currentplan = collect.get(i);
                Plan nextPlan = collect.get(i+1);

                LocalDateTime currentPlanStartDateTime = currentplan.getStartDateTime();
                LocalDateTime currentPlanEndDateTime = currentplan.getEndDateTime();
                LocalDateTime nextPlanStartDateTime = nextPlan.getStartDateTime();

                long diffPlansMinutes = getDiffMinutes(nextPlanStartDateTime, currentPlanEndDateTime);
                if (diffPlansMinutes <= 0) {
                    result.add(currentplan);

                    for(int j = 0; j < processList.size(); j++){
                        Plan process = processList.get(j);
                        long leftPlayTime = process.getLeftPlayTime() + diffPlansMinutes;
                        diffPlansMinutes = leftPlayTime;

                        if(leftPlayTime <= 0){
                            result.add(process);
                            processList.remove(process);
                            j--;

                        } else{
                            process.setLeftPlayTime(leftPlayTime);
                            break;
                        }
                    }

                } else {
                    Long cuurentleftPlayTime = currentplan.getLeftPlayTime();
                    Long nextLeftPlayIime = cuurentleftPlayTime - (cuurentleftPlayTime - diffPlansMinutes);
                    if(i == 0) {
                        diffPlansMinutes = getDiffMinutes(currentPlanStartDateTime, nextPlanStartDateTime);
                        nextLeftPlayIime = cuurentleftPlayTime - diffPlansMinutes;
                    }

                    currentplan.setLeftPlayTime(nextLeftPlayIime);
                    processList.add(0,currentplan);
                }

                i++;
                if(i == collect.size()-1){
                    processList.add(0,nextPlan);
                }
            }

            if(processList.size() > 0){ result.addAll(processList); }

            String[] answer = result.stream().map(x -> x.getName()).toArray(String[]::new);
            for (String answer1 : answer) {
                System.out.println("answer1 = " + answer1);
            }
            return answer;
        }

        Long getDiffMinutes(LocalDateTime start, LocalDateTime end){
            Duration duration = Duration.between(start, end);
            return duration.toMinutes();
        }

        String[][] dataSet() {
            //[name, start, playtime]
            String[][] plans = {{"korean", "11:40", "30"}, {"english", "09:00", "20"}, {"math", "12:30", "40"},
                    {"science", "12:40", "50"},{"music", "12:20", "40"},{"history", "14:00", "30"},{"computer", "12:50", "100"},
                    {"aaa", "12:00", "20"}, {"bbb", "12:10", "30"}, {"ccc", "12:25", "10"}
            };
            //String[][] plans = {{"science", "12:40", "30"},{"music", "12:50", "20"},{"history2", "13:00", "30"},{"history3", "13:10", "20"},{"history", "14:00", "10"}};
            //String[][] plans = {{"aaa", "12:00", "20"}, {"bbb", "12:10", "30"}, {"ccc", "12:40", "10"}};
            return plans;
        }
    }
    static class Plan{
        private String name;
        private LocalDateTime startDateTime;
        private LocalDateTime endDateTime;

        private Long leftPlayTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDateTime getStartDateTime() {
            return startDateTime;
        }

        public void setStartDateTime(LocalDateTime startDateTime) {
            this.startDateTime = startDateTime;
        }

        public LocalDateTime getEndDateTime() {
            return endDateTime;
        }

        public void setEndDateTime(LocalDateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        public Long getLeftPlayTime() {
            return leftPlayTime;
        }

        public void setLeftPlayTime(Long leftPlayTime) {
            this.leftPlayTime = leftPlayTime;
        }

        public Plan(String name, String startDateTime, String playtime) {
            this.name = name;
            LocalTime localTime = LocalTime.parse(startDateTime, DateTimeFormatter.ofPattern("HH:mm"));
            this.startDateTime = LocalDateTime.now()
                                        .withHour(localTime.getHour())
                                        .withMinute(localTime.getMinute())
                                        .withSecond(0)
                                        .withNano(0);
            this.endDateTime = this.startDateTime.plusMinutes(Long.parseLong(playtime));
            this.leftPlayTime = Long.parseLong(playtime);

        }
    }

}
