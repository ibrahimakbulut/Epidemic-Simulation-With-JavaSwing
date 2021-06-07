# Epidemic-Simulation-With-JavaSwing
implementation for visual simulation of an epidemic within a human society

I have a class called Individual. I have another class called IndividualBuilder to 
create objects of this class. I used builder pattern here because I didn't want to 
use parameterized contructor while creating the Individual objects. Another 
reason is that the user may not always provide a certain number of parameters 
for people. In this case, creating the object with the parameters given by the 
Builder pattern provides flexibility. The Individual constructor is called when 
the user adds individuals in bulk from the interface. This constructor randomly 
assigns fields within the object. However, if the user enter individual attributes 
and adds an individual, Individual is created using IndividualBuilder.
Actually, the information is in the SimulationModel class. So I realized the 
Mediator class, which will realize and control the interaction between 
individuals, as the inner class of the SimulationModel class. This inner 
MediatorTask class extends TimerTask class so it can be scheduled by Timer 
objects. From the moment the simulation starts, the MediatorTask is related 
with Timer object to control all interaction and runs continuously in the 
background with the Timer thread.

By adding ActionListener to the elements in the GUI in Java Swing, they are 
enabled to work responsibly. Because the thread runs automatically in the 
background to listen user interaction. When the view element is clicked in this 
ActionListener, the desired action is called over the model object. When the 
user presses pause, the schedule of MediatorTask scheduled on the timer is 
canceled. The point to note here; When the user presses the pause button, 
MediatorTask is provided to safely finish its operations. While implementing 
the GUI here, of course, I used the classic MVC compound pattern. Observer 
pattern vs. As the MediatorTask object manages the Individul relations, it does 
its job on each timer trigger and notifies the observers.
While the MediatorTask class works with the timer in the background and 
controls all interactions betweens individuals, the HospitalTask class acts as a 
hospital by working with the timer in the background. The point is that while 
adding and removing the patient queue and the population, the hospital locks these structures and does not allow them to be used by another thread until 
they are done, that is, adding or removing them. In this way, synchronization is 
achieved.

How to work Simulation, what is the logic ?
-The user can add Individual by giving its properties or by entering the number 
of how many Individuals it will add, it can add in bulk. In this case, the 
properties of those added will be random.
- The user is expected to enter the mortality rate and spreading factor to be 
used throughout the simulation. After both of these values are entered, it will 
not be possible to add new Individuals and these features will be disabled. So 
don't forget to add as many Individuals as you want before entering these 
values.
- If the first individual addition process is a single addition process, the added 
person automatically becomes an infected person. Therefore, it is 
recommended to do the first addition in bulk.
- While entering the value, it is assumed that the given intervals will be taken 
into consideration and the cases of wrong value input have not been checked. 
Please enter the values in the given ranges.
Steps :
- First, add as many individual as you want to add.
- Then enter the mortality rate and spreading factor values. It will automatically 
start the simulation processing.
- You can then stop and start the simulation as you wish
