var status = -1;

function start() {
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
            cm.sendOk("Thank you for rescuing me. Tell my brother I am okay. Take this item with you.");
        } else if (status == 1) {
            cm.gainItem(4032831, 1)
            cm.warp(211060200);
            cm.dispose();
        }
    }
}
