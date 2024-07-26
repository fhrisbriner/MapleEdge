package client.command.commands.gm6;

import client.Client;
import client.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scripting.AbstractScriptManager;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileInputStream;
import org.yaml.snakeyaml.Yaml;

public class DevtestCommand extends Command {
    {
        setDescription("Runs devtest.js. Developer utility - test stuff without restarting the server. Also reloads config.yaml (kind of)");
    }

    private static final Logger log = LoggerFactory.getLogger(DevtestCommand.class);

    private static class DevtestScriptManager extends AbstractScriptManager {
        @Override
        public ScriptEngine getInvocableScriptEngine(String path) {
            return super.getInvocableScriptEngine(path);
        }

    }

    @Override
    public void execute(Client client, String[] params) {
        // Reload the config.yaml
        try {
            reloadConfig();
            log.info("config.yaml reloaded successfully.");
        } catch (Exception e) {
            log.error("Failed to reload config.yaml", e);
        }

        // Execute the devtest.js script
        DevtestScriptManager scriptManager = new DevtestScriptManager();
        ScriptEngine scriptEngine = scriptManager.getInvocableScriptEngine("devtest.js");
        try {
            Invocable invocable = (Invocable) scriptEngine;
            invocable.invokeFunction("run", client.getPlayer());
        } catch (ScriptException | NoSuchMethodException e) {
            log.info("devtest.js run() threw an exception", e);
        }
    }

    private void reloadConfig() throws Exception {
        File f = new File(System.getProperty("user.dir")+ File.separator + "config.yaml" );
        Yaml yaml = new Yaml();
        try {
            FileInputStream inputStream = new FileInputStream(f);
            Object config = yaml.load(inputStream);
            //Assuming you have a static method to set the configuration
            //Config.setInstance(config);
            //log.info("Configuration loaded: " + config.toString());
        } catch (Exception e) {
            log.error("Error loading config.yaml", e);
            throw e;
        }
    }
}
