#CustomTpCommands

This plugin is created for create custom command who tp and can if you desire remember last position in the world where you tp the player.
For exemple, with this plugin, you create /minage who tp to minage world, player are tp in minage, walk 20 block, return in another world. Reuse /minage command and return in world at this last position.
This project is open source, you can modify its code like as you want.
Ps: for now, this plugin are tested only on 1.21 and java 21. You can change it in build your version via the source code on github (change pom.xml like you want).

each commands have to be added in config.yml (you can replace existing config if you want). you can change it in file config.yml. Each user position for specific world is stored in user-data folder when rememberLocation is true.

for another info, visit https://github.com/superrobinou/customtpcommands/blob/main/src/main/resources/config.yml