from gym.envs.registration import register

register(
    id='zhed-v0',
    entry_point='gym_zhed.envs:ZhedEnv',
    #TODO: add kwargs and possibly max_episode_steps
    kwargs={'playable_squares' : [(0,0,1),(0,1,1)], 'goal_square': (0,2), 'board_size': 3},
    max_episode_steps=100,
    reward_threshold=0.8196, # optimum = .8196,
)