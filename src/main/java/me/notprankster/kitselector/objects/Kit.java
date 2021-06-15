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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.HashMap;
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

    private Set<PotionEffect> potionEffects = new HashSet<>();
    private Set<ItemStack> inventory = new HashSet<>();

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
           mainHandItemMaterial = Material.valueOf(fileConfiguration.getString("kits." + kitName + ".items.mainHandItem.material"));
           offHandItemMaterial = Material.valueOf(fileConfiguration.getString("kits." + kitName + ".items.offHandItem.material"));
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

        Set<ItemStack> handItems = new HashSet<>();
        handItems.add(mainHandItem);
        handItems.add(offHandItem);





        for (ItemStack itemsPiece : items) {
            ItemMeta itemsPieceMeta = itemsPiece.getItemMeta();
            itemsPieceMeta.setUnbreakable(setUnbreakable);
            final String[] itemsPieceName = itemsPiece.getType().name().split("_");

            try {
                String itemName = itemsPieceName[1] != null ? itemsPieceName[1] : itemsPieceName[0];


                KitSelector.getInstance().getLogger().info(itemsPieceName[1]);
                List<String> enchantKeys = fileConfiguration.getStringList("kits." + kitName + "." + itemName.toLowerCase() + ".enchants");

                for (String enchantKey : enchantKeys) {

                    if (enchantKey.equalsIgnoreCase("no-enchant")) {
                        KitSelector.getInstance().getLogger().info("No enchant for item " + itemName);
                        continue;
                    }

                    String[] splitKeys = enchantKey.split(":");

                    // For debugging, just print the 2 to make sure that they're not null or we are getting the right one
                    // This will be removed in the future when KitSelector comes out of its Alpha state
                    KitSelector.getInstance().getLogger().info(splitKeys[0] + " " + splitKeys[1]);

                    Enchantment wantedEnchant = null;

                    try {
                        wantedEnchant = Enchantment.getByKey(NamespacedKey.minecraft(splitKeys[0]));
                    } catch (Exception e) {
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

        // for mainhand and offhand items

        ItemMeta mainHandItemMeta = !mainHandItem.getType().isAir() ? mainHandItem.getItemMeta() : null;
        ItemMeta offHandItemMeta = !offHandItem.getType().isAir() ? offHandItem.getItemMeta() : null;

        if (!(mainHandItemMeta == null)) {
            List<String> handItemEnchants = fileConfiguration.getStringList("kits." + kitName + ".items.mainHandItem.enchants");

            for (String enchantKey : handItemEnchants) {
                if (enchantKey.equalsIgnoreCase("NO-ENCHANT")) {
                    KitSelector.getInstance().getLogger().info("No enchant for item " + mainHandItemMeta.getDisplayName());
                    continue;
                }

                String[] splitKeys = enchantKey.split(":");

                Enchantment wantedEnchant = null;

                try {
                    wantedEnchant = Enchantment.getByKey(NamespacedKey.minecraft(splitKeys[0]));
                } catch (IllegalArgumentException e) {
                    KitSelector.getInstance().getLogger().info("Something went wrong while trying to apply enchantmnet");
                    e.printStackTrace();
                }

                int enchantLevel = Integer.valueOf(splitKeys[1]);
                if (!(wantedEnchant == null)) mainHandItemMeta.addEnchant(wantedEnchant, enchantLevel, true);
            }

            mainHandItem.setItemMeta(mainHandItemMeta);
        }

        if (!(offHandItemMeta == null)) {
            List<String> handItemEnchants = fileConfiguration.getStringList("kits." + kitName + ".items.offHandItem.enchants");

            for (String enchantKey : handItemEnchants) {
                if (enchantKey.equalsIgnoreCase("NO-ENCHANT")) {
                    KitSelector.getInstance().getLogger().info("No enchant for item " + offHandItemMeta.getDisplayName());
                    continue;
                }

                String[] splitKeys = enchantKey.split(":");
                Enchantment wantedEnchant = null;

                try {
                    wantedEnchant = Enchantment.getByKey(NamespacedKey.minecraft(splitKeys[0]));
                } catch (IllegalArgumentException e) {
                    KitSelector.getInstance().getLogger().info("Something went wrong while trying to apply enchant to item");
                    e.printStackTrace();
                }

                int enchantLevel = Integer.parseInt(splitKeys[1]);
                if (!(wantedEnchant == null)) offHandItemMeta.addEnchant(wantedEnchant, enchantLevel, true);
            }

            offHandItem.setItemMeta(offHandItemMeta);
        }

        try {
            KitSelector.getInstance().getLogger().info("\n\nEFFECTS==");
            List<String> kitPotionEffects = fileConfiguration.getStringList("kits." + kitName + ".effects");

            for (String potionEffect : kitPotionEffects) {
                if (potionEffect.equalsIgnoreCase("NO-EFFECT")) break;
                String[] splitKeys = potionEffect.split(":");
                PotionEffect convertedPotionEffect = null;

                KitSelector.getInstance().getLogger().info("PotionEffect: " + splitKeys[0] + " level: " + splitKeys[1]);

                convertedPotionEffect = new PotionEffect(PotionEffectType.getByName(splitKeys[0].toUpperCase()), 99999, Integer.parseInt(splitKeys[1]) - 1);

                KitSelector.getInstance().getLogger().info(convertedPotionEffect.toString());
                potionEffects.add(convertedPotionEffect);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //inventory stuff

        List<String> inventoryItems = fileConfiguration.getStringList("kits." + kitName + ".inventory");

        for (String inventoryItem : inventoryItems) {
            if (inventoryItem.equalsIgnoreCase("NO-ITEM")) {
                KitSelector.getInstance().getLogger().info("No items for kit " + kitName);
                continue;
            }

            String[] splitKeys = inventoryItem.split(":");

            Material itemMaterial = null;

            try {
                itemMaterial = Material.valueOf(splitKeys[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            int itemCount = Integer.parseInt(splitKeys[1]);
            if (!(itemMaterial == null)) {
                ItemStack item = new ItemStack(itemMaterial);
                item.setAmount(itemCount);

                inventory.add(item);
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

    public Set<ItemStack> getInventory() {
        return this.inventory;
    }

    public ItemStack getBoots() { return this.boots; }

    public boolean isUnbreakable() { return this.isUnbreakable; }

    public String getKitType() { return this.kitType; }

    public Set<PotionEffect> getPotionEffects() {
        return potionEffects;
    }
}
