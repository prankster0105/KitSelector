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
        Material mainHandItemMaterial;
        Material offHandItemMaterial;

        List<String> enchantList;


        //wrapping enchants and materials into a try catch statement to assure
        try {
            enchantList = fileConfiguration.getStringList("kits." + kitName + "enchants");
            this.kitType = fileConfiguration.getString("kits." + kitName + ".kit-type");


           helmetMaterial = Material.valueOf(fileConfiguration.getString("kits." + kitName + ".helmet.material"));
           chestplateMaterial = Material.valueOf(fileConfiguration.getString("kits." + kitName + ".chestplate.material"));
           leggingsMaterial = Material.valueOf(fileConfiguration.getString("kits." + kitName + ".leggings.material"));
           bootsMaterial = Material.valueOf(fileConfiguration.getString("kits." + kitName + ".boots.material"));
           mainHandItemMaterial = Material.valueOf(fileConfiguration.getString("kits." + kitName + ".sword.material"));
           offHandItemMaterial = Material.valueOf(fileConfiguration.getString("kits." + kitName + ".offHandItem.material"));
        } catch(IllegalArgumentException e) {
            KitSelector.getInstance().getLogger().severe("Something wrong happened while obtaining armour from kits." + kitName + ", Error: ");
            e.printStackTrace();
            return;
        }

        this.helmet = new ItemStack(helmetMaterial);
        this.chestplate = new ItemStack(chestplateMaterial);
        this.leggings = new ItemStack(leggingsMaterial);
        this.boots = new ItemStack(bootsMaterial);
        this.mainHandItem = new ItemStack(mainHandItemMaterial);
        this.offHandItem = new ItemStack(offHandItemMaterial);


        //Put the items in a set to loop around the mlater
        Set<ItemStack> items = new HashSet<>();
        items.add(helmet);
        items.add(chestplate);
        items.add(leggings);
        items.add(boots);
        items.add(this.mainHandItem);


        for (ItemStack itemsPiece : items) {
            ItemMeta itemsPieceMeta = itemsPiece.getItemMeta();
            itemsPieceMeta.setUnbreakable(setUnbreakable);

            final String[] itemsPieceName = itemsPiece.getType().name().split("_");



            try {
                KitSelector.getInstance().getLogger().info(itemsPieceName[1]);

                List<String> enchantKeys = fileConfiguration.getStringList("kits." + kitName + "." + itemsPieceName[1].toLowerCase() + ".enchants");

                for (String enchantKey : enchantKeys) {

                    if (enchantKey.equalsIgnoreCase("no-enchant")) {
                        KitSelector.getInstance().getLogger().info("No enchant for item " + itemsPieceName[1]);
                        continue;
                    }

                    String[] splitKeys = enchantKey.split(":");


                    // For debugging, just print the 2 to make sure that they're not null or we are getting the right one
                    // This will be removed in the future when KitSelector comes out of its Alpha state
                    KitSelector.getInstance().getLogger().info(splitKeys[0] + " " + splitKeys[1]);

                    Enchantment wantedEnchant = null;

                    try {
                        wantedEnchant = Enchantment.getByKey(NamespacedKey.minecraft(splitKeys[0]));
                    } catch(Exception e) {
                        KitSelector.getInstance().getLogger().severe("Something went wrong while trying to apply enchantment to kit " + kitName);
                        e.printStackTrace();
                    }

                    int enchantLevel = Integer.valueOf(splitKeys[1]);
                    if (wantedEnchant != null) itemsPieceMeta.addEnchant(wantedEnchant, enchantLevel, true);
                }

                itemsPiece.setItemMeta(itemsPieceMeta);
            } catch (Exception e) {
                e.printStackTrace();
                KitSelector.getInstance().getLogger().info("Something went wrong");
            }


        }
    }


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
