package me.notprankster.kitselector.objects;

import com.sun.istack.internal.Nullable;
import me.notprankster.kitselector.KitSelector;
import me.notprankster.kitselector.data.DataHandler;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Kit {

    private String displayName;
    private String description;
    private String kitType;

    private ItemStack mainHandItem;
    private ItemStack offHandItem;

    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;

    private boolean isUnbreakable = false;


    //Constructor
    public Kit(String kitName, boolean setUnbreakable) {
        FileConfiguration fileConfiguration = DataHandler.getInstance().getKitInfo();


        this.displayName = kitName;
        this.isUnbreakable = setUnbreakable;

        Material helmetMaterial;
        Material chestplateMaterial;
        Material leggingsMaterial;
        Material bootsMaterial;

        List<String> enchantList;


        //wrapping enchants and materials into a try catch statement to assure
        try {
            enchantList = fileConfiguration.getStringList("kits." + kitName + "enchants");
            this.kitType = fileConfiguration.getString("kits." + kitName + ".kit-type");


           helmetMaterial = Material.valueOf(fileConfiguration.getString("kits." + kitName + ".helmet.material"));
           chestplateMaterial = Material.valueOf(fileConfiguration.getString("kits." + kitName + ".chestplate.material"));
           leggingsMaterial = Material.valueOf(fileConfiguration.getString("kits." + kitName + ".leggings.material"));
           bootsMaterial = Material.valueOf(fileConfiguration.getString("kits." + kitName + ".boots.material"));
        } catch(IllegalArgumentException e) {
            KitSelector.getInstance().getLogger().severe("Something wrong happened while obtaining armour from kits." + kitName + ", Error: ");
            e.printStackTrace();
            return;
        }

        this.helmet = new ItemStack(helmetMaterial);
        this.chestplate = new ItemStack(chestplateMaterial);
        this.leggings = new ItemStack(leggingsMaterial);
        this.boots = new ItemStack(bootsMaterial);


        //Put the items in a set to loop around the mlater
        Set<ItemStack> armor = new HashSet<>();
        armor.add(helmet);
        armor.add(chestplate);
        armor.add(leggings);
        armor.add(boots);


        for (ItemStack armorPiece : armor) {
            ItemMeta armorPieceMeta = armorPiece.getItemMeta();
            armorPieceMeta.setUnbreakable(setUnbreakable);

            final String[] armorPieceName = armorPiece.getType().name().split("_");



            try {
                KitSelector.getInstance().getLogger().info(armorPieceName[1]);

                List<String> enchantKeys = fileConfiguration.getStringList("kits." + kitName + "." + armorPieceName[1].toLowerCase() + ".enchants");

                for (String enchantKey : enchantKeys) {
                    String[] splitKeys = enchantKey.split(":");


                    // For debugging, just print the 2 to make sure that they're not null or we are getting the right one
                    KitSelector.getInstance().getLogger().info(splitKeys[0] + " " + splitKeys[1]);

                    armorPieceMeta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(splitKeys[0])), Integer.valueOf(splitKeys[1]), false);
                }

                armorPiece.setItemMeta(armorPieceMeta);
            } catch (Exception e) {
                e.printStackTrace();
                KitSelector.getInstance().getLogger().info("SOmething went wrong man.");
            }


        }
    }

    //getters and setters
    public String getDisplayName() {
        return this.displayName;
    }

    public ItemStack getItemInMainHand() {
        return this.mainHandItem;
    }

    public ItemStack getItemInOffHand() {
        return this.offHandItem;
    }

    public ItemStack getHelmet() {
        return this.helmet;
    }

    public ItemStack getChestplate() {
        return this.chestplate;
    }

    public ItemStack getLeggings() {
        return this.leggings;
    }

    public ItemStack getBoots() { return this.boots; }

    public boolean isUnbreakable() { return this.isUnbreakable; }

    public String getKitType() { return this.kitType; }
}
