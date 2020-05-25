from gym.envs.registration import register

register(
    id='zhed-v0',
    entry_point='gym_zhed.envs:ZhedEnv',
    kwargs={'playable_squares' : [(0,0,1),(0,1,1)], 'goal_square': (0,2), 'board_width': 3, 'board_height': 3},
    max_episode_steps=100,
    reward_threshold=0.8196,
)

for level in range(1, 101):
    register(
        id='zhedLevel' + str(level) + "-v0",
        entry_point='gym_zhed.envs:ZhedEnvFromLevel',
        kwargs={'level': level},
    )

register(
    id='zhedContLevel5-v0',
    entry_point='gym_zhed.envs:ZhedContEnvFromLevel',
    kwargs={'level': 5},
)