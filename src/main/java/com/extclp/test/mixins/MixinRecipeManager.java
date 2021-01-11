package com.extclp.test.mixins;

import com.extclp.test.TestMod;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Map;

@Mixin(RecipeManager.class)
public abstract class MixinRecipeManager extends JsonDataLoader {

    public MixinRecipeManager(Gson gson, String dataType) {
        super(gson, dataType);
    }

    @Override
    protected Map<Identifier, JsonObject> prepare(ResourceManager resourceManager, Profiler profiler) {
        if(TestMod.getConfig().load_recipes){
            return super.prepare(resourceManager, profiler);
        }
        return Maps.newHashMap();
    }
}
