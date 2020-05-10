import gym
import numpy as np
from gym import error, spaces, utils
from gym.utils import seeding

UP = 0
RIGHT = 1
DOWN = 2
LEFT = 3

class ZhedEnv(gym.Env):
  metadata = {'render.modes': ['human']}
  
  # Playable Squares -> [ (x, y, value), etc. ]
  def __init__(self, playable_squares, board_size):
    super(ZhedEnv, self).__init__()
    self.playable_squares = playable_squares
    self.num_squares = len(playable_squares)

    self.observation_space = spaces.Box(-2,9,(board_size, board_size))
    self.action_space = spaces.Discrete(4*len(playable_squares))

    self,reset()
    print('Inited gym_zhed')

  def step(self, action):
    print('Step called')

  def reset(self):
    self.state = np.zeros((board_size, board_size))
    self.played_squares = 0
    self.done = False
    print('Reset called')

  def render(self, mode='human'):
    print('Render called')

  def close(self):
    print('Close called')

  # Game logic related functions
  def play(square, direction):
    
