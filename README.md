# SmartCampus - Benchmark

The project allows to test the SmartCampus middleware from collector to storage in database.
The idea is to launch a number of simulation of physical sensors through a service call and once the simulations have finished to query the middleware database and check if the awaited values are stored.
The simulation is launched with the smartcampus sensors simulation project. Simulations can be parametrized by :
 - the number of sensors
 - the name of the sensors group (sensors named NAME_0, NAME_1, etc. have to be defined in the smartcampus sensors database)
 - the duration of the simulation
 - the period at which data is sent
 - the duration we want to wait after the simulation to query the database
 - whether we will query for virtual of physical data 
		if virtual,  NAME_0V, NAME_1V, etc. have to be defined in the SmartCampus sensors database. The simulation launched will generate random doubles in this case.
		if not virtual, the simulator will generate random booleans
 - whether the sensors send data each period or only when its value changes


 ## How to use it

 1. SmartCampus middleware's must be deployed (see associated documentation)
 2. The simulation framework web service has to be deployed (see associated documentation)
 3. The simulation services IP addresses must be changed in the project Benchmark class
 4. Benchmark scenarios can be defined and lauched in the ScenarioRunner class
