package edu.byu.cs.minecraft.scenicview.common.communication;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Family_hint {
    Timer timer;
    MinecraftServer s;


    private String[] hintArray_family = {"NAME", "AGE", "Where is he/she from", "Family Memebers", "PETS", "SIBILINGS", "COUSINS", "Things to do with family"};

    public void popHint(){
        s = FMLCommonHandler.instance().getMinecraftServerInstance();
        timer = new Timer();
        timer.schedule(new Hint(), 0, 5*1000);
    }

    public String RandomHint(){
        Random rand = new Random();
        int index = rand.nextInt(hintArray_family.length);
        return hintArray_family[index];

    }

    class Hint extends TimerTask{
        public void run(){
            while (true){
                s.getCommandManager().executeCommand(s, "/msg @a[x=-7,y=56,z=38,dx=15,dy=7,dz=11] Hint:\nTalks about: " + RandomHint());
            }
        }
    }
}
