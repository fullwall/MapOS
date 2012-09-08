package com.fullwall.maps;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.fullwall.maps.command.CommandManager;
import com.fullwall.maps.command.CommandUsageException;
import com.fullwall.maps.command.NoPermissionsException;
import com.fullwall.maps.command.RequirementMissingException;
import com.fullwall.maps.command.ServerCommandException;
import com.fullwall.maps.command.UnhandledCommandException;
import com.fullwall.maps.command.WrappedCommandException;
import com.fullwall.maps.listeners.PlayerListen;
import com.fullwall.maps.utils.Messaging;
import com.fullwall.maps.utils.StringHelper;

public class MapOS extends JavaPlugin {
    private final CommandManager commands = new CommandManager();
    private final MapController controller = new MapController();
    private PluginDescriptionFile desc;

    public MapController getController() {
        return controller;
    }

    private boolean handleMistake(CommandSender sender, String command, String modifier) {
        String[] modifiers = commands.getAllCommandModifiers(command);
        Map<Integer, String> values = new TreeMap<Integer, String>();
        int i = 0;
        for (String string : modifiers) {
            values.put(StringHelper.getLevenshteinDistance(modifier, string), modifiers[i]);
            ++i;
        }
        int best = 0;
        boolean stop = false;
        Set<String> possible = new HashSet<String>();
        for (Entry<Integer, String> entry : values.entrySet()) {
            if (!stop) {
                best = entry.getKey();
                stop = true;
            } else if (entry.getKey() > best) {
                break;
            }
            possible.add(entry.getValue());
        }
        if (possible.size() > 0) {
            sender.sendMessage(ChatColor.GRAY + "Unknown command. Did you mean:");
            for (String string : possible) {
                sender.sendMessage(StringHelper.wrap("    /") + command + " " + StringHelper.wrap(string));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        try {
            // must put command into split.
            String[] split = new String[args.length + 1];
            System.arraycopy(args, 0, split, 1, args.length);
            split[0] = command.getName().toLowerCase();

            String modifier = "";
            if (args.length > 0) {
                modifier = args[0];
            }

            // No command found!
            if (!commands.hasCommand(split[0], modifier)) {
                if (!modifier.isEmpty()) {
                    boolean value = handleMistake(sender, split[0], modifier);
                    return value;
                }
            }
            try {
                commands.execute(split, player, player == null ? sender : player);
            } catch (ServerCommandException e) {
                sender.sendMessage(e.getMessage());
            } catch (NoPermissionsException e) {
                Messaging.sendError(sender, "You don't have permission to do that.");
            } catch (CommandUsageException e) {
                Messaging.sendError(player, e.getMessage());
                Messaging.sendError(player, e.getUsage());
            } catch (RequirementMissingException e) {
                Messaging.sendError(player, e.getMessage());
            } catch (WrappedCommandException e) {
                throw e.getCause();
            } catch (UnhandledCommandException e) {
                return false;
            }
        } catch (NumberFormatException e) {
            Messaging.sendError(player, "That is not a valid number.");
        } catch (Throwable excp) {
            excp.printStackTrace();
            Messaging.sendError(player, "Please report this error: [See console]");
            Messaging.sendError(player, excp.getClass().getName() + ": " + excp.getMessage());
        }
        return true;
    }

    @Override
    public void onDisable() {
        controller.reset();
        Messaging.log("version [" + desc.getVersion() + "] disabled");
    }

    @Override
    public void onEnable() {
        desc = getDescription();
        PluginManager pm = getServer().getPluginManager();
        new PlayerListen().registerEvents(this, pm);
        Messaging.log("version [" + desc.getVersion() + "] loaded");
    }
}
