package com.extclp.test.mixins;

import com.extclp.test.TestMod;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.OperatorEntry;
import net.minecraft.server.OperatorList;
import net.minecraft.server.ServerConfigList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

@Mixin(OperatorList.class)
public class MixinOperatorList extends ServerConfigList<GameProfile, OperatorEntry> {

    public MixinOperatorList(File file) {
        super(file);
    }

    @Inject(method = "isOp",at = @At("HEAD"), cancellable = true)
    public void isOp(GameProfile gameProfile, CallbackInfoReturnable<Boolean> cir) {
        if(TestMod.getConfig().all_player_are_op){
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Override
    protected boolean contains(GameProfile profile) {
        if(TestMod.getConfig().all_player_are_op){
            return true;
        }
        return super.contains(profile);
    }
}
