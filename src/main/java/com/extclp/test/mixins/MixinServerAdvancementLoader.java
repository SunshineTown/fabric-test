package com.extclp.test.mixins;

import com.extclp.test.TestMod;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Map;

@Mixin(ServerAdvancementLoader.class)
public abstract class MixinServerAdvancementLoader extends JsonDataLoader {

    public MixinServerAdvancementLoader(Gson gson, String dataType) {
        super(gson, dataType);
    }

    @Override
    protected Map<Identifier, JsonObject> method_20731(ResourceManager resourceManager, Profiler profiler) {
        if(TestMod.getConfig().loadAdvancements){
            return super.method_20731(resourceManager, profiler);
        }
        return Maps.newHashMap();
    }
}
