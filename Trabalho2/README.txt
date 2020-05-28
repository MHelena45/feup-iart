**Reinforcement learning as a tool to solve Zhed games**

By: Carlos Albuquerque, up201706735
    Maria Helena Ferreira, up201704508
    Pedro Mendes, up201704219

Project: Zhed - 1E

Description: an implementation of Zhed game as an openAI Gym environment to teach agents, through
Reinforcement Learning, to play the game.

To clarify our work, here is an explanation about each folder's content:

* aux_scripts - Auxiliary script to count the number of squares of each level and results;
            this information was very useful to estimate our memory limits when training the agents.

* graphs - Graphical results of our agents training, organized in two subfolders, each for an algorithm
            we used - PPO and Q-Learning.

* gym-zhed - Zhed game modeled as an environment, following the rules and tips found here:
            https://github.com/openai/gym/blob/master/docs/creating-environments.md .

* models - The resultant agents models; these were only generated for PPO algorithm since we used the
            stable-baselines lib which already includes methods to save your trained model.

* <root_files> - Scripts and notebooks used to test, train and plot the agents; in order to run our code
            simply open a notebook and run the code.

About each notebook:

* Continuous Q-Learning - A failed try to teach an agent using Q-Learning and a continuous environment.

* PPO - Code used to initially train and test an agent that solves our episodic environment using Proximal Policy Optimization.

* Q-Learning - Code used to initially train and test an agent that solves our episodic environment using Q-Learning.

* SARSA - Code used to initially train and test an agent that solves our episodic environment using SARSA.

About each script:

* generateGraphs - Script used to generate result graphics based on log files of PPO trained agent.

* testPPO - Script used to train 3 different agents (with different parameters) using PPO.

* testQL - Script used to train 3 different agents (with different parameters) using Q-Learning and extracting its result graphics.

Conclusion: We achieve some good results but could not go further than 7 levels because of time and memory restrictions.
It was very interesting to see how hyper parameters could vastly change the efficiency of the agent! It was an extremely
interesting work and we also got to write a scientific paper about it!

