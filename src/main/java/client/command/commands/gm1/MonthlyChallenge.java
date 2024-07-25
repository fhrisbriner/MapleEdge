/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.command.commands.gm1;

import client.Character;
import client.Client;
import client.command.Command;
import constants.game.GameConstants;

/*
public class MonthlyChallenge extends Command {

	{
		setDescription("");
	}

	@Override
	public void execute(Client c, String[] params) {
		Character player = c.getPlayer();

		// Monthly's Challenge Progress
		String showMsg_ = "#e#bMonthly Challenge Progress#n#k" + "\r\n\r\n";
		if (c.getPlayer().getMerchOpenM()>= 0) {
			showMsg_ += "Setup HiredMerchant: #e[#r" + player.OpenMerchM+ "#k#n#e/#e10]#k#n" + "\r\n";
		}
		if (c.getPlayer().getFameGivenM()>= 0) {
			showMsg_ += "Fame Given: #e[#r" + player.FameGivenM+ "#k#n#e/#e20]#k#n" + "\r\n";
		}
		if (c.getPlayer().getZakumKillM()>= 0) {
			showMsg_ += "Boss Points: #e[#r" + player.ZakumKillM+ "#k#n#e/#e200]#k#n" + "\r\n";
		}
		if (c.getPlayer().getMiniM()>= 0) {
			showMsg_ += "Mini-Game Wins: #e[#r" + player.MiniM+ "#k#n#e/#e30]#k#n" + "\r\n";
		}
		if (c.getPlayer().getFameM()>= 0) {
			showMsg_ += "Fame Received: #e[#r" + player.FameM+ "#k#n#e/50]#k#n" + "\r\n";
		}
		if (c.getPlayer().getRGachM()>= 0) {
			showMsg_ += "Gachapon Rare Items Found: #e[#r" + player.RGachM+ "#k#n/#e100]#k#n" + "\r\n";
		}
		if (c.getPlayer().getJQM()>= 0) {
			showMsg_ += "Jump Quest Points: #e[#r" + player.JQM+ "#k#n#e/150]#k#n" + "\r\n";
		}
		if (c.getPlayer().getQuestCM()>= 0) {
			showMsg_ += "Quests Completed: #e[#r" + player.QuestCM+ "#k#n/#e150]#k#n" + "\r\n";
		}
		if (c.getPlayer().getScrollFailM()>= 0) {
			showMsg_ += "Scroll Fails: #e[#r" + player.ScrollFailM+ "#k#n/#e500]#k#n" + "\r\n";
		}
		if (c.getPlayer().getNGachM()>= 0) {
			showMsg_ += "Gachapon Tickets Used: #e[#r" + player.NGachM+ "#k#n/#e1,000]#k#n" + "\r\n";
		}
		if (c.getPlayer().getNXM()>= 0) {
			showMsg_ += "NX Collected: #e[#r" + GameConstants.numberWithCommas(player.NXM) + "#k#n/#e50,000]#k#n" + "\r\n";
		}
		if (c.getPlayer().getSellItemM()>= 0) {
			showMsg_ += "[HiredMerchant] Gains: #e[#r" + GameConstants.numberWithCommas(player.SellItemM) + "#k#n#e/#e550,000,000]#k#n" + "\r\n";
		}
		if (c.getPlayer().getBoughtItemM()>= 0) {
			showMsg_ += "[HiredMerchant] Meso Spent: #e[#r" + GameConstants.numberWithCommas(player.BoughtItemM) + "#k#n#e/#e550,000,000]#k#n" + "\r\n";
		}
		player.showHint(showMsg_, 300);
	}
}

*/