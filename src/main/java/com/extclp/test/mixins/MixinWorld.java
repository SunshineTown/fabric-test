package com.extclp.test.mixins;

import com.extclp.test.api.TestWorld;
import net.minecraft.world.World;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(World.class)
public interface MixinWorld extends TestWorld {

    @Accessor
    LevelProperties getProperties();
}
