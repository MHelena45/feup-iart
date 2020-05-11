import gym
import numpy as np
from gym import error, spaces, utils
from gym.utils import seeding


class ZhedEnv(gym.Env):
    metadata = {'render.modes': ['human']}

    # Playable Squares -> [ (x, y, value), etc. ]
    # Goal Square -> (x, y)
    def __init__(self, playable_squares, goal_square, board_size):
        super(ZhedEnv, self).__init__()
        self.playable_squares = playable_squares
        self.goal_square = goal_square
        self.num_squares = len(playable_squares)
        self.board_size = board_size
        self.valid_directions = {'UP': 0, 'RIGHT': 1, 'DOWN': 2, 'LEFT': 3}

        self.observation_space = spaces.Box(-2, 9, (board_size, board_size))
        self.action_space = spaces.Discrete(4*len(playable_squares))

        self.reset()
        print('Inited gym_zhed')

    def step(self, action):
        print('Step called')

    def reset(self):
        self.state = np.zeros((self.board_size, self.board_size))
        self.played_squares = 0
        self.done = False
        print('Reset called')

    def render(self, mode='human'):
        print('Render called')

    def close(self):
        print('Close called')

    def play(self, square, direction):
        if square not in range(0, len(self.playable_squares)):
            return False
        if direction not in self.valid_directions:
            return False

        played_square = self.playable_squares[square]
        x = played_square[0]
        y = played_square[1]
        value = played_square[2]

        i = 1

        if direction == 'UP':
            while value > 0 and y - i >= 0:
                if self.fill(x, y - i):
                    value -= 1
                i += 1
        elif direction == 'RIGHT':
            while value > 0 and x + i < self.board_size:
                if self.fill(x + i, y):
                    value -= 1
                i += 1
        elif direction == 'DOWN':
            while value > 0 and y + i < self.board_size:
                if self.fill(x, y + i):
                    value -= 1
                i += 1
        elif direction == 'LEFT':
            while value > 0 and x - i >= 0:
                if self.fill(x - i, y):
                    value -= 1
                i += 1

    def fill(self, x, y):
        square = self.state[y][x]

        if square == 0 or square == -2:
            self.state[y][x] = 1
            return True

        return False
