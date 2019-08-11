package br.sp.edimilsonestevam.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import br.sp.edimilsonestevam.starwars.People;
import br.sp.edimilsonestevam.starwars.Planet;

@RunWith(org.junit.runners.Suite.class)
@SuiteClasses({
		
	People.class,
	Planet.class
	
})

public class TestSuite{


		
	}
