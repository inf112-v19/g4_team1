# Mandatory Assignment 4 (g4.team1)

#### Introduce a test role. Choose a person who can have a test role (it can be the same person as the teamlead or customer responsibility, or it can be someone else). This person will have the main responsibility for testing the application and can delegate both tasks to be implemented / executed, and find a strategy for testing (eg what to test manually and what is to be tested automatically) in collaboration with the rest of the team. If the team uses manual tests, you have to provide a detailed description of them
Simen agreed to be the tester of our project. We decided that he also will continue with some of the coding and not only run tests since we prefer that the person who implements some functionality should also make the test for it. Simen will look at how everything works together, check if someone has misunderstood anything and so on. 

#### Are there any experiences either team-wise or regarding project methodology worth mentioning? Does the team think the choices you've made are good? If not, what can you do differently to improve the way the team works?
Everyone agreed that usage of branches is very beneficial for the development of the project because anyone can create their own branch and try to tinker with the code a bit. Then the changes can be merged to the original branch using pull requests. We considered using pull requests but then decided that it takes more time to process the requests themselves than to merge the branches ourselves because everyone in a team knows how all the stuff is working.

We found that having a detailed and updated project board helped us work more efficiently. It made us more aware of task done and remaining, this made it easier for people to choose what they want to work with within our goal. 

#### Specify the knowledge shared within the team, and how the transfer of said knowledge is done between team members 
Among the specific things that are shared between group members are core parts/additions to the game that everyone needs to be aware of, e.g. rules, and game class. Things such as implementations of classes is not shared unless we have to. We think that it is more efficient to just ask if someone from the team needs information about something. 

We think the sharing of the knowledge is pretty fluid. During our weekly meetings we discuss the problems we encounter and provide comments about design choices. If nothing helps, we try to brainstorm and find the solution for the particularly hard task. We also use Slack and Facebook to share the knowledge although not that often as of late. 

#### How is the group dynamics?
All in all, the group dynamic is good. We focus on making everyone understand and helping each other. When someone is lost we use pair-programming so we can share thoughts and knowledge.

#### How does communication work for you?
We are communicating pretty actively and meet regularly, usually two times per week. We are using slack (although not as much as we should do) and facebook group chat to communicate with each other. We also share the problems and progress there. We communicate best in the meetings when we discuss problems and so on. Since we spend so much time on discussing problems in the meetings we don’t use slack and facebook that often. We find that when we do this we don’t need to discuss as much in slack/facebook. Since we prefer discussing face to face we chose to spend more time in the meetings on communication and find that this works well for us. 

Retrospective (description of which kind of methodologies the group planned to use, what the actually use, and why) + improvements from the retrospective 
#### Make a brief retrospective in which you consider what you have done so far and what can be improved. This is about project structure, not code. You can of course discuss code, but this is not about error correction, but about how to work and communicate
1. we were planning to use scrum and kanban methodologies. both are actually used and seem to be working. our sprints are ca. 1 week long and sometimes we achieve significant progress during these periods.
2. kanban is used less frequently than it should but still it’s proved to be very useful because we have all the relevant tasks there so we always know what should be done.
3. scrum meetings are regular and successful. most of the progress is achieved during these meetings because we can share the knowledge, brainstorm new ideas and help each other with problems that we could not solve alone.

Since the last delivery we have also added several things in the code, such as the mechanic to choose the cards, made a prototype of game round, experimented with different rendering methods and also tried to solve the pausing functionality between the actions.

#### Agree on maximum three improvement points from the retrospective, which will be followed up during the next sprint
Now when the final deadline is approaching we need to focus on finishing all the requirements provided by the customer. 

Regarding the methodology we are using: we could probably improve the productivity during the sprints because at the moment we usually make a lot of progress during the meetings instead of the sprint period. We should probably also use the kanban board more actively. Regarding the code: our plan is to finish the core mechanics which are ca. 80% ready at the moment of this report. 

The main requirements we need to finish are: 
- functional round from start to finish
- robot interaction with board objects and other robots
- board elements like laser and conveyors

Then we plan to finish implementing other mandatory requirements. Meanwhile, our tester will focus on finding bugs and possibly fixing them as well. After that we will focus on adding some of the less important features if we have enough time, such as making the larger board using several smaller ones, and playing against the AI. 

In conclusion, we decided on:
- Using the Kanban more actively
- Encouraging each other to work more during the sprint in order to get more done.
- Maybe have more meetings

There is also one small thing that needs to be mentioned - one of our group members has two user names on github. sca003 and kassapng are the same user.
## UML Class Diagrams
We haven't changed the class diagrams that much because most of the methods were not changed, only the content of the methods.

![ClassDiagramRoboRally](ClassDiagramRoboRally.png)

![ClassDiagramGameLogic](ClassDiagramGameLogic.png)
