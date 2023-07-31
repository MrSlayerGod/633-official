package com.rs.plugin.impl.objects;

import com.rs.constants.Animations;
import com.rs.game.item.Item;
import com.rs.game.map.GameObject;
import com.rs.game.player.Player;
import com.rs.plugin.listener.ObjectListener;
import com.rs.plugin.wrapper.ObjectSignature;

import skills.Skills;
import skills.prayer.Bone;
import skills.prayer.PrayerBoneAltar;

@ObjectSignature(objectId = {409, 36972, 34616, 19145}, name = {})
public class PrayerAltarObjectPlugin extends ObjectListener {

	@Override
	public void execute(Player player, GameObject object, int optionId) throws Exception {
		if (object.getDefinitions().containsOption("Pray") || object.getDefinitions().containsOption("Pray-at")){
			final int maxPrayer = player.getSkills().getTrueLevel(Skills.PRAYER) * 10;
            if (player.getPrayer().getPoints() < maxPrayer) {
                player.setNextAnimation(Animations.PRAYING_TO_ALTAR);
                player.getPrayer().restorePrayer(maxPrayer);
                player.getPackets().sendGameMessage("You've recharged your prayer points.");
                return;
            } else {
                player.getPackets().sendGameMessage("You already have full Prayer points.");
                return;
            }
		}
	}

	@Override
	public void executeItemOnObject(Player player, GameObject object, Item item) throws Exception {
		Bone.VALUES.stream().filter(bone -> bone.getId() == item.getId())
		.forEach(bone ->  new PrayerBoneAltar(player, object, bone).start());
	}
}