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
        self.flag_nr = 10
        
        # The observation space is a list of all possible states according to level completion
        # <= 20 %
        # <= 40 %
        # <= 60 %
        # <= 80 %
        # <= 100%
        self.observation_space = spaces.Discrete(5)

        # The action space is a list of 2^N values
        # Values representing the possible actions through flags
        # [0] Quadrado em linha com objetivo
        # [1] Quadrado adjacente ao quadrado em linha
        # [2] Quadrado fronteira
        # [3] Maior distância ?
        # [4] Menor distância ?
        # [5] Maior nº de quadrado
        # [6] Menor nº de quadrado
        # [7] Jogar para a direção do objetivo
        # [8] Jogar na direção com + interações
        # [9] Jogar na direção com - interações
        self.action_space = spaces.Box(np.zeros(self.flag_nr), np.ones(self.flag_nr), dtype=np.int)
        self.reset()
        print('Environment initialized')

    def getInLineSqrs(self):
        squares = []

        x = self.goal_square[0]
        y = self.goal_square[1]

        for sq in self.playable_squares:
            sqX = sq[0]
            sqY = sq[1]
            if x == sqX or y == sqY:
                squares.append(sq)

        return squares
    
    def getInLineAdjSqrs(self, inLineSqrs):
        squares = []

        gx = self.goal_square[0]
        gy = self.goal_square[1]

        for sq in inLineSqrs:
            sqX = sq[0]
            sqY = sq[1]
            for adj in self.playable_squares:
                adjX = adj[0]
                adjY = adj[1]
                num = adj[2]
                if gx < adjX < sqX or sqX < adjX < gx \
                or gy < adjY < sqY or sqY < adjY < gy:
                    squares.append(adj)

        return squares
    
    def getFrontierSqrs(self):
        minX = self.playable_squares[0][0]
        minY = self.playable_squares[0][1]
        maxX = 0
        maxY = 0
        
        for sq in self.playable_squares:
            sqX = sq[0]
            sqY = sq[1]
            if sqX < minX: minX = sqX
            if sqY < minY: minY = sqY
            if sqX > maxX: maxX = sqX
            if sqY > maxY: maxY = sqY

            frontier = []

        for sq in self.playable_squares:
            sqX = sq[0]
            sqY = sq[1]
            if sqX == minX or sqY == maxX or sqY == minY or sqY == maxY:
                frontier.append(sq)

        return frontier

    def calcDist(self, square):
        x = square[0]
        y = square[1]
        gx = self.goal_square[0]
        gy = self.goal_square[1]

        return abs(x-gx) + abs(y-gy)

    def getClosestSq(self):

        closest = self.playable_squares[0]

        for sq in self.playable_squares:
            if calcDist(sq) < calcDist(closest): closest = sq

        return closest
    
    def getFarthestSq(self):

        farthest = 0

        for sq in self.playable_squares:
            if calcDist(sq) > calcDist(farthest): farthest = sq

        return farthest
    
    def getSmallestSq(self):

        smallest = self.playable_squares[0]

        for sq in self.playable_squares:
            if sq[2] < smallest[2]: smallest = sq

        return smallest

    def getBiggestSq(self):

        biggest = self.playable_squares[0]

        for sq in self.playable_squares:
            if sq[2] > biggest[2]: biggest = sq

        return biggest


    # So vale a pena perguntar a quadrados nao em linha com o goal
    def getGoalDirections(self, square):
        sqX = square[0]
        sqY = square[1]
        gx = self.goal_square[0]
        gy = self.goal_square[1]

        dir = None

        if sqX > gx and sqY > gy: dir = 0 # DOWN RIGHT
        if sqX > gx and sqY < gy: dir = 1 # UP RIGHT
        if sqX < gx and sqY < gy: dir = 2 # UP LEFT
        if sqX < gx and sqY > gy: dir = 3 # DOWN LEFT

        return dir

    def step(self, action):
        
        if action[3] == 1:
            farthest = getFarthestSq()
        elif action[4] == 1:
            closest = getClosestSq()
        elif action[5] == 1:
            biggest = getBiggestSq()
        elif action[6] == 1:
            smallest = getSmallestSq()
        else:
            inLineSqrs = getInLineSqrs()
            if action[0] == 1:
                squares = inLineSqrs
            elif action[1] == 1:
                squares = getInLineAdjSqrs(inLineSqrs)
            if action[2] == 1:
                frontier = getFrontierSqrs()
                squares = [square for square in squares if square in frontier]
            
            
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
        percent = len(self.played_squares) / len(self.playable_squares)
        if percent <= 0.2:
            return 0
        elif percent <= 0.4:
            return 1
        elif percent <= 0.6:
            return 2
        elif percent <= 0.8:
            return 3
        # percent <= 100 %
        return 4

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