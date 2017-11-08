package org.z.entities.rules.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException; 

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine; 
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;  
import org.z.entities.rules.engine.rules.OppositeTack;
import org.z.entities.rules.engine.rules.SameTackNotOverlapped;
import org.z.entities.rules.engine.rules.SameTackOverlapped;
import org.z.entities.rules.engine.types.Tack;
import org.z.entities.rules.engine.types.Wind;
import org.z.entities.rules.engine.utils.Utils;  

import static org.jeasy.rules.core.RulesEngineBuilder.aNewRulesEngine;

public class Main {

	private static StringBuffer sb = new StringBuffer();
	private static String endl = "\n";
	
	public static void main(String[] args) throws FileNotFoundException {
		runJeasyRuleEngine();
		System.out.println("===================================");
		runIfConditions();
		System.out.println("===================================");
		runDroolsRulesEngine();
		
		System.out.println("\n"+sb.toString());
	}

	private static void runDroolsRulesEngine() throws FileNotFoundException {
		long startTime = System.currentTimeMillis(); 
	 
	    KieServices kieServices = KieServices.Factory.get();
	    KieFileSystem kfs = kieServices.newKieFileSystem();
	    FileInputStream fis = new FileInputStream(  "src/main/resources/WhichBoatCanKeepMove.drl" );
	    kfs.write( "src/main/resources/simple.drl",
	                kieServices.getResources().newInputStreamResource( fis ) );
	    KieBuilder kieBuilder = kieServices.newKieBuilder( kfs ).buildAll();
	    Results results = kieBuilder.getResults();
	    if( results.hasMessages( Message.Level.ERROR ) ){
	        System.out.println( results.getMessages() );
	        throw new IllegalStateException( "### errors ###" );
	    }
	    KieContainer kieContainer =
	        kieServices.newKieContainer( kieServices.getRepository().getDefaultReleaseId() );
	    KieSession kSession = kieContainer.newKieSession();
        
		long endTime = System.currentTimeMillis() - startTime;
		sb.append(">Drools Rules Engine:").append(endl);
		sb.append("Register rules took: " + endTime + " millisec").append(endl);
		
		Utils.generateOverlapped();

		Wind wind = Wind.EAST;
		Boat boat1 = new Boat(33, 44, "boat1");
		Utils.setTack(boat1, wind);
		Boat boat2 = new Boat(66, 77, "boat2");
		Utils.setTack(boat2, wind);
		Fact fact = new Fact(boat1, boat2, wind);

		kSession.insert(fact);

		startTime = System.currentTimeMillis();
		kSession.fireAllRules();
		endTime = System.currentTimeMillis() - startTime;

		sb.append("Fire rules took: " + endTime + " millisec").append(endl);
		System.out.println("The boats after running the Drools Rules Engine: ");
		System.out.println(boat1);
		System.out.println(boat2); 
	}

	private static void runIfConditions() {
		Utils.generateOverlapped();
		Boat boat1 = new Boat(33, 44, "boat1");
		Boat boat2 = new Boat(66, 77, "boat2");
		Wind wind = Wind.EAST;

		long startTime = System.currentTimeMillis();
		Utils.setTack(boat1, wind);
		Utils.setTack(boat2, wind);

		if (boat1.getTack() != boat2.getTack()) {
			if (boat1.getTack() == Tack.STARBOARD) {
				boat1.setKeepMove(true);
				boat2.setKeepMove(false);
			} else {
				boat1.setKeepMove(false);
				boat2.setKeepMove(true);
			}
		} else {
			int overlapped = Utils.getOverlappedForBoats(boat1, boat2);
			if (overlapped != 0) {
				if (overlapped == 1) {
					System.out.println("boat2 is clear ahead");
					boat1.setKeepMove(false);
					boat2.setKeepMove(true);
				} else {
					System.out.println("boat1 is clear ahead");
					boat1.setKeepMove(true);
					boat2.setKeepMove(false);
				}
			} else {
				int windward = Utils.getWindwardForBoats(boat1, boat2, wind);
				if (windward == 1) {
					System.out.println("boat2 is windward");
					boat1.setKeepMove(true);
					boat2.setKeepMove(false);
				} else {
					System.out.println("boat1 is windward");
					boat1.setKeepMove(false);
					boat2.setKeepMove(true);
				}
			}
		}

		System.out.println("The boats after running the if consitions: ");
		System.out.println(boat1);
		System.out.println(boat2);
		long endTime = System.currentTimeMillis() - startTime;
		sb.append(">if\\else consitions:").append(endl);
		sb.append("Took: " + endTime + " millisec").append(endl); 
	}

	private static void runJeasyRuleEngine() {
		Utils.generateOverlapped();
		Boat boat1 = new Boat(33, 44, "boat1");
		Boat boat2 = new Boat(66, 77, "boat2");
		Wind wind = Wind.EAST;

		Facts facts = new Facts();
		facts.put("boat1", boat1);
		facts.put("boat2", boat2);
		facts.put("wind", wind);

		long startTime = System.currentTimeMillis();
		Rules rules = new Rules();
		rules.register(new OppositeTack());
		rules.register(new SameTackNotOverlapped());
		rules.register(new SameTackOverlapped());
		long endTime = System.currentTimeMillis() - startTime;
		sb.append(">JEasy Rules Engine:").append(endl);
		sb.append("Register rules took: " + endTime + " millisec").append(endl); 

		// fire rules on known facts
		RulesEngine rulesEngine = aNewRulesEngine().withSkipOnFirstAppliedRule(
				true).build();
		startTime = System.currentTimeMillis();
		rulesEngine.fire(rules, facts);
		endTime = System.currentTimeMillis() - startTime;
		sb.append("Fire rules took: " + endTime + " millisec").append(endl);
		System.out.println("\nThe boats after running the rules engine: ");
		System.out.println(boat1);
		System.out.println(boat2); 
	}
}
