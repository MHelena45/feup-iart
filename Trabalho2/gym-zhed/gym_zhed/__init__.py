from gym.envs.registration import register

register(
    id='zhed-v0',
    entry_point='gym_zhed.envs:ZhedEnv',
    #TODO: add kwargs and possibly max_episode_steps
)