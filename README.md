Ping Pong Game
This project implements a Ping Pong game with a referee app and multiple player apps. The referee app manages the game and communicates with player apps running on different ports.

The Ping Pong game consists of:

A Referee App that manages the game logic and coordinates between players.
Player Apps that represent individual players participating in the game.
The referee app listens for events from player apps and determines the game state, including scores and turns.

Architecture
Referee App: Manages game state, receives and sends notifications to players.
Player Apps: Send notifications to the referee app and receive updates.
Requirements
Java 22 or higher

Setup
Clone the Repository:

In windows , go to your desired folder to clone the repo

https://github.com/sakshic75/pingpong.git

The project is already build .


Running the Apps
Referee App
Run the Referee app on port 8081:


Go inside the folder with a new terminal

/RefereeApp/out/artifacts/RefereeApp_jar

and then run 

java -jar RefereeApp.jar -n 2 -p 8081 -u localhost


Open n new terminals and with each new terminal 

Here n is mentioned as number of players

Example for 2 players

Player Apps
Run 2 Player apps on different ports:
using 
cd /PlayersApp/out/artifacts/PlayersApp_jar
java -jar PlayersApp.jar -n Jason -r 8081 -c http://127.0.0.1 -p 8083 -s localhost -d 10

again on a different terminal :
using 
cd /PlayersApp/out/artifacts/PlayersApp_jar
java -jar PlayersApp.jar -n Nick -r 8081 -c http://127.0.0.1 -p 8082 -s localhost -d 10

The app should be running and the game report will be stored 


License
This project is licensed under the MIT License. See the LICENSE file for details.

