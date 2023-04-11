package com.imhotepbizarrostriage.Main;

import com.imhotepbizarrostriage.BitwiseHuman;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.google.gson.Gson;

import dataBean.FileNavigator;
import dataBean.Kgi;
import dataBean.Kgml;
import dataBean.NameTable;
import dataProcess.KeggParser;
import dataProcess.KgiProcess;
import dataProcess.KgmlParser;
import dataProcess.NodeNameRetrieval;
import dataProcess.SettingProcess;
import fileIO.Copyer;
import fileIO.Writer;
import init.Initialization;

public class KeggNodeProcessMain extends BitwiseHuman {
	public static void main(String[] args) {
		
		System.out.println("(This program may take about 5 hours and 50 minutes.)");
		
		int root = Initialization.DESKTOP;
		String dir = "GeneMapLoop";
		String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
		if(args.length == 3) {
			switch(args[1]) {
			case "_USERDIR": root = Initialization.USER_DIR; break;
			case "_USERHOME": root = Initialization.USER_HOME; break;
			}
			date = args[2];
		}
		else if(args.length == 2) {
			String arg = args[1];
			if(arg.charAt(0) == '_') {
				switch(arg) {
				case "_USERDIR": root = Initialization.USER_DIR; break;
				case "_USERHOME": root = Initialization.USER_HOME; break;
				}
			}
			else date = arg;
		}
		
		String species = args[0];
		
		// read setting and complete setting.
		File stg = new Initialization(root, dir, date).getSettingFile();
		SettingProcess sp = new SettingProcess(stg);
		FileNavigator fn = null;
		try {
			fn = sp.analyze(sp.parse(), species);
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		
		System.out.println("===KeggParseMain=====================");
		System.out.println("Start on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");
		
		try{
			System.out.println("Parsing KGML on KEGG Site...");
			System.out.println("(It may take about 10 minutes.)");
			System.out.println("(If there is no response after about 10 mins, maybe you can restart this program.)");
			KeggParser kp = new KeggParser(fn, species);
			kp.execute();
			kp.execute(true, false, false, true);
			System.out.println("Complete Parsing KGML on KEGG Site!");
			
			System.out.println("Copying KGML Files...");
			File kf = new File(fn.getKeggDirPath() + File.separator + "kgml");
			File [] keggFiles = kf.listFiles();
			for(File keggFile : keggFiles){
				long start = System.nanoTime();
		        Copyer.copy(keggFile, new File(fn.getKgmlDirPath() + File.separator + keggFile.getName()));
		        System.out.println("Time taken by Stream Copy = " + (System.nanoTime() - start));
		        keggFile.delete();
			}
			kf.delete();
			System.out.println("Complete Copying KGML Files!");
		}
		catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
			System.out.println("If there is no .stg file,"
					+ "it means you lose the setting file!");
			System.out.println("To solve this problem,"
					+ "maybe you can run the InitializationMain.bat first.");
			System.out.println("If it shows connect time out,"
					+ "please check whether your internet connection is fine or not.");
			System.out.println("If your internet connection is fine,"
					+ "maybe you can try to run again after a few minutes.");
		}
		
		System.out.println("=====================================");
		System.out.println("Finish on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");
		System.out.println();
		
		//================================================================================================================================
		
		System.out.println("===NodeNameRetrievalMain=============");
		System.out.println("Start on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");
		
		NameTable nt = new NameTable();
		
		try {
			System.out.println("Retrieving Node Names...");
			System.out.println("(It may take about 5 hours and 30 mins.)");
			// transform kgml files into Kgml objects.
			File [] kgmlFiles = fn.getKgmlDir().listFiles();
			ArrayList<String> names = new ArrayList<String>();
			for(File kgmlFile : kgmlFiles){
				KgmlParser kp = new KgmlParser(kgmlFile, null);
				ArrayList<String> result = kp.parseEntry();
				for(String name : result)
					if(!names.contains(name)) names.add(name);
			}
			
			for(String name : names) {
				if(name.contains("(")) continue;
				else if(!name.contains("path") && !name.contains("PATH")) {	/* path:ko01052 have PATH case! */
					String nodeName = NodeNameRetrieval.nameReplace(NodeNameRetrieval.getGeneName(name.split(":")[1]));
					nt.addElement(name, nodeName);
				}
			}
			System.out.println("Complete Retrieving Node Names!");
			
			System.out.println("Constructing Name Table...");
			Writer w = new Writer(fn.getNameFile());
			Gson gson = new Gson();
			w.write(gson.toJson(nt));
			w.close();
			System.out.println("Complete Constructing Name Table!");
		}
		catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		
		System.out.println("=====================================");
		System.out.println("Finish on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");
		System.out.println();
		
		//================================================================================================================================
		
		System.out.println("===PreProcess========================");
		System.out.println("Start on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");
		System.out.println("(It may take about 8 minutes.)");
		
		try {
			System.out.println("Transforming kgml files into Kgml Objs...");
			// transform kgml files into Kgml objects.
			File [] kgmlFiles = fn.getKgmlDir().listFiles();
			ArrayList<Kgml> kgmls = new ArrayList<Kgml>();
			for(File kgmlFile : kgmlFiles){
				KgmlParser kp = new KgmlParser(kgmlFile, nt);
				kgmls.add(kp.parse());
			}
			System.out.println("Complete Transforming kgml files!");
			
			System.out.println("Transforming Kgml objs into Kgi obj...");
			// transform Kgml objects into Kgi object.
			KgiProcess kp = new KgiProcess(kgmls);
			Kgi kgi = kp.merge();
			System.out.println("Complete Transforming Kgml objs!");
			
			System.out.println("Saving Kgi object as file...");
			// save Kgi object as file.
			KgiProcess.save(fn, kgi);
			System.out.println("Complete Saving Kgi object!");
		}
		catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		
		System.out.println("=====================================");
		System.out.println("Finish on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");
	}
}
