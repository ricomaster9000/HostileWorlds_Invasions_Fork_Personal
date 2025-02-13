package com.corosus.inv.config;

import java.io.File;

import CoroUtil.ai.tasks.TaskDigTowardsTarget;
import com.corosus.inv.Invasion;
import modconfig.ConfigComment;
import modconfig.ConfigMod;
import modconfig.IConfigCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

public class ConfigInvasion implements IConfigCategory {

	@ConfigComment("Once a player has been playing for this many days, an invasion will start that night. If invasionCountingPerPlayer is false, it uses global world time instead")
	public static int firstInvasionNight = 3;

	@ConfigComment("If set to 3, there are 2 days of no invasions then the 3rd day is an invasion, etc")
	public static int invadeEveryXDays = 2;

	@ConfigComment("Amount of damage per second to give to mobs after invasion ends at sunrise to keep them from lingering around, set to 0 to disable")
	public static double damagePerSecondToInvadersAtSunrise = 0;

	@ConfigComment("use a per player active tracked playtime instead of global server time, used with firstInvasionNight and wave # in invasion configuration. Everyone will still get invasions on the same night but only if they qualify for one")
	public static boolean invasionCountingPerPlayer = true;

	@ConfigComment("-1 to disable. Max amount of invasions you can skip in a row before you are forced to deal with one")
	public static int maxConsecutiveInvasionSkips = 3;

	public static String Invasion_Message_startsTonight = TextFormatting.GOLD + "An invasion starts tonight! SpoOoOoky!";

	@ConfigComment("Used if invasionCountingPerPlayer is on")
	public static String Invasion_Message_startsTonightButNotYou = TextFormatting.GREEN + "An invasion might start tonight for others but not for you, you need about %d days played";

	@ConfigComment("The default invasion message if a wave doesn't have a custom one")
	public static String Invasion_Message_started = TextFormatting.RED + "An invasion has started! Be prepared!";

	public static String Invasion_Message_ended = TextFormatting.GREEN + "The invasion has ended! Next invasion in %d days!";

	public static String Invasion_Message_startedButSkippedForYou = TextFormatting.GREEN + "An invasion has started! But skipped for you!";

	public static String Invasion_Message_notLuckyNumberBypassed = TextFormatting.GREEN + "An invasion is skipped! The Lucky number was not drawn!";
	@ConfigComment("Used if invasionCountingPerPlayer is on")
	public static String Invasion_Message_startedButSkippedForYouTooSoon = TextFormatting.GREEN + "An invasion has started! But skipped for you because you havent been playing long enough, you need about %d days played";

	public static String Invasion_Message_tooLate = TextFormatting.RED + "Too late, invasion already started!";

	public static String Invasion_Message_notInvasionNight = "Not an invasion night, cant skip yet!";

	public static String Invasion_Message_alreadySkipping = TextFormatting.GREEN + "You are already skipping this nights invasion!";

	public static String Invasion_Message_skipping = TextFormatting.GREEN + "Skipping tonights invasion, skip count: %d";

	public static String Invasion_Message_skippedTooMany = TextFormatting.RED + "You've already skipped invasions %d times! You must fight!";

	public static String Invasion_Message_cantSleep = "You can't sleep during invasion nights!";

	public static String Invasion_Message_cantTeleport = "You can't leave the overworld during invasion nights!";
	
	@ConfigComment("Prevent players from sleeping through the night during invasion nights")
	public static boolean preventSleepDuringInvasions = true;

	@ConfigComment("Prevent players from teleporting away from the overworld during invasion nights")
	public static boolean preventDimensionTeleportingDuringInvasions = true;

	@ConfigComment("For seldom used but important things to print out in production")
	public static boolean useLoggingLog = true;

	@ConfigComment("For debugging things")
	public static boolean useLoggingDebug = false;

	@ConfigComment("For logging warnings/errors")
	public static boolean useLoggingError = true;

	@ConfigComment("Use at own risk, will not support")
	public static boolean enableAdvancedDeveloperConfigFiles = false;

	public static boolean Block_SacrificeNoRecipe = false;

	@ConfigComment("Prevents permanent damage caused by explosions during invasions, since zombie miners will be making holes they can get in")
	public static boolean convertExplodedBlocksToRepairingBlocksDuringInvasions = true;

	@ConfigComment("Chests, machines, etc, arent normal blocks that we can convert to repairing blocks, so instead this setting just protects them from being harmed at all by explosions")
	public static boolean preventExplodedTileEntitiesDuringInvasions = true;

	@ConfigComment("Disable if you want traditional block breaking that drops the itemized version of the block when its mined")
	public static boolean convertMinedBlocksToRepairingBlocksDuringInvasions = true;

	@ConfigComment("Allow tile entities (Chests, machines, etc) to be mined the traditional way, breaks it like a player would. Note: Tile entities will never be converted to repairing blocks, they're not supported.")
	public static boolean preventMinedTileEntitiesDuringInvasions = true;

	//public static boolean spawnItemsOfBlocksIfNotUsingRepairingBlock = true;

	@ConfigComment("UNUSED")
	public static String Sacrifice_Item_Name = "minecraft:diamond";
	@ConfigComment("UNUSED")
	public static int Sacrifice_Item_Meta = -1;
	@ConfigComment("UNUSED")
	public static int Sacrifice_CountNeeded = 1;
	@ConfigComment("UNUSED, Amount to multiply the count needed by for every skip done, to make continued skipping more difficult")
	public static double Sacrifice_CountNeeded_Multiplier = 2;
	@ConfigComment("UNUSED")
	public static boolean Sacrifice_CountNeeded_Multiplier_ResetOnInvasionNoSkip = true;

	//TODO: USE
	@ConfigComment("UNUSED, Make it so if multiplier was 2 and last needed amount was 2, next wave would be 4, then next wave after that would be 8, and so on")
	public static boolean Sacrifice_CountNeeded_Multiplier_MultiplyLastAmountNeeded = true;

	@ConfigComment("Force teleport players to their overworld spawn point during invasions after a configurable delay, if they arent already in the overworld")
	public static boolean forcePlayersToOverworldDuringInvasion = true;

	@ConfigComment("Time in ticks to wait before force teleporing player, 20 ticks = 1 second")
	public static int forcePlayersToOverworldDuringInvasion_TickDelay = 20*120;

	@ConfigComment("Message to warn player to get back to overworld, use of %d will show the seconds they have")
	public static String forcePlayersToOverworldDuringInvasion_FirstWarningMessage = TextFormatting.RED + "An invasion has started in the overworld, you must get back to fight it or be teleported to your spawn automatically in %d seconds";

	public static String forcePlayersToOverworldDuringInvasion_FinalWarningMessage = TextFormatting.RED + "10 Seconds to automatic teleport!";

	@ConfigComment("Message to tell player upon automatic teleport")
	public static String forcePlayersToOverworldDuringInvasion_TPMessage = TextFormatting.GOLD + "You've been automatically teleported to your spawn for the invasion!";

	@Override
	public String getName() {
		return "InvasionConfig";
	}

	@Override
	public String getRegistryName() {
		return "invasionConfig";
	}

	@Override
	public String getConfigFileName() {
		return "HW_Invasions" + File.separator + "InvasionConfig";
	}

	@Override
	public String getCategory() {
		return "Misc";
	}

	@Override
	public void hookUpdatedValues() {
		
		firstInvasionNight = MathHelper.clamp(firstInvasionNight, 0, 9999);
		invadeEveryXDays = MathHelper.clamp(invadeEveryXDays, 0, 9999);

		TaskDigTowardsTarget.convertMinedBlocksToRepairingBlocksDuringInvasions = convertMinedBlocksToRepairingBlocksDuringInvasions;
		TaskDigTowardsTarget.preventMinedTileEntitiesDuringInvasions = preventMinedTileEntitiesDuringInvasions;

		if (enableAdvancedDeveloperConfigFiles && !ConfigMod.instance.configLookup.containsKey(Invasion.configDev.getRegistryName())) {
			ConfigMod.addConfigFile(null, Invasion.configDev);
		}
		
	}

}
