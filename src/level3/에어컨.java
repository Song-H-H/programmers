package level3;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class 에어컨 {
    //https://school.programmers.co.kr/learn/courses/30/lessons/214289
    public static void main(String[] args) {
        Solution solution = new Solution();
        //solution.solution(28, 18, 26, 10, 8, new int[] {0, 0, 1, 1, 1, 1, 1});
        //solution.solution(-10, -5, 5, 5, 1, new int[] {0, 0, 0, 0, 0, 1, 0});
        //solution.solution(11, 8, 10, 10, 1, new int[] {0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1});
        //solution.solution(11, 8, 10, 10, 100, new int[] {0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1});


        Solution4 solution4 = new Solution4();
        //solution4.solution(11, 8, 10, 10, 100, new int[] {0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1});
        solution4.solution(28, 18, 26, 10, 8, new int[] {0, 0, 1, 1, 1, 1, 1});
        solution4.solution(-10, -5, 5, 5, 1, new int[] {0, 0, 0, 0, 0, 1, 0});
        solution4.solution(11, 8, 10, 10, 1, new int[] {0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1});
        solution4.solution(11, 8, 10, 10, 100, new int[] {0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1});

    }

    static class Solution4{

        public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
            int possibleTopPower = 100000;
            boolean upTemperature = temperature < t1 ? Boolean.TRUE : Boolean.FALSE;
            temperature += 10;
            t1 += 10;
            t2 += 10;

            int[][] powerOfCase = new int[onboard.length][51];
            for (int i = 0; i < onboard.length; i++) {
                for (int j = 0; j <= 50; j++) {
                    powerOfCase[i][j] = possibleTopPower;
                }
            }

            powerOfCase[0][temperature] = 0;

            for (int i = 1; i < onboard.length; i++) {
                for (int j = 0; j <= 50; j++) {
                    int currentPower = possibleTopPower;

                    if ((onboard[i] == 1 && t1 <= j && j <= t2) || onboard[i] == 0) {

                        if (t1 <= j && j <= t2) {
                            currentPower = Math.min(currentPower, powerOfCase[i - 1][j] + b);
                        }

                        if(!upTemperature && 0 <= j - 1){
                            currentPower = Math.min(currentPower, powerOfCase[i - 1][j - 1]);
                        }

                        if(upTemperature && j + 1 <= 50){
                            currentPower = Math.min(currentPower, powerOfCase[i - 1][j + 1]);
                        }

                        if(0 <= j - 1) {
                            currentPower = Math.min(currentPower, powerOfCase[i - 1][j - 1] + a);
                        }

                        if(j + 1 <= 50) {
                            currentPower = Math.min(currentPower, powerOfCase[i - 1][j + 1] + a);
                        }

                        if(j == temperature){
                            currentPower = Math.min(currentPower, powerOfCase[i - 1][j]);
                        }

                        powerOfCase[i][j] = currentPower;
                    }
                }
            }


            return Arrays.stream(powerOfCase[onboard.length - 1]).sorted().findFirst().getAsInt();

        }
    }
    static class Solution3{


        public void solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
            int currentTemperature = 0;
            int powerConsumption = 0;
            String controlTemperature = temperature < t1 ? "UP" : "DOWN";

            //무조건 적게 쓰는 방향으로
            //대기 전력과 사용 전력 비교하여 최저 방향으로 진행
            //온도 미 충족 시 전단계로 돌아가면서 맞춘다
            for (int i = 0; i < onboard.length; i++) {
                if(onboard[i] == 1){
                    if(currentTemperature < t1 || t2 < currentTemperature){
                        //되돌아가기

                    }else{
                        if("UP".equals(controlTemperature)) {
                            currentTemperature--;
                        }else{
                            currentTemperature++;
                        }
                    }
                }else{
                    if("UP".equals(controlTemperature)) {
                        currentTemperature--;
                    }else{
                        currentTemperature++;
                    }
                }
            }
        }
    }

    static class Solution2{

        public void solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
            List<DataInfo> temperatureOfCase = new ArrayList<>();
            int lastUserInCarIndex = 0;
            String controlTemperature;

            for (int i = onboard.length - 1; i >= 0; i--) {
                if (onboard[i] == 1) {
                    lastUserInCarIndex = i;
                    break;
                }
            }

            if(temperature < t1) controlTemperature = "UP";
            else controlTemperature = "DOWN";


            temperatureOfCase.add(setting(temperature, 0));
            for (int i = 0; i < onboard.length; i++) {
                if(lastUserInCarIndex < i) break;

                List<DataInfo> tempCase = new ArrayList<>();
                temperatureOfCase = temperatureOfCase.stream().filter(distinctByKeys(DataInfo::getCurrentTemperature, DataInfo::getPowerConsumption)).collect(Collectors.toList());

                for(DataInfo dataInfo : temperatureOfCase){
                    int currentTemperature = dataInfo.getCurrentTemperature();
                    int powerConsumption = dataInfo.getPowerConsumption();

                    if (currentTemperature < t1) {
                        if(onboard[i] == 1){
                            tempCase.add(setting(currentTemperature + 1, powerConsumption + t1));
                        }else{
                            tempCase.add(setting(currentTemperature - 1, powerConsumption));
                        }
                    } else if (t2 < currentTemperature) {
                        if(onboard[i] == 1){
                            tempCase.add(setting(currentTemperature - 1, powerConsumption + t1));
                        }else{
                            tempCase.add(setting(currentTemperature + 1, powerConsumption));
                        }
                    } else {
                        tempCase.add(setting(currentTemperature, powerConsumption + t2));

                        if("UP".equals(controlTemperature)){
                            if(currentTemperature < temperature){
                                tempCase.add(setting(currentTemperature +1, powerConsumption + t1));
                            }
                        }else{
                            if(currentTemperature > temperature){
                                tempCase.add(setting(currentTemperature -1, powerConsumption + t1));
                            }
                        }

                        if("UP".equals(controlTemperature)){
                            tempCase.add(setting(currentTemperature -1, powerConsumption));
                        } else {
                            tempCase.add(setting(currentTemperature +1, powerConsumption));
                        }
                    }
                }

                temperatureOfCase = tempCase;
            }

            Comparator<DataInfo> compare = Comparator.comparing(DataInfo::getPowerConsumption);
            DataInfo dataInfo = temperatureOfCase.stream().sorted(compare).findFirst().get();

            System.out.println("dataInfo = " + dataInfo.toString());
        }

        public DataInfo setting(int currentTemperature, int powerConsumption){
            return new DataInfo(currentTemperature, powerConsumption);
        }

        public <T> Predicate<T> distinctByKeys(final Function<? super T, ?>... keyExtractors)
        {
            final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

            return t ->
            {
                final List<?> keys = Arrays.stream(keyExtractors)
                        .map(ke -> ke.apply(t))
                        .collect(Collectors.toList());

                return seen.putIfAbsent(keys, Boolean.TRUE) == null;
            };
        }

        class DataInfo{

            DataInfo(int currentTemperature, int powerConsumption){
                this.currentTemperature = currentTemperature;
                this.powerConsumption = powerConsumption;
            }

            int currentTemperature;
            int powerConsumption;

            public int getCurrentTemperature() {
                return currentTemperature;
            }

            public int getPowerConsumption() {
                return powerConsumption;
            }

            @Override
            public String toString() {
                return "DataInfo{" +
                        "currentTemperature=" + currentTemperature +
                        ", powerConsumption=" + powerConsumption +
                        '}';
            }
        }
    }

    static class Solution{

        int temperature;
        int t1;
        int t2;
        int a;
        int b;
        int[] onboard;
        String controlTemperature;
        int lastUserInCarIndex;
        int lossPowerConsumption = 0;


        public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
            settingFirstTimeData(temperature, t1, t2, a, b, onboard);

            findLossPowerConsumption(temperature, 0,0);

            return this.lossPowerConsumption;
        }

        public void settingFirstTimeData(int temperature, int t1, int t2, int a, int b, int[] onboard){
            this.temperature = temperature;
            this.t1 = t1;
            this.t2 = t2;
            this.a = a;
            this.b = b;
            this.onboard = onboard;

            for (int i = onboard.length - 1; i >= 0; i--) {
                if (onboard[i] == 1) {
                    this.lastUserInCarIndex = i;
                    break;
                }
            }

            if(temperature < t1) this.controlTemperature = "UP";
            else this.controlTemperature = "DOWN";
        }

        public void findLossPowerConsumption(int currentTemperature, int powerConsumption, int index){
            if(index == this.lastUserInCarIndex +1){
                closeData(powerConsumption);
                return;
            } else if (index == this.onboard.length) {
                closeData(powerConsumption);
                return;
            }

            index++;

            if (currentTemperature < this.t1) {
                powerConsumption += this.a;
                currentTemperature++;
                findLossPowerConsumption(currentTemperature, powerConsumption, index);
            } else if (this.t2 < currentTemperature) {
                powerConsumption += this.a;
                currentTemperature--;
                findLossPowerConsumption(currentTemperature, powerConsumption, index);
            } else {
                maintainTemperature(currentTemperature, powerConsumption, index);
                changeTemperature(currentTemperature, powerConsumption, index);
                offAirConditioner(currentTemperature, powerConsumption, index);
            }

        }

        public void maintainTemperature(int currentTemperature, int powerConsumption, int index){
            powerConsumption += this.b;
            findLossPowerConsumption(currentTemperature, powerConsumption, index);
        }

        public void changeTemperature(int currentTemperature, int powerConsumption, int index){
            if("UP".equals(this.controlTemperature)){
                if (currentTemperature < t2) {
                    currentTemperature++;
                    powerConsumption += this.a;
                }else{
                    return;
                }
            } else{
                if (currentTemperature > t1) {
                    currentTemperature--;
                    powerConsumption += this.a;
                }else{
                    return;
                }
            }

            findLossPowerConsumption(currentTemperature, powerConsumption, index);
        }

        public void offAirConditioner(int currentTemperature, int powerConsumption, int index) {
            if("UP".equals(this.controlTemperature)) currentTemperature--;
            else currentTemperature++;

            findLossPowerConsumption(currentTemperature, powerConsumption, index);
        }

        public void closeData(int powerConsumption) {
            if(this.lossPowerConsumption == 0) this.lossPowerConsumption = powerConsumption;
            else if (this.lossPowerConsumption > powerConsumption) this.lossPowerConsumption = powerConsumption;
            else return;
        }

    }
}
