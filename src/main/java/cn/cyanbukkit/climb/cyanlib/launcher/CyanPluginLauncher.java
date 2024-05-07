package cn.cyanbukkit.climb.cyanlib.launcher;

import cn.cyanbukkit.climb.cyanlib.loader.KotlinBootstrap;
import cn.nostmc.slope.Slope;
import cn.nostmc.slope.command.SlopeTest;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;

/**
 * 嵌套框架
 */

public class CyanPluginLauncher extends JavaPlugin {

    public static CyanPluginLauncher cyanPlugin;

    public CyanPluginLauncher() {
        cyanPlugin = this;
        KotlinBootstrap.init();
    }
    public File yaml;
    public YamlConfiguration config;

    public void registerCommand(Command command) {
        Class<?> pluginManagerClass = cyanPlugin.getServer().getPluginManager().getClass();
        try {
            Field field = pluginManagerClass.getDeclaredField("commandMap");
            field.setAccessible(true);
            SimpleCommandMap commandMap = (SimpleCommandMap) field.get(cyanPlugin.getServer().getPluginManager());
            commandMap.register(cyanPlugin.getName(), command);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onEnable() {
        yaml = new File("plugins/ModuleGame-Bukkit/addon/"+ getDescription().getName() +"/config.yml");
        if (!yaml.exists()) {
            try {
                if(yaml.getParentFile().mkdirs()) {
                    InputStream is = getResource("config.yml");
                    if (is != null) {
                        Files.copy(is, yaml.toPath());
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        config = YamlConfiguration.loadConfiguration(yaml);
        // 斜坡版 需绑定地图
        Slope.INSTANCE.start();
        registerCommand(SlopeTest.INSTANCE);
    }

    @Override
    public void onDisable() {

    }



}