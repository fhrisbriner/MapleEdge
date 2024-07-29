package constants.net;

import java.awt.*;

public class ServerConstants {

    //Server Version
    public static final short VERSION = 83;

    public static final Color EMBED_COLOR = Color.decode("#e53349");

    public static final boolean Account_Linked_Stats = true; //Adds 1% per 10 levels
    public static final int Account_LINK_EquipID = 1143168; // ItemID of Stats Booster
    //
    public static final boolean Account_MonsterBook_STAT = true; //
    public static final int Account_MonsterBook_EquipID = 1143168;  //

    //Debug Variables
    public static int[] DEBUG_VALUES = new int[10];             // Field designed for packet testing purposes


    //TODO strings are only used for discord webhook logging
    //public will be able to see these
    public static final String BAN_MESSAGE_URL = "https://discord.com/api/webhooks/"; // refer to bancommand.java for info
    public static final String USER_VOTED_URL = "https://discord.com/api/webhooks/"; // if we can even get in-game voting to work first

    //internal staff logging only //
    public static final String PLACE_HOLDER_URL = "https://discord.com/api/webhooks/";
    public static final String COMMAND_EXECUTION_URL = "https://discord.com/api/webhooks/";
    public static final String REGISTRATION_URL = "https://discord.com/api/webhooks/1265619016337199186/82g196W0Ag19bgfUQ3VvPo03oVKwrXXMOByHrBrE6MkBTG0piLkP7MSL9UaYAbE_Aw_n";

}
