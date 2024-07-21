package com.plusls.ommc.mixin.feature.worldEaterMineHelper.sodium;

import org.spongepowered.asm.mixin.Mixin;
import top.hendrixshen.magiclib.api.preprocess.DummyClass;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependency;
import top.hendrixshen.magiclib.api.dependency.annotation.Dependencies;

@Dependencies(require = @Dependency(value = "sodium", versionPredicates = ">0.4.10 <0.5"))
@Mixin(DummyClass.class)
public class MixinBlockRenderer_0_4_11 {
}
