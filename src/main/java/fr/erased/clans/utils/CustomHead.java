package fr.erased.clans.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class CustomHead {

    public ItemStack create(String name, List<String> lore, String texture){
        ItemStack skull = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short) 3);
        SkullMeta skullM = (SkullMeta) skull.getItemMeta();
        if(name != null) skullM.setDisplayName(name);
        if(lore != null) skullM.setLore(lore);
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", texture));
        Field profileField;
        try {
            profileField = skullM.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullM, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(skullM);
        return skull;
    }

    public ItemStack create(String texture) {
        return create(null, null, texture);
    }

}