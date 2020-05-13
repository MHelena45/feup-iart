def load_file(filepath):
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

max_squares = 0
squares_per_level = {}
prefix = 'gym-zhed/gym_zhed/levels/'

for level in range(1, 101):
    if level < 10:
        filename = '00' + str(level) + '.txt'
    elif level < 100:
        filename = '0' + str(level) + '.txt'
    else:
        filename = str(level) + '.txt'

    playable_squares, _, _, _ = load_file(prefix + filename)

    current_squares = len(playable_squares)
    if current_squares > max_squares:
        max_squares = current_squares

    if current_squares not in squares_per_level:
        level_list = [level]
        squares_per_level[current_squares] = level_list
    else:
        squares_per_level[current_squares].append(level)

print('  Squares  |  List of levels')
for num_squares in sorted(squares_per_level):
    if num_squares < 10:
        print(f' {num_squares} squares: {squares_per_level[num_squares]}')
    else:
        print(f'{num_squares} squares: {squares_per_level[num_squares]}')

print('')
print(f'Max number of squares = {max_squares}; in levels: {squares_per_level[max_squares]}')