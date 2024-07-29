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
package client.command.commands.gm1;

import client.Character;
import client.*;
import client.command.Command;
import provider.Data;
import provider.DataProviderFactory;
import provider.wz.WZFiles;
import client.Skill;
import client.SkillFactory;
import config.YamlConfig; // Ensure this is imported for SP range

public class ResetSPStatCommand extends Command {
    {
        setDescription("Reset skills and add 700 SP.");
    }

    @Override
    public void execute(Client c, String[] params) {
        Character player = c.getPlayer();
        if (params.length > 0) {
            String job = params[0];
            for (Data skill_ : DataProviderFactory.getDataProvider(WZFiles.SKILL).getData(job + ".img").getChildByPath("skill").getChildren()) {
                try {
                    Skill skill = SkillFactory.getSkill(Integer.parseInt(skill_.getName()));
                    player.changeSkillLevel(skill, (byte) 0, 0, -1);
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    break;
                } catch (NullPointerException npe) {
                }
            }
        } else {
            for (Data skill_ : DataProviderFactory.getDataProvider(WZFiles.STRING).getData("Skill.img").getChildren()) {
                try {
                    Skill skill = SkillFactory.getSkill(Integer.parseInt(skill_.getName()));
                    player.changeSkillLevel(skill, (byte) 0, 0, -1);
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    break;
                } catch (NullPointerException npe) {
                }
            }

            if (player.getJob().isA(Job.ARAN1) || player.getJob().isA(Job.LEGEND)) {
                Skill skill = SkillFactory.getSkill(5001005);
                player.changeSkillLevel(skill, (byte) 0, 0, -1);
            } else {
                Skill skill = SkillFactory.getSkill(21001001);
                player.changeSkillLevel(skill, (byte) 0, 0, -1);
            }
        }

        // Add 700 SP to the player
        int newSp = player.getRemainingSp() + 700;
        if (newSp > YamlConfig.config.server.MAX_AP) {
            newSp = YamlConfig.config.server.MAX_AP;
        }
        player.updateRemainingSp(newSp);

        player.yellowMessage("Skills reset to level 0 and 700 SP added.");
    }
}
