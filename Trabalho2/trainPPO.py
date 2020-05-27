import gym
import gym_zhed

from stable_baselines.common.policies import MlpPolicy
from stable_baselines.common import make_vec_env
from stable_baselines import PPO2

solvable_levels = [8, 9, 10, 11, 12]

# multiprocess environment
for level in solvable_levels:
    env = make_vec_env('zhedLevel' + str(level) + '-v0', n_envs=1)
    model = PPO2(MlpPolicy, env, verbose=1)
    model.learn(total_timesteps=1000000)
    model.save("models/PPO2/ppo2_lv" + str(level))
    del model # remove to demonstrate saving and loading