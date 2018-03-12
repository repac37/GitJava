package alda.pathfinding;

import java.util.*;

public class Schedule {
	Map<Integer, List<Integer>> timeTabel;
	
	public Schedule() {
		timeTabel = new TreeMap<>();
		createTimeTableSub(0);
	}

	public Schedule(int offset) {
		timeTabel = new TreeMap<>();
		createTimeTableSub(offset);
	}
	
	public Schedule(int offset, boolean bus) {
		timeTabel = new TreeMap<>();
		if(bus)
			createTimeTableBus(offset);
		else
			createTimeTableBus(offset);
	}

	private void createTimeTableSub(int offset) {
		List<Integer> minArray = new ArrayList<>();
		minArray.add(hourCheck(0 + offset));
		minArray.add(hourCheck(10 + offset));
		minArray.add(hourCheck(20 + offset));
		minArray.add(hourCheck(30 + offset));
		minArray.add(hourCheck(40 + offset));
		minArray.add(hourCheck(50 + offset));
		Collections.sort(minArray);

		for (int i = 0; i < 24; i++) {
			timeTabel.put(i, minArray);
		}

		//System.out.println(timeTabel.values());

	}
	
	private void createTimeTableBus(int offset) {
		List<Integer> minArray = new ArrayList<>();
		minArray.add(hourCheck(15 + offset));
		minArray.add(hourCheck(45 + offset));
		Collections.sort(minArray);

		for (int i = 0; i < 24; i++) {
			timeTabel.put(i, minArray);
		}
	}

	private int hourCheck(int i) {
		while (i > 59) {
			i -= 60;
		}
		return i;
	}

	public int nextDepature(int hour, int min) {
		List<Integer> minList = timeTabel.get(hour);
		int timeLeft = 0;
		boolean nextHour = false;
		int depatureMin = 0;
		for (int m : minList) {
			if (min < m) {
				timeLeft = m - min;
				depatureMin = m;
				nextHour = true;
				break;
			}
		}

		if (nextHour) {
			System.out.println(timeLeft + " min " + (hour == 0 ? "00" : hour) + ":" + (depatureMin<10?"0"+depatureMin:depatureMin) );
		} else {
			if (hour == 23) {
				hour = 0;
			} else {
				hour++;
			}
			timeLeft = 60 - min;
			minList = timeTabel.get(hour);
			timeLeft += minList.get(0);
			System.out.println(
					timeLeft + " min " + (hour == 0 ? "00" : hour) + ":" + (minList.get(0) == 0 ? "00" : minList.get(0)));
		}
		return min;

	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		List<Integer> minArray = timeTabel.get(0);
		for(int i : timeTabel.keySet()){
			sb.append(i+": ");
			for(int min : minArray){
				sb.append(min+", ");
			}
			sb.replace(sb.length() - 2, sb.length(), "\n");
			if(i ==2){
				break;
			}
		}
		
		return sb.toString();
	}
	
	public Map<Integer, List<Integer>> getTable(){
		return this.timeTabel;
	}
	public void setTable(Map<Integer, List<Integer>> table){
		this.timeTabel= table;
	}
	
	public void combindSchedule(Schedule sub, Schedule bus){
		List<Integer> subL = sub.getTable().get(0);
		List<Integer> busL = bus.getTable().get(0);
		subL.addAll(busL);
		Collections.sort(subL);
		for(int i = 0; i < timeTabel.size(); i++){
			timeTabel.put(i, subL);
		}
	
	}

}
