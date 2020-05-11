from gym.envs.registration import register

register(
    id='zhed-v0',
    entry_point='gym_zhed.envs:ZhedEnv',
    kwargs={'playable_squares' : [(0,0,1),(0,1,1)], 'goal_square': (0,2), 'board_width': 3, 'board_height': 3},
    max_episode_steps=100,
    reward_threshold=0.8196, # optimum = .8196,
)

register(
    id='zhed-L1',
    entry_point='gym_zhed.envs:ZhedEnvFromLevel',
    kwargs={'level': 1},
    max_episode_steps=100,
    reward_threshold=0.8196, # optimum = .8196,
)