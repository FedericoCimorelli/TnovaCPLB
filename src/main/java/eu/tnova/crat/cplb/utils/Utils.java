package eu.tnova.crat.cplb.utils;

import eu.tnova.crat.cplb.data.Constants;

public class Utils {

	public static String getRoleCode(int role) {
		// TODO Auto-generated method stub
		
		switch(role){
		case Constants.OFPCRROLEMASTER:
			return "BECOMEMASTER";
		case Constants.OFPCRROLESLAVE:
			return "BECOMESLAVE";
		default:
			return "BECOMEEQUAL";
		}
	}
	
	public static String getRoleString(int role) {
		// TODO Auto-generated method stub
		
		switch(role){
		case Constants.OFPCRROLEMASTER:
			return "BECOMEMASTER";
		case Constants.OFPCRROLESLAVE:
			return "BECOMESLAVE";
		case Constants.OFPCRROLEEQUAL:
			return "BECOMEEQUAL";
		default:
			return "NULL";
		}
	}



}