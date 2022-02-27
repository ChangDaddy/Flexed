package gg.minehut.flexed.items;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.util.Base64;

@RequiredArgsConstructor
@Getter
public class Item implements Serializable {
    private final String name;
    private final ItemStack icon;
    private final int price;

    @SneakyThrows
    public String serialize() {
        ByteArrayOutputStream io = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(io);
        os.writeObject(this);
        os.flush();
        byte[] serializedObject = io.toByteArray();
        return Base64.getEncoder().encodeToString(serializedObject);
    }

    @SneakyThrows
    public static Item deserialize(String encodedObject) {
        byte[] serializedObject = Base64.getDecoder().decode(encodedObject);

        ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
        ObjectInputStream is = new ObjectInputStream(in);

        return (Item) is.readObject();
    }

}
