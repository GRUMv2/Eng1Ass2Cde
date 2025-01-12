package io.github.GRUMv2.EngSim.Server.EventHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class QuitTheGameAchievement extends Achievement {
    private int count = 0;

    QuitTheGameAchievement() {
        super();
        awardName = "Quit the game 14 times!";

        File file = new File("AchConst.bin");
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter r = new FileWriter(file);
                r.write(0);
                r.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileReader rr = new FileReader(file);
            count = rr.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        count++;

        try {
            FileWriter r = new FileWriter(file);
            r.write(count);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean tick(double delta, EventHandler handler) {
        if (count == 15) {
            System.out.println("Awarding");
            this.award();
            return false;
        }
        return true;
    }
}
