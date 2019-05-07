package com.gfike.SelfCheckoutSim;

public class Helpers {

	public static String createPlu (String input) {
		String plu = "";
					
			for (int i = 0; i < input.length(); i++) {
				int temp = (int)input.charAt(i);
				if (plu.length() < 12) {
					plu += temp;
				}
			}
			
			if (plu.length() > 12) {
				plu = plu.substring(0, 12);
			}
		
		return plu;
	}
}
