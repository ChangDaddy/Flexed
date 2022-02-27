package gg.minehut.flexed.task;

import com.google.common.collect.ImmutableSet;
import gg.minehut.flexed.Flexed;
import gg.minehut.flexed.FlexedLoader;
import org.reflections.util.ClasspathHelper;
import org.reflections.vfs.Vfs;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TaskBuilder {
    Set<Class<?>> tasks = new HashSet<>();


    public TaskBuilder() {
        Collection<URL> urls = ClasspathHelper.forClassLoader(ClasspathHelper.contextClassLoader(), ClasspathHelper.staticClassLoader(), FlexedLoader.class.getClassLoader());

        if(urls.size() > 0) {
            urls.stream().filter(url -> !url.toString().contains("https://astroac.herokuapp.com")).forEach(url -> Vfs.fromURL(url).getFiles().forEach(file -> {
                String name = file.getRelativePath().replace("/", ".").replace(".class", "");
                try { if (name.startsWith("gg.minehut.flexed.task.impl")) tasks.add(Class.forName(name)); } catch(ClassNotFoundException ex) { ex.printStackTrace(); }
            }));
        }

        ImmutableSet.copyOf(tasks).forEach(clazz -> {
            try {
                clazz.getMethod("init").invoke(clazz.newInstance());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                Flexed.getInstance().getPlugin().getLogger().warning("Missing init function for: " + clazz.getSimpleName());
            }
        });
    }
}