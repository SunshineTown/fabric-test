package com.extclp.test.mixins;

import com.extclp.test.TestMod;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class MixinServerPlayerEntity {

    @Inject(method = "changeDimension", at = @At(value = "HEAD"), cancellable = true)
    public void onChangeDimension(DimensionType newDimension, CallbackInfoReturnable<Entity> cir){
        if (newDimension == DimensionType.THE_END && !TestMod.getConfig().enableEnd) {
            cir.setReturnValue(null);
            cir.cancel();
        }
    }
}
