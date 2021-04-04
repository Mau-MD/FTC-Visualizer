# FTC-Visualizer
A Visualizer written in Java that lets you see how a standard Mecanum Drivetrain Robot will behave based in some code. This visualizer has been made with the 
intentation of making custom path functions without needing the physical robot with you. 

The code has exactly the same syntax as normal FTC, so you should not have any problem programming the virtual robot. 

It features odometry and encoder and gyro reading, so if you are planning to program pure pursuit or any path follower algorithm, this is your best chance,
because, yet again, you don't need any physical robot with you! It also features Telemetry so you can track exactly what's happening with your robot.

# Installation 
Just clone the repository using git (https://github.com/Mau-MD/FTC-Visualizer.git), or download the entire project by clicking on Code -> Download Zip. 

# Usage
All the code must be edited inside the TeamCode package. The main class will be like your "Autonomous Program", so you should write everything there. 
Also, if you would like to create external classes, you can do it without any problem, just remember to write them inside TeamCode. 

There's a Config Class, you should give it a look specially if you want to simulate odometry/encoder readings. 

Currently the "Main" class has an example that covers almost everything you can do with the simulator. You will get used to really fast because, well, it's almost
the same as FTC ;)

#Future Updates
Enhaced Telemtry functions that lets you add shapes to the visualizer (specially useful if you want to implement complex path algorithms)
