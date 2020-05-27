import sys
import gym
import gym_zhed
from stable_baselines import PPO2

if(len(sys.argv) != 2):
    print("Invalid number of arguments, please specify the level to solve")
    exit()

level = sys.argv[1]

env = gym.make('zhedLevel' + str(level) + '-v0')
model = PPO2.load('models/PPO2/ppo2_lv' + str(level) + '.zip')
dones = False

obs = env.reset()
while dones != True:
    action, _states = model.predict(obs)
    obs, rewards, dones, info = env.step(action)
    env.render()

if env.goal_filled():
    print("Puzzle Solved!!!")