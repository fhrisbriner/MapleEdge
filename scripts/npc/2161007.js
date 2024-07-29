var status = -1;
var rewardItemId = 4032831; // Set the ID of the item to be rewarded
var questId = 3164; // Set a unique quest ID for tracking the reward

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    } else {
        if (mode == 0 && status == 0) {
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        } else {
            status--;
        }
        if (status == 0) {
            if (cm.getQuestStatus(questId) == 1) { // Check if the quest status indicates reward has been given
                cm.sendOk("......");
                cm.dispose();
            } else {
                cm.gainItem(rewardItemId, 1); // Give the item to the player
                cm.newStatus.setCompleted(chr.getQuest(this).getCompleted())(questId, 1); // Set the quest status to indicate reward given
                cm.sendOk("Thank you for saving me! I don't know who brought me here..");
				cm.sendOk("Please tell my brother I am okay. Here......");
				cm.sendOk("Take this with you...");
                cm.dispose();
            }
        }
    }
}