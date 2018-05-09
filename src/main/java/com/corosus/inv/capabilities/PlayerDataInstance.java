package com.corosus.inv.capabilities;

import CoroUtil.difficulty.data.spawns.DataActionMobSpawns;
import CoroUtil.difficulty.data.spawns.DataMobSpawnsTemplate;
import CoroUtil.forge.CULog;
import CoroUtil.util.CoroUtilEntity;
import com.corosus.inv.InvLog;
import com.corosus.inv.InvasionEntitySpawn;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Corosus on 3/4/2017.
 */
public class PlayerDataInstance {

    private List<InvasionEntitySpawn> listSpawnables = new ArrayList<>();
    private EntityPlayer player;

    public boolean dataPlayerInvasionActive;
    public boolean dataPlayerInvasionWarned;
    public long dataCreatureLastPathWithDelay;

    private List<Class> listSpawnablesCached = new ArrayList<>();

    public PlayerDataInstance() {

    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public PlayerDataInstance setPlayer(EntityPlayer player) {
        this.player = player;
        return this;
    }

    public void initNewInvasion(DataMobSpawnsTemplate profile) {
        //resetInvasion();

        //convert data to runtime
        InvLog.dbg("init invasion spawn template to runtime data");
        if (profile.spawns.size() > 0) {
            for (DataActionMobSpawns spawns : profile.spawns) {
                InvasionEntitySpawn newSpawn = new InvasionEntitySpawn();
                //we must clone the list, not use direct reference, or we will corrupt(blank out) the data later
                newSpawn.spawnProfile = spawns.copy();
                InvLog.dbg("adding spawns: " + newSpawn.toString(true));
                listSpawnables.add(newSpawn);
            }
        } else {
            InvLog.dbg("CRITICAL: there was no spawn data to setup!");
        }

        //TEST
        /*NBTTagCompound nbt = new NBTTagCompound();
        writeNBT(nbt);

        resetInvasion();

        readNBT(nbt);*/
    }

    public void stopInvasion() {
        resetInvasion();
    }

    public void resetInvasion() {
        InvLog.dbg("resetInvasion()");
        //we make a copy of this, but why clear anyways?
        for (InvasionEntitySpawn spawns : listSpawnables) {
            spawns.clear();
        }
        listSpawnables.clear();
        listSpawnablesCached.clear();
    }

    public InvasionEntitySpawn getRandomEntityClassToSpawn() {
        List<InvasionEntitySpawn> listSpawnablesTry = new ArrayList<>();

        //filter out ones that are used up
        for (InvasionEntitySpawn spawns : listSpawnables) {
            if (spawns.spawnCountCurrent < spawns.spawnProfile.count && spawns.spawnProfile.entities.size() > 0) {
                listSpawnablesTry.add(spawns);
            }
        }

        Random random = new Random();
        //chose random spawn profile and increment
        //InvasionEntitySpawn spawns = listSpawnablesTry.get(random.nextInt(listSpawnablesTry.size()));

        //TODO: reorder code logic, outside this, spawn could fail so we wouldnt want to increment this!
        //spawns.spawnCountCurrent++; FIX ^

        //return spawns.spawnProfile.entities.get(random.nextInt(spawns.spawnProfile.entities.size()));

        if (listSpawnablesTry.size() > 0) {
            InvasionEntitySpawn spawn = listSpawnablesTry.get(random.nextInt(listSpawnablesTry.size()));
            InvLog.dbg("returning this to spawn in: " + spawn.toString());
            return spawn;
        } else {
            //this should be ok, happens when all the things that will spawn have spawned
            if (listSpawnables.size() <= 0) {
                InvLog.dbg("nothing to spawn and there was never anything to choose from, nothing to invade, this is bad?");
            } else {
                //fine, probably means everything already spawned
                //InvLog.dbg("all spawnables spawned in");
            }
            //System.out.println("nothing to spawn!");
            //return new InvasionEntitySpawn();
            return null;
        }


    }

    public List<Class> getSpawnableClasses() {
        if (listSpawnablesCached.size() == 0) {
            for (InvasionEntitySpawn spawns : listSpawnables) {
                for (String spawnable : spawns.spawnProfile.entities) {
                    Class classToSpawn = CoroUtilEntity.getClassFromRegisty(spawnable);
                    if (classToSpawn != null) {
                        if (!listSpawnablesCached.contains(classToSpawn)) {
                            listSpawnablesCached.add(classToSpawn);
                        }
                    }
                }
            }
        }
        return listSpawnablesCached;
    }

    public void readNBT(NBTTagCompound nbtTagCompound) {

        NBTTagCompound nbt = nbtTagCompound.getCompoundTag("spawns");

        Iterator it = nbt.getKeySet().iterator();

        while (it.hasNext()) {
            String tagName = (String) it.next();
            NBTTagCompound nbtEntry = nbt.getCompoundTag(tagName);

            InvasionEntitySpawn spawn = new InvasionEntitySpawn();
            spawn.readNBT(nbtEntry);
            listSpawnables.add(spawn);
        }

        dataPlayerInvasionActive = nbtTagCompound.getBoolean("dataPlayerInvasionActive");
        dataPlayerInvasionWarned = nbtTagCompound.getBoolean("dataPlayerInvasionWarned");

        CULog.dbg("read done");
    }

    public void writeNBT(NBTTagCompound nbtTagCompound) {

        NBTTagCompound nbt = new NBTTagCompound();
        for (int i = 0; i < listSpawnables.size(); i++) {
            NBTTagCompound nbtEntry = new NBTTagCompound();
            InvasionEntitySpawn spawn = listSpawnables.get(i);
            spawn.writeNBT(nbtEntry);
            nbt.setTag("spawn_" + i, nbtEntry);
        }
        nbtTagCompound.setTag("spawns", nbt);

        nbtTagCompound.setBoolean("dataPlayerInvasionActive", dataPlayerInvasionActive);
        nbtTagCompound.setBoolean("dataPlayerInvasionWarned", dataPlayerInvasionWarned);
    }

}
