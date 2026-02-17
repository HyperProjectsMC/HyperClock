# HyperClock Plugin

**HyperClock** is a Minecraft plugin that creates a global stopwatch and/or timer that can be fully controlled in-game through commands. With HyperClock, players and server administrators can easily start, pause, and reset time using the appropriate permissions or with OP. The plugin integrates with PlaceholderAPI and provides multiple placeholders, allowing the stopwatch and timer to be shown on scoreboards, tab lists, boss bars, or other UI elements. Perfect for events, minigames, and challenges where timing matters.

## Features

- Creates a global stopwatch and/or timer that can be controlled in-game via commands.
- Uses a permission-based system to manage who can start, stop, reset/set, or view the timer.
- Displays the current status using multiple PlaceholderAPI placeholders.
- Fully compatible with scoreboards, tab lists, boss bars, and other UI elements.
- Supports both stopwatch (count up) and timer (count down) modes for different use cases.
- Real-time updates for accurate and reliable timing.
- Lightweight and optimized for smooth gameplay.

## Installation

### Modrinth Website
1. Download the latest version of HyperClock from [Modrinth](https://modrinth.com/project/HyperClock) by clicking **Download** and selecting the correct options.
2. Move the downloaded .jar file into your Minecraft server’s plugins folder.
3. Start (or restart) your server to activate the plugin and check the commands below.
4. Enjoy an improved timing experience!

### Aternos
1. Open your Aternos server on [aternos.org](https://aternos.org/)
2. Go to Software and check that your server is running a software that allows plugins (recommended: paper). If not, install that first.
3. Click on the Plugins and search for HyperClock.
4. Click on the first result and download the latest version.
5. Start (or restart) your server to activate the plugin and check the commands below.
6. Enjoy an improved timing experience!

## Commands

HyperClock has two main commands:

- /stopwatch → counts time up
- /timer → counts time down

Both commands have similar subcommands.

<details>
<summary>/stopwatch Command</summary>

### `/stopwatch` Command
The Stopwatch command counts up from 0.

#### Subcommands:
| Command             | Description                            | Permission                 |
|---------------------|----------------------------------------|----------------------------|
| `/stopwatch start`  | Starts the stopwatch                   | `hyperclock.stopwatch.use` |
| `/stopwatch stop`   | Stops the stopwatch (can be restarted) | `hyperclock.stopwatch.use` |
| `/stopwatch reset`  | Resets the stopwatch to 0              | `hyperclock.stopwatch.use` |
| `/stopwatch status` | Shows the current status               | `hyperclock.stopwatch.use` |

</details>

<details>
<summary>/timer Command</summary>

### `/timer` Command

The timer counts down from a selected time.

#### Subcommands

| Command             | Description               | Permission             |
|---------------------|---------------------------|------------------------|
| `/timer start`      | Starts the timer          | `hyperclock.timer.use` |
| `/timer pause`      | Stops the timer           | `hyperclock.timer.use` |
| `/timer set <time>` | Set the timer (5m, 1h30m) | `hyperclock.timer.use` |
| `/timer status`     | Shows the current status  | `hyperclock.timer.use` |

#### Time Notation
- `30s` → 30 seconds
- `5m` → 5 minutes
- `1h` → 1 hour
- `1h30m` → 1 hour 30 minutes
- `2m45s` → 2 minutes 45 seconds
- `4h15m20s` → 4 hours 15 minutes 20 seconds

</details>

## Permissions

| Permission                 | Description                         |
|----------------------------|-------------------------------------|
| `hyperclock.stopwatch.use` | Full access to the stopwatch        |
| `hyperclock.timer.use`     | Full access to the timer            |

## Placeholders

This plugin uses the PlaceholderAPI to provide dynamic values. The PlaceholderAPI integration is fully integrated into the plugin and does not require separate installation.

### Stopwatch Placeholders

These placeholders provide information about the stopwatch.

| Placeholder                      | Description                                          | Output                             |
|----------------------------------|------------------------------------------------------|------------------------------------|
| `%hyperclock_stopwatch_time%`    | Displays the current stopwatch time                  | `00:00:00` (hours:minutes:seconds) |
| `%hyperclock_stopwatch_running%` | Indicates whether the stopwatch is currently running | `true` / `false`                   |

### Timer Placeholders

These placeholders provide information about the timer.

| Placeholder                   | Description                                      | Output                             |
|-------------------------------|--------------------------------------------------|------------------------------------|
| `%hyperclock_timer_time%`     | Displays the current or remaining timer time     | `00:00:00` (hours:minutes:seconds) |
| `%hyperclock_timer_running%`  | Indicates whether the timer is currently running | `true` / `false`                   |
| `%hyperclock_timer_finished%` | Indicates whether the timer has finished         | `true` / `false`                   |

These placeholders can be used in all plugins and configurations that support **PlaceholderAPI**, such as scoreboards, holograms, chat formatting and more.

## Configuration

HyperClock offers a wide range of configurable options to tailor the behavior to your specific needs. You can easily customize settings such as auto start, default time, storage method, and much more. Additionally, HyperClock supports multiple languages, with Dutch and English available by default. You can create your own language files by adding them to the `/lang` folder.

To select your preferred language, simply adjust the `general.language` setting in the `config.yml` file.

For a complete view of our `config.yml` file, please refer to the current config file down below. Feel free to explore and tweak the settings according to your preferences.

You can check the full config [here](https://raw.githubusercontent.com/HyperProjectsMC/HyperClock/refs/heads/main/src/main/resources/config/config.yml) on GitHub.

## Credits

- **HyperProjects:** This project is made by the HyperProjects team.

## License

This project is licensed under the **MIT License**.

View the license by:
- Clicking the LICENSE file on GitHub
- On Modrinth, clicking on the **Licensed MIT** section on the right

## Feedback & Contributions

We welcome contributions and suggestions! Feel free to submit pull requests, report issues, or share your ideas to help make HyperClock even better.

Enjoy timing like never before with **HyperClock**!