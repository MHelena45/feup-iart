from stable_baselines import bench
import matplotlib.pyplot as plt
import sys

def get_axis(df):
    rewards = df.get('r').values
    timesteps = df.get('l').values

    for i in range(1, len(rewards)):
        rewards[i] = rewards[i - 1] + rewards[i]
        timesteps[i] = timesteps[i - 1] + timesteps[i]

    return timesteps, rewards


def make_plot(level):
    dfO = bench.monitor.load_results('models/logOriginal').copy()
    dfF = bench.monitor.load_results('models/logFirst').copy()
    dfS = bench.monitor.load_results('models/logSecond').copy()
    
    x_O, y_O = get_axis(dfO)
    x_F, y_F = get_axis(dfF)
    x_S, y_S = get_axis(dfS)

    plt.figure(figsize=(7.2, 4.8))
    plt.title('Level ' + str(level))
    plt.xlabel('timesteps')
    plt.ylabel('acc_reward')
    plt.plot(x_O, y_O, '-', label="CR=0.1")
    plt.plot(x_F, y_F, '--', label="CR=0.2")
    plt.plot(x_S, y_S, '-.', label="CR=0.3")
    plt.legend()
    plt.tight_layout()
    plt.savefig('graphs/PPO2/level' + str(level) + '.png')

make_plot(sys.argv[1])