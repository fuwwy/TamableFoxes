package net.seanomik.tamablefoxes.versions.version_1_17_1_R1;

import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.EntityFox;
import net.minecraft.world.level.World;
import net.seanomik.tamablefoxes.Utils;
import net.seanomik.tamablefoxes.io.LanguageConfig;
import net.seanomik.tamablefoxes.versions.FieldHelper;
import net.seanomik.tamablefoxes.versions.NMSInterface;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class NMSInterface_1_17_1_R1 implements NMSInterface {

    @Override
    public void registerCustomFoxEntity() {
        Class<?> clazz = null;
        try {
            ClassReader cr = new ClassReader(EntityFox.class.getResourceAsStream("EntityFox.class"));
            ClassNode node = new ClassNode();
            cr.accept(node, 0);

            System.out.println(node.superName);

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            node.accept(cw);
            clazz = new ClassDefiner(ClassLoader.getSystemClassLoader()).get(node.name.replace("/", "."), cw.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }


        try { // Replace the fox entity
            Field field = EntityTypes.E.getClass().getDeclaredField("bm");
            Class<?> finalClazz = clazz;
            FieldHelper.setFieldUsingUnsafe(field, EntityTypes.E, (EntityTypes.b<EntityFox>) (entitytypes, world) -> {
                try {
                    return (EntityFox) finalClazz.getDeclaredConstructor(EntityTypes.class, World.class).newInstance(entitytypes, world);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return null;
            });
            Bukkit.getServer().getConsoleSender().sendMessage(Utils.getPrefix() + ChatColor.GREEN + LanguageConfig.getSuccessReplaced());
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage(Utils.getPrefix() + ChatColor.RED + LanguageConfig.getFailureReplace());
            e.printStackTrace();
        }
    }

    @Override
    public void spawnTamableFox(Location loc, FoxType type) {
        //Object tamableFox = (Object) ((CraftEntity) loc.getWorld().spawnEntity(loc, EntityType.FOX)).getHandle();
        //tamableFox.setFoxType((type == FoxType.RED) ? EntityFox.Type.RED : EntityFox.Type.SNOW);
    }

    static class ClassDefiner extends ClassLoader {
        public ClassDefiner(ClassLoader parent) {
            super(parent);
        }

        public Class<?> get(String name, byte[] bytes) {
            Class<?> c = defineClass(name, bytes, 0, bytes.length);
            resolveClass(c);
            return c;
        }
    }
}
