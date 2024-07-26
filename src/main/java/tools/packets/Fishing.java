/*
    This file is part of the HeavenMS MapleStory Server
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
package tools.packets;

import client.Character;
import constants.id.ItemId;
import constants.id.MapId;
import constants.inventory.ItemConstants;
import net.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.ItemInformationProvider;
import tools.PacketCreator;

import java.util.Calendar;

/**
 * @author FateJiki (RaGeZONE)
 * @author Ronan - timing pattern
 */
public class Fishing {
    private static final Logger log = LoggerFactory.getLogger(Fishing.class);

    public static double[] fetchFishingLikelihood() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        double yearLikelihood = getFishingLikelihoodDay(dayOfWeek);
        double timeLikelihood = getFishingLikelihoodTime(hours);
        return new double[]{yearLikelihood, timeLikelihood};
    }

    private static double getFishingLikelihoodDay(int x) {
        // Base value for 1x is 12.5
        // Sunday, Saturday: 2x -> 2 * 12.5 = 25.0
        // Monday, Wednesday, Friday: 1.5x -> 1.5 * 12.5 = 18.75
        // Tuesday, Thursday: 1x -> 1 * 12.5 = 12.5
        return switch (x) {
            case 1, 3, 5 -> 18.75;
            case 6, 0 -> 25.0;
            default -> 12.5;
        };
    }

    private static double getFishingLikelihoodTime(int x) {
        //Peak fishing at 7am - 9:59am,/ 5H Gap / 3pm - 5:59pm / (3H gap) / 9pm - 11:59pm
        return switch (x) {
            case 7, 8, 9, 15, 16, 17, 21, 22, 23 -> 10.0;
            default -> 5.0;
        };
    }

    // You will likely need to tweak this performance based on how you choose to implement your rewards
    private static boolean hitFishingTime(Character chr, int baitLevel) {
        double [] likelihoods = fetchFishingLikelihood();

        double baitLikelihood = baitLevel / 20;
        // this gives a MINIMUM success rate of 33.75% and a MAXIMUM of 82.5% before fishing rate bonuses.
        // effectively makes basic bait give a +7.5% success rate, mid gives a +15% success rate, and high gives a +30% success rate
        // server configuration can give a flat final bonus of +0% to +10%
        return ((likelihoods[0] + likelihoods[1] + baitLikelihood) * 1.5) + chr.getWorldServer().getFishingRate() > (100.0 * Math.random());
    }

    public static void doFishing(Character chr, int baitLevel) {
        // thanks Fadi, Vcoc for suggesting a custom fishing system
        if (!chr.isLoggedinWorld() || !chr.isAlive()) {
            return;
        }
        if (!MapId.isFishingArea(chr.getMapId())) {
            chr.dropMessage("You are not in a fishing area!");
            return;
        }
        if (chr.getLevel() < 30) {
            chr.dropMessage(5, "You must be above level 30 to fish!");
            return;
        }

        String fishingEffect;
        if (!hitFishingTime(chr, baitLevel)) {
            fishingEffect = "Effect/BasicEff.img/Catch/Fail";
        } else {
            fishingEffect = "Effect/BasicEff.img/Catch/Success";

            int rand = (int) (3.0 * Math.random());
            switch (rand) {
                case 0:
                    // Meso rewards should be tweaked here for your server's individual game balance
                    int mesoAward = (int) (5.0 * Math.random() + 10 * chr.getLevel()) * chr.getMesoRate();
                    mesoAward = mesoAward / 2 * baitLevel / 100;
                    chr.gainMeso(mesoAward, true, true, true);
                    break;
                case 1:
                    // exp rewards should be tweaked here for your server's individual game balance
                    int expAward = 0;
                    if (chr.getLevel() <= 80) {
                        expAward = (int) (15 * chr.getLevel() + chr.getLevel() * Math.random()) * chr.getExpRate();
                    }
                    else if (chr.getLevel() <= 200) {
                        expAward = (int) (50 * chr.getLevel() + 15*chr.getLevel() * Math.random()) * chr.getExpRate();
                    } else {
                        expAward = (int) (500 * chr.getLevel() + 200*chr.getLevel() * Math.random()) * chr.getExpRate();
                    }
                    expAward = expAward / 4 * (baitLevel / 100);
                    chr.gainExp(expAward, true, true);
                    break;
                case 2:
                    int itemid = getRandomItem(baitLevel);
                    if (chr.canHold(itemid)) {
                        if(ItemId.isJackpotFishingDrop(itemid))
                            Server.getInstance().broadcastMessage(chr.getWorld(), PacketCreator.serverNotice(6, 0, chr.getName() + " found a(n) " + ItemInformationProvider.getInstance().getName(itemid) + " while fishing! Congratulations!"));
                        chr.getAbstractPlayerInteraction().gainItem(itemid, true);
                    } else {
                        chr.showHint("Couldn't catch a(n) #r" + ItemInformationProvider.getInstance().getName(itemid) + "#k due to #e#b" + ItemConstants.getInventoryType(itemid) + "#k#n inventory limit.");
                    }
                    break;
            }
        }
        chr.sendPacket(PacketCreator.showInfo(fishingEffect));
        chr.getMap().broadcastMessage(chr, PacketCreator.showForeignInfo(chr.getId(), fishingEffect), false);
    }

    public static int getRandomItem(int baitLevel) {
        int rand = (int) (100.0 * Math.random());
        int baitBonus = baitLevel / 100;
        if (rand < 0) rand = 0;
        rand -= baitBonus; // gives a +1% / +2% / +4% shot at a non-common item
        if (rand >= 25) {
            return ItemId.FISHING_REWARDS_COMMON[(int) (ItemId.FISHING_REWARDS_COMMON.length * Math.random())];
        } else if (rand >= 4) {
            return ItemId.FISHING_REWARDS_UNCOMMON[(int) (ItemId.FISHING_REWARDS_UNCOMMON.length * Math.random())];
        } else {
            return ItemId.FISHING_REWARDS_RARE[(int) (ItemId.FISHING_REWARDS_RARE.length * Math.random())];
        }
    }
}