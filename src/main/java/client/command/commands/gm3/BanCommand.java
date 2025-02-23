/*
    This file is part of the HeavenMS MapleStory Server, commands OdinMS-based
    Copyleft (L) 2016 - 2019 RonanLana

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation version 3 as published by
    the Free Software Foundation. You may not use, modify or distribute
    this program under any other version of the GNU Affero General Public
    License.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/*
   @Author: Arthur L - Refactored command content into modules
*/
package client.command.commands.gm3;

import client.Character;
import client.Client;
import client.command.Command;
import net.server.Server;
import server.TimerManager;
import tools.DatabaseConnection;
import tools.PacketCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BanCommand extends Command {
    {
        setDescription("Ban a player.");
    }

    @Override
    public void execute(Client c, String[] params) {
        Character player = c.getPlayer();
        if (params.length < 2) {
            player.yellowMessage("Syntax: !ban <IGN> <Reason> (Please be descriptive)");
            return;
        }

        String ign = params[0];
        String reason = joinStringFrom(params, 1);
        Character target = c.getChannelServer().getPlayerStorage().getCharacterByName(ign);

        if (target != null) {
            String readableTargetName = Character.makeMapleReadable(target.getName());
            String ip = target.getClient().getRemoteAddress();
            String hwid = String.valueOf(target.getClient().getHwid());

            //TODO requires some cleaning but does ban MAC + HWID currently and bans every single account user tries to create
            //TODO - to use INSERT INTO HWID bans table instead of ipbans which appears to not be logging anything - even tho it bans MAC + HWID currently
            try (Connection con = DatabaseConnection.getConnection()) {
                //if (hwid.matches("/[0-9A-F]{8}$")) {
                //}
                if (ip.matches("/[0-9]{1,3}\\..*")) {
                    //try (PreparedStatement ps = con.prepareStatement("INSERT INTO hwidbans VALUES (? ?")) {
                    try (PreparedStatement ps = con.prepareStatement("INSERT INTO ipbans VALUES (DEFAULT, ?, ?)")) {
                        //ps.setString(1, hwidbanid);
                        ps.setString(1, ip);
                        ps.setString(2, String.valueOf(target.getClient().getAccID()));

                        ps.executeUpdate();
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                c.getPlayer().message("Error occured while banning IP address");
                c.getPlayer().message(target.getName() + "'s IP was not banned: " + ip);
            }
            target.getClient().banMacs();
            reason = c.getPlayer().getName() + " banned " + readableTargetName + " for " + reason + " (IP: " + ip + ") " + "(MAC: " + c.getMacs() + " (HWID: " + c.getHwid() + ")";
            target.ban(reason);
            target.yellowMessage("You have been banned by #b" + c.getPlayer().getName() + " #k.");
            target.yellowMessage("Reason: " + reason);
            c.sendPacket(PacketCreator.getGMEffect(4, (byte) 0));
            final Character rip = target;
            //TODO appears to not disconnect them properly but they can't do anything in-game or see anyone. (maybe true value?)
            //TODO to connect "DiscordWebhook webhookMessage" function here so we can shame players for hacking in shame wall
            TimerManager.getInstance().schedule(() -> rip.getClient().disconnect(false, false), 1000); // why is there a delay to wait before even disconnecting a hacker/bot
            Server.getInstance().broadcastMessage(c.getWorld(), PacketCreator.serverNotice(6, "My name is " + ign + ", I am bad at MapleStory, so i hack!! has been banned."));
        } else if (Character.ban(ign, reason, false)) {
            c.sendPacket(PacketCreator.getGMEffect(4, (byte) 0));
            Server.getInstance().broadcastMessage(c.getWorld(), PacketCreator.serverNotice(6, "My name is " + ign + ", I am bad at MapleStory, so i hack!! has been banned."));
        } else {
            c.sendPacket(PacketCreator.getGMEffect(6, (byte) 1));
        }
    }
}