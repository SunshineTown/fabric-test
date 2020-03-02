package com.extclp.test.commands;

import com.extclp.test.api.TestPlayerAbilities;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class FlySpeedCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("flyspeed")
                .requires(context -> context.getEntity() instanceof ServerPlayerEntity)
                .executes(context -> rateFlySpeed(context.getSource().getPlayer()))
                .then(CommandManager.argument("speed", FloatArgumentType.floatArg(0, 1))
                        .executes(context -> setFlySpeed(context.getSource().getPlayer(), FloatArgumentType.getFloat(context,"speed"))))
                .then(CommandManager.literal("reset")
                        .executes(context -> resetFlySpeed(context.getSource().getPlayer()))));
    }

    public static int rateFlySpeed(ServerPlayerEntity player){
        player.sendMessage(new LiteralText(String.format("当前飞行速度: %f", player.abilities.getFlySpeed())));
        return 1;
    }

    private static int setFlySpeed(ServerPlayerEntity player, float speed){
        ((TestPlayerAbilities)player.abilities).setFlySpeed(speed);
        player.sendAbilitiesUpdate();
        rateFlySpeed(player);
        return 1;
    }
    private static int resetFlySpeed(ServerPlayerEntity player){
        setFlySpeed(player,0.05f);
        return 1;
    }
}