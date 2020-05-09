from gym.envs.registration import register

register(
    id='zhed-v0',
    entry_point='gym_zhed.envs:ZhedEnv',
    #TODO: add kwargs and possibly max_episode_steps
    #kwargs={'map_name' : '15x15'},
    max_episode_steps=100,
    reward_threshold=0.8196, # optimum = .8196,
)