package alda.pathfinding;

public class Station {
	private Schedule sub;
	private Schedule bus;
	private Schedule schedule;
	private boolean subTimeSet = false;
	private boolean busTimeSet = false;
	private boolean busStation = false;
	private boolean subwayStation = false;
	
	public Station(){
		this.setSubwayStation(true);
	}
	
	public Station(boolean sub,boolean bus){
		this.setSubwayStation(sub);
		this.setBusStation(bus);
	}

	public boolean isSubwayStation() {
		return subwayStation;
	}

	public void setSubwayStation(boolean subwayStation) {
		this.subwayStation = subwayStation;
	}

	public boolean isBusStation() {
		return busStation;
	}

	public void setBusStation(boolean busStation) {
		this.busStation = busStation;
	}

	public Schedule getSub() {
		return sub;
	}

	public void setSub(Schedule sub) {
		this.sub = sub;
	}

	public Schedule getBus() {
		return bus;
	}

	public void setBus(Schedule bus) {
		this.bus = bus;
	}
	
	public String toString(){
		String station = "";
		if(subwayStation && busStation){
			station = "Subway & bus.";
		}else if (busStation){
			station = "Bus.";
		}else{
			station = "Subway.";
		}
		return station;
	}

	public boolean isSubTimeSet() {
		return subTimeSet;
	}

	public void setSubTimeSet(boolean timeSet) {
		this.subTimeSet = timeSet;
	}

	public boolean isBusTimeSet() {
		return busTimeSet;
	}

	public void setBusTimeSet(boolean busTimeSet) {
		this.busTimeSet = busTimeSet;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void combind() {
		this.schedule = new Schedule();
		this.schedule.combindSchedule(sub, bus); 
		
	}

	public void setSchedule() {
		if(subwayStation&&!busStation){
			this.schedule = this.sub;
		}else{
			this.schedule = this.bus;
		}
		
	}
	
}
