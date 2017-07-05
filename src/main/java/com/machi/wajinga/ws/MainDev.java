package com.machi.wajinga.ws;

import com.machi.wajinga.ws.dataset.DataSet;


public class MainDev {

	public static void main(String[] args) throws Exception {
		DataSet.generate("Wajinga-Dev");
		Main.main(args);
	}
}
