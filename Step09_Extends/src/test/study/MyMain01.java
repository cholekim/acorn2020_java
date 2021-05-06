package test.study;

import test.mypac.HandPhone;
import test.mypac.Phone;
import test.mypac.SmartPhone;

public class MyMain01 {
	public static void main(String[] args) {
		Phone p1=new Phone();
		HandPhone p2=new HandPhone();
		
		SmartPhone p3=new SmartPhone();
		p3.call();
		p3.mobileCall();
		p3.doInternet();
		p3.takePicture();
	}
}
