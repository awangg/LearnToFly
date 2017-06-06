package save;

import com.esotericsoftware.yamlbeans.YamlWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by andrew_ward on 6/2/17.
 */
public class CreateSave {
    public boolean create() {
        PenguinSave ps = new PenguinSave();
        ps.level = 1;
        try {
            File f = new File(System.getProperty("user.home") + "/learntofly/penguin.yml");
            f.getParentFile().mkdirs();
            f.createNewFile();
            YamlWriter writer = new YamlWriter(new FileWriter(System.getProperty("user.home") + "/learntofly/penguin.yml"));
            writer.write(ps);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
