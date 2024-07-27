/*
    This file is part of the HeavenMS MapleStory Server, commands OdinMS-based
    Copyleft (L) 2016 - 2018 RonanLana

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
import client.command.Command;
import client.Client;

public class Bonus extends Command {
	{
		setDescription("");
	}

	@Override
	public void execute(Client c, String[] params) {
		Character player = c.getPlayer();

		//TODO @bonus now shows level 0 because of getlinkedtotal structure changes
		String showMsg_ = "#eCHARACTER Bonus#n" + "\r\n\r\n";
		showMsg_ += "Total Link Level: #e#b" + player.getLinkedTotal() + "#k#n" + "\r\n";
		showMsg_ += "Bonus All-Stats%: #e#b" + player.getLinkedTotalPercent() + "%#k#n" + "\r\n";
		//int attBonus = (c.getPlayer().QuestsCompleted() / 2) + (c.getPlayer().baseBossPointsForAttRing() / 2);
		//showMsg_ += "Total ATT Obtained From Quests/Bosses: #e#b"+ Integer.toString(attBonus) +"#k#n" + "\r\n";
		player.showHint(showMsg_, 200);
	}
}
