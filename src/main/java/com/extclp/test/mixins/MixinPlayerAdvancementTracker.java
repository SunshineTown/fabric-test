package com.extclp.test.mixins;

import com.extclp.test.TestMod;
import net.minecraft.advancement.PlayerAdvancementTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerAdvancementTracker.class)
public class MixinPlayerAdvancementTracker {

    @Inject(method = "load", at = @At("HEAD"), cancellable = true)
    public void onLoad(CallbackInfo ci){
        if(!TestMod.getConfig().load_advancements){
            ci.cancel();
        }
    }
}
