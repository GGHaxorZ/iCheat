/*
 * Copyright (c) 2015. Copyright (c) 2015 Jonah Seguin (Shawckz).  All rights reserved.  You may not modify, decompile, distribute or use any code/text contained in this document(plugin) without explicit signed permission from Jonah Seguin.
 */

package com.shawckz.icheat.check;

import com.shawckz.icheat.ICheat;
import com.shawckz.icheat.checks.*;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

public class CheckManager {

    @Getter private final Map<CheckType,Check> checks;
    private static CheckManager instance;

    protected CheckManager(){
        checks = new HashMap<>();
    }

    public void setupChecks(){
        checks.clear();
        checks.put(CheckType.AUTO_CLICK, new CheckAutoClick());
        checks.put(CheckType.TAB_COMPLETE, new CheckTabComplete());
        checks.put(CheckType.FAST_BOW, new CheckFastBow());
        checks.put(CheckType.HEAD_ROLL, new CheckHeadRoll());
        checks.put(CheckType.FAST_HEAL, new CheckFastHeal());
        checks.put(CheckType.VCLIP, new CheckVClip());
        checks.put(CheckType.FLY, new CheckFly());
        checks.put(CheckType.SPEED, new CheckSpeed());

        for(Check c : checks.values()){
            c.setup();
            c.saveConfig();
            if(c.isEnabled()){
                Bukkit.getPluginManager().registerEvents(c, ICheat.getInstance());
            }
        }
    }

    public static CheckManager get() {
        if (instance == null) {
            synchronized (CheckManager.class) {
                if (instance == null) {
                    instance = new CheckManager();
                }
            }
        }
        return instance;
    }

    public final boolean hasCheck(String name){
        return getCheck(name) != null;
    }

    public final Check getCheck(String name){
        return getCheck(CheckType.fromString(name));
    }

    public final boolean hasCheck(CheckType hack){
        return getCheck(hack) != null;
    }

    public final Check getCheck(CheckType hack){
        if (checks.containsKey(hack)) {
            return checks.get(hack);
        }
        return null;
    }

}
