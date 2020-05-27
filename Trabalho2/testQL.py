import gym
import gym_zhed
import numpy as np
import random
import matplotlib.pyplot as plt

# @hyperparameters
total_episodes = 200000      # Total episodes
max_steps = 100             # Max steps per episode
gamma = 0.99                # Discounting rate

# Exploration parameters
max_epsilon = 1.0             # Exploration probability at start
min_epsilon = 0.1            # Minimum exploration probability 
decay_rate = 0.001             # Exponential decay rate for exploration prob

def original_params(level):
    env = gym.make('zhedLevel' + str(level) + '-v0')
    state_size = env.observation_space.n
    action_size = env.action_space.n
    qtable = np.zeros((state_size, action_size))

    rewards = []
    epsilon = 1.0
    learning_rate = 0.01

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
        
    return rewards

def first_params(level):
    env = gym.make('zhedLevel' + str(level) + '-v0')
    state_size = env.observation_space.n
    action_size = env.action_space.n
    qtable = np.zeros((state_size, action_size))

    rewards = []
    epsilon = 0.4
    learning_rate = 0.01

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

        rewards.append(total_rewards)
        
    return rewards

def second_params(level):
    env = gym.make('zhedLevel' + str(level) + '-v0')
    state_size = env.observation_space.n
    action_size = env.action_space.n
    qtable = np.zeros((state_size, action_size))

    rewards = []
    epsilon = 1.0
    learning_rate = 0.3

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
        
    return rewards

def third_params(level):
    env = gym.make('zhedLevel' + str(level) + '-v0')
    state_size = env.observation_space.n
    action_size = env.action_space.n
    qtable = np.zeros((state_size, action_size))

    rewards = []
    epsilon = 0.4
    learning_rate = 0.3

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

        rewards.append(total_rewards)
        
    return rewards

def convert_to_axis(rewards):
    result = []
    acc = 0
    for reward in rewards:
        acc += reward
        result.append(acc)
    
    return result

solvable_levels = [1,2,3,4,5,6,7,8,9,10,11,12]

rwO = original_params(5)
rw1 = first_params(5)
rw2 = second_params(5)
rw3 = third_params(5)

#making plot
x_axis = [episode for episode in range(total_episodes)]
y_O = convert_to_axis(rwO)
y_1 = convert_to_axis(rw1)
y_2 = convert_to_axis(rw2)
y_3 = convert_to_axis(rw3)

plt.clf()
plt.cla()
plt.title('Level ' + str(5))
plt.xlabel('episode')
plt.ylabel('acc_reward')
plt.plot(x_axis, y_O, '-', label="E=dynamic,A=0.01")
plt.plot(x_axis, y_1, '--', label="E=0.4,A=0.01")
plt.plot(x_axis, y_2, '-.', label="E=dynamic,A=0.3")
plt.plot(x_axis, y_3, ':', label="E=0.4,A=0.3")
plt.legend(loc=2)
plt.savefig('graphs/QL/level' + str(5) + '-varying.png')