package org.z.entities.rules.engine;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Before; 
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.z.entities.rules.engine.types.Tack;
import org.z.entities.rules.engine.types.Wind;
import org.z.entities.rules.engine.utils.Utils;

public class DroolsTests {
	
	private StatelessKieSession kSession;
	private Fact fact;

    @Before
	public void setUp() {
		
	    KieServices kieServices = KieServices.Factory.get();
	    KieFileSystem kFileSystem = kieServices.newKieFileSystem();
	    File file = new File( "src/main/resources/WhichBoatCanKeepMove.drl"); 
        Resource resource = kieServices.getResources().newFileSystemResource(file).setResourceType(ResourceType.DRL);
        kFileSystem.write( resource );  
        
	    File file2 = new File( "src/main/resources/BoatActions.drl"); 
        Resource resource2 = kieServices.getResources().newFileSystemResource(file2).setResourceType(ResourceType.DRL);
        kFileSystem.write( resource2 ); 

	    File file3 = new File( "src/main/resources/SpeedActions.drl"); 
        Resource resource3 = kieServices.getResources().newFileSystemResource(file3).setResourceType(ResourceType.DRL);
        kFileSystem.write(resource3); 
        
        KieBuilder kbuilder = kieServices.newKieBuilder( kFileSystem ); 
        kbuilder.buildAll();        
	    KieContainer kieContainer =
	        kieServices.newKieContainer( kieServices.getRepository().getDefaultReleaseId());
	    
	    kSession = kieContainer.newStatelessKieSession();
	   
		Wind wind = Wind.EAST;
		Boat boat1 = new Boat(33, 44, "boat1"); 
		Boat boat2 = new Boat(66, 77, "boat2"); 
		int overLappedInd = Utils.getOverlappedForBoats(boat1, boat2);
		int windwardInd = Utils.getWindwardForBoats(boat1, boat2, wind);
		
		fact = new Fact(boat1, boat2, wind, overLappedInd, windwardInd); 
	}

	@Test 
	public void sameTackOverlappedBoat2IsWindward () { 
		fact.getBoat1().setTack(Tack.PORT);
		fact.getBoat2().setTack(Tack.PORT);		
		fact.setOverLappedInd(0);
		fact.setWindwardInd(1);
		
		kSession.execute(fact);
		
		assertEquals(fact.getBoat1().isKeepMove(),true);
		assertEquals(fact.getBoat2().isKeepMove(),false);	
	}
	
	@Test 
	public void sameTackOverlappedBoat1IsWindward () { 
		fact.getBoat1().setTack(Tack.PORT);
		fact.getBoat2().setTack(Tack.PORT);		
		fact.setOverLappedInd(0);
		fact.setWindwardInd(-1);
		
		kSession.execute(fact);
		
		assertEquals(fact.getBoat1().isKeepMove(),false);
		assertEquals(fact.getBoat2().isKeepMove(),true);	
	}
	
 	@Test 
	public void sameTackNotOverlappedBoat1IsClearAhead () { 
		fact.getBoat1().setTack(Tack.PORT);
		fact.getBoat2().setTack(Tack.PORT);		
		fact.setOverLappedInd(-1);
		
		kSession.execute(fact);
		
		assertEquals(fact.getBoat1().isKeepMove(),true);
		assertEquals(fact.getBoat2().isKeepMove(),false);	
	}
	
 	@Test 
	public void sameTackNotOverlappedBoat2IsClearAhead () { 
		fact.getBoat1().setTack(Tack.PORT);
		fact.getBoat2().setTack(Tack.PORT);		
		fact.setOverLappedInd(1);
		
		kSession.execute(fact);
		
		assertEquals(fact.getBoat1().isKeepMove(),false);
		assertEquals(fact.getBoat2().isKeepMove(),true);	
	}

	
	//@Test 
	public void oppositeTack1 () { 
		fact.getBoat1().setTack(Tack.STARBOARD);
		fact.getBoat2().setTack(Tack.PORT); 
		
		kSession.execute(fact); 
		
		assertEquals(fact.getBoat1().isKeepMove(),true);
		assertEquals(fact.getBoat2().isKeepMove(),false);	
	}
	
	@Test 
	public void oppositeTack2 () { 
		fact.getBoat1().setTack(Tack.PORT);
		fact.getBoat2().setTack(Tack.STARBOARD);
		
		kSession.execute(fact);
		
		assertEquals(fact.getBoat1().isKeepMove(),false);
		assertEquals(fact.getBoat2().isKeepMove(),true);	
	}
	
	@Test 
	public void repostBoatSpeedNegativeTest () {  
		
		fact.getBoat1().setSpeed(345353);
		kSession.execute(fact.getBoat1());
		String ruleName = "Boat is reporting the current speed";		
		List<SpeedMessage> list = fact.getBoat1().getSpeedMessageList(); 
		
		assertEquals(list.contains(new SpeedMessage(ruleName,4444)),false);	
	}
	
	@Test 
	public void repostBoatSpeedPositiveTest () {  
		
		fact.getBoat1().setSpeed(345353);
		kSession.execute(fact.getBoat1());
		String ruleName = "Boat is reporting the current speed";		
		List<SpeedMessage> list = fact.getBoat1().getSpeedMessageList(); 
		
		assertEquals(list.contains(new SpeedMessage(ruleName,345353)),false);	
	}
}
