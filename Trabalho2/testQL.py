import gym
import gym_zhed
import numpy as np
import random
import matplotlib.pyplot as plt

solvable_levels = [1,2,3,4,5,6,7,8,9,10,11,12]

# @hyperparameters
total_episodes = 50000      # Total episodes
learning_rate = 0.01        # Learning rate
max_steps = 1000             # Max steps per episode
gamma = 0.99                # Discounting rate

# Exploration parameters
epsilon = 1.0                 # Exploration rate
max_epsilon = 1.0             # Exploration probability at start
min_epsilon = 0.1            # Minimum exploration probability 
decay_rate = 0.001             # Exponential decay rate for exploration prob

for level in solvable_levels:
    env = gym.make('zhedLevel' + str(level) + '-v0')
    state_size = env.observation_space.n
    action_size = env.action_space.n
    qtable = np.zeros((state_size, action_size))

    rewards = []
    epsilon = 1.0

    for episode in range(total_episodes):
        state = env.reset()
        step = 0
        done = False
        total_rewards = 0
        
        #print(f'First state: {state}')
        for step in range(max_steps):
            exp_exp_tradeoff = random.uniform(0, 1)
        
            if exp_exp_tradeoff > epsilon:
                action = np.argmax(qtable[state, :]) #exploitation
            else:
                action = env.action_space.sample() #exploration
            
            new_state, reward, done, info = env.step(action) #take action and observe results
            #print(f'new_state: {new_state}; reward: {reward}; done: {done}; info: {info}')

            #updating the table using Bellman Equation
            qtable[state, action] = qtable[state, action] + learning_rate * (reward + gamma * np.max(qtable[new_state, :]) - qtable[state, action])
            #cumulative rewards until now
            total_rewards = total_rewards + reward

            state = new_state
            if done == True:
                #print('Done')
                break

        episode += 1
        #reducing exploration rate
        epsilon = min_epsilon + (max_epsilon - min_epsilon) * np.exp(-decay_rate * episode)
        rewards.append(total_rewards)

    #making plot
    x_axis = [episode for episode in range(total_episodes)]
    y_axis = []
    acc = 0
    for reward in rewards:
        acc += reward
        y_axis.append(acc)

    plt.title('Level ' + str(level))
    plt.xlabel('episode')
    plt.ylabel('acc_reward')
    plt.plot(x_axis, y_axis)
    plt.savefig('graphs/QL/level' + str(level) + '-Q.png')