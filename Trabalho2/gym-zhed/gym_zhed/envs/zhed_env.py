import gym
from gym import error, spaces, utils
from gym.utils import seeding

class ZhedEnv(gym.Env):
  metadata = {'render.modes': ['human']}
  
  def __init__(self):
    print('Inited gym_zhed')

  def step(self, action):
    print('Step called')

  def reset(self):
    print('Reset called')

  def render(self, mode='human'):
    print('Render called')

  def close(self):
    print('Close called')
