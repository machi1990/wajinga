package com.machi.wajinga.ws;

import java.io.IOException;

import com.machi.wajinga.ws.dataset.DataSet;


public class MainDev {

	public static void main(String[] args) throws IOException {
		DataSet.generate("Wajinga-Dev");
		Main.main(args);
	}
}
