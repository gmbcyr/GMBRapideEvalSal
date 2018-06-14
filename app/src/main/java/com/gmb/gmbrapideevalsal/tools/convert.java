package com.gmb.gmbrapideevalsal.tools;

public interface convert {
	
	public double cfaToDollar(double amount, double rate);
	
	public double dollarToCfa(double amount, double rate);
	
	
	public String sayHello(String name);
	
	public String calc(String entry);
	
	
	public String testConnexion(String entry);
	
	//public String payHourToYear(double amount, double hpday, double dpweek);

	public String payHourToYear(double... params);
	
	//public String payYearToHour(double amount, double hpday,double dpweek,double pYear);
	
	public String payYearToHour(double... params);


	public String payFromHour(String... params);

	//public String payYearToHour(double amount, double hpday,double dpweek,double pYear);

	public String payFromYear(String... params);

	public String payFromWeek(String... params);

	String payFrom2Weeks(String... params);

	public String payFromMonth(String... params);
}
