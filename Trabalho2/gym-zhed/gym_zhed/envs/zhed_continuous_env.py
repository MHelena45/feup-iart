import gym
import numpy as np
import math
import os

from gym import error, spaces, utils
from gym.utils import seeding

class ZhedContinuousEnv(gym.Env):
    metadata = {'render.modes': ['human']}

    # Playable Squares -> [ (x, y, value), etc. ]
    # Goal Square -> (x, y)
    def __init__(self, playable_squares, goal_square, board_width, board_height):
        super(ZhedContinuousEnv, self).__init__()
        self.initial_playable_squares = playable_squares
        self.goal_square = goal_square
        self.num_squares = len(playable_squares)
        self.board_width = board_width
        self.board_height = board_height
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
        # [3] Maior distância ao quadrado objetivo?
        # [4] Menor distância ao quadrado objetivo?
        # [5] Maior nº de quadrado
        # [6] Menor nº de quadrado
        # [7] Jogar para a direção do objetivo
        # [8] Jogar na direção com + interações
        # [9] Jogar na direção com - interações
        self.action_space = spaces.Box(np.zeros(self.flag_nr), np.ones(self.flag_nr), dtype=np.int)
        self.reset()
        print('Environment initialized')

    def step(self, action):
        self.update_square_arrays()
        chosen_squares = self.playable_squares.copy()

        if action[0] == 1:
            chosen_squares = self.filter_common(self.inline_squares, chosen_squares)
        if action[1] == 1:
            chosen_squares = self.filter_common(self.inline_adj_squares, chosen_squares)
        if action[2] == 1:
            chosen_squares = self.filter_common(self.frontier_squares, chosen_squares)
        if action[3] == 1:
            chosen_squares = self.filter_common(self.farthest_squares, chosen_squares)
        if action[4] == 1:
            chosen_squares = self.filter_common(self.closest_squares, chosen_squares)
        if action[5] == 1:
            chosen_squares = self.filter_common(self.most_value_squares, chosen_squares)
        if action[6] == 1:
            chosen_squares = self.filter_common(self.least_value_squares, chosen_squares)

        if not chosen_squares: #list is empty
            #print('Empty squares')
            return self.get_state(), -5, False, {'debug': 'None'}
        
        #print('NOT Empty squares !!!')
        square = chosen_squares[0]
        max_interactions, min_interactions = self.get_interactions_directions(square)
        chosen_directions = []

        if action[7] == 1:
            chosen_directions = self.filter_common(self.get_goal_directions(square), chosen_directions)
        else:
            chosen_directions = self.filter_common(self.get_opp_goal_directions(square), chosen_directions)
        if action[8] == 1:
            chosen_directions = self.filter_common(max_interactions, chosen_directions)
        if action[9] == 1:
            chosen_directions = self.filter_common(min_interactions, chosen_directions)

        if not chosen_directions: #list is empty
            #print('Empty directions')
            return self.get_state(), -5, False, {'debug': 'None'}
        
        #print('NOT Empty directions !!!')
        valid = self.play(square, chosen_directions[0])

        if self.goal_filled():
            reward = 1000
            done = True
        elif self.no_more_moves():
            reward = -1000
            done = True
        elif not valid:
            reward = -10
            done = False
        else:
            reward = 10
            done = False

        return self.get_state(), reward, done, {'debug': 'None'}

    def reset(self):
        self.board = np.zeros((self.board_height, self.board_width), dtype=np.int)
        self.playable_squares = self.initial_playable_squares.copy()
        self.init_state()
        self.update_square_arrays()

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
        for square in self.initial_playable_squares:
            x = square[0]
            y = square[1]
            value = square[2]
            self.board[y][x] = value
        
        goal_x = self.goal_square[0]
        goal_y = self.goal_square[1]
        self.board[goal_y][goal_x] = -2

    def get_state(self):
        percent = len(self.playable_squares) / self.num_squares
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

    def update_square_arrays(self):
        self.inline_squares = self.get_inline_squares()
        self.inline_adj_squares = self.get_inline_adj_squares()
        self.frontier_squares = self.get_frontier_squares()
        self.farthest_squares = self.get_farthest_squares()
        self.closest_squares = self.get_closest_squares()
        self.most_value_squares = self.get_biggest_squares()
        self.least_value_squares = self.get_smallest_squares()

    def filter_common(self, array, already_filtered):
        if not already_filtered:
            return array

        result = []
        for sq in already_filtered:
            if sq in array:
                result.append(sq)

        return result

    def get_inline_squares(self):
        squares = []

        x = self.goal_square[0]
        y = self.goal_square[1]

        for sq in self.playable_squares:
            sqX = sq[0]
            sqY = sq[1]
            if x == sqX or y == sqY:
                squares.append(sq)

        return squares
    
    def get_inline_adj_squares(self):
        squares = []

        gx = self.goal_square[0]
        gy = self.goal_square[1]

        for sq in self.inline_squares:
            sqX = sq[0]
            sqY = sq[1]
            for adj in self.playable_squares:
                adjX = adj[0]
                adjY = adj[1]
                if (gx < adjX < sqX or sqX < adjX < gx \
                    or gy < adjY < sqY or sqY < adjY < gy):
                    squares.append(adj)

        return squares
    
    def get_frontier_squares(self):
        minX = self.board_width
        minY = self.board_height
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
            if sqX == minX or sqX == maxX or sqY == minY or sqY == maxY:
                frontier.append(sq)

        return frontier

    def manhattan_dist(self, square):
        x = square[0]
        y = square[1]
        gx = self.goal_square[0]
        gy = self.goal_square[1]

        return abs(x-gx) + abs(y-gy)

    def get_closest_squares(self):
        smallest_dist = self.manhattan_dist(self.playable_squares[0])
        closest = []

        for sq in self.playable_squares:
            dist = self.manhattan_dist(sq)
            if dist == smallest_dist:
                closest.append(sq)
            elif dist < smallest_dist:
                closest.clear()
                closest.append(sq)
                smallest_dist = dist

        return closest
    
    def get_farthest_squares(self):
        highest_dist = self.manhattan_dist(self.playable_squares[0])
        farthest = []

        for sq in self.playable_squares:
            dist = self.manhattan_dist(sq)
            if dist == highest_dist:
                farthest.append(sq)
            elif dist > highest_dist:
                farthest.clear()
                farthest.append(sq)
                highest_dist = dist

        return farthest
    
    def get_smallest_squares(self):
        smallest_value = self.playable_squares[0][2]
        smallest = []

        for sq in self.playable_squares:
            val = sq[2]
            if val == smallest_value:
                smallest.append(sq)
            elif val < smallest_value:
                smallest.clear()
                smallest.append(sq)
                smallest_value = val

        return smallest

    def get_biggest_squares(self):
        biggest_value = self.playable_squares[0][2]
        biggest = []

        for sq in self.playable_squares:
            val = sq[2]
            if val == biggest_value:
                biggest.append(sq)
            elif val > biggest_value:
                biggest.clear()
                biggest.append(sq)
                biggest_value = val

        return biggest

    def get_goal_directions(self, square):
        sqX = square[0]
        sqY = square[1]
        gx = self.goal_square[0]
        gy = self.goal_square[1]

        if sqX == gx:
            if sqY > gy: return ['UP']
            if sqY < gy: return ['DOWN']
        if sqY == gy:
            if sqX > gx: return ['LEFT']
            if sqX < gx: return ['RIGHT']
        if sqX > gx and sqY > gy: return ['UP', 'LEFT']
        if sqX > gx and sqY < gy: return ['DOWN', 'LEFT']
        if sqX < gx and sqY < gy: return ['DOWN', 'RIGHT']
        if sqX < gx and sqY > gy: return ['UP', 'RIGHT']

        return []

    def get_opp_goal_directions(self, square):
        directions = self.get_goal_directions(square)
        opposite = []

        for direction in self.valid_directions:
            if direction not in directions:
                opposite.append(direction)

        return opposite

    def get_interactions_directions(self, square):
        squareX = square[0]
        squareY = square[1]
        value = square[2]
        minX = squareX - value 
        maxX = squareX + value 
        minY = squareY - value 
        maxY = squareY + value

        leftCount = 0
        rightCount = 0
        upCount = 0
        downCount = 0

        for sq in self.playable_squares:
            thisX = sq[0]
            thisY = sq[1]
            if minX < thisX < squareX:
                leftCount += 1
            if squareX < thisX < maxX:
                rightCount += 1
            if minY < thisY < squareY:
                upCount += 1
            if squareY < thisY < maxY:
                downCount += 1

        counters = [upCount, rightCount, downCount, leftCount]
        max_val = max(counters)
        min_val = min(counters)
        max_index = [i for i, j in enumerate(counters) if j == max_val]
        min_index = [i for i, j in enumerate(counters) if j == min_val]
        max_directions = []
        min_directions = []
        for index in max_index:
            max_directions.append(self.valid_directions[index])
        for index in min_index:
            min_directions.append(self.valid_directions[index])

        return max_directions, min_directions

    # Game Logic related functions
    def play(self, square, direction):
        if direction not in self.valid_directions:
            #print('Invalid direction')
            return False

        x = square[0]
        y = square[1]
        value = square[2]
        self.played_coords.append((x, y))
        self.playable_squares.remove(square)

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

    def goal_filled(self):
        goal_x = self.goal_square[0]
        goal_y = self.goal_square[1]
        return self.board[goal_y][goal_x] == -1

    def no_more_moves(self):
        return len(self.playable_squares) == 0

    def fill(self, x, y):
        square = self.board[y][x]

        if square == 0 or square == -2:
            self.board[y][x] = -1
            return True

        return False

class ZhedContEnvFromLevel(ZhedContinuousEnv):
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
        super(ZhedContEnvFromLevel, self).__init__(playable, goal, width, height)

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
                    playable.append((x, y, int(char), False))

        if goal == None:
            print('File has no goal square!')
            exit()
        elif len(playable) == 0:
            print('File has no playable squares!')
            exit()

        return playable, goal, x + 1, y + 1