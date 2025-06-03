
# Glife Plugin (OLD) ![](https://img.shields.io/badge/Platform-Linux-only-blue) ![Alpha](https://img.shields.io/badge/Status-Release-red)

![Banner](https://images-ext-2.discordapp.net/external/K_Vj-_8FzSL7B57Ycey0crHLgwftXVdKUHPVUceQ26c/https/i.postimg.cc/yxBjZCXB/Glifebanner-dsgvgd.jpg?format=webp&width=2560&height=1180)

**Description:**

This Spigot plugin enhances gameplay by introducing a life system. Each player starts with three lives, loses one upon death, and gains one when killing another player. The plugin includes customizable placeholders and configurations.

**Note:** When a player's life count reaches zero, you can set in the configuration file a command.

**Commands:**

1. `/life check <player>` - View a player's lives.
4. `/life set <player> <number>` - Set a player's lives.

**Configurations:**

```yaml
# Settings
Join-messages: true
leave-messages: true
death-message: true
final-message: true
life-number: 3
execute-command: true
console-command: "/ban %player%" #Test

# Messages
prefix: "<gray>[<white>ServerName<gray>]<dark_gray> » "
no-player: "<red>Player not found"
reset-life: "<yellow>%player% is back in the game"
no-permission: "<red>You don't have the required permissions"
check-life: "<red>%player% has %life% lives"
your-life: "<aqua>You have %life% lives"
give-life: "<aqua>You have donated 1 life"
receive-life: "<aqua>You have received 1 life from %player%"
cant-find-player: "<red>Player not found"
cant-send-to-yourself: "<red>You can't do that to yourself"
revelation_message: "%player% is at (%position_x%, %position_y%, %position_z%, %world%)"
title_revelation: "<red>Revelation"
subtitle_revelation: "<red>"
death: "<yellow>%player% has died and now has <red>❤ %lifes% lives"
final-death: "<yellow>%player% has died permanently"
join: "<yellow>%player% has joined the server!"
leave: "<red>%player% has left the server"
gained-life: "<aqua>You have gained 1 life"
set-life: "<aqua>New life count: %life%"

# Help
help-commands: |
  <red>
  Usage:
   <gray>give: <white>/life give <player>
   <gray>check: <white>/life check <player
```

**Placeholders:**

- Life Placeholder: `%glife_life%`

**Permissions:**

- `glife.admin`

---
