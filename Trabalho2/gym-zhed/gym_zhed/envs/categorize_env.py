import gym
import numpy as np
import math
import os

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
        self.registered_states = []
        self.valid_directions = ['UP', 'RIGHT', 'DOWN', 'LEFT']
        self.square_classes = np.array([self.goal_square],[])

        
        # The observation space is a list of all possible
        #states (represented as an int).
        # For each square there are 4 possible moves, which means that there are 4**N (4 to the power of N) sets of moves
        #However, these sets do not count with the order by which the square is played, hence the N! (factorial of N) factor
        total_states = int(math.factorial(self.num_squares) * math.pow(4, self.num_squares) + 1)
        self.observation_space = spaces.Discrete(total_states)

        # The action space is a list of 4*N values
        #representing possible actions (4 directions for each playable square)
        # Note that a play on square Z has actions between Z*4 and Z*4 + 3
        self.action_space = spaces.Discrete(4*self.num_squares)
        self.reset()
        print('Environment initialized')
    
    def getSquare(self, x, y):
        for square in self.playable_squares:
            if (square[0] == x and square[1] == y):
                return square
  
        return None

    def getClass(self, square):
        for sq_class in self.square_classes:
            for sq in self.square_classes[sq_class]:
                if sq == square:
                    return sq_class
        
        return -1

    def updateClass(self, square, oldClass, adjClass):
        for sq in self.square_classes[oldClass]:
            if sq == square:
                self.square_classes = np.delete(self.square_classes, sq)

        if adjClass == self.square_classes.size:
            self.square_classes.append([square])
        else:
            self.square_classes[adjClass + 1].append(square)

    def categorizeSquare(self, square):
        x = square[0]
        y = square[1]
        square_class = getClass(square)
        for sq in self.playable_squares:
            xn = sq[0]
            yn = sq[1]
            num = sq[2]
            if x == xn and abs(y-yn) <= num:
                adj_square = getSquare(x,y)
                adj_square_class = getClass(adj_square)
                if (adj_square_class > 0 and adj_square_class < square_class):
                    updateClass(square, square_class, adj_square_class)
                else:
                    self.square_classes[-1].append(square)
    
    def categorizeLvl(self) :

        x = self.goal_square[0]
        y = self.goal_square[1]

        for x in range(self.board_width):
            square = getSquare(x,y)
            if square != None:
                self.square_classes[1].append(square)

        for y in range(self.board_height):
            square = getSquare(x,y)
            if square != None:
                self.square_classes[1].append(square)

        for square in self.playable_squares:
            categorizeSquare(square)

    def step(self, action):
        square_index = action // 4
        direction = self.valid_directions[action % 4]
        valid = self.play(square_index, direction)

        if self.goal_filled():
            reward = 10
            done = True
        elif self.no_more_moves():
            reward = -10
            done = True
        elif not valid:
            reward = -2
            done = False
        else:
            reward = 2
            done = False

        return self.get_state(), reward, done, {'debug': 'None'}

    def reset(self):
        self.board = np.zeros((self.board_height, self.board_width), dtype=np.int)
        self.init_state()

        self.played_squares = []
        self.played_coords = []
        self.done = False
        return self.get_state()

    def render(self, mode='human'):
        y = -1
        for line in self.board:
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
        print('\n', end='')

    def init_state(self):
        for square in self.playable_squares:
            x = square[0]
            y = square[1]
            value = square[2]
            self.board[y][x] = value
        
        goal_x = self.goal_square[0]
        goal_y = self.goal_square[1]
        self.board[goal_y][goal_x] = -2

    def get_state(self):
        index = -1
        for registered_board in self.registered_states:
            index += 1
            if (self.board == registered_board).all():
                #print(f'Found board in index: {index}')
                return index
        
        self.registered_states.append(self.board)
        index += 1 #index of last inserted element
        #print(f'Added new state at index: {index}')
        return index

    # Game Logic related functions
    def play(self, square_index, direction):
        if square_index not in range(0, len(self.playable_squares)):
            print('Invalid square index')
            return False
        if direction not in self.valid_directions:
            print('Invalid direction')
            return False
        if square_index in self.played_squares:
            print('Square already played')
            return False

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

        return True

    def play_coords(self, x, y, direction):
        for square_index in range(0, len(self.playable_squares)):
            square = self.playable_squares[square_index]
            if square[0] == x and square[1] == y:
                self.play(square_index, direction)

    def goal_filled(self):
        goal_x = self.goal_square[0]
        goal_y = self.goal_square[1]
        return self.board[goal_y][goal_x] == -1

    def no_more_moves(self):
        return self.num_squares == len(self.played_squares)

    def fill(self, x, y):
        square = self.board[y][x]

        if square == 0 or square == -2:
            self.board[y][x] = -1
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

        dir_path = os.path.dirname(os.path.abspath(__file__))
        filepath = os.path.join(dir_path, "levels", filename)

        playable, goal, width, height = self.load_file(filepath)
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