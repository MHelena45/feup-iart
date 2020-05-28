import sys
import gym
import gym_zhed
import matplotlib.pyplot as plt

from stable_baselines.bench.monitor import Monitor
from stable_baselines.common.policies import MlpPolicy
from stable_baselines import PPO2

# uncomment if the tensorflow binary isnt compiled to use AVX
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

import warnings
warnings.filterwarnings("ignore")

solvable_levels = [1, 2, 3, 4, 5, 6, 7]
total_timesteps = 2000000

def original_params(thislevel):
    env = gym.make('zhedLevel' + str(thislevel) + '-v0')
    env = Monitor(env, 'models/PPO2/logs/logOriginal_' + str(thislevel))
    model = PPO2(MlpPolicy, env, cliprange=0.1, verbose=1) #CP = 0.2
    model.learn(total_timesteps=total_timesteps, log_interval=1)
    model.save('models/PPO2/ppo2_Olv' + str(thislevel))

def first_params(thislevel):
    env = gym.make('zhedLevel' + str(thislevel) + '-v0')
    env = Monitor(env, 'models/PPO2/logs/logFirst_' + str(thislevel))
    model = PPO2(MlpPolicy, env, cliprange=0.2, verbose=1)
    model.learn(total_timesteps=total_timesteps, log_interval=1)
    model.save('models/PPO2/ppo2_Flv' + str(thislevel))

def second_params(thislevel):
    env = gym.make('zhedLevel' + str(thislevel) + '-v0')
    env = Monitor(env, 'models/PPO2/logs/logSecond_' + str(thislevel))
    model = PPO2(MlpPolicy, env, cliprange=0.3, verbose=1)
    model.learn(total_timesteps=total_timesteps, log_interval=1)
    model.save('models/PPO2/ppo2_Slv' + str(thislevel))

for level in solvable_levels:
    original_params(level)
    first_params(level)
    second_params(level)