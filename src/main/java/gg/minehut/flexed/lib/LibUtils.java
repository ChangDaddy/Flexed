package gg.minehut.flexed.lib;

import gg.minehut.flexed.Flexed;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class LibUtils {
    final JavaPlugin flexed = Flexed.getInstance();
    final File dataFolder;

    public LibUtils() {
        this.dataFolder = flexed.getDataFolder();
        if(!dataFolder.exists())
            dataFolder.mkdir();

        loadFile("SpiGUI", "https://github.com/ChangDaddy/libs/blob/main/SpiGUI-1.2.2-SNAPSHOT.jar?raw=true");
        loadFile("classindex", "https://repo1.maven.org/maven2/org/atteo/classindex/classindex/3.9/classindex-3.9.jar");
        loadFile("fastutil", "https://repo1.maven.org/maven2/it/unimi/dsi/fastutil/8.1.0/fastutil-8.1.0.jar");
        loadFile("javaassist", "https://github.com/ChangDaddy/libs/blob/main/javassist-3.28.0-GA.jar?raw=true");
        loadFile("jsr", "https://github.com/ChangDaddy/libs/blob/main/jsr305-3.0.2.jar?raw=true");
        loadFile("reflections", "https://github.com/ChangDaddy/libs/blob/main/reflections-0.10.2.jar?raw=true");
        loadFile("slf4j", "https://github.com/ChangDaddy/libs/blob/main/slf4j-api-1.7.32.jar?raw=true");

    }


    @SneakyThrows
    private void loadFile(String fileName, String fileUrl) {
        File lib = new File(dataFolder, String.format("lib/%s.jar", fileName));

        if(!lib.exists()) {
            if(!lib.getParentFile().exists())
                lib.getParentFile().mkdir();

            flexed.getLogger().info(String.format("Downloading %s...", fileName));
            FileUtil.download(lib, fileUrl);
            flexed.getLogger().info(String.format("Finished downloading %s", fileName));

        }
    }
}
