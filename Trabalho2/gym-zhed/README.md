# Gym_Zhed
An OpenAI Gym environment based on Zhed game.

## Testing
To test the ZhedEnv class run the following commands:  
  1. cd gym-zhed/gym_zhed/envs (changing to the folder where the script is)
  2. python3.8 (or whatever python yo have installed)

From now on you are inside the python interpreter. Run:
  1. import zhed_env as env
  2. zhed = env.ZhedEnv(<\args>) (where args is the list of arguments to use)
  3. zhed.render() (this is optional, just to confirm the game is created)

And that's it, you just created a Zhed environment! :)