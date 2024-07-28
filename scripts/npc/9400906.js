/*
    This file is part of the OdinMS Maple Story Server
    Copyright (C) 2008 Patrick Huy <patrick.huy@frz.cc>
               Matthias Butz <matze@odinms.de>
               Jan Christian Meyer <vimes@odinms.de>

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
/* Rooney
    Map Name (Map ID)
    Used to exchange VP for Maple Leaves, and Maple Leaves for rewards.
 */

var itemToUse = 4001126;

var weapons = [1702934, 1702935, 1702936, 1702937, 1702938, 1702939, 1702942, 1702943];
var bait1 = [4035001];
var bait2 = [4035002];
var bait3 = [4035004];
var gachatix = [5220000];

var weaponAmount = 1;
var buffAmount = 1;
var hiredMerchantLength = 7;
var bait1amount = 50;
var bait2amount = 25;
var bait3amount = 5;
var gachamount = 10;

var buff1ID = 2022273;
var buff2ID = 2022179;
var status;
var vp;
var choice;

function start() {
    vp = cm.getClient().getVotePoints();
    if (vp == null) vp = 0;

    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode < 0) {
        cm.dispose();
    } else {
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        
        if (status == 0) {
            if (cm.getPlayer().getLevel() < 30) {
                cm.sendOk("Hello, I am the Vote Point exchanger for #rMapleEdge#k!\r\n\r\nI am sorry, but I can only exchange Vote Points for players #blevel 30 or over#k.");
                cm.dispose();
                return;
            }
            
            var outStr = "Hello, I am the Vote Point exchanger for #rMapleEdge#k!\r\n";
			outStr += "#bPlease make sure to @dispose when you are done.#k\r\n";
            outStr += "You currently have #r#c" + itemToUse + "##k #t" + itemToUse + "# and #r" + vp + "#k Vote Points.#b\r\n\r\n";
            outStr += "#L0#I would like to exchange my vote points for Maple Leaves#l\r\n";
            outStr += "#L1#I would like to exchange 5 #t" + itemToUse + "# for " + gachamount + " Gachapon Tickets#l\r\n";
            outStr += "#L3#I would like to exchange 10 #t" + itemToUse + "# for " + weaponAmount + " random Custom weapon?#l\r\n";
            outStr += "#L4#I would like to exchange 50 #t" + itemToUse + "# for " + buffAmount + " #t" + buff1ID + "#s and " + buffAmount + " #t" + buff2ID + "#s#l\r\n";
            outStr += "#L5#I would like to exchange 10 #t" + itemToUse + "# for a " + hiredMerchantLength + " Day Hired Merchant#l\r\n";
            outStr += "#L6#I would like to exchange 5 #t" + itemToUse + "# for " + bait1amount + " Tier 1 bait\r\n";
            outStr += "#L7#I would like to exchange 10 #t" + itemToUse + "# for " + bait2amount + " Tier 2 bait\r\n";
            outStr += "#L8#I would like to exchange 15 #t" + itemToUse + "# for " + bait3amount + " Tier 3 bait\r\n";
            cm.sendSimple(outStr);
        } else if (status == 1) {
            choice = selection;
            
            if (selection == 0) {
                // Exchange VP for leaves
                if (vp <= 0) {
                    cm.sendOk("I'm sorry, but you don't have any Vote Points to exchange!");
                    cm.dispose();
                    return;
                }
                cm.sendYesNo("Would you like to exchange Vote Point" + (vp > 1 ? "s" : "") + " for Maple Leaves?");
            } else if (selection == 1) {
                // Exchange item for GachaTickets
                if (!cm.haveItem(itemToUse)) {
                    cm.sendOk("I'm sorry, but you don't have any #t" + itemToUse + "#.");
                    cm.dispose();
                    return;
                }
                cm.sendYesNo("Would you like to exchange 5 #t" + itemToUse + "# for " + gachamount + " Gacha tickets?");
            } else if (selection == 3) {
                // Exchange item for Custom Weapons
                if (!cm.haveItem(itemToUse)) {
                    cm.sendOk("I'm sorry, but you don't have any #t" + itemToUse + "#.");
                    cm.dispose();
                    return;
                }
                cm.sendYesNo("Would you like to exchange 10 #t" + itemToUse + "# for " + weaponAmount + " Random Custom Weapon?" + (weaponAmount > 1 ? "s" : "") + "?");
            } else if (selection == 4) {
                // Exchange item for buffs
                if (!cm.haveItem(itemToUse)) {
                    cm.sendOk("I'm sorry, but you don't have any #t" + itemToUse + "#.");
                    cm.dispose();
                    return;
                }
                cm.sendYesNo("Would you like to exchange 50 #t" + itemToUse + "# for " + buffAmount + " #t" + buff1ID + "#s and " + buffAmount + " #t" + buff2ID + "#s?");
            } else if (selection == 5) {
                // Exchange item for hired merchant
                if (!cm.haveItem(itemToUse)) {
                    cm.sendOk("I'm sorry, but you don't have any #t" + itemToUse + "#.");
                    cm.dispose();
                    return;
                }
                cm.sendYesNo("Would you like to exchange 10 #t" + itemToUse + "# for a " + hiredMerchantLength + " Day Hired Merchant?");
            } else if (selection == 6) {
                // Exchange item for Tier 1 bait
                if (!cm.haveItem(itemToUse)) {
                    cm.sendOk("I'm sorry, but you don't have any #t" + itemToUse + "#.");
                    cm.dispose();
                    return;
                }
                cm.sendYesNo("Would you like to exchange 5 #t" + itemToUse + "# for " + bait1amount + " Tier 1 bait?");
            } else if (selection == 7) {
                // Exchange item for Tier 2 bait
                if (!cm.haveItem(itemToUse)) {
                    cm.sendOk("I'm sorry, but you don't have any #t" + itemToUse + "#.");
                    cm.dispose();
                    return;
                }
                cm.sendYesNo("Would you like to exchange 10 #t" + itemToUse + "# for " + bait2amount + " Tier 2 bait?");
            } else if (selection == 8) {
                // Exchange item for Tier 3 bait
                if (!cm.haveItem(itemToUse)) {
                    cm.sendOk("I'm sorry, but you don't have any #t" + itemToUse + "#.");
                    cm.dispose();
                    return;
                }
                cm.sendYesNo("Would you like to exchange 15 #t" + itemToUse + "# for " + bait3amount + " Tier 3 bait?");
            }
        } else if (status == 2) {
				if (choice == 0) {
				// Handle VP to Maple Leaves
				if (vp <= 0) {
					cm.sendOk("I'm sorry, but you don't have any Vote Points to exchange!");
				} else if (vp >= 10) {
					cm.gainItem(itemToUse, +50);
					cm.getClient().useVotePoints(10);
					vp -= 10;
					cm.sendOk("You have successfully exchanged 10 Vote Points for 50 Maple Leaves.");
				} else if (vp >= 5) {
					cm.gainItem(itemToUse, +25);
					cm.getClient().useVotePoints(5);
					vp -= 5;
					cm.sendOk("You have successfully exchanged 5 Vote Points for 25 Maple Leaves.");
				} else {
            cm.sendOk("You don't have enough Vote Points to exchange.");
				}	
            } else if (choice == 1) {
                 // Handle item Gacha
                if (!cm.haveItem(itemToUse)) {
                    cm.sendOk("I'm sorry, but you don't have any #t" + itemToUse + "#.");
                } else {
                    cm.gainItem(itemToUse, -5);
                    cm.gainItem(5220000, 10); 
                    cm.sendOk("You have successfully exchanged 5 #t" + itemToUse + "# for 10 " + gachatix + " Gacha tickets.");
                }
            } else if (choice == 3) {
                // Handle item to Weapons
                if (!cm.haveItem(itemToUse)) {
                    cm.sendOk("I'm sorry, but you don't have any #t" + itemToUse + "#.");
                } else {
                    cm.gainItem(itemToUse, -1);
                    for (var i = 0; i < weaponAmount; i++) {
                        var weaponID = weapons[Math.floor(Math.random() * weapons.length)];
                        cm.gainItem(weaponID, 1);
                    }
                    cm.sendOk("You have successfully exchanged 1 #t" + itemToUse + "# for " + weaponAmount + " Random Maple Weapon" + (weaponAmount > 1 ? "s" : "") + ".");
                }
            } else if (choice == 4) {
                // Handle item to Buffs
                if (!cm.haveItem(itemToUse)) {
                    cm.sendOk("I'm sorry, but you don't have any #t" + itemToUse + "#.");
                } else {
                    cm.gainItem(itemToUse, -50);
                    for (var i = 0; i < buffAmount; i++) {
                        cm.gainItem(buff1ID, 1);
                        cm.gainItem(buff2ID, 1);
                    }
                    cm.sendOk("You have successfully exchanged 1 #t" + itemToUse + "# for " + buffAmount + " #t" + buff1ID + "#s and " + buffAmount + " #t" + buff2ID + "#s.");
                }
            } else if (choice == 5) {
                // Handle item to Hired Merchant
                if (!cm.haveItem(itemToUse)) {
                    cm.sendOk("I'm sorry, but you don't have any #t" + itemToUse + "#.");
                } else {
                    cm.gainItem(itemToUse, -1);
                    cm.gainItem(5030006, 1); // Assume Hired Merchant ID is 5020000
                    cm.sendOk("You have successfully exchanged 1 #t" + itemToUse + "# for a " + hiredMerchantLength + " Day Hired Merchant.");
                }
            } else if (choice == 6) {
                // Handle item to Tier 1 Bait
                if (!cm.haveItem(itemToUse)) {
                    cm.sendOk("I'm sorry, but you don't have any #t" + itemToUse + "#.");
                } else {
                    cm.gainItem(itemToUse, -1);
                    for (var i = 0; i < bait1amount; i++) {
                        cm.gainItem(bait1[0], 1);
                    }
                    cm.sendOk("You have successfully exchanged 1 #t" + itemToUse + "# for " + bait1amount + " Tier 1 bait.");
                }
            } else if (choice == 7) {
                // Handle item to Tier 2 Bait
                if (!cm.haveItem(itemToUse)) {
                    cm.sendOk("I'm sorry, but you don't have any #t" + itemToUse + "#.");
                } else {
                    cm.gainItem(itemToUse, -1);
                    for (var i = 0; i < bait2amount; i++) {
                        cm.gainItem(bait2[0], 1);
                    }
                    cm.sendOk("You have successfully exchanged 1 #t" + itemToUse + "# for " + bait2amount + " Tier 2 bait.");
                }
            } else if (choice == 8) {
                // Handle item to Tier 3 Bait
                if (!cm.haveItem(itemToUse)) {
                    cm.sendOk("I'm sorry, but you don't have any #t" + itemToUse + "#.");
                } else {
                    cm.gainItem(itemToUse, -1);
                    for (var i = 0; i < bait3amount; i++) {
                        cm.gainItem(bait3[0], 1);
                    }
                    cm.sendOk("You have successfully exchanged 1 #t" + itemToUse + "# for " + bait3amount + " Tier 3 bait.");
					cm.dispose();
                }
            }
            
            cm.dispose();
        }
    }
}
