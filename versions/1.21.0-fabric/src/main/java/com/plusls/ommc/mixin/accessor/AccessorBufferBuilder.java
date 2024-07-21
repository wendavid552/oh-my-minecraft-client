package com.plusls.ommc.mixin.accessor;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BufferBuilder.class)
public interface AccessorBufferBuilder {
    @Accessor
    ByteBufferBuilder getBuffer();
}
