package com.extclp.test.mixins;

import net.minecraft.server.ServerConfigList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(ServerConfigList.class)
public class MixinServerConfigList {

    @Shadow @Final private Map<String, Object> map;

    @Inject(method = "save", at = @At("HEAD"), cancellable = true)
    public void onSave(CallbackInfo ci){
        if(map.isEmpty()){
            ci.cancel();
        }
    }
}
