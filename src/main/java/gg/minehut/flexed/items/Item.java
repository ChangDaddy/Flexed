package gg.minehut.flexed.items;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.Base64;

@RequiredArgsConstructor
@Getter
public class Item implements Serializable {
    private final String name;

    public String serialize() {
        try {
            ByteArrayOutputStream io = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(io);
            os.writeObject(this);
            os.flush();
            byte[] serializedObject = io.toByteArray();
            return Base64.getEncoder().encodeToString(serializedObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Item deserialize(String encodedObject) {
        try {
            byte[] serializedObject = Base64.getDecoder().decode(encodedObject);

            ByteArrayInputStream in = new ByteArrayInputStream(serializedObject);
            ObjectInputStream is = new ObjectInputStream(in);

            return (Item) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
