package com.extclp.test.mixins;

import com.extclp.test.TestMod;
import com.google.common.collect.AbstractIterator;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;
import java.util.Map;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {


    @Redirect(method = "createWorlds", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 1))
    public <K, V> V onLoadEndWorld(Map<K, V> map, K key, V value){
        if(key.equals(DimensionType.THE_END)) {
            if(TestMod.getConfig().enableEnd){
                return map.put(key, value);
            }
            return null;
        }
        return map.put(key, value);
    }

    @Redirect(method = "prepareStartRegion", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;hasNext()Z", ordinal = 0))
    public boolean onPrepareStartRegion(Iterator<DimensionType> iterator){
        AbstractIterator<DimensionType> dimensionTypeAbstractIterator = ((AbstractIterator<DimensionType>) iterator);
        if(dimensionTypeAbstractIterator.hasNext()){
            if(dimensionTypeAbstractIterator.peek() == DimensionType.THE_END  && !TestMod.getConfig().enableEnd){
                dimensionTypeAbstractIterator.next();
            }
            return dimensionTypeAbstractIterator.hasNext();
        }
        return false;
    }
}
