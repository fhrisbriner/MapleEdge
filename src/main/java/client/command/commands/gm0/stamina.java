package client.command.commands.gm0;

import client.Client;
import client.command.Command;

public class stamina extends Command {

    {
        setDescription("Displays your current/max stamina limit.");
    }

    @Override
    public void execute(Client c, String[] params) {
        c.getPlayer().yellowMessage("Your Current/Max Stamina is: " + c.getPlayer().getStamina());
        c.getPlayer().yellowMessage("Stamina is recovered through leveling or every 24 hours.");
    }
}
