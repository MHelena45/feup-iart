from gym.envs.registration import register

register(
    id='zhed-v0',
    entry_point='gym_zhed.envs:ZhedEnv',
    kwargs={'playable_squares' : [(0,0,1),(0,1,1)], 'goal_square': (0,2), 'board_width': 3, 'board_height': 3},
    max_episode_steps=100,
    reward_threshold=0.8196,
)

register(
    id='zhed-v1',
    entry_point='gym_zhed.envs:ZhedEnvFromLevel',
    kwargs={'level': 1},
    max_episode_steps=100,
    reward_threshold=0.8196,
)

register(
    id='zhed-v2',
    entry_point='gym_zhed.envs:ZhedEnvFromLevel',
    kwargs={'level': 2},
    max_episode_steps=100,
    reward_threshold=0.8196,
)

register(
    id='zhed-v3',
    entry_point='gym_zhed.envs:ZhedEnvFromLevel',
    kwargs={'level': 3},
    max_episode_steps=100,
    reward_threshold=0.8196,
)

register(
    id='zhed-v4',
    entry_point='gym_zhed.envs:ZhedEnvFromLevel',
    kwargs={'level': 4},
    max_episode_steps=100,
    reward_threshold=0.8196,
)

register(
    id='zhed-v5',
    entry_point='gym_zhed.envs:ZhedEnvFromLevel',
    kwargs={'level': 5},
    max_episode_steps=100,
    reward_threshold=0.8196,
)

register(
    id='zhed-v6',
    entry_point='gym_zhed.envs:ZhedEnvFromLevel',
    kwargs={'level': 6},
    max_episode_steps=100,
    reward_threshold=0.8196,
)

register(
    id='zhed-v7',
    entry_point='gym_zhed.envs:ZhedEnvFromLevel',
    kwargs={'level': 7},
    max_episode_steps=100,
    reward_threshold=0.8196,
)

register(
    id='zhed-v8',
    entry_point='gym_zhed.envs:ZhedEnvFromLevel',
    kwargs={'level': 8},
    max_episode_steps=100,
    reward_threshold=0.8196,
)

register(
    id='zhed-v9',
    entry_point='gym_zhed.envs:ZhedEnvFromLevel',
    kwargs={'level': 9},
    max_episode_steps=100,
    reward_threshold=0.8196,
)

register(
    id='zhed-v10',
    entry_point='gym_zhed.envs:ZhedEnvFromLevel',
    kwargs={'level': 10},
    max_episode_steps=100,
    reward_threshold=0.8196,
)