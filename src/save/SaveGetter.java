package save;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class SaveGetter {

    public PenguinSave getPenguin() {
        YamlReader reader = null;
        PenguinSave save = null;
        try {
            reader = new YamlReader(new FileReader(System.getProperty("user.home") + "/learntofly/penguin.yml"));
            save = reader.read(PenguinSave.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (YamlException e) {
            e.printStackTrace();
        }
        return save;
    }

}
