import gym
import numpy as np
from gym import error, spaces, utils
from gym.utils import seeding

class ZhedEnv(gym.Env):
    metadata = {'render.modes': ['human']}

    # Playable Squares -> [ (x, y, value), etc. ]
    # Goal Square -> (x, y)
    def __init__(self, playable_squares, goal_square, board_width, board_height):
        super(ZhedEnv, self).__init__()
        self.playable_squares = playable_squares
        self.goal_square = goal_square
        self.num_squares = len(playable_squares)
        self.board_width = board_width
        self.board_height = board_height
        self.valid_directions = ['UP', 'RIGHT', 'DOWN', 'LEFT']

        self.observation_space = spaces.Box(-2, 9, (board_width, board_height))
        self.action_space = spaces.Discrete(4*len(playable_squares))
        self.reset()
        print('Environment initialized')

    def step(self, action):
        print('Step called')

    def reset(self):
        self.state = np.zeros((self.board_height, self.board_width), dtype=np.int)
        self.init_state()

        self.played_squares = []
        self.played_coords = []
        self.done = False
        return self.state

    def render(self, mode='human'):
        y = -1
        for line in self.state:
            x = -1
            y += 1
            print('| ', end='')
            for cell in line:
                x += 1
                if cell == -1 or (x, y) in self.played_coords:
                    print('X', end=' | ')
                elif cell == -2:
                    print('G', end=' | ')
                elif cell == 0:
                    print(' ', end=' | ')
                else:
                    print(cell, end=' | ')
            print('\n', end='')

    def close(self):
        print('Close called')

    def init_state(self):
        for square in self.playable_squares:
            x = square[0]
            y = square[1]
            value = square[2]
            self.state[y][x] = value
        
        goal_x = self.goal_square[0]
        goal_y = self.goal_square[1]
        self.state[goal_y][goal_x] = -2

    # Game Logic related functions
    def play(self, square_index, direction):
        if square_index not in range(0, len(self.playable_squares)):
            print('Invalid square index')
            return
        if direction not in self.valid_directions:
            print('Invalid direction')
            return
        if square_index in self.played_squares:
            print('Square already played')
            return

        square = self.playable_squares[square_index]
        x = square[0]
        y = square[1]
        value = square[2]
        self.played_squares.append(square_index)
        self.played_coords.append((x, y))

        i = 1
        if direction == 'UP':
            while value > 0 and y - i >= 0:
                if self.fill(x, y - i):
                    value -= 1
                i += 1
        elif direction == 'RIGHT':
            while value > 0 and x + i < self.board_width:
                if self.fill(x + i, y):
                    value -= 1
                i += 1
        elif direction == 'DOWN':
            while value > 0 and y + i < self.board_height:
                if self.fill(x, y + i):
                    value -= 1
                i += 1
        elif direction == 'LEFT':
            while value > 0 and x - i >= 0:
                if self.fill(x - i, y):
                    value -= 1
                i += 1

    def goal_filled(self):
        goal_x = self.goal_square[0]
        goal_y = self.goal_square[1]
        return self.state[goal_y][goal_x] == -1

    def no_more_moves(self):
        return self.num_squares == len(self.played_squares)

    def fill(self, x, y):
        square = self.state[y][x]

        if square == 0 or square == -2:
            self.state[y][x] = -1
            return True

        return False

class ZhedEnvFromLevel(ZhedEnv):
    def __init__(self, level):
        if level < 10:
            filename = '00' + str(level) + '.txt'
        elif level < 100:
            filename = '0' + str(level) + '.txt'
        else:
            filename = str(level) + '.txt'

        playable, goal, width, height = self.load_file('../levels/' + filename)
        super(ZhedEnvFromLevel, self).__init__(playable, goal, width, height)

    def load_file(self, filepath):
        f = open(filepath, 'rt')

        playable = []
        goal = None

        y = -1
        for line in f:
            x = -1
            y += 1
            for char in line:
                x += 1
                if char == '.' or char == '\n':
                    continue
                elif char == 'X':
                    goal = (x, y)
                else:
                    playable.append((x, y, int(char)))

        if goal == None:
            print('File has no goal square!')
            exit()
        elif len(playable) == 0:
            print('File has no playable squares!')
            exit()

        return playable, goal, x + 1, y + 1