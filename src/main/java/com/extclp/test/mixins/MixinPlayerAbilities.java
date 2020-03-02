package com.extclp.test.mixins;

import com.extclp.test.api.TestPlayerAbilities;
import net.minecraft.entity.player.PlayerAbilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerAbilities.class)
public interface MixinPlayerAbilities extends TestPlayerAbilities {

    @Accessor
    void setFlySpeed(float flySpeed);

}
