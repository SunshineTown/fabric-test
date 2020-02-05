package com.extclp.test.mixins;

import com.extclp.test.TestMod;
import com.extclp.test.api.TestWorld;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.LevelProperties;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerManager.class)
public abstract class MixinPlayerManger {

    @Shadow public abstract MinecraftServer getServer();

    @Redirect(method = "onPlayerConnect", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerPlayerEntity;dimension:Lnet/minecraft/world/dimension/DimensionType;", opcode = Opcodes.GETFIELD))
    public DimensionType onPlayerConnect(ServerPlayerEntity player){
        if(player.dimension == DimensionType.THE_END && !TestMod.getConfig().enableEnd ||
                player.dimension == DimensionType.THE_NETHER && !getServer().isNetherAllowed()
        ){
            ServerWorld world = getServer().getWorld(DimensionType.OVERWORLD);
            LevelProperties properties = ((TestWorld) world).getProperties();
            player.dimension = DimensionType.OVERWORLD;
            player.x = properties.getSpawnX();
            player.y = properties.getSpawnY();
            player.z = properties.getSpawnZ();
            return DimensionType.OVERWORLD;
        }
        return player.dimension;
    }
}
