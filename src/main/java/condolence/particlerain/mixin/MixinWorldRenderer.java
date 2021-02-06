package condolence.particlerain.mixin;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
    // Disable snow & rain rendering
    @Inject(at=@At("HEAD"), method = "renderRainSnow", cancellable = true)
    private void renderRainSnow(LightTexture lightmapIn, float partialTicks, double xIn, double yIn, double zIn, CallbackInfo ci) {
        ci.cancel();
    }
}
