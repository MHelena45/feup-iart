from stable_baselines import bench
import matplotlib.pyplot as plt

def cumulative_plot(level):
    df = bench.monitor.load_results('models/PPO1/logs/').copy()
    rewards = df.get('r').values
    timesteps = df.get('l').values

    for i in range(1, len(rewards)):
        rewards[i] = rewards[i - 1] + rewards[i]
        timesteps[i] = timesteps[i - 1] + timesteps[i]

    plt.plot(timesteps, rewards)
    plt.ylabel('acc_rewards')
    plt.xlabel('timesteps')
    plt.title('Level ' + level)
    plt.savefig('graphs/PPO1/level' + level + '.png')
