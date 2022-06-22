# CommandStack
A simple utility to assign commands to items

CommandStack depends on [Interface4](https://www.spigotmc.org/resources/interface4.102119/)

## Commands
| Command | Permission | Function |
|---------|-----------|---------|
| `/cstack add <command>` | `commandstack.add` | Adds a command to the handheld item |
| `/cstack remove <command>` | `commandstack.remove` | Removes a command from the handheld item |
| `/cstack view` | `commandstack.view` | Shown all commands assigned to the handheld item |
| `/cstack reload` | `commandstack.reload` | Reloads translations |
| `/cstack credits` | None | Mentions resource credits |

## Configuration
```yaml
Lang:
  Player-Only-Command: "Only players can use this command"
  Not-Holding-Item: "You must be holding an item to use this command"
  No-Commands: "This item has no commands assigned"
  Does-Not-Have-Command: "This item does not have this command"
  Removed-Command: "Removed command %cmd%"
  Removed-All-Commands: "Removed all commands"
  Added-Command: "Added command %cmd%"
  No-Permissions: "&cNo permissions!"
  Reloaded: "&aReloaded successfully"
  Usage:
    - "&7Usage:"
    - "&8-&7&o /cstack add <command>"
    - "&8-&7&o /cstack remove <command/all>"
    - "&8-&7&o /cstack view"
    - "&8-&7&o /cstack reload"
    - "&8-&7&o /cstack credits"
  
Settings:
  Prevent-Placement: true # Should CommandStack items be unplacable
  Updater:
    Alert-OP: true # Should OP players be notified if an update is available on join
    Auto-Update: true # Should CommandStack automatically update to the latest version (Recommended)
    Auto-Restart: true # Should CommandStack automatically restart the server if the plugin updates to apply changes
  
Permissions:
  Use: "commandstack.use"
  Reload: "commandstack.reload"
  View: "commandstack.view"
  Remove: "commandstack.remove"
  Add: "commandstack.add"
```
