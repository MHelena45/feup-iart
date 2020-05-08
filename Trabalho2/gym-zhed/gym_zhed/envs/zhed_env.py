import gym
from gym import error, spaces, utils
from gym.utils import seeding

class ZhedEnv(gym.Env):
  metadata = {'render.modes': ['human']}
  env = gym.make("zhed-v0")
  

  def __init__(self):
    action_size = env.action_space.n
    state_size = env.observation_space.n
    print(f'aciton size: {action_size}, state size: {state_size}')
    state = env.observation_space

  def step(self, action):
    ...

  def reset(self):
    ...

  def render(self, mode='human'):
    qtable = np.zeros((state_size, action_size))
    print(qtable)

  def close(self):
    env.close()