import gym
from gym import error, spaces, utils
from gym.utils import seeding

class ZhedEnv(gym.Env):
  metadata = {'render.modes': ['human']}
  
  def __init__(self):
    print('Inited gym_zhed')

  def step(self, action):
    

  def reset(self):
    

  def render(self, mode='human'):


  def close(self):
    